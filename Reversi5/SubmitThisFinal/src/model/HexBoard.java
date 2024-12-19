package model;

import Controller.BoardObserver;
import Controller.IModelStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a hexagonal board for a two-player game. The board is composed of hexagonal cells
 * arranged in a grid. Each cell can be in one of several states, represented by the `Cell` enum.
 * The game is played by two players, each assigned a different color.
 */

public class HexBoard implements Board, IModelStatus {
  public HashMap<Coordinate, Cell> board;
  public int radius;
  boolean startGameStatus = false;
  public Player userOne;
  public Player userTwo;
  List<HexCoordinate> flankingDirections = new ArrayList();
  public Player currentPlayer;
  public ArrayList<Coordinate> flippingList;
  private List<BoardObserver> observers = new ArrayList();

  /**
   * Constructs a new HexBoard with the specified radius. Initializes the board with a mix of
   * BLACK, WHITE, and EMPTY cells based on the radius. Sets up the two players and starts the game.
   *
   * @param radius The radius of the hexagonal board, must be non-negative.
   * @throws IllegalArgumentException If the radius is negative.
   */

  public HexBoard(int radius) {
    if (radius < 0) {
      throw new IllegalArgumentException("Radius number is invalid");
    } else {
      this.radius = radius;
      this.board = new HashMap();
      this.userOne = new Player(Cell.BLACK);
      this.userTwo = new Player(Cell.WHITE);
      this.currentPlayer = this.userOne;
      this.flippingList = new ArrayList();
      this.board.put(new HexCoordinate(0, 0, 0), Cell.EMPTY);
      this.buildSide(radius, 0, 1, -1, Cell.BLACK);
      this.buildSide(radius, 0, -1, 1, Cell.WHITE);
      this.buildSide(radius, 1, -1, 0, Cell.BLACK);
      this.buildSide(radius, -1, 1, 0, Cell.WHITE);
      this.buildSide(radius, -1, 0, 1, Cell.BLACK);
      this.buildSide(radius, 1, 0, -1, Cell.WHITE);

      for (int y = -radius; y <= radius; ++y) {
        for (int p = -radius; p <= radius; ++p) {
          for (int n = -radius; n <= radius; ++n) {
            if (y + p + n == 0) {
              HexCoordinate h = new HexCoordinate(y, p, n);
              if (!this.board.containsKey(h)) {
                this.board.put(h, Cell.EMPTY);
              }
            }
          }
        }
      }

      this.startGame();
    }
  }


  /**
   * Adds a {@link BoardObserver} to the list of observers.
   * <p>
   * This method registers an observer to receive updates about board changes.
   * When the state of the board changes, each registered observer will be notified.
   * </p>
   *
   * @param observer The {@link BoardObserver} to be added. Must not be {@code null}.
   * @throws NullPointerException if the observer is {@code null}.
   */
  public void addObserver(BoardObserver observer) {
    this.observers.add(observer);
  }

  /**
   * Notifies all registered observers about a change in the board state.
   * <p>
   * This method iterates through all the registered observers and calls their
   * {@code onBoardChanged()} method, informing them about the board state change.
   * It should be called whenever the board state is modified.
   * </p>
   */
  public void notifyObservers() {
    Iterator var1 = this.observers.iterator();

    while (var1.hasNext()) {
      BoardObserver observer = (BoardObserver)var1.next();
      observer.onBoardChanged();
    }
  }

  /**
   * Constructs a new HexBoard by copying the state of an existing HexBoard.
   * This includes the board's cells, players, and game status.
   *
   * @param a The HexBoard to copy.
   */
  public HexBoard(HexBoard a) {
    this.radius = a.radius;
    this.board = new HashMap();
    Iterator var2 = a.board.keySet().iterator();

    while (var2.hasNext()) {
      HexCoordinate key = (HexCoordinate)var2.next();
      Cell cellValue = (Cell)a.board.get(key);
      HexCoordinate newKey = new HexCoordinate(key.getY(), key.getP(), key.getN());
      this.board.put(newKey, cellValue);
    }

    this.startGameStatus = a.startGameStatus;
    this.userOne = a.userOne;
    this.userTwo = a.userTwo;
    this.currentPlayer = this.userOne.equals(a.currentPlayer) ? this.userOne : this.userTwo;
    this.flankingDirections = new ArrayList(a.flankingDirections);
  }

  /**
   * Returns the current state of the board as a HashMap mapping HexCoordinates to Cells.
   *
   * @return The current state of the board.
   */

  public HashMap getBoard() {
    return this.board;
  }

  private void buildSide(int radius, int y, int p, int n, Cell initialColor) {
    for (int i = 1; i <= radius; ++i) {
      HexCoordinate c = new HexCoordinate(y * i, p * i, n * i);
      if (i == 1) {
        this.board.put(c, initialColor);
      } else {
        this.board.put(c, Cell.EMPTY);
      }
    }

    this.startGame();
  }

  /**
   * Starts a new game with the specified radius. Resets the scores of both players.
   *
   */
  public void startGame() {
    this.startGameStatus = true;
    this.userOne.resetScore();
    this.userTwo.resetScore();
  }

  /**
   * Checks if the game is over. The game is over if there are no empty cells left or all non-empty
   * cells are of the same color.
   *
   * @return true if the game is over, false otherwise.
   * @throws IllegalStateException If the game has not started yet.
   */
  public boolean isGameOver() {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      return !this.containsEmptyCell() || this.allNonEmptyCellsSameColor();
    }
  }

  /**
   * Retrieves the current player in the game.
   *
   * @return The current player.
   * @throws IllegalStateException If the game has not started yet.
   */
  public Player getCurrentPlayer() {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      return this.currentPlayer;
    }
  }

  /**
   * Retrieves the opponent of the current player.
   *
   * @return The opponent player.
   * @throws IllegalStateException If the game has not started yet.
   */
  public Player getOpponentPlayer() {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      return this.currentPlayer == this.userOne ? this.userTwo : this.userOne;
    }
  }

  /**
   * Switches the current player to the other player.
   *
   * @throws IllegalStateException If the game has not started yet.
   */
  public void switchPlayer() {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      if (this.currentPlayer == this.userOne) {
        this.currentPlayer = this.userTwo;
      } else {
        this.currentPlayer = this.userOne;
      }

    }
  }

  /**
   * Checks if there are any empty cells left on the board.
   *
   * @return true if there is at least one empty cell, false otherwise.
   * @throws IllegalStateException If the game has not started yet.
   */
  public boolean containsEmptyCell() {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      Iterator var1 = this.board.values().iterator();

      while (var1.hasNext()) {
        Cell cell = (Cell)var1.next();
        if (cell == Cell.EMPTY) {
          return true;
        }
      }

      return false;
    }
  }

  /**
   * Returns the current state of the cell essentially where it is on the board and color.
   * @param hc is the coordinate of the current cell on the board.
   * @return the cell and what is representing.
   */
  public Cell getCellState(Coordinate hc) {
    return (Cell)this.board.get(hc);
  }

  /**
   * Determines if all non-empty cells on the board are of the same color.
   *
   * @return true if all non-empty cells are of the same color, false otherwise.
   * @throws IllegalStateException If the game has not started yet.
   */

  public boolean allNonEmptyCellsSameColor() {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      Cell firstNonEmptyCell = null;
      Iterator var2 = this.board.values().iterator();

      while (var2.hasNext()) {
        Cell cell = (Cell)var2.next();
        if (cell != Cell.EMPTY) {
          if (firstNonEmptyCell == null) {
            firstNonEmptyCell = cell;
          } else if (cell != firstNonEmptyCell) {
            return false;
          }
        }
      }

      return true;
    }
  }

  /**
   * Gets the radius of the hexagonal board.
   *
   * @return The radius of the board.
   * @throws IllegalStateException If the game has not started yet.
   */
  public int getRadius() {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      return this.radius;
    }
  }

  /**
   * Calculates the total number of cells on the board.
   *
   * @return The number of cells on the board.
   * @throws IllegalStateException If the game has not started yet.
   */
  public int getNumberOfCells() {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      int a = 0;

      for (int i = 0; i < this.board.size(); ++i) {
        ++a;
      }

      return a;
    }
  }

  /**
   * Retrieves the state of a specific cell on the board.
   *
   * @param coordinate The coordinate of the cell to check.
   * @return The state of the cell at the specified coordinate.
   * @throws IllegalStateException If the game has not started yet.
   */
  public Cell getCoordinateState(Coordinate coordinate) {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      return (Cell)this.board.get(coordinate);
    }
  }

  /**
   * Sets the state of a specific cell for testing purposes.
   *
   * @param coordinate The coordinate of the cell to set.
   * @param color The new state of the cell.
   * @throws IllegalStateException If the game has not started yet.
   * @throws IllegalArgumentException If the coordinate is not valid.
   */
  public void testSet(Coordinate coordinate, Cell color) {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else if (!this.isValidCoordinate((HexCoordinate) coordinate)) {
      throw new IllegalArgumentException("Not a valid coordinate");
    } else {
      this.board.put(coordinate, color);
    }
  }

  /**
   * Checks if the current player should skip their turn.
   *
   * @param coordinate The coordinate to check for valid moves.
   * @return true if the player has to skip their turn, false otherwise.
   * @throws IllegalStateException If the game has not started yet.
   */
  public boolean checkSkipTurn(Coordinate coordinate) {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else if (this.validMoveList(coordinate).isEmpty()) {
      this.switchPlayer();
      return true;
    } else {
      return false;
    }
  }

  /**
   * Skips the turn of the current player.
   *
   * @param coordinate The coordinate to check for valid moves before skipping.
   * @throws IllegalStateException If the game has not started yet.
   */
  public void skipTurn(Coordinate coordinate) {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      if (this.checkSkipTurn(coordinate)) {
        this.switchPlayer();
      }

    }
  }

  /**
   * Sets the state of a specific coordinate and handles turn progression.
   *
   * @param coordinate The coordinate to set the state of.
   * @throws IllegalStateException If the game has not started yet or if the move is not valid.
   * @throws IllegalArgumentException If the coordinate is not valid.
   */
  public void setCoordinateState(Coordinate coordinate) {
    this.flippingList.clear();
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      this.throwsIllegalTurn();
      if (!this.isValidCoordinate((HexCoordinate) coordinate)) {
        throw new IllegalArgumentException("Coordinate not valid");
      } else if (!this.isValidMove(coordinate)) {
        throw new IllegalStateException("Not a valid move");
      } else {
        this.skipTurn(coordinate);
        this.board.put(coordinate, this.getCurrentPlayer().getColor());
        Iterator var2 = this.validMoveList(coordinate).iterator();

        while (var2.hasNext()) {
          HexCoordinate flipCoord = (HexCoordinate)var2.next();
          this.flipPieces((HexCoordinate) coordinate, flipCoord);
        }

        this.notifyObservers();
        this.switchPlayer();
      }
    }
  }

  /**
   * Makes a list of pieces that need to be flipped after a certain move.
   * @return the list of moves possible.
   */
  public List getFlipPiecesList() {
    return this.flippingList;
  }

  /**
   * Checks if a given coordinate is valid on the board.
   *
   * @param h The HexCoordinate to check.
   * @return true if the coordinate is valid, false otherwise.
   * @throws IllegalStateException If the game has not started yet.
   */
  public boolean isValidCoordinate(HexCoordinate h) {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      int y = h.getY();
      int p = h.getP();
      int n = h.getN();
      return y + p + n == 0 && Math.abs(y) <= this.radius && Math.abs(p) <= this.radius
              && Math.abs(n) <= this.radius;
    }
  }

  /**
   * Retrieves the current player in the game.
   *
   * @return The current player.
   * @throws IllegalStateException If the game has not started yet.
   */
  public int getCurrentPlayerScore() {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      return this.getCurrentPlayer().getScore();
    }
  }

  /**
   * Retrieves the opponent of the current player.
   *
   * @return The opponent player.
   * @throws IllegalStateException If the game has not started yet.
   */
  public int getOpponentPlayerScore() {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      return this.getCurrentPlayer().equals(this.userOne) ? this.userTwo.getScore() :
              this.userOne.getScore();
    }
  }

  /**
   * Switches the current player to the other player.
   *
   * @throws IllegalStateException If the game has not started yet.
   */
  public String whoWon() {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else if (this.userOne.getScore() > this.userTwo.getScore()) {
      return this.userOne.toString();
    } else {
      return this.userOne.getScore() < this.userTwo.getScore() ? this.userTwo.toString() :
              "It's a tie";
    }
  }

  /**
   * Checks if there are any empty cells left on the board.
   * @throws IllegalStateException If the game has not started yet.
   */
  public void addUpScore(Player user) {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      Cell c = user.getColor();
      Iterator var3 = this.board.values().iterator();

      while (var3.hasNext()) {
        Cell cell = (Cell)var3.next();
        if (cell.equals(c)) {
          user.addScore();
        }
      }

    }
  }

  /**
   * Checks if there are any empty cells left on the board.
   *
   * @return true if there is at least one empty cell, false otherwise.
   * @throws IllegalStateException If the game has not started yet.
   */
  public boolean isValidMove(Coordinate h) {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      Player currentPlayer = this.getCurrentPlayer();
      if (this.board.get(h) == null) {
        throw new IllegalArgumentException("Hex Coordinate invalid");
      } else if (!this.isValidCoordinate((HexCoordinate) h)) {
        return false;
      } else {
        return this.checkDirection((HexCoordinate) h, -1, 0, 1,
                currentPlayer.getColor())
                || this.checkDirection((HexCoordinate) h, 0, -1, 1,
                currentPlayer.getColor())
                || this.checkDirection((HexCoordinate) h, 1, -1, 0,
                currentPlayer.getColor())
                || this.checkDirection((HexCoordinate) h, 1, 0, -1,
                currentPlayer.getColor())
                || this.checkDirection((HexCoordinate) h, 0, 1, -1,
                currentPlayer.getColor())
                || this.checkDirection((HexCoordinate) h, -1, 1, 0,
                currentPlayer.getColor());
      }
    }
  }

  /**
   * Checks the direction.
   *
   * @return true if there is at least one empty cell, false otherwise.
   * @throws IllegalStateException If the game has not started yet.
   */
  public boolean checkDirection(HexCoordinate h, int dy, int dp, int dn, Cell currentPlayer) {
    int y = h.getY() + dy;
    int p = h.getP() + dp;
    int n = h.getN() + dn;

    for (boolean foundOpponent = false;
         this.isValidCoordinate(new HexCoordinate(y, p, n)); n += dn) {
      Cell nextCell = this.getCoordinateState(new HexCoordinate(y, p, n));
      if (nextCell.equals(Cell.EMPTY) || nextCell.equals(currentPlayer) && !foundOpponent) {
        return false;
      }

      if (nextCell.equals(currentPlayer)) {
        return true;
      }

      foundOpponent = true;
      y += dy;
      p += dp;
    }

    return false;
  }

  /**
   * Returns a list of all valid moves for the specified player.
   *
   * @param player The player to check valid moves for.
   * @return A list of HexCoordinates representing valid moves.
   */
  public List<HexCoordinate> getAllValidMoves(Player player) {
    List<HexCoordinate> validMoves = new ArrayList();
    Iterator var3 = this.board.keySet().iterator();

    while (var3.hasNext()) {
      HexCoordinate hex = (HexCoordinate)var3.next();
      if (this.isValidMoveForPlayer(hex, player)) {
        validMoves.add(hex);
      }
    }

    return validMoves;
  }

  /**
   * Checks if the given hex coordinate is a valid move for the specified player.
   *
   * @param hex The hex coordinate to check.
   * @param player The player for whom to check the move.
   * @return true if it is a valid move, false otherwise.
   */
  private boolean isValidMoveForPlayer(HexCoordinate hex, Player player) {
    Cell currentCell = (Cell)this.board.get(hex);
    return currentCell != Cell.EMPTY ? false : this.isValidMove(hex);
  }

  /**
   * Checks if the given hex coordinate is a valid move for the specified player.
   *
   * @param h THe hexcoordinate.
   * @return true if it is a valid move, false otherwise.
   */
  public List<HexCoordinate> validMoveList(Coordinate h) {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      Player currentPlayer = this.getCurrentPlayer();
      if (!this.flankingDirections.isEmpty()) {
        this.flankingDirections.clear();
      }

      this.addFlankingDirection(this.flankingDirections, (HexCoordinate) h, -1, 0, 1,
              currentPlayer.getColor());
      this.addFlankingDirection(this.flankingDirections, (HexCoordinate) h, 0, -1, 1,
              currentPlayer.getColor());
      this.addFlankingDirection(this.flankingDirections, (HexCoordinate) h, 1, -1, 0,
              currentPlayer.getColor());
      this.addFlankingDirection(this.flankingDirections, (HexCoordinate) h, 1, 0, -1,
              currentPlayer.getColor());
      this.addFlankingDirection(this.flankingDirections, (HexCoordinate) h, 0, 1, -1,
              currentPlayer.getColor());
      this.addFlankingDirection(this.flankingDirections, (HexCoordinate) h, -1, 1, 0,
              currentPlayer.getColor());
      return this.flankingDirections;
    }
  }

  /**
   * Adds flanking direction.
   * @param directions list to add.
   * @param h hex coordiante.
   * @param dy which way in terms of y.
   * @param dp which way in terms of p.
   * @param dn which way in terms of n.
   * @param currentPlayer the player in quesion.
   */
  private void addFlankingDirection(List<HexCoordinate> directions, HexCoordinate h, int dy,
                                    int dp, int dn, Cell currentPlayer) {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      if (this.checkDirection(h, dy, dp, dn, currentPlayer)) {
        directions.add(new HexCoordinate(dy, dp, dn));
      }

    }
  }

  /**
   * Flips the pieces itself.
   * @param h the hex coordiante.
   * @param direction the direction of the pieces.
   */
  private void flipPieces(HexCoordinate h, HexCoordinate direction) {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else {
      Player currentPlayer = this.getCurrentPlayer();
      int y = h.getY() + direction.getY();
      int p = h.getP() + direction.getP();

      for (int n = h.getN() + direction.getN(); this.isValidCoordinate(new HexCoordinate(y, p, n));
           n += direction.getN()) {
        Cell nextCell = this.getCoordinateState(new HexCoordinate(y, p, n));
        if (nextCell.equals(currentPlayer.getColor())) {
          break;
        }

        this.board.put(new HexCoordinate(y, p, n), currentPlayer.getColor());
        this.flippingList.add(new HexCoordinate(y, p, n));
        currentPlayer.addScore();
        y += direction.getY();
        p += direction.getP();
      }

    }
  }

  /**
   * Checks if the current player is attempting to make a move out of turn and throws an exception
   * if the turn is illegal. This method validates whether the game has started and whether the
   * current player's color corresponds to their assigned turn.
   *
   * @throws IllegalStateException If the game has not started yet, or if a player attempts to make
   *                               a move when it is not their turn.
   */
  public void throwsIllegalTurn() {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    } else if (this.getCurrentPlayer().equals(this.userOne) &&
            this.getCurrentPlayer().getColor().equals(Cell.WHITE)) {
      throw new IllegalStateException("Not their turn");
    } else if (this.getCurrentPlayer().equals(this.userTwo) &&
            this.getCurrentPlayer().getColor().equals(Cell.BLACK)) {
      throw new IllegalStateException("Not their turn");
    }
  }

  /**
   * Calculates how many cells would flip if the current player were to make a move at the given
   * coordinate, without actually making the move.
   *
   * @param coordinate The hypothetical coordinate for the move.
   * @return The number of cells that would be flipped by the move.
   * @throws IllegalStateException If the game has not started yet or if the coordinate is not valid.
   */
  @Override
  public int calculateFlipsIfMoved(Coordinate coordinate) {
    if (!this.startGameStatus) {
      throw new IllegalStateException("Game has not even started yet");
    }
    if (!this.isValidCoordinate((HexCoordinate) coordinate)) {
      throw new IllegalArgumentException("Coordinate not valid");
    }
    if (!this.isValidMove(coordinate)) {
      return 0; // No flips if the cell is not empty
    }

    int flipCount = 0;
    List<HexCoordinate> potentialFlips = this.validMoveList(coordinate);

    for (Coordinate direction : potentialFlips) {
      flipCount += countFlipsInDirection((HexCoordinate) coordinate, (HexCoordinate) direction);
    }

    return flipCount;
  }

  /**
   * Counts how many cells would flip in a specific direction from a given coordinate.
   *
   * @param startCoord The starting coordinate.
   * @param direction The direction to check for flips.
   * @return The number of cells that would flip in this direction.
   */
  private int countFlipsInDirection(HexCoordinate startCoord, HexCoordinate direction) {
    int flips = 0;
    int y = startCoord.getY() + direction.getY();
    int p = startCoord.getP() + direction.getP();
    int n = startCoord.getN() + direction.getN();

    HexCoordinate nextCoord = new HexCoordinate(y, p, n);
    while (this.isValidCoordinate(nextCoord)) {
      Cell nextCell = this.getCoordinateState(nextCoord);
      if (nextCell == Cell.EMPTY || nextCell == this.getCurrentPlayer().getColor()) {
        break;
      }

      flips++;
      y += direction.getY();
      p += direction.getP();
      n += direction.getN();
      nextCoord = new HexCoordinate(y, p, n);
    }

    return flips;
  }
}
