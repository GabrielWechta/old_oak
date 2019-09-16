package com.harman.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeService {

	private static EmployeeService instance;
	private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());

	private final HashMap<String, Employee> contacts = new HashMap<>();
	private int nextId = 0;

	private EmployeeService() {
	}

	public static EmployeeService getInstance() {
		if (instance == null) {
			instance = new EmployeeService();
			instance.ensureTestData();
		}
		return instance;
	}

	public synchronized List<Employee> findAll() {
		return findAll(null);
	}

	public synchronized List<Employee> findAll(String stringFilter) {
		ArrayList<Employee> arrayList = new ArrayList<>();
		for (Employee contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Employee>() {

			@Override
			public int compare(Employee o1, Employee o2) {
				return (int) (Integer.parseInt(o2.getId()) - Integer.parseInt(o1.getId()));
			}
		});
		return arrayList;
	}

	public synchronized List<Employee> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Employee> arrayList = new ArrayList<>();
		for (Employee contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Employee>() {

			@Override
			public int compare(Employee o1, Employee o2) {
				return (int) (Integer.parseInt(o2.getId()) - Integer.parseInt(o1.getId()));
			}
		});
		int end = start + maxresults;
		if (end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}

	public synchronized int count() {
		return contacts.size();
	}

	public synchronized void delete(Employee value) {
		contacts.remove(value.getId());
	}

	public synchronized void save(Employee entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Employee is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
			return;
		}
		if (entry.getId() == null) {
		    entry.setId(Integer.toString(nextId));
		    nextId++;
		}
		try {
			entry = (Employee) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}

	public void ensureTestData() {
		if (findAll().isEmpty()) {
			final String[] names = new String[] { "Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
					"Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
					"Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
					"Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
					"Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
					"Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
					"Jaydan Jackson", "Bernard Nilsen" };
			for (String name : names) {
				String[] split = name.split(" ");
				Employee c = new Employee(split[0], split[1]);
				save(c);
			}
		
		LOGGER.info("_____________________________________________________________");
		LOGGER.info(Integer.toString(count()));
		LOGGER.info("_____________________________________________________________");
		}
	}
}