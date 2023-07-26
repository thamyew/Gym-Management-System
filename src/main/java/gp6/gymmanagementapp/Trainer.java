/*
class Trainer inherited from class People. Class Trainer is used for the people works as the trainer in the gym
*/
package gp6.gymmanagementapp;

public class Trainer extends People
{
    // Declaration of the private attributes/variables \\
    // trainerID records the id number (number only) of the trainer
    // trainerUsername records the username used by trainer to login the system
    // trainerPassword records the password used by trainer to login the system
    private int trainerID;
    private String trainerUsername, trainerPassword;
    
    // Constructors \\
    // Default constructor which takes no argument and initialize all the variables with String datatype to null and int datatype to 0 (including superclass People)
    public Trainer()
    {
        super();
        trainerID = 0;
        trainerUsername = null;
        trainerPassword = null;
    }
    
    // Overloaded constructor which takes 8 arguments to store the value of variables such as trainer id, trainer username, trainer password including the variables in superclass
    public Trainer(String firstName, String lastName, String address, String phone, String email, int trainerID, String trainerUsername, String trainerPassword)
    {
        super(firstName, lastName, address, phone, email);
        this.trainerID = trainerID;
        this.trainerUsername = trainerUsername;
        this.trainerPassword = trainerPassword;
    }
    
    // Mutator functions \\
    // Mutator function to set trainer ID number
    public void setTrainerID(int trainerID)
    {
        this.trainerID = trainerID;
    }
    
    // Mutator function to set trainer username
    public void setTrainerUsername(String trainerUsername)
    {
        this.trainerUsername = trainerUsername;
    }
    
    // Mutator function to set trainer password
    public void setTrainerPassword(String trainerPassword)
    {
        this.trainerPassword = trainerPassword;
    }
    
    // Accessor functions \\
    // Accessor function to return trainer ID number
    public int getTrainerID()
    {
        return trainerID;
    }
    
    // Accessor function to return trainer username
    public String getTrainerUsername()
    {
        return trainerUsername;
    }
    
    // Accessor function to return trainer password
    public String getTrainerPassword()
    {
        return trainerPassword;
    }
    
    // Overridden abstract function in superclass
    @Override
    public String getID()
    {
        return "TR" + trainerID;
    }
}