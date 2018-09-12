package com.hatenablog.gikoha.shiftsolver;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.lang.builder.CompareToBuilder;

public class WorkDayAssignmentDifficultyComparator implements
        Comparator<WorkDayAssignment>, Serializable
{
    public int compare(final WorkDayAssignment arg0, final WorkDayAssignment arg1) {
        return new CompareToBuilder().append(arg0.getId(), arg1.getId())
                .toComparison();
    }
}
