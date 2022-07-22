package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Program;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentViewController implements Initializable {
	
	private DepartmentService departmentService;
	private ObservableList<Department> departmentList;
	
	@FXML
	private Button buttonNew;
	
	@FXML
	private Button buttonDelete;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		this.createNewDepartmentFormView("/gui/NewDepartmentFormView.fxml", Utils.currentStage(event));
	}
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
 
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.tableViewDepartment.prefHeightProperty().bind(( (Stage) Program.getMainScene().getWindow()).heightProperty());
	}

	public void updateTableViewDepartment() {
		if (this.departmentService == null) {
			throw new IllegalStateException("DepartmentService is null");
		}
		this.departmentList = FXCollections.observableArrayList(departmentService.findAll());
		this.tableViewDepartment.setItems(departmentList);
	}
	
	private void createNewDepartmentFormView(String viewPath, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
			Pane pane = loader.load();
			
			Stage formStage = new Stage();
			formStage.setTitle("Enter department data");
			formStage.setScene(new Scene(pane));
			formStage.setResizable(false);
			formStage.initOwner(parentStage);
			formStage.initModality(Modality.WINDOW_MODAL);
			formStage.showAndWait();
		}
		catch (IOException e) {
			Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
		}
	}
}