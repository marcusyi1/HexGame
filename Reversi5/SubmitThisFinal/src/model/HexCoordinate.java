//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package model;

import java.util.Objects;

/**
 * Is a class representing the coordinate of any hexagon.
 */

public class HexCoordinate implements Coordinate {
  /**
   * The first coordinate component.
   */
  private final int p;

  /**
   * The second coordinate component.
   */
  private final int n;

  /**
   * The third coordinate component.
   */
  private final int y;

  /**
   * Constructs a new HexCoordinate with specified coordinate components.
   *
   * @param y The third coordinate component.
   * @param p The first coordinate component.
   * @param n The second coordinate component.
   */
  public HexCoordinate(int y, int p, int n) {
    this.y = y;
    this.p = p;
    this.n = n;
  }

  /**
   * Returns the third coordinate component.
   *
   * @return The value of the third coordinate component.
   */
  public int getY() {
    return this.y;
  }

  /**
   * Returns the second coordinate component.
   *
   * @return The value of the second coordinate component.
   */
  public int getN() {
    return this.n;
  }

  /**
   * Returns the first coordinate component.
   *
   * @return The value of the first coordinate component.
   */
  public int getP() {
    return this.p;
  }

  /**
   * Returns a string representation of the HexCoordinate.
   * <p>
   * The format of the string is "HexCoordinate: y, p, n", where y, p, and n
   * are the coordinate components of this HexCoordinate.
   * </p>
   *
   * @return A string representation of this HexCoordinate.
   */
  @Override
  public String toString() {
    return "HexCoordinate: " + this.y + ", " + this.p + ", " + this.n;
  }

  /**
   * Compares this HexCoordinate with the specified object for equality.
   * <p>
   * The result is {@code true} if and only if the argument is not {@code null},
   * is a HexCoordinate object, and has the same values for the y, p, and n
   * coordinate components as this object.
   * </p>
   *
   * @param o The object to compare this HexCoordinate against.
   * @return {@code true} if the given object represents an equivalent HexCoordinate,
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o != null && this.getClass() == o.getClass()) {
      HexCoordinate that = (HexCoordinate) o;
      return this.y == that.y && this.p == that.p && this.n == that.n;
    } else {
      return false;
    }
  }

  /**
   * Returns a hash code for this HexCoordinate.
   * <p>
   * This method is supported for the benefit of hash tables such as those
   * provided by {@link java.util.HashMap}.
   * </p>
   *
   * @return A hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.y, this.p, this.n);
  }
}

