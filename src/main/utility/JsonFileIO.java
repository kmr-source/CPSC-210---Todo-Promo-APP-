package utility;



import model.Task;
import org.json.JSONArray;
import parsers.TaskParser;
import persistence.Jsonifier;


import java.io.*;
import java.util.List;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");

    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() throws IOException {
        TaskParser taskParser = new TaskParser();
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(jsonDataFile));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskParser.parse(sb.toString());
    }



    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {


        try {
            JSONArray taskJson = Jsonifier.taskListToJson(tasks);
            String fileContent = taskJson.toString(1);
            BufferedWriter writer = new BufferedWriter(new FileWriter(jsonDataFile));
            writer.write(fileContent);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
