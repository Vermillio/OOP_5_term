package com.camisado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Station {

    String station_name;

    public static class Coordinates {
        int x;
        int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    Coordinates coordinates;

    ArrayList<Train> trains = new ArrayList<>();

    Station() {}

    @Override
    public String toString() {
        return station_name;
    }


    public Station(String station_name, Coordinates coordinates ) {
        this.station_name = station_name;
        this.coordinates = coordinates;
    }

    void addTrain(Train train) {
        trains.add(train);
    }

    ArrayList<Train> findAvailableTrains(LocalDate date, Station destination) {
        if (this==destination)
            return null;
        ArrayList<Train> result = new ArrayList<>();
        for (Train train : trains) {
            Railway.Route route = train.getRoute();
            if  (
                    train.visits(destination)
                    && route.indexOf(this) < route.indexOf(destination)
                    && route.get(route.indexOf(destination)).departure.toLocalDate().equals(date)
                    && train.hasFreePlaces()
                )
                    result.add(train);
        }
        return result;
    }
}
