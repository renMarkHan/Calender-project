package app.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import app.Model.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

public class ModifyHolidayController extends Controller implements Initializable {

    @FXML
    DatePicker dp1;
    @FXML
    TextField holidayName;
    @FXML
    DatePicker dp2;

    Task task;

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

        task = ((Task)stage.getUserData());
        dp1.setValue(LocalDate.parse(task.getPreferDate()));

        if (task.getDeadlineDate() != null) {
            dp2.setValue(LocalDate.parse(task.getDeadlineDate()));
        }
        holidayName.setText(task.getDescription());
    }

    @FXML
    public void save(){
        LocalDate preferDate = dp1.getValue();
        String desc = holidayName.getText();
        if (preferDate == null && desc.isEmpty()) {
            showAlert("Prefer date and Holiday name cannot be empty!");
            return;
        }
        if (desc.isEmpty()) {
            showAlert("Holiday name can not be empty!");
            return;
        }
        if (preferDate == null) {
            showAlert("Prefer date can not be empty!");
            return;
        }

        String deadlineDate = dp2.getValue() == null ? null : dp2.getValue().toString();
        if (deadlineDate != null && preferDate.isAfter(dp2.getValue())) {
            showAlert("Deadline Date must be later than your Prefer Date");
            return;
        }

        Task modifyTask = new Task(preferDate.toString(), deadlineDate, desc, false, 1,"holiday");
        modifyTask.setId(task.getId());

        if(model.update(modifyTask)){
            showAlert("Modify Success!");
            back();
        }else {
            showAlert("Modify Fail!");
        }

    }

}
