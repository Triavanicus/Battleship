package battleship;

public class TargetStatus {

  enum Type {
    HIT,
    MISS
  }

  public Type type;
  public Ship ship;

  public TargetStatus() {
    type = Type.MISS;
  }

  public TargetStatus(Ship ship) {
    this.ship = ship;
    if (ship != null) {
      this.type = Type.HIT;
    } else {
      this.type = Type.MISS;
    }
  }
}
