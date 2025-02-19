# virtual_pet_sanctuary_simulator
A java based Object-Oriented Programming project that simulates a Virtual Pet sanctuary Where users can adopt, manage and care for virtual pets, events like illness, escape  and natural disasters add unpredictablity to the simulation


<h2>Featurs and functionality</h2>
<h3>User Managment System</h3>
<ul>
  <li>User can register </li>
  <li>Login System for careTakers and Managers </li>
  <li>Role based System </li>
  <li>CareTaker manage and care for pets. </li>
  <li>Guest can browse and buy pets without an account. </li>
  <li>Managers handle events pet, assignement, and events. </li>
  <li>User is  an abstract class  extended by caretaker and manager.</li>
</ul>
<h3>Pet Managment System</h3>
<ul>
  <li>Multiple pets types(Dog, Horse, Lion, Rabbit). </li>
  <li>Pets have attribute name, age , type, Hunger, mood price, health</li>
  <li>CareTakers can feed, play with and heal assigned pets</li>
  <li>Pet manager allowing adding , removing and updating pets.</li>
  <li>Pet is an abstract class exented for specific pet types. </li>

</ul>
<h3>Guest Managment System</h3>
<ul>
  <li>Guest can view pets for sale without registrations. </li>
  <li>Only pet marked as isForSale = True is available for sale.</li>


</ul>
<h3>Sanctuary finacial  System and updates.</h3>
<ul>
  <li>Managers can manage sanctuary funds. </li>
  <li>Funds can be used as buy pets.</li>
  <li>Playground (Improve pet hpppines).</li>
  <li>Clinic (Increase pet health).</li>
  <li>Feeding Station (Reducese pet hunger).</li>


</ul>
<h3>Event management System.</h3>
<ul>
  <li>Random events affects pets. </li>
  <li>Illness: (Pet health deacreases).</li>
  <li> Escape: An unhappy pet leaves the sanctuary.</li>
  <li>Natural Disasters Affects multiple pets.</li>
  <li>Eventmanager stores and manage events.</li>
  <li>CareTakers can resolve events for assigned pets.</li>


</ul>
<h3>File Handling for data persistence</h3>
<ul>
  <li>users.txt->Store registered users. </li>
  <li>pets.txt->stores pets details.</li>
  <li>Events.txt-> Stores events history.</li>
  
</ul>
  
<h3>Technologies Used</h3>
<ul>
  <li>Java(JDK + 17)-> Core programming language </li>
  <li>OOP Principles ->Encapsulation, inheritance , polymorphism, Abstraction, Agggregtion, Composition.</li>
  <li>File Hanlding ->Persientnce of users, pets, and events.</li>
  <li>Git and Github ->Version control and Collaboration.</li>
  
</ul>

📦 Virtual-Pet-Sanctuary-Simulator
 ┣ 📂 src
 ┃ ┣ 📂 Sanctuary
 ┃ ┃ ┣ 📜 Sanctuary.java  // Main System Controller
 ┃ ┃ ┣ 📜 Main.java       // Entry point of program
 ┃ ┣ 📂 User
 ┃ ┃ ┣ 📜 User.java       // Abstract Base Class
 ┃ ┃ ┣ 📜 Manager.java    // Handles finances, upgrades, reports
 ┃ ┃ ┣ 📜 CareTaker.java  // Manages pet interactions
 ┃ ┃ ┣ 📜 UserManager.java // Handles login & registration
 ┃ ┃ ┣ 📜 Interactable.java // Interface for user actions
 ┃ ┣ 📂 Pet
 ┃ ┃ ┣ 📜 Pet.java        // Abstract Base Class for all pets
 ┃ ┃ ┣ 📜 Dog.java        // Concrete Pet Class
 ┃ ┃ ┣ 📜 Rabbit.java     // Concrete Pet Class
 ┃ ┃ ┣ 📜 Horse.java      // Concrete Pet Class
 ┃ ┃ ┣ 📜 Lion.java       // Concrete Pet Class
 ┃ ┃ ┣ 📜 PetManager.java // Handles pet creation & management
 ┃ ┃ ┣ 📜 InteractPet.java // Interface for pet actions
 ┃ ┣ 📂 Event
 ┃ ┃ ┣ 📜 Event.java      // Event class (Illness, Escape, Disaster)
 ┃ ┃ ┣ 📜 EventManager.java // Handles event creation & resolution
 ┃ ┃ ┣ 📜 EventTriggerable.java // Interface for event behavior
 ┃ ┣ 📂 Guest
 ┃ ┃ ┣ 📜 Guest.java      // Allows non-registered users to buy pets
 ┣ 📂 data
 ┃ ┣ 📜 users.txt         // Stored user accounts
 ┃ ┣ 📜 pets.txt          // Stored pet data
 ┃ ┣ 📜 events.txt        // Stored event history

 <h2>Set up and Installation</h2>
git clone https://github.com/your-username/Virtual-Pet-Sanctuary.git
cd Virtual-Pet-Sanctuary

 <h2>Compile And Run</h2>
javac -d bin src/**/*.java
java -cp bin Main


<h2>📄 License</h2>
This project is open-source under the MIT License.

<h2>OOP Principles Used</h2>
1️ Encapsulation 
 Private fields (e.g., username, password, hunger)
 Getters/Setters for controlled access

2️ <h2> Inheritance </h2>
 User → Parent class of Manager & Caretaker
Pet → Parent class of Dog, Rabbit, Horse, Lion

3️ <h2> Polymorphism </h2>
 interactWithPet() (User Subclass) behaves differently for Manager vs CareTaker
 play(), eat(), heal() (Pet Subclasses) overridden in Dog, Rabbit, etc.

4️  <h2>Composition </h2>
 Sanctuary contains UserManager, PetManager, and EventManager
 UserManager manages a list of users

5️ <h2> Aggregation </h2>
 PetManager manages a list of pets
 EventManager triggers events on pets

  
