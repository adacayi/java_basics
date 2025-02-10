package com.sanver.basics.condition;

public class IfStatementSample {
    public static void main(String[] args) {
        boolean b = false;
        if (b = true) { // This assigns b to true and the assigned value is evaluated as the condition expression
            System.out.println(b);
        }

        int x = 0;
//        if(x = 5){} // This results in a compile error. Only expressions which evaluate to a boolean value can be used as the condition in an if statement.
        if(true){} // This works without any compile errors
        if(false);else; // This works without any compile errors
    }
}
