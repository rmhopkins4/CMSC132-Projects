package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game,
 * specifically.
 * 
 * @author Dept of Computer Science, UMCP
 */

public class ClearCellGameModel extends GameModel {
	
	/* Include whatever instance variables you think are useful. */
	private int score;
	private Random random;
	
	
	/**
	 * Defines a board with empty cells.  It relies on the
	 * super class constructor to define the board.
	 * 
	 * @param rows number of rows in board
	 * @param cols number of columns in board
	 * @param random random number generator to be used during game when
	 * rows are randomly created
	 */
	public ClearCellGameModel(int rows, int cols, Random random) {
		super(rows, cols);
		this.random = random;
	}

	/**
	 * The game is over when the last row (the one with index equal
	 * to board.length-1) contains at least one cell that is not empty.
	 */
	public boolean isGameOver() {
		for(int i = 0; i < board[0].length; i++) {
			if(!(board[board.length - 1][i].equals(BoardCell.EMPTY))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the player's score.  The player should be awarded one point
	 * for each cell that is cleared.
	 * 
	 * @return player's score
	 */
	public int getScore() {
		return score;
	}

	
	/**
	 * This method must do nothing in the case where the game is over.
	 * 
	 * As long as the game is not over yet, this method will do 
	 * the following:
	 * 
	 * 1. Shift the existing rows down by one position.
	 * 2. Insert a row of random BoardCell objects at the top
	 * of the board. The row will be filled from left to right with cells 
	 * obtained by calling BoardCell.getNonEmptyRandomBoardCell().  (The Random
	 * number generator passed to the constructor of this class should be
	 * passed as the argument to this method call.)
	 */
	public void nextAnimationStep() {
		if(isGameOver()) {
			return;
		}
		BoardCell[][] tempBoard = new BoardCell[board.length][board[0].length];
		for(int i = 0; i < board.length - 1; i++) {
			for(int j = 0; j < board[0].length; j++) {
				tempBoard[i+1][j] = board[i][j];
			}
		}
		for(int i = 0; i < board[0].length; i++) {
			tempBoard[0][i] = BoardCell.getNonEmptyRandomBoardCell(random);
		}
		board = tempBoard;
	}

	/**
	 * This method is called when the user clicks a cell on the board.
	 * If the selected cell is not empty, it will be set to BoardCell.EMPTY, 
	 * along with any adjacent cells that are the same color as this one.  
	 * (This includes the cells above, below, to the left, to the right, and 
	 * all in all four diagonal directions.)
	 * 
	 * If any rows on the board become empty as a result of the removal of 
	 * cells then those rows will "collapse", meaning that all non-empty 
	 * rows beneath the collapsing row will shift upward. 
	 * 
	 * @throws IllegalArgumentException with message "Invalid row index" for 
	 * invalid row or "Invalid column index" for invalid column.  We check 
	 * for row validity first.
	 */
	public void processCell(int rowIndex, int colIndex) {
		
		if(rowIndex < 0 || rowIndex >= board.length) {
			throw new IllegalArgumentException("Invalid row index");
		}
		if(colIndex < 0 || colIndex >= board[0].length) {
			throw new IllegalArgumentException("Invalid column index");
		}
		
		if(board[rowIndex][colIndex].equals(BoardCell.EMPTY)) {
			return;
		}
		String pickedColor = board[rowIndex][colIndex].getName();
		
		removeIfColor(rowIndex, colIndex, pickedColor);
		if(rowIndex > 0) {
			removeIfColor(rowIndex - 1, colIndex, pickedColor); //LEFT
			if(colIndex > 0) {
				removeIfColor(rowIndex - 1, colIndex - 1, pickedColor); //LEFT UP
			}
			if(colIndex < board[0].length - 1) {
				removeIfColor(rowIndex - 1, colIndex + 1, pickedColor);
			}
		}
		if(rowIndex < board.length - 1) {
			removeIfColor(rowIndex + 1, colIndex, pickedColor);
			if(colIndex > 0) {
				removeIfColor(rowIndex + 1, colIndex - 1, pickedColor);
			}
			if(colIndex < board[0].length - 1) {
				removeIfColor(rowIndex + 1, colIndex + 1, pickedColor);
			}
		}
		if(colIndex > 0) {
			removeIfColor(rowIndex, colIndex - 1, pickedColor);
		}
		if(colIndex < board[0].length - 1) {
			removeIfColor(rowIndex, colIndex + 1, pickedColor);
		}
		
		collapseRows(rowIndex);
		if(rowIndex < board.length - 1) {
			collapseRows(rowIndex + 1);
		}
		if(rowIndex > 0) {
			collapseRows(rowIndex - 1);
		}
	}
	
	/**
	 * Method is called when a cell is meant to be removed if it matches a color.
	 * Used to avoid checking if the cell's color matches when  the cell is meant to be removed.
	 * @throws IndexOutOfBoundsException if the rowIndex or colIndex is out of the grid.
	 */
	public void removeIfColor(int rowIndex, int colIndex, String colorName) {
		if(board[rowIndex][colIndex].getName().equals(colorName)){
			board[rowIndex][colIndex] = BoardCell.EMPTY;
			score++;
		}
	}

	public void collapseRows(int centralRow) {
		System.out.println(centralRow);
		int clearRow = centralRow;
		for(int i = 0; i < board[centralRow].length; i++) {
			if(!board[centralRow][i].equals(BoardCell.EMPTY)) {
				clearRow = -1;
			}
		}
		if(clearRow != -1) {
			BoardCell[][] tempBoard = new BoardCell[board.length][board[0].length];
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board[0].length; j++) {
					if(i > clearRow) {
						tempBoard[i - 1][j] = board[i][j];
					} else {
						tempBoard[i][j] = board[i][j];
					}
				}
			}
			for(int i = 0; i < board[0].length; i++) {
				tempBoard[board.length - 1][i] = BoardCell.EMPTY;
			}
			board = tempBoard;
		}
		
	}
	
}