package com.camisado;

public class CarriageWithPhone extends CarriageDecorator {

    static public final int additionalPrice = 50;


    public CarriageWithPhone(AbstractPassengerCarriage carriage) {
        super(carriage);
    }

    @Override
    public int price(Station from, Station to) {
        return super.price(from, to) + additionalPrice;
    }

    @Override
    public String toString() {
        return super.toString()+
                "has phone\n";
    }

}
