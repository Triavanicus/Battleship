package battleship;

import java.util.ArrayList;

public class Ship {

  private final String name;
  private final int size;

  private Coordinate[] coordinates;
  private final ArrayList<Coordinate> hitCoordinates;
  private boolean hasSunk;

  public Ship(String name, int size) {
    this.name = name;
    this.size = size;
    this.coordinates = new Coordinate[size];
    this.hitCoordinates = new ArrayList<>();
    this.hasSunk = false;
  }

  public void setLocation(Coordinate start, Coordinate end) {
    if (start.distanceTo(end) != size) {
      throw new IllegalArgumentException(
          String.format("Error! Wrong length of the %s! Try again:%n", this.name));
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
