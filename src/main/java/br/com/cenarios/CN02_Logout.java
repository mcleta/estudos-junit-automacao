package br.com.cenarios;

import br.com.core.DriverFactory;
import br.com.core.ReportGenerator;
import br.com.pages.PageLogout;
import com.aventstack.extentreports.ExtentReports;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CN02_Logout {
    private static ExtentReports extent;

    @BeforeClass
    public static void setUp() {
        extent = new ExtentReports();
        ReportGenerator.createReport("CN02 - Logout");
    }

    @AfterClass
    public static void tearDown() {
        extent.flush();
    }

    @After
    public void finaliza(){
        DriverFactory.killDriver();
        ReportGenerator.endTest();
    }

    @Test
    public void sairDoApp(){
        ReportGenerator.startTest("CT02 - Logout do Cliente");

        System.out.println("CT02 Started");
        new PageLogout().sairDoApp();
    }
}
