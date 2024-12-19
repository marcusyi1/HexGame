package model;

import java.util.HashMap;
import java.util.List;

import Controller.BoardObserver;

public class SquareBoard implements Board{
  public HashMap<XYCoordinate, Cell> board;
  boolean startGameStatus = false;
  public int sideLength;
  public Player blackPlayer;
  public Player whitePlayer;
  public Player currentPlayer;

  public SquareBoard(int sideLength) {
    if (sideLength <= 0 || sideLength % 2 != 0) {
      throw new IllegalArgumentException("Invalid side length");
    }

    this.sideLength = sideLength;
    this.board = new HashMap<>();
    this.blackPlayer = new Player(Cell.BLACK);
    this.whitePlayer = new Player(Cell.WHITE);
    this.currentPlayer = blackPlayer;


    for (int x = 0; x < sideLength; x++) {
      for (int y = 0; y < sideLength; y++) {
        if ((x == sideLength / 2 && y == sideLength / 2)
                || (x == (sideLength / 2) - 1 && y == (sideLength / 2) - 1)) {
          board.put(new XYCoordinate(x, y), Cell.WHITE);
        }
        else if ((x == sideLength / 2 && y == sideLength / 2 - 1)
                || (x == (sideLength / 2) - 1 && y == (sideLength / 2))) {
          board.put(new XYCoordinate(x, y), Cell.BLACK);
        }
        else {
          board.put(new XYCoordinate(x, y), Cell.EMPTY);
        }
      }
    }
  }

  @Override
  public void startGame() {
    startGameStatus = true;
  }

  @Override
  public void setCoordinateState(Coordinate var1) {
    if (isValidMove((XYCoordinate) var1)) {

    }
  }

  public boolean isValidCoordinate(XYCoordinate xy) {
    return xy.getX() < this.sideLength && xy.getY() < this.sideLength;
  }

  @Override
  public void switchPlayer() {
    if (currentPlayer.equals(blackPlayer)) {
      currentPlayer = whitePlayer;
    }
    else {
      currentPlayer = blackPlayer;
    }
  }

  @Override
  public void testSet(Coordinate var1, Cell var2) {

  }

  @Override
  public void skipTurn(Coordinate var1) {

  }

  @Override
  public void addUpScore(Player var1) {

  }

  @Override
  public HashMap<XYCoordinate, Cell> getBoard() {
    return board;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getRadius() {
    return sideLength;
  }

  @Override
  public Cell getCoordinateState(Coordinate var1) {
    return board.get(var1);
  }

  @Override
  public int getCurrentPlayerScore() {
    return 0;
  }

  @Override
  public boolean isValidMove(Coordinate var1) {
    XYCoordinate coordinate = (XYCoordinate) var1;

    if (board.get(coordinate) != Cell.EMPTY) {
      return false;
    }

    Cell upperLeftCell = board.get(new XYCoordinate(coordinate.getX() - 1,
            coordinate.getY() + 1));
    Cell upperCell = board.get(new XYCoordinate(coordinate.getX(),
            coordinate.getY() + 1));
    Cell upperRightCell = board.get(new XYCoordinate(coordinate.getX() + 1,
            coordinate.getY() + 1));
    Cell rightCell = board.get(new XYCoordinate(coordinate.getX() + 1,
            coordinate.getY()));
    Cell lowerRightCell = board.get(new XYCoordinate(coordinate.getX() + 1,
            coordinate.getY() - 1));
    Cell lowerCell = board.get(new XYCoordinate(coordinate.getX(),
            coordinate.getY() - 1));
    Cell lowerLeftCell = board.get(new XYCoordinate(coordinate.getX() - 1,
            coordinate.getY() - 1));
    Cell leftCell = board.get(new XYCoordinate(coordinate.getX() - 1,
            coordinate.getY()));
    return true;

  }

  @Override
  public String whoWon() {
    return null;
  }

  @Override
  public Player getCurrentPlayer() {
    return null;
  }

  @Override
  public Player getOpponentPlayer() {
    return null;
  }

  @Override
  public int getNumberOfCells() {
    return 0;
  }

  @Override
  public boolean checkSkipTurn(Coordinate var1) {
    return false;
  }

  @Override
  public int getOpponentPlayerScore() {
    return 0;
  }

  @Override
  public Cell getCellState(Coordinate var1) {
    return board.get(var1);
  }

  @Override
  public List getFlipPiecesList() {
    return null;
  }

  @Override
  public void addObserver(BoardObserver var1) {

  }

  @Override
  public int calculateFlipsIfMoved(Coordinate coordinate) {
    return 0;
  }
}
