package battleship;

import java.util.ArrayList;

public class Ship {

  private String name;
  private int size;

  private Coordinate[] coordinates;
  private ArrayList<Coordinate> hitCoordinates;
  private int numHits;
  private boolean hasSunk;

  public Ship(String name, int size) {
    this.name = name;
    this.size = size;
    this.coordinates = new Coordinate[size];
    this.hitCoordinates = new ArrayList<>();
    this.hasSunk = false;
    this.numHits = 0;
  }

  public void setLocation(Coordinate start, Coordinate end) {
    if (start.distanceTo(end) != size) {
      throw new IllegalShipLengthException();
    }

    coordinates = start.pathTo(end);
  }

  public String getName() {
    return this.name;
  }

  public int getSize() {
    return this.size;
  }

  public boolean getHasSunk() {
    return this.hasSunk;
  }

  public Coordinate[] getCoordinates() {
    return coordinates.clone();
  }

  public void hit(Coordinate coordinate) {
    for (Coordinate hitCoordinate : this.hitCoordinates) {
      if (coordinate.equals(hitCoordinate)) {
        return;
      }
    }
    for (Coordinate shipCoordinate : coordinates) {
      if (shipCoordinate.equals(coordinate)) {
        this.hitCoordinates.add(coordinate);
        break;
      }
    }
    if (this.hitCoordinates.size() == size) {
      this.hasSunk = true;
    }
  }
}
