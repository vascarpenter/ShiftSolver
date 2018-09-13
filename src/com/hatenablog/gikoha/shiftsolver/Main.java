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
            public void run()
            {
                try
                {
                    ShiftView window = new ShiftView();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


}
