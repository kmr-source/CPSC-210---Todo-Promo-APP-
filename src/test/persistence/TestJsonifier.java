package persistence;

import model.*;
import model.exceptions.EmptyStringException;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestJsonifier {

    // store jsonifier into the json object

    private List<Task> task;


    @BeforeEach
    void TestJsonfier() {
        Jsonifier jsonifier = new Jsonifier();
        task = new ArrayList<>();


        Task t1 = new Task("HOME");

        t1.setStatus(Status.TODO);
        t1.setPriority(new Priority(1));
        t1.setDueDate(null);
        t1.addTag(new Tag("Furniture"));
        t1.addTag(new Tag("Chair"));
        t1.addTag(new Tag("Table"));


        Task t2 = new Task("SWIM");
        Date date = new Date();
        date.setHours(4);
        date.setMinutes(20);

        t2.setDueDate(new DueDate(date));
        t2.setStatus(Status.DONE);
        t2.setPriority(new Priority(3));


        Task t3 = new Task ("WINTER");
        t3.setStatus(Status.IN_PROGRESS);
        t3.setPriority(new Priority(2));




        task.add(t1);
        task.add(t2);
        task.add(t3);

    }

    @Test
    void testTaskJson() {
        Task t4 = new Task("SUMMER");
        t4.setStatus(Status.UP_NEXT);
        t4.setPriority(new Priority(2));
        t4.addTag(new Tag("you"));
        t4.setDueDate(new DueDate());
        task.add(t4);
        try {
            t4.addTag(new Tag(null));
            fail();
        } catch (EmptyStringException e) {
            // do nothing
        }

        JSONArray taskJson = Jsonifier.taskListToJson(task);


        System.out.println("JSON representation of library:\n");
        System.out.println(taskJson.toString(2));
        System.out.println("\n");

        Jsonifier.tagToJson(new Tag ("NO"));

        System.out.println(taskJson);

    }
}
