package battleship;

import java.util.ArrayList;

public class Battlefield {

  private int width;
  private int height;
  private ArrayList<Ship> ships;
  private ArrayList<Coordinate> hits;
  private ArrayList<Coordinate> misses;
  public static final int MAX_HEIGHT = 26;

  public Battlefield() {
    this(10, 10);
  }

  public Battlefield(int width, int height) {
    if (height > MAX_HEIGHT) {
      throw new IllegalArgumentException("height cannot be larger than " + MAX_HEIGHT);
    }
    this.width = width;
    this.height = height;
    this.ships = new ArrayList<>();
    this.hits = new ArrayList<>();
    this.misses = new ArrayList<>();
  }

  public void display() {
    display(false);
  }

  public void display(boolean fow) {
    int numDigits = 0;//String.valueOf(width).length();
    String format = " %" + (numDigits + 1) + "s";

    System.out.print(" ");
    for (int x = 1; x <= width; x++) {
      System.out.printf(format, x);
    }
    System.out.println();

    for (int y = 0; y < height; y++) {
      System.out.printf("%c", 'A' + y);
      for (int x = 1; x <= width; x++) {
        Coordinate coordinate = new Coordinate(x, y);
        if (containsCoordinate(hits, coordinate)) {
          System.out.printf(format, "X");
        } else if (containsCoordinate(misses, coordinate)) {
          System.out.printf(format, "M");
        } else if (getShipAtCoordinate(coordinate) != null && !fow) {
          System.out.printf(format, "O");
        } else {
          System.out.printf(format, "~");
        }
      }
      System.out.println();
    }
  }

  private boolean containsCoordinate(ArrayList<Coordinate> list, Coordinate coordinate) {
    for (Coordinate listCoordinate : list) {
      if (listCoordinate.equals(coordinate)) {
        return true;
      }
    }
    return false;
  }

  public boolean isInBounds(Coordinate coordinate) {
    return (coordinate.getX() >= 1 && coordinate.getX() <= width && coordinate.getY() >= 0
        && coordinate.getY() <= height);
  }

  public void placeShip(Ship ship) {
    for (Coordinate coordinate :
        ship.getCoordinates()) {
      if (!isInBounds(coordinate)) {
        throw new CoordinateOutOfBoundsException();
      }

      if (getShipAtCoordinate(coordinate) != null
          || getShipAtCoordinate(coordinate.offset(0, 1)) != null
          || getShipAtCoordinate(coordinate.offset(0, -1)) != null
          || getShipAtCoordinate(coordinate.offset(1, 0)) != null
          || getShipAtCoordinate(coordinate.offset(-1, 0)) != null) {
        throw new IllegalShipPlacementException();
      }
    }
    ships.add(ship);
  }

  private Ship getShipAtCoordinate(Coordinate other) {
    for (Ship ship : ships) {
      for (Coordinate coordinate : ship.getCoordinates()) {
        if (coordinate.equals(other)) {
          return ship;
        }
      }
    }
    return null;
  }

  public TargetStatus shootTarget(Coordinate coordinate) {
    if (!isInBounds(coordinate) && !containsCoordinate(hits, coordinate) && !containsCoordinate(
        misses, coordinate)) {
      throw new IllegalArgumentException();
    }
    Ship ship = getShipAtCoordinate(coordinate);
    if (ship != null) {
      hits.add(coordinate);
      ship.hit(coordinate);
    } else {
      misses.add(coordinate);
    }
    return new TargetStatus(ship);
  }

  public int getNumShipsLeft() {
    int result = 0;
    for (Ship ship : ships) {
      if (!ship.getHasSunk()) {
        result++;
      }
    }
    return result;
  }
}
