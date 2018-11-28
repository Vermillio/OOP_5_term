package com.camisado;

public class CarriageWithTV extends CarriageDecorator {

    static public final int additionalPrice = 30;

    public CarriageWithTV(AbstractPassengerCarriage carriage) {
        super(carriage);
    }

    @Override
    public int price(Station from, Station to) {
        return super.price(from, to) + additionalPrice;
    }

    @Override
    public String toString() {
        return super.toString() + "has TV\n";
    }

}