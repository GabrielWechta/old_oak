package com.harman.web_pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.harman.utils.LogoutButton;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = -1757630587061487264L;
	private RouterLink link_2;

	@Autowired
	public MainView() {
		String userPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		H1 secondheading = new H1("RPT Project Main View");

		Label greeting = new Label("Hello " + userPrincipalName);
		Style grretingStyle = greeting.getElement().getStyle();
		grretingStyle.set("display", "block");
		grretingStyle.set("margin-bottom", "10px");

		link_2 = new RouterLink("users", EmployeesCRUDView.class);
		

		add(secondheading, greeting, new LogoutButton(), link_2);
	}
}
