package br.com.pages;

import br.com.attributes.AttributesFCompra;
import br.com.core.Utils;
import org.openqa.selenium.By;

public class PageFCompras extends AttributesFCompra implements Utils{

    public void addItens(){
        click(linkDoItem());
        click(btnAddCarrinho());
        assertElementText(
                btnAddPraRemoverCarrinho(),
                "REMOVE",
                "Verificação de mudança do texto do botão para REMOVE"
                );
        assertElementInsideElementDisplayed(
                By.cssSelector("div#shopping_cart_container"),
                By.cssSelector("a>span.fa-layers-counter"),
                "Verificação de visibilidade do contador no icone de carrinho",
                10
        );
    }

    public void entrarCarrinhoDeCompras(){
        click(iconCarrinho());
    }

    public void carrinhoCheckout(){
    }

}
