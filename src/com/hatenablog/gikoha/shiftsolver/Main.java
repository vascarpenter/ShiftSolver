package com.hatenablog.gikoha.shiftsolver;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Main implements SolverEventListener
{
    protected final transient Logger logger = LoggerFactory.getLogger(this
            .getClass());
    private long startDateTime;

    public static void main(String[] args)
    {
        Main main = new Main();
        main.execute();
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

    public void execute()
    {
        SolverFactory solverFactory = SolverFactory.createFromXmlResource("optaplannerConfig.xml");
        Solver solver = solverFactory.buildSolver();
        WorkDaySolution unsolvedWorkDaySolution = new WorkDaySolutionGenerator().createWorkDaySolution();

        solver.addEventListener(this);

        this.startDateTime = new Date().getTime();

        solver.solve(unsolvedWorkDaySolution);
        WorkDaySolution solvedSolution = (WorkDaySolution) solver.getBestSolution();
        this.printScore(solvedSolution);

        //  Display the result
        String jobResult = toDisplayJobString(solvedSolution);
        System.out.println(jobResult);
    }

    public void bestSolutionChanged(final BestSolutionChangedEvent event)
    {
        this.printScore((WorkDaySolution) event.getNewBestSolution());
    }

    private void printScore(final WorkDaySolution solution)
    {
        this.logger.debug("TaskName:TaskName0 " + "ElapsedTime:"
                + (new Date().getTime() - this.startDateTime) + "ms BestScore:"
                + solution.getScore().toString());
    }
}
