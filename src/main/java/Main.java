
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		//UTF-8 en la consola
		System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
		
		//vamos a cargar la URL para trabajar con este recurso:
		//Class FXMLLoader nor permite cargar todos los .fxml, getClass() recuperamos de la class actual, .getResource() recurso
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
		//vamos a indar el controlado Padre, q contiene todo los controladores
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		Scene scene = new Scene(root); //Scene es como el Panel Raiz= JFrame
		scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());	//añadimos a "scene" el recurso CSS "App.css"
		primaryStage.setScene(scene);  //primaryStage.setScene, le indicamos el "scene" creado al parametro de stage
        primaryStage.setTitle("Control de Stock");
		primaryStage.show();  //.show() carga todo el codigo del primaryStage
		
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
