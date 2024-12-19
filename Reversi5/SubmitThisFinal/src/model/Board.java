package model;

/**
 * Interface representing the core functionalities of a game board.
 * This interface extends ReadOnlyBoard, indicating that it includes
 * methods for both reading and modifying the state of the game board.
 */
public interface Board extends ReadOnlyBoard {

  /**
   * Starts a new game. Initializes the board and sets up necessary
   * elements to begin the game.
   */
  void startGame();

  /**
   * Sets the state of a given coordinate on the board.
   *
   * @param var1 The HexCoordinate object representing the specific coordinate
   *             on the board whose state is to be set.
   */
  void setCoordinateState(Coordinate var1);

  /**
   * Switches the current player. This method is typically called at the end
   * of a player's turn to pass control to the next player.
   */
  void switchPlayer();

  /**
   * Used for testing purposes to set a specific cell on the board to a desired state.
   *
   * @param var1 The HexCoordinate to be tested.
   * @param var2 The Cell object representing the state to set at the given coordinate.
   */
  void testSet(Coordinate var1, Cell var2);

  /**
   * Skips the turn of the current player at the specified coordinate.
   * This might be used in scenarios where a player cannot make a valid move.
   *
   * @param var1 The HexCoordinate where the turn is to be skipped.
   */
  void skipTurn(Coordinate var1);

  /**
   * Adds up the score for a specified player. This could be called when a player
   * achieves certain milestones or at the end of their turn.
   *
   * @param var1 The Player object whose score is to be updated.
   */
  void addUpScore(Player var1);
}
