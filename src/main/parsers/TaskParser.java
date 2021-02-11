package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.*;

// Represents Task parser
public class TaskParser {
    // no task add if it is wrong - exception run time LOL

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {
        List<Task> tasks = new ArrayList<>();
        JSONArray taskArray = new JSONArray(input);

        for (Object object : taskArray) {
            try {
                Task task = new Task("default input");
                JSONObject taskJson = (JSONObject) object;


                Priority priority = new Priority();

                // getter and setter methods
                this.setDescription(task, taskJson);
                this.setStatus(task, taskJson);
                this.getTagObject(task, taskJson);
                this.setPriority(task, priority, taskJson);
                this.setTaskDueDate(task, taskJson);
                tasks.add(task);

            } catch (JSONException j) {

                System.out.println("");
            }
        }
        return tasks;
    }

    //EFFECTS: set the description of the JsonObject
    private void setDescription(Task t, JSONObject jsonObject) throws JSONException {

        Boolean descriptionBoolean = jsonObject.getString("description") instanceof String;

        if (!descriptionBoolean) {

            throw new JSONException("");
        }
        String description = jsonObject.getString("description");
        t.setDescription(description);
    }

    //EFFECTS: set the status of the JsonObject
    private void setStatus(Task t, JSONObject jsonObject) throws JSONException {
        Boolean statusBoolean = jsonObject.getString("status") instanceof String;
        if (!statusBoolean) {
            throw new JSONException("");
        }
        Status status = Status.valueOf(jsonObject.getString("status"));
        t.setStatus(status);
    }

    //EFFECTS: set the priority of the JsonObject
    private void setPriority(Task t, Priority priority, JSONObject jsonObject) throws JSONException {
        Boolean priorityBoolean = jsonObject.getJSONObject("priority") instanceof JSONObject;
        JSONObject n = jsonObject.getJSONObject("priority");

        if (!priorityBoolean) {

            throw new JSONException("");
        }
        priority.setImportant(n.getBoolean("important"));
        priority.setUrgent(n.getBoolean("urgent"));
        t.setPriority(priority);

    }

    //EFFECTS: set the Task duedate
    private void setTaskDueDate(Task t, JSONObject json) throws JSONException {

        t.setDueDate(this.getDateObject(t, json));

    }

    //EFFECTS: set the tag description
    private void getTagObject(Task task, JSONObject jsonObject) throws JSONException {
        Boolean tagBoolean = jsonObject.getJSONArray("tags") instanceof JSONArray;

        if (!tagBoolean) {
            throw new JSONException("");
        }

        List<String> tagsLists = new ArrayList<>();
        JSONArray tags = jsonObject.getJSONArray("tags");
        for (Object o : tags) {
            JSONObject j = (JSONObject) o;
            tagsLists.add(j.getString("name"));
        }
        for (String strings : tagsLists) {
            task.addTag(strings);
        }
    }

    //EFFECTS: returns true if obj type is instance of their type
    private boolean dueB(Integer year, Integer month, Integer day, Integer hour, Integer minute) {

        Boolean y = year instanceof Integer;
        Boolean m = month instanceof Integer;
        Boolean d = day instanceof Integer;
        Boolean hr = hour instanceof Integer;
        Boolean min = minute instanceof Integer;

        return y & m & d & hr & min;
    }

    //EFFECTS: returns the dueDate year
    private Integer getYear(JSONObject dueDate) {
        Integer year = dueDate.getInt("year");
        return year;
    }

    //EFFECTS: returns the dueDate month
    private Integer getMonth(JSONObject dueDate) {
        Integer month = dueDate.getInt("month");
        return month;
    }

    //EFFECTS: returns the dueDate Day
    private Integer getDay(JSONObject dueDate) {
        Integer day = dueDate.getInt("day");
        return day;
    }

    //EFFECTS: returns the dueDate get hour
    private Integer getHour(JSONObject dueDate) {
        Integer hour = dueDate.getInt("hour");
        return hour;
    }

    //EFFECTS: returns the dueDate minute
    private Integer getMinute(JSONObject dueDate) {
        Integer minute = dueDate.getInt("minute");
        return minute;
    }

    //EFFECTS: returns the dueDate
    private DueDate getDateObject(Task t, JSONObject jsonObject) throws JSONException {

        DueDate due = new DueDate();
        Calendar c = Calendar.getInstance();
        if (!(jsonObject.has("due-date"))) {
            throw new JSONException("misspell due-date jsonobject ");
        }
        if (jsonObject.isNull("due-date")) {
            return t.NO_DUE_DATE;
        } else {
            JSONObject dueDate = jsonObject.getJSONObject("due-date");

            if (!(dueB(getYear(dueDate), getMonth(dueDate), getDay(dueDate), getHour(dueDate), getMinute(dueDate)))) {
                throw new JSONException("");
            }
            c.set(getYear(dueDate), getMonth(dueDate), getDay(dueDate), getHour(dueDate), getMinute(dueDate));
            due.setDueDate(c.getTime());

            return due;
        }

    }


}