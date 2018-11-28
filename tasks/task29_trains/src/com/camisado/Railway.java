package com.camisado;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Railway {
    HashMap<Station, HashMap<Station, Integer>> stations;

    public Railway() {stations=new HashMap<>(); }

    public Railway( HashMap<Station, HashMap<Station, Integer>> stations ) {
        this.stations = stations;
    }

    List<Station> getStations() {
        return new ArrayList<>(stations.keySet());
    }

    Station get(String stationName) {
        Iterator<Station> key = stations.keySet().iterator();
        while (key.hasNext()) {
            Station station = key.next();
            if (station.station_name==stationName)
                return station;
        }
        return null;
    }

    void addStation(Station station) {
        if (stations.containsKey(station))
            throw new KeyAlreadyExistsException();
        stations.put(station, new HashMap<>());
    }

    Station addStation(String name, Station.Coordinates coordinates) {
        Station station = new Station(name, coordinates);
        stations.put(station, new HashMap<>());
        return station;
    }

    void addConnection(Station x, Station y, int distance) {
        if (stations.get(x)==null || stations.get(y)==null)
            throw new NoSuchElementException();
        stations.get(x).put(y, new Integer(distance));
        stations.get(y).put(x, new Integer(distance));
    }


    Route createRoute(List<Point> stations) {
        for (int i = 0;i < stations.size()-1; ++i) {
            Integer connection = this.stations.get(stations.get(i).getStation()).get(stations.get(i+1).getStation());
            if (connection==null)
                return null;
        }
        return new Route(this, stations);
    }

    public class Route {

        Railway railway;
        List<Point> points;

        private Route(Railway railway, List<Point> points) { this.railway = railway; this.points = points; }

        public int size() {return points.size();}

        public Point get(int i) { return points.get(i); }
        public Station getStation(int i) { return get(i).getStation(); }
        public boolean contains(Station x) { return points.contains(Point.of(x,null,null)); }
        public int indexOf(Station x) { return points.indexOf(Point.of(x, null,null)); }

        public Point first() { return get(0); }
        public Point last() { return get(size()-1); }

        public Duration getDuration() { return Duration.between(first().departure, last().arrival); }
        public Duration getDuration(Station x, Station y) {
            return Duration.between(get(indexOf(x)).departure, get(indexOf(y)).arrival);
        }

        public int lengthKm() {
            if (size()==0)
                return 0;
            int sum=0;
            for (int i = 0; i < size()-1; ++i) {
                Integer connection = railway.stations.get(getStation(i)).get(getStation(i+1));
                if (connection==null)
                    throw new IllegalArgumentException("Not connected points in route");
                sum+=connection.intValue();
            }
            return sum;
        }

        public int lengthKm(Station from, Station to) {
            if (!contains(from) || !contains(to))
                return -1;
            int fromIndex = indexOf(from);
            int toIndex = indexOf(to);

            int sum=0;

            for (int i = fromIndex; i < toIndex; ++i) {
                Integer connection = railway.stations.get(getStation(i)).get(getStation(i+1));
                if (connection==null)
                    throw new IllegalArgumentException("Not connected points in route");
                sum+=connection.intValue();
            }

            return sum;
        }

    }

    public static class Point {

        static Point of(Station station, LocalDateTime arrival, LocalDateTime departure) {
            return new Point(station, arrival, departure);
        }

        Station station;
        LocalDateTime arrival;
        LocalDateTime departure;

        public Point(Station station, LocalDateTime arrival, LocalDateTime departure) {
            this.station=station;
            this.arrival=arrival;
            this.departure=departure;
        }

        public Station getStation() {
            return station;
        }

        public LocalDateTime getDeparture() {
            return departure;
        }

        public LocalDateTime getArrival() {
            return arrival;
        }

        @Override
        public boolean equals(Object x) {
            if (x instanceof Point)
                return hashCode() == ((Point)x).hashCode() || station == ((Point)x).station;
            if (x instanceof Station)
                return station == (Station)x;
            return false;
        }
    }
}
