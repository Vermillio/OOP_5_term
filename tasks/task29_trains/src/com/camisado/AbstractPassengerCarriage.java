package com.camisado;

public abstract class AbstractPassengerCarriage extends Carriage {

    public AbstractPassengerCarriage(int number) {
        super(number);
    }

    public abstract int price(Station from, Station to);

    public abstract int getPlacesLeft();
    public abstract int getPlacesTotal();

    public abstract void setNumber(int number);
    public abstract int getNumber();

    public abstract void setType(PassengerCarriage.CarriageType type);
    public abstract PassengerCarriage.CarriageType getType();

    public abstract boolean reservePlace();

    @Override
    public String toString() {
        return "№: " + getNumber() + "\n";
    }

//    @Override
//    public String toString();

}
