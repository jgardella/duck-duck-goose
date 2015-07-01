package com.jgardella.app.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainWindow extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Duck Duck Goose");
		
		StackPane root = new StackPane();
		primaryStage.setScene(new Scene(root, 300, 300));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
