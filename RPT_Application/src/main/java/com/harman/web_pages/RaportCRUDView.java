package com.harman.web_pages;

import org.springframework.beans.factory.annotation.Autowired;

import com.harman.raport_database.Task;
import com.harman.raport_database.TaskService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("raports")
@UIScope
public class RaportCRUDView extends VerticalLayout {

	private static final long serialVersionUID = 2179544351434704188L;

	@Autowired
	private TaskService taskService;

	public RaportCRUDView(TaskService taskService) {

		Grid<Task> grid = new Grid<>(Task.class);
		grid.setItems(taskService.findAll());
		grid.setColumns("id", "type", "name", "placement", "description");

		// grid.getColumnByKey("id").setFlexGrow(0);
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

		setSizeFull();
		add(grid);
	}

}
