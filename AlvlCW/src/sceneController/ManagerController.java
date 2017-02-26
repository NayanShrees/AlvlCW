package sceneController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import application.Main;
import application.Reset;
import database.DatabaseConnect;
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

public class ManagerController {
	private static Stage stage;

	@FXML private AnchorPane p_ManagerP;
	@FXML private Button btn_addP;
	@FXML private Label lbl_time;
	@FXML private Label lbl_date;
	@FXML private TableView<ObservableList<String>> tbl_EmpCheck;

	@FXML void initialize(){
		try{
			assert p_ManagerP != null : "Manager Anchor pane was not loaded";
			assert btn_addP != null : "Add person button was not loaded";
		}catch(AssertionError ae){
			System.out.println("Assertion Error " + ae.getMessage());
			Main.terminate();
		}
		DateTime();
	}

	public void prepareStageEvents(Stage secStage){
		System.out.println("Preparing stage events");
		ManagerController.stage = secStage;
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
		attendance();
	}
	
	
	public void attendance(){
		String[] columnNames = {"EmployeeID", "First Name", "Second Name", "Date", "Check In", "Check Out"};
		String Query = "SELECT Employee.EmployeeID, Employee.FirstName, Employee.Surname, CheckInOut.Date, CheckInOut.CheckIn, CheckInOut.CheckOut, CheckInOut.CheckedIn from CheckInOut INNER JOIN Employee ON Employee.EmployeeID = CheckInOut.EmployeeID";

		for (int i = 0; i < columnNames.length; i++) {
			final int finalIdx = i;
			TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
			column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));
			tbl_EmpCheck.getColumns().add(column);
		}

		tbl_EmpCheck.setItems(DatabaseConnect.newTableView(Query, columnNames.length));
	}
	
	public void addBtn(ActionEvent event){
		Parent root;
		try {
			stage.close();
			root = FXMLLoader.load(getClass().getResource("/scene/Insert.fxml"));
			Stage addStage = new Stage();
			addStage.setTitle("Insert");
			addStage.setScene(new Scene(root));
			addStage.show();
			InsertController controller = new InsertController();
			controller.prepareStageEvents(addStage);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			Main.terminate();
		}
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