import java.util.Random;

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

public class MasterMind {

	public static void main(String[] args) {
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
}
