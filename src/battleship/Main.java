package battleship;

import battleship.TargetStatus.Type;
import java.util.Scanner;

public class Main {

  static Scanner s = new Scanner(System.in);

  /*
  10 X 10 field
  ~ denotes fog of war
  O denotes cell with your ship
  X denotes that the ship was hit
  M signifies a miss

  5 ships
  - Aircraft Carrier has 5 cells
  - Battleship has 4 cells
  - Submarine has 3 cells
  - Cruiser has 3 cells
  - Destroyer has 2 cells

  To place a ship, enter two coordinates, the beginning and end of the ship
  If there is an error in the input coordinates, your program should report it, with the word "Error"
   */

  public static void main(String[] args) {
    Battlefield player1Battlefield = new Battlefield();
    Battlefield player2Battlefield = new Battlefield();

    System.out.println("Player 1, place your ships on the game field:");
    placeShips(player1Battlefield);
    passToOtherPlayer();

    System.out.println("Player 2, place your ships to the game field:");
    placeShips(player2Battlefield);
    passToOtherPlayer();

    startGame(player1Battlefield, player2Battlefield);
  }

  public static void startGame(Battlefield player1Battlefield, Battlefield player2Battlefield) {
    while (true) {
      displayBattlefields(player1Battlefield, player2Battlefield);
      System.out.println("Player 1, it's your turn:");
      if (takeTurn(player2Battlefield)) {
        break;
      }
      passToOtherPlayer();

      displayBattlefields(player2Battlefield, player1Battlefield);
      System.out.println("Player 2, it's your turn:");
      if (takeTurn(player1Battlefield)) {
        break;
      }
      passToOtherPlayer();
    }
  }

  public static void placeShip(Battlefield battlefield, Ship ship) {
    System.out.printf("Enter the coordinates of the %s (%d cells):%n", ship.getName(),
        ship.getSize());
    while (true) {
      String line = s.nextLine();
      String[] coordinates = line.toUpperCase().split(" ");

      Coordinate start = new Coordinate(coordinates[0]);
      Coordinate end = new Coordinate(coordinates[1]);

      try {
        ship.setLocation(start, end);
        battlefield.placeShip(ship);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
        continue;
      }
      break;
    }
    battlefield.display();
  }

  public static void placeShips(Battlefield battlefield) {
    battlefield.display();
    placeShip(battlefield, new Ship("Aircraft Carrier", 5));
    placeShip(battlefield, new Ship("Battleship", 4));
    placeShip(battlefield, new Ship("Submarine", 3));
    placeShip(battlefield, new Ship("Cruiser", 3));
    placeShip(battlefield, new Ship("Destroyer", 2));
  }

  public static void passToOtherPlayer() {
    System.out.println("Press Enter and pass the move to another player");
    s.nextLine();
  }

  public static boolean takeTurn(Battlefield foeBattlefield) {
    while (true) {
      String line = s.nextLine().toUpperCase();
      Coordinate coordinate = new Coordinate(line);
      try {
        TargetStatus status = foeBattlefield.shootTarget(coordinate);
        if (status.type == Type.HIT) {
          if (status.ship.getHasSunk()) {
            if (foeBattlefield.getNumShipsLeft() == 0) {
              System.out.println("You sank the last ship. You won. Congratulations!");
              return true;
            } else {
              System.out.println("You sank a ship!");
            }
          } else {
            System.out.println("You hit a ship!");
          }
        } else {
          System.out.println("You missed!");
        }
        return false;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void displayBattlefields(Battlefield friendlyBattlefield,
      Battlefield foeBattlefield) {
    foeBattlefield.display(true);
    System.out.println("----------------------");
    friendlyBattlefield.display();
  }
}
