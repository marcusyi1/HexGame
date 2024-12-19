package view;

import Controller.BoardObserver;
import Controller.Controller;
import Controller.IPlayerAction;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import model.Cell;
import model.HexCoordinate;
import model.Player;
import model.ReadOnlyBoard;
import javax.swing.JOptionPane;

/**
 * A custom JPanel that acts as the main view for a hex-based board game.
 * It listens to mouse, component, and key events, and implements game-specific player actions.
 * and board updates.
 */
public class Panel extends JPanel implements IView, MouseListener, ComponentListener, KeyListener,
        IPlayerAction, BoardObserver {
  public static int dimensionWidth = 1000;
  public static int dimensionHeight = 1000;
  public int radius;
  public ReadOnlyBoard board;
  public ArrayList<Hexagon> hexList;
  public ArrayList<HexCoordinate> hcList;
  private Player blackPlayer;
  private Player whitePlayer;
  public Player currentPlayer;
  public Player opponentPlayer;
  public Hexagon highlightedHex;
  public Controller controller;

  /**
   * Constructs a Panel with a given game board model.
   * Initializes players, sets up listeners, and prepares the game board.
   *
   * @param board The read-only board model to visualize.
   */
  public Panel(ReadOnlyBoard board) {
    this.blackPlayer = new Player(Cell.BLACK);
    this.whitePlayer = new Player(Cell.WHITE);
    this.board = board;
    this.addMouseListener(this);
    this.addComponentListener(this);
    this.addKeyListener(this);
    this.setFocusable(true);
    this.requestFocusInWindow();
    this.hexList = new ArrayList();
    this.hcList = new ArrayList();
    this.currentPlayer = blackPlayer;
    this.opponentPlayer = whitePlayer;
    this.board.addObserver(this);
  }

  /**
   * Sets the game controller.
   *
   * @param controller The game controller.
   */
  public void setController(Controller controller) {
    this.controller = controller;
  }

  /**
   * Sets the radius for hexagons in the game board.
   *
   * @param radius The radius of hexagons.
   */
  public void inputtedRadius(int radius) {
    this.radius = radius;
  }

  /**
   * Generates and lays out hexagons on the board based on the current radius and panel size.
   */
  public void createHex() {
    this.hexList.clear();
    double middleX = (double)this.getWidth() / 2.0;
    double middleY = (double)this.getHeight() / 2.0;
    double hexRadius = (double)(this.getWidth() / ((this.radius * 2 + 1) * 2));
    double hexWidth = hexRadius * Math.sqrt(3.0);
    int hexFromPRow = this.radius;
    int hexFromNRow = 0;

    for(int row = -this.radius; row <= this.radius; ++row) {
      int cols = this.radius * 2 - Math.abs(row) + 1;
      int count = 0;

      for(int col = -cols / 2; col <= cols / 2; ++col) {
        double offsetX = row % 2 != 0 ? hexWidth / 2.0 : 0.0;
        double x = middleX + (double)col * hexWidth + offsetX;
        double y = middleY + (double)row * hexRadius * 1.5;
        Hexagon hex = new Hexagon(x, y, hexRadius);
        this.hexList.add(hex);
        HexCoordinate hc = new HexCoordinate(row, -hexFromNRow + count, hexFromPRow - count);
        hex.setHexCoordinate(hc);
        if ((row % 2 == 1 || row % 2 == -1) && col == cols / 2 - 1) {
          break;
        }

        if (row == 1 && col == -1 || row == 0 && col == 1 || row == -1 && col == -1) {
          hex.setPiece(Cell.BLACK);
        }

        if (row == 1 && col == 0 || row == 0 && col == -1 || row == -1 && col == 0) {
          hex.setPiece(Cell.WHITE);
        }

        ++count;
      }

      if (row >= 0) {
        --hexFromPRow;
      }

      if (row < 0) {
        ++hexFromNRow;
      }
    }

    this.repaint();
  }

  /**
   * Retrieves the hex coordinate at a mouse event location.
   *
   * @param e The mouse event.
   * @return The HexCoordinate at the event location, or null if none.
   */
  public HexCoordinate getHexCoordinateAt(MouseEvent e) {
    int clickedX = e.getX();
    int clickedY = e.getY();
    Iterator var4 = this.hexList.iterator();

    Hexagon hex;
    do {
      if (!var4.hasNext()) {
        return null;
      }

      hex = (Hexagon)var4.next();
    } while(!hex.contains((double)clickedX, (double)clickedY));

    return hex.getHexCoordinate();
  }

  /**
   * Gets the currently highlighted hexagon on the board.
   *
   * @return The highlighted Hexagon.
   */
  public Hexagon getHighlightedHex() {
    return this.highlightedHex;
  }

  /**
   * Paints the board itself.
   * @param g the <code>Graphics</code> object to protect.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;
    g2d.setColor(Color.DARK_GRAY);
    g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    g2d.setColor(Color.BLACK);
    Iterator var3 = this.hexList.iterator();

    while(var3.hasNext()) {
      Hexagon hex = (Hexagon)var3.next();
      hex.draw(g2d);
      if (hex.getPiece() != null) {
        hex.getPiece().draw(g2d);
      }
    }

  }

  /**
   * Retrieves a hexagon by its coordinate.
   *
   * @param coord The HexCoordinate to search for.
   * @return The corresponding Hexagon, or null if not found.
   */
  private Hexagon getHexagonByCoordinate(HexCoordinate coord) {
    Iterator var2 = this.hexList.iterator();

    Hexagon hex;
    do {
      if (!var2.hasNext()) {
        return null;
      }

      hex = (Hexagon)var2.next();
    } while(!hex.getHexCoordinate().equals(coord));

    return hex;
  }

  /**
   * Responds to mouse click events. Determines if a hexagon on the board is clicked and.
   * if so, handles game-related actions based on the click.
   *
   * @param e The MouseEvent triggered by the user.
   */
  public void mouseClicked(MouseEvent e) {
    int clickedX = e.getX();
    int clickedY = e.getY();
    boolean isClickInRange = false;
    Iterator var5 = this.hexList.iterator();

    while(true) {
      Hexagon hex;
      do {
        if (!var5.hasNext()) {
          if (!isClickInRange) {
            var5 = this.hexList.iterator();

            while(var5.hasNext()) {
              hex = (Hexagon)var5.next();
              if (hex.getColor().equals(Color.cyan)) {
                hex.setColor(Color.lightGray);
                this.repaint();
              }
            }
          }

          return;
        }

        hex = (Hexagon)var5.next();
      } while(!hex.contains((double)clickedX, (double)clickedY));

      isClickInRange = true;
      if (hex.getColor().equals(Color.cyan)) {
        hex.setColor(Color.lightGray);
        this.highlightedHex = null;
      } else {
        hex.setColor(Color.cyan);
        this.highlightedHex = hex;
        hex.setNumber(board.calculateFlipsIfMoved(hex.getHexCoordinate()));
        Iterator var7 = this.hexList.iterator();

        while(var7.hasNext()) {
          Hexagon hexagon = (Hexagon)var7.next();
          if (hexagon.getColor().equals(Color.cyan) && !hexagon.equals(hex)) {
            hexagon.setColor(Color.lightGray);
            this.repaint();
          }
        }
      }

      PrintStream var10000 = System.out;
      int var10001 = hex.getHexCoordinate().getY();
      var10000.println("Highlighted Hex HexCoordinate: " + var10001 + ", " +
              hex.getHexCoordinate().getP() + ", " + hex.getHexCoordinate().getN());
      var10000.println(board.calculateFlipsIfMoved(hex.getHexCoordinate()));
      this.repaint();
    }
  }

  /**
   * Responds to mouse pressed events. This method is currently not implemented but can be.
   * overridden for additional functionality.
   *
   * @param e The MouseEvent when the mouse is pressed.
   */
  public void mousePressed(MouseEvent e) {
  }

  /**
   * Responds to mouse released events. This method is currently not implemented but can be.
   * overridden for additional functionality.
   *
   * @param e The MouseEvent when the mouse is released.
   */
  public void mouseReleased(MouseEvent e) {
  }

  /**
   * Responds to mouse entered events. This method is currently not implemented but can be.
   * overridden for additional functionality.
   *
   * @param e The MouseEvent when the mouse enters the component.
   */
  public void mouseEntered(MouseEvent e) {
  }

  /**
   * Responds to mouse exited events. This method is currently not implemented but can be.
   * overridden for additional functionality.
   *
   * @param e The MouseEvent when the mouse exits the component.
   */
  public void mouseExited(MouseEvent e) {
  }

  /**
   * Responds to component resized events by recreating the hex layout.
   *
   * @param e The ComponentEvent when the component is resized.
   */
  public void componentResized(ComponentEvent e) {
    this.createHex();
  }

  /**
   * Responds to component moved events. This method is currently not implemented but can be.
   * overridden for additional functionality.
   *
   * @param e The ComponentEvent when the component is moved.
   */
  public void componentMoved(ComponentEvent e) {
  }

  /**
   * Responds to component shown events by recreating the hex layout.
   *
   * @param e The ComponentEvent when the component is shown.
   */
  public void componentShown(ComponentEvent e) {
    this.createHex();
  }

  /**
   * Responds to component hidden events. This method is currently not implemented but can be.
   * overridden for additional functionality.
   *
   * @param e The ComponentEvent when the component is hidden.
   */
  public void componentHidden(ComponentEvent e) {
  }

  /**
   * Responds to key typed events. This method is currently not implemented but can be.
   * overridden for additional functionality.
   *
   * @param e The KeyEvent when a key is typed.
   */
  public void keyTyped(KeyEvent e) {
  }

  /**
   * Responds to key pressed events, particularly for handling game-specific actions.
   * such as move or pass based on the key pressed.
   *
   * @param e The KeyEvent when a key is pressed.
   */
  public void keyPressed(KeyEvent e) {
    if (e.getKeyChar() != 'p' && e.getKeyChar() != 'P') {
      if ((e.getKeyChar() == 'm' || e.getKeyChar() == 'M') && !e.isConsumed()) {
        System.out.println("Move called for " + currentPlayer.getColor().toString());
        onMove();
        onPass();
        e.consume();
      }
    } else if (!e.isConsumed()) {
      System.out.println("Turn passed. Current Player: " + this.currentPlayer.toString());
      onPass();
      e.consume();
    }

  }

  /**
   * Responds to key released events. This method is currently not implemented but can be.
   * overridden for additional functionality.
   *
   * @param e The KeyEvent when a key is released.
   */
  public void keyReleased(KeyEvent e) {
  }

  /**
   * Handles the 'Pass' action in the game, usually involving changing the current player.
   */
  public void onPass() {
    this.controller.onPass();
    if (this.currentPlayer.equals(this.blackPlayer)) {
      this.currentPlayer = this.whitePlayer;
      this.opponentPlayer = this.blackPlayer;
    } else {
      this.currentPlayer = this.blackPlayer;
      this.opponentPlayer = this.whitePlayer;
    }
    System.out.println("Current player: " + currentPlayer.getColor().toString());

  }

  /**
   * Handles the 'Move' action in the game, typically by updating the board based on the.
   * selected hexagon.
   */
  public void onMove() {
    if (this.board.isValidMove(this.highlightedHex.getHexCoordinate())
            && !highlightedHex.containsCell()) {
      this.highlightedHex.setPiece(this.board.getCurrentPlayer().getColor());
      this.controller.onMove();
      this.repaint();
    }
    else {
      JOptionPane.showMessageDialog(this, "Invalid move!",
              "Move Error", JOptionPane.ERROR_MESSAGE);
    }

    System.out.println(this.board.isValidMove(this.highlightedHex.getHexCoordinate()));
  }

  /**
   * Updates the board based on the current state of the game model, including flipping pieces and.
   * switching players.
   */
  public void updateBoard() {
    List hcl = this.board.getFlipPiecesList();
    Iterator var2 = this.hexList.iterator();

    while(var2.hasNext()) {
      Hexagon hex = (Hexagon)var2.next();
      Iterator var4 = hcl.iterator();

      while(var4.hasNext()) {
        Object o = var4.next();
        if (hex.getHexCoordinate().equals(o) &&
                board.isValidMove(hex.getHexCoordinate())) {
          hex.setColor(Color.CYAN);
          this.repaint();
        }
        if (hex.getHexCoordinate().equals(o)) {
          hex.setPiece(this.board.getCurrentPlayer().getColor());
          this.repaint();
        }
      }
    }

    controller.changeCurrent();
    repaint();
  }

  /**
   * Called when the board model changes. Updates the board's visual state to reflect the model.
   */
  @Override
  public void onBoardChanged() {
    updateBoardStateFromModel();
    repaint();
  }

  /**
   * Updates the board's visual state to match the current state of the game model.
   */
  private void updateBoardStateFromModel() {
    for (Hexagon hex : hexList) {
      Cell cell = board.getCoordinateState(hex.getHexCoordinate());
      if(cell != null) {
        // Only update hexagon if the corresponding cell state is not null
        hex.setPiece(cell); // Assuming setPiece method correctly sets the appearance based on Cell state
      }
    }
  }
}