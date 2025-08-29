package br.com.attributes;

import org.openqa.selenium.By;

public class AttributesLogout {
    protected By menuHamburguer(){
        return By.xpath("//div[@class='bm-burger-button']");
    }

    protected By logout() {
        return By.xpath("//a[@id='logout_sidebar_link']");

    }
}
