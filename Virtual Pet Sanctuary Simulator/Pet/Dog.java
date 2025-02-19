package Pet;

public class Dog extends Pet{

   
    public Dog(int id, String name, boolean isForSale, int age, double price){
        super(id,name,"dog",isForSale,age,price);

    }
    
    
    // Overrides
    @Override
    public void eat(){
        setPetHunger(getPetHunger() - 10);
        System.out.println(getPetName()  + "(Dog) ate food. Hunger reduced to: "+ getPetHunger());
    }

    @Override
    public void play(){
        setPetMood(getPetMood() + 12);
        System.out.println(getPetName() + "(Dog) Plays mood increased"+ getPetMood());
    }

    @Override
    public void heal(){
             setPetHealth(getPetHealth() + 16);
             System.out.println(getPetName() +"(Dog) health increase "+ getPetHealth());
    }
    
}
