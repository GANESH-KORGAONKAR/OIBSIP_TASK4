package online_exam;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class User1 {
    private String username;
    private String password;
    private String profile;

    public User1(String username, String password, String profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public String getProfile() {
        return profile;
    }

    public void updateProfile(String newProfile) {
        profile = newProfile;
        System.out.println("Profile updated successfully!");
    }

    public boolean authenticate(String enteredPassword) {
        return password.equals(enteredPassword);
    }
}

class Exam {
    private String[] questions;
    private String[] options;
    private int[] correctAnswers;
    private int examDuration; // Duration of the exam in seconds

    public Exam() {
        questions = new String[]{
            "1. Who invented Java Programming?",
            "2. Which component is used to compile, debug, and execute the java programs?",
            "3. What is the extension of java code files?",
            "4. Which one of the following is not a Java feature?",
            "5. Which of these cannot be used for a variable name in Java?",
            "6.Which statement is true about Java?",
            "7.Which environment variable is used to set the java path?",
            "8.Which of the following is not an OOPS concept in Java?",
            "9.What is not the use of <strong>this</strong> keyword in Java?",
            "10. Which exception is thrown when java is out of memory?"
        };

        options = new String[]{
            "1) Guido van Rossum  2) James Gosling  3) Dennis Ritchie  4) Bjarne Stroustrup",
            "1) JRE  2) JIT  3) JDK  4) JVM",
            "1) .js  2) .txt  3) .class  4) .java",
            "1) Object-oriented  2) Use of pointers  3) Portable  4) Dynamic and Extensible",
            "1) identifier and keyword  2) identifier  3) keyword  4) none of the mentioned",
            "1) Java is a sequence-dependent programming language  2) Java is a code dependent programming language  3) Java is a Platform-dependent programming language  4) Java is a Platform-Independent programming language",
            "1) MAVEN_Path  2) JavaPATH  3) JAVA  4) JAVA_HOME",
            "1) Polymorphism  2) Inheritance  3) Compilation  4) Encapsulation",
            "1) Referring to the instance variable when a local variable has the same name  2) Passing itself to the method of the same class  3) Passing itself to another method  4) Calling another constructor in constructor chaining",
            "1) MemoryError 2) OutOfMemoryError  3) MemoryOutOfBoundsException  4) MemoryFullException"
        };

        correctAnswers = new int[]{2, 4, 4, 2, 3, 4, 4, 3, 2, 2};
        examDuration = 60; //  minutes (in seconds)
    }

    public void startExam(User1 user) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        long startTime = System.currentTimeMillis() / 1000;
        long endTime = startTime + examDuration;

        System.out.println("Welcome, " + user.getUsername() + "! You have " + examDuration + " seconds to complete the exam.");

        for (int i = 0; i < questions.length; i++) {
            System.out.println("\nQuestion " + (i + 1) + ": " + questions[i]);
            System.out.println(options[i]);

            System.out.print("Your choice (1-4): ");
            int choice = scanner.nextInt();

            if (choice == correctAnswers[i]) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect.");
            }

            // Check if the time is up for automatic submission after each question
            if (System.currentTimeMillis() / 1000 >= endTime) {
                submitAutomatically(score);
                return;  // Exit the method after automatic submission
            }
        }

        // Check if the user wants to submit the exam manually
        System.out.print("\nDo you want to submit the exam now manually? (yes/no): ");
        String submitChoice = scanner.next().toLowerCase();
        if (submitChoice.equals("yes")) {
            submitManually(score);
        } else {
            System.out.println("\nExam completed. Your score: " + score + "/" + questions.length);
        }
    }

    private void submitManually(int score) {
        System.out.println("\nExam submitted manually. Your score: " + score + "/" + questions.length);
    }

    private void submitAutomatically(int score) {
        System.out.println("\nTime's up! Automatically submitting the exam. Your score: " + score + "/" + questions.length);
    }
}
    
public class ExamPortal{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User1 currentUser = null;

        while (true) {
            System.out.println("\nOnline Examination System");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scanner.next();
                System.out.print("Enter password: ");
                String password = scanner.next();

                currentUser = new User1(username, password, "Default Profile");

                if (currentUser != null) {
                    System.out.println("Login successful. Welcome, " + currentUser.getUsername());

                    while (true) {
                        System.out.println("\nUser Profile: " + currentUser.getProfile());
                        System.out.println("Exam Menu:");
                        System.out.println("1. Start Exam");
                        System.out.println("2. Update Profile");
                        System.out.println("3. Logout");
                        System.out.print("Enter your choice: ");

                        int examChoice = scanner.nextInt();

                        if (examChoice == 1) {
                            Exam exam = new Exam();
                            exam.startExam(currentUser);
                            break;
                        } else if (examChoice == 2) {
                            System.out.print("Enter your new profile: ");
                            scanner.nextLine(); // Consume newline character
                            String newProfile = scanner.nextLine();
                            currentUser.updateProfile(newProfile);
                        } else if (examChoice == 3) {
                            System.out.println("Logging out.");
                            currentUser = null;
                            break;
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                    }
                } else {
                    System.out.println("Login failed. Please try again.");
                }
            } else if (choice == 2) {
                System.out.println("Thank You For Appreciating Us!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}