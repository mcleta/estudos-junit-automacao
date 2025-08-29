package br.com.pages;

import br.com.pages.PageLogin;
import br.com.attributes.AttributesLogout;
import br.com.core.Utils;

public class PageLogout extends AttributesLogout implements Utils {

    public void sairDoApp(){
        PageLogin pageLogin = new PageLogin();
        pageLogin.login();
        click(menuHamburguer());
        click(logout());
    }

}
