/*
class Student inherited from class Customer. Class Student is used for the customer who is currently a student in the gym
*/
package gp6.gymmanagementapp;

public class Student extends Customer
{
    // Declaration of the private attributes/variables \\
    // studentID records the student ID of a student in the institute
    // instituteName records the current educational institute the student in
    // expectedYearOfGraduation records the expected year the student is going to graduate from the institute
    private String studentID, instituteName;
    private int expectedYearOfGraduation;
    
    // Constructors \\
    // Default constructor which takes no argument and initialize all the variables with String datatype to null and int datatype to 0 (including superclass Customer and People)
    public Student()
    {
        super();
        studentID = null;
        instituteName = null;
        expectedYearOfGraduation = 0;
    }
    
    // Overloaded constructor which takes 11 arguments to store the value of variables such as student id, institute name, expected year of graduation including the variables in superclasses
    public Student(String firstName, String lastName, String address, String phone, String email, int customerID, String customerUsername, String customerPassword, String studentID, String instituteName, int expectedYearOfGraduation)
    {
        super(firstName, lastName, address, phone, email, customerID, customerUsername, customerPassword);
        this.studentID = studentID;
        this.instituteName = instituteName;
        this.expectedYearOfGraduation = expectedYearOfGraduation;
    }
    
    // Overloaded constructor which takes object Customer, student ID, institute name and expected year of graduation as arguments when an existing customer wants to change to STUDENT type account
    public Student(Customer customer, String studentID, String instituteName, int expectedYearOfGraduation)
    {
        super(customer.getFirstName(), customer.getLastName(), customer.getAddress(), customer.getPhone(), customer.getEmail(), customer.getCustomerID(), customer.getCustomerUsername(), customer.getCustomerPassword());
        this.studentID = studentID;
        this.instituteName = instituteName;
        this.expectedYearOfGraduation = expectedYearOfGraduation;
    }
    
    // Mutator functions \\
    // Mutator function to set student ID
    public void setStudentID(String studentID)
    {
        this.studentID = studentID;
    }
    
    // Mutator function to set institute name
    public void setInstituteName(String instituteName)
    {
        this.instituteName = instituteName;
    }
    
    // Mutator function to set expected year of graduation
    public void setExpectedYearOfGraduation(int expectedYearOfGraduation)
    {
        this.expectedYearOfGraduation = expectedYearOfGraduation;
    }
    
    // Accessor functions \\
    // Accessor function to return student ID
    public String getStudentID()
    {
        return studentID;
    }
    
    // Accessor function to return institute name
    public String getInstituteName()
    {
        return instituteName;
    }
    
    // Accessor function to return expected year of graduation
    public int getExpectedYearOfGraduation()
    {
        return expectedYearOfGraduation;
    }
}