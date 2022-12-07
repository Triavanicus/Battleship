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
        } else if (isShipCoordinate(coordinate)) {
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

      if (isShipCoordinate(coordinate) || isShipCoordinate(coordinate.offset(0, 1))
          || isShipCoordinate(coordinate.offset(0, -1)) || isShipCoordinate(coordinate.offset(1, 0))
          || isShipCoordinate(coordinate.offset(-1, 0))) {
        throw new IllegalShipPlacementException();
      }
    }
    ships.add(ship);
  }

  private boolean isShipCoordinate(Coordinate other) {
    for (Ship ship : ships) {
      for (Coordinate coordinate : ship.getCoordinates()) {
        if (coordinate.equals(other)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean shoot(Coordinate coordinate) {
    if (!isInBounds(coordinate) && !containsCoordinate(hits, coordinate) && !containsCoordinate(
        misses, coordinate)) {
      throw new IllegalArgumentException();
    }
    if (isShipCoordinate(coordinate)) {
      hits.add(coordinate);
      return true;
    } else {
      misses.add(coordinate);
      return false;
    }
  }
}
