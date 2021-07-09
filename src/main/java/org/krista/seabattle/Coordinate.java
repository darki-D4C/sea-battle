package org.krista.seabattle;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class Coordinate {
    @JsonbProperty("x")
    private int x;
    @JsonbProperty("y")
    private int y;


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
        return 31 + ( x*5 / (y+1)) * 2;
    }

}
