package com.hatenablog.gikoha.shiftsolver;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class ShiftView extends JFrame implements SolverEventListener
{
    private JButton startButton;
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton stopButton;
    private JLabel scoreField;

    private long startDateTime;

    private Solver<WorkDaySolution> solver;
    private WorkDaySolution unsolvedWorkDaySolution;
    private WorkDaySolution solvedSolution;

    public ShiftView()
    {
        setTitle("シフト割り振りプログラム 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        pack();
        setBounds(100, 100, 500, 500);
        setVisible(true);
        solver = null;
        solvedSolution = null;
        unsolvedWorkDaySolution = null;

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

    }

    public static String toDisplayJobString(final WorkDaySolution sol)
    {
        StringBuilder displayString = new StringBuilder();

        displayString.append("Day\tPeople\n");
        for (WorkDayAssignment workDayAssignment : sol.getWorkDayAssignmentList())
        {
            Employee employee = workDayAssignment.getEmployee();
            displayString.append(workDayAssignment.getDay())
                    .append("\t")
                    .append(employee)
                    .append("\n");
        }
        return displayString.toString();
    }

    public final void setSolvingState(boolean state)
    {
        startButton.setEnabled(!state);
        stopButton.setEnabled(state);

    }

    public final void executeAction()
    {
        setSolvingState(true);
        textArea1.setText("検索中...");

        SolverFactory solverFactory = SolverFactory.createFromXmlResource("optaplannerConfig.xml");
        solver = solverFactory.buildSolver();
        unsolvedWorkDaySolution = new WorkDaySolutionGenerator().createWorkDaySolution();

        solver.addEventListener(this);

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
                WorkDaySolution bestSolution = get();

                //  Display the result
                String jobResult = toDisplayJobString(bestSolution);
                textArea1.setText(jobResult);

            } catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Solving was interrupted.", e);
            } catch (ExecutionException e)
            {
                throw new IllegalStateException("Solving failed.", e.getCause());
            } finally
            {
                setSolvingState(false);
            }
        }

    }
}
