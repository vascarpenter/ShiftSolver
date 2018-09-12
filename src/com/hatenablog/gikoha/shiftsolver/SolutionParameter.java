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
