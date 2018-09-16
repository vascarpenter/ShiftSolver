/*
 * Copyright (c) gikoha 2018.
 * このソフトウェアは、 Apache 2.0ライセンスで配布されている製作物が含まれています。
 * This software includes the work that is distributed in the Apache License 2.0
 *
 */

package com.hatenablog.gikoha.shiftsolver;

import java.util.HashMap;
import java.util.List;

public class Employee extends AbstractPersistable
{
    protected String workDayAssets;
    protected String holidayAssets;
    protected List<Day> dayList;
    protected HashMap<Day, Boolean> othersWorkDayMap;
    private HashMap<Day, Boolean> holidayMap;
    private HashMap<Day, Boolean> workDayMap;

    public Employee()
    {
        super();
    }

    public Employee(final int id, final String name, final String workDay, final String holiday, final List<Day> dayList)
    {
        super(id, name);
        // HashMapの設定にdayListを用いるためコピーを保存
        setDayList(dayList);

        setWorkDayAssets(workDay);
        setHolidayAssets(holiday);

        HashMap<Day, Boolean> othersWorkDayMap = new HashMap<Day, Boolean>();

        // HashMapで nullを帰すとDRLで落ちるので最初すべてfalseで初期化
        for (Day day : this.dayList)
        {
            othersWorkDayMap.put(day, false);
        }
        this.othersWorkDayMap = othersWorkDayMap;
    }

    public void setDayList(List<Day> dayList)
    {
        this.dayList = dayList;
    }

    public boolean isHoliday(final Day day)
    {
        if (day == null)
            return false;
        return this.holidayMap.get(day);
    }

    public boolean isWorkDay(final Day day)
    {
        if (day == null)
            return false;
        return this.workDayMap.get(day);
    }

    public boolean isOthersWorkDay(final Day day)
    {
        if (day == null)
            return false;
        return this.othersWorkDayMap.get(day);
    }


    public String getWorkDayAssets()
    {
        if (workDayAssets == null) return "";
        return workDayAssets;
    }

    public void setWorkDayAssets(String workDayAssets)
    {
        HashMap<Day, Boolean> workDayMap = new HashMap<Day, Boolean>();
        this.workDayAssets = workDayAssets;

        // HashMapで nullを帰すとDRLで落ちるので最初すべてfalseで初期化
        for (Day day : this.dayList)
        {
            workDayMap.put(day, false);
        }
        if (workDayAssets != null)
        {
            // ","で区切られた文字列を解析
            String[] s = workDayAssets.split(",");
            for (String st : s)
            {
                int day = 0;
                try
                {
                    day = Integer.parseInt(st);
                    if (day >= 1 && day <= 31)
                        workDayMap.put(this.dayList.get(day - 1), true);
                }
                catch (NumberFormatException e)
                {
                }
            }
        }
        this.workDayMap = workDayMap;

    }

    public String getHolidayAssets()
    {
        if (holidayAssets == null) return "";
        return holidayAssets;
    }

    public void setHolidayAssets(String holidayAssets)
    {
        HashMap<Day, Boolean> holidayMap = new HashMap<Day, Boolean>();
        this.holidayAssets = holidayAssets;

        // HashMapで nullを帰すとDRLで落ちるので最初すべてfalseで初期化
        for (Day day : this.dayList)
        {
            holidayMap.put(day, false);
        }
        if (holidayAssets != null)
        {
            // ","で区切られた文字列を解析
            String[] s = holidayAssets.split(",");
            for (String st : s)
            {
                int day = 0;
                try
                {
                    day = Integer.parseInt(st);
                    if (day >= 1 && day <= 31)
                        holidayMap.put(this.dayList.get(day - 1), true);
                }
                catch (NumberFormatException e)
                {
                }
            }
        }
        this.holidayMap = holidayMap;
    }

    public HashMap<Day, Boolean> getOthersWorkDayMap()
    {
        return othersWorkDayMap;
    }

    public void setOthersWorkDayMap(HashMap<Day, Boolean> othersWorkDayMap)
    {
        this.othersWorkDayMap = othersWorkDayMap;
    }

}
