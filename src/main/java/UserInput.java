import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInput {
    private Scanner sc;
    private List<Task> newStorage;

    public UserInput(Scanner sc) {
        this.newStorage = new ArrayList<>();
        this.sc = sc;
    }

    public void executeInput() {
        while (sc.hasNextLine()) {
            String userInput = sc.nextLine();
            String[] inputBreakdown = userInput.split(" ");
            if (inputBreakdown[0].equals("bye")) {
                indentInput("Bye. Hope to see you again!");
                break;
            } else if (inputBreakdown[0].equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < this.newStorage.size(); i++) {
                    printTask(i, this.newStorage.get(i));
                }
            } else if (inputBreakdown[0].equals("done") && Integer.valueOf(inputBreakdown[1]) > 0 && Integer.valueOf(inputBreakdown[1]) <= this.newStorage.size() + 1) {
                System.out.println("Nice! I've marked this task as done:");
                int selectedIndex = Integer.valueOf(inputBreakdown[1]) - 1;
                this.newStorage.get(selectedIndex).setDone(true);
                printTask(selectedIndex, this.newStorage.get(selectedIndex));
            } else {
                addTask(userInput);
            }
        }
    }

    public static void indentInput(String input) {
        String line = "";
        for (int i = 0; i < 50; i++) {
            line = line.concat("_");
        }
        System.out.println(line);
        System.out.println("added: " + input);
        System.out.println(line);
    }

    public void printTask(int num, Task task) {
        if (task.getDone()) {
            System.out.println(num + 1 + "." + "[" + task.type + "]" + "[X] " + this.newStorage.get(num).getDescription());
        } else {
            System.out.println(num + 1 + "." + "[" + task.type + "]" + "[ ] " + this.newStorage.get(num).getDescription());
        }
    }

    public void printTaskWithNoNum(Task task) {
        System.out.println("[" + task.type + "]" + "[ ] " + task.getDescription());
    }

    public void addTask(String userInput) {
        Task task;
        String[] input = userInput.split(" ");
        if (input[0].equals("todo")) {
            String description = userInput.substring(userInput.indexOf("todo") + 5);
            task = new Todo(description);
        } else if (input[0].equals("deadline")) {
            String description = userInput.substring(userInput.indexOf("deadline") + 9, userInput.indexOf("/by"));
            String deadline = userInput.substring(userInput.indexOf("/by") + 4);
            task = new Deadline(description, deadline);
        } else {
            String description = userInput.substring(userInput.indexOf("event") + 6, userInput.indexOf("/at"));
            String eventDate = userInput.substring(userInput.indexOf("/at") + 4);
            task = new Event(description, eventDate);
        }
        newStorage.add(task);
        System.out.println("Got it. I've added this task: ");
        printTaskWithNoNum(task);
        System.out.println("Now you have " + this.newStorage.size() + " tasks in the list.");
    }
}