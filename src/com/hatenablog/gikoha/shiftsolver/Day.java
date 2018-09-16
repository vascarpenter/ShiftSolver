/*
 * Copyright (c) gikoha 2018.
 * このソフトウェアは、 Apache 2.0ライセンスで配布されている製作物が含まれています。
 * This software includes the work that is distributed in the Apache License 2.0
 *
 */

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
