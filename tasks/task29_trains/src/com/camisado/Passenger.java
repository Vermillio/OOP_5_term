//package com.camisado;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class Passenger {
//    public class PersonalData {
//        String name;
//        String birth_date;
//    }
//
//    List<Railway.Ticket> tickets;
//
//    Station station;
//
//    public boolean buy_ticket(int train_number, LocalDate date, String destination) {
//        Railway.Ticket ticket = station.buy_ticket(train_number, date, destination);
//        if (ticket!=null) {
//            tickets.add(ticket);
//            return true;
//        }
//        else return false;
//    }
//
//    boolean use_ticket(Railway.Ticket ticket) {
//        tickets.remove(ticket);
//    }
//
//    void manage_input() {
//        System.out.println("Choose operation:");
//        System.out.println("[b]buy ticket, []");
//    }
//
//
//}