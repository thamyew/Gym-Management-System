/*
class Staff inherited from class People. Class administrator is used for the people works as the administrator in the gym
*/

package gp6.gymmanagementapp;

public class Staff extends People
{
    // Declaration of the private attributes/variables \\
    // staffID records the id number (number only) of the administrator
    // staffUsername records the username used by staff to login the system
    // staffPassword records the password used by staff to login the system
    private int staffID;
    private String staffUsername, staffPassword;
    
    // Constructors 
    // Default constructor which takes no argument and initialize all the variables with String datatype to null and int datatype to 0 (including superclass People)
    public Staff()
    {
        super();
        staffID = 0;
        staffUsername = null;
        staffPassword = null;
    }
    
    // Overloaded constructor which takes 8 arguments to store the value of variables such as staff id, staff username, staff password including the variables in superclass
    public Staff(String firstName, String lastName, String address, String phone, String email, int staffID, String staffUsername, String staffPassword)
    {
        super(firstName, lastName, address, phone, email);
        this.staffID = staffID;
        this.staffUsername = staffUsername;
        this.staffPassword = staffPassword;
    }
    
    // Mutator functions \\
    // Mutator function to set the staff id number
    public void setStaffID(int staffID)
    {
        this.staffID = staffID;
    }
    
    // Mutator function to set the staff username
    public void setStaffUsername(String staffUsername)
    {
        this.staffUsername = staffUsername;
    }
    
    // Mutator function to set the staff password
    public void setStaffPassword(String staffPassword)
    {
        this.staffPassword = staffPassword;
    }
    
    // Accessor functions \\
    // Accessor function to return staff id number
    public int getStaffID()
    {
        return staffID;
    }
    
    // Accessor function to return staff username
    public String getStaffUsername()
    {
        return staffUsername;
    }
    
    // Accessor function to return staff password
    public String getStaffPassword()
    {
        return staffPassword;
    }
    
    // Overridden abstract function in superclass
    @Override
    public String getID()
    {
        return "ST" + getStaffID();
    }
}