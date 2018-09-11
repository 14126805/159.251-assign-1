package nz.ac.massey.cs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

// This class models a Task item

public class Task implements Serializable {

    private String description;
    private boolean completed;
    private String name;
    private Integer id;
    private Date date;
    private String dueDates;
    private String projectTitle;

    
/////////////////////////////////////////////
public String getDescription()
{
return description;
}

public void setDescription(String sdescription)
{
this.description = sdescription;
}

/////////////////////////////////////////////

public boolean getCompleted()
{
return completed;
}

public void setCompleted(Boolean scompleted)
{
this.completed = scompleted;
}

/////////////////////////////////////////////
public String getName()
{
return name;
}

public void setname(String sname)
{
this.name = sname;
}
/////////////////////////////////////////////

public Integer getId()
{
return id;
}

public void setId(int sid)
{
this.id = sid;
}



/////////////////////////////////////////////
public Date getDate() 
{
return date;
}

public void setDate(Date sdate)
{
this.date = sdate;
}

/////////////////////////////////////////////

public String getdueDate() 
{
return dueDates;
}

public void setdueDate(String sdueDates)
{
this.dueDates = sdueDates;
}

/////////////////////////////////////////////
public String getprojectTitle()
{
return projectTitle;
}

public void setprojectTitle(String sprojectTitle)
{
this.projectTitle = sprojectTitle;
}

/////////////////////////////////////////////
    
    
    
    

    public Task(String name, String dueDates, String projectTitle) {
        this.name = name;
        this.description = "";
        this.completed = false;
        this.dueDates = dueDates;
        this.projectTitle = projectTitle;
    }
    
    public Task()
    {
 	   this.name = "";
        this.description = "";
        this.completed = false;
        this.dueDates = "";
        this.projectTitle = "";
    }
     
     
    
    //THIS IS FOR A FAILED ATTEMPT AT CSV WRITING, It's close to working
    public static ArrayList<Task> reader() {
		BufferedReader theReader = null;
        String line = "";
        String theSeperator = ",";        
        ArrayList<Task> testList = new ArrayList<Task>();
        try {
		theReader = new BufferedReader(new FileReader("reader.csv"));
        }catch(FileNotFoundException e){e.printStackTrace();}
        //Read the file and create an arraylist containing all the tasks
        try {
		while ((line = theReader.readLine()) != null){
		    String[] aTask = line.split(theSeperator);     
		    if (!aTask[0].equals("")) {
		    	Task someTask = new Task(aTask[0],aTask[1],aTask[2]);
			    testList.add(someTask);
		    	}	    
			}
		theReader.close();
        }catch (IOException e) {System.out.println("it broke");} 
        System.out.println("You clicked it and it didn't work"); // TESTING REMOVE THIS WHEN IT WORKS
        return testList;
    }
  //END FAILED ATTEMPT AT CSV WRITING, IT WORKS BUT SORT OF SUCKS
    
	public String getDetails () {
		return description + " " + completed + " " + name + " " + id + " " + date;
	}
	
    public boolean isComplete() {
        return completed;
    }
    
    
    public void setComplete(boolean complete) { completed = complete; }
}
