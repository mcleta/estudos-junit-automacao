package br.com.cenarios;

import br.com.core.DriverFactory;
import br.com.core.ReportGenerator;
import br.com.pages.PageLogin;
import br.com.pages.PageFCompras;
import com.aventstack.extentreports.ExtentReports;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CN03_FluxoDeCompra {
    private static final Log log = LogFactory.getLog(CN03_FluxoDeCompra.class);
    private static ExtentReports extent;

    @BeforeClass
    public static void setUp() {
        extent = new ExtentReports();
        ReportGenerator.createReport("CN03 - Fluxo de compras");
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
    public void addItensNoCarrinho(){
        ReportGenerator.startTest("CT03 - Adicionar itens ao carrinho");

        PageLogin pageLogin = new PageLogin();
        PageFCompras pageFCompras = new PageFCompras();

        System.out.println("CT03 Started");
        pageLogin.login();
        pageFCompras.addItens();
//        pageFCompras.entrarCarrinhoDeCompras();
//        pageFCompras.carrinhoCheckout();
    }
}
