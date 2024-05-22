import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static int taskNumber = 0;

    public static void main(String[] args) {
        // Welcome the user and prompt to sign up
        JOptionPane.showMessageDialog(null, "Welcome User");
        JOptionPane.showMessageDialog(null, "Sign up");

        String name = JOptionPane.showInputDialog("Enter Name");
        String surname = JOptionPane.showInputDialog("Enter Surname");
        String username = JOptionPane.showInputDialog("Enter Username:");

        // Check username format
        while (!checkUserName(username)) {
            JOptionPane.showMessageDialog(null, "Username is not correctly formatted. Please ensure that your username contains an underscore (_) and is no more than 5 characters in length.");
            username = JOptionPane.showInputDialog("Enter Username:");
        }
        JOptionPane.showMessageDialog(null, "Username successfully captured");

        // Prompt the user to enter a password
        String password = JOptionPane.showInputDialog("Enter password");

        // Check password complexity
        while (!checkPasswordComplexity(password)) {
            JOptionPane.showMessageDialog(null, "Password is not correctly formatted. Please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
            password = JOptionPane.showInputDialog("Enter password");
        }
        JOptionPane.showMessageDialog(null, "Password successfully captured");

        JOptionPane.showMessageDialog(null, "LOGIN");

        // Check user login
        String newUsername = JOptionPane.showInputDialog("Enter username");
        String newPassword = JOptionPane.showInputDialog("Enter password");

        while (!userLogin(newUsername, username, newPassword, password)) {
            loginStatus(newUsername, username, newPassword, password, name, surname);
            JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again");
            newUsername = JOptionPane.showInputDialog("Enter username");
            newPassword = JOptionPane.showInputDialog("Enter password");
        }

        loginStatus(newUsername, username, newPassword, password, name, surname);

        // Task must welcome user and have 3 options with an array to store the tasks added by the user
        JOptionPane.showMessageDialog(null, "Welcome to EasyKanban");

        List<Task> tasks = new ArrayList<>();

        while (true) {
            String choice = JOptionPane.showInputDialog("Menu:\n1. Add tasks\n2. Show report - 'coming soon'\n3. Quit");

            switch (choice) {
                case "1":
                    addTask(tasks);
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null, "Report feature is 'coming soon'");
                    break;
                case "3":
                    JOptionPane.showMessageDialog(null, "Quit");
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please choose again.");
            }
        }
    }

    // User login status
    public static void loginStatus(String newUsername, String username, String newPassword, String password, String name, String surname) {
        if (newUsername.equals(username) && newPassword.equals(password)) {
            JOptionPane.showMessageDialog(null, "Login successful, welcome " + name + " " + surname + ", it's great to see you again");
        } else {
            JOptionPane.showMessageDialog(null, "Login Failed");
        }
    }

    // User login authentication
    public static boolean userLogin(String newUsername, String username, String newPassword, String password) {
        return newUsername.equals(username) && newPassword.equals(password);
    }

    // Validate username format
    public static boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // Check password complexity
    public static boolean checkPasswordComplexity(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    // Check the task description and it must not be more than 50 characters in length
    public static boolean checkTaskDescription(String taskDescription) {
        return taskDescription.length() <= 50;
    }

    // The Addition of the tasks onto the list
    public static void addTask(List<Task> tasks) {
        String taskName = JOptionPane.showInputDialog("Enter Task Name:");
        String taskDescription = JOptionPane.showInputDialog("Enter Task Description:");

        // Display "Please enter a task description of less than 50 characters" if characters are more than 50
        while (!checkTaskDescription(taskDescription)) {
            JOptionPane.showMessageDialog(null, "Please enter a task description of less than 50 characters");
            taskDescription = JOptionPane.showInputDialog("Enter Task Description:");
        }
        // Enter the developer's details
        String developerFirstName = JOptionPane.showInputDialog("Enter Developer First Name:");
        String developerLastName = JOptionPane.showInputDialog("Enter Developer Last Name:");
        String developerDetails = developerFirstName + " " + developerLastName;

        int taskDuration = Integer.parseInt(JOptionPane.showInputDialog("Enter Duration of Task in hours:"));

        taskNumber++;
        String taskId = createTaskID(taskName, taskNumber, developerLastName);

        String[] statuses = {"To Do", "Doing", "Done"};
        String taskStatus = (String) JOptionPane.showInputDialog(null, "Select Task Status:", "Task Status Selection",
                JOptionPane.QUESTION_MESSAGE, null, statuses, statuses[0]);

        Task task = new Task(taskName, taskDescription, developerDetails, taskDuration, taskId, taskStatus);
        tasks.add(task);
        JOptionPane.showMessageDialog(null, "Task successfully captured!");

        // Print task details
        JOptionPane.showMessageDialog(null, task.printTaskDetails());

        // Return the total hours
        int totalHours = returnTotalHours(tasks);
        JOptionPane.showMessageDialog(null, "Total number of hours of all combined tasks: " + totalHours);
    }

    // Generate task ID and autogenerate taskId which contains the first two letters a colon, a task number, and last three letters of the developer's name
    public static String createTaskID(String taskName, int taskNumber, String developerLastName) {
        String taskId = taskName.substring(0, 2).toUpperCase() + ":" + taskNumber + ":" + developerLastName.substring(developerLastName.length() - 3).toUpperCase();
        return taskId;
    }

    // Calculate total hours of all tasks combined
    public static int returnTotalHours(List<Task> tasks) {
        int totalHours = 0;
        for (Task task : tasks) {
            totalHours += task.getTaskDuration();
        }
        return totalHours;
    }
}

// Task class to store details of the task
class Task {
    private String taskName;
    private String taskDescription;
    private String developerDetails;
    private int taskDuration;
    private String taskId;
    private String taskStatus;

    public Task(String taskName, String taskDescription, String developerDetails, int taskDuration, String taskId, String taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.developerDetails = developerDetails;
        this.taskDuration = taskDuration;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getDeveloperDetails() {
        return developerDetails;
    }

    public int getTaskDuration() {
        return taskDuration;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    // Print the task's full details
    public String printTaskDetails() {
        return "Task ID: " + taskId + "\n" +
                "Task Name: " + taskName + "\n" +
                "Task Description: " + taskDescription + "\n" +
                "Developer Details: " + developerDetails + "\n" +
                "Task Duration: " + taskDuration + " hours\n" +
                "Task Status: " + taskStatus;
    }
}
