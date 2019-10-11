package com.harman.web.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.harman.database.entities.Authority;
import com.harman.database.entities.AuthorityType;
import com.harman.database.entities.Employee;
import com.harman.database.entities.Report;
import com.harman.database.services.EmployeeService;
import com.harman.database.services.ReportService;
import com.harman.database.services.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

@Service
public class DialogCreatorMaster {

	private Dialog dialog; // = new Dialog(); ////?!?
	private HorizontalLayout horizontalLayout;
	private FormLayout formLayout;
	private Label label;
	

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ReportService reportService;

	@Autowired
	TaskService taskService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public void createAreYouSureRemoveEmployeeDialog(Grid<Employee> grid, Employee employee) {
		this.dialog = new Dialog();
		this.horizontalLayout = new HorizontalLayout();

		this.label = new Label("Are you sure you want to delete " + employee.getUsername() + "? It will be permament.");
		Button yesButton = new Button("Yes");
		yesButton.addClickListener((__) -> {
			if (employeeService.delete(employee)) {
				grid.setItems(employeeService.findAll());
				dialog.close();
			} else {
				Dialog notAllowedDialog = new Dialog(); /// !!!!!!
				Label label2 = new Label("You can not remove admin");
				notAllowedDialog.add(label2);
				notAllowedDialog.open();
			}

		});
		horizontalLayout.add(yesButton);

		Button noButton = new Button("No");
		noButton.addClickListener((__) -> {
			dialog.close();
		});

		horizontalLayout.add(noButton);
		dialog.add(label, horizontalLayout);
		dialog.open();
	}

	public void createEmployeeReportsDialog(Employee employee) {
		dialog = new Dialog();
		horizontalLayout = new HorizontalLayout();

		horizontalLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
		horizontalLayout.add(new Label("Id: " + employee.getId() + " | Username: " + employee.getUsername()));

		Icon exitIcon = new Icon(VaadinIcon.CLOSE);
		exitIcon.getStyle().set("cursor", "pointer");
		exitIcon.setColor("red");
		exitIcon.addClickListener((__) -> dialog.close());
		horizontalLayout.add(exitIcon);

		AdminReportCRUDView adminReportCRUDView = new AdminReportCRUDView(reportService, employeeService, employee, taskService);
		adminReportCRUDView.setSizeFull();
		dialog.add(horizontalLayout, adminReportCRUDView);
		dialog.setWidth("1500px");
		dialog.setHeight("900px");
		dialog.open();
	}

	public void createSaveEmployeeDialog(Grid<Employee> grid) {
		this.dialog = new Dialog();
		this.formLayout = new FormLayout();
		Authority auth = new Authority();
		Binder<Employee> binder = new Binder<>(Employee.class);

		TextField idTextField = new TextField();
		idTextField.setPlaceholder("id");
		binder.forField(idTextField)
				.withValidator(id -> employeeService.findById(id) == null, "In database is user with that id already.")
				.asRequired("Id can not be empty.").bind(Employee::getId, Employee::setId);

		TextField usernameTextField = new TextField();
		usernameTextField.setPlaceholder("Username");
		binder.forField(usernameTextField)
				.withValidator(username -> employeeService.findByUsername(username) == null,
						"In database is user with that username already.")
				.asRequired("Username can not be empty.").bind(Employee::getUsername, Employee::setUsername);

		TextField passwordTextField = new TextField();
		passwordTextField.setPlaceholder("Password");
		binder.forField(passwordTextField)
				.withValidator(password -> password.length() >= 4, "Password should have at least 4 characters") // standards?
				.asRequired("Password can not be empty.").bind(Employee::getPassword, Employee::setPassword);

		Select<String> select = new Select<>();
		select.setPlaceholder("Role");
		select.setItems("User", "Admin");
		binder.forField(select).asRequired("Role can not be empty").bind(Employee::getAuthority,
				Employee::setAuthority);

		select.addValueChangeListener(e -> {
			if (e.getValue().equals("User"))
				auth.setName(AuthorityType.ROLE_USER);
			else
				auth.setName(AuthorityType.ROLE_ADMIN);
		});

		Button saveButton = new Button("Save");
		saveButton.setHeight("40px");
		saveButton.setWidth("150px");
		saveButton.addClickListener(a -> {
			Employee employee = new Employee();
			try {
				binder.writeBean(employee);
				employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
				employeeService.save(employee);
				dialog.close();
				grid.setItems(employeeService.findAll());
			} catch (ValidationException e1) {
				e1.printStackTrace();
			}
		});
		formLayout.add(idTextField, usernameTextField, passwordTextField, select);
		dialog.add(formLayout, saveButton);
		dialog.open();

	}

	public void createEditEmployeeDialog(Grid<Employee> grid, Employee employee) {
		this.dialog = new Dialog();
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
			if (e.getValue().equals("User")) {
				auth.setName(AuthorityType.ROLE_USER);
			} else {
				auth.setName(AuthorityType.ROLE_ADMIN);
			}
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
				employee.setAuthority(auth.getName().toString());

				employeeService.save(employee);
				dialog.close();
				grid.setItems(employeeService.findAll());
			}
		});

		dialog.add(idTextField, usernameTextField, passwordTextField, select, saveButton);
		dialog.open();
	}

	public void createSaveReportDialog(Grid<Report> grid, Employee employee) {
		Dialog dialog = new Dialog();

		DatePicker datePicker = new DatePicker();
		datePicker.setPlaceholder("Choose date");

		Button saveButton = new Button("Save");
		saveButton.addClickListener(a -> {
			Report report = new Report();
			report.setDate(datePicker.getValue());
			report.setEmployee(employee);
			employee.addReport(report);
			reportService.save(report);

			dialog.close();
			grid.setItems(reportService.findByEmployeeUsername(employee.getUsername()));
		});

		dialog.add(datePicker, saveButton);
		dialog.open();
	}
}

