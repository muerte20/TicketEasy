package com.example.kassa;

public class Movie {
    private String name;
    private int ticketCount;

    public Movie(String name, int ticketCount) {
        this.name = name;
        this.ticketCount = ticketCount;
    }

    public String getName() {
        return name;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}
