package sceneController;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ManagerController {
	private static Stage stage;


	@FXML private AnchorPane p_ManagerP;
	@FXML private Button btn_addP;

	final TextField firstName = new TextField();
	final TextField secondName = new TextField();
	final TextField age = new TextField();
	final TextField addLine = new TextField();
	final TextField town = new TextField();
	final TextField postcode = new TextField();
	final TextField number = new TextField();

	@FXML void initialize(){
		try{
			assert p_ManagerP != null : "Manager Anchor pane was not loaded";
			assert btn_addP != null : "Add person button was not loaded";
		}catch(AssertionError ae){
			System.out.println("Assertion Error " + ae.getMessage());
			Main.terminate();
		}
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
}