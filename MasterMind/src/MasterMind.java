//Random numbers library
import java.util.Random;
//JavaFx UI and elements
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
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

class GameParameters {
	int tentative;
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
		int gameRows = 10;
		int gameColumns = 4;
		int possibleColors = 6;
		GameParameters param = new GameParameters();
		param.tentative = 0;

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
		
		Sphere[] userProposition = new Sphere[gameColumns];
		for (int i = 0; i < gameColumns; i++) {
			Sphere emptySphere = new Sphere();
		    emptySphere.setRadius(40.0);
		    emptySphere.setCullFace(CullFace.FRONT);
		    emptySphere.setTranslateX(400+110*i);
		    emptySphere.setTranslateY(50);
		    root.getChildren().add(emptySphere);
			userProposition[i] = emptySphere;
		}
		Sphere[][] userChoices = new Sphere[possibleColors][gameColumns];
		for (int i = 0; i < possibleColors; i++) {
			for (int j = 0; j < gameColumns; j++) {
			    Sphere emptySphere = new Sphere();
			    emptySphere.setRadius(20.0);
			    emptySphere.setCullFace(CullFace.BACK);
			    if ((i % 2) == 0) {
			    	emptySphere.setTranslateX(375+110*j);
				    emptySphere.setTranslateY(125+25*i);
			    } else {
			    	emptySphere.setTranslateX(425+110*j);
				    emptySphere.setTranslateY(125+25*(i-1));
			    }
			    //emptySphere.setTranslateX(400+100*j);
			    //emptySphere.setTranslateY(150+50*i);
			    PhongMaterial material = new PhongMaterial();
			    material.setSpecularColor(Color.BLACK);
			    switch (i) {
	            case 0:  material.setDiffuseColor(Color.RED);
	                    break;
	            case 1:  material.setDiffuseColor(Color.YELLOW);
                		break;
	            case 2:  material.setDiffuseColor(Color.BLUE);
                		break;
	            case 3:  material.setDiffuseColor(Color.ORANGE);
                		break;
	            case 4:  material.setDiffuseColor(Color.GREEN);
                		break;
	            case 5:  material.setDiffuseColor(Color.BLACK);
	            		material.setSpecularColor(Color.WHITE);
                		break;
			    }
			    emptySphere.setMaterial(material);
			    emptySphere.setCullFace(CullFace.BACK);
			    switch (j) {
	            case 0:  emptySphere.setOnMouseClicked(event -> {
					userProposition[0].setCullFace(CullFace.BACK);
					userProposition[0].setMaterial(material);
				});
	                    break;
	            case 1:  emptySphere.setOnMouseClicked(event -> {
					userProposition[1].setCullFace(CullFace.BACK);
					userProposition[1].setMaterial(material);
				});
                		break;
	            case 2:  emptySphere.setOnMouseClicked(event -> {
					userProposition[2].setCullFace(CullFace.BACK);
					userProposition[2].setMaterial(material);
				});
                		break;
	            case 3:  emptySphere.setOnMouseClicked(event -> {
					userProposition[3].setCullFace(CullFace.BACK);
					userProposition[3].setMaterial(material);
				});
                		break;
			    }
			    root.getChildren().add(emptySphere);
				userChoices[i][j] = emptySphere;
			}
		}
		Sphere[][] userFeedback = new Sphere[gameRows][gameColumns];
		for (int i = 0; i < gameRows; i++) {
			for (int j = 0; j < gameColumns; j++) {
			    Sphere emptySphere = new Sphere();
			    emptySphere.setRadius(5.0);
			    emptySphere.setCullFace(CullFace.FRONT);
			    emptySphere.setTranslateX(240+(j % 2)*20);
			    if (j >= 2) {
			    	emptySphere.setTranslateY(60 + 50*i);
			    } else {
			    	emptySphere.setTranslateY(40 + 50*i);
			    }
			    PhongMaterial material = new PhongMaterial();
			    material.setSpecularColor(Color.BLACK);
			    material.setDiffuseColor(Color.RED);
			    emptySphere.setMaterial(material);
			    root.getChildren().add(emptySphere);
			    userFeedback[i][j] = emptySphere;
			}
		}
		Button btn = new Button("Confirm your selection");
		btn.setTranslateX(500);
		btn.setTranslateY(275);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle (ActionEvent event) {
				for (int j = 0; j < gameColumns; j++) {
				    grid[param.tentative][j].setMaterial(userProposition[j].getMaterial());
					grid[param.tentative][j].setCullFace(CullFace.BACK);
				}
				param.tentative++;
				if (param.tentative >= 10) {
					DropShadow ds = new DropShadow();
					ds.setOffsetY(3.0f);
					ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

					Text t = new Text("Game Over");
					t.setEffect(ds);
					t.setCache(true);
					t.setFont(Font.font ("Verdana", 80));
					t.setFill(Color.RED);
					t.setX(300);
					t.setY(550);
					root.getChildren().add(t);
				}
			}
		});
		root.getChildren().add(btn);
		
		//userChoices[0][0].setOnMouseClicked(event -> {
		//		userProposition[1].setCullFace(CullFace.BACK);
		//});
		
		Scene scene = new Scene(root, 800, 600);
		arg0.setTitle("MasterMind");
		arg0.setScene(scene);
		arg0.show();
		
	}
}
