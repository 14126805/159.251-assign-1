package nz.ac.massey.cs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

// form with two fields for adding a task item

public class EntryForm extends Form<Void> {
	private RequiredTextField incompleteField;
    private RequiredTextField nameField;
    private RequiredTextField descriptionField;
    private RequiredTextField dueDateField;
    private RequiredTextField projectTitleField;
   
    public EntryForm(String id) {
        super(id);
        nameField = new RequiredTextField("name", Model.of(""));
        descriptionField = new RequiredTextField("description", Model.of(""));
        dueDateField = new RequiredTextField("dueDates", Model.of(""));
        projectTitleField = new RequiredTextField("projectTitle", Model.of("")); 
        add(projectTitleField);
        add(dueDateField);
        add(nameField);
        add(descriptionField);  
    }
    Method method;
    
    // adds the task when the form is submitted (by clicking the Add button)
    protected void onSubmit() {
        super.onSubmit();
        String name = (String)nameField.getDefaultModelObject();
        String description = (String)descriptionField.getDefaultModelObject();
        String dueDates = (String)dueDateField.getDefaultModelObject();
        String projectTitle = (String)projectTitleField.getDefaultModelObject();
      
        //Setup the writer to store information
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter("reader.csv",true));
			String xxx = name+","+description+","+dueDates+","+projectTitle+","+"X"+","+"A";
			bw.write(xxx);
			bw.newLine();
			bw.close();			
		}catch (IOException e) {e.printStackTrace();}
        
        descriptionField.clearInput();
        descriptionField.setModelObject(null);
        nameField.clearInput();
        nameField.setModelObject(null);
        dueDateField.clearInput();
        dueDateField.setModelObject(null);
        projectTitleField.clearInput();
        projectTitleField.setModelObject(null);

        WicketApplication app = (WicketApplication) this.getApplication();
        TaskList collection = app.getTaskList();
        collection.addTask(new Task(name, dueDates,projectTitle));
    }
}
