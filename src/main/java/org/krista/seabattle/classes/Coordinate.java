package org.krista.seabattle.classes;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class to represent the coordinates for tiles.
 */
public class Coordinate implements Serializable {
    private static final long serialVersionUID = 6223692443075652319L;

    private final int x;
    private final int y;

    @JsonbCreator
    public Coordinate(@JsonbProperty("x") int x, @JsonbProperty("y") int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public boolean equals(Object o) {


        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Coordinate c = (Coordinate) o;

        // Compare the data members and return accordingly
        return this.getX() == c.getX()
                && (this.getY() == c.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }


}
