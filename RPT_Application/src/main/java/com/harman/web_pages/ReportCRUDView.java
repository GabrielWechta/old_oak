package com.harman.web_pages;

import java.awt.Label;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.harman.report_database.Report;
import com.harman.report_database.ReportService;
import com.harman.report_database.Task;
import com.harman.user_database.Employee;
import com.harman.user_database.EmployeeService;
import com.harman.user_database.UserDao;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("reports")
@UIScope
public class ReportCRUDView extends VerticalLayout {

	private static final long serialVersionUID = 2179544351434704188L;
	@Autowired
	ReportService reportService;
	
	@Autowired
	EmployeeService employeeService;

	@Autowired
	public ReportCRUDView(ReportService reportService, EmployeeService employeeService) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDao) {
			Grid<Report> grid = new Grid<>(Report.class);
			
			Employee employee;
			employee = ((UserDao) principal).getUserDetails();
			grid.setItems(reportService.findByEmployeeUsername(employee.getUsername()));
			
			grid.setColumns("id", "date", "tasks");

			grid.getColumnByKey("id").setFlexGrow(0);

			grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

			Button addButton = new Button("Add Report");
			addButton.addClickListener(e -> createSaveDialog(grid, employee));
			grid.addItemDoubleClickListener(e -> createTaskDialog(grid, e, employee));

			setSizeFull();
			add(addButton, grid);

		}
		else add(new H3 ("error, you are nolonger logged"));	
	}

	private void createTaskDialog(Grid<Report> grid, ItemDoubleClickEvent<Report> e, Employee employee) {
		Dialog dialog = new Dialog();
		FormLayout formLayout = new FormLayout();
	
		dialog.setHeightFull();
		dialog.setWidth("750px");

		TextArea taskTextArea = new TextArea("Task");
		taskTextArea.setSizeFull();
		TextArea nameTextArea = new TextArea("Name");
		taskTextArea.setSizeFull();
		TextArea placementTextArea = new TextArea("Placement");
		taskTextArea.setSizeFull();
		TextArea descriptionTextArea = new TextArea("Description");
		taskTextArea.setSizeFull();
		
		ListBox<String> listBox = new ListBox<>();
		listBox.setItems("Feature", "Bug", "Refactor");
		listBox.prependComponents("Feature", new H3("WWB"));
		
		Button saveButton = new Button("Add");
		saveButton.addClickListener(a -> {
			Task task = new Task(taskTextArea.getValue(), nameTextArea.getValue(), placementTextArea.getValue(), descriptionTextArea.getValue());
			//listbox enum?
			//sprawdzanie nulli
			Report report = reportService.findById(e.getItem().getId());
			task.setReport(report);
			report.addTask(task);
			
			employee.addReport(report);
			reportService.save(report);
			//employeeService.save(employee);
			
			grid.setItems(reportService.findByEmployeeUsername(employee.getUsername()));
			dialog.close();
		});
		
		formLayout.add(taskTextArea, nameTextArea, placementTextArea, descriptionTextArea, listBox, saveButton);
		dialog.add(formLayout);
		dialog.open();
	}

	private void createSaveDialog(Grid<Report> grid, Employee employee) {
		Dialog dialog = new Dialog();

		TextField dateTextField = new TextField();
		dateTextField.setPlaceholder("Date");

		Button saveButton = new Button("Save");
		saveButton.addClickListener(a -> {
			Report report = new Report();
			report.setDate(dateTextField.getValue());
			report.setEmployee(employee);
			employee.addReport(report);
			employeeService.save(employee);
			
			dialog.close();
			grid.setItems(reportService.findByEmployeeUsername(employee.getUsername()));
		});

		dialog.add(dateTextField, saveButton);
		dialog.open();
	}

}
