package br.com.core;

import static org.junit.Assert.assertFalse;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class ReportGenerator {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void createReport(String scenarioName) {
        // Criar o relatório somente se ainda não tiver sido inicializado
        if (extent == null) {
            // Configurar o local do relatório e outros detalhes
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("relatorios//test-report.html");
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);

            // Definir informações sobre o relatório
            extent.setSystemInfo("Sistema", "");
            extent.setSystemInfo("Ambiente", "Pré Produção");
            htmlReporter.config().setReportName(scenarioName);
            deleteFilesInDirectory("relatorios/screenshots/");
            
        }
                
    }

    public static void startTest(String testName) {
        test = extent.createTest(testName);
    }
    

    public static void logStatus(Status status, String message) {
        test.log(status, message, MediaEntityBuilder.createScreenCaptureFromPath(addScreenshotToReport()).build());
    }
    

    public static void endTest() {
        extent.flush();
    }
    
    
    public static void validateEquals(String esperado, String recebido) {
    	if(esperado.equals(recebido)) {
    		ReportGenerator.logStatus(Status.PASS, "O valor esperado é igual ao recebido. Esperado: " + esperado + " Recebido: " + recebido);
    	}else {
    		ReportGenerator.logStatus(Status.FAIL, "O valor esperado é diferente do recebido. Esperado: " + esperado + " Recebido: " + recebido);
    		assertFalse("Falha! Verifique possivel BUG", true);
    	} 	
    }
    
 
    public static String addScreenshotToReport() {
    	String path = null;
        try {
            WebDriver driver = DriverFactory.getDriver();

            // Use TakesScreenshot para capturar a tela
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
            path = "relatorios/screenshots/" + System.currentTimeMillis() + ".png";
            // Copiar a captura de tela para o diretório do relatório (ou qualquer diretório desejado)
            File destFile = new File(path);
            FileUtils.copyFile(screenshotFile, destFile);

            path = destFile.getAbsolutePath();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		return path;
    }
    

    
    public static void deleteFilesInDirectory(String directoryPath) {
        try {
            File directory = new File(directoryPath);
            if (directory.exists() && directory.isDirectory()) {
                FileUtils.cleanDirectory(directory);
            } else {
                directory.mkdirs(); // Crie o diretório se ele não existir
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
