package Event;
// Event file to handle events

import java.time.LocalDateTime;
import Pet.Pet;

public class Event {

    // Fields
    private String eventType;
    private LocalDateTime date;
    private String description;
    private Pet affectedPet;
    private boolean isResolved;

// Constructors
    public Event(String eventType, Pet affectedPet, String description, LocalDateTime date) {
        this.eventType = eventType;
        this.affectedPet = affectedPet;
        this.description = description;
        this.date = date;
        this.isResolved = false; //By Default is always false
    }

// Methods
// Check event details
    // Method to get event details
    public String getEventDetails() {
        return "Event Type: " + eventType + "\n"
                + "Date: " + date.toString() + "\n"
                + "Description: " + description + "\n"
                + "Affected Pet: " + (affectedPet != null ? affectedPet.getPetName() : "None") + "\n"
                + "Resolved: " + (isResolved ? "Yes" : "No");
    }

// Check pet is in citical
    public boolean isCritical() {

        return eventType.equalsIgnoreCase("Illness") || eventType.equalsIgnoreCase("Escape");
    }

// Resolve the event
    public void resolveEvent() {
        if (!isResolved) {
            isResolved = true;
            System.out.println("Event '" + eventType + "' has been resolved.");
        } else {
            System.out.println("Event '" + eventType + "' is already resolved.");
        }
    }

// Get Event type
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    // Local Date time
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // Get local date time
    public LocalDateTime getDate() {
        return date;
    }

    // Get Description
    public String getDescription() {
        return description;
    }

    public Pet getAffectedPet() {
        return affectedPet;
    }

    public void setAffectedPet(Pet affectedPet) {
        this.affectedPet = affectedPet;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        this.isResolved = resolved;
    }

    @Override
    public String toString() {
        return "Event: " + eventType + " | Date: " + date + " | Resolved: " + (isResolved ? "Yes" : "No");
    }
}
