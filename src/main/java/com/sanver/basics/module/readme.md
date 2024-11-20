# Java Modules

[Source1](https://www.oracle.com/uk/corporate/features/understanding-java-9-modules.html)

[Source2](https://www.baeldung.com/java-modularity)

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
 
    To specify a dependency on another module and to ensure that other modules reading your module also read that dependency—known as implied readability—use requires transitive, as in:
    ```
    requires transitive modulename;
    ```
    Consider the following directive from the `java.desktop` module declaration:
    ```
    requires transitive java.xml;
    ```
    In this case, any module that reads `java.desktop` also implicitly reads `java.xml`. 
    For example, if a method from the `java.desktop` module returns a type from the `java.xml` module, 
    code in modules that read `java.desktop` becomes dependent on `java.xml`. 
    Without the `requires transitive` directive in `java.desktop`’s module declaration, 
    such dependent modules will not compile unless they *explicitly* read `java.xml`.

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
    
    A **service** is an object of a class that implements the interface or extends the abstract class specified in the uses directive.

    We might require a module that provides a service we want to consume, but that service implements an interface from one of its transitive dependencies.

    Instead of forcing our module to require all transitive dependencies just in case, we use the uses directive to add the required interface to the module path.
    ```
    module com.client.myservicelient {
        requires com.company.myservice;
        uses com.company.myservice.MyService;
    }
    ```
 
    The following example demonstrates the use of `uses` and `provides with` in the Java 9 Module System for service loading.
    
    ```java
    // 1. Service Interface (in com.example.service)
    package com.example.service;
    
    public interface MyService {
        void execute();
    }
    
    // 2. Service Provider (in com.example.provider)
    package com.example.provider;
    
    import com.example.service.MyService;
    
    public class MyServiceImpl implements MyService {
        @Override
        public void execute() {
            System.out.println("Service Executed");
        }
    }
    
    // 3. Module Descriptor for Provider
    module com.example.provider {
        requires com.example.service;
        provides com.example.service.MyService with com.example.provider.MyServiceImpl;
    }
    
    // 4. Service Consumer (in com.example.consumer)
    module com.example.consumer {
        requires com.example.service;
        uses com.example.service.MyService;
    }
    
    // 5. Service Loading in the Consumer
    import com.example.service.MyService;
    
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
 


