package org.example.utils.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    public static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/extent-report.html");

        reporter.config().setReportName("Test Report");
        reporter.config().setTheme(Theme.DARK);

        extentReports.attachReporter(reporter);
        return extentReports;
    }
}
