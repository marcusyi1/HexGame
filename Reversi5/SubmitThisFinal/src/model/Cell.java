package model;

/**
 * Enum representing the possible states of a cell on a game board.
 * This enum is used to represent the different types of pieces that can occupy
 * a cell, such as in a board game with black and white pieces and empty spaces.
 */
public enum Cell {
  // Represents a cell with a black piece.
  BLACK("X"),

  // Represents an empty cell.
  EMPTY("-"),

  // Represents a cell with a white piece.
  WHITE("O");

  // String representation of the cell state.
  private final String symbol;

  /**
   * Constructor for the Cell enum.
   *
   * @param symbol A string representation of the cell state. This is used
   *               for displaying the cell state in a textual format.
   */
  private Cell(String symbol) {
    this.symbol = symbol;
  }

  /**
   * Returns the string representation of the cell state.
   *
   * @return The string symbol representing the current state of the cell.
   */
  @Override
  public String toString() {
    return this.symbol;
  }
}
