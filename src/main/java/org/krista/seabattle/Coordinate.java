package org.krista.seabattle;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class Coordinate {
    @JsonbProperty("x")
    private int x;
    @JsonbProperty("y")
    private int y;

    public Coordinate() {
        this.x = -1;
        this.y = -1;
    }

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
}
