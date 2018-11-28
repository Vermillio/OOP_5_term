package com.camisado;

public class CarriageDecorator extends AbstractPassengerCarriage {

    protected AbstractPassengerCarriage carriage;

    public CarriageDecorator(AbstractPassengerCarriage c) {
        super(c.number);
        this.carriage = c;
    }

    @Override
    public int price(Station from, Station to) {
        return carriage.price(from, to);
    }

    @Override
    public int getPlacesLeft() {
        return carriage.getPlacesLeft();
    }

    @Override
    public int getPlacesTotal() {
        return carriage.getPlacesTotal();
    }

    @Override
    public void setNumber(int number) {
        carriage.number = number;
    }

    @Override
    public int getNumber() {
        return carriage.getNumber();
    }

    @Override
    public void setType(PassengerCarriage.CarriageType type) {
        carriage.setType(type);
    }

    @Override
    public PassengerCarriage.CarriageType getType() {
        return carriage.getType();
    }

    @Override
    public boolean reservePlace() {
        return carriage.reservePlace();
    }

    @Override
    public String toString() {
        return carriage.toString();
    }

    @Override
    void setTrain(Train train) {
        super.setTrain(train);
        carriage.setTrain(train);
    }

}
