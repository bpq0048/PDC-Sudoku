/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sudoku;

/**
 * Runs a tutorial game of Sudoku to allow users to learn how to play the game.
 * 
 * @author paige
 */
public class TutorialGame extends Game {
    
    public TutorialGame(User user) {
        super(user);
        this.setupBoard();
        this.startGame();
    }
    
    /**
     * Initializes and runs a tutorial on how to play Sudoku.
     */
    private void tutorial() {
        
        // Introduction to sudoku
        System.out.println("\nWelcome to the Sudoku Tutorial!");

        System.out.println("\nFirst, let's look at the Sudoku board. For this tutorial, we will be using a beginner board, however, there are multiple difficulties you can choose from.");
        System.out.println();
        board.printBoard();

        // Basic Sudoku rules
        System.out.println("In Sudoku, your goal is to fill the grid so that every row, every column, and every 3x3 box contains the numbers 1 through 9.");
        System.out.println("Numbers cannot repeat in any row, column, or 3x3 box.");

        // Instructions for placing a number
        System.out.println("\nLet's start by placing a number.");
        System.out.println("Don't worry if you accidentally select a cell that already has a number present, the game will simply ask you to retype your selection!");
        
        // Instructions for seleting the row
        System.out.print("\nSelect the row (1-9) where you see an empty cell you would like to fill: ");
        int userRow = getValidInput(1, 9) - 1;
        if (userRow == -2) return;
        
        // Instructions for seleting the column
        System.out.print("Select the column (1-9) in the same row to identify the cell: ");
        int userCol = getValidInput(1, 9) - 1;
        if (userCol == -2) return;

        // Check if cell is already filled
        if (!board.isEmptyCell(userRow, userCol)) {
            System.out.println("\nThis cell is already filled. But don't worry, you can re-select a cell until you pick one that is not filled.");
            
            while(!board.isEmptyCell(userRow, userCol)) {
                // Prompts the user to input a row number for the cell they wish to fill
                System.out.print("Select the row (1-9): ");
                userRow = getValidInput(1, 9) - 1;
                if (userRow == -2) return;

                // Prompts the user to input a column number for the cell they wish to fill
                System.out.print("Select the column (1-9): ");
                userCol = getValidInput(1, 9) - 1;
                if (userCol == -2) return;
            }
        }
        
        // Prompts the user to input a number to place in the selected cell
        System.out.print("Now, enter the number (1-9) you believe fits in this cell: ");
        int userNum = getValidInput(1, 9);
        if (userNum == -2) return;
        
        // Verifies if all input information is correct
        System.out.println("\nBefore you finish your input, you can verify that everything you typed was correct.");
        System.out.println("Are you sure you want to add " + userNum + " at (" + (userRow+1) + "," + (userCol+1) + ")?");
        System.out.println("  (1) Yes");
        System.out.println("  (2) No");
        int userVerify = getValidInput(1, 2);
        
        // If the user wants to repeat the process again
        if (userVerify == 2) {
            while(userVerify == 2) {
                System.out.println("No problem! Lets try again.\n");
                board.printBoard();
                
                // Prompts the user to input a row number for the cell they wish to fill
                System.out.print("Select the row (1-9): ");
                userRow = getValidInput(1, 9) - 1;
                if (userRow == -2) return;

                // Prompts the user to input a column number for the cell they wish to fill
                System.out.print("Select the column (1-9): ");
                userCol = getValidInput(1, 9) - 1;
                if (userCol == -2) return;
                
                if (!board.isEmptyCell(userRow, userCol)) {
                    System.out.println("That spot is already filled! Please select a different cell.");
                    while(!board.isEmptyCell(userRow, userCol)) {
                        // Prompts the user to input a row number for the cell they wish to fill
                        System.out.print("Select the row (1-9): ");
                        userRow = getValidInput(1, 9) - 1;
                        if (userRow == -2) return;

                        // Prompts the user to input a column number for the cell they wish to fill
                        System.out.print("Select the column (1-9): ");
                        userCol = getValidInput(1, 9) - 1;
                        if (userCol == -2) return;
                    }
                }

                // Prompts the user to input a number to place in the selected cell
                System.out.print("Enter the number (1-9): ");
                userNum = getValidInput(1, 9);
                if (userNum == -1) return;
                
                System.out.println("Are you sure you want to add " + userNum + " at (" + (userRow+1) + "," + (userCol+1) + ")?");
                System.out.println("  (1) Yes");
                System.out.println("  (2) No");
                userVerify = getValidInput(1, 2);
            }
        }

        // Check if the user's number is correct
        if (completeBoard.getCell(userRow, userCol).getValue() == userNum) {
            board.updateCell(userRow, userCol, userNum);
            System.out.println("Correct! This number fits the Sudoku rules. You're getting the hang of this!");
        } else {
            System.out.println("That was not the correct number for this cell, but don't worry! This is just a tutorial.");
        }
        
        // Explaining the life system
        System.out.println("\nIn a real game of Sudoku, you'll get 3 lives. After 3 failed guesses, you lose.");
        System.out.println("However, since this is a tutorial, you will get untimiled lives!");
        
        // Explaining the hint system
        System.out.println("\nBut don't worry if you ever get stuck, you get 3 hints to help you!");
        System.out.println("Simply type 'H' at any point and you'll be able to input which cell you want a hint for.");
        
        // Offers user to continue playing tutorial or to play the normal game
        System.out.println("\nWould you like to continue this board, or perhaps attempt a proper game?");
        System.out.println("  (1) I would like to continue this board");
        System.out.println("  (2) I would like to attempt a proper game");
        int userChoice = getValidInput(1, 2);
        
        if (userChoice == 1) {
            this.continueTutorial();
        }
        else {
            this.stopTimer();
        }
    }
    
    /**
     * Continues the tutorial game loop. 
     */
    public void continueTutorial() {
        // continues to play the game until finished
        while (!board.isComplete()) {
            
            // Displays the current state of the Sudoku board to the player
            System.out.println();
            board.printBoard();
            
            // Prompts the user to input a row number for the cell they wish to fill
            System.out.print("Select the row (1-9): ");
            int row = getValidInput(1, 9);
            if (row == -1) continue; // Restarts the loop if user paused the game or picked a hint
            
            // Prompts the user to input a column number for the cell they wish to fill
            System.out.print("Select the column (1-9): ");
            int col = getValidInput(1, 9);
            if (col == -1) continue; // Restarts the loop if user paused the game or picked a hint
            
            // Checks if the selected cell is already filled; if so, prompts the user to select a different cell
            if (!board.isEmptyCell(row-1, col-1)) {
                System.out.println("That spot is already filled! Please select a different cell.");
                continue; // Skip the rest of the loop iteration and prompt for input again
            }
            
            // Prompts the user to input a number to place in the selected cell
            System.out.print("Enter the number (1-9): ");
            int num =getValidInput(1, 9);
            if (num == -1) continue; // Restarts the loop if user paused the game or picked a hint
            
            // Verifies if all input information is correct
            System.out.println("Are you sure you want to add " + num + " at (" + row + "," + col + ")?");
            System.out.println("  (1) Yes");
            System.out.println("  (2) No");
            int input = getValidInput(1, 2);
            if (input == 2 || input == -1) continue; // Restarts the loop if user paused the game, picked a hint, or picked no
            
            // Checks the user's move by comparing their input to the completed board's cell value
            if (completeBoard.getCell(row-1, col-1).getValue() == num) {
                // If correct, update the board with the user's input
                board.updateCell(row-1, col-1, num);
                System.out.println("That's Correct!");
            }
            else {
                System.out.println("Incorrect! But that's okay, you have unlimited lives to solve this. You've got this!");
            }
        }
        
        // Ends the game
        this.endGame();
    }

    /**
     * Initializes the game of Sudoku by creating the board.
     * 
     * The method sets the difficulty for the puzzle at beginner (1),  
     * then generates a puzzle and completed board for the user to solve.
     */
    @Override
    protected void setupBoard() {
        // Generates a Sudoku puzzle through generating a complete board and removing cells based on the selected difficulty
        puzzleGenerator = new PuzzleGenerator(1); // Difficulty hard coded to 1 (beginner) for the tutorial
        completeBoard = puzzleGenerator.getCompleteBoard();
        board = puzzleGenerator.getPuzzleBoard();
    }
    
    /**
     * Starts the game of Sudoku.
     * 
     * This method initializes the game by resetting the timer, starting the timer and running the main game loop.
     */
    @Override
    protected void startGame() {
        this.resetTimer(); // Resets the timer
        this.startTimer(); // Starts timer
        this.tutorial();   // Run the main game method
    }

    /**
     * End the game of Sudoku after the user completes the board or runs out of lives.
     * 
     * This method stops the timer and checks if the user has completed the board.
     * If completed, it updates the user's best time if necessary and congratulates them.
     * If not completed, it notifies the user of the loss.
     * The method then prompts the user to either play another round or exit the game.
     */
    @Override
    protected void endGame() {
        this.stopTimer(); // Stops the timer
        
        if (board.isComplete()) {
            // Congragulate the user if solved
            System.out.println("\nCongratulations! You've completed the tutorial!");
            System.out.println("You finished the tutorial in " + timer.getTime());
            
            System.out.println("In a real game, we'll keep track of how fast you solve the board. Try and beat your high score each game!");
            System.out.println("Now, lets try an actual game.");
        }
        else {
            // Notify the user they lost the game
            System.out.println("\nUh oh! You lost! But that's okay, this was just a tutorial.");
            
            System.out.println("In a real game, we'll keep track of how fast you solve the board. Try and beat your high score each game!");
            System.out.println("Now, lets try an actual game.");
        }
    }
}
