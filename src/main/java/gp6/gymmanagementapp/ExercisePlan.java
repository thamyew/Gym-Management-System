package gp6.gymmanagementapp;

import java.time.LocalTime;

public class ExercisePlan 
{
    //Private attributes
    private int planID, expectedDuration;
    private String planDescription;
    private int[] exerciseDay;
    private float price;
    private LocalTime startTime, endTime;
    private final Trainer trainer;
    private Equipment equipment;
    
    //No-arg constructor
    public ExercisePlan()
    {
       planID =  0;
       trainer = null;
       equipment = null;
       planDescription = null;
       expectedDuration = 0;
       price = 0;
       startTime = null;
       endTime = null;
       exerciseDay = new int[0];
    }
    
    //Parameterized constructor
    public ExercisePlan(int planID, Trainer trainer, Equipment equipment, String planDescription, int expectedDuration, float price, LocalTime startTime, LocalTime endTime, int[] exerciseDay)
    {
        this.planID = planID;
        this.trainer = trainer;
        this.equipment = equipment;
        this.planDescription = planDescription;
        this.expectedDuration = expectedDuration;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.exerciseDay = new int[exerciseDay.length];
        System.arraycopy(exerciseDay, 0, this.exerciseDay, 0, exerciseDay.length);
    }
    
    //Mutator for planID
    public void setPlanID(int planID)
    {
        this.planID = planID;
    }
    
    //Mutator for planDescription
    public void setPlanDescription(String planDescription)
    {
        this.planDescription = planDescription;
    }
    
    //Mutator for ExpectedDuration
    public void setExpectedDuration(int expectedDuration)
    {
        this.expectedDuration = expectedDuration;
    }        
    
    //Mutator for price
    public void setPrice(float price)
    {
        this.price = price;
    }
    
    //Mutator for startTime and endTime
    public void setTime(LocalTime startTime, LocalTime endTime)
    {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    //Mutator for exerciseDay
    public void setExerciseDay(int[] exerciseDay)
    {
        this.exerciseDay = new int[exerciseDay.length];
        System.arraycopy(exerciseDay, 0, this.exerciseDay, 0, exerciseDay.length);
    }
    
    public void setEquipment(Equipment equipment)
    {
        this.equipment = equipment;
    }
    
    //Accessor for planID
    public int getPlanID()
    {
        return planID;
    }
    
    //Accessor for trainerID
    public Trainer getTrainer()
    {
        return trainer;
    }
    
    //Accessor for equipmentID
    public Equipment getEquipment()
    {
        return equipment;
    }
    
    //Accessor for planDescription
    public String getPlanDescription()
    {
        return planDescription;
    }
    
    //Accessor for expectedDuration
    public int getExpectedDuration()
    {
        return expectedDuration;
    }
    
    //Accessor for price
    public float getPrice()
    {
        return price;
    }
    
    //Method to return price based on the type of customer
    public float getPrice(Customer customer)
    {
        // Check if the Customer object passed is an instance of Student class
        if(customer instanceof Student)
            // If yes, return a discounted price
            return price * 0.85f;
        
        // If not, return normal price
        return price;
    }
    
    //Accessor for startTime
    public LocalTime getStartTime()
    {
        return startTime;
    }
    
    //Accessor for endTime
    public LocalTime getEndTime()
    {
        return endTime;
    }
    
    //Accessor for exerciseDay
    public int[] getExerciseDay()
    {
        return exerciseDay;
    }
}
