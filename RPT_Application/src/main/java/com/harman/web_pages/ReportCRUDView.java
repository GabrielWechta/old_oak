package com.harman.web_pages;

import org.springframework.beans.factory.annotation.Autowired;

import com.harman.report_database.Report;
import com.harman.report_database.ReportService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("raports")
@UIScope
public class ReportCRUDView extends VerticalLayout {

	private static final long serialVersionUID = 2179544351434704188L;

	@Autowired
	private ReportService raportService;

	@Autowired
	public ReportCRUDView(ReportService raportService) {

		Grid<Report> grid = new Grid<>(Report.class);
		grid.setItems(raportService.findByEmployeeUsername("Gabrielle"));
		grid.setColumns("id", "employeeUsername", "tasks");

		// grid.getColumnByKey("id").setFlexGrow(0);
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

		setSizeFull();
		add(grid);
	}

}
