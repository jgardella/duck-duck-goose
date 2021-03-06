package com.jgardella.app.frontend.controller;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

// Controller and Root for EventTypeView
public class EventTypeView extends VBox
{
	@FXML private Label eventLabel;
	@FXML private TextField eventDirField;
	@FXML private TextField numReqField;
	@FXML private TextField totalField;
	@FXML private ComboBox<String> reqTypeComboBox;
	
	private File eventTypeDirectory;
	
	private int viewNum;
	private EventTypeViewCallback callback;
	
	public interface EventTypeViewCallback
	{
		public void deleteEventTypeView(EventTypeView view);
	}
	
	public EventTypeView(int viewNum, EventTypeViewCallback callback)
	{
		this.viewNum = viewNum;
		this.callback = callback;
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/EventTypeView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try 
		{
			fxmlLoader.load();
			eventLabel.setText("Event Type #" + viewNum);
		}
		catch(IOException exception)
		{
			throw new RuntimeException(exception);
		}
	}
	
	public EventTypeView(int viewNum, EventTypeViewCallback callback, String eventTypeDir, String reqType, String numReqFieldText, String totalFieldText) 
	{
		this.viewNum = viewNum;
		this.callback = callback;
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/EventTypeView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try 
		{
			fxmlLoader.load();
			eventDirField.setText(eventTypeDir);
			eventTypeDirectory = new File(eventTypeDir);
			reqTypeComboBox.setValue(reqType);
			numReqField.setText(numReqFieldText);
			totalField.setText(totalFieldText);
			eventLabel.setText("Event Type #" + viewNum + " (" + eventTypeDirectory.getName() + ")");
		}
		catch(IOException exception)
		{
			throw new RuntimeException(exception);
		}
	}

	/**
	 * @return The selected directory for this event type (null if no directory selected).
	 */
	public File getEventTypeDirectory()
	{
		return eventTypeDirectory;
	}
	
	/**
	 * @return The value of the number req field.
	 */
	public int getNumReqField()
	{
		return Integer.parseInt(numReqField.getText());
	}
	
	/**
	 * @return The value of the total field.
	 */
	public int getTotalField()
	{
		return Integer.parseInt(totalField.getText());
	}
	
	/**
	 * @return The value of the req type combo box.
	 */
	public String getReqType()
	{
		return reqTypeComboBox.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * @return True if all of the fields in the control have valid values, false otherwise.
	 */
	public boolean isValid()
	{
		return eventTypeDirectory != null && numReqField.getText().matches("[0-9]") 
				&& totalField.getText().matches("[0-9]");
	}
	
	@FXML
	protected void handleBrowseButton()
	{
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
		eventTypeDirectory = chooser.showDialog(this.getScene().getWindow());
		
		// update text field
		if(eventTypeDirectory != null)
		{
			eventDirField.setText(eventTypeDirectory.getAbsolutePath());
			eventLabel.setText("Event Type #" + viewNum + " (" + eventTypeDirectory.getName() + ")");
		}
		else
		{
			eventDirField.setText("");
			eventLabel.setText("Event Type #" + viewNum);
		}
	}
	
	@FXML
	protected void handleEventRemoveButton()
	{
		callback.deleteEventTypeView(this);
	}
	
}
