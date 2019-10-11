package com.harman.web.service;

import org.springframework.stereotype.Component;

import com.harman.LoginView;
import com.harman.web.security.SecurityUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener { //

	private static final long serialVersionUID = -2093071242594955458L;

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::beforeEnter);
		});
	}

	private void beforeEnter(BeforeEnterEvent event) {
		if (!SecurityUtils.isAccessGranted(event.getNavigationTarget())) {
			if (SecurityUtils.isUserLoggedIn()) {
				event.rerouteToError(NotFoundException.class);
			} else {
				event.rerouteTo(LoginView.class);
			}
		}
	}
}
