package org.example;

import lombok.Getter;
public class FourArray{
    private Foure[] foures;
    private int capasity;
    public int size;

    public FourArray(int capasity){
        foures = new Foure[capasity];
        this.capasity = 0;
        this.size = 0;
    }

    public FourArray() {
        foures = new Foure[10];
        capasity = 0;
        size = 0;
    }

    public Foure get(int index){
        if(index < 0 || index >= foures.length){
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return foures[index];
    }

    public void set(int index, Foure foure){
        if(index < 0 || index >= foures.length){
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        foures[index] = foure;
    }

    public void add(Foure foure){
        if(capasity == size){
            capasity += 10;
            Foure[] newFoures = new Foure[capasity];
            for(int i = 0; i < size; i++){
                newFoures[i] = foures[i];
            }
            foures = newFoures;
        }
        foures[size] = foure;
        size++;
    }

    public static class Foure{
        @Getter private int currentState;
        @Getter private String value;
        @Getter private int nextState;
        @Getter private boolean isWritable;

        public Foure(int currentState, String value, int nextState, boolean isWritable) {
            this.currentState = currentState;
            this.value = value;
            this.nextState = nextState;
            this.isWritable = isWritable;
        }
        @Override
        public String toString() {
            return "Four{" +
                    "currentState=" + currentState +
                    ", value='" + value + '\'' +
                    ", nextState=" + nextState +
                    ", isWritable=" + isWritable +
                    '}';
        }
    }
}
