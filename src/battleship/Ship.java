package battleship;

public class Ship {

  private String name;
  private int size;

  private Coordinate[] coordinates;

  public Ship(String name, int size) {
    this.name = name;
    this.size = size;
    this.coordinates = new Coordinate[size];
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

  public Coordinate[] getCoordinates() {
    return coordinates.clone();
  }
}
