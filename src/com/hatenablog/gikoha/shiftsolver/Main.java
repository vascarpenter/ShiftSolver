/*
 * Copyright (c) gikoha 2018.
 * このソフトウェアは、 Apache 2.0ライセンスで配布されている製作物が含まれています。
 * This software includes the work that is distributed in the Apache License 2.0
 *
 */

package com.hatenablog.gikoha.shiftsolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Main
{
    protected final transient Logger logger = LoggerFactory.getLogger(this
            .getClass());

    public static void main(String[] args)
    {

        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    ShiftView window = new ShiftView();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


}
