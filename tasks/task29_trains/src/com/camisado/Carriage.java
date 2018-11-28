package com.camisado;

abstract public class Carriage  {
    int number;
    Train train;

    Carriage(int number) {
        this.number = number;
    }

    boolean hasTrain() {return train!=null;}
    Train getTrain() {return train;}

    void setTrain(Train train) {
        this.train=train;
    }
}
