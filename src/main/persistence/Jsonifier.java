package persistence;


import model.*;
import org.json.JSONArray;
import org.json.JSONObject;



import java.util.Calendar;
import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {

        JSONObject tagJson = new JSONObject();
        tagJson.put("name", tag.getName());

        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {

        JSONObject priorJson = new JSONObject();
        priorJson.put("urgent", priority.isUrgent());
        priorJson.put("important",priority.isImportant());
        return priorJson;
    }

    // EFFECTS: returns JSON respresentation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject dueDateJson = new JSONObject();

        if (dueDate == null) {

            return null;
            //dueDateJson.NULL.toString();
        } else {
            // use the duedate parameter

            Calendar cal = Calendar.getInstance();
            cal.setTime(dueDate.getDate());

            dueDateJson.put("year", cal.get(Calendar.YEAR));
            dueDateJson.put("month", cal.get(Calendar.MONTH));
            dueDateJson.put("day", cal.get(Calendar.DAY_OF_MONTH));
            dueDateJson.put("hour", cal.get(Calendar.HOUR_OF_DAY));
            dueDateJson.put("minute", cal.get(Calendar.MINUTE));

            return dueDateJson;
        }
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {

        JSONObject taskJson = new JSONObject();

        taskJson.put("description",task.getDescription());
        JSONArray tagArray = new JSONArray();
        /// no tags throws error
        for (Tag t : task.getTags()) {
            JSONObject tagJson = tagToJson(t);
            tagArray.put(tagJson);
        }
        taskJson.put("tags", tagArray);

        if (dueDateToJson(task.getDueDate()) == null) {
            taskJson.put("due-date",JSONObject.NULL);
        } else {
            taskJson.put("due-date",dueDateToJson(task.getDueDate()));
        }
        taskJson.put("priority", priorityToJson(task.getPriority()));
        taskJson.put("status", task.getStatus().name());


        return taskJson;
    }


    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {

        JSONArray taskArray = new JSONArray();
        for (Task t : tasks) {
            taskArray.put(taskToJson(t));
        }

        return taskArray;
    }
}
