package com.jgardella.app.frontend.controller;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

// Controller and Root for EventTypeView
public class EventTypeView extends GridPane
{
	@FXML private TextField eventDirField;
	
	private File eventTypeDirectory;
	
	public EventTypeView()
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/EventTypeView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try 
		{
			fxmlLoader.load();
		}
		catch(IOException exception)
		{
			throw new RuntimeException(exception);
		}
	}
	
	@FXML
	protected void handleBrowsePress()
	{
		DirectoryChooser chooser = new DirectoryChooser();
		eventTypeDirectory = chooser.showDialog(this.getScene().getWindow());
		
		// update text field
		if(eventTypeDirectory != null)
		{
			eventDirField.setText(eventTypeDirectory.getAbsolutePath());
		}
		else
		{
			eventDirField.setText("");
		}
	}
	
}
