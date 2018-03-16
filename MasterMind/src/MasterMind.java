//Random numbers library
import java.util.Random;
//JavaFx UI
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

class Colors {
	// Set of available colors
	enum Color {R, J, B, O, V, N}
	Color color;
	// Function: Random selection of a color
	static Color randomColor() {
	    int pick = new Random().nextInt(Color.values().length);
	    return Color.values()[pick];
	}
}

public class MasterMind extends Application {

	public static void main(String[] args) {
		//UI
		launch(args);
		//Create a random Color
		Colors color = new Colors();
		color.color = Colors.randomColor();
		//Create the computer selection of colors
		Colors[] computerSelection;
		computerSelection = new Colors[4];
		for (int i = 0; i < computerSelection.length; i++) {
			computerSelection[i] = color;
			System.out.println(computerSelection[i].color);
			color.color = Colors.randomColor();
	     }
		//User chooses colors
		Colors[] userSelection;
		userSelection = new Colors[4];
		userSelection[0] = color;
		userSelection[1] = color;
		userSelection[2] = color;
		userSelection[3] = color;
		//Compute how many colors are correct
		int correctScore = 0;
		for (int i = 0; i < computerSelection.length; i++) {
			for (int j = 0; j < userSelection.length; j++) {
				if (computerSelection[i] == userSelection[j]) {
					correctScore++;
				}
			}
		}
		//Compute how many colors are correct and at the right place
		int correctPlace = 0;
		for (int i = 0; i < computerSelection.length; i++) {
			if (computerSelection[i] == userSelection[i]) {
				correctPlace++;
			}
		}
		//Display status on console
		String consoleResult = "";
		consoleResult = "|";
		for (int i = 0; i < userSelection.length; i++) {
			consoleResult += userSelection[i].color;
		}
		consoleResult += "| ";
		consoleResult += Integer.toString(correctScore);
		consoleResult += " | ";
		consoleResult += Integer.toString(correctPlace);
		consoleResult += " | ";
		consoleResult += Integer.toString(1);
		consoleResult += "/10 | ";
		System.out.println(consoleResult);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		//Button btn = new Button("Hello");
		//btn.setOnAction(new EventHandler<ActionEvent>() {
		//	public void handle (ActionEvent event) {
		//		System.out.println("Hellow");
		//	}
		//});
		int gameRows = 10;
		int gameColumns = 4;

		Group root = new Group();
		Sphere[][] grid = new Sphere[gameRows][gameColumns];
		for (int i = 0; i < gameRows; i++) {
			for (int j = 0; j < gameColumns; j++) {
			    Sphere emptySphere = new Sphere();
			    emptySphere.setRadius(20.0);
			    emptySphere.setCullFace(CullFace.FRONT);
			    emptySphere.setTranslateX(50+50*j);
			    emptySphere.setTranslateY(50+50*i);
			    root.getChildren().add(emptySphere);
				grid[i][j] = emptySphere;
			}
		}
		for (int j = 0; j < gameColumns; j++) {
			PhongMaterial material = new PhongMaterial();
		    material.setDiffuseColor(Color.ORANGE);
		    material.setSpecularColor(Color.BLACK);
		    grid[0][j].setMaterial(material);
			grid[0][j].setCullFace(CullFace.BACK);
		}
		
		Scene scene = new Scene(root, 800, 600);
		arg0.setTitle("MasterMind");
		arg0.setScene(scene);
		arg0.show();
		
	}
}
