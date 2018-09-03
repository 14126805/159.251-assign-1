package nz.ac.massey.cs;

import java.io.Serializable;
import java.util.Date;

// This class models a Task item

public class Task implements Serializable {

    private String description;
    private boolean completed;
    private String name;
    private Integer id;
    private Date date;


    public Task(String name) {
        this.name = name;
        this.description = "";
        this.completed = false;
    }
    public boolean isComplete() {
        return completed;
    }
    public void setComplete(boolean complete) { completed = complete; }
}
