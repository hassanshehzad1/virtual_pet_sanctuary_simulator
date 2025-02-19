package Pet;

import User.CareTaker;
import User.User;
import User.UserManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PetManager {

    // Fields
    private ArrayList<Pet> petList;
    private UserManager userManager; // To assign pets to CareTakers
    private PetManager petManager;

    // Constructor
    public PetManager(UserManager userManager) {
        this.petList = new ArrayList<>();
        this.userManager = userManager;
        loadPetsFromFile("pets.txt");          //  Load pets at startup
        loadAssignmentsFromFile("assignments.txt"); //  Load assignments
    }

    //  Add Pet
    public void addPet(Pet pet) {
        if (pet == null) {
            System.out.println(" Cannot add a null pet.");
            return;
        }

        if (isDuplicate(pet.getPetName())) {
            System.out.println(" Pet " + pet.getPetName() + " already exists.");
            return;
        }

        petList.add(pet);
        System.out.println(" Pet " + pet.getPetName() + " added.");
        savePetsToFile("pets.txt"); //  Save changes
    }

    //  Remove Pet
    public void removePet(Pet pet) {
        if (pet == null) {
            System.out.println(" Invalid pet.");
            return;
        }

        if (petList.remove(pet)) {
            System.out.println(" Pet " + pet.getPetName() + " removed.");
            savePetsToFile("pets.txt"); //  Save changes
        } else {
            System.out.println(" Pet not found.");
        }
    }

    public ArrayList<Pet> getAllPets() {
        return petList;
    }

    //  Get Pet by Name
    public Pet getPet(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println(" Invalid pet name.");
            return null;
        }

        for (Pet pet : petList) {
            if (pet.getPetName().equalsIgnoreCase(name)) {
                return pet;
            }
        }
        System.out.println(" No pet found with name: " + name);
        return null;
    }

    //  Select Pet Using ID
    public Pet selectPet() {
        if (petList.isEmpty()) {
            System.out.println(" No pets available.");
            return null;
        }

        displayAllPets();

        Scanner input = new Scanner(System.in);
        System.out.print("🔹 Enter Pet ID to Select: ");
        int petIndex = input.nextInt();

        if (petIndex > 0 && petIndex <= petList.size()) {
            return petList.get(petIndex - 1);
        } else {
            System.out.println(" Invalid selection.");
            return null;
        }
    }

    //  Assign Pet to CareTaker
    public void assignPetToCareTaker(CareTaker careTaker, Pet pet) {
        if (careTaker == null || pet == null) {
            System.out.println(" Invalid CareTaker or Pet.");
            return;
        }

        if (careTaker.isAssignedToPet(pet)) {
            System.out.println(" Pet is already assigned.");
            return;
        }

        careTaker.addAssignedPet(pet);
        // System.out.println(" Assigned " + pet.getPetName() + " to " + careTaker.getUserName());
        saveAssignmentsToFile("assignments.txt");  //  Save changes
    }

    //  Get Pets for Sale
    public ArrayList<Pet> getPetsForSale() {
        ArrayList<Pet> petsForSale = new ArrayList<>();
        for (Pet pet : petList) {
            if (pet.getPetForSale()) {
                petsForSale.add(pet);
            }
        }
        return petsForSale;
    }

    //  Get Total Pet Count
    public int getPetCount() {
        return petList.size();
    }

    //  Display All Pets
    public void displayAllPets() {
        if (petList.isEmpty()) {
            System.out.println(" No pets available.");
            return;
        }

        System.out.println("\n===  All Pets in Sanctuary ===");
        for (int i = 0; i < petList.size(); i++) {
            System.out.println((i + 1) + ". " + petList.get(i));
        }
    }

    //  Save Pets to File
    public void savePetsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Pet pet : petList) {
                writer.write(
                        pet.getPetId() + ","
                        + pet.getPetName() + ","
                        + pet.getPetAge() + ","
                        + pet.getPetType() + ","
                        + pet.getPetHunger() + ","
                        + pet.getPetHealth() + ","
                        + pet.getPetMood() + ","
                        + pet.getPetPrice() + ","
                        + pet.getPetForSale()
                );
                writer.newLine();
            }
            System.out.println(" Pets saved successfully.");
        } catch (IOException e) {
            System.out.println(" Error saving pets: " + e.getMessage());
        }
    }

    //  Load Pets from File
    public void loadPetsFromFile(String fileName) {
        petList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String petType = parts[3];
                    int hunger = Integer.parseInt(parts[4]);
                    int health = Integer.parseInt(parts[5]);
                    int mood = Integer.parseInt(parts[6]);
                    double price = Double.parseDouble(parts[7]);
                    boolean isForSale = Boolean.parseBoolean(parts[8]);

                    Pet pet;
                    switch (petType.toLowerCase()) {
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

                    pet.setPetHunger(hunger);
                    pet.setPetHealth(health);
                    pet.setPetMood(mood);
                    pet.setPetType(petType);

                    petList.add(pet);
                }
            }
            // System.out.println(" Pets loaded successfully.");
        } catch (IOException e) {
            System.out.println(" Error loading pets: " + e.getMessage());
        }
    }

    //  Save Assignments to File
    public void saveAssignmentsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : userManager.getUsers()) {
                if (user instanceof CareTaker) {
                    CareTaker careTaker = (CareTaker) user;
                    for (Pet pet : careTaker.getAssignedPets()) {
                        writer.write(careTaker.getEmail() + "," + pet.getPetName());
                        writer.newLine();
                    }
                }
            }
            // System.out.println(" Assignments saved successfully.");
        } catch (IOException e) {
            System.out.println(" Error saving assignments: " + e.getMessage());
        }
    }

    //  Load Assignments from File
    public void loadAssignmentsFromFile(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println(" Assignments file not found. Creating new one...");
            return;  // Don't attempt to read if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String careTakerEmail = parts[0];
                    String petName = parts[1];

                    User user = userManager.getUser(careTakerEmail);
                    Pet pet = getPet(petName);

                    if (user instanceof CareTaker && pet != null) {
                        assignPetToCareTaker((CareTaker) user, pet);
                    }
                }
            }
            // System.out.println(" Assignments loaded successfully.");
        } catch (IOException e) {
            System.out.println(" Error loading assignments: " + e.getMessage());
        }
    }

    //  Check Duplicate Pet Name
    public boolean isDuplicate(String name) {
        for (Pet pet : petList) {
            if (pet.getPetName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    //  Add this method in UserManager to allow setting PetManager later
    public void setPetManager(PetManager petManager) {
        this.petManager = petManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }


    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    
}
