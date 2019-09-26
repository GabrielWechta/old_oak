package com.harman.web_pages;

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
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

public class DefaultReportCRUDView extends VerticalLayout {

	private static final long serialVersionUID = 2179544351434704188L;
	@Autowired
	ReportService reportService;

	@Autowired
	EmployeeService employeeService;

	private Grid<Report> grid;
	private Employee employee;

	@Autowired
	public DefaultReportCRUDView(ReportService reportService, EmployeeService employeeService) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDao) {
			grid = new Grid<>(Report.class);

			employee = ((UserDao) principal).getUserDetails();
			grid.setItems(reportService.findByEmployeeUsername(employee.getUsername()));

			grid.setColumns("id", "date", "wwb");

			grid.getColumnByKey("id").setHeader("rep_id");
			grid.getColumnByKey("id").setFlexGrow(0);

			Icon icon = new Icon(VaadinIcon.DOWNLOAD);
			icon.setColor("orange");
			grid.addComponentColumn((__) -> {
				return icon;
			}).setHeader("Download").setFlexGrow(0).setKey("Download");

			grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

			setSizeFull();
			add(grid);

		} else
			add(new H3("error, you are no longer logged"));
	}

	public Grid<Report> getGrid() {
		return grid;
	}

//	public static void setGrid(Grid<Report> grid) {
//		this.grid = grid;
//	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
