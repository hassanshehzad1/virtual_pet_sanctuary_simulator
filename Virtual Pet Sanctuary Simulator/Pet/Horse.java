package Pet;

public class Horse extends Pet{

   
    public Horse(int id, String name, boolean isForSale, int age, double price){
        super(id,name,"horse",isForSale,age,price);

    }
    
    
    // Overrides
    @Override
    public void eat(){
        setPetHunger(getPetHunger() - 10);
        System.out.println(getPetName()  + "(Horse) ate food. Hunger reduced to: "+ getPetHunger());
    }

    @Override
    public void play(){
        setPetMood(getPetMood() + 12);
        System.out.println(getPetName() + "(Horse) Plays mood increased"+ getPetMood());
    }

    @Override
    public void heal(){
             setPetHealth(getPetHealth() + 16);
             System.out.println(getPetName() +"(Horse) health increase "+ getPetHealth());
    }
    
}
