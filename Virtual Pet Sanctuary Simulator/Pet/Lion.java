package Pet;

public class Lion extends Pet{

   
    public Lion(int id, String name, boolean isForSale, int age, double price){
        super(id,name,"lion",isForSale,age,price);

    }
    
    
    // Overrides
    @Override
    public void eat(){
        setPetHunger(getPetHunger() - 10);
        System.out.println(getPetName()  + "(Lion) ate food. Hunger reduced to: "+ getPetHunger());
    }

    @Override
    public void play(){
        setPetMood(getPetMood() + 12);
        System.out.println(getPetName() + "(Lion) Plays mood increased"+ getPetMood());
    }

    @Override
    public void heal(){
             setPetHealth(getPetHealth() + 16);
             System.out.println(getPetName() +"(lion) health increase "+ getPetHealth());
    }
    
}
