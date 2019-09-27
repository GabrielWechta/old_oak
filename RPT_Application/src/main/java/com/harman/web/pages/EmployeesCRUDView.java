package com.harman.web.pages;

import org.springframework.beans.factory.annotation.Autowired;

import com.harman.database.entities.Employee;
import com.harman.database.services.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("users")
@UIScope
public class EmployeesCRUDView extends VerticalLayout {

	private static final long serialVersionUID = 2435396446114365838L;

	@Autowired
	public EmployeesCRUDView(EmployeeService employeeService, DialogCreatorMaster dialogCreatorMaster) {

		Grid<Employee> grid = new Grid<>(Employee.class);
		grid.setItems(employeeService.findAll());
		grid.setColumns("id", "username", "authority");

		grid.getColumnByKey("id").setFlexGrow(0);

		GridContextMenu<Employee> contextMenu = new GridContextMenu<>(grid);

		contextMenu.addItem("Remove", e -> {
			e.getItem().ifPresent(a -> {
				dialogCreatorMaster.createAreYouSureRemoveEmployeeDialog(grid, a);
			});
		});

		contextMenu.addItem("Edit", e -> {
			e.getItem().ifPresent(a -> {
				dialogCreatorMaster.createEditEmployeeDialog(grid, a);
			});
		});

		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

		Button addButton = new Button("Add Employee");
		addButton.addClickListener(e -> dialogCreatorMaster.createSaveEmployeeDialog(grid));

		grid.addItemDoubleClickListener(e -> {
			dialogCreatorMaster.createEmployeeReportsDialog(e.getItem());
		});

		setSizeFull();
		add(addButton, grid);
	}

}
