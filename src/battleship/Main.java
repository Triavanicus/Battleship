package battleship;

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
