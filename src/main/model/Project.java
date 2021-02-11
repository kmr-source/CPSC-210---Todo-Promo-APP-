package model;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo>, Observer  {

    private List<Todo> tasks;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it) and Observer
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (!contains(task) && !task.equals(this)) {

            // this removes the task from the observerable's list of this project
            task.addObserver((Observer) this);
            tasks.add(task);
            //setChanged();
            // notifyObservers(this);
            update(this,null);
        }
    }


    // MODIFIES: this
    // EFFECTS: removes task from this project and Observer
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {

            // remove task from this observer List
            task.deleteObserver((Observer) this);
            tasks.remove(task);
            //setChanged();
            //notifyObservers(this);

            update(this,null);
        }
    }


    // EFFECTS: return a non-negative integer as the Estimated Time To Complete
    // Note: Estimated time to complete is a value that is expressed in
    //       hours of work required to complete a task or project.
    @Override
    public int getEstimatedTimeToComplete() {

        return etcHours;
    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the nearest integer).
    //     the value returned is the average of the percentage of completion of
    //     all the tasks and sub-projects in this project.
    public int getProgress() {
        if (tasks.size() == 0) {
            return 0;
        }

        double done = 0;
        int denominator = tasks.size();

        for (Todo t : tasks) {
            done = done + t.getProgress();

        }
        return (int) Math.floor(done / denominator);

    }


    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
    //     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {

        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }

    // EFFECTS: Checks if object is a instance of project/
    // , returns true of false
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }


    @Override
    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        int time = 0;

        for (Todo t : tasks) {

            time = time + t.getEstimatedTimeToComplete();
        }
        setChanged();
        notifyObservers();
        this.etcHours = time;
    }


    // EFFECTS: iterates through list
    public class ProjectIterator implements Iterator<Todo> {

        private int priority = 1;
        private int countIndex = 0;
        private int index = 0;
        private Todo todo = null;

        //EFFECTS: checks if it has the next element in list
        @Override
        public boolean hasNext() {
            return countIndex < tasks.size();
        }


        //EFFECTS: checks if there is a next element in the list
        @Override
        public Todo next() {

            while (priority <= 4) {
                while (index < (tasks.size())) {
                    Priority priority1 = new Priority(priority);
                    Priority prior = tasks.get(index).getPriority();
                    if (prior.equals(priority1)) {
                        countIndex++;
                        this.todo = tasks.get(index);
                        index++;
                        return todo;
                    } else {
                        index++;
                    }
                }
                reset();
            }
            throw new NoSuchElementException();
        }

        //MODIFIES: this
        //EFFECTS: resets the index to 0 and increments priority
        public void reset() {
            if (index == tasks.size()) {
                index = 0;
                priority++;
            }
        }



    }
}