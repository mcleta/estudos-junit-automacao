package br.com.core;

import static br.com.core.DriverFactory.getDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

public interface Utils {

	default void navigateTo(String url) {
		try {
			getDriver().navigate().to(url);
			ReportGenerator.logStatus(Status.PASS, "Acessou a URL: "+url);
		} catch (Exception e) {
			ReportGenerator.logStatus(Status.PASS, "Não acessou a URL: "+url);
		}
	}

	public default void click(By by) {
		try {	
			//highlightElement(getDriver(), getDriver().findElement(by));
			getDriver().findElement(by);
			getDriver().findElement(by).click();
			ReportGenerator.logStatus(Status.PASS, "Clicou no elemento");	
		} catch (Exception e) {
			ReportGenerator.logStatus(Status.FAIL, "Não clicou no elemento");
		}

	}
	

		
	public default  void selectOptions(WebElement element, int indexToSelect) {
	    Select select = new Select(element);
	    select.selectByIndex(indexToSelect);
//	    List<WebElement> options = select.getOptions();
//	    System.out.println("opção escolhido eh: "+options.get(indexToSelect).getText());
	}
			


	public default void setText(By by, String texto) {

		try {
			getDriver().findElement(by).clear();
			getDriver().findElement(by).sendKeys(texto);
			ReportGenerator.logStatus(Status.PASS, "Preencheu campo");
			
		} catch (Exception e) {
			ReportGenerator.logStatus(Status.FAIL, "Não preencheu campo");
		}
	}
	

	public default String getText(By by) {
		String text = null;
				
		try {
			getDriver().findElement(by).click();
			WebElement object = getDriver().findElement(by);
			text = object.getText();
			ReportGenerator.logStatus(Status.PASS, "Clicou no elemento");
			return text;
		} catch (Exception e) {
			ReportGenerator.logStatus(Status.FAIL, "Não clicou no elemento");
		}
		

		return text;

	}
	


	default void explicitWait(WebElement by, int timeoutInSeconds) {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
		wait.until(ExpectedConditions.visibilityOf(by));
	}

	public default String alertaObterTexto() {
		Alert alert = getDriver().switchTo().alert();
		return alert.getText();
	}

	public default String alertaObterTextoEAceita() {
		Alert alert = getDriver().switchTo().alert();
		String valor = alert.getText();
		alert.accept();
		return valor;

	}

	public default String alertaObterTextoENega() {
		Alert alert = getDriver().switchTo().alert();
		String valor = alert.getText();
		alert.dismiss();
		return valor;

	}

	public default void alertaEscrever(String valor) {
		Alert alert = getDriver().switchTo().alert();
		alert.sendKeys(valor);
		alert.accept();
	}

	public default void entrarFrame(String id) {
		getDriver().switchTo().frame(id);
	}

	public default void sairFrame() {
		getDriver().switchTo().defaultContent();
	}

	public default void trocarJanela(int indice) {
		
		List<String> handlesList = new ArrayList <String>(getDriver().getWindowHandles());
		getDriver().switchTo().window(handlesList.get(indice));
	}

	default void fixedWait(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (Exception e) {
		}
	}

	public default void switchTo(By by) {
		WebElement element = getDriver().findElement(by);
		((WebDriver) element).switchTo().frame(element);
	}
	
	

	public default void redimensionar() {
		
		getDriver().manage().window().maximize();	
	    }
    public static void highlightElement(WebDriver driver, WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Armazena o estilo atual do elemento
            String originalStyle = element.getAttribute("style");

            // Adiciona um estilo temporário para destacar o elemento
            js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red; background: yellow;');", element);

            // Aguarda um curto período para que o destaque seja visível
            Thread.sleep(500);

            // Reverte ao estilo original
            js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public default void clickByJS() {
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();
        js.executeScript("arguments[0].click();");
    }


    default void assertElementText(By by, String expectedText, String message) {
        try {
            WebElement element = getDriver().findElement(by);
            String actualText = element.getText();

            Assert.assertEquals(message, expectedText, actualText);

            ReportGenerator.logStatus(Status.PASS,
                    String.format("Texto verificado com sucesso. Esperado: '%s', Encontrado: '%s'",
                            expectedText, actualText));

        } catch (Exception e) {
            ReportGenerator.logStatus(Status.FAIL,
                    String.format("Falha ao verificar texto do elemento: %s", e.getMessage()));
            throw new AssertionError(message + " - " + e.getMessage(), e);
        }
    }
  

}
