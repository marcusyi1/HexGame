//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package cs3500.reversi;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.Scanner;
import model.HexBoard;
import Controller.BlackController;
import Controller.WhiteController;
import Controller.Controller;
import model.Player;
import strategy.Strategy;
import strategy.StrategyTwo;
import view.Panel;

/**
 * The main class for the Reversi game application.
 * <p>
 * This class contains the main method which sets up and starts a game of Hexagon Reversi.
 * It initializes the game model, views, and controllers, and then launches the GUI.
 * </p>
 */
public final class Reversi {

  /**
   * The main method for the Reversi game.
   * <p>
   * This method initializes the game by setting up the game board, players, views (GUI components),
   * and controllers. It starts by reading the game settings from the console, then creates the game
   * model, determines player types (human or strategy), initializes the views, and sets up the game controllers.
   * Finally, it starts the GUI in the Event Dispatch Thread.
   * </p>
   *
   * @param args Command line arguments (not used in this application).
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Getting player types from the console
    System.out.println("Enter the radius of the large hexagon:");
    int radius = Integer.parseInt(scanner.nextLine());

    System.out.println("First Player (enter 'strategy' for Strategy, anything else for Player):");
    String firstPlayer = scanner.nextLine();

    System.out.println("Second Player (enter 'strategy' for Strategy, anything else for Player):");
    String secondPlayer = scanner.nextLine();

    // Create the game model
    HexBoard model = new HexBoard(radius);

    // Determine player types
    Player player1, player2;

    if ("strategy".equalsIgnoreCase(firstPlayer)) {
      player1 = new Player(model);
      Strategy player1Strat = new StrategyTwo(model, player1);
    } else {
      player1 = new Player(model);
    }

    if ("strategy".equalsIgnoreCase(secondPlayer)) {
      player2 = new Player(model);
      Strategy player2Strat = new StrategyTwo(model, player2);
    } else {
      player2 = new Player(model);
    }

    // Initialize GUI in the Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
      Panel view1 = new Panel(model);
      Panel view2 = new Panel(model);
      view1.inputtedRadius(radius);
      view2.inputtedRadius(radius);
      view1.setFocusable(true);
      view2.setFocusable(true);
      view1.addKeyListener(view1);
      view2.addKeyListener(view1);

      Controller controller1 = new BlackController(model, player1, view1);
      Controller controller2 = new WhiteController(model, player2, view2);
      view1.setController(controller1);
      view2.setController(controller2);

      JFrame frame1 = new JFrame("Hexagon Reversi - Black");
      frame1.setSize(Panel.dimensionWidth / 2, Panel.dimensionHeight);
      frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame1.add(view1);
      frame1.setVisible(true);

      JFrame frame2 = new JFrame("Hexagon Reversi - White");
      frame2.setSize(Panel.dimensionWidth / 2, Panel.dimensionHeight);
      frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame2.add(view2);
      frame2.setVisible(true);

      view1.requestFocusInWindow();
      view2.requestFocusInWindow();

      model.startGame();
    });

    scanner.close();
  }
}


