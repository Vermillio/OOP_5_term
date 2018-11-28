package com.camisado;


public class PassengerCarriageBuilder {

    PassengerCarriageBuilder(int placesNum, int pricePerKm, PassengerCarriage.CarriageType type) {
        object = new PassengerCarriage(0, placesNum, pricePerKm, type);
        this.placesNum = placesNum;
        this.pricePerKm = pricePerKm;
        this.type = type;
    }

    void addPhone() {
        object = new CarriageWithPhone((AbstractPassengerCarriage)object);
    }
    void addTV() {
        object = new CarriageWithTV((AbstractPassengerCarriage)object);
    }

    Carriage build(int number) {
        Carriage tmp = object;
        ((AbstractPassengerCarriage)tmp).setNumber(number);
        object = new PassengerCarriage(number, placesNum, pricePerKm, type);
        return tmp;
    }

    Carriage object;
    int placesNum;
    int pricePerKm;
    PassengerCarriage.CarriageType type;
}
