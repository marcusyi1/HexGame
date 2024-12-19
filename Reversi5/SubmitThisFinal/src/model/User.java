package model;

/**
 * Interface for a user in a board game.
 * <p>
 * This interface defines the basic functionalities expected from a user, such as getting the score,
 * getting the color, adding to the score, and providing a string representation.
 * It is intended to be implemented by classes representing players or participants in the game.
 * </p>
 */
public interface User {

  /**
   * Retrieves the user's current score.
   *
   * @return The current score of the user.
   */
  int getScore();

  /**
   * Retrieves the user's color.
   *
   * @return The {@link Cell} color of the user.
   */
  Cell getColor();

  /**
   * Increments the user's score by a predefined amount (usually 1).
   */
  void addScore();

  /**
   * Returns a string representation of the user.
   * <p>
   * This method should be overridden to provide a meaningful description of the user,
   * such as their name or identifier.
   * </p>
   *
   * @return A string representing the user.
   */
  @Override
  String toString();
}
