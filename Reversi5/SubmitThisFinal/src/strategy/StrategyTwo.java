package strategy;

import model.HexBoard;
import model.HexCoordinate;
import model.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * Avoid cells.
 * The main purpose of this class is to make an AI which essentially uses the code.
 * And then looks at the current state of the hexagon and then determines next best move.
 */
public class StrategyTwo implements Strategy {
  HexBoard board;
  Player p;

  /**
   * Constructor for the sharing.
   */
  public StrategyTwo(HexBoard model, Player p) {
    this.board = model;
    this.p = p;
  }

  /**
   * Choose the next move according to the use of AI.
   * @return hexCoordiante of move.
   */
  public HexCoordinate determineMove() {
    List<HexCoordinate> allPossibleMoves = board.getAllValidMoves(p);
    List<HexCoordinate> cornerMoves = new ArrayList<>();
    List<HexCoordinate> nonCornerAdjacentMoves = new ArrayList<>();
    HexCoordinate bestMoveForMostCells = null;
    int maxFlips = 0;

    for (HexCoordinate move : allPossibleMoves) {
      if (isCorner(move, board.getRadius())) {
        cornerMoves.add(move);
      }
      if (!isAdjacentToCorner(move, board.getRadius())) {
        nonCornerAdjacentMoves.add(move);
      }
      int flips = board.validMoveList(move).size();
      if (flips > maxFlips) {
        maxFlips = flips;
        bestMoveForMostCells = move;
      }
    }

    // Prioritize corner moves
    if (!cornerMoves.isEmpty()) {
      return selectUppermostLeftmost(cornerMoves);
    }

    // Then prioritize moves that do not give away corners
    if (!nonCornerAdjacentMoves.isEmpty()) {
      return selectUppermostLeftmost(nonCornerAdjacentMoves);
    }

    // Finally, select the move that captures the most cells
    return bestMoveForMostCells != null ? bestMoveForMostCells : handleNoValidMoves(board, p);
  }

  private HexCoordinate handleNoValidMoves(HexBoard board, Player p) {
    return null;
  }

  private HexCoordinate selectUppermostLeftmost(List<HexCoordinate> moves) {
    return moves.stream()
            .min(this::compareCoordinates)
            .orElse(null);
  }

  private int compareCoordinates(HexCoordinate c1, HexCoordinate c2) {
    if (c1.getY() != c2.getY()) {
      return Integer.compare(c1.getY(), c2.getY());
    }
    if (c1.getP() != c2.getP()) {
      return Integer.compare(c1.getP(), c2.getP());
    }

    return Integer.compare(c1.getN(), c2.getN());
  }

  private boolean isCorner(HexCoordinate coord, int radius) {
    int y = coord.getY();
    int p = coord.getP();
    int n = coord.getN();
    return (Math.abs(y) == radius && Math.abs(p) == radius) ||
            (Math.abs(p) == radius && Math.abs(n) == radius) ||
            (Math.abs(n) == radius && Math.abs(y) == radius);
  }

  private boolean isAdjacentToCorner(HexCoordinate coord, int radius) {
    int y = coord.getY();
    int p = coord.getP();
    int n = coord.getN();
    return !(Math.abs(y) == radius && Math.abs(p) == radius ||
            Math.abs(p) == radius && Math.abs(n) == radius ||
            Math.abs(n) == radius && Math.abs(y) == radius);
  }
}