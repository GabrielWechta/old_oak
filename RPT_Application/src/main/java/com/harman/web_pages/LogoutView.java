package com.harman.web_pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("logoutTemp")
@UIScope
public class LogoutView extends VerticalLayout {

	private static final long serialVersionUID = 8540470468600591090L;

	public LogoutView() {
		Notification notification = new Notification("You have been logout", 3000, Position.TOP_START);
		notification.open();
		UI.getCurrent().navigate("logout");

	}
}
