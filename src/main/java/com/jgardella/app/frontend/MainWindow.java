package com.jgardella.app.frontend;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.jgardella.app.backend.MembershipEvaluator;
import com.jgardella.app.backend.Requirement;
import com.jgardella.app.backend.Requirement.ReqType;
import com.jgardella.app.frontend.controller.AttendanceDialog;
import com.jgardella.app.frontend.controller.EventTypeView;
import com.jgardella.app.frontend.controller.EventTypeView.EventTypeViewCallback;

// Main Window root and controller
public class MainWindow extends Application implements EventTypeViewCallback
{
	
	@FXML private BorderPane mainWin;
	
	@FXML private VBox eventTypeVBox;
	@FXML private Button evaluateButton;
	
	private ArrayList<EventTypeView> eventTypeViewList = new ArrayList<EventTypeView>();
	
	private int eventTypeNum = 1;

	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			Parent root = FXMLLoader.load(getClass().getResource("/view/MainWindow.fxml"));
			
			Scene scene = new Scene(root, 400, 500);
			
			primaryStage.setTitle("Duck Duck Goose");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	@FXML
	protected void handleAddEventButton()
	{
		// create new EventTypeView control, add to list and vbox
		EventTypeView eventTypeView = new EventTypeView(eventTypeNum++, this);
		eventTypeViewList.add(eventTypeView);
		eventTypeVBox.getChildren().add(eventTypeView);
		evaluateButton.setDisable(false);
	}
	
	@FXML
	protected void handleEvaluateButton()
	{
		File[] eventDirs = new File[eventTypeViewList.size()];
		ArrayList<Requirement> reqList = new ArrayList<Requirement>();
		
		for(int i = 0; i < eventTypeViewList.size(); i++)
		{
			EventTypeView eventTypeView = eventTypeViewList.get(i);
			if(!eventTypeView.isValid())
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid Event(s)");
				alert.setHeaderText("One or more events are invalid.");
				alert.setContentText("Invalid data in event #" + (i + 1) + ".");
				alert.show();
				return;
			}
			
			// add event dir and req to lists
			eventDirs[i] = eventTypeView.getEventTypeDirectory();
			reqList.add(new Requirement(ReqType.valueOf(eventTypeView.getReqType()), eventTypeView.getNumReqField(),
					eventTypeView.getEventTypeDirectory().getName(), eventTypeView.getTotalField()));
		}
		
		// create evaluator
		MembershipEvaluator evaluator = new MembershipEvaluator();
		
		// add all requirements
		for(Requirement req : reqList)
		{
			evaluator.addRequirement(req);
		}
		
		try 
		{
			evaluator.parseEvents(eventDirs);
		} catch (InvalidFormatException e) 
		{
			showExceptionDialog("Error Parsing XLS", "Encountered error when parsing attendence sheet.", "InvalidFormatException was caught.", e);
			e.printStackTrace();
		} catch (IOException e) 
		{
			showExceptionDialog("Error Reading XLS", "Encounterted error when reading attendence sheet.", "IOException was caught.", e);
			e.printStackTrace();
		}
		
		// evaluate membership
		evaluator.evaluateMembership();
		
		// display membership status
		Stage attendancePopup = new Stage();
		AttendanceDialog dialog = new AttendanceDialog(evaluator.getActiveMembers(), evaluator.getNonactiveMembers());
		
		Scene scene = new Scene(dialog, 350, 450);
		
		attendancePopup.setTitle("Attendance");
		attendancePopup.setScene(scene);
		attendancePopup.setResizable(false);
		attendancePopup.show();
	}
	
	@FXML
	protected void handleExportButton()
	{
			
	}
	
	@FXML
	protected void handleImportButton()
	{
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void deleteEventTypeView(EventTypeView view) 
	{
		eventTypeVBox.getChildren().remove(view);
		eventTypeViewList.remove(view);
		if(eventTypeViewList.size() == 0)
		{
			evaluateButton.setDisable(true);
		}
	}
	
	/**
	 * Shows an exception dialog for the given exception
	 * @param title Title for the dialog
	 * @param headerText Header text for the dialog
	 * @param content Content text for the dialog
	 * @param e Exception to display in dialog
	 */
	public void showExceptionDialog(String title, String headerText, String content, Exception e)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(content);

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
}
