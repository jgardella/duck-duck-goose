package com.jgardella.app.frontend.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import com.jgardella.app.backend.Member;

// Controller and Root for AttendanceDialog
public class AttendanceDialog extends HBox
{
	
	@FXML private Label activeLabel, inactiveLabel;
	@FXML private VBox activeMembersVBox, inactiveMembersVBox;
	
	private ArrayList<Member> activeMembers;
	private ArrayList<Member> inactiveMembers;
	
	public AttendanceDialog(ArrayList<Member> activeMembers, ArrayList<Member> inactiveMembers)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AttendanceDialog.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		this.activeMembers = activeMembers;
		this.inactiveMembers = inactiveMembers;
		
		try 
		{
			fxmlLoader.load();
			
			// insert info on active members
			activeLabel.setText("Active Members (" + activeMembers.size() + ")");
			for(Member member : activeMembers)
			{
				activeMembersVBox.getChildren().add(new Label(member.getFullName()));
			}
			
			// insert info on inactive members
			inactiveLabel.setText("Inactive Members (" + inactiveMembers.size() + ")");
			for(Member member : inactiveMembers)
			{
				inactiveMembersVBox.getChildren().add(new Label(member.getFullName()));
			}
		}
		catch(IOException exception)
		{
			throw new RuntimeException(exception);
		}
	}
	
	@FXML
	protected void handleExportActiveButton()
	{
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File memberFile = chooser.showSaveDialog(this.getScene().getWindow());
		writeMemberListToFile(memberFile, activeMembers);
	}
	
	@FXML
	protected void handleExportInactiveButton()
	{
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File memberFile = chooser.showSaveDialog(this.getScene().getWindow());
		writeMemberListToFile(memberFile, inactiveMembers);
	}
	
	private void writeMemberListToFile(File file, ArrayList<Member> memberList)
	{
		if(file != null)
		{
			try(BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
			{
				for(int i = 0; i < memberList.size(); i++)
				{
					writer.write((i + 1) + ". " + memberList.get(i).getFullName() + "\n");
				}
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
}
