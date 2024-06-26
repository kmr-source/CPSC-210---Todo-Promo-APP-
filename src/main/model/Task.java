package model;

import javafx.beans.Observable;
import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import parsers.Parser;
import parsers.TagParser;
import parsers.exceptions.ParsingException;

import java.util.*;

// Represents a Task having a description, status, priorities, set of tags and due date.
public class Task extends Todo {

    public static final DueDate NO_DUE_DATE = null;


    private Set<Tag> tags;
    private DueDate dueDate;
    private Status status;

    // MODIFIES: this
    // EFFECTS: constructs a task with the given description
    //    parses the description to extract meta-data (i.e., tags, status, priority and deadline).
    //    If description does not contain meta-data, the task is set to have no due date,
    //    status of 'To Do', and default priority level (i.e., not important nor urgent)
    //  throws EmptyStringException if description is null or empty
    public Task(String description) {
        super(description);
        tags = new HashSet<>();
        dueDate = NO_DUE_DATE;
        status = Status.TODO;
        setDescription(description);
    }


    // MODIFIES: this
    // EFFECTS: creates a tag with name tagName and adds it to this task
    //  throws EmptyStringException if tagName is null or empty
    // Note: no two tags are to have the same name
    public void addTag(String tagName) {
        addTag(new Tag(tagName));
    }

    // MODIFIES: this
    // EFFECTS: adds tag to this task if it is not already exist
    //  throws NullArgumentException if tag is null
    public void addTag(Tag tag) {
        if (!containsTag(tag)) {
            tags.add(tag);
            tag.addTask(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the tag with name tagName from this task
    //  throws EmptyStringException if tagName is empty or null
    public void removeTag(String tagName) {

        removeTag(new Tag(tagName));
    }

    // MODIFIES: this
    // EFFECTS: removes tag from this task
    //  throws NullArgumentException if tag is null
    public void removeTag(Tag tag) {
        if (containsTag(tag)) {
            tags.remove(tag);
            tag.removeTask(this);
        }
    }

    // EFFECTS: returns an unmodifiable set of tags
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    // EFFECTS: returns the status of this task
    public Status getStatus() {
        return status;
    }

    // MODIFIES: this
    // EFFECTS: sets the status of this task
    //   throws NullArgumentException when status is null
    public void setStatus(Status status) {
        if (status == null) {
            throw new NullArgumentException("Illegal argument: status is null");
        }
      /*  if (status == Status.DONE) {
            setProgress(100);
        }*/
        this.status = status;
    }


    // EFFECTS: return a non-negative integer as the Estimated Time To Complete
    // Note: Estimated time to complete is a value that is expressed in
    //       hours of work required to complete a task or project.
    @Override
    public int getEstimatedTimeToComplete() {

        return this.etcHours;
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the closest integer).
    @Override
    public int getProgress() {

        return this.progress;
      /*  if (progress == 100) {
            setStatus(Status.DONE);
            return 100;
        } else {
            return this.progress;
        }*/

    }

    // MODIFIES: this
    // EFFECTS: sets the progress made towards the completion of this task
    //  throws InvalidProgressException if !(0 <= progress <= 100)
    public void setProgress(int progress) {
        if (!(progress >= 0) || !(progress <= 100)) {
            throw new InvalidProgressException();
        }
        this.progress = progress;

    }

    // MODIFIES: this
    // EFFECTS: sets the estimated time to complete this task (in hours of work)
    //  throws NegativeInputException if hours < 0
    public void setEstimatedTimeToComplete(int hours) {
        if (hours < 0) {
            throw new NegativeInputException();
        }

        this.etcHours = hours;
        setChanged();
        notifyObservers();
    }

    // MODIFIES: this
    // EFFECTS:  sets the description of this task
    //     parses the description to extract meta-data (i.e., tags, status, priority and deadline).
    //  throws EmptyStringException if description is null or empty
    public void setDescription(String description) {
        if (description == null || description.length() == 0) {
            throw new EmptyStringException("setDescription is called with no description");
        }
        this.description = description;
        parseDescription(description);
    }

    // EFFECTS: returns the due date of this task
    public DueDate getDueDate() {
        return dueDate;
    }

    // MODIFIES: this
    // EFFECTS: sets the due date of this task
    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }

    // EFFECTS: returns true if task contains a tag with tagName,
    //     returns false otherwise
    //  throws EmptyStringException if tagName is empty or null
    public boolean containsTag(String tagName) {
        if (tagName == null || tagName.length() == 0) {
            throw new EmptyStringException("Tag name cannot be empty or null");
        }
        return containsTag(new Tag(tagName));
    }

    // EFFECTS: returns true if task contains this tag,
    //     returns false otherwise
    //  throws NullArgumentException if tag is null
    public boolean containsTag(Tag tag) {
        if (tag == null) {
            throw new NullArgumentException("Invalid Argument: tag cannot be null");
        }
        return tags.contains(tag);
    }

    // REQUIRES: description is non-empty
    // MODIFIES: this
    // EFFECTS: parses the description to extract meta-data (i.e., tags, status, priority and deadline).
    private void parseDescription(String description) {
        Parser parser = new TagParser();
        try {
            parser.parse(description, this);
            this.description = parser.getDescription();
        } catch (ParsingException e) {
            this.description = description;
        }
    }

    //EFFECTS: returns a string representation of this task in the following format
    //    {
    //        Description: Read collaboration policy of the term project
    //        Due date: Sat Feb 02 2019 11:59 PM
    //        Status: IN PROGRESS
    //        Priority: IMPORTANT & URGENT
    //        Tags: #cpsc210, #project
    //    }
    // The order of printed tags do not matter.
    @Override
    public String toString() {
        StringBuffer output = new StringBuffer();
        output.append("\n{");
        output.append("\n\tDescription: " + getDescription());
        output.append("\n\tDue date: " + (getDueDate() == null ? "" : getDueDate()));
        output.append("\n\tStatus: " + getStatus());
        output.append("\n\tPriority: " + getPriority().toString());
        output.append("\n\tTags: " + tagsToString(new ArrayList<Tag>(getTags())));
        output.append("\n}");
        return output.toString();

    }

    // EFFECTS: returns a string containing a comma-separated list of tags,
    //     where each tag is preceded by label "Tags: "
    private String tagsToString(List<Tag> tags) {
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < tags.size() - 1; i++) {
            output.append(tags.get(i) + ", ");
        }
        if (tags.size() > 0) {
            output.append(tags.get(tags.size() - 1));
        } else {
            output.append(" ");
        }
        return output.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals(description, task.description)
                // && Objects.equals(tags, task.tags)
                && Objects.equals(dueDate, task.dueDate)
                && Objects.equals(priority, task.priority)
                && status == task.status;
    }

    @Override
    public int hashCode() {
        // return Objects.hash(description, tags, dueDate, priority, status);
        return Objects.hash(description, dueDate, priority, status);
    }

}
