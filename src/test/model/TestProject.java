package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {
    Project p;
    Task Task1;
    Task Task2;
    Task Task3;

    @BeforeEach
    void TestProject() {
        p = new Project("Low");
        Task1 = new Task("hw");
        Task2 = new Task("job");
        Task3 = new Task("Swim");
        Task1.addTag(new Tag("1"));
        Task1.addTag(new Tag("2"));
        Task1.addTag(new Tag("3"));
        p.add(Task1);
        p.add(Task2);
        p.add(Task3);
    }

    // change this one
    @Test
    void testGetProgress() {
        Project Project;
        Task t1 = new Task("Ams");
        Task t2 = new Task("UBC");
        Task t3 = new Task("Car");
        Project p12 = new Project("project 1");

        Project p0 = new Project("project 0");

        Project = new Project("My CPSC project");
        p12.add(t1);
        p12.add(t2);
        p0.add(p12);
        p0.add(t3);

        assertEquals(p0.getProgress(), 0);
        t1.setProgress(53);
        t2.setProgress(2);
        assertEquals(p12.getProgress(), Math.floor((53 + 2) / 2));
        assertEquals(p0.getProgress(), Math.floor((p12.getProgress() + 0) / 2));
    }

    @Test
    public void projectAddSameName() {
        Project project = new Project("1");
        Task task = new Task("task");
        Project subProject = new Project("1");

        project.add(task);
        project.add(subProject);

        assertEquals(project.getNumberOfTasks(), 1);
    }


    @Test
    public void testProgress() {
        Task1.setProgress(50);
        Task2.setProgress(100);
        Task3.setProgress(25);
        assertEquals(p.getProgress(), 58);

        Task1.setProgress(100);
        Task3.setProgress(100);

        Task1.toString();

        assertTrue(p.isCompleted());

        try {
            Task3.setProgress(-1);
            fail();
        } catch (InvalidProgressException e) {
            System.out.println("invalid");
        }

        try {
            Task3.setProgress(0);
        } catch (InvalidProgressException e) {
            System.out.println("invalid ");
            fail();
        }

        try {
            Task3.setProgress(120);
            fail();
        } catch (InvalidProgressException e) {
            System.out.println("can't be over 100");
        }

        Project projectNothing = new Project("no Tasks inside ");
        assertEquals(0, projectNothing.getProgress());


    }


    @Test
    public void testAddProjectAsItsOwnSubProject() {
        p.add(p);
        // since proj not added/ can't be added to itself it should return 3
        assertTrue(p.getNumberOfTasks() == 3);
    }

    @Test
    public void testNullDescription() {
        String str = null;
        Todo project = new Project("Homework List");
        project.getDescription();

        try {
            p.add(new Project(str));
            fail();
        } catch (EmptyStringException e) {
            e.printStackTrace();
            // do nothing
        }
    }

    @Test
    public void testException() {
        try {
            throw new NegativeInputException("message neg exception");

        } catch (NegativeInputException e) {
            // expecting
        }

        try {
            throw new InvalidProgressException("message");
        } catch (InvalidProgressException e) {
            // expecting
        }
    }

    @Test
    public void testEstimateTimeCompletion() {
        Task1.setEstimatedTimeToComplete(2);
        Task2.setEstimatedTimeToComplete(3);
        Task3.setEstimatedTimeToComplete(2);

        // should be 7hrs
        assertEquals(p.getEstimatedTimeToComplete(), 7);

        Task3.setEstimatedTimeToComplete(0);

        assertEquals(p.getEstimatedTimeToComplete(), 5);

        // make new composite project
        Project project = new Project("Banking project");
        Task t1 = new Task("savings");
        Task t2 = new Task("cheque");
        t1.setEstimatedTimeToComplete(2);
        t2.setEstimatedTimeToComplete(5);
        project.add(t1);
        project.add(t2);

        p.add(project);
        // should be 14 hours
        assertEquals(p.getEstimatedTimeToComplete(), 14);

        try {
            t2.setEstimatedTimeToComplete(-1);
            fail();
        } catch (NegativeInputException e) {
            // do nothing
        }

    }

    @Test
    public void testAddTask() {
        p.add(Task1);

        assertEquals(p.contains(Task1), true);
        assertEquals(p.contains(Task2), true);
        assertEquals(p.contains(Task3), true);
    }

    @Test
    public void testContainsTask() {
        Tag duplicateT1 = new Tag("1");
        Task Task4 = new Task("4");

        assertTrue(Task1.containsTag("1"));
        assertTrue(Task1.containsTag(duplicateT1.getName()));
        assertFalse(Task1.containsTag("7"));

        assertEquals(p.contains(Task1), true);
        assertEquals(p.contains(Task2), true);
        assertEquals(p.contains(Task3), true);
        assertEquals(p.contains(Task4), false); // true, true --- false, false(correct)

    }

    @Test
    public void testGetTasksMethod() {
        try {
            p.getTasks();
            fail();
        } catch (UnsupportedOperationException e) {
            // do nothing
        }
    }


    @Test
    public void testRemoveTask() {

        Task1.setProgress(100);
        Task2.setProgress(100);
        //Task2.getStatus().getDescription();
        p.getProgress();
        assertEquals(p.getProgress(), 66);
        assertEquals(p.isCompleted(), false);
        Task3.setProgress(100);
        assertEquals(p.getProgress(), 100);
        assertEquals(p.isCompleted(), true);

        assertEquals("Low", p.getDescription());
        assertEquals(3, p.getNumberOfTasks());
        p.remove(Task1);
        assertEquals(2, p.getNumberOfTasks());

    }

    @Test
    public void testCheckContainsMoreThanOn2() {
        Task task4 = new Task("r");
        Task task5 = new Task("t");
        Task task6 = new Task("lol");
        p.add(task5);
        p.add(task4);
        p.contains(task5);
        p.contains(task6);
    }

    @Test
    public void testEmptyStringException() {
        try {
            Project p = new Project(null);
            fail();
        } catch (EmptyStringException e) {
            // throw exception
        }
    }

    @Test
    public void testDoesNotContainTask() {
        //Task taskTestNone = new Task("none");
        try {
            p.contains(null);
            fail();
        } catch (NullArgumentException e) {
            System.out.println("no such task in list");
        } catch (EmptyStringException e) {
            // do nothing
        }
    }

    @Test
    public void testEqualsProjects() {
        List<Project> projectList = new ArrayList<>();

        Project x = new Project("hello");
        Project y = new Project("hello");

        projectList.add(x);
        projectList.add(y);


        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
        //assertFalse(x.equals(p));
        assertFalse(x.equals(projectList));
        assertTrue(p.equals(p));

    }

    @Test
    void testIsComplete() {
        Project p = new Project("p");
        Task t1 = new Task("A");
        Task t2 = new Task("B");
        Task t3 = new Task("C");

        p.add(t1);
        p.add(t2);

        assertEquals(p.isCompleted(), false);

        // set progress 100 test one branch
        t1.setProgress(100);
        t2.setProgress(100);
        t3.setProgress(100);

        assertEquals(p.isCompleted(), true);

        // remove all and test isComplete returns false

        p.remove(t1);
        p.remove(t2);

        assertEquals(p.isCompleted(), false);

        p.remove(t3);
        assertEquals(p.contains(t3), false);
        assertEquals(p.isCompleted(), false);

    }

    @Test
    void testContainsTag() {
        Project project = new Project("P");
        Task t1 = new Task("A");
        Task t2 = new Task("B");
        String str = null;
        String zero = "";

        project.add(t1);
        project.add(t2);

        try {
            t1.addTag(str);
        } catch (EmptyStringException e) {
            e.getMessage();
        }

        try {
            t1.addTag(zero);
        } catch (EmptyStringException e) {
            e.getMessage();
        }
    }

    @Test
    void testTaskSetDescription() {
        Project p = new Project("P");
        Task t = new Task("A");
        String str = null;
        String zero = "";

        p.add(t);

        try {
            t.setDescription(str);
        } catch (EmptyStringException e) {
            e.getMessage();
        }


        try {
            t.setDescription(zero);
        } catch (EmptyStringException e) {
            e.getMessage();
        }
    }

}