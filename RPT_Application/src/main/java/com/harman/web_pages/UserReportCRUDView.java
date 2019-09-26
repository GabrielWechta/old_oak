package com.harman.web_pages;

import org.springframework.beans.factory.annotation.Autowired;

import com.harman.report_database.Report;
import com.harman.report_database.ReportService;
import com.harman.report_database.Task;
import com.harman.report_database.TaskService;
import com.harman.user_database.Employee;
import com.harman.user_database.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("my_reports")
@UIScope
public class UserReportCRUDView extends DefaultReportCRUDView {

	private static final long serialVersionUID = 5606092593469344695L;

	@Autowired
	ReportService reportService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	TaskService taskService;

	public UserReportCRUDView(ReportService reportService, EmployeeService employeeService) {
		super(reportService, employeeService);

		super.getGrid().addItemDoubleClickListener(e -> taskDialog(super.getGrid(), e, super.getEmployee()));

		Button addButton = new Button("Add Report");
		addButton.setWidth("250px");
		addButton.setHeight("75px");

		addButton.addClickListener(e -> createSaveDialog(super.getGrid(), super.getEmployee()));
		add(addButton);
	}

	private void taskDialog(Grid<Report> grid, ItemDoubleClickEvent<Report> e, Employee employee) {
		Dialog dialog = new Dialog();
		HorizontalLayout layout = new HorizontalLayout();
		layout.setJustifyContentMode(JustifyContentMode.BETWEEN);
		layout.add(new Label("Month: " + e.getItem().getDate()));
		Icon exitIcon = new Icon(VaadinIcon.CLOSE);
		exitIcon.getStyle().set("cursor", "pointer");
		exitIcon.setColor("red");
		exitIcon.addClickListener((__) -> dialog.close());
		layout.add(exitIcon);
		
		Button addTaskButton = new Button("Add Task");
		addTaskButton.setHeight("75px");
		addTaskButton.setWidth("250px");
		
		addTaskButton.addClickListener(event -> createTask(TaskCRUDView.getGrid(), e.getItem(), employee));
		TextArea wwbTextArea = new TextArea();
		wwbTextArea.setValue("WWB: " + e.getItem().getWwb());
		TaskCRUDView taskCRUDView = new TaskCRUDView(taskService, e.getItem());
		dialog.add(layout, taskCRUDView, addTaskButton, wwbTextArea);
		taskCRUDView.setHeight("500px");
		dialog.setHeight("600px");
		dialog.setWidth("1000px");
		dialog.open();
	}

	private void createSaveDialog(Grid<Report> grid, Employee employee) {
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
			// employeeService.save(employee);

			dialog.close();
			grid.setItems(reportService.findByEmployeeUsername(employee.getUsername()));
		});

		dialog.add(datePicker, saveButton);
		dialog.open();
	}

	private void createTask(Grid<Task> grid, Report report, Employee employee) {
		Dialog dialog = new Dialog();
		FormLayout formLayout = new FormLayout();

		dialog.setHeightFull();
		dialog.setWidth("750px");

		TextArea typeTextArea = new TextArea("Type");
		typeTextArea.setSizeFull();
		TextArea nameTextArea = new TextArea("Name");
		nameTextArea.setSizeFull();
		TextArea placementTextArea = new TextArea("Placement");
		placementTextArea.setSizeFull();
		TextArea descriptionTextArea = new TextArea("Description");
		descriptionTextArea.setSizeFull();

		ListBox<String> listBox = new ListBox<>();
		listBox.setItems("Feature", "Bug", "Refactor");
		listBox.prependComponents("Feature", new H3("WWB"));

		Button saveButton = new Button("Save");
		saveButton.addClickListener(a -> {
			if ((typeTextArea.getValue() == "") || (nameTextArea.getValue() == "")
					|| (placementTextArea.getValue() == "") || (descriptionTextArea.getValue() == "")
					|| (listBox.getValue() == null)) {
				Dialog secDialog = new Dialog();
				Label secUserLabel = new Label("All fields must not be empty");
				secDialog.add(secUserLabel);
				secDialog.open();
			} else {
				Task task = new Task(typeTextArea.getValue(), nameTextArea.getValue(), placementTextArea.getValue(),
						descriptionTextArea.getValue(), listBox.getValue());

				task.setReport(report);
				report.addTask(task);

				taskService.save(task);

				grid.setItems(taskService.findByReport(report));
				super.getGrid().setItems(reportService.findByEmployeeUsername(employee.getUsername()));
				
				dialog.close();
			}
		});

		formLayout.add(typeTextArea, nameTextArea, placementTextArea, descriptionTextArea, listBox, saveButton);
		dialog.add(formLayout);
		dialog.open();
	}

}