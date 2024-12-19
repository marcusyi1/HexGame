//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package model;

import Controller.BoardObserver;
import java.util.HashMap;
import java.util.List;

public interface ReadOnlyBoard {
  HashMap getBoard();

  boolean isGameOver();

  int getRadius();

  Cell getCoordinateState(Coordinate var1);

  int getCurrentPlayerScore();

  boolean isValidMove(Coordinate var1);

  String whoWon();

  Player getCurrentPlayer();

  Player getOpponentPlayer();

  int getNumberOfCells();

  boolean checkSkipTurn(Coordinate var1);

  int getOpponentPlayerScore();

  Cell getCellState(Coordinate var1);

  List getFlipPiecesList();

  void addObserver(BoardObserver var1);
  int calculateFlipsIfMoved(Coordinate coordinate);
}
