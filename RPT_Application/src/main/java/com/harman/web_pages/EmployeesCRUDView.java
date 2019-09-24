package com.harman.web_pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.harman.user_database.Authority;
import com.harman.user_database.AuthorityType;
import com.harman.user_database.Employee;
import com.harman.user_database.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
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
		grid.setColumns("id", "username", /* "password", */"reports", "authority");

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
		Authority auth = new Authority();

		TextField idTextField = new TextField();
		idTextField.setPlaceholder("id");

		TextField usernameTextField = new TextField();
		usernameTextField.setPlaceholder("Username");

		TextField passwordTextField = new TextField();
		passwordTextField.setPlaceholder("Password");

		Select<String> select = new Select<>();
		select.setPlaceholder("Role");
		select.setItems("User", "Admin");

		select.addValueChangeListener(e -> {
			if (e.getValue().equals("User"))
				auth.setName(AuthorityType.ROLE_USER);
			else
				auth.setName(AuthorityType.ROLE_ADMIN);
		});

		Button saveButton = new Button("Save");
		saveButton.addClickListener(a -> {
			if (employeeService.findByUsername(usernameTextField.getValue()) != null) {
				Dialog secDialog = new Dialog();
				Label secUserLabel = new Label(
						"In database is user with '" + usernameTextField.getValue() + "' username already");
				secDialog.add(secUserLabel);
				secDialog.open();
			} else if (employeeService.findById(idTextField.getValue()) != null) {
				Dialog secDialog = new Dialog();
				Label secUserLabel = new Label(
						"In database is user with '" + idTextField.getValue() + "' id already");
				secDialog.add(secUserLabel);
				secDialog.open();
			} else if ((passwordTextField.getValue() == null) || (select.getValue() == null)
					|| (usernameTextField.getValue() == null)) {
				Dialog secDialog = new Dialog();
				Label secUserLabel = new Label("All fields must not be empty");
				secDialog.add(secUserLabel);
				secDialog.open();
			} else {
				employeeService.save(new Employee(idTextField.getValue(), usernameTextField.getValue(),
						bCryptPasswordEncoder.encode(passwordTextField.getValue()), auth));
				dialog.close();
				grid.setItems(employeeService.findAll());
			}
		});

		dialog.add(idTextField, usernameTextField, passwordTextField, select, saveButton);
		dialog.open();
	}
}
