package com.sanver.basics.statics;

public class StaticInnerClass {
    private String firstName;
    private String lastName;
    private int age;

    public StaticInnerClass(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Person with name %s, surname: %s at age %d", firstName, lastName, age);
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private int age;


        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder() {
        }

        public StaticInnerClass build() {
            return new StaticInnerClass(firstName, lastName, age);
        }
    }

    public static void main(String... args) {
        System.out.println(new Builder().setFirstName("Abdullah").setAge(2).build());
    }
}
