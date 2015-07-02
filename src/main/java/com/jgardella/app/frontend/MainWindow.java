package com.jgardella.app.frontend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.jgardella.app.backend.Member;
import com.jgardella.app.backend.MembershipEvaluator;
import com.jgardella.app.backend.Requirement;
import com.jgardella.app.backend.Requirement.ReqType;
import com.jgardella.app.frontend.controller.EventTypeView;

// Main Window root and controller
public class MainWindow extends Application 
{
	
	@FXML private BorderPane mainWin;
	
	@FXML private VBox eventTypeVBox;
	
	private ArrayList<EventTypeView> eventTypeViewList = new ArrayList<EventTypeView>();

	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			Parent root = FXMLLoader.load(getClass().getResource("/view/MainWindow.fxml"));
			
			Scene scene = new Scene(root, 400, 500);
			
			primaryStage.setTitle("Duck Duck Goose");
			primaryStage.setScene(scene);
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
		EventTypeView eventTypeView = new EventTypeView(eventTypeViewList.size() + 1);
		eventTypeViewList.add(eventTypeView);
		eventTypeVBox.getChildren().add(eventTypeView);
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
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Parsing XLS");
			alert.setHeaderText("Encountered error when parsing attendence sheet.");
			alert.setContentText("InvalidFormatException was caught.");
			alert.show();
			
			e.printStackTrace();
		} catch (IOException e) 
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Reading XLS");
			alert.setHeaderText("Encounterted error when reading attendence sheet.");
			alert.setContentText("IOException was caught.");
			alert.show();
			
			e.printStackTrace();
		}
		
		// evaluate membership
		evaluator.evaluateMembership();
		
		ArrayList<Member> activeMembers = evaluator.getActiveMembers();
		
		ArrayList<Member> inactiveMembers = evaluator.getNonactiveMembers();
		// display membership status
		
	}
	
	@FXML
	protected void handleExportButton()
	{
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
