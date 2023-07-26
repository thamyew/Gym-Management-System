/*
Main driver file
*/
package gp6.gymmanagementapp;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.regex.Pattern;

public class GymManagementApp 
{
    public static ArrayList<Staff> staffList = new ArrayList();
    public static ArrayList<Trainer> trainerList = new ArrayList();
    public static ArrayList<Customer> customerList = new ArrayList();
    public static ArrayList<Equipment> equipmentList = new ArrayList();
    public static ArrayList<ExercisePlan> exercisePlanList = new ArrayList();
    public static ArrayList<Subscription> subscriptionList = new ArrayList();
    
    public static String GymName = "xxx";
    public static String adminUsername = "admin";
    public static String adminPassword = "admin";
    
    // This function is used to delete all subscription that is related to the ongoing deleted customer
    public static void deleteAllRelatedSubscription(Customer customer)
    {
        // For loop to loop over subscription list
        for (int i = 0; i < subscriptionList.size(); i++)
        {
            // Check if the customer ID of the customer in the subscription is similar to the Customer object passed to this function
            if (subscriptionList.get(i).getCustomer().getCustomerID() == customer.getCustomerID())
            {
                // If is the same, remove the subscription
                subscriptionList.remove(i);
                // Decrement in i to loop over the same index as the object in the position is removed and the next element is pushed forward 
                i--;
            }
        }
    }
    
    // This function is used to delete all subscription that is related to the ongoing deleted exercise plan
    public static void deleteAllRelatedSubscription(ExercisePlan exercisePlan)
    {
        // For loop to loop over subscription list
        for (int i = 0; i < subscriptionList.size(); i++)
        {
            // Check if the exercise plan ID of the exercise plan in the subscription is similar to the ExercisePlan object passed to this function
            if (subscriptionList.get(i).getExercisePlan().getPlanID() == exercisePlan.getPlanID())
            {
                // If is the same, remove the subscription
                subscriptionList.remove(i);
                // Decrement in i to loop over the same index as the object in the position is removed and the next element is pushed forward 
                i--;
            }
        }
    }
    
    // This function is used to delete all exercise plan that is related to the ongoing deleted trainer
    public static void deleteAllRelatedExercisePlan(Trainer trainer)
    {
        // For loop to loop over exercise plan list
        for (int i = 0; i < exercisePlanList.size(); i++)
        {
            // Check if the trainer ID of the trainer in the exercise plan is similar to the Trainer object passed to this function
            if (exercisePlanList.get(i).getTrainer().getTrainerID() == trainer.getTrainerID())
            {
                // If is the same, calling deleteAllRelatedSubscription() and take the current looped exercise plan as argument
                // (**NOTES: This is because deleting the exercise plan also need to delete the subscription related to the exercise plan to make sure no data conflict)
                deleteAllRelatedSubscription(exercisePlanList.get(i));
                // Remove the exercise plan
                exercisePlanList.remove(i);
                // Decrement in i to loop over the same index as the object in the position is removed and the next element is pushed forward 
                i--;
            }
        }
    }
    
    // This function is used to delete all exercise plan that is related to the ongoing deleted equipment
    public static void deleteAllRelatedExercisePlan(Equipment equipment)
    {
        // For loop to loop over exercise plan list
        for (int i = 0; i < exercisePlanList.size(); i++)
        {
            // Check if the equipment ID of the equipment in the exercise plan is similar to the Equipment object passed to this function
            if (exercisePlanList.get(i).getEquipment().getEquipmentID() == equipment.getEquipmentID())
            {
                // If is the same, calling deleteAllRelatedSubscription() and take the current looped exercise plan as argument
                // (**NOTES: This is because deleting the exercise plan also need to delete the subscription related to the exercise plan to make sure no data conflict)
                deleteAllRelatedSubscription(exercisePlanList.get(i));
                // Remove the exercise plan
                exercisePlanList.remove(i);
                // Decrement in i to loop over the same index as the object in the position is removed and the next element is pushed forward
                i--;
            }
        }
    }
    
    // Function used to check whether the String input contains only number
    // The function returns true if all the characters in the String input are number
    // Is used when (can be used in every menu):
    // 1) validation of the inputs that should be in integer (eg. checkDataValid() function)
    public static boolean checkIsNumeric(String input)
    {
        // Return true if all the characters in the String input are number and vice-versa
        return input.chars().allMatch(Character::isDigit);
    }
    
    // Function used to check whether the String input contains only number
    // The function returns true if all the characters in the String input are number
    // Is used when (can be used in every menu):
    // 1) validation of the inputs that should be in integer (eg. checkDataValid() function)
    public static boolean checkIsPrice(String input)
    {
        // Return true if all the characters in the String input are number with point only and vice-versa
        String regex = "[0-9]+[\\.]?[0-9]*";
        return Pattern.matches(regex, input);
    }
    
    // This function is used to search the Staff based on the staff ID passed to the function and return the Staff object to the function calling this function
    // The function returns Staff object if the staff is found or returns null if the staff cannot be found
    public static Staff getStaff(String staffID)
    {
        // Enhanced for-loop to loop over trainerList
        for (Staff staff : staffList)
            // Check if the there is Staff object with ID that is equal to the staff ID passed to the function
            // return the Staff object if there exists Staff object with ID that is equal to the staff ID passed to the function
            if (staff.getID().equals(staffID))
                return staff;
        // return null if there is no Staff object with ID that is equal to the staff ID passed to the function
        return null;
    }
    
    // This function is used to search the trainer based on the trainer ID passed to the function and return the Trainer object to the function calling this function
    // The function returns Trainer object if the trainer is found or returns null if the trainer cannot be found
    public static Trainer getTrainer(String trainerID)
    {
        // Enhanced for-loop to loop over trainerList
        for (Trainer trainer : trainerList)
            // Check if the there is Trainer object with ID that is equal to the trainer ID passed to the function
            // return the Trainer object if there exists Trainer object with ID that is equal to the trainer ID passed to the function
            if (trainer.getID().equals(trainerID))
                return trainer;
        // return null if there is no Trainer object with ID that is equal to the trainer ID passed to the function
        return null;
    }
    
    // This function is used to search the customer based on the customer ID passed to the function and return the Customer object to the function calling this function
    // The function returns Customer object if the customer is found or returns null if the customer cannot be found
    public static Customer getCustomer(String customerID)
    {
        // Enhanced for-loop to loop over customerList
        for (Customer customer : customerList)
            // Check if the there is Customer object with ID that is equal to the customer ID passed to the function
            // return the Customer object if there exists Customer object with ID that is equal to the customer ID passed to the function
            if (customer.getID().equals(customerID))
                return customer;
        // return null if there is no Customer object with ID that is equal to the customer ID passed to the function
        return null;
    }
    
    // This function is used to search the equipment based on the equipment ID passed to the function and return the Equipment object to the function calling this function
    // The function returns Equipment object if the equipment is found or returns null if the equipment cannot be found
    public static Equipment getEquipment(int equipmentID)
    {
        // Enhanced for-loop to loop over equipmentList
        for (Equipment equipment : equipmentList)
            // Check if the there is equipment object with ID that is equal to the equipment ID passed to the function
            // return the Equipment object if there exists Equipment object with ID that is equal to the equipment ID passed to the function
            if (equipment.getEquipmentID() == equipmentID)
                return equipment;
        // return null if there is no Equipment object with ID that is equal to the equipment ID passed to the function
        return null;
    }
    
    // This function is used to search the exercise plan based on the exercise plan ID passed to the function and return the ExercisePlan object to the function calling this function
    // The function returns ExercisePlan object if the exercise plan is found or returns null if the exercise plan cannot be found
    public static ExercisePlan getExercisePlan(int exercisePlanID)
    {
        // Enhanced for-loop to loop over exercisePlanList
        for (ExercisePlan exercisePlan : exercisePlanList)
            // Check if the there is ExercisePlan object with ID that is equal to the exercise plan ID passed to the function
            // return the ExercisePlan object if there exists ExercisePlan object with ID that is equal to the exercise plan ID passed to the function
            if (exercisePlan.getPlanID() == exercisePlanID)
                return exercisePlan;
        // return null if there is no ExercisePlan object with ID that is equal to the exercise plan ID passed to the function
        return null;
    }
    
    // This function is used to search the subscription based on the subscription ID passed to the function and return the Subscription object to the function calling this function
    // The function returns Subscription object if the subscription is found or returns null if the subscription cannot be found
    public static Subscription getSubscription(int subscriptionID)
    {
        // Enhanced for-loop to loop over subscriptionList
        for (Subscription subscription : subscriptionList)
            // Check if the there is Subscription object with ID that is equal to the subscription ID passed to the function
            // return the Subscription object if there exists Subscription object with ID that is equal to the subscription ID passed to the function
            if (subscription.getSubscriptionID() == subscriptionID)
                return subscription;
        // return null if there is no Subscription object with ID that is equal to the subscription ID passed to the function
        return null;
    }

    // Staff Data Read & Write \\
    // Read the data of current recorded staff from a file named "StaffData.txt"
    // Assign the data into an ArrayList of Staff
    static void readStaffFile(ArrayList<Staff> arrayList)
    {
        try 
        {
            File myFile = new File("StaffData.txt");    // Specify the name of the file to be read
            
            try 
            {
                if (myFile.createNewFile())     // Check whether there exists the file already, if not, new file named "StaffData.txt" will be created
                    return;                     // Exit the function if the file is newly created to be used
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try (Scanner input = new Scanner(myFile)) // Prepare scanner to read the contents of the file and close the scanner after reading all contents
            {
                while (input.hasNextLine()) 
                {
                    // Temporary variables used to store the value used to create an Staff object
                    String firstName, lastName, address, email, username, phone, password;
                    int id;
                    
                    // Reading contents from the file and assign to each variable used to instantiate Staff object
                    firstName = input.nextLine();
                    lastName = input.nextLine();
                    address = input.nextLine();
                    phone = input.nextLine();
                    email = input.nextLine();
                    
                    id = input.nextInt();
                    input.nextLine();   // Used to clear "\n" in the file after reading an integer value
                    
                    username = input.nextLine();
                    password = input.nextLine();
                    
                    // Complete reading all the values required to instantiate an Staff object
                    // Adding a new Staff object to the ArrayList of Staff
                    arrayList.add(new Staff(firstName, lastName, address, phone, email, id, username, password));
                    
                    if (input.hasNextLine())    // Checking whether there is blank line in the next line
                        input.nextLine();       // Skip the blank line if there is one on the next line
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Write the data of staff list to a file named "StaffData.txt"
    static void writeStaffFile(ArrayList<Staff> arrayList)
    {
        try 
        {
            try (FileWriter writer = new FileWriter("StaffData.txt"))   // Prepare FileWriter to write the contents to the file named "StaffData.txt" and close the FileWriter object
            {
                for (int i = 0; i < arrayList.size(); i++)
                {
                    // Write data of staff into the file
                    writer.write(arrayList.get(i).getFirstName() + "\n");
                    writer.write(arrayList.get(i).getLastName() + "\n");
                    writer.write(arrayList.get(i).getAddress() + "\n");
                    writer.write(arrayList.get(i).getPhone() + "\n");
                    writer.write(arrayList.get(i).getEmail() + "\n");
                    writer.write(arrayList.get(i).getStaffID() + "\n");
                    writer.write(arrayList.get(i).getStaffUsername() + "\n");
                    writer.write(arrayList.get(i).getStaffPassword() + "\n");
                    
                    // Add a new blank line if the current Staff object used to write the data is not the final one
                    if (i != arrayList.size() - 1)
                        writer.write("\n");
                }
            }
        }
        catch (IOException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Trainer Data Read & Write \\
    // Read the data of current recorded trainers from a file named "TrainerData.txt"
    // Assign the data into an ArrayList of Trainer
    static void readTrainerFile(ArrayList<Trainer> arrayList)
    {
        try 
        {
            File myFile = new File("TrainerData.txt");  // Specify the name of the file to be read
            
            try 
            {
                if (myFile.createNewFile())     // Check whether there exists the file already, if not, new file named "TrainerData.txt" will be created
                    return;                     // Exit the function if the file is newly created to be used
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try (Scanner input = new Scanner(myFile)) // Prepare scanner to read the contents of the file and close the scanner object
            {
                while (input.hasNextLine()) 
                {
                    // Temporary variables used to store the value used to create a Trainer object
                    String firstName, lastName, address, email, username, phone, password;
                    int id;
                    
                    // Reading contents from the file and assign to each variable used to instantiate Trainer object
                    firstName = input.nextLine();
                    lastName = input.nextLine();
                    address = input.nextLine();
                    phone = input.nextLine();
                    email = input.nextLine();
                    
                    id = input.nextInt();
                    input.nextLine();       // Used to clear "\n" in the file after reading an integer value
                    
                    username = input.nextLine();
                    password = input.nextLine();
                    
                    // Complete reading all the values required to instantiate a Trainer object
                    // Adding a new Trainer object to the ArrayList of Trainer
                    arrayList.add(new Trainer(firstName, lastName, address, phone, email, id, username, password));
                    
                    if (input.hasNextLine())    // Checking whether there is blank line in the next line
                        input.nextLine();       // Skip the blank line if there is one on the next line
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Write the data of trainer list to a file named "TrainerData.txt"
    static void writeTrainerFile(ArrayList<Trainer> arrayList)
    {
        try 
        {
            try (FileWriter writer = new FileWriter("TrainerData.txt"))   // Prepare FileWriter to write the contents to the file named "TrainerData.txt" and close the FileWriter object
            {
                for (int i = 0; i < arrayList.size(); i++)
                {
                    // Write data of trainer into the file
                    writer.write(arrayList.get(i).getFirstName() + "\n");
                    writer.write(arrayList.get(i).getLastName() + "\n");
                    writer.write(arrayList.get(i).getAddress() + "\n");
                    writer.write(arrayList.get(i).getPhone() + "\n");
                    writer.write(arrayList.get(i).getEmail() + "\n");
                    writer.write(arrayList.get(i).getTrainerID() + "\n");
                    writer.write(arrayList.get(i).getTrainerUsername() + "\n");
                    writer.write(arrayList.get(i).getTrainerPassword() + "\n");
                    
                    // Add a new blank line if the current Trainer object used to write the data is not the final one
                    if (i != arrayList.size() - 1)
                        writer.write("\n");
                }
            }
        }
        catch (IOException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Customer Data Read & Write \\
    // Read the data of current recorded custommers from a file named "CustomernData.txt"
    // Assign the data into an ArrayList of Customer
    static void readCustomerFile(ArrayList<Customer> arrayList)
    {
        try 
        {
            File myFile = new File("CustomerData.txt");     // Specify the name of the file to be read
            
            try 
            {
                if (myFile.createNewFile())     // Check whether there exists the file already, if not, new file named "CustomerData.txt" will be created
                    return;                     // Exit the function if the file is newly created to be used
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try (Scanner input = new Scanner(myFile)) // Prepare scanner to read the contents of the file and close the scanner object
            {
                while (input.hasNextLine())     // Check if there still exists contents in the file
                {
                    // Temporary variables used to store the value used to create a Customer object
                    String firstName, lastName, address, email, username, phone, password, typeOfCustomer;
                    int id;
                    
                    // Reading contents from the file and assign to each variable used to instantiate Customer object
                    firstName = input.nextLine();
                    lastName = input.nextLine();
                    address = input.nextLine();
                    phone = input.next();
                    email = input.next();
                    
                    id = input.nextInt();
                    input.nextLine();       // Used to clear "\n" in the file after reading an integer value

                    username = input.nextLine();
                    password = input.nextLine();
                    
                    typeOfCustomer = input.nextLine();
                    
                    // Checking the the class used to instantiate object based on the value of variable typeOfCustomer
                    if (typeOfCustomer.equalsIgnoreCase("NORMAL"))    // Check if the type of customer is normal customer
                        // Complete reading all the values required to instantiate a Customer object
                        // Adding a new Customer object to the ArrayList of Customer
                        arrayList.add(new Customer(firstName, lastName, address, phone, email, id, username, password));
                    else    // The type of customer is STUDENT
                    {
                        // Temporary extra variables used to store the value used to create a Student object
                        String studentID, instituteName;
                        int yearOfGraduation;
                        
                        // Reading contents from the file and assign to each variable used to instantiate Student object
                        studentID = input.nextLine();
                        instituteName = input.nextLine();
                        
                        yearOfGraduation = input.nextInt();
                        input.nextLine();                       // Used to clear "\n" in the file after reading an integer value
                        
                        // Complete reading all the values required to instantiate a Student object
                        // Adding a new Student object to the ArrayList of Customer
                        arrayList.add(new Student(firstName, lastName, address, phone, email, id, username, password, studentID, instituteName, yearOfGraduation));
                    }
                    
                    if (input.hasNextLine())    // Checking whether there is blank line in the next line
                        input.nextLine();       // Skip the blank line if there is one on the next line
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Write the data of customer list to a file named "CustomerData.txt"
    static void writeCustomerFile(ArrayList<Customer> arrayList)
    {
        try 
        {
            try (FileWriter writer = new FileWriter("CustomerData.txt"))   // Prepare FileWriter to write the contents to the file named "CustomerData.txt" and close the FileWriter object
            {
                for (int i = 0; i < arrayList.size(); i++)
                {
                    // Write data of customer into the file
                    writer.write(arrayList.get(i).getFirstName() + "\n");
                    writer.write(arrayList.get(i).getLastName() + "\n");
                    writer.write(arrayList.get(i).getAddress() + "\n");
                    writer.write(arrayList.get(i).getPhone() + "\n");
                    writer.write(arrayList.get(i).getEmail() + "\n");
                    writer.write(arrayList.get(i).getCustomerID() + "\n");
                    writer.write(arrayList.get(i).getCustomerUsername() + "\n");
                    writer.write(arrayList.get(i).getCustomerPassword() + "\n");
                    writer.write(arrayList.get(i).getTypeOfCustomer() + "\n");
                    
                    // Write the following content if the object is instantiated from class Student
                    if (arrayList.get(i) instanceof Student student)
                    {
                        writer.write(student.getStudentID() + "\n");
                        writer.write(student.getInstituteName() + "\n");
                        writer.write(String.valueOf(student.getExpectedYearOfGraduation()) + "\n");
                    }
                    
                    // Add a new blank line if the current Customer/Student object used to write the data is not the final one
                    if (i != arrayList.size() - 1)
                        writer.write("\n");
                }
            }
        }
        catch (IOException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Equipment Data Read & Write \\
    // Read the data of current recorded equipment from a file named "EquipmentData.txt"
    // Assign the data into an ArrayList of Equipment
    static void readEquipmentFile(ArrayList<Equipment> arrayList)
    {
        try 
        {
            File myFile = new File("EquipmentData.txt");    // Specify the name of the file to be read
            
            try 
            {
                if (myFile.createNewFile())     // Check whether there exists the file already, if not, new file named "EquipmentData.txt" will be created
                    return;                     // Exit the function if the file is newly created to be used
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try (Scanner input = new Scanner(myFile)) // Prepare scanner to read the contents of the file and close the scanner object
            {
                while (input.hasNextLine())     // Check if there still exists contents in the file
                {
                    // Temporary variables used to store the value used to create an Equipment object
                    String equipmentName;
                    int id;
                    
                    // Reading contents from the file and assign to each variable used to instantiate Equipment object
                    id = input.nextInt();
                    input.nextLine();       // Used to clear "\n" in the file after reading an integer value
                    
                    equipmentName = input.nextLine();
                    
                    // Complete reading all the values required to instantiate an Equipment object
                    // Adding a new Equipment object to the ArrayList of Equipment
                    arrayList.add(new Equipment(id, equipmentName));
                    
                    if (input.hasNextLine())    // Checking whether there is blank line in the next line
                        input.nextLine();       // Skip the blank line if there is one on the next line
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Write the data of equipment list to a file named "EquipmentData.txt"
    static void writeEquipmentFile(ArrayList<Equipment> arrayList)
    {
        try 
        {
            try (FileWriter writer = new FileWriter("EquipmentData.txt"))   // Prepare FileWriter to write the contents to the file named "EquipmentData.txt" and close the FileWriter object
            {
                for (int i = 0; i < arrayList.size(); i++)
                {
                    // Write data of equipment into the file
                    writer.write(arrayList.get(i).getEquipmentID() + "\n");
                    writer.write(arrayList.get(i).getEquipmentName() + "\n");

                    // Add a new blank line if the current Equipment object used to write the data is not the final one
                    if (i != arrayList.size() - 1)
                        writer.write("\n");
                }
            }
        }
        catch (IOException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // ExercisePlan Data Read & Write \\
    // Read the data of current recorded exercise plans from a file named "ExercisePlanData.txt"
    // Assign the data into an ArrayList of ExercisePlan
    static void readExercisePlanFile(ArrayList<ExercisePlan> arrayList)
    {
        try 
        {
            File myFile = new File("ExercisePlanData.txt");     // Specify the name of the file to be read
            
            try 
            {
                if (myFile.createNewFile())     // Check whether there exists the file already, if not, new file named "ExercisePlanData.txt" will be created
                    return;                     // Exit the function if the file is newly created to be used
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try (Scanner input = new Scanner(myFile)) // Prepare scanner to read the contents of the file and close the scanner object
            {
                while (input.hasNextLine())         // Check if there still exists contents in the file
                {
                    // Temporary variables used to store the value used to create an ExercisePlan object
                    String planDescription, trainerID;
                    int planID, equipmentID, expectedDuration, startHour, startMinute, endHour, endMinute, exerciseDaySize;
                    int[] exerciseDay;
                    float price;
                    Trainer trainer;
                    Equipment equipment;
                    
                    // Reading contents from the file and assign to each variable used to instantiate ExercisePlan object
                    planID = input.nextInt();
                    input.nextLine();               // Used to clear "\n" in the file after reading an integer value
                    
                    trainerID = input.nextLine();
                    
                    equipmentID = input.nextInt();
                    input.nextLine();               // Used to clear "\n" in the file after reading an integer value
                    
                    planDescription = input.nextLine();
                    
                    expectedDuration = input.nextInt();
                    price = input.nextFloat();
                    
                    startHour = input.nextInt();
                    startMinute = input.nextInt();
                    endHour = input.nextInt();
                    endMinute = input.nextInt();
                    
                    exerciseDaySize = input.nextInt();
                    
                    exerciseDay = new int[exerciseDaySize];
                    
                    for (int counter = 0; counter < exerciseDaySize; counter++)
                    {
                        exerciseDay[counter] = input.nextInt();
                    }
                    
                    trainer = getTrainer(trainerID);
                    equipment = getEquipment(equipmentID);
                    
                    // Complete reading all the values required to instantiate an ExercisePlan object
                    // Adding a new ExercisePlan object to the ArrayList of ExercisePlan
                    arrayList.add(new ExercisePlan(planID, trainer, equipment, planDescription, expectedDuration, (float)price, LocalTime.of(startHour, startMinute), LocalTime.of(endHour, endMinute), exerciseDay));
                    
                    if (input.hasNextLine())    // Checking whether there is blank line in the next line
                        input.nextLine();       // Skip the blank line if there is one on the next line
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Write the data of exercise plan list to a file named "ExercisePlanData.txt"
    static void writeExercisePlanFile(ArrayList<ExercisePlan> arrayList)
    {
        try 
        {
            try (FileWriter writer = new FileWriter("ExercisePlanData.txt"))   // Prepare FileWriter to write the contents to the file named "ExercisePlanData.txt" and close the FileWriter object
            {
                for (int i = 0; i < arrayList.size(); i++)
                {
                    // Write data of exercise plan into the file
                    writer.write(arrayList.get(i).getPlanID() + "\n");
                    writer.write(arrayList.get(i).getTrainer().getID() + "\n");
                    writer.write(arrayList.get(i).getEquipment().getEquipmentID() + "\n");
                    writer.write(arrayList.get(i).getPlanDescription() + "\n");
                    writer.write(arrayList.get(i).getExpectedDuration() + "\n");
                    writer.write(arrayList.get(i).getPrice() + "\n");
                    writer.write(arrayList.get(i).getStartTime().getHour() + "\n");
                    writer.write(arrayList.get(i).getStartTime().getMinute() + "\n");
                    writer.write(arrayList.get(i).getEndTime().getHour() + "\n");
                    writer.write(arrayList.get(i).getEndTime().getMinute() + "\n");
                    
                    int[] exerciseDay = arrayList.get(i).getExerciseDay();
                    writer.write(exerciseDay.length + "\n");
                    
                    for (int j = 0; j < exerciseDay.length; j++) 
                    {
                        writer.write(exerciseDay[j] + "\n");
                    }
                    
                    // Add a new blank line if the current ExercisePlan object used to write the data is not the final one
                    if (i != arrayList.size() - 1)
                        writer.write("\n");
                }
            }
        }
        catch (IOException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Subscription Data Read & Write \\
    // Read the data of current recorded subscriptions from a file named "SubscriptionData.txt"
    // Assign the data into an ArrayList of Subscription
    static void readSubscriptionFile(ArrayList<Subscription> arrayList)
    {
        try 
        {
            File myFile = new File("SubscriptionData.txt");     // Specify the name of the file to be read
            
            try 
            {
                if (myFile.createNewFile())     // Check whether there exists the file already, if not, new file named "SubscriptionData.txt" will be created
                    return;                     // Exit the function if the file is newly created to be used
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try (Scanner input = new Scanner(myFile)) // Prepare scanner to read the contents of the file and close the scanner object
            {
                while (input.hasNextLine())         // Check if there still exists contents in the file
                {
                    // Temporary variables used to store the value used to create a Subscription object
                    String customerID;
                    int subscriptionID, planID, startYear, startMonth, startDay, endYear, endMonth, endDay;
                    float payment;
                    Customer customer;
                    ExercisePlan exercisePlan;
                    
                    // Reading contents from the file and assign to each variable used to instantiate Subscription object
                    subscriptionID = input.nextInt();
                    input.nextLine();
                    
                    customerID = input.nextLine();
                    
                    planID = input.nextInt();
                    
                    startYear = input.nextInt();
                    startMonth = input.nextInt();
                    startDay = input.nextInt();
                    
                    endYear = input.nextInt();
                    endMonth = input.nextInt();
                    endDay = input.nextInt();
                    
                    payment = input.nextFloat();
                    
                    customer = getCustomer(customerID);
                    exercisePlan = getExercisePlan(planID);
                    
                    // Complete reading all the values required to instantiate a Subscription object
                    // Adding a new Subscription object to the ArrayList of Subscription
                    arrayList.add(new Subscription(subscriptionID, customer, exercisePlan, LocalDate.of(startYear, Month.of(startMonth), startDay), LocalDate.of(endYear, Month.of(endMonth), endDay), payment));
                    
                    if (input.hasNextLine())    // Checking whether there is blank line in the next line
                        input.nextLine();       // Skip the blank line if there is one on the next line
                }
            }
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Write the data of subsciption list to a file named "SubscriptionData.txt"
    static void writeSubscriptionFile(ArrayList<Subscription> arrayList)
    {
        try 
        {
            try (FileWriter writer = new FileWriter("SubscriptionData.txt"))   // Prepare FileWriter to write the contents to the file named "SubscriptionData.txt" and close the FileWriter object
            {
                for (int i = 0; i < arrayList.size(); i++)
                {
                    // Write data of subscription into the file
                    writer.write(arrayList.get(i).getSubscriptionID() + "\n");
                    writer.write(arrayList.get(i).getCustomer().getID() + "\n");
                    writer.write(arrayList.get(i).getExercisePlan().getPlanID() + "\n");
                    writer.write(arrayList.get(i).getDatePurchased().getYear() + "\n");
                    writer.write(arrayList.get(i).getDatePurchased().getMonthValue() + "\n");
                    writer.write(arrayList.get(i).getDatePurchased().getDayOfMonth() + "\n");
                    writer.write(arrayList.get(i).getDateEnd().getYear() + "\n");
                    writer.write(arrayList.get(i).getDateEnd().getMonthValue() + "\n");
                    writer.write(arrayList.get(i).getDateEnd().getDayOfMonth() + "\n");
                    writer.write(arrayList.get(i).getTotalAmountPaid() + "\n");
                    
                    // Add a new blank line if the current Subscription object used to write the data is not the final one
                    if (i != arrayList.size() - 1)
                        writer.write("\n");
                }
            }
        }
        catch (IOException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Gym Name Data Read & Write \\
    // Read the data of current recorded gym name from a file named "GymData.txt"
    // Assign the gym name to static variable gymName
    // Assign the gym admin username and password to static variable adminUsername and adminPassword
    static void readGymFile()
    {
        try 
        {
            File myFile = new File("GymData.txt");     // Specify the name of the file to be read
            
            try 
            {
                if (myFile.createNewFile())     // Check whether there exists the file already, if not, new file named "GymData.txt" will be created
                {
                    try (FileWriter writer = new FileWriter("GymData.txt"))   // Prepare FileWriter to write the name of gym to the newly created file named "DymData.txt" and close the FileWriter object
                    {
                        writer.write(GymName + "\n");  // Write the default gym name "XXX" to the file 
                        writer.write(adminUsername + "\n");   // Write the default administrator username to the file
                        writer.write(adminPassword + "\n");   // Write the default administrator password to the file
                    }
                    
                    return;     // Exit the function if the file is newly created to be used
                }                    
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try (Scanner input = new Scanner(myFile)) // Prepare scanner to read the contents of the file and close the scanner object
            {
                GymName = input.nextLine();     // Read the gym name from the file and assign it to variable gymName
                adminUsername = input.nextLine();   // Read the admin username from the file and assign it to variable adminUsername
                adminPassword = input.nextLine();   // Read the admin password from the file and assign it to variable adminPassword
            }
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Write the data of gym name to a file named "GymData.txt"
    static void writeGymNameFile()
    {
        try 
        {
            try (FileWriter writer = new FileWriter("GymData.txt"))   // Prepare FileWriter to write the contents to the file named "GymData.txt" and close the FileWriter object
            {
                writer.write(GymName  + "\n");      // Write the current gym name to the file
                writer.write(adminUsername  + "\n");    // Write the current admin username to the file
                writer.write(adminPassword  + "\n");    // Write the current admin password to the file
            }
        }
        catch (IOException ex) 
        {
            Logger.getLogger(GymManagementApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Write all the current data into all files and exit the system (**Only use when the application should be closed)
    public static void writeFiles()
    {
        writeStaffFile(staffList);
        writeTrainerFile(trainerList);
        writeCustomerFile(customerList);
        writeEquipmentFile(equipmentList);
        writeExercisePlanFile(exercisePlanList);
        writeSubscriptionFile(subscriptionList);
        writeGymNameFile();
        System.exit(0);
    }
    
    public static void main(String[] args)
    {   
        readStaffFile(staffList);
        readTrainerFile(trainerList);
        readCustomerFile(customerList);
        readEquipmentFile(equipmentList);
        readExercisePlanFile(exercisePlanList);
        readSubscriptionFile(subscriptionList);
        readGymFile();

        new MainMenu();
    }
}