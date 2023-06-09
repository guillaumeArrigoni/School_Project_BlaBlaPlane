package com.example.blablaplane.object.trip;

import java.text.ParseException;
import java.util.ArrayList;

public class TripArray extends ArrayList<Trip> {
    private static TripArray instance;

    private TripArray() {
        super();
        try {
            add(new Trip(Trip.dateFormatter.parse("2021-05-01 08:25:00"), Trip.dateFormatter.parse("2021-05-01 12:20:00"), City.NICE, City.PARIS, 400.5F, "mariorossigmailcom", 0, 3));
            add(new Trip(Trip.dateFormatter.parse("2021-05-01 10:00:00"), Trip.dateFormatter.parse("2021-05-01 15:25:00"), City.BORDEAUX, City.LILLE, 100F, "luigiverdigmailcom", 1, 6));
            add(new Trip(Trip.dateFormatter.parse("2021-05-01 12:00:00"), Trip.dateFormatter.parse("2021-05-01 21:40:00"), City.BERLIN, City.PARIS, 250F, "giovannibianchigmailcom", 3, 2));
            add(new Trip(Trip.dateFormatter.parse("2021-05-01 22:05:00"), Trip.dateFormatter.parse("2021-05-01 23:00:00"), City.MONTPELLIER, City.PARIS, 200F, "francescogmailcom", 4, 4));
            add(new Trip(Trip.dateFormatter.parse("2021-05-01 17:12:00"), Trip.dateFormatter.parse("2021-05-01 18:00:00"), City.MARSEILLE, City.STRASBOURG, 110F, "mariorossigmailcom", 3, 0));
            add(new Trip(Trip.dateFormatter.parse("2021-05-01 02:45:00"), Trip.dateFormatter.parse("2021-05-01 08:06:00"), City.AMSTERDAM, City.PISE, 150F, "luigiverdigmailcom\"", 2, 5));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the instance of the TripArray
     *
     * @return the instance of the TripArray
     */
    public static TripArray getInstance() {
        if (instance == null) {
            instance = new TripArray();
        }
        return instance;
    }

    /**
     * Get a trip by its id
     *
     * @param id the id of the trip
     * @return the trip with the given id
     */
    public Trip getTripById(int id) {
        for (Trip trip : this) {
            if (trip.getId() == id) {
                return trip;
            }
        }
        return null;
    }

    /**
     * Get all the trips of a pilot
     *
     * @param pilotId the id of the pilot
     * @return a TripArray containing all the trips of the pilot
     */
    public TripArray getTripsByPilotId(String pilotId) {
        TripArray trips = new TripArray();
        for (Trip trip : this) {
            if (trip.getPilotId().equals(pilotId)) {
                trips.add(trip);
            }
        }
        return trips;
    }

    /**
     * Get all the trips of a pilot
     *
     * @param departure the departure city
     * @param arrival   the arrival city
     * @param date      the departure date
     * @return a TripArray containing all the trips of the pilot
     */
    public TripArray getTripsByDepartureAndArrival(String departure, String arrival, String date) throws ParseException {
        TripArray trips = new TripArray();
        for (Trip trip : this) {
            if (trip.getDeparture().equals(City.getCityByName(departure))
                    && trip.getArrival().equals(City.getCityByName(arrival))
                    && trip.getDepartureDate().equals(Trip.dateFormatter.parse(date))) {
                trips.add(trip);
            }
        }
        return trips;
    }
}