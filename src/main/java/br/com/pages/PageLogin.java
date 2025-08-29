package br.com.pages;

import br.com.attributes.AttributesLogin;
import br.com.core.Utils;

public class PageLogin extends AttributesLogin implements Utils{

	public void login() {
		navigateTo("https://www.saucedemo.com/v1/index.html");
		setText(user(),"standard_user");
		setText(password(),"secret_sauce");
		click(entrar());
	}

}
