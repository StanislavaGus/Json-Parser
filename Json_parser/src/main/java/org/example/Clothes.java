package org.example;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Clothes{
    private int height;
    private int size;

    @Override
    public String toString() {
        return "Height: " + height + ", Size: " + size;
    }
}