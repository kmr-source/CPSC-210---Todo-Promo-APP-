package model;


import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.*;

public class TestIterator {
    private Project project;
    private Project p;
    private Task t1;
    private Task t2;
    private Task t3;

    @BeforeEach
    void TestIterator() {
        project = new Project("Project1");

        t1 = new Task("I and U");
        t1.setPriority(new Priority(1));
        t2 = new Task("I not U");
        t2.setPriority(new Priority(2));
        p = new Project("subProject");
        p.setPriority(new Priority(3));

        t3 = new Task("not U not I");
        t3.setPriority(new Priority(4));

        // add them inside
        project.add(t1);
        project.add(t2);
        project.add(p);
        project.add(t3);
    }

    @Test
    void testAddTodo() {

        String str = null;
         //adding same task SHOULD NOT WORK AAAAAAAA
        project.add(t1);
        project.add(t1);
        project.add(project);
        project.add(project);

        assertEquals(project.getNumberOfTasks(), 4);

        try {
            project.add(new Task(str));
            fail();
        } catch (EmptyStringException e) {
            // do thing
        }
    }

    @Test
    void testEqualsPriority() {
        t1.setPriority(new Priority(4));
        t1.getPriority();
        Priority p = new Priority(4);
        assertTrue(t1.getPriority().equals(p));
    }

    @Test
    void testIterator() {
        Iterator itr = project.iterator();
        Task taskimportant = new Task("important and urgent");
        project.add(taskimportant);
        taskimportant.setPriority(new Priority(1));
        assertEquals(t1, itr.next());
        assertEquals(taskimportant, itr.next());
        assertEquals(t2, itr.next());
        assertEquals(p, itr.next());
        assertEquals(t3, itr.next());

        for (Todo t : project) {
            System.out.println(t.getDescription());
        }
    }

    @Test
    void testReturnNullIteratorTaskXXX() {
        Project project = new Project("one null tasks");
        for (Todo t : project) {
            try {
                project.add(null);
                t.getDescription();
                fail();
            } catch (NullArgumentException e) {
                System.out.println("hello");
            }
        }
    }

    @Test
    void testRemove() {
        project.remove(t1);
        assertEquals(project.getNumberOfTasks(), 3);
        // try removing it again

        project.remove(t1);
        assertEquals(project.getNumberOfTasks(), 3);

        project.remove(t2);
        project.remove(t3);
        assertEquals(project.getNumberOfTasks(), 1);
    }

    @Test
    void testGetEstimatedTimexxx() {
        t1.setEstimatedTimeToComplete(2);
        t2.setEstimatedTimeToComplete(3);
        t3.setEstimatedTimeToComplete(4);

        try {
            project.getTasks();

        } catch (UnsupportedOperationException e) {
            // nothing pass
        }

        assertEquals(project.getProgress(), 0);

        assertEquals(project.getEstimatedTimeToComplete(), 9);

    }

    @Test
    void testNull() {
        Project p = new Project("n");

        try {
            p.contains(null);
            fail();
        } catch (NullArgumentException e) {

        }

    }

    @Test
    void testNextMethod() {
        for (Todo t : project) {
            System.out.println(t.getDescription());
        }

    }

    @Test
    void testIteratorEmptyProject() {
        Project p = new Project("Empty");
        Iterator<Todo> itr = p.iterator();
        assertFalse(itr.hasNext());
    }

    @Test
    void testIteratorExceptionHandling() {
        Project proj = new Project("empty");
        Iterator<Todo> itr = proj.iterator();
        try {
            itr.next();
            fail("Exception should have been thrown.");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    @Test
    void testProjectEmptyString() {
        String emptyStr = null;
        String lengthZero = "";

        try {
            Project empty = new Project(emptyStr);
        } catch (EmptyStringException e) {
            System.out.println("empty string");
        }

        try {
            Project empty2 = new Project(lengthZero);
        } catch (EmptyStringException e) {
            System.out.println("empty string");
        }
    }

    @Test
    void testProjectNoNextElement() {
        Iterator<Todo> itr = project.iterator();

        assertEquals(t1, (Todo) itr.next());
        assertEquals(t2, (Todo) itr.next());
        assertEquals(p, (Todo) itr.next());
        assertEquals(t3, (Todo) itr.next());

        try {
            itr.next();
            fail();
        } catch (NoSuchElementException e) {
            System.out.println("hello");
        }


        assertFalse(itr.hasNext());

    }

    @Test
    void testIteratorReset() {
        Project.ProjectIterator p = (Project.ProjectIterator) project.iterator();
        p.reset();


    }


}
