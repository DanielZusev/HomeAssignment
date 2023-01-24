package org.example.utils.logger;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.example.utils.report.ExtentTestManager.getTest;

/**
 * custom logger class based on log4j library
 * for future cases we could add any functionality to each log event
 * like adding screenshot for each info/error step for better documentation of the tests
 */
public class MyLogger {
    private static final Logger Log = LogManager.getLogger(MyLogger.class);
    private static final ExtentTest extentTest = getTest();

    //Info Level Logs
    public static void info(String message) {
        Log.info(message);
        if (extentTest != null)
            extentTest.info(message);
    }

    //Warn Level Logs
    public static void warn(String message) {
        Log.warn(message);
    }

    //Error Level Logs
    public static void error(String message) {
        Log.error(message);
        if (extentTest != null)
            extentTest.fail(message);
    }

    //Fatal Level Logs
    public static void fatal(String message) {
        Log.fatal(message);
    }

    //Debug Level Logs
    public static void debug(String message) {
        Log.debug(message);
    }
}
