package platformer.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import platformer.engine.screen.MyScreen;
import javafx.scene.Group;


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
	
	public static void main(String[] args){
		Application.launch(args);
	}
}
