package com.camisado;

public class PassengerCarriage extends AbstractPassengerCarriage {
    private int placesTotal;
    private int placesLeft;
    private int pricePerKm;
    private CarriageType type;
    private String name = "PassengerCarriage";

    @Override
    public int getPlacesTotal() {
        return placesTotal;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setType(CarriageType type) {
        this.type = type;
    }

    @Override
    public CarriageType getType() {
        return type;
    }

    enum CarriageType {
        seat,
        economy,
        compartment
    }

    public PassengerCarriage(int number, int placesNum, int pricePerKm, CarriageType type )
    {
        super(number);
        this.placesTotal = placesNum;
        this.placesLeft =placesNum;
        this.pricePerKm = pricePerKm;
        this.type = type;
    }

    @Override
    public boolean reservePlace() {
        if (placesLeft <= 0)
            return false;
        placesLeft--;
        return true;
    }

    @Override
    public int getPlacesLeft() {
        return placesLeft;
    }

    @Override
    public String toString() {
        return super.toString() + "type: " + getType().toString() + "\n";
    }

    @Override
    public int price(Station from, Station to) {
        return pricePerKm * train.lengthKm(from, to);
    }
}