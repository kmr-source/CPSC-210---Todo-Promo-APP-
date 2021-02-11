package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.ListView;
import ui.PomoTodoApp;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoOptionsFxmlFile = new File(todoOptionsPopUpFXML);
    private File todoActionsFxmlFile = new File(todoActionsPopUpFXML);
    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private JFXPopup todoOptionsPopUp;
    private JFXPopup todoActionsPopUp;

    private Task task;

    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // options
        loadTodoOptionsPopup();
        loadTodoOptionsListener();
        // actions
        loadToDoActionsPopup();
        loadTodoActionsListener();

    }

    // load options popup
    private void loadTodoOptionsPopup() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoOptionsPopUpController());
            todoOptionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTodoOptionsListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                todoOptionsPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }


    private class TodoOptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();

            switch (selectedIndex) {
                case 0:
                    Logger.log("TodoOptionsPopUpController", "Edit was selected");
                    EditTask editTask = new EditTask(task);
                    PomoTodoApp.setScene(editTask);
                    break;
                case 1:
                    Logger.log("TodoOptionsPopUpController", "Delete was selected");
                    PomoTodoApp.getTasks().remove(task);
                    PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));
                    break;
                default:
                    Logger.log("TodoOptionsPopUpController", "No action is implemented for the selected option");


            }
            todoOptionsPopUp.hide();

        }
    }

    private void loadToDoActionsPopup() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoActionsPopupController());
            todoActionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadTodoActionsListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                todoActionsPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }


    private class TodoActionsPopupController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();

            switch (selectedIndex) {
                case 0: Logger.log("TodoActionsPopupController", "TODO not implemented");
                    break;
                case 1: Logger.log("TodoActionsPopupController", "Up Next not implemented");
                    break;
                case 2: Logger.log("TodoActionsPopupController", "In Progress inot implemented");
                    break;
                case 3: Logger.log("TodoActionsPopupController", "Done not implemented");
                    break;
                case 4: Logger.log("TodoActionsPopupController", "Pomodoro! not implemented");
                    break;
                default:
                    Logger.log("TodobarActionsPopUpController", "No action is implemented for the selected option");
            }
            todoActionsPopUp.hide();
        }
    }
}
