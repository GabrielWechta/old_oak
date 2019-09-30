package com.harman.web.pages;

import org.springframework.beans.factory.annotation.Autowired;

import com.harman.database.entities.Report;
import com.harman.database.entities.Task;
import com.harman.database.services.TaskService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

public class TaskCRUDView extends VerticalLayout {
	private static final long serialVersionUID = -7953493189284280980L;
	
	
	public TaskCRUDView(/*final */TaskService taskService, Report report) {

		Grid<Task> grid = new Grid<>(Task.class);
		grid.setItems(taskService.findByReport(report));
		grid.setColumns("id", "type", "name", "placement", "description", "wwbType");

		grid.addItemDoubleClickListener(e -> viewTask(e));
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);
		add(grid);
	}

	public void viewTask(ItemDoubleClickEvent<Task> e) {
		Dialog dialog = new Dialog();
		FormLayout formLayout = new FormLayout();
		dialog.setHeightFull();
		dialog.setWidth("750px");

		Task task = e.getItem();
		TextArea typeTextArea = new TextArea("Type");
		typeTextArea.setValue(task.getType());
		typeTextArea.setSizeFull();
		typeTextArea.setReadOnly(true);
		TextArea nameTextArea = new TextArea("Name");
		nameTextArea.setValue(task.getName());
		nameTextArea.setSizeFull();
		nameTextArea.setReadOnly(true);
		TextArea placementTextArea = new TextArea("Placement");
		placementTextArea.setValue(task.getPlacement());
		placementTextArea.setSizeFull();
		placementTextArea.setReadOnly(true);
		TextArea descriptionTextArea = new TextArea("Description");
		descriptionTextArea.setValue(task.getDescription());
		descriptionTextArea.setSizeFull();
		descriptionTextArea.setReadOnly(true);
		TextArea wwbTextArea = new TextArea("WWB");
		wwbTextArea.setValue(task.getWwbType().toString());
		wwbTextArea.setReadOnly(true);

		formLayout.add(typeTextArea, nameTextArea, placementTextArea, descriptionTextArea, wwbTextArea);
		dialog.add(formLayout);
		dialog.open();
	}

}
