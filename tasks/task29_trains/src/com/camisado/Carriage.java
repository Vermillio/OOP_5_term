package com.camisado;

public class Carriage {
    int number;
    boolean has_tv;
    boolean has_phone;
    Train train;

    Carriage(int number, boolean has_tv, boolean has_phone) {
        this.number = number;
        this.has_tv = has_tv;
        this.has_phone = has_phone;
    }

    boolean hasTrain() {return train!=null;}
    Train getTrain() {return train;}

    void setTrain(Train train) {
        this.train=train;
    }
}
