package User;

import Pet.Pet;

public abstract class User implements Interactable {

    // Static 
    private static int totalUser = 0;

    // Fields
    protected String userName;
    protected String password;
    protected String email;
    protected int age;
    protected char role;

    // Constructors
    public User(String userName, String password, String email, int age, char role) {

        // boolean result = CheckUser(email);
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.age = age;
        this.role = role;

        totalUser++;

    }

// Getters
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public char getRol() {
        return role;
    }

    public String getEmail() {
        return email;
    }
// Setters

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRol(char role) {
        this.role = role;
    }

    // Static
    public static int getTotalUser() {
        return totalUser;
    }
// Abstract
// Interact with pet

    @Override
    public abstract void interactWithPet(Pet pet);

    @Override
    public String toString() {
        return "\n___User Details__\n Name: " + userName + ", Email: " + email + ", Password: " + password + ", Age: " + age + ", Role: " + role;
    }

}
