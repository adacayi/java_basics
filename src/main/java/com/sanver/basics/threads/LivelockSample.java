package com.sanver.basics.threads;

/**
 * Demonstrates a livelock scenario in Java.
 */
public class LivelockSample {

    /**
     * Represents a shared resource that two workers attempt to process.
     */
    static class SharedResource {
        private boolean taskCompleted;

        /**
         * Checks if the task is completed.
         * @return true if the task is completed; false otherwise.
         */
        public boolean isTaskCompleted() {
            return taskCompleted;
        }

        /**
         * Sets the task completion status.
         * @param taskCompleted true if task is completed; false otherwise.
         */
        public void setTaskCompleted(boolean taskCompleted) {
            this.taskCompleted = taskCompleted;
        }
    }

    /**
     * Represents a worker attempting to perform a task.
     */
    static class Worker {
        private final String name;

        /**
         * Constructs a worker with the given name.
         * @param name the name of the worker.
         */
        public Worker(String name) {
            this.name = name;
        }

        /**
         * Attempts to perform a task using a shared resource.
         * The worker yields control to another worker to avoid conflict,
         * potentially causing a livelock.
         *
         * @param resource the shared resource to work on.
         * @param otherWorker the other worker involved.
         */
        public void performTask(SharedResource resource, Worker otherWorker) {
            while (!resource.isTaskCompleted()) {
                System.out.println(name + ": Waiting for " + otherWorker.name + " to finish...");
                // Yield control to "help" the other worker
                try {
                    Thread.sleep(1000); // Simulates yielding
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(name + ": Task completed!");
        }
    }

    /**
     * Main method to demonstrate livelock.
     *
     * <p>Steps:</p>
     * <ol>
     * <li>Create a shared resource.</li>
     * <li>Instantiate two workers sharing the resource.</li>
     * <li> Start two threads representing the workers.</li>
     * </ol>
     *
     * Observed Behavior:
     * - Both threads will continuously yield control, leading to livelock.
     */
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        Worker worker1 = new Worker("Worker 1");
        Worker worker2 = new Worker("Worker 2");

        Thread thread1 = new Thread(() -> worker1.performTask(resource, worker2));
        Thread thread2 = new Thread(() -> worker2.performTask(resource, worker1));

        thread1.start();
        thread2.start();
    }
}
