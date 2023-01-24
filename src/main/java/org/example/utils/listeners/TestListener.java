package org.example.utils.listeners;

import com.aventstack.extentreports.Status;
import org.example.utils.logger.MyLogger;
import org.example.utils.report.ExtentManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static org.example.utils.report.ExtentTestManager.getTest;

public class TestListener implements ITestListener {
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        MyLogger.info("Suite " + iTestContext.getName() + " is finished");
        //tier down operations for ExtentReports reporting
        ExtentManager.extentReports.flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        MyLogger.info(getTestMethodName(iTestResult) + " test is starting.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        MyLogger.info(getTestMethodName(iTestResult) + " test is succeed.");
        //ExtentReports log operation for passed tests.
        getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        MyLogger.info(getTestMethodName(iTestResult) + " test is failed.");
        //ExtentReports log operation for failed tests.
        getTest().log(Status.FAIL, "Test Failed");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        MyLogger.info(getTestMethodName(iTestResult) + " test is skipped.");
        //ExtentReports log operation for skipped tests.
        getTest().log(Status.SKIP, "Test Skipped");
    }

}
