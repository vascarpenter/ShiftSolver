/*
 * Copyright (c) gikoha 2018.
 * このソフトウェアは、 Apache 2.0ライセンスで配布されている製作物が含まれています。
 * This software includes the work that is distributed in the Apache License 2.0
 *
 */

package com.hatenablog.gikoha.shiftsolver;

//workaround class for drl
public class SolutionParameter extends AbstractPersistable
{
    private float countAverage;

    public SolutionParameter(final int id, final String name)
    {
        super(id, name);
    }

    public float getCountAverage()
    {
        return countAverage;
    }

    public void setCountAverage(float countAverage)
    {
        this.countAverage = countAverage;
    }
}
