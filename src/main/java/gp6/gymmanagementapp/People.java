/*
class People is an abstract class used for polymorphism concept. There are three child classes for class People
which are class Administrator, class Trainer and class Customer
*/

package gp6.gymmanagementapp;

public abstract class People 
{
    // Declaration of the private attributes/variables \\
    // firstName records the given name of the people
    // lastName records the surname or family name of the people
    // address records the current home address of the people
    // phone records the phone number of the people
    // email records the current active email of the people
    private String firstName, lastName, address, phone, email;
    
    // Constructors \\
    // Default constructor which takes no argument and intialize all the private attributes to null
    public People()
    {
        firstName = null;
        lastName = null;
        address = null;
        phone = null;
        email = null;
    }
    
    // Overloaded constructor which takes five arguments to store the first name, last name, address, phone number and email of the people
    public People(String firstName, String lastName, String address, String phone, String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
    
    // Mutator functions \\
    // Mutator function to set the first name of the people
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    // Mutator function to set the last name of the people    
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    // Mutator function to set the home address of the people
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    // Mutator function to set the phone number of the people
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    // Mutator function to set the email of the people
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    // Accessor functions \\
    // Accessor function to return the first name of the people
    public String getFirstName()
    {
        return firstName;
    }
    
    // Accessor function to return the last name of the people
    public String getLastName()
    {
        return lastName;
    }
    
    // Accessor function to return the address of the people
    public String getAddress()
    {
        return address;
    }
    
    // Accessor function to return the phone number of the people
    public String getPhone()
    {
        return phone;
    }
    
    // Accessor function to return the email of the people
    public String getEmail()
    {
        return email;
    }
    
    // Abstract accessor function to return the id of the people used in gym in String data type
    public abstract String getID();
}