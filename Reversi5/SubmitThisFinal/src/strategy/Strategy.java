package strategy;

import model.HexCoordinate;

/**
 * Strategy interface to get strategy.
 */
public interface Strategy {

  HexCoordinate determineMove();

}