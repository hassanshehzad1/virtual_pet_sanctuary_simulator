package User;

import Pet.*;
import Event.Event;
import Event.EventManager;
import java.util.ArrayList;
import java.util.Scanner;

// Concrete class inherits User
public class CareTaker extends User {

    // Fields
    private ArrayList<Pet> assignedPets; //  CareTaker can have assigned pets

    public CareTaker(String userName, String email, String password, int age, char role) {
        super(userName, password, email, age, role);
        this.assignedPets = new ArrayList<>(); // Initialize assigned pets list
    }

    //  Assign a Pet to the CareTaker
    public void assignPetToCareTaker(Pet pet) {
        if (pet == null) {
            System.out.println("Invalid pet selection.");
            return;
        }
        assignedPets.add(pet);
        System.out.println("Pet " + pet.getPetName() + " is now assigned to " + super.getUserName());
    }

    //  Interact with Pet
    @Override
    public void interactWithPet(Pet pet) {
        System.out.println("CareTaker: " + super.getUserName() + " is taking care of pet: " + pet.getPetName());
        feedPet(pet);
        playWithPet(pet);
        healPet(pet);
    }

    //  Feed pet
    public void feedPet(Pet pet) {
        if (pet == null) {
            System.out.println("Invalid pet selection.");
            return;
        }
        int newHunger = Math.max(pet.getPetHunger() - 10, 0);
        pet.setPetHunger(newHunger);
        System.out.println("Fed: " + pet.getPetName() + ". New Hunger Level: " + pet.getPetHunger());
    }

    //  Play with Pet
    public void playWithPet(Pet pet) {
        if (pet == null) {
            System.out.println("Invalid pet selection.");
            return;
        }
        int newMood = Math.min(pet.getPetMood() + 10, 100);
        pet.setPetMood(newMood);
        System.out.println("Played with " + pet.getPetName() + ". New Mood Level: " + pet.getPetMood());
    }

    //  Heal pet
    public void healPet(Pet pet) {
        if (pet == null) {
            System.out.println("Invalid pet selection.");
            return;
        }
        int newHealth = Math.min(pet.getPetHealth() + 12, 100);
        pet.setPetHealth(newHealth);
        System.out.println("Healed: " + pet.getPetName() + ". New Health Level: " + pet.getPetHealth());
    }

    //  CareTaker menu (🔹 Added "Manage Events" Option)
    public void loadCareTakerMenu(PetManager petManager, EventManager eventManager) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== CareTaker Menu ===");
            System.out.println("1. Feed a Pet");
            System.out.println("2. Play with a Pet");
            System.out.println("3. Heal a Pet");
            System.out.println("4. Display All Pets");
            System.out.println("5. Manage Events"); //  Added option
            System.out.println("6. View Assigned Pets"); //  Added option
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            choice = input.nextInt();
            input.nextLine(); // Consume newline

            Pet selectedPet;
            switch (choice) {
                case 1:
                    selectedPet = petManager.selectPet();
                    if (selectedPet != null) {
                        feedPet(selectedPet);
                        petManager.savePetsToFile("pets.txt");
                    }
                    break;
                case 2:
                    selectedPet = petManager.selectPet();
                    if (selectedPet != null) {
                        playWithPet(selectedPet);
                        petManager.savePetsToFile("pets.txt");
                    }
                    break;
                case 3:
                    selectedPet = petManager.selectPet();
                    if (selectedPet != null) {
                        healPet(selectedPet);
                        petManager.savePetsToFile("pets.txt");
                    }
                    break;
                case 4:
                    petManager.displayAllPets();
                    break;
                case 5:
                    eventMenu(eventManager, petManager); //  Manage Events
                    break;
                case 6:
                    viewAssignedPets(); //  Manage Events
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (true);
    }

    //  Event Management Menu for CareTaker
    public void eventMenu(EventManager eventManager, PetManager petManager) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Event Management ===");
            System.out.println("1. View All Events");
            System.out.println("2. View Unresolved Events");
            System.out.println("3. Resolve an Event");
            System.out.println("0. Back to Main Menu");
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
                    resolveEvent(eventManager, petManager);
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        } while (choice != 0);
    }

    //  Resolve an Event for an Assigned Pet
    public void resolveEvent(EventManager eventManager, PetManager petManager) {
        if (assignedPets.isEmpty()) {
            System.out.println("No assigned pets for this CareTaker.");
            return;
        }

        Scanner input = new Scanner(System.in);
        System.out.println("\n=== Select a Pet to Resolve Events ===");
        for (int i = 0; i < assignedPets.size(); i++) {
            System.out.println((i + 1) + ". " + assignedPets.get(i).getPetName());
        }
        System.out.print("Enter Pet Number: ");
        int petChoice = input.nextInt();
        if (petChoice < 1 || petChoice > assignedPets.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        Pet selectedPet = assignedPets.get(petChoice - 1);

        ArrayList<Event> unresolvedEvents = eventManager.getUnresolvedEvent();
        boolean found = false;

        //  FIX: Compare pet name instead of reference
        for (Event event : unresolvedEvents) {
            if (event.getAffectedPet().getPetName().equalsIgnoreCase(selectedPet.getPetName())) {
                System.out.println(event.getEventDetails());
                event.resolveEvent();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No unresolved events for " + selectedPet.getPetName());
        }
    }

    public boolean isAssignedToPet(Pet pet) {
        return assignedPets.contains(pet);
    }

    public void addAssignedPet(Pet pet) {
        if (!isAssignedToPet(pet)) {
            assignedPets.add(pet);
            // System.out.println("Assigned pet: " + pet.getPetName());
        }
    }

    public ArrayList<Pet> getAssignedPets() {
        return assignedPets;
    }

    public void viewAssignedPets() {
        if (assignedPets.isEmpty()) {
            System.out.println(" No assigned pets.");
            return;
        }

        System.out.println("\n=== 🐾 Assigned Pets ===");
        for (Pet pet : assignedPets) {
            System.out.println("- " + pet.getPetName());
        }
    }

}
