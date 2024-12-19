package Controller;

/**
 * Interface for defining player actions in a game.
 * This interface outlines the essential actions a player can perform.
 */
public interface IPlayerAction {

  /**
   * Method to handle the 'Pass' action of a player.
   * This method should be implemented to define what happens when a player decides to pass their turn.
   */
  void onPass();

  /**
   * Method to handle the 'Move' action of a player.
   * This method should be implemented to define the actions taken when a player makes a move in the game.
   */
  void onMove();

  /**
   * Method to update the game board.
   * This method should be implemented to refresh or redraw the game board based on the current game state.
   */
  void updateBoard();
}
