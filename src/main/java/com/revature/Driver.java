package com.revature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Driver
{
    private static final Logger logger = LogManager.getLogger(Driver.class);

    //Entry point of the application.
    public static void main(String args[])
    {
        logger.info("Starting Application");
        Application.run();
    }
}
