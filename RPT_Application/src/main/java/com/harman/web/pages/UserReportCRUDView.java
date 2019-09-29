package com.harman.web.pages;

import org.springframework.beans.factory.annotation.Autowired;

import com.harman.database.entities.Employee;
import com.harman.database.entities.Report;
import com.harman.database.entities.Task;
import com.harman.database.entities.WwbType;
import com.harman.database.services.EmployeeService;
import com.harman.database.services.ReportService;
import com.harman.database.services.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
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

	private H3 wwbH3;

	public UserReportCRUDView(ReportService reportService, EmployeeService employeeService,
			DialogCreatorMaster dialogCreatorMaster) {
		super(reportService, employeeService);

		super.getGrid().addItemDoubleClickListener(e -> taskDialog(super.getGrid(), e, super.getEmployee()));

		Button addButton = new Button("Add Report");
		addButton.setWidth("250px");
		addButton.setHeight("75px");

		addButton.addClickListener(
				e -> dialogCreatorMaster.createSaveReportDialog(super.getGrid(), super.getEmployee()));
		add(addButton);
	}

	private void taskDialog(Grid<Report> grid, ItemDoubleClickEvent<Report> e, Employee employee) {
		Dialog dialog = new Dialog();
		HorizontalLayout layout = new HorizontalLayout();
		FormLayout buttomLayout = new FormLayout();

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
		TaskCRUDView taskCRUDView = new TaskCRUDView(taskService, e.getItem());
		addTaskButton.addClickListener(event -> {
			createTask(e.getItem(), employee, taskCRUDView);
		});

		wwbH3 = new H3();
		wwbH3.setText("WWB: " + e.getItem().getWwb());
		buttomLayout.add(addTaskButton, wwbH3);

		
		dialog.add(layout, taskCRUDView, buttomLayout);// , addTaskButton, wwbTextArea);
		taskCRUDView.setHeight("500px");
		dialog.setHeight("600px");
		dialog.setWidth("1000px");
		dialog.open();
	}

	private void createTask(Report report, Employee employee, TaskCRUDView taskCRUDView) {
		Dialog dialog = new Dialog();
		FormLayout formLayout = new FormLayout();
		Binder<Task> binder = new Binder<>(Task.class);
		//Grid<Task> grid = new Grid<>(Task.class);

		dialog.setHeightFull();
		dialog.setWidth("750px");

		TextArea typeTextArea = new TextArea("Type");
		typeTextArea.setSizeFull();
		binder.forField(typeTextArea).asRequired("This field can not be empty").bind(Task::getType, Task::setType);

		TextArea nameTextArea = new TextArea("Name");
		nameTextArea.setSizeFull();
		binder.forField(nameTextArea).asRequired("This field can not be empty").bind(Task::getName, Task::setName);

		TextArea placementTextArea = new TextArea("Placement");
		placementTextArea.setSizeFull();
		binder.forField(placementTextArea).asRequired("This field can not be empty").bind(Task::getPlacement,
				Task::setPlacement);

		TextArea descriptionTextArea = new TextArea("Description");
		descriptionTextArea.setSizeFull();
		binder.forField(descriptionTextArea).asRequired("This field can not be empty").bind(Task::getDescription,
				Task::setDescription);

		Select<WwbType> select = new Select<>();
		select.setLabel("WWB");
		select.setItems(WwbType.FEAUTURE, WwbType.BUG, WwbType.REFACTOR);
		binder.forField(select).asRequired("WWB can not be empty").bind(Task::getWwbType, Task::setWwbType);

		Button saveButton = new Button("Save");
		saveButton.addClickListener(a -> {
			Task task = new Task();
			try {
				binder.writeBean(task);
				task.setReport(report);
				report.addTask(task); 

				taskService.save(task);
				
				taskCRUDView.getGrid().setItems(taskService.findByReport(report));
				super.getGrid().setItems(reportService.findByEmployeeUsername(employee.getUsername()));
				getWwbH3().setText("WWB: " + report.getWwb());
				dialog.close();
			} catch (ValidationException e1) {
				e1.printStackTrace();
			}
		});

		formLayout.add(typeTextArea, nameTextArea, placementTextArea, descriptionTextArea, select, saveButton);
		dialog.add(formLayout);
		dialog.open();
	}

	public H3 getWwbH3() {
		return wwbH3;
	}

	public void setWwbH3(H3 wwbH3) {
		this.wwbH3 = wwbH3;
	}

}