package app.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import app.Model.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class CreateTaskController extends Controller implements Initializable {

    @FXML
    DatePicker dp1;
    @FXML
    TextField taskName;
    @FXML
    DatePicker dp2;

    StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
    	final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		@Override
		public String toString(LocalDate object) {
			if (object != null) {
				return dateFormatter.format(object);
			}
			return "";
		}

		@Override
		public LocalDate fromString(String string) {
			if (string != null && !string.isEmpty()) {
				return LocalDate.parse(string,dateFormatter);
			}
			return null;
		}
	};


    @Override
    public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

    	dp1.setConverter(converter);
    	dp2.setConverter(converter);
    }

    @FXML
    public void save(){
        LocalDate preferDate = dp1.getValue();
        String desc = taskName.getText();
        if (preferDate == null && desc.isEmpty()) {
            showAlert("Prefer date and Task name cannot be empty!");
            return;
        }
        if (desc.isEmpty()) {
            showAlert("Task name can not be empty!");
            return;
        }
        if (preferDate == null) {
            showAlert("Prefer date can not be empty!");
            return;
        }

        int priority = 1;

        String deadlineDate = dp2.getValue() == null ? null : dp2.getValue().toString();

        boolean isNotification = false;

        if (deadlineDate != null && preferDate.isAfter(dp2.getValue())) {
            showAlert("Deadline Date must be later than your Prefer Date");
            return;
        }
        Task task = new Task(preferDate.toString(), deadlineDate, desc, isNotification, priority,"task");
        if(model.add(task)){
            showAlert("Add Success!");
            back();
        }else {
            showAlert("Add Fail!");
        }

    }

}
