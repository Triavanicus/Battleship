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
    Battlefield battlefield = new Battlefield();
    battlefield.display();
    placeShip(battlefield, new Ship("Aircraft Carrier", 5));
    battlefield.display();
    placeShip(battlefield, new Ship("Battleship", 4));
    battlefield.display();
    placeShip(battlefield, new Ship("Submarine", 3));
    battlefield.display();
    placeShip(battlefield, new Ship("Cruiser", 3));
    battlefield.display();
    placeShip(battlefield, new Ship("Destroyer", 2));
    battlefield.display();

    startGame(battlefield);
  }

  public static void startGame(Battlefield battlefield) {
    System.out.println("The game starts!");
    battlefield.display(true);
    System.out.println("Take a shot!");
    while (true) {
      String line = s.nextLine();
      Coordinate coordinate = new Coordinate(line);
      try {
        TargetStatus status = battlefield.shootTarget(coordinate);
        battlefield.display(true);
        if (status.type == Type.HIT) {
          if (status.ship.getHasSunk()) {
            if (battlefield.getNumShipsLeft() == 0) {
              System.out.println("You sank the last ship. You won. Congratulations!");
              return;
            } else {
              System.out.println("You sank a ship! Specify a new target:");
            }
          } else {
            System.out.println("You hit a ship! Try again:");
          }
        } else {
          System.out.println("You missed!");
        }
      } catch (IllegalArgumentException e) {
        System.out.println("Error you entered the wrong coordinates! Try again:");
        continue;
      }
    }
  }

  public static void placeShip(Battlefield battlefield, Ship ship) {
    System.out.printf("Enter the coordinates of the %s (%d cells):%n", ship.getName(),
        ship.getSize());
    while (true) {
      String line = s.nextLine();
      String[] coords = line.split(" ");

      Coordinate start = new Coordinate(coords[0]);
      Coordinate end = new Coordinate(coords[1]);

      try {
        ship.setLocation(start, end);
        battlefield.placeShip(ship);
      } catch (IllegalCoordinatesException e) {
        System.out.println("Error! Wrong ship location! Try again:");
        continue;
      } catch (IllegalShipLengthException e) {
        System.out.printf("Error! Wrong length of the %s! Try again:%n", ship.getName());
        continue;
      } catch (IllegalShipPlacementException e) {
        System.out.println("Error! you placed it too close to another one. Try again:");
        continue;
      } catch (CoordinateOutOfBoundsException e) {
        System.out.println("Error! Invalid coordinate.");
        continue;
      }
      break;
    }
  }
}
