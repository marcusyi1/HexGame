package strategy;

import model.HexBoard;
import model.HexCoordinate;
import model.Player;
import model.Cell;

import java.util.List;

/**
 * StrategyOne is the class that implements strategy which chooses the Hex that.
 * flips the most cells.
 */
public class StrategyOne implements Strategy {

  private HexBoard board;
  private Player botPlayer;

  /**
   * Basic constructor for the strategy.
   * @param board represents the baord on which the AI is running.
   * @param botPlayer is the player which is currently being chosen for.
   */
  public StrategyOne(HexBoard board, Player botPlayer) {
    this.board = board;
    this.botPlayer = botPlayer;
  }

  /**
   * Choose the next move according to the use of AI.
   * @return hexCoordiante of move.
   */
  @Override
  public HexCoordinate determineMove() {
    List<HexCoordinate> validMoves = board.getAllValidMoves(botPlayer);

    if (validMoves.isEmpty()) {
      return null; // No valid moves available
    }

    return selectMoveForMaximumFlip(validMoves);
  }

  private HexCoordinate selectMoveForMaximumFlip(List<HexCoordinate> validMoves) {
    int maxFlips = 0;
    HexCoordinate bestMove = null;

    for (HexCoordinate move : validMoves) {
      int flips = calculateFlips(move);
      if (flips > maxFlips) {
        maxFlips = flips;
        bestMove = move;
      }
    }

    return bestMove;
  }

  private int calculateFlips(HexCoordinate move) {
    // Assuming the HexBoard class has a method like `getFlipPiecesList`
    // that returns the list of pieces that would be flipped for a given move
    // Temporarily make the move, calculate flips, and then undo the move
    Cell originalState = board.getCellState(move);
    board.setCoordinateState(move); // Temporarily make the move
    int flips = board.getFlipPiecesList().size();
    board.testSet(move, originalState); // Undo the move
    return flips;
  }
}
