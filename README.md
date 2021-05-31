# Sample project for Mockito

References

Generate Jacoco code coverage
https://mkyong.com/maven/maven-jacoco-code-coverage-example/

https://lkrnac.net/blog/2014/01/mock-private-method/#mock-private-method

## Testing Controller
### Path variables
We can test as normal method by passing path variables as function arguements. If it does not work then we can use [mockMvc](https://gist.github.com/keesun/2373081).

## [Testing Repository](https://github.com/SaurabhPotdar/jpa-demo/tree/main/jpa-demo/src/test)
```@DataJpaTest``` automatically creates an in memory H2 Database for testting.</br>
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
