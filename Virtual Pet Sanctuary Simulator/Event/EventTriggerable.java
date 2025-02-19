package Event;

import java.util.ArrayList;


import Pet.Pet;

public  interface EventTriggerable {
    
    // Methods
    public abstract void triggerRandomEvent(ArrayList<Pet> pets);
    
    public abstract void resolveEvent(Event event);
    public abstract void displayEvents();
    public abstract ArrayList<Event> getUnresolvedEvent();
}
