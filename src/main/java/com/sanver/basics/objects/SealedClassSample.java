package com.sanver.basics.objects;

import java.util.function.Consumer;

/**
 * Demonstrates the use of Sealed Classes, a feature that restricts which classes or interfaces can extend
 * or implement a particular superclass or interface. Sealed classes provide a controlled and closed hierarchy,
 * making it clear and type-safe which classes can be part of an inheritance structure.
 *
 * <p>Features demonstrated:
 * <ul>
 *   <li><b>Sealed Superclass</b>: Defines a sealed superclass or interface using the {@code permits} clause.</li>
 *   <li><b>Permitted Subclasses</b>: Shows how only specific subclasses can extend or implement the sealed superclass.</li>
 *   <li><b>Final, Non-Sealed, and Further Sealed Subclasses</b>: Demonstrates different types of subclasses that are allowed
 *       within a sealed hierarchy (final, non-sealed, and further sealed).</li>
 *   <li><b>Pattern Matching with Sealed Classes</b>: Combines pattern matching with {@code instanceof} to handle sealed hierarchies.</li>
 * </ul>
 * <p><b>Constraints:</b></p>
 * A sealed class imposes three important constraints on its permitted subclasses:
 * <ol>
 *     <li>All permitted subclasses must belong to the same module as the sealed class.</li>
 *     <li>Every permitted subclass must explicitly extend the sealed class.</li>
 *     <li>Every permitted subclass must define a modifier: final, sealed, or non-sealed.</li>
 * </ul>
 * <a href="https://www.baeldung.com/java-sealed-classes-interfaces">Source</a>
 */
public class SealedClassSample {

    /**
     * Sealed superclass {@code Shape}, which permits only specific subclasses (Circle, Rectangle, and Triangle).
     */
    public static sealed class Shape permits Circle, Rectangle, Triangle { // Note: In case when all permitted subclasses are declared in the same file there is no need to mention the explicitly and permits part of a declaration can be omitted.
        public double area(){
            return 0;
        }
    }

    /**
     * A final subclass {@code Circle}, which is part of the sealed {@code Shape} hierarchy.
     * Being {@code final}, no other class can extend {@code Circle}.
     */
    public static final class Circle extends Shape {
        private final double radius;

        public Circle(double radius) {
            this.radius = radius;
        }

        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }

    /**
     * A non-sealed subclass {@code Rectangle}, which can have further subclasses outside of the {@code Shape} hierarchy.
     */
    public static non-sealed class Rectangle extends Shape {
        private final double width;
        private final double height;

        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public double area() {
            return width * height;
        }
    }

    /**
     * A further sealed subclass {@code Triangle}, which is itself sealed and permits only specific subclasses.
     * The {@code Triangle} class defines an additional hierarchy within the sealed {@code Shape} hierarchy.
     */
    public static sealed class Triangle extends Shape permits EquilateralTriangle {
        private final double base;
        private final double height;

        public Triangle(double base, double height) {
            this.base = base;
            this.height = height;
        }

        @Override
        public double area() {
            return 0.5 * base * height;
        }
    }

    /**
     * A final subclass {@code EquilateralTriangle}, which is a permitted subclass of {@code Triangle}.
     */
    public static final class EquilateralTriangle extends Triangle {
        public EquilateralTriangle(double side) {
            super(side, Math.sqrt(3) / 2 * side);
        }
    }

    /**
     * Processes a shape object and uses pattern matching to determine its type and calculate its area.
     * Pattern matching combined with sealed classes provides a clear and safe way to handle all possible
     * subclasses of a sealed class.
     *
     * @param shape the shape to process
     */
    public static void processShape(Shape shape) {
        if (shape instanceof Circle c) {
            System.out.println("Processing a circle with area: " + c.area());
        } else if (shape instanceof Rectangle r) {
            System.out.println("Processing a rectangle with area: " + r.area());
        } else if (shape instanceof EquilateralTriangle et) {
            System.out.println("Processing an equilateral triangle with area: " + et.area());
        } else if (shape instanceof Triangle t) {
            System.out.println("Processing a generic triangle with area: " + t.area());
        } else {
            System.out.println("Generic shape with area: " + shape.area());
        }
    }

    /**
     * Switch pattern matching is finalized in Java 21
     * @param shape the shape to process
     */
    public static void processShapeWithSwitchPatternMatching(Shape shape) {
        switch (shape) {
            case Circle c -> System.out.println("Processing a circle with area: " + c.area());
            case Rectangle r -> System.out.println("Processing a rectangle with area: " + r.area());
            case EquilateralTriangle et ->
                    System.out.println("Processing an equilateral triangle with area: " + et.area());
            case Triangle t -> System.out.println("Processing a generic triangle with area: " + t.area());
            case Shape s -> System.out.println("Generic shape"); // If this was not defined, it would result in an error since all possible cases are not listed.
            // If this was defined above, it would result in a compile error as well suggesting: Label is dominated by a preceding case label 'Shape s'
            // Although we do not use s, s should be put in case Shape s, otherwise it will result in this error: Type pattern expected
        }
    }

    /**
     * Main method to demonstrate usage of sealed classes and pattern matching in a sealed hierarchy.
     */
    public static void main(String[] args) {
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        Shape triangle = new Triangle(4.0, 5.0);
        Shape equilateralTriangle = new EquilateralTriangle(3.0);
//        Shape genericShape = new Shape(){ // Anonymous classes are not allowed to extend the sealed class.
//            @Override
//            public double area() {
//                return 0;
//            }
//        };
        Shape genericShape = new Shape();
        var shapes = new Shape[]{circle, rectangle, triangle, equilateralTriangle, genericShape};
        processShapes(SealedClassSample::processShape, shapes);
        System.out.println();
        processShapes(SealedClassSample::processShapeWithSwitchPatternMatching, shapes);
    }

    public static void processShapes(Consumer<Shape> shapeProcessor, Shape... shapes) {
        for (var shape: shapes) {
            shapeProcessor.accept(shape);
        }
    }
}
