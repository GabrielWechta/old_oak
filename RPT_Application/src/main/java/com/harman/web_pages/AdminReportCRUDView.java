package com.harman.web_pages;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.harman.report_database.Report;
import com.harman.report_database.ReportService;
import com.harman.user_database.Employee;
import com.harman.user_database.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class AdminReportCRUDView extends DefaultReportCRUDView {

	private static final long serialVersionUID = -4744817894242273882L;
	private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());
//	@Autowired
//	ReportService reportService;

	@Autowired
	public AdminReportCRUDView(final ReportService reportService, EmployeeService employeeService, Employee employee) {
		super(reportService, employeeService);
		//Grid<Report> grid = super.getGrid();
		
		super.getGrid().setItems(reportService.findByEmployeeUsername(employee.getUsername()));

		GridContextMenu<Report> contextMenu = new GridContextMenu<>(super.getGrid());
		contextMenu.addItem("Remove", e -> {
			e.getItem().ifPresent(a -> {
				reportService.delete(a);
				super.getGrid().setItems(reportService.findByEmployeeUsername(employee.getUsername()));
				//createAreYouSureDialog(reportService, grid, a, employee);
			});
		});
	}

	private void createAreYouSureDialog(ReportService reportService, Grid<Report> grid, Report report, Employee employee) {
		Dialog dialog = new Dialog();
		HorizontalLayout layout = new HorizontalLayout();

		Label label = new Label(
				"Are you sure you want to delete report from " + report.getDate() + "? It will be permament.");
		Button yesButton = new Button("Yes");
		yesButton.addClickListener((__) -> {
			LOGGER.info(report.getDate().toString());
			LOGGER.info("++++++++++++++++++++++++++++++++++++++" + Long.toString(report.getId()));
			reportService.delete(report);
			grid.setItems(reportService.findAll());
			dialog.close();
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

}
