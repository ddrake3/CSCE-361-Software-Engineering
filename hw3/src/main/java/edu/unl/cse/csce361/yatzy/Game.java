package edu.unl.cse.csce361.yatzy;

import java.util.InputMismatchException;
import java.util.Scanner;


import edu.unl.cse.csce361.yatzy.controller.Controller;
import edu.unl.cse.csce361.yatzy.controller.DiceController;
import edu.unl.cse.csce361.yatzy.controller.NavigationController;
import edu.unl.cse.csce361.yatzy.controller.ScoreController;
import edu.unl.cse.csce361.yatzy.view.GameBoard;
import edu.unl.cse.csce361.yatzy.view.textview.TextGameBoard;

/**
 * <p>The main class for the game of Yatzy.</p>
 *
 * <p>If running from a terminal window that emulates a VT100 terminal then you can use <code>VT100</code> as a
 * command-line argument to cause the cursor to move when refreshing instead of scrolling the screen.</p>
 */
public class Game {
    public static final int NUMBER_OF_DICE = 5;
    public static final int NUMBER_OF_DIE_SIDES;
    
    static {
        NUMBER_OF_DIE_SIDES = promptForNumberOfDieSides();
    }
    
    enum MenuOption {
        SIX_SIDES("Play traditional Yatzy with 6-sided dice"),
        SEVEN_SIDES("Play Ya7zy with 7-sided dice"),
        EXIT("Exit program");

        private String description;

        MenuOption(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Prompts the player to select the number of die faces and returns that value or exits if no value is chosen.
     * @return the number of die sides the player wants
     */
    private static int promptForNumberOfDieSides() {
        int numberOfSides = 0;
        int intOption = 0;
        MenuOption menuOption = MenuOption.EXIT;
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Welcome to Ya7zy by Procrastination Pastimes!");
            for (MenuOption option : MenuOption.values()) {
                System.out.println((option.ordinal() + 1) + " -- " + option.getDescription());
            }
            System.out.println();
            System.out.print("Please select your game preference: ");
            intOption = scanner.nextInt();
            scanner.nextLine();     // consume the newline
            menuOption = MenuOption.values()[intOption - 1];
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException ignored) {
            System.out.println("Please select a valid, in-range integer.");
            menuOption = MenuOption.EXIT;
        } finally {
            switch (menuOption) {
                case SIX_SIDES:
                    numberOfSides = 6;
                    break;
                case SEVEN_SIDES:
                    numberOfSides = 7;
                    break;
                case EXIT:
                    System.out.println("Goodbye.");
                    System.exit(0);
                default:
                    System.err.println("Somehow you selected an impossible option.");
                    System.exit(1);
            }
        }
        return numberOfSides;
    }


    public static void main(String[] args) {
        boolean isVT100 = ((args.length > 0) && args[0].equalsIgnoreCase("vt100"));
        GameBoard board = new TextGameBoard(isVT100);
        Controller.setBoard(board);
        DiceController.getController();
        ScoreController.getController();
        NavigationController.getController().startGame();
    }
}
