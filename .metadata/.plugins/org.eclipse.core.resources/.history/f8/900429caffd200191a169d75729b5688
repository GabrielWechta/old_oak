package org.vaadin.spring.tutorial;

import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("login")
@UIScope
public class LoginComponent extends VerticalLayout{
	
	private String user = new String("gabriel");
	private String password = new String("1gabi2");
	
	public LoginComponent() {
		TextField userField = new TextField("User");
		
		PasswordField passwordField = new PasswordField();
		passwordField.setLabel("Password");
		
		Button sumbitbutton = new Button();
		sumbitbutton.setText("Login");
		sumbitbutton.addClickListener(e -> {
			System.out.println(userField.getValue());
			System.out.println(passwordField.getValue());
			
			if(userField.getValue().equals(user) && passwordField.getValue().equals(password)) {
				//getUI().ifPresent(ui -> ui.navigate("view/nicefeeling"));
				getUI().ifPresent(ui -> ui.navigate(NiceFeelingComponent.class));
			}
			else {
				System.out.println("login failure");
				Dialog wrongPassword = new Dialog();
				wrongPassword.add(new Label("incorrect password or username"));
				wrongPassword.setVisible(true);
				wrongPassword.open();
			}
		});
	
		//passwordField.set	
		
		add(userField, passwordField, sumbitbutton);
		
	}

}
