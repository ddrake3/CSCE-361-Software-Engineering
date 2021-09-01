package edu.unl.cse.csce361.yatzy.view.textview;

import edu.unl.cse.csce361.yatzy.Game;
import edu.unl.cse.csce361.yatzy.controller.Command;
import edu.unl.cse.csce361.yatzy.controller.DieCommand;
import edu.unl.cse.csce361.yatzy.controller.GamePhase;
import edu.unl.cse.csce361.yatzy.controller.NavigationCommand;
import edu.unl.cse.csce361.yatzy.controller.ScoringCommand;
import edu.unl.cse.csce361.yatzy.view.DieView;
import edu.unl.cse.csce361.yatzy.view.GameBoard;

import java.util.*;
import java.util.function.Supplier;

/**
 * The game board for a text-based console.
 */
public class TextGameBoard implements GameBoard {

    public static final int DISPLAY_WIDTH = 80;
    public static final int DISPLAY_HEIGHT = 23;

    static final String VT100_HOME = "\u001B[H";
    static final String VT100_CLEAR_SCREEN = "\u001B[2J";
    private final boolean isVT100;
    private final Scanner scanner;

    private boolean playingGame;

    private final StringBox stringBox;
    private final TextDiceView diceView;
    final List<Command> dieCommands;
    private final TextScoreSheetView scoreSheetView;
    final List<Command> scoreCommands;
    List<Command> currentScoreCommands;
    final Map<GamePhase, List<Command>> optionCommands;
    final Set<NavigationCommand> currentNavigationCommands;
    final List<Command> activeCommands;
    List<Command> usedScoreCommands;

    public TextGameBoard() {
        this(false);
    }

    public TextGameBoard(boolean isVT100) {
        stringBox = new StringBox(DISPLAY_HEIGHT, DISPLAY_WIDTH);
        this.isVT100 = isVT100;
        scanner = new Scanner(System.in);
        // Dice
        dieCommands = new LinkedList<>();
        diceView = new TextDiceView();
        // Score sheet
        scoreCommands = new ArrayList<>(3 * Game.NUMBER_OF_DIE_SIDES);    // that's ample
        scoreSheetView = new TextScoreSheetView();
        // Option menus
        optionCommands = new HashMap<>();
        optionCommands.put(GamePhase.READY, new LinkedList<>());
        optionCommands.put(GamePhase.ROLLING, new LinkedList<>());
        optionCommands.put(GamePhase.SCORING, new LinkedList<>());
        currentNavigationCommands = new TreeSet<>();
        // The game
        activeCommands = new LinkedList<>();
        usedScoreCommands = new ArrayList<>();
        setMessage("Welcome to Yatzy!");
        setPrompt("Please enter your selection:");
    }

    @Override
    public void setMessage(String message) {
        stringBox.placeString(" ".repeat(DISPLAY_WIDTH), 0, 0);
        stringBox.placeString(message, 0, 0);
    }

    private void setPrompt(String prompt) {
        stringBox.placeString(prompt + " ", DISPLAY_HEIGHT - 1, 0);
    }

    private List<Command> getCommands() {
        return activeCommands;
    }

    private String createOptionMenu() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : currentNavigationCommands) {
            if (activeCommands.contains(command)) {
                stringBuilder
                        .append(activeCommands.indexOf(command) + 1)
                        .append(". ")
                        .append(command.toString())
                        .append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

    private String clearOptionMenu() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : currentNavigationCommands) {
            if (activeCommands.contains(command)) {
                stringBuilder
                        .append(" ".repeat((int) Math.log10(activeCommands.indexOf(command) + 1) + 1))
                        .append("  ")
                        .append(" ".repeat(command.toString().length()))
                        .append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void addCommand(DieCommand command, DieView dieView) {
        dieCommands.add(command);
        diceView.addDie(dieView, () -> activeCommands.contains(command) ?
                (activeCommands.indexOf(command) + 1) + ". " + command.toString() : "");
    }

    @Override
    public void addCommand(ScoringCommand command, CategorySide side) {
        Supplier<String> labeler = () -> (
                activeCommands.contains(command)
                        ? String.format("%1$2d. ", (activeCommands.indexOf(command) + 1))
                        : usedScoreCommands.contains(command) ? " ✓  " : "    "
        ) + command;
        Supplier<Integer> scorer = command::getCategoryScore;
        scoreCommands.add(command);
        switch (side) {
            case NUMBER_SIDE:
                scoreSheetView.addNumberCategory(labeler, scorer);
                break;
            case COMBO_SIDE:
                scoreSheetView.addComboCategory(labeler, scorer);
                break;
            default:
                System.err.println("Unknown category partition: " + side);
        }
    }

    @Override
    public void addSubtotalCommand(ScoringCommand command, CategorySide side) {
        switch (side) {
            case NUMBER_SIDE:
                scoreSheetView.addNumberCategory(() -> "Left " + command, command::getCategoryScore);
                break;
            case COMBO_SIDE:
                scoreSheetView.addComboCategory(() -> "Right " + command, command::getCategoryScore);
                break;
            default:
                System.err.println("Unknown category partition: " + side);
        }
    }

    @Override
    public void addGrandTotalCommand(ScoringCommand command) {
        scoreSheetView.addGrandTotal(command::toString, command::getCategoryScore);
    }

    @Override
    public void addCommand(NavigationCommand command) {
        boolean isNewlyAddedCommand = currentNavigationCommands.add(command);
        if (isNewlyAddedCommand) {
            activeCommands.add(command);
        }
    }

    @Override
    public void activateDieCommands() {
        activeCommands.addAll(dieCommands);
    }

    @Override
    public void activateScoreCommands() {
        currentScoreCommands = new ArrayList<>(scoreCommands);
        currentScoreCommands.removeAll(usedScoreCommands);
        activeCommands.addAll(currentScoreCommands);
    }

    @Override
    public void deactivateCommand(Command command) {
        activeCommands.remove(command);
        currentScoreCommands.remove(command);
        if (command instanceof NavigationCommand) {
            currentNavigationCommands.remove(command);
        }
    }

    @Override
    public void deactivateDieCommands() {
        dieCommands.forEach(this::deactivateCommand);
    }

    @Override
    public void deactivateScoreCommands() {
        usedScoreCommands = new ArrayList<>(scoreCommands);
        usedScoreCommands.removeAll(currentScoreCommands);
        scoreCommands.forEach(this::deactivateCommand);
    }

    @Override
    public boolean allScoresMade() {
        return usedScoreCommands.size() == scoreCommands.size();
    }


    @Override
    public boolean replaceCommand(Command commandToBeRemoved, Command replacementCommand) {
        Command actualCommandToBeRemoved, actualReplacementCommand;
        boolean correctOrder;
        Set<Command> inactiveCommands = new HashSet<>(dieCommands);
        inactiveCommands.addAll(scoreCommands);
        for (List<Command> commands : optionCommands.values()) {
            inactiveCommands.addAll(commands);
        }
        if (inactiveCommands.contains(commandToBeRemoved)) {
            actualCommandToBeRemoved = commandToBeRemoved;
            actualReplacementCommand = replacementCommand;
            correctOrder = true;
        } else if (inactiveCommands.contains(replacementCommand)) {     // client code got it backwards
            actualCommandToBeRemoved = replacementCommand;
            actualReplacementCommand = commandToBeRemoved;
            correctOrder = false;
        } else {                            // client code using removeAndReplaceCommand instead of addCommand
            actualCommandToBeRemoved = commandToBeRemoved;
            actualReplacementCommand = replacementCommand;
            correctOrder = true;
        }
        if (activeCommands.contains(actualCommandToBeRemoved)) {
            activeCommands.set(activeCommands.indexOf(actualCommandToBeRemoved), actualReplacementCommand);
        }
        Set<List<Command>> allCommands = Set.of(dieCommands, scoreCommands,
                optionCommands.get(GamePhase.READY),
                optionCommands.get(GamePhase.ROLLING), optionCommands.get(GamePhase.SCORING));
        for (List<Command> commands : allCommands) {
            if (commands.contains(actualCommandToBeRemoved)) {
                commands.set(commands.indexOf(actualCommandToBeRemoved), actualReplacementCommand);
            }
        }
        return correctOrder;
    }

    @Override
    public void endGame() {
        playingGame = false;
    }

    @Override
    public void playGame() {
        playingGame = true;
        while (playingGame) {
            stringBox.placeString(diceView.toString(), StringBox.Vertical.BOTTOM, DISPLAY_HEIGHT - 1,
                    StringBox.Horizontal.LEFT, 0);
            stringBox.placeString(scoreSheetView.toString(), StringBox.Vertical.TOP, 2,
                    StringBox.Horizontal.RIGHT, DISPLAY_WIDTH);
            stringBox.placeString(createOptionMenu(), StringBox.Vertical.TOP, 3,
                    StringBox.Horizontal.LEFT, 0);
            System.out.print(isVT100 ? VT100_HOME + VT100_CLEAR_SCREEN + stringBox : stringBox);
            stringBox.placeString(clearOptionMenu(), StringBox.Vertical.TOP, 3,
                    StringBox.Horizontal.LEFT, 0);
            try {
                int choice = scanner.nextInt();
                getCommands().get(choice - 1).execute();
            } catch (InputMismatchException | IndexOutOfBoundsException ignored) {
                setMessage("Please enter the number of a menu item");
            } finally {
                scanner.nextLine();     // consume the newline
            }
        }
    }

}
