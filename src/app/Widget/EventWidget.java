package app.Widget;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EventWidget extends HBox {

    Text text = new Text();
    CheckBox checkBox = new CheckBox();

    public EventWidget(String text, String type) {
        this(text, true, false, type);
    }

    public EventWidget(String text, boolean hasLiStyle, String type) {
        this(text, hasLiStyle, true, type);
    }

    public EventWidget(String str, boolean hasLiStyle, boolean hasCheckBox, String type) {
        super();
        setAlignment(Pos.CENTER_LEFT);

        //dot on the calendar
        if (hasLiStyle) {
            Text e = new Text("\u2022 ");
            e.setFont(new Font(12));

            if (type.equals("task")){
                e.setFill(Color.DARKBLUE);
            }
            else if (type.equals("holiday")){
                e.setFill(Color.DARKGREEN);
            }
            else if (type.equals("birthday")){
                e.setFill(Color.RED);
            }
            else if (type.equals("other")){
                e.setFill(Color.BLACK);
            }
            else if (type.equals("appointment")){
                e.setFill(Color.PURPLE);
            }

			getChildren().add(e);
        }
        // TODO: Add color to the string according to its event type
        if (type.equals("task")){
            text.setFill(Color.DARKBLUE);
        }
        else if (type.equals("holiday")){
            text.setFill(Color.DARKGREEN);
        }
        else if (type.equals("birthday")){
            text.setFill(Color.RED);
        }
        else if (type.equals("other")){
            text.setFill(Color.BLACK);
        }
        else if (type.equals("appointment")){
            text.setFill(Color.PURPLE);
        }

        text.setText(str);
        getChildren().add(text);
        if (hasCheckBox) {
            setSpacing(6);
            getChildren().add(checkBox);
        }
        
    }

	public void addMouseStyle() {
		setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event paramT) {
                setStyle("-fx-background-color: lightblue");
			}
		});

		setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event paramT) {
                setStyle("-fx-background-color: white");
			}
		});
	}

	public Button delete(){
        // TODO: Make this delete button more natural
        Button delete = new Button("x");
        delete.setPrefSize(10,10);
        delete.setStyle("-fx-text-fill: red");
        delete.setId("delete");

        getChildren().add(delete);

        return delete;
	}

	public void setStrikethrough(boolean f){
        text.setStrikethrough(f);
    }
    
    public CheckBox getCheckBox(){
        return checkBox;
    }
    
    public Text getText(){
        return text;
    }
}
