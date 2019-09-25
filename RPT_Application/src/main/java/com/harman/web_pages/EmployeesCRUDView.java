package com.harman.web_pages;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.harman.report_database.ReportService;
import com.harman.user_database.Authority;
import com.harman.user_database.AuthorityType;
import com.harman.user_database.Employee;
import com.harman.user_database.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
	private ReportService reportService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public EmployeesCRUDView(EmployeeService employeeService) {

		Grid<Employee> grid = new Grid<>(Employee.class);
		grid.setItems(employeeService.findAll());
		grid.setColumns("id", "username", "authority");

		grid.getColumnByKey("id").setFlexGrow(0);

		GridContextMenu<Employee> contextMenu = new GridContextMenu<>(grid);

		contextMenu.addItem("Remove", e -> {
			e.getItem().ifPresent(a -> {
				createAreYouSureDialog(grid, a);
			});
		});

		contextMenu.addItem("Edit", e -> {
			e.getItem().ifPresent(a -> {
				createEditDialog(grid, a);
			});
		});

		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

		Button addButton = new Button("Add Employee");
		addButton.addClickListener(e -> createSaveDialog(grid));
		
		grid.addItemDoubleClickListener(e -> {
			createEmployeeReportsDialog(e.getItem());
		});
		
		setSizeFull();
		add(addButton, grid);
	}

	private void createEmployeeReportsDialog(Employee employee) {
		Dialog dialog = new Dialog();
		AdminReportCRUDView adminReportCRUDView = new AdminReportCRUDView(reportService, employeeService, employee);
		adminReportCRUDView.setSizeFull();
		dialog.add(adminReportCRUDView);
		dialog.setWidth("1500px");
		dialog.setHeight("900px");
		dialog.open();
	}

	private void createAreYouSureDialog(Grid<Employee> grid, Employee employee) {
		Dialog dialog = new Dialog();
		HorizontalLayout layout = new HorizontalLayout();

		Label label = new Label(
				"Are you sure you want to delete " + employee.getUsername() + "? It will be permament.");
		Button yesButton = new Button("Yes");
		yesButton.addClickListener((__) -> {
			if (employeeService.delete(employee)) {
				grid.setItems(employeeService.findAll());
				dialog.close();
			} else {
				Dialog notAllowedDialog = new Dialog();
				Label label2 = new Label("You can not remove admin");
				notAllowedDialog.add(label2);
				notAllowedDialog.open();
			}

		});
		layout.add(yesButton);

		Button noButton = new Button("Missclick, sorry");
		noButton.addClickListener((__) -> {
			dialog.close();
		});

		layout.add(noButton);
		layout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
		dialog.add(label, layout);
		dialog.open();
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
				Label secUserLabel = new Label("In database is user with '" + idTextField.getValue() + "' id already");
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

	private void createEditDialog(Grid<Employee> grid, Employee employee) {
		Dialog dialog = new Dialog();
		Authority auth = new Authority();

		TextField idTextField = new TextField();
		idTextField.setValue(employee.getId());
		idTextField.setEnabled(false);

		TextField usernameTextField = new TextField();
		usernameTextField.setValue(employee.getUsername());
		usernameTextField.setEnabled(false);

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
			if ((passwordTextField.getValue() == null) || (select.getValue() == null)) {
				Dialog secDialog = new Dialog();
				Label secUserLabel = new Label("All fields must not be empty");
				secDialog.add(secUserLabel);
				secDialog.open();
			} else {
				employee.setPassword(bCryptPasswordEncoder.encode(passwordTextField.getValue()));
				employee.setAuthority(auth.getName());

				employeeService.save(employee);
				dialog.close();
				grid.setItems(employeeService.findAll());
			}
		});

		dialog.add(idTextField, usernameTextField, passwordTextField, select, saveButton);
		dialog.open();
	}
}
