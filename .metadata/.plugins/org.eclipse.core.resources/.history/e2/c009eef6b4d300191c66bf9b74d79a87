package com.harman;

import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;

public class CustomRequestCache extends HttpSessionRequestCache{
	public String resolveRedirectUrl() {
	    SavedRequest savedRequest = getRequest(VaadinServletRequest.getCurrent().getHttpServletRequest(), VaadinServletResponse.getCurrent().getHttpServletResponse());
	    if(savedRequest instanceof DefaultSavedRequest) {
	        final String requestURI = ((DefaultSavedRequest) savedRequest).getRequestURI(); // 
	        // check for valid URI and prevent redirecting to the login view
	        if (requestURI != null && !requestURI.isEmpty() && !requestURI.contains(LoginView.ROUTE)) { // 
	            return requestURI.startsWith("/") ? requestURI.substring(1) : requestURI; // 
	        }
	    }

	    // if everything fails, redirect to the main view
	    return "";
	}
}
