package gp6.gymmanagementapp;

public class Equipment 
{
    //private attributes
    private int equipmentID;
    private String equipmentName;
    
    //no-arg constructor
    public Equipment()
    {
        equipmentID = 0;
        equipmentName = null;    
    }

    ////constructor with parameter
    public Equipment(int equipmentID, String equipmentName)
    {
        this.equipmentID = equipmentID;
        this.equipmentName = equipmentName;
    }

    //mutator of equipmentID
    public void setEquipmentID(int equipmentID)
    {
        this.equipmentID = equipmentID;
    }

    //mutator of equipmentName
    public void setEquipmentName(String equipmentName)
    {
        this.equipmentName = equipmentName;
    }
    
    //accessor of equipmentID
    public int getEquipmentID()
    {
        return equipmentID;
    }

    //accessor of equipmentName
    public String getEquipmentName()
    {
        return equipmentName;
    }
}
