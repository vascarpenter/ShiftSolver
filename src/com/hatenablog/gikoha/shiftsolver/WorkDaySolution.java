package com.hatenablog.gikoha.shiftsolver;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoftbigdecimal.HardSoftBigDecimalScore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PlanningSolution
public class WorkDaySolution implements Solution<HardSoftBigDecimalScore>
{
    private List<Employee> employeeList;
    private List<Day> dayList;

    private HardSoftBigDecimalScore score;

    private List<WorkDayAssignment> workDayAssignmentList;

    private SolutionParameter solutionParameter;

    public WorkDaySolution()
    {   // The class (class com.hatenablog.gikoha.shiftsolver.WorkDaySolution) should have a no-arg constructor to create a planning clone
    }

    public WorkDaySolution(final List<Employee>employeeList, final List<WorkDayAssignment>workDayAssignmentList,
                           final List<Day> dayList)
    {
        this.employeeList = employeeList;
        this.workDayAssignmentList = workDayAssignmentList;
        this.dayList = dayList;
        this.solutionParameter = new SolutionParameter(9999, "SolutionParameter");

        // 平均的な拘束回数を求め、DRL用のworkaround classに入れる
        this.solutionParameter.setCountAverage((float)workDayAssignmentList.size() / (float)employeeList.size());
    }

    @ValueRangeProvider(id = "employeeList")
    public List<Employee> getEmployeeList()
    {
        return this.employeeList;
    }
    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @PlanningEntityCollectionProperty()
    public List<WorkDayAssignment> getWorkDayAssignmentList() {
        return workDayAssignmentList;
    }

    public void setWorkDayAssignmentList(List<WorkDayAssignment> workDayAssignmentList) {
        this.workDayAssignmentList = workDayAssignmentList;
    }


    public HardSoftBigDecimalScore getScore() {
        return this.score;
    }

    public void setScore(final HardSoftBigDecimalScore score) {
        this.score = score;
    }


    @Override
    public Collection<Object> getProblemFacts()
    {
        List<Object> facts = new ArrayList<Object>();
        facts.addAll(this.employeeList);
        facts.addAll(this.dayList);
        facts.add(this.solutionParameter);
        return facts;
    }
}
