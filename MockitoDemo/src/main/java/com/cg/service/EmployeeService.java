package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cg.dto.Employee;
import com.cg.repository.EmployeeRepository;

public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public void add(Employee employee) {
		employeeRepository.save(employee);
	}
	
	public Employee findById(int id) {
		return employeeRepository.findById(id).orElse(null);
	}
	
	public void foo() {
		Employee employee = new Employee();
		bar(employee);
	}
	
	public void bar(Employee employee) {
		//Will throw null pointer if name is null
		System.out.println(employee.getName().equals("ABC"));
	}
	
	private void privateMethod() {
		Employee employee = new Employee();
		int x=1;
		employee.setId(x);
		System.out.println(employee);
	}
	
	public void setId(int x, Employee employee) {
		privateMethod();
	}
	
	

}
