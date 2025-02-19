package Pet;

public abstract class Pet implements InteractPet{

    // Static 
    protected int petCount = 0;
    // Encapsulation
    protected int petId;
    protected String petName;
    protected String type;
    protected boolean isForSale;
    protected int age;
    protected double price;
    protected int mood;
    protected int hunger;
    protected int health;

    // Constructors
    Pet(int petId, String petName, String type, boolean isForSale, int age, double price) {
        this.petId = petId;
        this.petName = petName;
        this.isForSale = isForSale;
        this.age = age;
        this.price = price;
        this.mood = 50;
        this.hunger = 80;
        this.health = 100;
        this.type = type;
        petCount++;
    }

// Setters 
    public void setPetHunger(int hunger) {
        this.hunger = Math.max(hunger, 0);
    }

    public void setPetHealth(int health) {
        this.health = Math.min(health, 100);
    }

    public void setPetMood(int mood) {
        this.mood = Math.min(mood, 100);
    }

    public void setPetForSale(boolean isForSale) {
        this.isForSale = isForSale;
    }

    public void setPetType(String type) {
        this.type = type;
    }

    // Getters
    public int getPetId() {
        return petId;
    }

    public String getPetName() {
        return petName;
    }

    public String getPetType() {
        return type;
    }

    public int getPetMood() {
        return mood;
    }

    public int getPetAge() {
        return age;
    }

    public double getPetPrice() {
        return price;
    }

    public int getPetHealth() {
        return health;
    }

    public int getPetHunger() {
        return hunger;
    }

    public boolean getPetForSale() {
        return isForSale;
    }

    // Display Status
    @Override
    public String toString() {
        return "(ID: " + petId + " Name: " + petName + ", Type: " + type + ", isForSale: " + isForSale + ", Age: " + age + ", Price: " + price + ", Health: " + health + ", Mood: " + mood + ", Hunger: " + hunger + ")";
    }

    // Abstract
    public abstract void eat();

    public abstract void play();

    public abstract void heal();
}
