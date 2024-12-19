# README for Hexagon Board Game

## Overview
The Hexagon Board Game is a two-player game that operates on a hexagonal grid.
Each player, distinguished by their color (either black or white), wants to occupy
hexagonal cells on the board.
The game's objective is for players to increase their score by populating the board with their more
of their own color. The board uses a unique coordinate system with an origin at
`(0,0,0)` in the very middle, where positions on the hexagonal grid are represented
using the variables `(y,p,n)`; here, `y` runs along the x-axis,
`p` traces the positive slope, and `n` spans the negative slope.

## Quick Start
To swiftly initiate a game session and visualize the board on the console:

```java
int radius = 6; //this can be any radius
HexBoard board = new HexBoard(radius);
board.startGame(); //this starts the game
TextView textView = new TextView(); //this renders the game
System.out.println(textView.renderBoard(board));
```

## Further Breakdown

### 1. model
The game's logic is mainly in the model. This component comprises the foundational
data structures that drive the game's mechanics.

- **HexBoard**: A class that symbolizes the entire game board, containing a
  collection of hexagonal cells. It possesses methods to interact with and modify
  the board's state, offering a holistic view of the game's progress.
- **Cell**: An enum type representing the individual hexagonal cells on the board.
  Each cell has a state— it can be empty, occupied by the black player, or occupied by the white player.

### 2. View
The component responsible for rendering the game state. As of now, the codebase offers only some
functions since the project requirements didn't ask for us to have the game be operational through
the command line and little instructions were given on when to render:

- **TextView**: A class that facilitates a textual representation of the board.
  It's optimized for console displays but can be extended for other types of visual presentations.

### 3. user
This component captures the essence of the game's participants.

- **Player**: Denotes an individual game player, holding crucial data points like color and
  current score. Each player can retrieve their score, color, increment their score, and provide a
  string representation of their color.
- **user Interface**: A blueprint that sketches out the expected behaviors of a game player.
  The `Player` class adheres to this blueprint, ensuring a consistent user experience.

## Source Organization
For ease of navigation through the codebase:

- **model Package**: Your go-to destination for core game mechanics, hosting essential classes
  like `HexBoard` and `Cell`.
- **textualview Package**: Delves into the visual representation aspect of the game. Primarily
  contains the `TextView` class, which translates board data into a format suitable for console output.
- **user Package**: Encapsulates the essence of the game's participants. Here, you'll find the
  `Player` class and the `user` interface, defining the game's human interaction layer.
- **Main Class**: Serves as the program's starting point. It sets the stage by initializing a game
  board and rendering it via the textual view.
- **Test**: This contains all the test methods which we use to verify if the methods contained are
  running properly.

## Notes on Co-ordinate Representaton
The board's origin is anchored at `(0,0,0)`, providing a consistent reference point.
The hexagonal positioning system, using `(y,p,n)`, ensures precise cell identification and
interaction. This tri-coordinate system is paramount for game logic and user interaction.

Certainly! Here's the detailed update formatted in Markdown for your README file:

---

## Changes for Part 2

In this update of the HexBoard game implementation, several significant enhancements and new features have been introduced to refine gameplay, provide more comprehensive game information, and improve user experience. Below is a detailed breakdown of each change:

### Board Size Method:
- **New Functionality**: Method to determine the board's size.
- **Reason for Addition**: Essential for providing dynamic access to the board's dimensions, crucial for game strategy, UI rendering, and correct gameplay experience.
- **Implementation**: Implemented the `getRadius()` method, which returns the radius of the hexagonal board.

### Coordinate Numbering System:
- **Enhancement Added**: New system for numbering board coordinates.
- **Implementation Details**: Coordinates are numbered starting from 0 at the top and increase clockwise. This approach simplifies identifying and referencing specific board cells.

### Cell Count Method:
- **New Feature**: Method to count the total number of board cells.
- **Purpose**: Crucial for strategic decision-making and understanding the game's scope.
- **Implementation**: The `getNumberOfCells()` method calculates and returns the total cell count.

### Board Copying Functionality:
- **Feature Introduced**: Method to create a board copy.
- **Application**: Useful for AI algorithms and simulations that require game state prediction without altering the actual state.
- **Implementation**: The `HexBoard(HexBoard a)` constructor creates a deep copy of the board.

### Retrieval of Valid Moves:
- **Added Functionality**: Method to list all valid moves for the current game state.
- **Strategic Advantage**: Helps in AI decision-making and assists players in understanding potential moves.
- **Technical Details**: The `validMoveList(HexCoordinate h)` method lists all valid moves from a given coordinate.

### Comprehensive Score Tracking:
- **Enhancement**: Score tracking for both current and non-current players.
- **Importance**: Makes the game more competitive and strategic by allowing players to be aware of each other's scores.
- **Implementation**: The `getOpponentPlayerScore()` method fetches the non-current player's score.

### Enhanced Game Over Conditions:
- **Feature Update**: Improved conditions for determining the game's end.
- **Significance**: Ensures game conclusion only when actual end-game conditions are met, maintaining gameplay integrity.
- **Implementation**: Enhanced `isGameOver()` method to include conditions like no empty cells left or all cells being of the same color.


---
##  README for HexBoard Game Display and TextView Implementation

### Introduction
This README document provides an extensive explanation of the display components and TextView implementation for the HexBoard game, a strategic board game played on a hexagonal grid. It covers the design and functionality of the `Frame`, `Hexagon`, `Panel`, and `TextView` classes, detailing their roles, functionalities, interactions, and specific implementation strategies.

### Frame Class
#### Overview
- The `Frame` class, extending `JFrame`, forms the primary window or frame for the HexBoard game application. It implements the `IView` interface, indicating its role in the application's view layer.
- It is responsible for creating and displaying the main window that hosts other graphical components of the game, such as the `Panel` which contains the hexagonal grid.

#### Implementation Details
```java
public class Frame extends JFrame implements IView {
  public Frame() {
    setPreferredSize(new Dimension(1000, 1000));
  }
}
```
- The constructor sets the preferred size of the frame to 1000x1000 pixels. This size is chosen to provide sufficient space for displaying the hexagonal grid and any additional GUI components or information.
- By extending `JFrame`, the class inherits methods for window management, including size, layout, and visibility, which are crucial for a GUI-based application.

### Hexagon Class
#### Purpose and Design
- The `Hexagon` class encapsulates the properties and behaviors of a single hexagonal cell on the game board.
- Each instance of `Hexagon` holds information about its geometric center, size (radius), color, cell state (`Cell` enum value), and a unique identifier or number.
- The class also manages the graphical representation of a hexagon, including drawing itself and handling color changes to reflect the game state or user interactions.

#### Key Functionalities
- **Selection and Deselection**: The `select()` and `deselect()` methods manage the visual indication of a hexagon being selected or not. For instance, a selected hexagon might change its color to blue, making it visually distinct.
- **Cell State Management**: The `setCell(Cell c)` method updates the cell's state (e.g., empty, occupied by a black or white piece).
- **Drawing Logic**: The `draw(Graphics2D g2)` method is responsible for the graphical rendering of the hexagon, using Java's 2D Graphics API. It involves complex calculations for drawing a perfect hexagon and applying colors based on the game logic.
- **Hit Detection**: The `contains(Point p)` method determines whether a given point (usually from a mouse click) lies within the hexagon's area. This is crucial for handling user interactions.

#### Detailed Drawing Method
```java
public void draw(Graphics2D g2) {
  // Detailed code to calculate vertices and draw the hexagon
  // Includes rotation, filling, and drawing the hexagon's edge
  // Drawing the inner circle based on the cell state
}
```
- The hexagon is initially drawn in a flat orientation and then rotated to achieve the required appearance.
- The method incorporates a series of trigonometric calculations to accurately render the hexagon shape and position it correctly on the grid.

### Panel Class
#### Functionality and Role
- Inherits from `JPanel` and implements `MouseListener`, making it a central component for user interaction and graphical display of the game's hexagonal grid.
- Manages a collection of `Hexagon` objects, arranging them to form the complete game board.

#### Key Features and Methods
- **Hexagon Initialization**: The `createHexagons()` method is called during the panel's initialization and whenever the panel is resized. It calculates the positions and sizes of all hexagons based on the panel's dimensions and the specified hexagonal grid radius.
- **Event Handling**: Implements the `MouseListener` interface to handle mouse events, particularly clicks on individual hexagons. This feature is essential for interactive gameplay.
- **Paint Component Override**: The `paintComponent(Graphics g)` method is overridden to custom draw the collection of hexagons. This method is called whenever the panel needs to be repainted, such as after a window resize or a state change in the game.

#### Detailed Hexagon Creation and Interaction
```java
private void createHexagons() {
  // Advanced code for calculating hexagon positions and initializing them
}

public void mouseClicked(MouseEvent e) {
  // Detailed code to handle mouse clicks on hexagons
}
```
- The method for creating hexagons involves complex geometry to ensure that hexagons are evenly spaced and properly aligned.
- Mouse click handling includes identifying which hexagon was clicked and updating the game state or visual representation accordingly.

### TextView Class
#### Description and Purpose
- Provides a text-based representation of the HexBoard, suitable for console output or non-GUI interfaces.
- Useful for debugging, logging, or providing an alternative view of the game state.

#### Rendering Logic
- `renderBoard(HexBoard board)`: Converts the current state of a given `HexBoard` into a string format. It displays cell states in a format that visually resembles the hexagonal layout. Additionally, it includes current game information like player scores and the active player.

#### Detailed Implementation
```java
public String renderBoard(HexBoard board) {
  // Comprehensive code to build a string representation of the HexBoard
  // Includes logic to accurately display cell states and player information
}
```
- The method involves iterating over the hexagonal grid and formatting each cell's state into a string.
- Indentation and spacing are carefully managed to visually mimic the hexagonal structure in a text format.

### Main Class
#### Function and Execution
- Contains the `main` method, serving as the entry point for the application.
- It demonstrates the initialization and basic usage of the `HexBoard`, `TextView`, and GUI components.

#### Example Main Method
```java
public static void main(String[] args) {
  // Code to initialize the game board and view components
  // Demonstrates setting a hexagon on the board and displaying it
}
```
- The main method sets up the initial game board, creates a frame (window), and adds a panel to it. It also demonstrates how to render the board's state using the `TextView`.

### Conclusion
The `Frame`, `Hexagon`, `Panel`, and `TextView` classes form a comprehensive system for rendering and interacting with the HexBoard game. They combine to create a rich graphical user interface, handle user inputs, and provide an alternative text-based view of the game. Each class has been carefully designed to fulfill its specific role while seamlessly integrating with others to deliver a cohesive and engaging gaming experience.





## Detailed README for HexBoard Game Coordinate System and Color Mechanism

### Introduction
This README section delves into the intricacies of the coordinate system used in the HexBoard game and the color mechanism, including the implementation of a dynamic color change feature for each hexagon. These functionalities are central to the game's user interface and interaction design.

### Coordinate System in HexBoard
#### Overview
- The HexBoard game utilizes a specialized coordinate system designed to accurately represent positions on a hexagonal grid.
- Each hexagon is identified using a unique set of coordinates, crucial for tracking game state, rendering the board, and handling user interactions.

#### Coordinate Representation
- The coordinate system is a three-dimensional approach (referred to as cube coordinates) where each hexagon's position is determined by three values: `x`, `y`, and `z`.
- The coordinates adhere to the constraint `x + y + z = 0`, ensuring consistent and logical placement within the hexagonal layout.

#### Coordinate Tracking Implementation
- The `Hexagon` class includes a `number` attribute, which serves as an identifier for each hexagon. This identifier is directly tied to the hexagon's coordinates, providing a clear reference to its position on the grid.
- In the `Panel` class, hexagons are arranged and stored in a collection (such as an `ArrayList`) based on their coordinates. This arrangement is crucial for rendering the grid and for mapping user interactions (like clicks) to specific hexagons.

#### Example of Coordinate Management
```java
private void createHexagons() {
  // Code to initialize hexagons with proper coordinates
  // Mapping each hexagon's center point to its cube coordinates
}
```
- When creating hexagons, their center points are calculated based on their cube coordinates, ensuring accurate placement within the grid.

### Color Mechanism in HexBoard
#### Design and Functionality
- Each hexagon on the board can display different colors to represent various states or player interactions.
- The initial state of a hexagon might be neutral (e.g., gray), but it can change color based on game events (e.g., a player's move).

#### Dynamic Color Change Feature
- Hexagons have a `color` attribute and methods like `setColorToBlue()`, `select()`, and `deselect()` to dynamically change their colors.
- The color change is triggered by events such as a mouse click, reflecting the game's progression or player actions.

#### Implementation of Click-to-Change Color
- The `Panel` class listens for mouse events and identifies which hexagon is clicked.
- On a click event, the corresponding hexagon's color is changed, indicating a player's move or selection. This feature is not only visually engaging but also paves the way for implementing game logic related to player moves.

#### Example of Color Change Logic
```java
public void mouseClicked(MouseEvent e) {
  // Iterate over hexagons to find which one was clicked
  // Change the color of the clicked hexagon
}
```
- The `mouseClicked` method detects user interactions and updates the hexagon's color, providing immediate visual feedback to the player.

### Circle Overlay on Hexagons
#### Purpose and Design
- Each hexagon features a circle overlay that can change color independently.
- The circle's color represents the current state or ownership (e.g., black or white for different players) in the game.

#### Implementation and Future Flexibility
- The `drawCircle(Graphics2D g2, Color c)` method in the `Hexagon` class is used to draw the circle overlay.
- Initially, circles might be a neutral color but can be changed dynamically as the game progresses, similar to the hexagon's color change mechanism.

#### Advantage for Future Development
- This design choice makes the game's UI adaptable and ready for future enhancements. For instance, implementing different game modes or adding new player interactions becomes simpler as the visual feedback mechanism is already in place.

#### Example of Circle Drawing
```java
public void drawCircle(Graphics2D g2, Color c) {
  // Drawing logic for the circle inside the hexagon
}
```
- The circle is drawn within the hexagon, and its color can be dynamically set, allowing for a wide range of visual representations depending on the game's state.

## New Implementations

### `MostCellInOneGo` Strategy Class
**Purpose:** Implements the `Strategy` interface to provide a gameplay strategy. This strategy selects the move that maximizes cell flips in one go.

**Key Decisions:**
1. **Efficiency Focus:** The method `chooseMove(MockBoard2 board, Player p)` loops through each cell, checking for the best move by simulating moves and counting flips. This ensures the most advantageous move is chosen, prioritizing efficiency in gameplay.
2. **Unimplemented Method for `HexBoard`:** The `chooseMove(HexBoard board, Player p)` method returns `null`. This decision acknowledges the current lack of support for `HexBoard` implementations, leaving room for future expansion.

### Test Classes
**Purpose:** Ensure reliability and correctness of the strategy and model implementations.

#### `TestMock`
1. **Game Initialization Test:** Verifies the game starts correctly with the given parameters.
2. **Coordinate State Test:** Confirms that cell states are accurately set and retrieved.
3. **Base Strategy Test:** Checks the base functionality of the strategy implementation on mock data.

#### `TestStrategy`
1. **Strategy Effectiveness:** Ensures that `MostCellInOneGo` selects the most advantageous move.
2. **Comprehensive Move Examination:** Verifies that all possible moves are considered before selecting one.
3. **Model Interaction:** Assesses the strategy's decision-making based on the model's state.

## Model Updates

### `MockBoard` and `MockBoard2` Classes
**Purpose:** Simulate game boards for testing and strategy development.

**Key Decisions:**
1. **MockBoard Implementation:** Mimics a standard game board, integrating methods for game control and state management.
2. **MockBoard2 Enhancements:** Specialized for testing `MostCellInOneGo` strategy. Includes a method to simulate moves and count flips, aiding in validating the strategy's decision-making process.

### Interface `Strategy`
**Purpose:** Define a contract for strategy implementations, ensuring consistency and interchangeability.