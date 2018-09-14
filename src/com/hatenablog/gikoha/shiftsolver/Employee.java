package com.hatenablog.gikoha.shiftsolver;

public class Employee extends AbstractPersistable
{
    protected String workDayAssets;
    protected String holidayAssets;

    public Employee()
    {
        super();
    }

    public Employee(final int id, final String name, final String workDay, final String holiday)
    {
        super(id, name);
        workDayAssets = workDay;
        holidayAssets = holiday;
    }

    public String getWorkDayAssets()
    {
        if (workDayAssets == null) return "";
        return workDayAssets;
    }

    public void setWorkDayAssets(String workDayAssets)
    {
        this.workDayAssets = workDayAssets;
    }

    public String getHolidayAssets()
    {
        if (holidayAssets == null) return "";
        return holidayAssets;
    }

    public void setHolidayAssets(String holidayAssets)
    {
        this.holidayAssets = holidayAssets;
    }
}
