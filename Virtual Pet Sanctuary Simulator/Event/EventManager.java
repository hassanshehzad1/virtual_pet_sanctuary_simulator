package Event;

import Pet.Pet;
import Pet.PetManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class EventManager implements EventTriggerable {

    // Aggregation
    private ArrayList<Event> events;
private  PetManager petManager;
    private static final String FILE_NAME = "events.txt";
    // Constructors

    public EventManager(PetManager petManager) {
        events = new ArrayList<Event>();
         this.petManager = petManager;
        loadEventsFromFile();
    }

    // Methods 
    // Method to add a custom event
    public void addEvent(String eventType, String description, Pet affectedPet) {
        Event event = new Event(eventType, affectedPet, description, LocalDateTime.now());
        events.add(event);
        saveEventsToFile();
        System.out.println("Event added: " + event.getEventDetails());
    }

    // Display unresolved events
    public void displayUnresolvedEvents() {
        System.out.println("Unresolved Events..");
        for (Event event : events) {
            if (!event.isResolved()) {
                System.out.println(event.getEventDetails());
            }
        }
    }

    @Override
    public void triggerRandomEvent(ArrayList<Pet> pets) {
        if (pets.isEmpty()) {
            System.out.println("No pets available for random events.");
            return;
        }

        Random random = new Random();

        int randomIndex = random.nextInt(pets.size());
        Pet affectedPet = pets.get(randomIndex);

        // generate a random event 
        String[] eventTypes = {"Illness", "Escape", "Natural Disasters"};
        String eventType = eventTypes[random.nextInt(eventTypes.length)];

        // Description
        String description = "";

        // Create an event
        Event event = new Event(eventType, affectedPet, description, LocalDateTime.now());

        events.add(event);

        System.out.println("Random event Triggered: " + event.getEventDetails());

    }

    // Illness method
    @Override
    public void resolveEvent(Event event) {
        if (events.isEmpty()) {
            System.out.println("No events display");
        } else {
            System.out.println("All Events");
            for (Event ev : events) {
                System.out.println(ev);
            }
        }

    }

    //   Display Events
    @Override
    public void displayEvents() {
        if (events.isEmpty()) {
            System.out.println("No events to display.");
            return;
        }

        System.out.println("Displaying all events:");
        for (Event event : events) {
            System.out.println("- " + event.getEventDetails());
        }
    }

    // Get Unresolved Events
    @Override
    public ArrayList<Event> getUnresolvedEvent() {
        ArrayList<Event> unresolved = new ArrayList<Event>();

        for (Event event : events) {
            if (!event.isResolved()) {
                unresolved.add(event);
            }
        }

        return unresolved;
    }

    // Event by id
    public Event getEventById(int eventID) {

        if (eventID < 1 || eventID > events.size()) {
            return null;
        }

        return events.get(eventID - 1);
    }

    // Add Events to file
    public void saveEventsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Event event : events) {
                writer.write(
                        event.getEventType() + ","
                        + event.getAffectedPet().getPetName() + ","
                        + //  Store pet name
                        event.getDescription() + ","
                        + event.getDate() + ","
                        + event.isResolved()
                );
                writer.newLine();
            }
            System.out.println("Events saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving events: " + e.getMessage());
        }
    }

    public void loadEventsFromFile() {
        events.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String eventType = parts[0];
                    String petName = parts[1];  //  Load pet name
                    String description = parts[2];
                    LocalDateTime date = LocalDateTime.parse(parts[3]);
                    boolean isResolved = Boolean.parseBoolean(parts[4]);

                    Pet affectedPet = petManager.getPet(petName);  //  Get Pet object from PetManager

                    if (affectedPet != null) {
                        Event event = new Event(eventType, affectedPet, description, date);
                        event.setResolved(isResolved);
                        events.add(event);
                    }
                }
            }
            // System.out.println("Events loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading events: " + e.getMessage());
        }
    }



    // Resolve Event by id
    public boolean resolveEventByID(int eventID){
        if (eventID < 1 || eventID > events.size()) {
            return false;
        }
        events.get(eventID - 1).resolveEvent();
        saveEventsToFile(); // ✅ Save after resolving
        return true;
    }
}
