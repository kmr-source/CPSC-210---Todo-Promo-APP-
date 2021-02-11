package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTagPhase3 {
    // TODO: design tests for new behaviour added to Tag class
    private Tag tag;
    private Task t;
    private DueDate dueDate;
    private Priority p;

    @BeforeEach
    void TestTagPhase3() {
        tag = new Tag("Homework");

        t = new Task("Math");
        dueDate = new DueDate();
        p = new Priority();

    }

    @Test
    void testAddTasks() {
        t.setDescription("Homework");
        t.setStatus(Status.TODO);
        t.setDueDate(dueDate);
        t.setPriority(p);
        try {
            this.tag.addTask(this.t);

        } catch (NullArgumentException e) {
            fail();
        }
        try {
            tag.addTask(null);
            fail();
        } catch (NullArgumentException e) {
            System.out.println(e);
        }
    }

    @Test
    void testRemoveTags() {
        try {
            this.tag.addTask(this.t);
        } catch (NullArgumentException e) {
            fail();
        }

        try {
            tag.removeTask(t);

        } catch (NullArgumentException e) {
            fail();
        }

        try {
            tag.removeTask(null);
            fail();
        } catch (NullArgumentException e) {
            System.out.println(e);
            System.out.println("No task specified");
        }
    }

    @Test
    void testGetTagString() {
        tag.toString();
        tag.getName();
        assertEquals("Homework", tag.getName());
        assertEquals("#Homework", tag.toString());

    }

    @Test
    void testEquals() {
        Tag tag2 = new Tag("Homework");
        assertTrue(tag.equals(tag2));
        assertEquals(tag.toString(), tag2.toString());

        Tag tagNull = null;
        assertFalse(tag.equals(tagNull));

    }

    @Test
    Void testConstructTagNoName() {
        Tag noName;
        try {
            noName = new Tag(null);
            fail();
        } catch (EmptyStringException e) {
            System.out.println(e);
        }
        return null;
    }

    @Test
    void testContainsTask() {
        tag.addTask(t);

        assertTrue(tag.containsTask(t));

        tag.getTasks();
        tag.removeTask(t);
        assertFalse(tag.containsTask(t));

        Task nullTask = null;
        try {
            tag.containsTask(nullTask);
            fail();
        } catch (NullArgumentException e) {
            System.out.println(e);
        }

    }

    @Test
    public void testEqualsBooleanThis() {
        assertTrue(tag.equals(tag));
    }


}
