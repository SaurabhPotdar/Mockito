# Sample project for Mockito

References

Generate Jacoco code coverage
https://mkyong.com/maven/maven-jacoco-code-coverage-example/

https://lkrnac.net/blog/2014/01/mock-private-method/#mock-private-method

## Testing Controller
### [Path variables](https://gist.github.com/keesun/2373081)

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
