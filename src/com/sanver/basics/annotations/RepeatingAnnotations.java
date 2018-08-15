package com.sanver.basics.annotations;

import java.lang.annotation.*;
import java.util.Arrays;
import java.util.List;

/*
We must define a container annotation which contains the annotation that we want it to be repeatable.
Authors class is the container.
In our case, it is the @RepeatableAuthor annotation that we want it to be used multiple times.
To make it possible we must use the same modifiers used in the @RepeatableAuthor annotation for Authors class
(i.e. @Documented, @Inherited, @Target and @Retention).
*/
@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@interface Authors {
    RepeatableAuthor[] value();
}

@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME) // If it is not defined as RUNTIME, default is CLASS , which does not retain the
// annotation at runtime
@Repeatable(value = Authors.class)
@interface RepeatableAuthor {
    String name();

    int age();
}

@RepeatableAuthor(name = "Abdullah Sanver", age = 35)
@RepeatableAuthor(name = "Hatice Sanver", age = 35)
class NewPerson {

}

public class RepeatingAnnotations {

    public static void main(String[] args) {
        Class<NewPerson> person = NewPerson.class;
        List<RepeatableAuthor> authors;
        System.out.println("Showing the author of the class Person and its contents\n");
        authors = Arrays.asList(person.getAnnotationsByType(RepeatableAuthor.class));
        authors.forEach(
                author -> System.out.println(("Author of the class: " + author.name() + " at age " + author.age())));

        System.out.println(
                "\nAnother approach for retrieving repeatable annotations is by getting the container annotation and invoking its value method\n");
        Authors container = person.getAnnotation(Authors.class);
        authors = Arrays.asList(container.value());

        authors.forEach(
                author -> System.out.println(("Author of the class: " + author.name() + " at age " + author.age())));
    }
}
