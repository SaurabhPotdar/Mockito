# Sample project for Mockito

References

Generate Jacoco code coverage
https://mkyong.com/maven/maven-jacoco-code-coverage-example/

https://lkrnac.net/blog/2014/01/mock-private-method/#mock-private-method

## Mock vs Spy
With mock we are just creating a fake object, so calling methods on the mock will do nothing or return default values. eg. size() returns 0. We can mock methods to change this behaviour.</br>
```
@Test
public void testMockList() {
  List<String> mockList = mock(ArrayList.class);
  //by default, calling the methods of mock object will do nothing
  mockList.add("test");  //does nothing
  assertEquals(0, mockList.size());  //returns default values
  assertNull(mockList.get(0));
  
  //Mocking methods of mock
  when(mockList.get(100)).thenReturn(expected);
  assertEquals(expected, mockList.get(100));
}
```
With spy we get a real object and it will call the real method if it is not stubbed.
```
@Test
public void testSpyList() {
  //spy object will call the real method when not stubbed
  spyList.add("test");
  assertEquals(1, spyList.size());
  assertEquals("test", spyList.get(0));
  
  //Mocking methods of spy
  when(mockList.get(100)).thenReturn(expected);
  assertEquals(expected, mockList.get(100));
}
```

## @InjectMocks and @Mock
We cannot @InjectMocks and @Mock together as it throws exception, so we need to use @InjectMocks and @Spy together. So how to simulate @InjectMocks and @Mock??</br>

Mock on service will not work as it does nothing.
```
@Mock
private EmployeeService mock;
mock.foo();  //Will not do anything and will just return default value.
```
To call the test method we need @Spy @InjectMocks or create a new() service object and inject mocks into it using constructor injection.
@Spy will be used to stub internal methods.

## Testing Controller
@WebMvcTest
### Path variables
We can test as normal method by passing path variables as function arguements. If it does not work then we can use [mockMvc](https://gist.github.com/keesun/2373081).

## [Testing Repository](https://github.com/SaurabhPotdar/jpa-demo/tree/main/jpa-demo/src/test)
```@DataJpaTest``` automatically creates an in memory H2 Database for testing.</br>
We can configure our own DB for testing by creating an application.properties file in src/test/resources.[Ref](https://stackoverflow.com/questions/36649179/java-h2-in-memory-database-error-table-not-found)</br>
We can also use [annotations](https://stackoverflow.com/questions/61671173/problem-running-unit-test-under-datajpatest).

## Dependency injection
1. We can use **@InjectMocks** if dependencies are **Autowired** in our class.
2. For constructor injection
```
class MyController{
  
  private MyService service;
  
  MyController(MyService service){	
    this.service = service;
  }
}
```
```
class Test{
  private MyService service = Mockito.mock(MyService.class);
  private MyController controller = new Controller(service);
}
```
