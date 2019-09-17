package com.harman.web_pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.harman.database.Employee;
import com.harman.database.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("users")
@UIScope
public class EmployeesCRUDView extends VerticalLayout {

	private static final long serialVersionUID = 2435396446114365838L;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public EmployeesCRUDView(EmployeeService employeeService) {

		Grid<Employee> grid = new Grid<>(Employee.class);
		grid.setItems(employeeService.findAll());
		grid.setColumns("id", "username", "password");

		grid.getColumnByKey("id").setFlexGrow(0);
		grid.addComponentColumn(item -> createRemoveButton(grid, item)).setHeader("").setFlexGrow(0);

		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

		Button addButton = new Button("Add Employee");
		addButton.addClickListener(e -> createSaveDialog(grid));

		setSizeFull();
		add(addButton, grid);
	}

	private Button createRemoveButton(Grid<Employee> grid, Employee item) {
		Button button = new Button("Remove", clickEvent -> {
			employeeService.delete(item);
			grid.setItems(employeeService.findAll());
		});
		return button;
	}

	private void createSaveDialog(Grid<Employee> grid) {
		Dialog dialog = new Dialog();

		TextField usernameTextField = new TextField();
		usernameTextField.setPlaceholder("Username");

		TextField passwordTextField = new TextField();
		passwordTextField.setPlaceholder("Password");

		Button saveButton = new Button("Save");
		saveButton.addClickListener(a -> {
			employeeService.save(new Employee(usernameTextField.getValue(),
					bCryptPasswordEncoder.encode(passwordTextField.getValue())));
			dialog.close();
			grid.setItems(employeeService.findAll());
		});

		dialog.add(usernameTextField, passwordTextField, saveButton);
		dialog.open();
	}
}
