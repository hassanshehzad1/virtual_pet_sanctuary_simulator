package Pet;

public class Rabbit extends Pet{

   
    public Rabbit(int id, String name, boolean isForSale, int age, double price){
        super(id,name,"rabbit",isForSale,age,price);

    }
    
    
    // Overrides
    @Override
    public void eat(){
        setPetHunger(getPetHunger() - 10);
        System.out.println(getPetName()  + "(Rabbit) ate food. Hunger reduced to: "+ getPetHunger());
    }

    @Override
    public void play(){
        setPetMood(getPetMood() + 12);
        System.out.println(getPetName() + "(Rabbit) Plays mood increased"+ getPetMood());
    }

    @Override
    public void heal(){
             setPetHealth(getPetHealth() + 16);
             System.out.println(getPetName() +"(Rabbit) health increase "+ getPetHealth());
    }
    
}
