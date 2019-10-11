package com.harman.utils;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.spring.annotation.UIScope;

//JESZCZE NIE DZIALA
@UIScope
public class LogoutButton extends Button {

	private static final long serialVersionUID = -8771798917543377255L;

	public LogoutButton() {
		super("Logout");
		addClickListener(e -> {
			UI.getCurrent().navigate("logoutTemp");
		});
	}

}
