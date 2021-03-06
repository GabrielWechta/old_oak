package com.harman.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.harman.utils.Greeter;
import com.harman.web_service.ExampleTemplate.ExampleModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Simple template example.
 */
@SuppressWarnings("serial")
@Tag("example-template")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExampleTemplate extends PolymerTemplate<ExampleModel> {

    /**
     * Template model which defines the single "name" property.
     */
    public static interface ExampleModel extends TemplateModel {

        void setMessage(String message);
    }

    public ExampleTemplate(@Autowired Greeter bean) {
        // Set the initial value to the "message" property.
        getModel().setMessage(bean.sayHello());
    }
    
}
