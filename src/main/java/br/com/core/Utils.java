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

/*
    Métodos de asserção para textos e elementos
    *********************************************************************************************
*/

    public default void assertElementText(By by, String expectedText) {
        assertElementText(by, expectedText, "Verificação de texto do elemento");
    }

    public default void assertElementText(By by, String expectedText, String message) {
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

    public default void assertElementContainsText(By by, String expectedPartialText) {
        assertElementContainsText(by, expectedPartialText, "Verificação de texto parcial do elemento");
    }

    public default void assertElementContainsText(By by, String expectedPartialText, String message) {
        try {
            WebElement element = getDriver().findElement(by);
            String actualText = element.getText();

            Assert.assertTrue(message + " - Texto esperado: '" + expectedPartialText +
                            "', Texto encontrado: '" + actualText + "'",
                    actualText.contains(expectedPartialText));

            ReportGenerator.logStatus(Status.PASS,
                    String.format("Texto parcial verificado. Contém: '%s', Texto completo: '%s'",
                            expectedPartialText, actualText));

        } catch (Exception e) {
            ReportGenerator.logStatus(Status.FAIL,
                    String.format("Falha ao verificar texto parcial do elemento: %s", e.getMessage()));
            throw new AssertionError(message + " - " + e.getMessage(), e);
        }
    }

    public default void assertElementIsDisplayed(By by) {
        assertElementIsDisplayed(by, "Elemento deve estar visível");
    }

    public default void assertElementIsDisplayed(By by, String message) {
        try {
            WebElement element = getDriver().findElement(by);
            Assert.assertTrue(message, element.isDisplayed());

            ReportGenerator.logStatus(Status.PASS, "Elemento está visível: " + by.toString());

        } catch (Exception e) {
            ReportGenerator.logStatus(Status.FAIL,
                    String.format("Elemento não está visível: %s - %s", by.toString(), e.getMessage()));
            throw new AssertionError(message, e);
        }
    }

    public default void assertElementIsEnabled(By by) {
        assertElementIsEnabled(by, "Elemento deve estar habilitado");
    }

    public default void assertElementIsEnabled(By by, String message) {
        try {
            WebElement element = getDriver().findElement(by);
            Assert.assertTrue(message, element.isEnabled());

            ReportGenerator.logStatus(Status.PASS, "Elemento está habilitado: " + by.toString());

        } catch (Exception e) {
            ReportGenerator.logStatus(Status.FAIL,
                    String.format("Elemento não está habilitado: %s - %s", by.toString(), e.getMessage()));
            throw new AssertionError(message, e);
        }
    }

    public default void assertElementIsNotDisplayed(By by) {
        assertElementIsNotDisplayed(by, "Elemento não deve estar visível");
    }

    public default void assertElementIsNotDisplayed(By by, String message) {
        try {
            WebElement element = getDriver().findElement(by);
            Assert.assertFalse(message, element.isDisplayed());

            ReportGenerator.logStatus(Status.PASS, "Elemento não está visível: " + by.toString());

        } catch (Exception e) {
            ReportGenerator.logStatus(Status.FAIL,
                    String.format("Erro ao verificar visibilidade do elemento: %s - %s", by.toString(), e.getMessage()));
            throw new AssertionError(message, e);
        }
    }

    public default void assertElementInsideElement(By parentBy, By childBy) {
        assertElementInsideElement(parentBy, childBy, "Elemento filho deve estar presente dentro do elemento pai", 10);
    }

    public default void assertElementInsideElement(By parentBy, By childBy, String message) {
        assertElementInsideElement(parentBy, childBy, message, 10);
    }

    public default void assertElementInsideElement(By parentBy, By childBy, String message, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));

            // Primeiro aguarda o elemento pai estar presente
            WebElement parentElement = wait.until(ExpectedConditions.presenceOfElementLocated(parentBy));

            // Depois verifica se o elemento filho existe dentro do pai
            WebElement childElement = wait.until(driver -> {
                try {
                    return parentElement.findElement(childBy);
                } catch (Exception e) {
                    return null;
                }
            });

            Assert.assertNotNull(message, childElement);

            ReportGenerator.logStatus(Status.PASS,
                    String.format("Elemento filho '%s' encontrado dentro do elemento pai '%s'",
                            childBy.toString(), parentBy.toString()));

        } catch (Exception e) {
            String errorMsg = String.format("Elemento filho '%s' não encontrado dentro do pai '%s' em %d segundos: %s",
                    childBy.toString(), parentBy.toString(), timeoutSeconds, e.getMessage());
            ReportGenerator.logStatus(Status.FAIL, errorMsg);
            throw new AssertionError(message + " - " + errorMsg, e);
        }
    }

    public default void assertElementInsideElementDisplayed(By parentBy, By childBy) {
        assertElementInsideElementDisplayed(parentBy, childBy, "Elemento filho deve estar visível dentro do elemento pai", 10);
    }

    public default void assertElementInsideElementDisplayed(By parentBy, By childBy, String message) {
        assertElementInsideElementDisplayed(parentBy, childBy, message, 10);
    }

    public default void assertElementInsideElementDisplayed(By parentBy, By childBy, String message, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));

            // Aguarda o elemento pai estar presente e visível
            WebElement parentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(parentBy));

            // Aguarda o elemento filho estar visível dentro do pai
            WebElement childElement = wait.until(driver -> {
                try {
                    WebElement child = parentElement.findElement(childBy);
                    return child.isDisplayed() ? child : null;
                } catch (Exception e) {
                    return null;
                }
            });

            Assert.assertNotNull(message, childElement);
            Assert.assertTrue(message, childElement.isDisplayed());

            ReportGenerator.logStatus(Status.PASS,
                    String.format("Elemento filho '%s' visível dentro do elemento pai '%s'",
                            childBy.toString(), parentBy.toString()));

        } catch (Exception e) {
            String errorMsg = String.format("Elemento filho '%s' não visível dentro do pai '%s' em %d segundos: %s",
                    childBy.toString(), parentBy.toString(), timeoutSeconds, e.getMessage());
            ReportGenerator.logStatus(Status.FAIL, errorMsg);
            throw new AssertionError(message + " - " + errorMsg, e);
        }
    }

    public default void assertElementInsideElementWithText(By parentBy, By childBy, String expectedText) {
        assertElementInsideElementWithText(parentBy, childBy, expectedText,
                "Elemento filho deve conter o texto esperado", 10);
    }

    public default void assertElementInsideElementWithText(By parentBy, By childBy, String expectedText, String message) {
        assertElementInsideElementWithText(parentBy, childBy, expectedText, message, 10);
    }

    public default void assertElementInsideElementWithText(By parentBy, By childBy, String expectedText,
                                                           String message, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));

            // Aguarda o elemento pai estar presente
            WebElement parentElement = wait.until(ExpectedConditions.presenceOfElementLocated(parentBy));

            // Aguarda o elemento filho estar presente e com o texto correto
            WebElement childElement = wait.until(driver -> {
                try {
                    WebElement child = parentElement.findElement(childBy);
                    return child.getText().equals(expectedText) ? child : null;
                } catch (Exception e) {
                    return null;
                }
            });

            Assert.assertNotNull(message, childElement);
            Assert.assertEquals(message, expectedText, childElement.getText());

            ReportGenerator.logStatus(Status.PASS,
                    String.format("Elemento filho '%s' com texto '%s' encontrado dentro do pai '%s'",
                            childBy.toString(), expectedText, parentBy.toString()));

        } catch (Exception e) {
            String errorMsg = String.format("Elemento filho '%s' não contém texto '%s' dentro do pai '%s' em %d segundos: %s",
                    childBy.toString(), expectedText, parentBy.toString(), timeoutSeconds, e.getMessage());
            ReportGenerator.logStatus(Status.FAIL, errorMsg);
            throw new AssertionError(message + " - " + errorMsg, e);
        }
    }

    public default WebElement findElementInsideElement(By parentBy, By childBy, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));

            WebElement parentElement = wait.until(ExpectedConditions.presenceOfElementLocated(parentBy));
            WebElement childElement = parentElement.findElement(childBy);

            return childElement;

        } catch (Exception e) {
            String errorMsg = String.format("Falha ao encontrar elemento '%s' dentro de '%s': %s",
                    childBy.toString(), parentBy.toString(), e.getMessage());
            ReportGenerator.logStatus(Status.FAIL, errorMsg);
            throw new RuntimeException(errorMsg, e);
        }
    }

}
