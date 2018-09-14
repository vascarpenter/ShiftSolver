package com.hatenablog.gikoha.shiftsolver;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;
import org.optaplanner.core.config.solver.SolverConfig;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.nio.channels.*;

public class ShiftView extends JFrame implements SolverEventListener
{
    private JButton startButton;
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton stopButton;
    private JLabel scoreField;
    private JTable table1;
    private JButton loadButton;
    private JButton saveButton;
    private JTable table2;
    private JButton saveResultButton;
    private JLabel statusLabel;

    private long startDateTime;

    private Solver<WorkDaySolution> solver;
    private WorkDaySolution unsolvedWorkDaySolution;
    private WorkDaySolution bestSolution;
    private String[] columns = {"名前", "拘束日", "休日希望日"};
    private String[] columns1 = {"日付", "名前"};

    private List<Employee> employeeList;
    private List<WorkDayAssignment> workDayAssignmentList;
    private List<Day> dayList;
    private TableModel dataModel1;
    private TableModel dataModel2;

    public ShiftView()
    {
        readLists();

        dataModel1 = new
                AbstractTableModel()
                {
                    public int getColumnCount()
                    {
                        return 3;
                    }

                    public String getColumnName(int column)
                    {
                        return columns[column];
                    }

                    public int getRowCount()
                    {
                        return employeeList.size();
                    }

                    public boolean isCellEditable(int row, int col)
                    {
                        return (col == 1 || col == 2);
                    }

                    public Object getValueAt(int row, int col)
                    {
                        switch (col)
                        {
                        case 0:
                            return employeeList.get(row);
                        case 1:
                            return employeeList.get(row).getWorkDayAssets();
                        case 2:
                            return employeeList.get(row).getHolidayAssets();
                        }
                        return "";
                    }

                    public void setValueAt(Object aValue, int row, int col)
                    {
                        switch (col)
                        {
                        case 1:
                            employeeList.get(row).setWorkDayAssets(aValue.toString());
                            break;
                        case 2:
                            employeeList.get(row).setHolidayAssets(aValue.toString());
                            break;
                        }
                    }
                };

        dataModel2 = new
                AbstractTableModel()
                {
                    public int getColumnCount()
                    {
                        return 2;
                    }

                    public String getColumnName(int column)
                    {
                        return columns1[column];
                    }

                    public int getRowCount()
                    {
                        return dayList.size();
                    }

                    public Object getValueAt(int row, int col)
                    {
                        if (col == 1)
                        {
                            if (bestSolution == null)
                                return "-";         // まだoptaplannerが走っていない
                            WorkDayAssignment workDayAssignment = bestSolution.getWorkDayAssignmentList().get(row);
                            return workDayAssignment.getEmployee();
                        }
                        return dayList.get(row);
                    }

                };

        solver = null;
        unsolvedWorkDaySolution = null;
        bestSolution = null;

        table1.setModel(dataModel1);
        table2.setModel(dataModel2);

        setTitle("シフト割り振りプログラム 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        pack();
        setBounds(100, 100, 600, 500);
        setVisible(true);

        setSolvingState(false);

        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                executeAction();

            }
        });
        stopButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (solver != null && solver.isSolving())
                    solver.terminateEarly();
            }
        });

        loadButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loadAction();
            }
        });
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                saveAction();
            }
        });
        saveResultButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                saveResultAction();
            }
        });
    }


    public void readLists()
    {
        int id = 0;
        dayList = new ArrayList<Day>();
        workDayAssignmentList = new ArrayList<WorkDayAssignment>();

        for (int i = 0; i < 31; i++)
        {
            String s = String.valueOf(i + 1);
            Day day = new Day(id++, s, i + 1);
            dayList.add(day);

            WorkDayAssignment workDayAssignment = new WorkDayAssignment(id++, s + " Assignment", day);
            workDayAssignmentList.add(workDayAssignment);
        }

        InputStream employeeFileStream = this.getClass().getClassLoader()
                .getResourceAsStream("com/hatenablog/gikoha/shiftsolver/doctor.csv");

        readEmployeeListCSV(new InputStreamReader(employeeFileStream));

    }

    public void readEmployeeListCSV(Reader stream)
    {
        int id = 1000;
        employeeList = new ArrayList<Employee>();

        try
        {
            CSVReader employeeReader = new CSVReader(stream);
            String str[];
            while ((str = employeeReader.readNext()) != null)
            {
                Employee e;
                if (str.length == 3)
                {
                    e = new Employee(id++, str[0], str[1], str[2]);
                } else
                {
                    e = new Employee(id++, str[0], "", "");
                }
                employeeList.add(e);
            }
            employeeReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // error, default to Doctor A,B,C
            Employee e1 = new Employee(id++, "Doctor A", null, null);
            employeeList.add(e1);

            Employee e2 = new Employee(id++, "Doctor B", null, null);
            employeeList.add(e2);

            Employee e3 = new Employee(id++, "Doctor C", null, null);
            employeeList.add(e3);
        }

    }

    public void loadAction()
    {
        JFileChooser filechooser = new JFileChooser();
        int selected = filechooser.showOpenDialog(this);
        if (selected != JFileChooser.APPROVE_OPTION) return;
        File file = filechooser.getSelectedFile();

        // delete all employee from list

        Iterator<Employee> i = employeeList.iterator();
        while (i.hasNext())
        {
            Employee e = i.next();
            i.remove();
        }

        try
        {
            readEmployeeListCSV(new FileReader(file));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        // TableData changed notification
        ((AbstractTableModel) dataModel1).fireTableDataChanged();
    }

    public void saveAction()
    {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setSelectedFile(new File(System.getProperty("user.home"),
                "doctor.csv"));
        int selected = filechooser.showSaveDialog(this);
        if (selected != JFileChooser.APPROVE_OPTION) return;
        File file = filechooser.getSelectedFile();
        try
        {
            CSVWriter writer = new CSVWriter(new FileWriter(file.getPath()));
            for (Employee e : employeeList)
            {
                String[] entries = {e.toString(), e.getWorkDayAssets(), e.getHolidayAssets()};
                writer.writeNext(entries);
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void saveResultAction()
    {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setSelectedFile(new File(System.getProperty("user.home"),
                "result.csv"));
        int selected = filechooser.showSaveDialog(this);
        if (selected != JFileChooser.APPROVE_OPTION) return;
        File file = filechooser.getSelectedFile();
        try
        {
            CSVWriter writer = new CSVWriter(new FileWriter(file.getPath()));
            for (WorkDayAssignment e : bestSolution.getWorkDayAssignmentList())
            {
                String[] entries = {e.getDay().toString(), e.getEmployee().toString()};
                writer.writeNext(entries);
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public final void setSolvingState(boolean state)
    {
        startButton.setEnabled(!state);
        stopButton.setEnabled(state);
        saveResultButton.setEnabled(bestSolution != null);
    }

    /*
    public final void copyStreamToTemp(String src, String dest)
    {
        try
        {

            InputStream drlStream = this.getClass().getClassLoader()
                    .getResourceAsStream(src);

            // copy stream to temporary file
            FileOutputStream fo = new FileOutputStream(dest);
            byte[] buf = new byte[256];
            int len;
            // ファイルの終わりまで読み込む
            while((len = drlStream.read(buf)) != -1){
                fo.write(buf,0,len);
            }

            //ファイルの終了処理
            fo.close();
            drlStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
*/

    public final void executeAction()
    {
        bestSolution = null;

        setSolvingState(true);
        statusLabel.setText("検索中...(10秒間)");

        //copyStreamToTemp("com/hatenablog/gikoha/shiftsolver/optaplannerWorkSolutionScoreRules.drl", "/tmp/temp.drl");
        // specify optaplannerConfig.xml


 //       SolverFactory solverFactory = SolverFactory.createFromXmlFile(new File("/tmp/temp.xml"));
        InputStream cfgStream = this.getClass().getClassLoader()
                .getResourceAsStream("com/hatenablog/gikoha/shiftsolver/optaplannerConfig.xml");
        SolverFactory solverFactory = SolverFactory.createFromXmlInputStream(cfgStream);

        solver = solverFactory.buildSolver();
        solver.addEventListener(this);
        unsolvedWorkDaySolution = new WorkDaySolution(employeeList, workDayAssignmentList, dayList);

        this.startDateTime = new Date().getTime();

        SolveWorker worker = new SolveWorker(unsolvedWorkDaySolution);      // 非同期実行
        worker.execute();
    }

    public void bestSolutionChanged(final BestSolutionChangedEvent event)
    {
        this.printScore((WorkDaySolution) event.getNewBestSolution());
    }

    private void printScore(final WorkDaySolution solution)
    {
        scoreField.setText("ElapsedTime:"
                + (new Date().getTime() - this.startDateTime) + "ms BestScore:"
                + solution.getScore().toString());
    }

    protected class SolveWorker extends SwingWorker<WorkDaySolution, Void>
    {

        protected final WorkDaySolution problem;

        public SolveWorker(WorkDaySolution problem)
        {
            this.problem = problem;
        }

        @Override
        protected WorkDaySolution doInBackground() throws Exception
        {
            return solver.solve(problem);
        }

        @Override
        protected void done()
        {
            try
            {
                bestSolution = get();

                printScore(bestSolution);
                ((AbstractTableModel) dataModel2).fireTableDataChanged();

            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Solving was interrupted.", e);
            }
            catch (ExecutionException e)
            {
                throw new IllegalStateException("Solving failed.", e.getCause());
            }
            finally
            {
                setSolvingState(false);
                statusLabel.setText("終了");
            }
        }

    }
}
