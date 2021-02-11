package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestTaskPhase3 {
    // TODO: design tests for new behaviour added to Task class
    private Task task;
    private Tag t;
    private DueDate dueDate;
    private Priority p;
    private String nullString;
    private String description;

    @BeforeEach
    void TestTaskPhase3() {
        this.description = "Register for the course. ## cpsc210; tomorrow; important; urgent; in progress";
        this.nullString = null;
        this.task = new Task("Homework");

        t = new Tag("Math");
        dueDate = new DueDate();
        p = new Priority();

    }

    @Test
    void testAddTags() {

        try {
            Tag nullTag = new Tag(null);
        } catch (NullArgumentException e) {
            //nothing
        } catch (EmptyStringException e) {
            System.out.println(e);
        }

        try {
            this.task.addTag(this.t);
            this.task.addTag("hope");
            this.task.addTag("hope");
        } catch (NullArgumentException e) {
            fail();
        } catch (EmptyStringException e) {
            fail();
        }

        assertTrue(task.containsTag("hope"));
        assertTrue(task.containsTag(t));
        assertTrue(task.containsTag(t.getName()));
        System.out.println(task.toString());

        // remove twice
        task.removeTag("hope");
        task.removeTag("hope");
        assertFalse(task.containsTag("hope"));

        // test tag string and tag obj
        assertTrue(task.containsTag("Math"));
        assertTrue(task.containsTag(t));

        // test tag after remove if tag contain tag obj's string and obj tag
        task.removeTag("Math");
        assertFalse(task.containsTag("Math"));
        assertFalse(task.containsTag(t));

        assertFalse(task.containsTag("Math"));

        try {
            Tag t = null;
            task.addTag(t);
            task.containsTag(t);
            fail();
        } catch (NullArgumentException e) {
            System.out.println(e);

        } catch (EmptyStringException e) {
            System.out.println(e);
        }

        try {
            String zero = "";
            task.containsTag(zero);
            fail();
        } catch (EmptyStringException e) {
            System.out.println(e);
        }
    }

    @Test
    void testRemoveTags() {
        try {
            this.task.addTag(this.t);
        } catch (NullArgumentException e) {
            fail();
        }

        try {
            task.removeTag(t);

        } catch (NullArgumentException e) {
            fail();
        }

        try {
            task.addTag(this.t);
            task.removeTag(t.getName());
        } catch (NullArgumentException e) {
            fail();
        }
        assertTrue(!task.containsTag(t));


        try {
            Tag n = null;
            task.removeTag(n);
            fail();
        } catch (NullArgumentException e) {
            System.out.println(e);
            System.out.println("No task specified");
        }
    }

    @Test
    void testGetTagString() {
        task.toString();
        task.getDescription();
        task.getStatus();
        task.getPriority();
        task.getTags();
        task.getDueDate();
        assertEquals("Homework", task.getDescription());

    }


    @Test
    void testConstructor() {
        String nullString = null;
        try {
            Task taskNew = new Task(nullString);
            taskNew.getDescription();
            fail();
        } catch (NullArgumentException e) {
            fail();
        } catch (EmptyStringException e) {
            // do something
        }
    }

    @Test
    void testSetDescription() {
        Task t = new Task("Lone");
        try {
            t.setDescription("nice");
        } catch (EmptyStringException e) {
            fail();
        }

        try {
            t.setDescription(nullString);
            fail();
        } catch (EmptyStringException e) {
            // do nothing passes
        }

        try {
            t.containsTag(nullString);
            fail();
        } catch (EmptyStringException e) {
            // test passes
        }
        t.setDescription(description);

    }

    @Test
    void setNull() {
        Task t2 = new Task(description);
        try {
            t2.setPriority(null);
            fail();
        } catch (NullArgumentException e) {
            System.out.println(e);
        }

        try {
            t2.setStatus(null);
            fail();
        } catch (NullArgumentException e) {
            System.out.println(e);
        }

        try {
            Tag tag = new Tag(nullString);
            fail();
        } catch (EmptyStringException e) {
            System.out.println(e);
        }

        try {
            Tag tagnull = null;
            t2.containsTag(tagnull);
            fail();
        } catch (NullArgumentException e) {
            //do nothing passes
        }

    }

    @Test
    void testTagToString() {
        Task t2 = new Task(description);
        Tag tag2 = new Tag("Home");
        Tag tag3 = new Tag("Songs");
        t2.addTag(t);
        t2.addTag(tag2);
        t2.addTag(tag3);

        try {
            task.toString();
        } catch (NullArgumentException e) {
            fail();
        } catch (EmptyStringException e) {
            fail();
        }


    }


    @Test
    public void testProgressAndEstimatedTime() {

        assertTrue(task.getProgress() == 0);
        assertTrue(task.getEstimatedTimeToComplete() == 0);
    }

    @Test
    public void testEqualsAndHash() {
        List<Task> taskList = new ArrayList<>();

        Task taskTest = new Task("Homework");

        assertFalse(task.equals(p));
        assertTrue(task.equals(task));
        assertTrue(task.equals(taskTest));

        taskList.add(task);
        taskList.add(taskTest);

        assertFalse(task.equals(taskList));
    }

    @Test
    void testTaskEqualsFalsexxx() {
        Date date = new Date(2019, 02, 30);
        Task t1 = new Task("hello kitty");
        Task t2 = new Task("hello kitty");

        assertTrue(t1.equals(t2));

        // change status t2
        t2.setStatus(Status.IN_PROGRESS);
        assertFalse(t1.equals(t2));

        //change priority of t2
        t2.setPriority(new Priority(1));
        assertFalse(t1.equals(t2));

        // change due date of t2
        t2.setDueDate(new DueDate(date));
        assertFalse(t1.equals(t2));


        assertFalse(t1.equals(t2));
    }

    @Test
    void testEquals() {
        Date date = new Date(2019, 11, 12, 12, 14);
        DueDate d = new DueDate(date);
        Task task2 = new Task("Homework");
        assertTrue(task.equals(task2));

        // test equals dueDate obj true and false
        task2.setDueDate(d);
        assertTrue(task2.getDueDate().equals(d));
        assertFalse(task2.getDueDate().equals(new DueDate()));

        // test task Priority equals status true and false
        assertFalse(task2.getPriority().equals(new Priority(1)));
        assertTrue(task2.getPriority().equals(new Priority(4)));
        assertTrue(task2.getPriority().equals(task.getPriority()));

        // test task Status equals status true and false
        assertFalse(task2.getStatus().equals(Status.IN_PROGRESS));
        assertTrue(task2.getStatus().equals(Status.TODO));
        assertTrue(task2.getStatus().equals(task.getStatus()));


        assertEquals(task.toString(), task.toString());
        assertEquals(task2.getDescription(), task.getDescription());

    }

    @Test
    void testRemoveAndAdd() {

        Project project = new Project("1");
        Project subProject = new Project("2");

        Task t1 = new Task("A");
        Task t2 = new Task("B");
        Task t3 = new Task("C");
        Task t4 = new Task("D");

        t1.setEstimatedTimeToComplete(3);
        t2.setEstimatedTimeToComplete(4);
        t4.setEstimatedTimeToComplete(2);
        t3.setEstimatedTimeToComplete(2);

        project.add(t1);
        project.add(t2);
        subProject.add(t3);
        subProject.add(t4);
        project.add(subProject);
        project.add(t2);

        assertEquals(project.getEstimatedTimeToComplete(), 11);

        project.remove(t1);
        project.remove(t1);
        project.remove(t2);

        assertEquals(project.getEstimatedTimeToComplete(), 4);

        project.remove(subProject);

        assertEquals(project.getEstimatedTimeToComplete(),0);

    }

}

