package com.cg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.cg.dto.Employee;
import com.cg.repository.EmployeeRepository;
import com.cg.service.EmployeeService;

@SpringBootTest(classes = EmployeeService.class)
//@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = MockitoDemoApplication.class)
class MockitoDemoApplicationTests {

	
	@InjectMocks  //Injects dependencies autowired by service
	@Spy  //Used to mock internal methods
	EmployeeService employeeService;
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@Test
	@DisplayName("FindById")
	public void testFindById() {
		int id = 1;
		Employee employee = new Employee(id,"Saurabh");
		doReturn(Optional.of(employee)).when(employeeRepository).findById(id);
		assertEquals(employee, employeeService.findById(id));
		verify(employeeRepository, times(id)).findById(id);
	}
	
	@Test
	@DisplayName("Add Employee")
	public void testAdd() {
		Employee employee = new Employee();
		doReturn(employee).when(employeeRepository).save(employee);
		employeeService.add(employee);
		verify(employeeRepository, times(1)).save(employee);
	}
	
	@Test
	public void testFoo() {
		// Avoid internal methods calls
		// employeeService.foo();  //Will throw null pointer, as name is not set
		// We dont want to execute bar, as it will be tested separately
		doNothing().when(employeeService).bar(any());
		employeeService.foo();
		verify(employeeService, times(1)).bar(any());
	}

}
