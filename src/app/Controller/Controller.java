package app.Controller;

import java.io.IOException;

import app.Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Controller {
    Model model;
    Stage stage;

    public void setModel(Model model) {
        this.model = model;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void loadView(String fxml, Stage stage, Model model) {
        Parent root = null;
        try {
            System.out.println(fxml);
            root = FXMLLoader.load(Controller.class.getResource(fxml), null, null, c -> {
                try {
                    Controller controller = (Controller) c.newInstance();
                    controller.setModel(model);
                    controller.setStage(stage);
                    return controller;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("The Ultimate To Do App");
        stage.centerOnScreen();
        stage.show();
    }

    public void showAlert(String msg){
        new Alert(AlertType.INFORMATION, msg).showAndWait();
    }

    @FXML
    public void addTask() {
        loadView("/app/View/CreateTask.fxml", stage, model);
    }

    @FXML
    public void addAppointment() {
        loadView("/app/View/CreateAppointment.fxml", stage, model);
    }

    @FXML
    public void addHoliday() {
        loadView("/app/View/CreateHoliday.fxml", stage, model);
    }

    @FXML
    public void addBirthday() {
        loadView("/app/View/CreateBirthday.fxml", stage, model);
    }

    @FXML
    public void addOther() {
        loadView("/app/View/CreateOther.fxml", stage, model);
    }

    @FXML
    public void about() {
        loadView("/app/View/about.fxml", stage, model);
    }

    @FXML
    public void help() {
        loadView("/app/View/help.fxml", stage, model);
    }

    @FXML
    public void back() {
        loadView("/app/View/Calendar.fxml", stage, model);
    }
}
