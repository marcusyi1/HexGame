package view;

import model.Cell;
import model.HexBoard;
import model.HexCoordinate;
import model.Player;
import model.SquareBoard;
import model.XYCoordinate;

/**
 * The textView of the board.
 */
public class TextView {
  /**
   * TextView place hold constructor.
   */
  public TextView() {
  }

  /**
   * Function actually renders the board into a string.
   * @param board the model itself.
   * @return String representation of the board.
   */
  public String hexRenderBoard(HexBoard board) {
    StringBuilder builder = new StringBuilder();
    int radius = board.getRadius();

    for(int y = -radius; y <= radius; ++y) {
      int indent = Math.abs(y);
      builder.append(" ".repeat(indent * 2));

      for(int p = -radius; p <= radius; ++p) {
        int n = -y - p;
        if (board.isValidCoordinate(new HexCoordinate(y, p, n))) {
          Cell cell = board.getCoordinateState(new HexCoordinate(y, p, n));
          builder.append(cell).append("   ");
        }
      }

      builder.append("\n");
    }

    String var10000 = builder.toString();
    return var10000 + builder.append("Current Player: ").append(board.getCurrentPlayer().
            toString()).append("\nCurrent Player Score: ").append(board.getCurrentPlayerScore()).
            append("\nOpponent Player Score: ").append(board.getOpponentPlayerScore());
  }

  public String squareRenderBoard(SquareBoard board) {
    StringBuilder builder = new StringBuilder();
    int radius = board.getRadius();

    for (int y = radius - 1; y >= 0; y--) {
      for (int x = 0; x < radius; x++) {
        XYCoordinate coord = new XYCoordinate(x, y);
        if (board.isValidCoordinate(coord)) {
          Cell cell = board.getCoordinateState(coord);
          builder.append(cell).append("  ");
        }
      }
      builder.append("\n");
    }

    return builder.toString();
  }

  /**
   * Main class that runs the textView.
   * @param args String arguments.
   */
  public static void main(String[] args) {
    int radius = 4;

    HexBoard hexBoard = new HexBoard(radius);
    SquareBoard squareBoard = new SquareBoard(8);
    TextView textView = new TextView();

    System.out.println(textView.squareRenderBoard(squareBoard));
  }
}
