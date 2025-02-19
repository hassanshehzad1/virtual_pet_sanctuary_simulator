package Sanctuary;

import Event.EventManager;
import Guest.Guest;
import Pet.PetManager;
import User.UserManager;

import java.io.*;
import java.util.Scanner;

// Main System Controller
public class Sanctuary {

    // Static Fields
    private static double funds;

    // Composition 
    private UserManager userManager;
    private PetManager petManager;
    private EventManager eventManager;
    private Guest guest;

    // Constructor
    public Sanctuary(double f) {
        loadFunds(); // Load funds from file
        funds += f; // Add initial funds
    
        //  Create objects in correct order
        this.userManager = new UserManager(null, null); // Temporary nulls
        this.petManager = new PetManager(userManager);  // Pass userManager
        this.eventManager = new EventManager(petManager); 
        this.guest = new Guest(petManager, this);
    
        //  Now set the correct references
        userManager = new UserManager(petManager, eventManager);
        petManager.setUserManager(userManager);  // Set the UserManager reference properly
    
        // Load users at startup
        userManager.loadUsers("users.txt");
    }
    
    

    //  Add funds
    public static void addFunds(double f) {
        if (f > 0) {
            funds += f;
            saveFunds(); //  Save funds after updating
        } else {
            System.out.println("Amount to add must be greater than 0.");
        }
    }

    //  Get funds
    public static double getFunds() {
        if (funds < 1) {
            System.out.println("Funds are low. Consider adding more funds.");
        }
        return funds;
    }

    //  Save funds to file
    public static void saveFunds() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("funds.txt"))) {
            writer.write(String.valueOf(funds));
        } catch (IOException e) {
            System.out.println("Error saving funds: " + e.getMessage());
        }
    }

    //  Load funds from file
    public static void loadFunds() {
        try (BufferedReader reader = new BufferedReader(new FileReader("funds.txt"))) {
            funds = Double.parseDouble(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("No previous funds found. Defaulting to $0.");
            funds = 0;
        }
    }

    //  Display Main Menu System
    public void displayMenu() {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n<------ Welcome to the 'Virtual Pet Sanctuary Simulator' ------>");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Guest (View or Buy Pets)");
            System.out.println("4. Run Daily Updates (Trigger Random Events)"); 
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            choice = input.nextInt();
            input.nextLine();  // Clear buffer

            switch (choice) {
                case 1:
                    userManager.loginMenu();
                    break;
                case 2:
                    userManager.registerMenu();
                    break;
                case 3:
                    guest.guestMenu();
                    break;
                    case 4:
                    runDailyUpdates(); //  RANDOM EVENTS WILL TRIGGER
                    break;
                case 0:
                    System.out.println("Saving funds and exiting...");
                    saveFunds(); //  Save funds before exiting
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        input.close(); //  Close scanner to prevent memory leaks
    }

    //  Run Daily Updates (Triggers Random Events)

     public void runDailyUpdates() {
        System.out.println("\nRunning Daily Updates...");
        eventManager.triggerRandomEvent(petManager.getAllPets()); //  Trigger random events
        System.out.println("Daily updates completed.");
    }
}
