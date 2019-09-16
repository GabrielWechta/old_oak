package com.harman.web_pages;

import com.harman.database.Employee;
import com.harman.database.EmployeeService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("users")
@UIScope
public class EmployeesCRUDView extends VerticalLayout {

	private EmployeeService employeeService = EmployeeService.getInstance(); 
    private Grid<Employee> grid = new Grid<>(Employee.class);
    
	public EmployeesCRUDView() {
        updateList();
        grid.addColumn(Employee::getUsername).setHeader("Name");
        grid.addColumn(Employee::getPassword).setHeader("Passy");
        add(grid);
        setSizeFull();
	}
	
    public void updateList() {
        grid.setItems(employeeService.findAll());
    }
}
