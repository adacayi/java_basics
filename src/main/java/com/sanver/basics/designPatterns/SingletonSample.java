package com.sanver.basics.designPatterns;

class Connection {
    private static Connection single_instance;
    private static final Object lock = new Object();

    private Connection() {
    }

    public static Connection getInstance() {
        synchronized (lock) {
            if (single_instance == null)
                single_instance = new Connection();
        }

        return single_instance;
    }
}

public class SingletonSample {
    public static void main(String... args) {
        Connection first = Connection.getInstance();
        Connection second = Connection.getInstance();
        System.out.println(first==second);
    }
}
