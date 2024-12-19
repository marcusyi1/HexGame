package Controller;

import model.HexBoard;
import model.Player;
import view.Panel;

/**
 * Controller for player white.
 */
public class WhiteController extends Controller {
  /**
   * Constructor for controller white, it just supers back into the abstract class.
   * @param model model being used.
   * @param player the player.
   * @param view veiw being used.
   */
  public WhiteController(HexBoard model, Player player, Panel view) {
    super(model, player, view);
  }
}
