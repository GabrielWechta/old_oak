package com.harman.utils;

import com.vaadin.flow.component.button.Button;
//JESZCZE NIE DZIALA
public class LogoutButton extends Button {
	public LogoutButton() {
		super("Logout");
		addClickListener(e -> {
			getUI().ifPresent(ui -> ui.navigate("logout"));
			});
	}

}
