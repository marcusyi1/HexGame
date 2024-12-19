package view;

import java.awt.*;
import java.awt.geom.Path2D;
import model.Cell;
import model.HexCoordinate;

/**
 * Represents a hexagonal shape on the board.
 * <p>
 * This class encapsulates the properties and behaviors of a hexagonal cell in the game board.
 * It includes methods for drawing the hexagon, setting its color, and managing a game piece on it.
 * </p>
 */
public class Hexagon {
  public double x;
  public double y;
  public Color color;
  public Path2D hexagon;
  public double radius;
  public Piece piece;
  public HexCoordinate hc;
  public int number;

  /**
   * Constructs a Hexagon at a specified location with a given radius.
   *
   * @param x      The x-coordinate of the hexagon's center.
   * @param y      The y-coordinate of the hexagon's center.
   * @param radius The radius of the hexagon.
   */
  public Hexagon(double x, double y, double radius) {
    this.x = x;
    this.y = y;
    this.color = Color.lightGray;
    this.hexagon = new Path2D.Double();
    this.radius = radius;
    this.piece = null;
    this.number = 0;
  }
  /**
   * Draws the hexagon on a given Graphics2D context.
   *
   * @param g2 The Graphics2D context to draw on.
   */
  public void draw(Graphics2D g2) {
    this.hexagon.moveTo(this.x, this.y - this.radius);
    double rightX = this.x + this.radius * (Math.sqrt(3.0) / 2.0);
    double leftX = this.x - this.radius * (Math.sqrt(3.0) / 2.0);
    double topY = this.y - this.radius / 2.0;
    double bottomY = this.y + this.radius / 2.0;
    this.hexagon.lineTo(rightX, topY);
    this.hexagon.lineTo(rightX, bottomY);
    this.hexagon.lineTo(this.x, this.y + this.radius);
    this.hexagon.lineTo(leftX, bottomY);
    this.hexagon.lineTo(leftX, topY);
    this.hexagon.closePath();
    g2.setColor(this.color);
    g2.fill(this.hexagon);
    g2.setStroke(new BasicStroke(1.0F));
    g2.setColor(Color.BLACK);
    g2.draw(this.hexagon);

    if (number == 0) {
      return;
    }
    else {
      // Set font
      Font font = new Font("Arial", Font.BOLD, 12);
      g2.setFont(font);
      g2.setColor(Color.BLACK); // Choose an appropriate color for the text

      // Calculate the position for the text (center of the hexagon)
      String text = String.valueOf(number);
      FontMetrics metrics = g2.getFontMetrics(font);
      int textWidth = metrics.stringWidth(text);
      int textHeight = metrics.getHeight();
      int textX = (int) this.x - (textWidth / 2);
      int textY = (int) this.y + (textHeight / 4); // Adjust this to better center the text vertically

      // Draw the number
      g2.drawString(text, textX, textY);
    }
  }

  public void setNumber(int num) {
    this.number = num;
  }

  /**
   * Finds the x coordinate.
   * @return the x coordinate of the hex.
   */
  public double getX() {
    return this.x;
  }

  /**
   * Finds the y coordinate.
   * @return the y coordinate of the hex.
   */
  public double getY() {
    return this.y;
  }

  /**
   * Finds the radius of the hexagon.
   * @return the radius of hexagon diagram.
   */
  public double getRadius() {
    return this.radius;
  }

  /**
   * Sets color of cell to shift.
   * @return the color.
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * gets color of cell to shift.
   * @return gets the colors.
   */
  public Color getColor() {
    return this.color;
  }

  /**
   * Gets the pieces that are selected.
   * @return the selected pieces.
   */
  public Piece getPiece() {
    return this.piece;
  }

//  public void setPiece(Cell cell) {
//    this.piece = new Piece(this, cell);
//  }
  /**
   * Sets the game piece on the hexagon based on the specified cell state.
   *
   * @param cell The cell state to determine the piece.
   */

  public void setPiece(Cell cell) {
    if (cell == Cell.BLACK) {
      this.piece = new Piece(this, cell); // Create a black piece
    } else if (cell == Cell.WHITE) {
      this.piece = new Piece(this, cell); // Create a white piece
    } else {
      this.piece = null; // No piece for empty cells
    }
  }

  /**
   * Checks if the hexagon currently contains a piece.
   *
   * @return {@code true} if the hexagon contains a piece, {@code false} otherwise.
   */

  public boolean containsCell() {
    return piece != null;
  }

  /**
   * Checks if the hexagon contains a given point.
   *
   * @param pointX The x-coordinate of the point.
   * @param pointY The y-coordinate of the point.
   * @return {@code true} if the point is within the hexagon, {@code false} otherwise.
   */
  public boolean contains(double pointX, double pointY) {
    double rightX = this.x + this.radius * (Math.sqrt(3.0) / 2.0);
    double leftX = this.x - this.radius * (Math.sqrt(3.0) / 2.0);
    return leftX < pointX && pointX < rightX && this.y + this.radius > pointY && this.y - this.radius < pointY;
  }

  /**
   * Sets a hex coordinate for the cell.
   * @param hc the hexcoordinate which needs to be set.
   */
  public void setHexCoordinate(HexCoordinate hc) {
    this.hc = hc;
  }

  /**
   * getter for the hexcoordinate.
   * @return the hexcoordinate.
   */
  public HexCoordinate getHexCoordinate() {
    return this.hc;
  }

  /**
   * converts the variable to a string.
   * @return the in string.
   */
  public String toString() {
    double var10000 = this.getX();
    return "" + var10000 + " ," + this.getY();
  }
}