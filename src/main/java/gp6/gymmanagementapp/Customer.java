/*
class Customer inherited from class People. Class Customer is used for the people acts as customer of the gym
There is a specialized subclass for class Customer, which is class Student. Class Student uses the concept of inheritance and inherited from class Customer
*/
package gp6.gymmanagementapp;

public class Customer extends People
{
    // Declaration of the private attributes/variables \\
    // customerID records the id number (number only) of the customer
    // customerUsername records the username used by customer to login the system
    // customerPassword records the password used by customer to login the system
    // typeOfCustomer records whether customer is a student or not (either Normal or Student)
    // **type of customer shouldn't be altered in future
    // **Changing type of customer required changing class
    private int customerID;
    private String customerUsername, customerPassword;
    
    // Constructors \\
    // Default constructor which takes no argument and initialize all the variables with String datatype to null and int datatype to 0 (including superclass People)
    public Customer()
    {
        super();
        customerID = 0;
        customerUsername = null;
        customerPassword = null;
    }
    
    // Overloaded constructor which takes 8 arguments to store the value of variables such as customer id, customer username, customer password including the variables in superclass
    public Customer(String firstName, String lastName, String address, String phone, String email, int customerID, String customerUsername, String customerPassword)
    {
        super(firstName, lastName, address, phone, email);
        this.customerID = customerID;
        this.customerUsername = customerUsername;
        this.customerPassword = customerPassword;
    }
    
    // Overloaded constructor which takes object Student as argument when a customer registered as student transform into normal customer
    public Customer(Student student)
    {
        super(student.getFirstName(), student.getLastName(), student.getAddress(), student.getPhone(), student.getEmail());
        customerID = student.getCustomerID();
        customerUsername = student.getCustomerUsername();
        customerPassword = student.getCustomerPassword();
    }
    
    // Mutator functions \\
    // Mutator function to set customer ID number
    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;
    }
    
    // Mutator function to set customer username
    public void setCustomerUsername(String customerUsername)
    {
        this.customerUsername = customerUsername;
    }
    
    // Mutator function to set customer password
    public void setCustomerPassword(String customerPassword)
    {
        this.customerPassword = customerPassword;
    }
    
    // Accessor functions \\
    // Accessor function to return customer ID number
    public int getCustomerID()
    {
        return customerID;
    }
    
    // Accessor function to return customer username
    public String getCustomerUsername()
    {
        return customerUsername;
    }
    
    // Accessor function to return customer password
    public String getCustomerPassword()
    {
        return customerPassword;
    }
    
    // Accessor function to return type of custoemr
    public String getTypeOfCustomer()
    {
        if (this instanceof Student)
            return "Student";
        
        return "Normal";
    }
    
    // Overridden abstract function in superclass
    @Override
    public String getID()
    {
        return "CT" + customerID;
    }
}