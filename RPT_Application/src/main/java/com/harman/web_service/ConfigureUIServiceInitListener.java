package com.harman.web_service;

import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

//NIE WIEM CZY TA KLASA JEST DO CZEGOKOLWIEK UZYWANA, SPRAWDZALEM JAK PROGRAM DZIALA Z NIA I BEZ NIEJ I NIE WIEDZE ROZNIC
@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener { //

	private static final long serialVersionUID = -2093071242594955458L;

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
		});
	}
}
