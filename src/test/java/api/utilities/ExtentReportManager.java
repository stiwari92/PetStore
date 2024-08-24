package api.utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {

    private ExtentSparkReporter extentSparkReporter;
    private ExtentReports extentReports;
    private ExtentTest extentTest;
    private String repName;

    @Override
    public void onStart(ITestContext context) {
        // Initialize ExtentReports when the test suite starts
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timestamp + ".html";
        extentSparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/Reports/" + repName);
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);

        // Configuration for the report
        extentSparkReporter.config().setDocumentTitle("Simple Automation Report");
        extentSparkReporter.config().setReportName("Test Report");
        extentSparkReporter.config().setTheme(Theme.STANDARD);
        extentSparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Initialize extentTest when a new test starts
        extentTest = extentReports.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.log(Status.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.log(Status.SKIP, "Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Write or update test information to the reporter
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
