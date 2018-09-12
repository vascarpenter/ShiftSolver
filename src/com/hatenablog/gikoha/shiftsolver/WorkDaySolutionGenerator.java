package com.hatenablog.gikoha.shiftsolver;

import java.util.ArrayList;
import java.util.List;

public class WorkDaySolutionGenerator
{

    public WorkDaySolution createWorkDaySolution()
    {
        int id = 0;

        List<Day> dayList = new ArrayList<Day>();
        List<WorkDayAssignment> workDayAssignmentList = new ArrayList<WorkDayAssignment>();

        for (int i = 0; i < 31; i++)
        {
            String s = String.valueOf(i + 1);
            Day day = new Day(id++, s, i + 1);
            dayList.add(day);

            WorkDayAssignment workDayAssignment = new WorkDayAssignment(id++, s + " Assignment", day);
            workDayAssignmentList.add(workDayAssignment);
        }

        List<Employee> employeeList = new ArrayList<Employee>();
        Employee e1 = new Employee(id++, "Doctor A");
        employeeList.add(e1);

        Employee e2 = new Employee(id++, "Doctor B");
        employeeList.add(e2);

        Employee e3 = new Employee(id++, "Doctor C");
        employeeList.add(e3);

        return new WorkDaySolution(employeeList, workDayAssignmentList, dayList);


    }
}
