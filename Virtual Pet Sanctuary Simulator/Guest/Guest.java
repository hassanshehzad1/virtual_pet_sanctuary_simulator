package Guest;

import Event.Event;
import Pet.Pet;
import Pet.PetManager;
import Sanctuary.Sanctuary;
import java.util.Scanner;

import Event.EventManager;
import java.util.ArrayList;

public class Guest {
// Fields

    private PetManager petManager;
    private Sanctuary sanctuary;

// Constructors
    public Guest(PetManager petManager, Sanctuary sanctuary) {
        this.petManager = petManager;
        this.sanctuary = sanctuary;
    }

//! Behaviours
// Guest Menu
    public void guestMenu() {
        Scanner input = new Scanner(System.in);

        System.out.println("Guest, Enter your name: ");
        String guestName = input.nextLine();

        System.out.println("\nWelcome, " + guestName + " What would you like do? ");

        int choice;

        do {
            System.out.println("\n===Guest Menu===");
            System.out.println("1. View pets for sale ");
            System.out.println("2. Buy a pet ");
            System.out.println("0. Back to main menu ");
            System.out.print("Enter choice: ");
            choice = input.nextInt();

            input.nextLine(); //clear buffer

            switch (choice) {
                case 1:
                    viewPetsForSale();
                    break;
                case 2:
                    buyPet();
                    break;
                case 0:
                    System.out.println("Returning to main menu....");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }

    // View Pets for sale
    public void viewPetsForSale() {
        System.out.println("\n=== Available pets for sale===");
        for (Pet pet : petManager.getPetsForSale()) {
            System.out.println(pet);
        }
    }

    // Buy Pets
    public void buyPet() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter pet name to buy: ");

        String petName = input.nextLine();

        Pet pet = petManager.getPet(petName);

        if (pet != null && pet.getPetForSale()) {
            System.out.println("Congratulations, you have bought" + pet.getPetName() + ".");

            petManager.removePet(pet);
        } else {
            System.out.println("Sorry, this pet is not available for sale....");
        }
    }


    // View pet events
    public void viewPetEvents(EventManager eventManager){
        System.out.println("Viewing pet-related events..");
        ArrayList<Event> unresolvedEvents = eventManager.getUnresolvedEvent();
        
        for(Event event:unresolvedEvents){
            System.out.println(event.getEventDetails());
        }
    }
}
