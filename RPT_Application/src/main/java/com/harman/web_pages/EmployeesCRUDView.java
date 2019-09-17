package com.harman.web_pages;

import org.springframework.beans.factory.annotation.Autowired;

import com.harman.database.Employee;
import com.harman.database.EmployeeService;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("users")
@UIScope
public class EmployeesCRUDView extends VerticalLayout {

	private EmployeeService employeeService; 
    //private Grid<Employee> grid = new Grid<>();
	
	Crud<Employee> crud = new Crud<>(Employee.class, createPersonEditor());
	@Autowired
	private EmployeeService dataProvider;

	crud.setDataProvider(dataProvider);
	crud.addSaveListener(e -> dataProvider.persist(e.getItem()));
	crud.addDeleteListener(e -> dataProvider.delete(e.getItem()));

	crud.getGrid().removeColumnByKey("id");
	crud.addThemeVariants(CrudVariant.NO_BORDER);
    
	private CrudEditor<Employee> createPersonEditor() {
	    TextField firstName = new TextField("First name");
	    TextField password = new TextField("password");
	    FormLayout form = new FormLayout(firstName, password);

	    Binder<Employee> binder = new Binder<>(Employee.class);
	    binder.bind(firstName, Employee::getUsername, Employee::setUsername);
	    binder.bind(password, Employee::getPassword, Employee::setPassword);

	    return new BinderCrudEditor<>(binder, form);
	}
}
 