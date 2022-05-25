package app.Controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import app.Model.Task;
import app.Widget.EventWidget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

public class EventListController extends Controller implements Initializable {

    @FXML
    VBox contentDay;
    @FXML
    VBox contentDeadline;
    @FXML
    Label lb_day;

    @Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
    	String day;
    	if(stage.getUserData()==null){
			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		     Date date = new Date();
		     day=dateFormat.format(date);
		}
    	else
    		day = stage.getUserData() + "";

    	model.getTasks();

		lb_day.setText(day);
		contentDay.getChildren().clear();
		contentDeadline.getChildren().clear();
		List<Task> noDeadLineTasks = model.getNoDeadLineTasks(day);

		show(noDeadLineTasks, contentDay);

		List<Task> deadLineTasks = model.getDeadLineTasks(day);
		show(deadLineTasks, contentDeadline);
	}

    private void show(List<Task> tasks, VBox content){
   	 for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);
            EventWidget eventWidget = new EventWidget((i + 1) + " . " + task.getDescription(), false, task.getType());
            Background background = new Background(new BackgroundFill(Color.valueOf("#fff"), null, null));
            eventWidget.setBackground(background);
            eventWidget.addMouseStyle();
            eventWidget.getText().setFont(Font.font("", FontWeight.BOLD, 12));
            eventWidget.getText().wrappingWidthProperty().set(690);
            if (task.isFinish()) {
                eventWidget.getCheckBox().selectedProperty().set(true);
                eventWidget.setStrikethrough(true);
            }

            eventWidget.getCheckBox().selectedProperty().addListener((observable, oldValue, newValue) -> {
                eventWidget.setStrikethrough(newValue);
                task.setFinish(newValue);
                model.update(task);
                initialize(null, null);
            });

			eventWidget.delete().setOnAction(e -> {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Are you sure you want to delete this " + task.getType() + "?");
				alert.setResultConverter(new Callback<ButtonType, ButtonType>() {
					@Override
					public ButtonType call(ButtonType buttonType) {
						if (buttonType == ButtonType.OK) {
							eventWidget.getChildren().clear();
							model.delete(task);
							initialize(null, null);
						}
						return buttonType;
					}
				});
				alert.showAndWait();
			});

            eventWidget.setOnMouseClicked(e-> {
                    stage.setUserData(task);
				switch (task.getType()) {
					case "task":
						loadView("/app/View/ModifyTask.fxml", stage, model);
						break;
					case "holiday":
						loadView("/app/View/ModifyHoliday.fxml", stage, model);
						break;
					case "birthday":
						loadView("/app/View/ModifyBirthday.fxml", stage, model);
						break;
					case "other":
						loadView("/app/View/ModifyOther.fxml", stage, model);
						break;
					case "appointment":
						loadView("/app/View/ModifyAppointment.fxml", stage, model);
						break;
				}
            });

            content.getChildren().add(eventWidget);
        }
   }
}
