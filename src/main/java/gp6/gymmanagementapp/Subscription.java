package gp6.gymmanagementapp;

import java.time.LocalDate;

public class Subscription 
{
    private int subscriptionID;
    private final Customer customer;
    private final ExercisePlan exercisePlan;    //attribute
    private final LocalDate datePurchased, dateEnd;
    private final float totalAmountPaid;
    
    public Subscription()                           //no-arg constructor
    {
        subscriptionID = 0;
        customer = null;
        exercisePlan = null;
        datePurchased = null;
        dateEnd = null;
        totalAmountPaid = 0;
    }
    
    public Subscription(int subscriptionID, Customer customer, ExercisePlan exercisePlan     //parameterized constructor
                        , LocalDate datePurchased, LocalDate dateEnd, float totalAmountPaid)
    {
        this.subscriptionID = subscriptionID;
        this.customer = customer;
        this.exercisePlan = exercisePlan;
        this.datePurchased = datePurchased;
        this.dateEnd = dateEnd;
        this.totalAmountPaid = totalAmountPaid;
    }
    
    public void setSubscriptionID(int subscriptionID)       //mutator for each private attribute
    {
        this.subscriptionID = subscriptionID;
    }
    
    public int getSubscriptionID()                  //accessor for each private attribute
    {
        return subscriptionID;
    }
    
    public Customer getCustomer()
    {
        return customer;
    }
    
    public ExercisePlan getExercisePlan()
    {
        return exercisePlan;
    }
    
    public LocalDate getDatePurchased()
    {
        return datePurchased;
    }
    
    public LocalDate getDateEnd()
    {
        return dateEnd;
    }
    
    public float getTotalAmountPaid()
    {
        return totalAmountPaid;
    }
}