package com.camisado;

import java.util.ArrayList;
import java.util.Arrays;

public class PassengerCarriage extends Carriage {
    int placesNum;
    int placesNumLeft;
    int pricePerKm;
    CarriageType type;
    String name="PassengerCarriage";

    enum CarriageType {
        seat,
        economy,
        compartment
    }

    public PassengerCarriage(int number, boolean has_tv, boolean has_phone, int placesNum, int pricePerKm, CarriageType type )
    {
        super(number, has_tv, has_phone);
        this.placesNum = placesNum;
        this.placesNumLeft=placesNum;
        this.pricePerKm = pricePerKm;
        this.type = type;
    }

    boolean reservePlace() {
        if (placesNumLeft <= 0)
            return false;
        placesNumLeft--;
        return true;
    }

    int getPlacesLeft() {
        return placesNumLeft;
    }

    @Override
    public String toString() {
        return "№: " + number + "\n"+
                "type: " + type.toString() + "\n"+
                "has tv: " + (this.has_tv ? "yes" : "no") + "\n" +
                "telephone: " + (this.has_phone ? "yes" : "no") +"\n";
    }

    public int price(Station from, Station to) {
        return pricePerKm * train.lengthKm(from, to)+train.getPhonePrice()+train.getTvPrice();
    }


}
