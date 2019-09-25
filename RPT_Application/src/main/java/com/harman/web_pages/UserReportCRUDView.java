package com.harman.web_pages;

import org.springframework.beans.factory.annotation.Autowired;

import com.harman.report_database.Report;
import com.harman.report_database.ReportService;
import com.harman.user_database.Employee;
import com.harman.user_database.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
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

	public UserReportCRUDView(ReportService reportService, EmployeeService employeeService) {
		super(reportService, employeeService);

		Button addButton = new Button("Add Report");
		addButton.setWidth("250px");
		addButton.setHeight("75px");
		
		addButton.addClickListener(e -> createSaveDialog(super.getGrid(), super.getEmployee()));
		add(addButton);
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

			dialog.close();
			grid.setItems(reportService.findByEmployeeUsername(employee.getUsername()));
		});

		dialog.add(datePicker, saveButton);
		dialog.open();
	}

}