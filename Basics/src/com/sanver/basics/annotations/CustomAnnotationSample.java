package com.sanver.basics.annotations;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Documented
@Inherited
@Target({ ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME) // If it is not defined as RUNTIME, default is CLASS , which does not retain the
									// annotation at runtime
@interface Author {
	String name();
	int age();
}

@Author(name = "Abdullah Sanver", age = 35)
class Person {
	@Author(name = "Abdullah Sanver 2", age = 35)
	String name;
	@Author(name = "Abdullah Sanver 3", age = 35)
	int age;

	@Author(name = "Abdullah Sanver 4", age = 35)
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	@Author(name = "Abdullah Sanver 5", age = 35)
	public String toString() {
		return name + " " + age;
	}
}

public class CustomAnnotationSample {

	public static void main(String[] args) {
		Class<Person> person = Person.class;
		Constructor<?>[] constructors = person.getConstructors();
		Field[] fields = person.getDeclaredFields();
		Method[] methods = person.getDeclaredMethods();
		Author author;
		System.out.println("Showing the author of the class Person and its contents\n");
		author = person.getAnnotation(Author.class);
		System.out.println("Author of the class: " + author.name() + " at age " + author.age());
		System.out.println("\nAuthors of the constructors\n");

		for (Constructor<?> constructor : constructors) {
			author = constructor.getAnnotation(Author.class);
			System.out.printf("Author of the constructor %s is %s at age %s\n", constructor, author.name(),
					author.age());
		}

		System.out.println("\nAuthors of the fields\n");

		for (Field field : fields) {
			author = field.getAnnotation(Author.class);
			System.out.printf("Author of the field %s is %s at age %s\n", field, author.name(), author.age());
		}

		System.out.println("\nAuthors of the methods\n");

		for (Method method : methods) {
			author = method.getAnnotation(Author.class);
			System.out.printf("Author of the method %s is %s at age %s\n", method, author.name(), author.age());
		}
	}

}
