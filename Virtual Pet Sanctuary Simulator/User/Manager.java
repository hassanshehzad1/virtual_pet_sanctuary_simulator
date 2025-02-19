package User;

import Pet.*;
import Event.Event;
import Event.EventManager;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Manager class inherits User
public class Manager extends User {

    // Fields
    private double totalFunds = 0;

    //  Constructor
    public Manager(String userName, String email, String password, int age, char role) {
        super(userName, password, email, age, role);
        loadFunds(); // Load funds from file
    }

    //  Interact with Pet
    @Override
    public void interactWithPet(Pet pet) {
        System.out.println("Manager: " + super.getUserName() + " managing sanctuary for pet " + pet.getPetName());
    }

    //  Add funds (🔹 Changed to `public`)
    public void addFunds(double addFunds) {
        if (addFunds < 0) {
            System.out.println("Amount to add must be greater than 0");
        } else {
            totalFunds += addFunds;
            System.out.println("Funds added: $" + addFunds + " | Total Funds: $" + totalFunds);
            saveFunds();
        }
    }

    //  Save funds to file
    public void saveFunds() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("funds.txt"))) {
            writer.write(String.valueOf(totalFunds));
            System.out.println("Funds saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving funds: " + e.getMessage());
        }
    }

    //  Load funds from file
    public void loadFunds() {
        try (BufferedReader reader = new BufferedReader(new FileReader("funds.txt"))) {
            totalFunds = Double.parseDouble(reader.readLine());
            // System.out.println("Funds loaded successfully. Total Funds: $" + totalFunds);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading funds. Defaulting to $0.");
            totalFunds = 0;
        }
    }

    //  Generate report
    public void generateReport(PetManager petManager) {
        System.out.println("\n=== Sanctuary Report ===");
        System.out.println("Total Funds: $" + totalFunds);
        System.out.println("Total Pets: " + petManager.getPetCount());
        System.out.println("Pets Available for Sale: " + petManager.getPetsForSale().size());
    }

    //  Manager Menu
    public void loadManagerMenu(PetManager petManager, EventManager eventManager) {
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== Manager Menu ===");
            System.out.println("1: Add Funds");
            System.out.println("2: Purchase Upgrades");
            System.out.println("3: Generate Report");
            System.out.println("4: Add Pet to Sanctuary");
            System.out.println("5: Remove Pet from Sanctuary");
            System.out.println("6: Manage Events");
            System.out.println("7: Assign pet to careTaker");
            System.out.println("0: Logout");
            System.out.print("Enter choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to add: ");
                    double amount = input.nextDouble();
                    addFunds(amount);
                    break;
                case 2:
                    System.out.print("Enter upgrade to purchase (Playground/Clinic/Feeding Station): ");
                    input.nextLine();
                    String upgradeName = input.nextLine();
                    updatePurchase(upgradeName);
                    break;
                case 3:
                    generateReport(petManager);
                    break;
                case 4:
                    addPetToSanctuary(petManager);
                    break;
                case 5:
                    removePet(petManager);
                    break;
                case 6:
                    eventMenu(eventManager);
                    break;
                case 7:
                    assignPetToCareTaker(petManager);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    //  Add Pet to Sanctuary
    public void addPetToSanctuary(PetManager petManager) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nEnter Pet Details to Add");
        System.out.print("Pet Name: ");
        String name = input.nextLine();

        System.out.print("Pet Age: ");
        int age = input.nextInt();
        input.nextLine(); // Clear the buffer

        System.out.print("Pet Type (Dog, Rabbit, Horse, Lion): ");
        String type = input.nextLine();

        System.out.print("Hunger Level (0-100): ");
        int hunger = input.nextInt();

        System.out.print("Health Level (0-100): ");
        int health = input.nextInt();

        System.out.print("Mood Level (0-100): ");
        int mood = input.nextInt();

        System.out.print("Price: ");
        double price = input.nextDouble();

        System.out.print("Is for Sale? (true/false): ");
        boolean isForSale = input.nextBoolean();

        int id = petManager.getPetCount() + 1;

        Pet pet;
        switch (type.toLowerCase()) {
            case "dog":
                pet = new Dog(id, name, isForSale, age, price);
                break;
            case "rabbit":
                pet = new Rabbit(id, name, isForSale, age, price);
                break;
            case "horse":
                pet = new Horse(id, name, isForSale, age, price);
                break;
            case "lion":
                pet = new Lion(id, name, isForSale, age, price);
                break;
            default:
                System.out.println("Invalid pet type.");
                return;
        }
        petManager.addPet(pet);

    }

    //  Remove Pet from Sanctuary
    public void removePet(PetManager petManager) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter pet Name to remove:");
        Pet pet = petManager.getPet(input.nextLine());
        if (pet != null) {
            petManager.removePet(pet);
            System.out.println("Pet " + pet.getPetName() + " removed successfully.");
        } else {
            System.out.println("Pet not found.");
        }
    }

    //  Purchase Upgrades (🔹 Fixed Missing `break;`)
    public void updatePurchase(String upgradeType) {
        double cost;
        switch (upgradeType.toLowerCase()) {
            case "playground":
                cost = 5000;
                break;
            case "clinic":
                cost = 2000;
                break;
            case "feeding station":
                cost = 1000;
                break;
            default:
                System.out.println("Invalid upgrade. No purchase made.");
                return;
        }

        if (totalFunds >= cost) {
            totalFunds -= cost;
            System.out.println("Upgrade '" + upgradeType + "' purchased successfully. Remaining funds: $" + totalFunds);
            saveFunds();
        } else {
            System.out.println("Insufficient funds for " + upgradeType + ". Required: $" + cost + ", Available: $" + totalFunds);
        }
    }

    //  Event Management Menu
    public void eventMenu(EventManager eventManager) {
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== Event Management ===");
            System.out.println("1: View All Events");
            System.out.println("2: View Unresolved Events");
            System.out.println("3: Resolve an Event");
            System.out.println("0: Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    eventManager.displayEvents();
                    break;
                case 2:
                    eventManager.displayUnresolvedEvents();
                    break;
                case 3:
                    System.out.print("Enter ID of Event to resolve: ");
                    int eventID = input.nextInt();
                    boolean success = resolveEventById(eventManager, eventID);
                    System.out.println(success ? "Event resolved successfully!" : "Invalid event ID or already resolved.");
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        } while (choice != 0);
    }

    //  Resolve event by ID
    public boolean resolveEventById(EventManager eventManager, int eventID) {
        ArrayList<Event> events = eventManager.getUnresolvedEvent();
        if (eventID < 1 || eventID > events.size()) {
            return false;
        }
        events.get(eventID - 1).resolveEvent();
        return true;
    }

    public void assignPetToCareTaker(PetManager petManager) {
        Scanner input = new Scanner(System.in);

        System.out.println("\n=== Assign Pet to a CareTaker ===");

        //  Fetch all users and find CareTakers
        ArrayList<User> users = petManager.getUserManager().getUsers();
        ArrayList<CareTaker> careTakers = new ArrayList<>();

        for (User user : users) {
            if (user instanceof CareTaker) {
                careTakers.add((CareTaker) user);
            }
        }

        if (careTakers.isEmpty()) {
            System.out.println(" No available CareTakers.");
            return;
        }

        //  Display CareTakers
        System.out.println("Select a CareTaker:");
        for (int i = 0; i < careTakers.size(); i++) {
            System.out.println((i + 1) + ". " + careTakers.get(i).getUserName());
        }

        System.out.print("Enter CareTaker number: ");
        int caretakerIndex = input.nextInt();
        if (caretakerIndex < 1 || caretakerIndex > careTakers.size()) {
            System.out.println(" Invalid selection.");
            return;
        }
        CareTaker selectedCareTaker = careTakers.get(caretakerIndex - 1);

        //  Display available pets
        petManager.displayAllPets();
        System.out.print("Enter Pet Name to Assign: ");
        input.nextLine();
        String petName = input.nextLine();

        Pet pet = petManager.getPet(petName);
        if (pet == null) {
            System.out.println(" Pet not found.");
            return;
        }

        petManager.assignPetToCareTaker(selectedCareTaker, pet);
        System.out.println(" Pet " + pet.getPetName() + " has been assigned to " + selectedCareTaker.getUserName());
    }

}
