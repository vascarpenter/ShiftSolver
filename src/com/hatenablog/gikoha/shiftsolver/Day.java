package com.hatenablog.gikoha.shiftsolver;

public class Day extends AbstractPersistable
{
    protected int dayValue;
    public Day()
    {
        super();
    }

    public Day(final int id, final String name, final int dayValue)
    {
        super(id, name);
        this.dayValue = dayValue;
    }

    public int getDayValue()
    {
        return dayValue;
    }

    public void setDayValue(int dayValue)
    {
        this.dayValue = dayValue;
    }
}
