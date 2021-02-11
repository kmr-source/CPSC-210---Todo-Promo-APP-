package parsers;

import model.*;
import model.exceptions.EmptyStringException;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Jsonifier;

import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class TestTaskParser {

    // TODO: design tests for TaskParser class

    private TaskParser taskParser;
    private List<Task> taskList;

    @BeforeEach
    void TestTaskParser() {

        taskList = new ArrayList<>();

        taskParser = new TaskParser();
        Task t = new Task ("Swim");
        Task t2 = new Task ("Live");
        Task t3 = new Task ("Love");
        Task t4 = new Task ("Love ComputerScience");

        taskList.add(t);
        t.setDueDate(new DueDate());
        taskList.add(t2);
        t2.addTag(new Tag ("lol"));
        taskList.add(t3);
        t3.addTag(new Tag ("yes"));
        taskList.add(t4);

        t.setPriority(new Priority(1));
        t.setDueDate(new DueDate());
        t.setStatus(Status.IN_PROGRESS);
        t.addTag(new Tag("Stroke"));
        t.addTag(new Tag("Back"));
        t.addTag(new Tag ("Left"));

        t2.setDueDate(null);

        t3.addTag(new Tag("O"));
        t3.addTag(new Tag("B"));
        t3.addTag(new Tag ("L"));

    }

    @Test
    void testJsonObjectDuedateFormat() {
        System.out.println(taskParser.parse("[\n" +
                "{\n" +
                "  \"description\":\"Register for the course. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"duedate\":{\"year\":2019,\"month\":0,\"day\":16,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":true,\"urgent\":true},\n" +
                "  \"status\":\"IN_PROGRESS\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Download the syllabus. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":null,\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"status\":\"UP_NEXT\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Read the syllabus! \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":null,\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"status\":\"TODO\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Make note of assignments deadlines. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"},{\"name\":\"assigns\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":17,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":false,\"urgent\":true},\n" +
                "  \"status\":\"TODO\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Update my calendar with those dates. \",\n" +
                "  \"tags\":[{\"name\":\"planning\"},{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":20,\"hour\":17,\"minute\":10},\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"status\":\"TODO\"\n" +
                "}\n" + "]"));

    }


    @Test
    void testImproperInputs() {
        System.out.println(taskParser.parse("[\n" +
                "{\n" +
                "  \"description\":\"Register for the course. \",\n" +
                "  \"tgs\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":16,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":true,\"urgent\":true},\n" +
                "  \"status\":\"IN_PROGRESS\"\n" +
                "},\n" +
                "{\n" +
                "  \"decription\":\"Download the syllabus. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":null,\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"status\":\"UP_NEXT\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Read the syllabus! \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":nll,\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"status\":\"TODO\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Make note of assignments deadlines. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"},{\"name\":\"assigns\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"moth\":0,\"day\":17,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":false,\"urgent\":true},\n" +
                "  \"status\":\"TODO\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Make note of quizzes and midterm dates. \",\n" +
                "  \"tags\":[{\"name\":\"exams\"},{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0s,\"day\":22,\"hour\":17,\"minute\":10},\n" +
                "  \"priority\":{\"important\":true,\"urgnt\":false},\n" +
                "  \"status\":\"TODO\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Update my calendar with those dates. \",\n" +
                "  \"tags\":[{\"name\":\"planning\"},{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":20,\"hour\":17,\"minute\":10},\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"stats\":\"TODO\"\n" +
                "}\n" + "]"));


    }


    @Test
    void testParse() {
        Task t4 = new Task("SUMMER");
        t4.setDueDate(null);
        t4.setStatus(Status.UP_NEXT);
        t4.setPriority(new Priority(4));
        taskList.add(t4);
        try {
            t4.addTag(new Tag(null));
            fail();
        } catch (EmptyStringException e) {
            // do nothing
        }


        JSONArray taskJson = Jsonifier.taskListToJson(taskList);

        System.out.println(taskJson.toString(4));

        System.out.println(taskParser.parse(taskJson.toString(4)));

        System.out.println(taskParser.parse("[\n" +
                "{\n" +
                "  \"description\":\"Register for the course. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":16,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":true,\"urgent\":true},\n" +
                "  \"status\":\"IN_PROGRESS\"\n" +
                "},\n" +
                        "{\n" +
                        "  \"description\":\"Download the syllabus. \",\n" +
                        "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                        "  \"due-date\":null,\n" +
                        "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                        "  \"status\":\"UP_NEXT\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"description\":\"Read the syllabus! \",\n" +
                        "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                        "  \"due-date\":null,\n" +
                        "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                        "  \"status\":\"TODO\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"description\":\"Make note of assignments deadlines. \",\n" +
                        "  \"tags\":[{\"name\":\"cpsc210\"},{\"name\":\"assigns\"}],\n" +
                        "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":17,\"hour\":23,\"minute\":59},\n" +
                        "  \"priority\":{\"important\":false,\"urgent\":true},\n" +
                        "  \"status\":\"TODO\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"description\":\"Make note of quizzes and midterm dates. \",\n" +
                        "  \"tags\":[{\"name\":\"exams\"},{\"name\":\"cpsc210\"}],\n" +
                        "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":22,\"hour\":17,\"minute\":10},\n" +
                        "  \"priority\":{\"important\":true,\"urgent\":false},\n" +
                        "  \"status\":\"TODO\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"description\":\"Update my calendar with those dates. \",\n" +
                        "  \"tags\":[{\"name\":\"planning\"},{\"name\":\"cpsc210\"}],\n" +
                        "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":20,\"hour\":17,\"minute\":10},\n" +
                        "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                        "  \"status\":\"TODO\"\n" +
                        "}\n" + "]"));
    }

}
