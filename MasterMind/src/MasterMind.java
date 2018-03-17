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
	// Define a set of available colors
	enum Color {R, J, B, O, V, N}
	Color color;
	// Select a random color
	static Color randomColor() {
	    int pick = new Random().nextInt(Color.values().length);
	    return Color.values()[pick];
	}
}

class GameParameters {
	int tentative;
	int gameRows;
	int gameColumns;
	int possibleColors;
	int screenWidth;
	int screenHeight;
	String gameTitle;
	
	void initParameters() {
		tentative = 0;
		gameRows = 10;
		gameColumns = 4;
		possibleColors = 6;
		screenWidth = 800;
		screenHeight = 600;
		gameTitle = "MasterMind Game - Gonzague Leruth\'s Premium Edition";
	}
}

class ComputerSelectionManager {
	Colors[] computerSelection;
	// Create a computer random selection of colors
	Colors[] createComputerSelection() {
		computerSelection = new Colors[4];
		for (int i = 0; i < computerSelection.length; i++) {
			Colors color = new Colors();
			color.color = Colors.randomColor();
			computerSelection[i] = color;
			System.out.println(computerSelection[i].color);
		}
		return computerSelection;
	}
}

class PlaceSphereManager{
	// Place a sphere on the screen
	Sphere placeSphere(Group root, double radius, boolean visible, int x, int y) {
		Sphere sphere = new Sphere();
	    sphere.setRadius(radius);
	    if (visible) {
	    	sphere.setCullFace(CullFace.BACK);
	    } else {
	    	sphere.setCullFace(CullFace.FRONT);
	    }
	    sphere.setTranslateX(x);
	    sphere.setTranslateY(y);
	    root.getChildren().add(sphere);
	    return sphere;
	}
	void assignColor(Color color, Sphere sphere) {
	    PhongMaterial material = new PhongMaterial();
	    material.setSpecularColor(Color.BLACK);
	    if (color == Color.BLACK) {
	    	material.setSpecularColor(Color.WHITE);
	    }
	    material.setDiffuseColor(color);
	    sphere.setCullFace(CullFace.BACK);
	    sphere.setMaterial(material);
	}
	void assignColorEvent(Color color, Sphere sphereToClick, Sphere sphereToAffect) {
		assignColor(color, sphereToClick);
		sphereToClick.setOnMouseClicked(event -> {
			assignColor(color, sphereToAffect);
		});
	}
}

class SphereGridManager{
	// Create a grid of spheres
	Sphere[][] createSphereGrid(Group root, int columns, int rows, double radius, boolean visible, int x_orig, int y_orig, int x_spacing, int y_spacing) {
		Sphere[][] grid = new Sphere[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				PlaceSphereManager placeSphereManager = new PlaceSphereManager();
				Sphere sphere = placeSphereManager.placeSphere(root, radius, visible, x_orig + j*x_spacing, y_orig + i*y_spacing);
				grid[i][j] = sphere;
			}
		}
		return grid;
	}
}

class GameGridManager{
	// Create the game grid of spheres
	Sphere[][] createGameGrid(Group root, GameParameters param) {
		SphereGridManager gridManager = new SphereGridManager();
		Sphere[][] grid = gridManager.createSphereGrid(root, param.gameColumns, param.gameRows, 20.0, false, 50, 50, 50, 50);
		return grid;
	}
}

class ScoreGridManager{
	// Create the score grid of spheres
		Sphere[][] createScoreGrid(Group root, GameParameters param) {
			SphereGridManager gridManager = new SphereGridManager();
			Sphere[][] grid = gridManager.createSphereGrid(root, param.gameColumns, param.gameRows, 5.0, false, 0, 0, 0, 0);
			for (int i = 0; i < param.gameRows; i++) {
				for (int j = 0; j < param.gameColumns; j++) {
				    grid[i][j].setTranslateX(240+(j % 2)*20);
				    if (j >= 2) {
				    	grid[i][j].setTranslateY(60 + 50*i);
				    } else {
				    	grid[i][j].setTranslateY(40 + 50*i);
				    }
				}
			}	
			return grid;
		}
}

class UserSelectionLineManager{
	// Create the user selection line of spheres
	Sphere[] createUserSelectionLine(Group root, int columns, double radius, boolean visible, int x_orig, int y_orig, int x_spacing) {
		Sphere[] line = new Sphere[columns];
		for (int i = 0; i < columns; i++) {
			PlaceSphereManager placeSphereManager = new PlaceSphereManager();
			Sphere sphere = placeSphereManager.placeSphere(root, radius, visible, x_orig + i*x_spacing, y_orig);
			line[i] = sphere;
		}
		return line;	
	}
}

class PickGridManager{
	// Create the grid of color spheres to be picked
		Sphere[][] createPickGrid(Group root, GameParameters param, Sphere[] userSelectionLine) {
			SphereGridManager gridManager = new SphereGridManager();
			Sphere[][] grid = gridManager.createSphereGrid(root, param.possibleColors, param.gameColumns, 20.0, true, 0, 0, 0, 0);
			
			for (int i = 0; i < param.possibleColors; i++) {
				for (int j = 0; j < param.gameColumns; j++) {
					if ((i % 2) == 0) {
						grid[j][i].setTranslateX(375+110*j);
						grid[j][i].setTranslateY(125+25*i);
				    } else {
				    	grid[j][i].setTranslateX(425+110*j);
				    	grid[j][i].setTranslateY(125+25*(i-1));
				    }
					PlaceSphereManager placeSphereManager = new PlaceSphereManager();
					switch (i) {
		            	case 0:  placeSphereManager.assignColorEvent(Color.RED, grid[j][i], userSelectionLine[j]);
		                    break;
		            	case 1:  placeSphereManager.assignColorEvent(Color.YELLOW, grid[j][i], userSelectionLine[j]);
	                		break;
		            	case 2:  placeSphereManager.assignColorEvent(Color.BLUE, grid[j][i], userSelectionLine[j]);
	                		break;
		            	case 3:  placeSphereManager.assignColorEvent(Color.ORANGE, grid[j][i], userSelectionLine[j]);
	                		break;
		            	case 4:  placeSphereManager.assignColorEvent(Color.GREEN, grid[j][i], userSelectionLine[j]);
	                		break;
		            	case 5:  placeSphereManager.assignColorEvent(Color.BLACK, grid[j][i], userSelectionLine[j]);
	                		break;
				    	}
				}
			}
			return grid;
		}		
}

class ExtraUIElementsManager{
	void addExtraUIElements(Group root, GameParameters param, Sphere[][] gameGrid, Sphere[] userSelectionLine){
		Button btn = new Button("Confirm your selection");
		btn.setTranslateX(500);
		btn.setTranslateY(275);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle (ActionEvent event) {
				TentativeManager tentativeManager = new TentativeManager();
				tentativeManager.newTentative(root, param, gameGrid, userSelectionLine);
			}
		});
		root.getChildren().add(btn);
	}
}

class TentativeManager{
	void newTentative(Group root, GameParameters param, Sphere[][] gameGrid, Sphere[] userSelectionLine) {
		if (param.tentative < 10) {
			// if enough attempts left
			// check validity
			for (int j = 0; j < param.gameColumns; j++) {
				// TO CONTINUE
			}
			// and take new user proposition into account
			for (int j = 0; j < param.gameColumns; j++) {
				gameGrid[param.tentative][j].setMaterial(userSelectionLine[j].getMaterial());
				gameGrid[param.tentative][j].setCullFace(CullFace.BACK);
			}
			param.tentative++;
		}
		else {
			// otherwise, display game over text
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
}

class UIElementsManager{
	void positionUIElements(Group root, GameParameters param, Colors[] computerSelection) {
		// Game grid
		GameGridManager gameGridManager = new GameGridManager();
		Sphere[][] grid = gameGridManager.createGameGrid(root, param);
		// Score grid
		ScoreGridManager scoreGridManager = new ScoreGridManager();
		Sphere[][] scoreGrid = scoreGridManager.createScoreGrid(root, param);
		// User new selection line
		UserSelectionLineManager userSelectionLineManager = new UserSelectionLineManager();
		Sphere[] userSelectionLine = userSelectionLineManager.createUserSelectionLine(root, param.gameColumns, 40.0, false, 400, 50, 110);	
		// Colors to be picked grid
		PickGridManager pickGridManager = new PickGridManager();
		Sphere[][] pickGrid = pickGridManager.createPickGrid(root, param, userSelectionLine);
		// Extra UI elements
		ExtraUIElementsManager extraUIelementsManager = new ExtraUIElementsManager();
		extraUIelementsManager.addExtraUIElements(root, param, grid, userSelectionLine);
		
		
		
		
		
		
		
		
		
		
		
		
		//Create a random Color
		Colors color = new Colors();
		color.color = Colors.randomColor();
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
}

class MainScreenManager{
	static void generateScreen(Stage arg0) {
		// Set game parameters
		GameParameters param = new GameParameters();
		param.initParameters();
		
		// Create the computer selection of colors
		ComputerSelectionManager computerSelectionManager = new ComputerSelectionManager();
		Colors[] computerSelection = computerSelectionManager.createComputerSelection();
		
		// Set the screen
		Group root = new Group();
		UIElementsManager uiElementsManager = new UIElementsManager();
		uiElementsManager.positionUIElements(root, param, computerSelection);
		Scene scene = new Scene(root, param.screenWidth, param.screenHeight);
		arg0.setTitle(param.gameTitle);
		arg0.setScene(scene);
		arg0.show();
	}
}

public class MasterMind extends Application {
	//Main function
	public static void main(String[] args) {
		launch(args);
	}
	//JavaFX UI management
	public void start(Stage arg0) throws Exception {
		MainScreenManager.generateScreen(arg0);
	}
}
