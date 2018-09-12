/*
 * Copyright 2014 OGIS-RI All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hatenablog.gikoha.shiftsolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkDaySolutionGenerator
{

	public WorkDaySolution createWorkDaySolution()
    {
        int id = 0;

        List<Day> dayList = new ArrayList<Day>();
        List<WorkDayAssignment> workDayAssignmentList = new ArrayList<WorkDayAssignment>();

        for(int i=0;i<31;i++)
        {
            String s = String.valueOf(i+1);
            Day day = new Day(id++,s,i+1);
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
