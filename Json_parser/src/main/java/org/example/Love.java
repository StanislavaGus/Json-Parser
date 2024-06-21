package org.example;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
public class Love {
    private ArrayList<String> food;
    private ArrayList<String> read;
    private String draw;

    @Override
    public String toString() {
        return "Food: " + food + ", Read: " + read + ", Draw: " + draw;
    }
}
