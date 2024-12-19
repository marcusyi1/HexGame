package model;

import java.awt.*;

/**
 * Represents a player in a board game.
 * <p>
 * This class encapsulates the properties and actions of a player, including their score,
 * color, and associated hex board. The player's color is represented by a {@link Cell} enum.
 * </p>
 */
public class Player implements User {
  /**
   * The color of the player, represented by a {@link Cell} enum.
   */
  public final Cell color;

  /**
   * The player's current score in the game.
   */
  public int score;

  /**
   * The hex board associated with the player.
   */
  public Board hexBoard;

  /**
   * Constructs a new Player with a specified color.
   *
   * @param color The color of the player.
   */
  public Player(Cell color) {
    this.color = color;
    this.score = 0;
  }

  /**
   * Constructs a new Player associated with a specific hex board.
   *
   * @param hexBoard The hex board associated with the player.
   */
  public Player(Board hexBoard) {
    this.color = this.getColor();
    this.score = 0;
    this.hexBoard = hexBoard;
  }

  /**
   * Retrieves the player's current score.
   *
   * @return The current score of the player.
   */
  public int getScore() {
    return this.score;
  }

  /**
   * Retrieves the player's color.
   *
   * @return The color of the player.
   */
  public Cell getColor() {
    return this.color;
  }

  /**
   * Increments the player's score by 1.
   */
  public void addScore() {
    ++this.score;
  }

  /**
   * Resets the player's score to 0.
   */
  public void resetScore() {
    this.score = 0;
  }

  /**
   * Returns a string representation of the player, typically their color.
   *
   * @return A string representing the player's color.
   */
  @Override
  public String toString() {
    return this.color.toString();
  }
}
