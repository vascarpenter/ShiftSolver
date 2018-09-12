package com.hatenablog.gikoha.shiftsolver;

import org.apache.commons.lang.builder.CompareToBuilder;

import java.io.Serializable;
import java.util.Comparator;

public class WorkDayAssignmentDifficultyComparator implements
        Comparator<WorkDayAssignment>, Serializable
{
    public int compare(final WorkDayAssignment arg0, final WorkDayAssignment arg1)
    {
        return new CompareToBuilder().append(arg0.getId(), arg1.getId())
                .toComparison();
    }
}
