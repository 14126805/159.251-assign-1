package nz.ac.massey.cs;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.ResourceReference;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TasksPage extends WebPage {

	private static final long serialVersionUID = 1L;

	Method method;
	public TasksPage() {
	//START OF IMPORT DATA CODE
	   //I am super embarressed with how poor coding these 10 first lines are haha - ah well it works
	   List<File> allFiles = new ArrayList<File>();
       File folder = new File("data\\files\\alice");
       File folderTwo = new File("data\\files\\bob");
       File folderThree = new File("data\\files\\olivia");
       File[] files = folder.listFiles();
       File[] filesTwo = folderTwo.listFiles();
       File[] filesThree = folderThree.listFiles();
       for (File file : files)		{allFiles.add(file);}
       for (File file : filesTwo)		{allFiles.add(file);}
       for (File file : filesThree)		{allFiles.add(file);}
       
     //Imports existing tasks from the directorys
       for (File file : allFiles)		{
    	//Sets up the reader
   		BufferedReader theReader = null;
        String line = "";  
        ArrayList<Task> testList = new ArrayList<Task>();
           try {
   		theReader = new BufferedReader(new FileReader(file));
           }catch(FileNotFoundException e){e.printStackTrace();}
           //Read the file and create an arraylist containing the tasks - THIS MIGHT BE LEGACY CODE DOUBLE CHECK IF I STILL USE THIS
           try {
   		String theTitle = null;
   		String tasksNameFinal = null;
   		String theDate = null;
   		
   		//The good stuff is in here. Reader is working, just manipulating the data
   		while ((line = theReader.readLine()) != null){
   			//Silly error on some of the data, need to remove spaces at start
   			if (!line.substring(0,1).equals(" ")){
   			//These 2 lines find the project title
   			if (line.substring(0,1).equals("#")&line.substring(1,2).equals(" ")) {
   				theTitle = line.substring(line.lastIndexOf("#")+2);
   			}
   			//These lines find the rest of the data in the file
   		    if (!line.substring(0,1).equals("#")) {
   		    	//Finds if its completed
   		    	String isTaskComplete = line.substring(1,2);
   		    	//Finds if Active
   		    	String firstActive = line.substring(line.lastIndexOf("(")+1);
   		    	String isTaskActive = firstActive.substring(0,1);
   		    	//Finds the Date
   		    	String theDateAlmost = line.substring(line.indexOf("20")+1);
   		    	//Following if / else statement deals with tasks with no dates
   		    	if(theDateAlmost.substring(0,1).equals("0")) {
   		    		theDate  = "2"+theDateAlmost;
   		    	}
   		    	else { theDate = " ";
   		    	}
   		    	//Finds the Name, if Active
   		    	if(isTaskActive.equals("A")) {
   		    		String tasksName = line.substring(line.indexOf(")")+2);
   		    		String[] tasksNameNew = tasksName.split("due");
   		    		tasksNameFinal = tasksNameNew[0];
   		    	}//Finds the Name, if not active
   		    	else {
   		    		String tasksName = line.substring(line.indexOf("]")+2);
   		    		String[] tasksNameNew = tasksName.split("due");
   		    		tasksNameFinal = tasksNameNew[0];
   		    	}    	
   		        WicketApplication app = (WicketApplication) this.getApplication();
   		        TaskList collection = app.getTaskList();
   		    	collection.addTask(new Task(tasksNameFinal, theDate,theTitle));
   		    	}	  
   			}}
   		theReader.close();
           }catch (IOException e) {System.out.println("it broke");}
       } 
     //END OF IMPORT DATA CODE
 
	
        add(new EntryForm("entryForm"));
        Form listForm = new Form("listForm");
        add(listForm);

		Date now = new Date();
		Label dateTimeLabel = new Label("datetime", now.toString());
		add(dateTimeLabel);

		WicketApplication app = (WicketApplication) this.getApplication();
		TaskList collection = app.getTaskList();
		List<Task> tasks = collection.getTasks();

		Label countLabel = new Label("inactiveCount", new PropertyModel(collection,"InactiveCount"));
		add(countLabel);
		
		
		PropertyListView taskListView =
				new PropertyListView("task_list", tasks) {
					private static final long serialVersionUID = 1L;

					@Override
					protected void populateItem(ListItem item) {
						item.add(new Label("name"));
						item.add(new Label("description"));
						item.add(new Label("dueDates"));
						item.add(new Label("projectTitle"));

						item.add(new AjaxCheckBox("completed") {
							@Override
							protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
							}
						});
					}
				};
		
		
		add(new Link<Void>("selectAll") {
			@Override
			public void onClick() {
				for(Task t: tasks) {
					t.setComplete(true);
				}
			}
		});
		
		add(new Link<Void>("ClearCompleted") {
			@Override
			public void onClick() {
				List<Task> forRemoval = new ArrayList<Task>();
				for(Task t: tasks) {
					if(t.isComplete())
						forRemoval.add(t);
				}
				tasks.removeAll(forRemoval);
			}
		});

		
		add(new Link<Void>("showCompleted") {
		@Override
		public void onClick() {
			ArrayList<Task> allTasks = Task.reader();
			List<Task> forRemoval = new ArrayList<Task>();
			for(Task t: allTasks) {
				if(!t.isComplete())
					forRemoval.add(t);
			}
			System.out.println("allTasks");
			System.out.println(allTasks);
			System.out.println(forRemoval);
			allTasks.removeAll(forRemoval);
			System.out.println(allTasks);
			System.out.println("done");
			System.out.println(tasks);
		}
	});
		
		add(new Link<Void>("showAllTasks") {
		@Override
		public void onClick() {
			ArrayList<Task> allTasks = Task.reader();	
			System.out.println(allTasks);
		}
	});
		
		
		
		
//		add(new Link<Void>("showActive") {
//		@Override
//		public void onClick() {
//			List<Task> forRemoval = new ArrayList<Task>();
//			List<Task> baseLine = new ArrayList<Task>();
//			
//			for(Task t: tasks) {
//				baseLine.add(t);
//				if(t.isComplete())
//					forRemoval.add(t);
//			}
//			
//			baseLine.removeAll(forRemoval);
//		}
//	});
		
		
		
		listForm.add(taskListView);

	}
	
}
