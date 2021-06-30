package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cg.dto.Employee;
import com.cg.repository.EmployeeRepository;

public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public String helloWorld(int a) {
		if (a == 1)
			return "Hello";
		else if (a == 2)
			return "World";
		else
			return "HelloWorld";
	}

	public boolean branchCoverage(boolean a, boolean b, boolean c) {
		return a 
			&& b 
			&& c;
	}

	public void add(Employee employee) {
		employeeRepository.save(employee);
	}

	public Employee findByIdAndIncrement(int id) {
		Employee employee = employeeRepository.findById(id).orElse(null);
		employee.setId(employee.getId() + 1);
		return employee;
	}

	public void foo() {
		Employee employee = new Employee();
		bar(employee);
	}

	public void bar(Employee employee) {
		// Will throw null pointer if employee is null
		System.out.println(employee.getName().equals("ABC"));
	}

	private void privateMethod(int id, Employee employee) {
		employee.setId(id);
		System.out.println(employee);
	}

	public void setId(int x, Employee employee) {
		privateMethod(x, employee);
	}
	
	public int fizz() {
		foo();
		bar(null);
		return 1;
	}
}
