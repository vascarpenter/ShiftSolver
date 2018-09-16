/*
 * Copyright (c) gikoha 2018.
 * このソフトウェアは、 Apache 2.0ライセンスで配布されている製作物が含まれています。
 * This software includes the work that is distributed in the Apache License 2.0
 *
 */

package com.hatenablog.gikoha.shiftsolver;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity(difficultyComparatorClass = WorkDayAssignmentDifficultyComparator.class)
public class WorkDayAssignment extends AbstractPersistable
{

    private Employee employee;
    private Day day;

    public WorkDayAssignment()
    {
        super();
    }

    public WorkDayAssignment(final int id, final String name, final Day day)
    {
        super(id, name);
        this.day = day;
    }

    @PlanningVariable(nullable = true, valueRangeProviderRefs = {"employeeList"})

    public Employee getEmployee()
    {
        return employee;
    }

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    public Day getDay()
    {
        return day;
    }

    public void setDay(Day day)
    {
        this.day = day;
    }
}
