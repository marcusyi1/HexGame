package Controller;

import model.HexBoard;
import strategy.Strategy;
import view.Panel;

/**
 * A controller class that specializes in handling strategy-based game interactions.
 * This class extends the generic Controller class, providing specific implementations
 * for games that involve strategy elements.
 */
public class StrategyController extends Controller {

  /**
   * Constructs a StrategyController with a specified game model, strategy, and view.
   * This constructor initializes the StrategyController with the necessary components
   * to manage game interactions based on a given strategy.
   *
   * @param model     The game model, representing the state and logic of the game.
   * @param strategy  The strategy to be used in controlling the game.
   * @param view      The view component responsible for rendering the game's visuals.
   */
  public StrategyController(HexBoard model, Strategy strategy, Panel view) {
    super(model, strategy, view);
  }
}
