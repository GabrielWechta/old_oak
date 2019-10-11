package com.harman.web.pages;

import org.springframework.beans.factory.annotation.Autowired;

import com.harman.database.entities.Employee;
import com.harman.database.entities.Report;
import com.harman.database.services.EmployeeService;
import com.harman.database.services.ReportService;
import com.harman.database.services.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;

public class AdminReportCRUDView extends DefaultReportCRUDView {

	private static final long serialVersionUID = -4744817894242273882L;
	
	private H3 wwbH3;
	
	public AdminReportCRUDView(final ReportService reportService, EmployeeService employeeService, Employee employee, TaskService taskService) {
		super(reportService, employeeService);
		Grid<Report> grid = super.getGrid();

		grid.setItems(reportService.findByEmployeeUsername(employee.getUsername()));
		grid.addItemDoubleClickListener(e -> taskDialog(grid, e.getItem(), super.getEmployee(), taskService));

		GridContextMenu<Report> contextMenu = new GridContextMenu<>(super.getGrid());
		contextMenu.addItem("Remove", e -> {
			e.getItem().ifPresent(a -> {
				createAreYouSureDialog(reportService, super.getGrid(), a, employee);
			});
		});
	}

	private void taskDialog(Grid<Report> grid, Report report, Employee employee, TaskService taskService) {
		Dialog dialog = new Dialog();
		HorizontalLayout layout = new HorizontalLayout();
		FormLayout buttomLayout = new FormLayout();

		layout.setJustifyContentMode(JustifyContentMode.BETWEEN);
		layout.add(new Label("Month: " + report.getDate()));
		Icon exitIcon = new Icon(VaadinIcon.CLOSE);
		exitIcon.getStyle().set("cursor", "pointer");
		exitIcon.setColor("red");
		exitIcon.addClickListener((__) -> dialog.close());
		layout.add(exitIcon);

		wwbH3 = new H3();
		wwbH3.setText("WWB: " + report.getWwb());
		buttomLayout.add(wwbH3);

		TaskCRUDView taskCRUDView = new TaskCRUDView(taskService, report);
		dialog.add(layout, taskCRUDView, buttomLayout);// , addTaskButton, wwbTextArea);
		taskCRUDView.setHeight("500px");
		dialog.setHeight("600px");
		dialog.setWidth("1000px");
		dialog.open();
	}

	private void createAreYouSureDialog(ReportService reportService, Grid<Report> grid, Report report,
			Employee employee) {
		Dialog dialog = new Dialog();
		HorizontalLayout layout = new HorizontalLayout();

		Label label = new Label(
				"Are you sure you want to delete report from " + report.getDate() + "? It will be permament.");
		Button yesButton = new Button("Yes");
		yesButton.addClickListener((__) -> {
			reportService.delete(report);
			grid.setItems(reportService.findByEmployeeUsername(employee.getUsername()));
			dialog.close();
		});
		layout.add(yesButton);

		Button noButton = new Button("No");
		noButton.addClickListener((__) -> {
			dialog.close();
		});

		layout.add(noButton);
		layout.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
		dialog.add(label, layout);
		dialog.open();
	}

}
