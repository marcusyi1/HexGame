//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Cell;
import model.HexBoard;
import model.HexCoordinate;
import model.Player;
import strategy.Strategy;
import view.Hexagon;
import view.Panel;

public abstract class Controller implements MouseListener, KeyListener, IModelStatus,
        IPlayerAction {
  public HexBoard model;
  public Panel view;
  public Player player;
  public boolean isCurrent;
  public Strategy strategy;
  private Cell playerColor;

  public Controller(HexBoard model, Player player, Panel view) {
    this.model = model;
    this.player = player;
    this.view = view;
    this.isCurrent = false;
  }

  public Controller(HexBoard model, Strategy strategy, Panel view) {
    this.model = model;
    this.strategy = strategy;
    this.view = view;
    this.isCurrent = false;
  }

  public void changeCurrent() {
    isCurrent = !isCurrent;
  }
  /**
   * Sets the player for this controller.
   *
   * @param player The player to be set.
   */
  public void setPlayer(Player player) {
    this.player = player;
  }
  /**
   * Gets the current player associated with this controller.
   *
   * @return The current player.
   */
  public Player getPlayer() {
    return player;
  }

  public void mouseClicked(MouseEvent e) {
  }
  /**
   * Handles moiuse press events, processing game-specific actions.
   *
   * @param e The mouse triggered by the user.
   */

  public void mousePressed(MouseEvent e) {
    if (model.getCurrentPlayer().getColor() != this.playerColor) {
      // If it's not this player's turn, ignore the mouse press
      return;
    }
    else {
      HexCoordinate hc = this.view.getHexCoordinateAt(e);
      this.model.setCoordinateState(hc);
      this.view.mousePressed(e);
    }
  }
  /**
   * Handles key press events, processing game-specific actions.
   *
   * @param e The KeyEvent triggered by the user.
   */

  public void mouseReleased(MouseEvent e) {
  }
  /**
   * Handles key press events, processing game-specific actions.
   *
   * @param e The KeyEvent triggered by the user.
   */

  public void mouseEntered(MouseEvent e) {
  }
  /**
   * Handles key press events, processing game-specific actions.
   *
   * @param e The KeyEvent triggered by the user.
   */

  public void mouseExited(MouseEvent e) {
  }

  /**
   * Handles key press events, processing game-specific actions.
   *
   * @param e The KeyEvent triggered by the user.
   */

  public void keyTyped(KeyEvent e) {
  }

  /**
   * Handles key press events, processing game-specific actions.
   *
   * @param e The KeyEvent triggered by the user.
   */

  public void keyPressed(KeyEvent e) {
    if (model.getCurrentPlayer().getColor() != this.playerColor) {
      // If it's not this player's turn, ignore the mouse press
      return;
    }
    if (e.getKeyChar() == 'm' || e.getKeyChar() == 'M') {
      System.out.println("Move:");
      this.onMove();
    }
    if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {
      System.out.println("Move:");
      this.onPass();
    }

  }

  /**
   * Handles 'Pass' action in the game.
   */
  public void onPass() {
    changeCurrent();
    this.model.switchPlayer();
  }

  /**
   * Returns the current state of the controller.
   *
   * @return True if it is the current controller, false otherwise.
   */
  public boolean getCurrent() {
    return isCurrent;
  }

  /**
   * Handles 'Move' action in the game.
   */
  public void onMove() {
    Hexagon hex = this.view.getHighlightedHex();
    HexCoordinate hc = hex.getHexCoordinate();
    this.model.setCoordinateState(hc);
    this.model.switchPlayer();
    model.notifyObservers();
    this.view.updateBoard();
    changeCurrent();
  }

  /**
   * Updates the game board based on the current state of the model.
   */  public void updateBoard() {
    // Update the panel based on the current state of the model
    this.view.updateBoard(); // Assuming updateBoard method in Panel class refreshes the view
  }
  public void keyReleased(KeyEvent e) {
  }
}
