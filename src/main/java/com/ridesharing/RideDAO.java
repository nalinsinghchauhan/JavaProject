package com.ridesharing;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RideDAO {
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ridesharing";
        String username = "root";
        String password = "root"; // Use your actual password
        return DriverManager.getConnection(url, username, password);
    }

    public boolean postRide(int userId, String startPoint, String destination, LocalDate tripDate, LocalTime tripTime, int availableSeats, double fare) throws SQLException {
        String query = "INSERT INTO rides (user_id, start_point, destination, trip_date, trip_time, available_seats, fare) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.setString(2, startPoint);
            ps.setString(3, destination);
            ps.setDate(4, Date.valueOf(tripDate));
            ps.setTime(5, Time.valueOf(tripTime));
            ps.setInt(6, availableSeats);
            ps.setDouble(7, fare);

            return ps.executeUpdate() > 0;
        }
    }

    public List<Ride> searchRides(String startPoint, String destination) throws SQLException {
        String query = "SELECT * FROM rides WHERE start_point = ? AND destination = ? AND available_seats > 0";
        List<Ride> rides = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, startPoint);
            ps.setString(2, destination);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rides.add(new Ride(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("start_point"),
                        rs.getString("destination"),
                        rs.getDate("trip_date").toLocalDate(),
                        rs.getTime("trip_time").toLocalTime(),
                        rs.getInt("available_seats"),
                        rs.getDouble("fare")
                ));
            }
        }
        return rides;
    }

    public boolean bookRide(int rideId, int userId) throws SQLException {
        String query = "INSERT INTO bookings (ride_id, user_id) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, rideId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        }
    }

    public List<Ride> getBookedRides(int userId) throws SQLException {
        String query = "SELECT r.* FROM rides r JOIN bookings b ON r.id = b.ride_id WHERE b.user_id = ?";
        List<Ride> rides = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rides.add(new Ride(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("start_point"),
                        rs.getString("destination"),
                        rs.getDate("trip_date").toLocalDate(),
                        rs.getTime("trip_time").toLocalTime(),
                        rs.getInt("available_seats"),
                        rs.getDouble("fare")
                ));
            }
        }
        return rides;
    }

    public List<Ride> getPostedRides(int userId) throws SQLException {
        String query = "SELECT * FROM rides WHERE user_id = ?";
        List<Ride> rides = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rides.add(new Ride(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("start_point"),
                        rs.getString("destination"),
                        rs.getDate("trip_date").toLocalDate(),
                        rs.getTime("trip_time").toLocalTime(),
                        rs.getInt("available_seats"),
                        rs.getDouble("fare")
                ));
            }
        }
        return rides;
    }
}
