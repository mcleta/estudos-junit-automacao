package br.com.pages;

import br.com.attributes.AttributesFCompra;
import br.com.core.Utils;
import org.openqa.selenium.By;

public class PageFCompras extends AttributesFCompra implements Utils{

    public void addItens(){
        click(linkDoItem());
        click(btnAddCarrinho());
        assertElementText(
                By.xpath("//button[@class='btn_secondary btn_inventory']"),
                "REMOVE",
                "Verificação de mudança do texto do botão para REMOVE"
                );
    }

    public void entrarCarrinhoDeCompras(){

    }

    public void carrinhoCheckout(){
    }

}
