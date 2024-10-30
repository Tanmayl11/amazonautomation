package amazon;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporter {

    private static ExtentReports extentReportInstance;
    private static ExtentSparkReporter htmlReporter;

    private Reporter() {
        // private constructor to prevent instantiation
    }

    public static synchronized ExtentReports getInstance() throws IOException {
        // Get the current timestamp to create a unique report file
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String timestampString = String.valueOf(timestamp.getTime());

        if (extentReportInstance == null) {
            // Initialize the ExtentHtmlReporter with the desired report file path
            String reportPath = System.getProperty("user.dir") + "/ExtentReport_" + timestampString + ".html";
            htmlReporter = new ExtentSparkReporter(reportPath);

            // Optionally configure the report appearance
            htmlReporter.config().setTheme(Theme.STANDARD); // Set report theme
            htmlReporter.config().setDocumentTitle("Automation Test Report"); // Report title
            htmlReporter.config().setEncoding("utf-8"); // Encoding type
            htmlReporter.config().setReportName("Automation Test Report"); // Report name

            // Load external configuration file, if necessary
            htmlReporter.loadXMLConfig(new File("C:\\Users\\tladk\\Amazon\\extent_customization_configs.xml"));

            // Create the ExtentReports instance and attach the reporter
            extentReportInstance = new ExtentReports();
            extentReportInstance.attachReporter(htmlReporter);

            // You can optionally add system info here
            extentReportInstance.setSystemInfo("OS", System.getProperty("os.name"));
            extentReportInstance.setSystemInfo("Java Version", System.getProperty("java.version"));
        }

        return extentReportInstance;
    }

    public static synchronized void endTest(ExtentTest test) {
        if (extentReportInstance != null) {
            // End the test
            extentReportInstance.flush();
        }
    }

    public static synchronized void flush() {
        if (extentReportInstance != null) {
            // Ensure all information is written to the report
            extentReportInstance.flush();
        }
    }
}
