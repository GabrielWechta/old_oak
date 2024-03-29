package com.harman;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.harman.web.service.CustomRequestCache;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private static final long serialVersionUID = 4455204321765926669L;
	public static final String ROUTE = "login";
	private LoginOverlay login = new LoginOverlay();

	@Autowired
	public LoginView(AuthenticationManager authenticationManager, CustomRequestCache requestCache) {
		login.setAction("login");
		login.setOpened(true);
		login.setTitle("RPT Application");
		login.setDescription("Welcome to Harman RPT");
		login.setForgotPasswordButtonVisible(false);

		getElement().appendChild(login.getElement());

		login.addLoginListener(e -> {
			try {
				// try to authenticate with given credentials, should always return not null or
				// throw an {@link AuthenticationException}
				final Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword()));

				// if authentication was successful we will update the security context and
				// redirect to the page requested first
				SecurityContextHolder.getContext().setAuthentication(authentication);
				login.close();
				UI.getCurrent().navigate(requestCache.resolveRedirectUrl());

			} catch (AuthenticationException ex) {
				login.setError(true);
			}
		});
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) { //
		if (!event.getLocation().getQueryParameters().getParameters().getOrDefault("error", Collections.emptyList())
				.isEmpty()) {
			login.setError(true); //
		}
	}
}