package battleship;

public class Coordinate {

  private final int x;
  private final int y;

  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Coordinate(String coordinate) {
    this.x = Integer.parseInt(coordinate.substring(1));
    this.y = coordinate.charAt(0) - 'A';
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int distanceTo(Coordinate other) {
    if (this.x != other.x && this.y != other.y) {
      return -1;
    }

    return Math.max(Math.abs(this.x - other.x), Math.abs(this.y - other.y)) + 1;
  }

  public Coordinate[] pathTo(Coordinate other) {
    Coordinate[] result = new Coordinate[distanceTo(other)];

    int start;
    if (this.x == other.x) {
      start = Math.min(this.y, other.y);
      for (int i = 0; i < result.length; i++) {
        result[i] = new Coordinate(this.x, start + i);
      }
    } else {
      start = Math.min(this.x, other.x);
      for (int i = 0; i < result.length; i++) {
        result[i] = new Coordinate(start + i, this.y);
      }
    }

    return result;
  }

  public String toString() {
    return String.format("{ x: %d, y: %d }", this.x, this.y);
  }

  public Coordinate offset(int x, int y) {
    return new Coordinate(this.x + x, this.y + y);
  }

  public boolean equals(Coordinate other) {
    return this.x == other.x && this.y == other.y;
  }
}
