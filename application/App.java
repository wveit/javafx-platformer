package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import screen.MyScreen;
import screen.GameScreen;


public class App extends Application{
	@Override
	public void start(Stage primaryStage){
		MyScreen screen = new GameScreen(1200, 800);
		Scene scene = new Scene(new Group(screen));
		primaryStage.setScene(scene);
		primaryStage.setTitle("App");
		primaryStage.show();
		screen.start();
		screen.requestFocus();
	}
}
