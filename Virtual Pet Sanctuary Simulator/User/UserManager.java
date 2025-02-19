package User;

import Event.EventManager;
import Pet.PetManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {

    // Fields
    private ArrayList<User> usersLists; //  Dynamic storage for users
    private PetManager petManager; //  Aggregation (UserManager has PetManager)
    private EventManager eventManager; //  Store EventManager reference
    private static Manager managerInstance; //  Singleton Manager instance

    //  Constructor (Pass PetManager and EventManager)
    public UserManager(PetManager petManager, EventManager eventManager) {
        this.usersLists = new ArrayList<>();
        this.petManager = petManager;
        this.eventManager = eventManager; //  Store EventManager reference
        loadUsers("users.txt"); //  Load users at startup
    }

    //  Register a New User
    public void register(String userName, String email, String password, int age, char role) {
        if (isEmailRegistered(email)) {
            System.out.println("Error! Email is already registered.");
            return;
        }

        User user = null;
        if ((role == 'M' || role == 'm') && managerInstance != null) {
            System.out.println("A Manager already exists! Cannot register another.");
            return;
        }

        if (role == 'C' || role == 'c') {
            user = new CareTaker(userName, email, password, age, role);
        } else if (role == 'M' || role == 'm') {
            user = new Manager(userName, email, password, age, role);
            managerInstance = (Manager) user; // Assign manager instance
        }

        if (user != null) {
            usersLists.add(user);
            saveUsers("users.txt");
            System.out.println(" Registration successful for " + role + ": " + userName);
        } else {
            System.out.println(" Error! User creation failed.");
        }
    }

    //  Login a User
    public User login(String email, String password, char role) {
        for (User user : usersLists) {
            if (user != null && user.getEmail().equalsIgnoreCase(email)
                    && user.getPassword().equals(password) && user.getRol() == role) {
                return user;
            }
        }
        System.out.println(" Invalid credentials. Please try again.");
        return null;
    }

    //  Get User by Email
    public User getUser(String email) {
        for (User user : usersLists) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    //  Load Users from File
    public void loadUsers(String fileName) {
        usersLists.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length == 5) {
                    String userName = values[0];
                    String email = values[1];
                    String password = values[2];
                    int age = Integer.parseInt(values[3]);
                    char role = values[4].charAt(0);

                    User user;
                    if (role == 'C' || role == 'c') {
                        user = new CareTaker(userName, email, password, age, role);
                    } else {
                        user = new Manager(userName, email, password, age, role);
                        managerInstance = (Manager) user;
                    }
                    usersLists.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println(" Error loading users: " + e.getMessage());
        }
    }

    //  Save Users to File
    public void saveUsers(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : usersLists) {
                writer.write(user.getUserName() + "," + user.getEmail() + "," + user.getPassword() + ","
                        + user.getAge() + "," + user.getRol());
                writer.newLine();
            }
            System.out.println(" Users saved successfully.");
        } catch (IOException e) {
            System.out.println(" Error saving users: " + e.getMessage());
        }
    }

    //  Check if Email is Already Registered
    public boolean isEmailRegistered(String email) {
        for (User user : usersLists) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    //  Get Total User Count
    public int getUserCount() {
        return usersLists.size();
    }

    //  Login Menu
    public void loginMenu() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n Login User");
        System.out.print(" Enter Email: ");
        String email = input.nextLine();

        while (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            System.out.print(" Invalid Email Format. Enter again: ");
            email = input.nextLine();
        }

        System.out.print(" Enter Password: ");
        String password = input.nextLine();

        while (password.length() < 8) {
            System.out.print(" Password must be at least 8 characters. Enter again: ");
            password = input.nextLine();
        }

        char role;
        do {
            System.out.print(" Enter Role (C for CareTaker, M for Manager): ");
            role = Character.toUpperCase(input.next().charAt(0));
        } while (role != 'C' && role != 'M');

        User user = login(email, password, role);

        if (user != null) {
            System.out.println(" Login Successful! Welcome, " + user.getUserName());

            if (user instanceof CareTaker) {
                ((CareTaker) user).loadCareTakerMenu(petManager, eventManager); //  Pass eventManager
            } else if (user instanceof Manager) {
                ((Manager) user).loadManagerMenu(petManager, eventManager); //  Pass eventManager
            }
        }
    }

    //  Register Menu
    public void registerMenu() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n🔹 Register New User");
        System.out.print(" Enter User Name: ");
        String userName = input.nextLine();

        System.out.print(" Enter Email: ");
        String email = input.nextLine();

        while (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            System.out.print(" Invalid Email Format. Enter again: ");
            email = input.nextLine();
        }

        System.out.print(" Enter Password: ");
        String password = input.nextLine();

        while (password.length() < 8) {
            System.out.print(" Password must be at least 8 characters. Enter again: ");
            password = input.nextLine();
        }

        System.out.print(" Enter Age: ");
        while (!input.hasNextInt()) {
            System.out.print(" Invalid age. Enter a number: ");
            input.next();
        }
        int age = input.nextInt();

        while (age < 18 || age > 50) {
            System.out.print(" Age must be between 18 and 50. Enter again: ");
            age = input.nextInt();
        }

        char role;
        do {
            System.out.print(" Enter Role (C for CareTaker, M for Manager): ");
            role = Character.toUpperCase(input.next().charAt(0));
        } while (role != 'C' && role != 'M');

        register(userName, email, password, age, role);
    }

    //  Get the Singleton Manager Instance
    public static Manager getManagerInstance() {
        return managerInstance;
    }

    public ArrayList<User> getUsers() {
        return usersLists;
    }

}
