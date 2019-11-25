# Mocks

This project is a proof-of-concept prototype of the [Mockito](https://github.com/mockito/mockito) framework. This library offers the basic
functionality of the Mockito framework.

[![Build Status](https://travis-ci.org/riguron/Mocks.svg?branch=master)](https://travis-ci.org/riguron/Mocks)
[![codecov](https://codecov.io/gh/riguron/Mocks/branch/master/graph/badge.svg)](https://codecov.io/gh/riguron/Mocks)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=riguron_Mocks&metric=alert_status)](https://sonarcloud.io/dashboard?id=riguron_Mocks)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=riguron_Mocks&metric=code_smells)](https://sonarcloud.io/dashboard?id=riguron_Mocks)

## Dependency

This project is distributed via JitPack. Register a JitPack repository at your pom.xml:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories
```

And add the following dependency:

```xml
<dependency>
    <groupId>com.github.riguron</groupId>
    <artifactId>Mocks</artifactId>
    <version>v1.0</version>
</dependency>
```

## Features

The public API is represented by Mocks class. Add the following static method in your test class in order to access Mocks API
in a concise manner:

```java
import static io.riguron.mocks.Mocks.*;
```

### Mocking

To create a configurable mock object, make use of mock method:

```java
UserRepository userRepository = mock(UserRepository.class); 
```

#### Configuring a mock

To configure mock behaviour, use `when` method:

```java
UserRepository userRepository = mock(UserRepository.class);
when(userRepository.count()).thenReturn(5);
assertEquals(5, userRepository.count());
```

#### Arguments

To configure a behaviour of a method with arguments on mock object, use matchers provided by ArgumentMatchers class:

```java
UserRepository userRepository = mock(UserRepository.class);
when(userRepository.findNameById(eq(1))).thenReturn("John Doe");
assertEquals("John Doe", userRepository.findNameById(1));
```

By specifying matchers, you set the arguments that must be passed to the method invocation so that the configured method call 
result is returned. In above example, you configure mock so that "John Doe" is returned if method's called with argument
of value 1. In case of providing any arguments other than 1, nothing will be returned. 

That's true for the arbitrary argument count: the return logic of your method will be reached only if each actual invocation
argument matches the corresponding matcher specified in the invocation configuration.

Note: unlike the Mockito, Mocks library always accepts a matcher as an argument during mock configuration. 

###### Generalized arguments

If the invocation arguments are not known at the compilation time or for some reason you don't want to set exact
expected arguments, make use of matchers that accept any possible arguments:

```java
Random random = new Random();
UserRepository userRepository = mock(UserRepository.class);
when(userRepository.findNameById(anyInt())).thenReturn("Name");
assertEquals("Name", userRepository.findNameById(random.nextInt()));
```

No matter what argument is passed to findNameById method, it will always return "Name".
Such a behaviour is achieved by using anyInt matcher that configures mock not to check the argument value, i.e
always match the argument. 

#### Custom return logic

Sometimes it is not enough just to return a fixed value. For example, you may need to access the arguments of the invocation.
To set a computable return result, use thenAnswer method:

```java
UserRepository userRepository = mock(UserRepository.class);
when(userRepository.findNameById(eq(1))).thenAnswer(invocation -> "User-" + invocation.getArgument(0));
assertEquals("User-1", userRepository.findNameById(1));
```

Answer interface represents the logic of the computation of the mocked method execution result, allowing for accessing invocation
arguments and other data involved in the invocation. 

#### Mocking void methods

To mock a void method, make use of prefix-style mocking:

```java
UserRepository userRepository = mock(UserRepository.class);
doAnswer(mockInvocation -> {
    System.out.println("Arguments: " + Arrays.toString(mockInvocation.getArguments()));
    return null;
}).when(userRepository).updateName(1, "Name");
userRepository.findNameById(5);
```

This test will produce the following output:

```java
Arguments: [1, Name]
```

#### Throwing exceptions

In order to configure a method to throw an exception, use either thenThrow or doThrow depending on whether
your method is void:

```java
UserRepository userRepository = mock(UserRepository.class);
when(userRepository.count()).thenThrow(UnsupportedOperationException::new);
assertThrows(UnsupportedOperationException.class, userRepository::count);
```

For the void methods, make use of doThrow methods:

```java
UserRepository userRepository = mock(UserRepository.class);
doThrow(UnsupportedOperationException::new).when(userRepository).updateName(eq(1), eq("Name"));
assertThrows(UnsupportedOperationException.class, () -> userRepository.updateName(1, "Name"));
```

### Verification

Verification allows for checking whether the method was executed a certain amount of times (including 0).
It may be useful, for example, when you have no control on method return result and can't assert the result of its
execution. It's also helpful for asserting the execution of the void methods, since there's no other elegant way to do it.

To access all verification features, declare the following import into your class file:

```java
import static io.riguron.mocks.verify.VerificationModes.*;
```

#### Asserting the number of invocations

```java
UserRepository userRepository = mock(UserRepository.class);
userRepository.count();
userRepository.count();
verify(userRepository, times(2)).count();
```

At line 3, we assert that method count was called two times so far. 
To assert that method was executed only once, the following syntax:

```java
verify(userRepository, times(1)).count();
```
may be simplified to:

```java
verify(userRepository).count();
```

#### Verifying with arguments

There's no special syntax for verifying invocations with the arguments. However, the assertion
must be configured with use of matchers, as in previous exaxmples:

```java
UserRepository userRepository = mock(UserRepository.class);
userRepository.findNameById(1);
verify(userRepository).findNameById(eq(1));
```

You may define separate assertions for the distinct arguments:

```java
UserRepository userRepository = mock(UserRepository.class);
userRepository.findNameById(1);
userRepository.findNameById(2);
verify(userRepository).findNameById(eq(1));
verify(userRepository).findNameById(eq(2));
```

Any-style syntax is also acceptable:

```java
UserRepository userRepository = mock(UserRepository.class);
userRepository.findNameById(1);
userRepository.findNameById(5);
verify(userRepository, times(2)).findNameById(anyInt());
```

#### Asserting the absence of invocations

If you need to assert that neither of mock methods was called, take advantage of verifyNoInteractions method:

```java
UserRepository userRepository = mock(UserRepository.class);
verifyNoInteractions(userRepository);
```

This assertion will pass only if none of UserRepository methods were called. 

#### Reseting method calls

Sometimes there's a need to make a verification from a clean sheet, ignoring any previous invocations.
To reset any data about method calls on a specific mock, use reset method:

```java
UserRepository userRepository = mock(UserRepository.class);
userRepository.count();
userRepository.count();
verify(userRepository, times(2)).count();
reset(userRepository);
verifyNoInteractions(userRepository);
```

Information about method calls on the mock was reset at the penultimate line, so the assertion at the last time can be
passed.

### Advanced matching

This section elaborates on the argument matching and provides the information on complex matching cases.

#### Primitive any-style matchers

If a method's parameter is primitive, you must use primitive any-style matcher and not the generalized one.
The latter is intended for configuring reference-type parameters (String, List or any non-primitive types) and not
the primitive ones.

So, always use any[int/double/etc] matchers for primitive parameters, and not just any matcher. Thus, for the given method:

```java
String findNameById(int id);
```

The following configuration will fail at the runtime:

```java
UserRepository userRepository = mock(UserRepository.class);
when(userRepository.findNameById(any())).thenReturn("Result");
```

To fix the issue, replace any with anyInt:

```java
UserRepository userRepository = mock(UserRepository.class);
when(userRepository.findNameById(anyInt())).thenReturn("Result");
assertEquals("Result", userRepository.findNameById(100));
```

#### Custom matchers

Custom matchers, i.e predicates, are intended for the complex matching cases, where the matching loggic differs from plain value comparation. 
Custom matchers are passed via argThat method:

```java
UserRepository userRepository = mock(UserRepository.class);
when(userRepository.getAge(argThat(new ArgumentMatcher<String>() {
    @Override
    public boolean matches(String x) {
        return x.length() == 8 && x.contains("Name");
    }
}))).thenReturn(5);
assertEquals(5, userRepository.getAge("UserName"));
```

Lambda-style is acceptable as well:

```java
UserRepository userRepository = mock(UserRepository.class);
when(userRepository.getAge(argThat(x -> x.length() == 8 && x.contains("Name")))).thenReturn(5);
assertEquals(5, userRepository.getAge("UserName"));
```

### Mocking limitations

An entire idea of mocking is based on [Proxy](https://sourcemaking.com/design_patterns/proxy) pattern. Mocks implement it using 
subclassing your original class in runtime to intercept its methods. Therefore, Mocks is not able to mock a component that is not
subclassable / overridable. Thus, the following components can not be mocked or configured:

- Final and private methods
- Final classes
- Methods that are not accessible from the test class
- Static methods, regardless of the visibility

Also, you can't mock equals, hashCode and toString methods since they're involved in the internal Mocks implementation.
Obviously, you can't mock fields (and should never do it).
Constructors can not be mocked or configured as well.

However, you are free to mock:

- Ordinary classes
- Abstract classes and interfaces
- Classes without the default empty constructor
- Classes with a private constructor
- Local classes
- Non-static inner classes

### Exception handling

Mocks framework is pretty strict in terms of syntax and aims to report any API misuses. This section elaborates on exceptions
that may arise while using Mocks features.

#### UnfinishedStubbingException

Indicates that the method behaviour is configured improperly, namely lacking the essential part. 
Consider the following example:

```java
UserRepository repository = mock(UserRepository.class);
when(repository.getAge(any()));
assertEquals(5, repository.getAge("Name"));
```

As you see, when expression is not finished and lacks the specification of the return result, which is mandatory part of the
behaviour configuration.
