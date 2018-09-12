package com.hatenablog.gikoha.shiftsolver;
import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;

public abstract class AbstractPersistable implements Serializable,
        Comparable<AbstractPersistable>
{
    protected int id;

    protected String name;

	public AbstractPersistable()
    {
    }

	public AbstractPersistable(final int id, final String name)
    {
        super();
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(final int id)
    {
	    this.id = id;
    }

    public int compareTo(final AbstractPersistable other)
    {
        return new CompareToBuilder()
            .append(this.getClass().getName(), other.getClass().getName())
            .append(this.id, other.id).toComparison();
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
