package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import model.Cell;

/**
 * The class for the piece itself.
 */
public class Piece {
  public Cell cellStatus;
  public Color color;
  public Path2D piece;
  Hexagon hex;

  /**
   * Piece constructor.
   * @param hex the hex that the piece is being placed on.
   * @param cellStatus the status of the piece itself.
   */
  public Piece(Hexagon hex, Cell cellStatus) {
    this.cellStatus = cellStatus;
    this.piece = new Path2D.Double();
    this.hex = hex;
    if (cellStatus.equals(Cell.BLACK)) {
      this.color = Color.BLACK;
    }

    if (cellStatus.equals(Cell.WHITE)) {
      this.color = Color.white;
    }

  }

  /**
   * draws the piece itself.
   * @param g2 Graphics2D constructor.
   */
  public void draw(Graphics2D g2) {
    g2.setColor(this.color);
    double x = this.hex.getX();
    double y = this.hex.getY();
    double radius = this.hex.getRadius() / 2.0;
    g2.fillOval((int)(x - radius), (int)(y - radius), (int)radius * 2, (int)radius * 2);
  }

  /**
   * Sets the piece color.
   * @param color the color being set.
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * Returns the cell status of piece.
   * @return cell status.
   */
  public Cell getCellStatus() {
    return this.cellStatus;
  }

  /**
   * toString of piece (for testing).
   * @return black, white or empty depending on piece.
   */
  public String toString() {
    if (this.cellStatus.equals(Cell.WHITE)) {
      return "white";
    } else {
      return this.cellStatus.equals(Cell.BLACK) ? "black" : "empty";
    }
  }
}
