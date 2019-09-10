package com.harman;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("view")
@UIScope
public class ViewComponent extends VerticalLayout {

    public ViewComponent(@Autowired Greeter greeter) {
        Label trueLabelField = new Label(greeter.sayHello());
        TextField labelField = new TextField();
        labelField.setLabel("Label");

        TextField placeholderField = new TextField();
        placeholderField.setPlaceholder("Placeholder");

        TextField valueField = new TextField();
        valueField.setValue("Value");
        valueField.setClearButtonVisible(true);
        
        Image twinPeaksImage = new Image("https://pmcvariety.files.wordpress.com/2014/10/twin-peaks-returning-to-tv.jpg?w=1000&h=563&crop=1", "Cooper's Dream");
        
        add(trueLabelField, labelField, placeholderField, valueField, twinPeaksImage);
        
        String name = labelField.toString();
        System.out.println(name);
        
    }
}
