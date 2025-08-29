package br.com.attributes;
import org.openqa.selenium.By;

public class AttributesFCompra{
    protected By linkDoItem(){
        return By.xpath("//a[@id='item_4_title_link']");
    }
    protected By btnAddCarrinho(){
        return By.xpath("//button[@class='btn_primary btn_inventory']");
    }
    protected By btnRemoverCarrinho(){
        return By.xpath("//button[@class='btn_secondary btn_inventory']");
    }
    protected By btnAddPraRemoverCarrinho(){
        return By.xpath("//button[@class='btn_secondary btn_inventory']");
    }
    protected By iconCarrinho(){
        return By.xpath("//div[@id='shopping_cart_container']");
    }
    protected By iconCarrinhoNumProdutos(){
        return By.xpath("span[@class='fa-layers-counter shopping_cart_badge']");
    };
}
