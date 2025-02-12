# Java Modules

[Source1](https://www.oracle.com/uk/corporate/features/understanding-java-9-modules.html)

[Source2](https://www.baeldung.com/java-modularity)

## Testing modules
1. Unzip the `module.zip` in this folder to here, so that there will be `client`, `greeter`, `hellogreeter` and `higreeter` under this folder.
2. Content of each **module declaration** (`module-info.java`)

`com.greeter` is the module containing the service interface.

`com.greeter\module-info.java`
```
module com.greeter {
	exports com.greeter to com.client, com.hellogreeter, com.higreeter;
}
```

`com.hellogreeter` and `com.higreeter` are service providers for `com.greeter.Greeter`.

`com.hellogreeter\module-info.java`
```
module com.hellogreeter {
	requires com.greeter;
	provides com.greeter.Greeter with com.hellogreeter.HelloGreeter;
}
```

`com.higreeter\module-info.java`
```
module com.higreeter{
	requires com.greeter;
	provides com.greeter.Greeter with com.higreeter.HiGreeter;
}
```

`com.client` is a service consumer module, consuming `com.greeter.Greeter` services.

Note that there is no `uses` or `requires` needed for the service providers in the `com.client\module-info.java`.

It just specifies `requires` and `uses` for the service interface.

The service providers used will be decided when defining module path at the module execution.

`com.client\module-info.java`
```
module com.client {
	requires com.greeter;
	uses com.greeter.Greeter;
}
```

3. Open a terminal in this folder
4. Compile the modules `com.client`, `com.greeter`, `com.hellogreeter` and `com.higreeter` with the following command
```
    javac -d target --module-source-path 'client\src;greeter\src;hellogreeter\src;higreeter\src' --module com.client,com.greeter,com.hellogreeter,com.higreeter
    or
    javac -d target --module-source-path 'client\src;greeter\src;hellogreeter\src;higreeter\src' -m com.client,com.greeter,com.hellogreeter,com.higreeter
```
5. Running the `com.client.Application` class.
```
    java --module-path target --module com.client/com.client.Application
    or
    java -p target -m com.client/com.client.Application
    or you can specify each module directory individually
    java -p 'target/com.client;target/com.greeter;target/com.hellogreeter;target/com.higreeter' -m com.client/com.client.Application
```

6. Specifying service providers when running modules

Note that you can include any service provider when running a module, without making any change to the module (thus showing how loosely coupled the service provider and the consumer is).

The below will only run the `com.hellogreeter.HelloGreeter`.

```
java -p 'target/com.client;target/com.greeter;target/com.hellogreeter' -m com.client/com.client.Application
```

7. Packaging modules
```
    jar --create --file com.client.jar --main-class com.client.Application -C target/com.client .
    jar --create --file com.greeter.jar -C target/com.greeter .
    jar --create --file com.hellogreeter.jar -C target/com.hellogreeter .
    jar --create --file com.higreeter.jar -C target/com.higreeter . 
```
Note that `--main-class` specifies the main class in the jar. 

The `-C` option is to make the jar command change its working directory before adding files to the jar.

The `.` at the end means including everything in the current directory to the jar file.

7. Running modules
```
    java --module-path 'com.client.jar;com.greeter.jar;com.hellogreeter.jar;com.higreeter.jar' --module com.client
    or
    java --module-path 'com.client.jar;com.greeter.jar;com.hellogreeter.jar;com.higreeter.jar' --module com.client/com.client.Application
    or
    java -p 'com.client.jar;com.greeter.jar;com.hellogreeter.jar;com.higreeter.jar' -m com.client
    or
    java -p . -m com.client
```
8. It is possible to use a modular jar just like a non-modular jar. But this way, we lose all the benefits of the module like the services. For example, we won't see the services when `com.client.Application` is run even with the implementations are provided in the classpath.
```
    java -cp 'com.client.jar;com.greeter.jar;com.hellogreeter.jar;com.higreeter.jar' com.client.Application
```
9. Listing modules in a module path
```
    java --module-path target\com.client --list-modules
    java -p com.client.jar --list-modules
    java -p . --list-modules
```
10. Describing a module
```
    java --module-path com.client.jar -describe com.client
    java -p target/com.client -d com.client
    java -p com.client.jar -d com.client
    java -d java.base
```
11. `--show-module-resolution` Showing all modules being referred to while running a module. Note that this switch is available only when running a module.
```
    java -p 'target/com.client;target/com.greeter;target/com.hellogreeter' --show-module-resolution -m com.client/com.client.Application
    java -p 'com.client.jar;com.greeter.jar;com.hellogreeter.jar' --show-module-resolution -m com.client
```
12. `jdeps` This is a tool to deal with module dependencies without executing them. It helps show module, package and class level dependencies of a given module.
```
    jdeps --module-path 'com.client.jar;com.greeter.jar' -m com.client
    jdeps --module-path 'com.client.jar;com.greeter.jar' -m com.client -verbose:class // showing class level dependencies, excluding dependencies within the same package by default
    jdeps --module-path 'com.client.jar;com.greeter.jar' -m com.client -v // same as -verbose:class -filter:none
    jdeps --module-path 'com.client.jar;com.greeter.jar' -m com.client -verbose // same as -verbose:class -filter:none
    jdeps --module-path 'com.client.jar;com.greeter.jar' -m com.client -filter:module // Filter dependences within the same module. default is -filter:package
    jdeps -m java.base
```

## Key points
1. **Module declaration** is defined in a file named `module-info.java`.
2. Compiling the module declaration creates the **module descriptor**, which is stored in a file named `module-info.class` in the module’s root folder.
3. Module declaration file must contain 
    <pre>
    module <b>modulename</b> {
    }
    </pre>
4. The body can be empty or contain various module directives.
5. The directives are `requires`, `exports`, `uses`, `opens`,`provides...with`.
6. A `requires` module directive specifies that this module depends on ***another module***—this relationship is called a **module dependency**.
7. `requires` usage:
    ```
    requires modulename;
    ```
8. `requires transitive` **implied readability** 

    In module terminology, accessing a module is called **reading** the module.

    To specify a dependency on another module and to ensure that other modules reading your module also read that dependency—known as **implied readability**—use `requires transitive`, as in:
   
    <pre>
    <b>requires transitive</b> modulename;
    </pre>
   
    Consider the following directive from the `java.desktop` module declaration:
    ```
    requires transitive java.xml;
    ```
    In this case, any module that reads `java.desktop` also implicitly reads `java.xml`. 
    For example, if a method from the `java.desktop` module returns a type from the `java.xml` module, 
    code in modules that read `java.desktop` becomes dependent on `java.xml`. 
    Without the `requires transitive` directive in `java.desktop`’s module declaration, 
    such dependent modules will not compile unless they *explicitly* read `java.xml` (i.e. they include a `requires java.xml` in their module declaration).

9. `exports` and `exports…to`

    An `exports` module directive **specifies one of the module’s packages** whose public types (and their nested public and protected types) should be accessible to code in all other modules.
    
    An `exports…to` directive enables you to specify in a comma-separated list precisely which module’s or modules’ code can access the exported package—this is known as a **qualified export**.

    Note that `*` is not allowed in `exports`. This is to enforce fine-grained control over what parts of a module are exposed, promoting better encapsulation and modularity.

    ```
    module java.xml {
    exports javax.xml;
    exports javax.xml.catalog;
    exports javax.xml.datatype;
    exports javax.xml.namespace;
    exports javax.xml.parsers;
    . . .
    }
    ```
   
    ```
    module java.xml {
        exports com.sun.org.apache.xml.internal.dtm to
            java.xml.crypto;
        exports com.sun.org.apache.xml.internal.utils to
            java.xml.crypto;
    }
    ```
   
10. `uses` 

    A `uses` module directive specifies a service used by this module—making the module a service consumer. 
    
    A **service** is an object of a class that implements the interface or extends the abstract class specified in the uses directive. Technically, it can be a concrete class as well.

    We might require a module that provides a service we want to consume, but that service implements an interface from one of its transitive dependencies.

    Instead of forcing our module to require all transitive dependencies just in case, we use the uses directive to add the required interface to the module path.
    ```
    module com.example.consumer {
        requires com.company.myservice;
        uses com.company.myservice.MyService;
    }
    ```
 
    The following example demonstrates the use of `uses` and `provides with` in the Java 9 Module System for service loading.
    
    ```java
    // 1. Service Interface (in com.company.myservice)
    package com.company.myservice;
    
    public interface MyService {
        void execute();
    }
    
    // 2. Service Provider (in com.example.provider)
    package com.example.provider;
    
    import com.company.myservice.MyService;
    
    public class MyServiceImpl implements MyService {
        @Override
        public void execute() {
            System.out.println("Service Executed");
        }
    }
    
    // 3. Module Descriptor for Provider
    module com.example.provider {
        requires com.company.myservice;
        provides com.company.myservice.MyService with com.example.provider.MyServiceImpl;
    }
    
    // 4. Service Consumer (in com.example.consumer)
    module com.example.consumer {
        requires com.company.myservice;
        uses com.company.myservice.MyService;
    }
    
    // 5. Service Loading in the Consumer
    import com.company.myservice.MyService;
    
    import java.util.ServiceLoader;
    
    public class Main {
        public static void main(String[] args) {
            ServiceLoader<MyService> loader = ServiceLoader.load(MyService.class);
            loader.forEach(MyService::execute);
        }
    }
    
11. `provides…with`

    A `provides…with` module directive specifies that a module provides a service implementation—making the module a **service provider**. 

    The `provides` part of the directive specifies an **interface or abstract class** listed in a module’s uses directive and the `with` part of the directive specifies the name of the **service provider class that implements the interface or extends the abstract class**.

    ```
    module my.module {
        provides MyInterface with MyInterfaceImpl;
    }
    ```

#### Service Provider Requirements

To ensure proper implementation, the service provider must adhere to the following guidelines:

- **Class Structure**
   - The service provider **must be public**.
   - It **must not be an inner class**.
   - It can be either:
     - A **top-level class**, or  
     - A **nested static class**.

- **Provider Method**
   - The service provider should define a **public static method** named `provider`.
   - This method must:
     - Take **no arguments**.
     - Have a **return type assignable** to the service's interface or class.
   - If a `provider` method is present:
     - It will be invoked **whenever an instance** of the service provider is needed.
     - It **does not necessarily need to return a new instance**; it can return a **cached instance** instead.

- **Constructor Requirement (If No Provider Method)**
   - If the service provider does **not** have a `provider` method:
     - It **must** have a **public no-argument constructor**.
   - This constructor will be used to instantiate the service provider **whenever needed**.
   
12. `open`, `opens`, and `opens…to` 

    Before Java 9, reflection could be used to learn about all types in a package and all members of a type—even its private members—whether you wanted to allow this capability or not. Thus, nothing was truly encapsulated.
    
    A key motivation of the module system is strong encapsulation. By default, a type in a module is not accessible to other modules unless it’s a public type and you export its package. You expose only the packages you want to expose. With Java 9, this also applies to reflection.
    
    #### Allowing runtime-only access to a package 
    An opens module directive of the form
    
    ```
    opens package
    ```
    
    indicates that a specific **package’s public types** (and their nested public and protected types) are accessible to code in other modules at runtime only. Also, all the types in the specified package (and all of the types’ members) are accessible via reflection.
    
    #### Allowing runtime-only access to a package by specific modules

    An `opens…to` module directive of the form
    
    ```
    opens package to comma-separated-list-of-modules
    ```
    
    indicates that a specific package’s public types (and their nested public and protected types) are accessible to code in the listed modules at runtime only. All of the types in the specified package (and all of the types’ members) are accessible via reflection to code in the specified modules.
    
    #### Allowing runtime-only access to all packages in a module
 
    If all the packages in a given module should be accessible at runtime and via reflection to all other modules, you may open the entire module, as in:
    
    ```
    open module modulename {
    // module directives
    }
    ```
    **Note:** A module requiring the `modulename` above will not automatically be able to read the public classes of `modulename` at compile time, even though `modulename` is declared as open. The `modulename` should export the desired packages explicitly for that purpose. 
 


