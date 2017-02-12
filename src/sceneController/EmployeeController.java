package sceneController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import application.Main;
import application.Reset;
import database.Employeedb;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class EmployeeController {
	private static Stage stage;

	@FXML private AnchorPane p_employeeP;
	@FXML private Label lbl_time;
	@FXML private Label lbl_date;
	@FXML private Button btn_checkIn;
	@FXML private Button btn_checkOut;
	@FXML private Button btn_back;
	@FXML private Button btn_exit;
	@FXML private TableView<ObservableList<String>> col_Info;
	@FXML private TableView checkinOut;

	@FXML void initialize(){
		try{
			assert p_employeeP != null : "Anchor pane was not loaded!";
			assert lbl_time != null : "Time was not loaded!";
			assert lbl_date != null : "Date was not loaded!";
			assert btn_checkIn != null : "Check in button was not loaded!";
			assert btn_checkOut != null : "Check out button was not loaded!";
			assert btn_back != null : "Back button was not loaded!";
			assert btn_exit != null : "Exit button was not loaded!";
			assert col_Info != null : "Table was not loaded!";
		}catch(AssertionError ae){
			System.out.println("Assertion Error " + ae.getMessage());
			Main.terminate();
		}
		DateTime();
	}

	public void prepareStageEvents(Stage secStage){
		System.out.println("Preparing employee stage events");
		EmployeeController.stage = secStage;
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				Main.terminate();
			}
		});
	}

	private void DateTime() {
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(0),
						new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent actionEvent) {
						Calendar time = Calendar.getInstance();
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
						lbl_time.setText(simpleDateFormat.format(time.getTime()));
						simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
						lbl_date.setText(simpleDateFormat.format(time.getTime()));
					}
				}
						),
				new KeyFrame(Duration.seconds(1))
				);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		myInfo();

	}

	public void myInfo(){
		String[] columnNames = {"EmployeeID", "FirstName", "Surname", "Age", "AddressLine1", "TownOrCity", "Postcode", "Number"};
		String Query = "SELECT EmployeeID, FirstName, Surname, Age, AddressLine1, TownOrCity, Postcode, Number from Employee";
				
		for (int i = 0; i < columnNames.length; i++) {
			final int finalIdx = i;
			TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
			column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));
			col_Info.getColumns().add(column);
		}
		
		Employeedb dConnect = new Employeedb();
		col_Info.setItems(dConnect.newTableView(Query, columnNames.length));
		attendance();
	}

	public void attendance(){

	}

	public void back(){
		Reset.resetVar();
		Stage back = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/scene/login.fxml"));
			back.setScene(new Scene(root));
			back.setTitle("Login");
			back.show();
			LoginController controller = new LoginController();
			controller.prepareStageEvents(back);
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.close();
	}

	public void exit(){
		Main.terminate();
	}
}