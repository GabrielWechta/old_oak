package org.vaadin.spring.tutorial;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("view/nicefeeling")
@UIScope
public class NiceFeelingComponent extends Div{
	
	public NiceFeelingComponent() {
		setText("How are you feeling?");
	}


}
