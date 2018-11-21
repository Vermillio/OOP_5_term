package com.camisado;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;


public class Train {

    enum CarriageTypes {
        passenger,
        restaurant,
        buffet
    };

    static ArrayList<String> CarriageTypeNames = new ArrayList<>(Arrays.asList("passenger", "restaurant", "buffet"));

    int number;
    ArrayList<Carriage> carriages;
    Railway railway;
    Railway.Route route;
    boolean valid = false;

    Train(Train x) {
        this.number = x.number;
        this.route = x.route;
        this.carriages = x.carriages;
        this.railway=x.railway;
        this.valid=x.valid;
    }

    Train(int number, Railway railway, ArrayList<Railway.Point> route, ArrayList<Carriage> carriages) {
        this.number = number;
        this.railway = railway;
        this.route = railway.createRoute(route);
        if (this.route==null) {
            valid=false;
            return;
        }
        this.carriages = carriages;

        for (Railway.Point point : route) {
            point.getStation().addTrain(this);
        }
        for (Carriage carriage: carriages) {
            addCarriage(carriage);
        }
        valid=true;
    }

    Railway.Route getRoute() { return route; }

    void addCarriage(Carriage carriage) {
        if (carriage.hasTrain()) {
            Train oldTrain=carriage.getTrain();
            oldTrain.removeCarriage(carriage);
        }
        carriage.setTrain(this);
    }

    void removeCarriage(Carriage carriage) {
        carriages.remove(carriage);
    }

    Duration getDuration() {
        return route.getDuration();
    }

    Duration getDuration(Station x, Station y) {
        return route.getDuration(x, y);
    }

    int getPhonePrice() {
        return railway.getPhonePrice();
    }

    int getTvPrice() {
        return railway.getTvPrice();
    }

    boolean visits(Station station) {
        if (route.contains(station))
            return true;
        else
            return false;
    }

    boolean hasFreePlaces() {
        for (Carriage carriage : carriages) {
            if (carriage instanceof PassengerCarriage) {
                if (((PassengerCarriage) carriage).placesNumLeft > 0)
                    return true;
            }
        }
        return false;
    }

    int lengthKm() {
        return route.lengthKm();
    }

    int lengthKm(Station from, Station to) {
        return route.lengthKm(from, to);
    }

    int getCarriagesNum() {
        return carriages.size();
    }

    @Override
    public String toString() {
        return  "№: " + number + "\n" +
                "from: " + route.first().getStation() + "\n" +
                "to: " + route.last().getStation() + "\n" +
                "free carriages num: " + getAvailableCarriages().size() + "\n";
    }

    ArrayList<PassengerCarriage> getAvailableCarriages() {
        ArrayList<PassengerCarriage> res = new ArrayList<>();
        for (Carriage carriage: carriages) {
            if (carriage instanceof PassengerCarriage) {
                res.add((PassengerCarriage)carriage);
            }
        }
        return res;
    }


}
