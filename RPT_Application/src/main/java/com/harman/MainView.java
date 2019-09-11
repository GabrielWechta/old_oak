package com.harman;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route
public class MainView extends VerticalLayout implements LocaleChangeObserver {

    private RouterLink link, link_2;

    public MainView(@Autowired Greeter greeter) {//, @Autowired ExampleTemplate template) {
    	
    	
        H1 secondheading = new H1("Just checking");
        
        Label greeting = new Label(greeter.sayHello());
        Style grretingStyle = greeting.getElement().getStyle();
        grretingStyle.set("display", "block");
        grretingStyle.set("margin-bottom", "10px");

        link = new RouterLink("to view",ViewComponent.class);
        link_2 = new RouterLink("to nice feeling", NiceFeelingComponent.class);
       // link_3 = new RouterLink("h2-console", );
        
        Style linkStyle = link.getElement().getStyle();
        linkStyle.set("display", "block");
        linkStyle.set("margin-bottom", "10px");

        add(secondheading, greeting, link, link_2);// template); 
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        link.setText("to view");
    }

}
