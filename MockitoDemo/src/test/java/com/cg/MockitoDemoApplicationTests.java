package com.cg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.cg.dto.Employee;
import com.cg.repository.EmployeeRepository;
import com.cg.service.EmployeeService;

@SpringBootTest(classes = EmployeeService.class)
//@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = MockitoDemoApplication.class)
class MockitoDemoApplicationTests {

	@InjectMocks // Injects dependencies autowired by service
	@Spy // Used to mock(avoid) internal method calls
	EmployeeService employeeService;

	@Mock // Will be injected into @InjectMocks employeeService
	private EmployeeRepository employeeRepository;

	@Test
	@DisplayName("Basic Testing")
	public void testHelloWorld() {
		assertEquals("Hello", employeeService.helloWorld(1));
		assertEquals("World", employeeService.helloWorld(2));
		assertEquals("HelloWorld", employeeService.helloWorld(3));
	}

	@Test
	@DisplayName("Branch Coverage")
	public void testBranchCoverage() {
		assertTrue(employeeService.branchCoverage(true, true, true));
	}

	@ParameterizedTest
	@MethodSource("provideStringsForTestBranchCoverage")
	@DisplayName("Branch Coverage with parametrized tests")
	public void testBranchCoverageWithParametrized(boolean a, boolean b, boolean c) {
		// We will have write a separate test, when all are true
		assertFalse(employeeService.branchCoverage(a,b,c));
	}
	
	private static Stream<Arguments> provideStringsForTestBranchCoverage() {
		return Stream.of(Arguments.of(false, false, false), 
				Arguments.of(true, false, false), 
				Arguments.of(true, true, false));
	}

	@Test
	@DisplayName("FindById")
	public void testFindByIdAndIncrement() {
		int id = 1;
		Employee employee = new Employee(id, "Saurabh");
		doReturn(Optional.of(employee)).when(employeeRepository).findById(id);
		assertEquals(2, employeeService.findByIdAndIncrement(id).getId());
		verify(employeeRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("Add Employee")
	public void testAdd() {
		Employee employee = new Employee();
		doReturn(employee).when(employeeRepository).save(employee);
		// Now this will be injected(autowired) into employeeService
		employeeService.add(employee);
		verify(employeeRepository, times(1)).save(employee);
	}

	@Test
	public void testFoo() {
		// Avoid internal methods calls
		// employeeService.foo(); //Will throw null pointer, as name is not set
		// We dont want to execute bar, as it will be tested separately
		doNothing().when(employeeService).bar(any());
		employeeService.foo();
		verify(employeeService, times(1)).bar(any());
	}

	@Test
	public void testPrivateMethod() {
		// Invoking private method for coverage
		String methodName = "privateMethod";
		Employee employee = new Employee();
		ReflectionTestUtils.invokeMethod(employeeService, methodName, 10, employee);
	}

	@Test
	@Disabled
	public void testSetId() throws Exception {
		// https://stackoverflow.com/questions/28121177/mock-private-method-using-powermockito/28211434
		// https://stackoverflow.com/questions/7803944/how-to-mock-private-method-for-testing-using-powermock
		// Return from private method.
		// We want to avoid private methods
//		EmployeeService classUnderTest = PowerMockito.spy(new EmployeeService());
//        PowerMockito.when(classUnderTest, "a").thenReturn(20);
//        classUnderTest.b();
	}

	@Test
	public void testFizz() {
		EmployeeService mock = Mockito.mock(EmployeeService.class);
		assertEquals(mock.fizz(), 1); // Will not work as mock does nothing and returns default value which is 0 for
										// int method.
		verify(mock, atLeastOnce()).foo(); // Does not work as mock does nothing so it does not go inside method
		verify(mock, atLeastOnce()).bar(any());
	}

}
