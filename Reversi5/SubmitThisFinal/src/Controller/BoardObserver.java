package Controller;

/**
 * The Board Observer interface that observes the board.
 */
public interface BoardObserver {
  /**
   * This function is called when the board is changed in the model for the purpose to notify.
   * the view.
   */
  void onBoardChanged();
}
