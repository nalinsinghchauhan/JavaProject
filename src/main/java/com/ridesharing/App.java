package com.ridesharing;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class App {
    public static void main(String[] args) {
        // Open login form
        openLoginForm();
    }

    public static void openLoginForm() {
        JFrame loginFrame = new JFrame("Vroommate - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(500, 400); // Larger window size
        loginFrame.setLocationRelativeTo(null); // Center on screen

        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 230, 255)); // Soft light blue
        panel.setBorder(BorderFactory.createTitledBorder("User Login"));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add company name above the logo
        JLabel companyNameLabel = new JLabel("VROOMMATE");
        companyNameLabel.setFont(new Font("Courier New", Font.BOLD, 30)); // Courier New, bold, size 30
        companyNameLabel.setForeground(new Color(50, 50, 150)); // Eye-catching dark blue color

        // Optional: Add a shadow effect using HTML
        companyNameLabel.setText("<html><div style='text-shadow: 2px 2px 5px gray;'>VROOMMATE</div></html>");

        companyNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        companyNameLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 2, 10)); // Small padding

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Center align, span two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the text
        panel.add(companyNameLabel, gbc);

        // Load logo directly from the file path
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("D:/sss/Carpooling v1/src/main/resources/carlogo.png"); // Adjust to your path
        Image logoImage = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH); // Resize to 120x120
        logoLabel.setIcon(new ImageIcon(logoImage));

        // Add logo below the company name with less gap
        gbc.gridy = 1; // Increment row
        gbc.insets = new Insets(2, 10, 10, 10); // Smaller gap above the logo
        panel.add(logoLabel, gbc);

        // Reset grid width for next components
        gbc.gridwidth = 1;

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Customize button appearance
        loginButton.setBackground(new Color(100, 200, 100)); // Green
        loginButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(200, 100, 100)); // Red
        registerButton.setForeground(Color.WHITE);

        // Position email label and field
        gbc.gridx = 0;
        gbc.gridy = 2; // Increment row
        gbc.insets = new Insets(10, 10, 10, 10); // Reset to default for other fields
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // Position password label and field
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Position login and register buttons
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        // Action listeners
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both email and password.");
                return;
            }

            try {
                UserDAO userDAO = new UserDAO();
                User user = userDAO.loginUser(email, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    openDashboard(user);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error connecting to the database. Please try again later.");
            }
        });

        registerButton.addActionListener(e -> openRegistrationForm());

        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

    private static void openRegistrationForm() {
        JFrame registrationFrame = new JFrame("Register");
        registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registrationFrame.setSize(400, 350);
        registrationFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 230, 255)); // Soft light blue
        panel.setBorder(BorderFactory.createTitledBorder("User Registration"));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton registerButton = new JButton("Register");

        registerButton.setBackground(new Color(100, 200, 100)); // Green
        registerButton.setForeground(Color.WHITE);

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(registerButton, gbc);

        // Action listener for registration button
        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            User user = new User(name, email, password);
            try {
                UserDAO userDAO = new UserDAO();
                boolean success = userDAO.registerUser(user);
                JOptionPane.showMessageDialog(null, success ? "Registration successful!" : "Registration failed.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while registering.");
            }
        });

        registrationFrame.add(panel);
        registrationFrame.setVisible(true);
    }

    private static void openDashboard(User user) {
        JFrame dashboardFrame = new JFrame("Dashboard - Welcome " + user.getName());
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardFrame.setSize(500, 500);
        dashboardFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 255)); // Light background

        // Header
        JLabel headerLabel = new JLabel("Welcome, " + user.getName(), JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.setBackground(new Color(245, 245, 255));

        JButton postRideButton = createDashboardButton("Post a Ride (For Drivers)", new Color(100, 200, 100));
        JButton searchRideButton = createDashboardButton("Search Rides (For Passengers)", new Color(100, 150, 250));
        JButton viewBookedRidesButton = createDashboardButton("View Booked Rides (For Passengers)", new Color(250, 150, 100));
        JButton viewPostedRidesButton = createDashboardButton("View Posted Rides (For Drivers)", new Color(200, 100, 200));

        buttonPanel.add(postRideButton);
        buttonPanel.add(searchRideButton);
        buttonPanel.add(viewBookedRidesButton);
        buttonPanel.add(viewPostedRidesButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Footer with "Our Team" button
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(245, 245, 255));

        JLabel footerLabel = new JLabel("Vroommate © 2024", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        footerPanel.add(footerLabel, BorderLayout.NORTH);

        JButton teamButton = new JButton("Our Team");
        teamButton.setFont(new Font("Arial", Font.BOLD, 12));
        teamButton.setBackground(new Color(100, 100, 200));
        teamButton.setForeground(Color.WHITE);
        teamButton.setFocusPainted(false);
        teamButton.addActionListener(e -> openOurTeamPage());
        footerPanel.add(teamButton, BorderLayout.EAST);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Add action listeners for other buttons
        postRideButton.addActionListener(e -> openPostRideForm(user));
        searchRideButton.addActionListener(e -> openSearchRideForm(user));
        viewBookedRidesButton.addActionListener(e -> openViewBookedRidesForm(user));
        viewPostedRidesButton.addActionListener(e -> openViewPostedRidesForm(user));

        dashboardFrame.add(mainPanel);
        dashboardFrame.setVisible(true);
    }

    private static JButton createDashboardButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
        return button;
    }

    private static void openOurTeamPage() {
        JFrame teamFrame = new JFrame("Our Team");
        teamFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        teamFrame.setSize(400, 400);
        teamFrame.setLocationRelativeTo(null);

        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BorderLayout());
        teamPanel.setBackground(new Color(245, 245, 255));

        // Product Name
        JLabel productNameLabel = new JLabel("VROOMMATE", JLabel.CENTER);
        productNameLabel.setFont(new Font("Courier New", Font.BOLD, 26));
        productNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        productNameLabel.setForeground(new Color(50, 50, 150));

        // Logo
        ImageIcon originalLogo = new ImageIcon("D:/sss/Carpooling v1/src/main/resources/carlogo.png");
        Image scaledLogo = originalLogo.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH); // Adjust size as needed
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        // Team Members
        JTextArea teamTextArea = new JTextArea(
                "Team Members:\n\n" +
                        "Krishiv Vaid (RA2311003010152)\n" +
                        "Ishaan Bajpai (RA2311003010170)\n" +
                        "Shivansh Thakral (RA2311003010171)\n" +
                        "Nalin Singh Chauhan (RA2311003010172)"
        );
        teamTextArea.setEditable(false);
        teamTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        teamTextArea.setBackground(new Color(245, 245, 255));
        teamTextArea.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        teamPanel.add(productNameLabel, BorderLayout.NORTH);
        teamPanel.add(logoLabel, BorderLayout.CENTER);
        teamPanel.add(teamTextArea, BorderLayout.SOUTH);

        teamFrame.add(teamPanel);
        teamFrame.setVisible(true);
    }

    private static void openPostRideForm(User user) {
        JFrame postRideFrame = new JFrame("Post a Ride");
        postRideFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        postRideFrame.setSize(500, 500);
        postRideFrame.setLocationRelativeTo(null); // Center the window

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 255)); // Light background

        // Header
        JLabel headerLabel = new JLabel("Post a Ride", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(230, 230, 255)); // Soft light blue
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Input fields for ride information
        JLabel startPointLabel = new JLabel("Start Point:");
        JTextField startPointField = new JTextField(15);
        JLabel destinationLabel = new JLabel("Destination:");
        JTextField destinationField = new JTextField(15);
        JLabel tripDateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField tripDateField = new JTextField(15);
        JLabel tripTimeLabel = new JLabel("Time (HH:MM):");
        JTextField tripTimeField = new JTextField(15);
        JLabel availableSeatsLabel = new JLabel("Available Seats:");
        JTextField availableSeatsField = new JTextField(5);
        JLabel fareLabel = new JLabel("Fare:");
        JTextField fareField = new JTextField(10);

        JButton postButton = new JButton("Post Ride");
        postButton.setBackground(new Color(100, 200, 100));
        postButton.setForeground(Color.WHITE);
        postButton.setFont(new Font("Arial", Font.BOLD, 14));
        postButton.setFocusPainted(false);

        // Add components to the form panel
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(startPointLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(startPointField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(destinationLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(destinationField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(tripDateLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(tripDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(tripTimeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(tripTimeField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(availableSeatsLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(availableSeatsField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(fareLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(fareField, gbc);

        gbc.gridx = 1; gbc.gridy = 6; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(postButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Footer
        JLabel footerLabel = new JLabel("RideShare © 2024", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        // Action listener for posting a ride
        postButton.addActionListener(e -> {
            String startPoint = startPointField.getText();
            String destination = destinationField.getText();
            String tripDate = tripDateField.getText();
            String tripTime = tripTimeField.getText();
            int availableSeats;
            double fare;

            // Input validation
            try {
                availableSeats = Integer.parseInt(availableSeatsField.getText());
                fare = Double.parseDouble(fareField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(postRideFrame, "Invalid number format for seats or fare.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate date = LocalDate.parse(tripDate);  // Validate date format
                LocalTime time = LocalTime.parse(tripTime);  // Validate time format

                // Call RideDAO to post the ride
                RideDAO rideDAO = new RideDAO();
                boolean success = rideDAO.postRide(user.getId(), startPoint, destination, date, time, availableSeats, fare);

                if (success) {
                    JOptionPane.showMessageDialog(postRideFrame, "Ride posted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    postRideFrame.dispose(); // Close the post ride window
                } else {
                    JOptionPane.showMessageDialog(postRideFrame, "Failed to post ride. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(postRideFrame, "An error occurred while posting the ride.", "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(postRideFrame, "Invalid date or time format. Please follow YYYY-MM-DD and HH:MM formats.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        postRideFrame.add(mainPanel);
        postRideFrame.setVisible(true);
    }

    private static void openSearchRideForm(User user) {
        JFrame searchRideFrame = new JFrame("Search Rides");
        searchRideFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchRideFrame.setSize(700, 500);
        searchRideFrame.setLocationRelativeTo(null); // Center the window

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 255)); // Light background

        // Header
        JLabel headerLabel = new JLabel("Search for Available Rides", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(230, 230, 255)); // Soft light blue
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel startPointLabel = new JLabel("Start Point:");
        JTextField startPointField = new JTextField(15);
        JLabel destinationLabel = new JLabel("Destination:");
        JTextField destinationField = new JTextField(15);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(100, 150, 250));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setFocusPainted(false);

        // Add components to form panel
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(startPointLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(startPointField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(destinationLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(destinationField, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(searchButton, gbc);

        mainPanel.add(formPanel, BorderLayout.WEST);

        // Table Panel
        String[] columnNames = {"ID", "Start Point", "Destination", "Date", "Time", "Available Seats", "Fare"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable ridesTable = new JTable(model);
        ridesTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(ridesTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Available Rides"));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(245, 245, 255));

        JButton bookButton = new JButton("Book Ride");
        bookButton.setBackground(new Color(100, 200, 100));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFont(new Font("Arial", Font.BOLD, 14));
        bookButton.setFocusPainted(false);

        footerPanel.add(bookButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Search Button Action
        searchButton.addActionListener(e -> {
            String startPoint = startPointField.getText();
            String destination = destinationField.getText();

            try {
                RideDAO rideDAO = new RideDAO();
                List<Ride> rides = rideDAO.searchRides(startPoint, destination);
                model.setRowCount(0); // Clear previous results

                for (Ride ride : rides) {
                    model.addRow(new Object[]{
                            ride.getId(),
                            ride.getStartPoint(),
                            ride.getDestination(),
                            ride.getTripDate(),
                            ride.getTripTime(),
                            ride.getAvailableSeats(),
                            ride.getFare()
                    });
                }

                if (rides.isEmpty()) {
                    JOptionPane.showMessageDialog(searchRideFrame, "No rides found for the given criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(searchRideFrame, "An error occurred while searching for rides.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Book Button Action
        bookButton.addActionListener(e -> {
            int selectedRow = ridesTable.getSelectedRow();
            if (selectedRow != -1) {
                int rideId = (int) model.getValueAt(selectedRow, 0); // Get ride ID
                try {
                    RideDAO rideDAO = new RideDAO();
                    boolean booked = rideDAO.bookRide(rideId, user.getId());
                    if (booked) {
                        JOptionPane.showMessageDialog(searchRideFrame, "Ride booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(searchRideFrame, "Failed to book ride.", "Booking Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(searchRideFrame, "An error occurred while booking the ride.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(searchRideFrame, "Please select a ride to book.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            }
        });

        searchRideFrame.add(mainPanel);
        searchRideFrame.setVisible(true);
    }

    private static void openViewBookedRidesForm(User user) {
        JFrame bookedRidesFrame = new JFrame("View Booked Rides");
        bookedRidesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookedRidesFrame.setSize(700, 500);
        bookedRidesFrame.setLocationRelativeTo(null); // Center the window

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 255)); // Light background

        // Header
        JLabel headerLabel = new JLabel("Your Booked Rides", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Table Panel
        String[] columnNames = {"Ride ID", "Start Point", "Destination", "Date", "Time", "Fare"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable bookedRidesTable = new JTable(model);
        bookedRidesTable.setFillsViewportHeight(true);
        bookedRidesTable.setFont(new Font("Arial", Font.PLAIN, 14));
        bookedRidesTable.setRowHeight(24);

        JScrollPane scrollPane = new JScrollPane(bookedRidesTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Booked Rides"));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(245, 245, 255));

        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(200, 100, 100));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setFocusPainted(false);

        closeButton.addActionListener(e -> bookedRidesFrame.dispose());
        footerPanel.add(closeButton);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Load Booked Rides Data
        try {
            RideDAO rideDAO = new RideDAO();
            List<Ride> bookedRides = rideDAO.getBookedRides(user.getId()); // Assuming this method exists
            for (Ride ride : bookedRides) {
                model.addRow(new Object[]{
                        ride.getId(),
                        ride.getStartPoint(),
                        ride.getDestination(),
                        ride.getTripDate(),
                        ride.getTripTime(),
                        ride.getFare()
                });
            }

            if (bookedRides.isEmpty()) {
                JOptionPane.showMessageDialog(bookedRidesFrame, "You haven't booked any rides yet.", "No Booked Rides", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(bookedRidesFrame, "An error occurred while fetching booked rides.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        bookedRidesFrame.add(mainPanel);
        bookedRidesFrame.setVisible(true);
    }


    private static void openViewPostedRidesForm(User user) {
        JFrame postedRidesFrame = new JFrame("View Posted Rides");
        postedRidesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        postedRidesFrame.setSize(700, 500);
        postedRidesFrame.setLocationRelativeTo(null); // Center the window

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 255)); // Light background

        // Header
        JLabel headerLabel = new JLabel("Your Posted Rides", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Table Panel
        String[] columnNames = {"Ride ID", "Start Point", "Destination", "Date", "Time", "Available Seats", "Fare"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable postedRidesTable = new JTable(model);
        postedRidesTable.setFillsViewportHeight(true);
        postedRidesTable.setFont(new Font("Arial", Font.PLAIN, 14));
        postedRidesTable.setRowHeight(24);

        JScrollPane scrollPane = new JScrollPane(postedRidesTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Posted Rides"));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(245, 245, 255));

        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(200, 100, 100));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setFocusPainted(false);

        closeButton.addActionListener(e -> postedRidesFrame.dispose());
        footerPanel.add(closeButton);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Load Posted Rides Data
        try {
            RideDAO rideDAO = new RideDAO();
            List<Ride> postedRides = rideDAO.getPostedRides(user.getId()); // Assuming this method exists
            for (Ride ride : postedRides) {
                model.addRow(new Object[]{
                        ride.getId(),
                        ride.getStartPoint(),
                        ride.getDestination(),
                        ride.getTripDate(),
                        ride.getTripTime(),
                        ride.getAvailableSeats(),
                        ride.getFare()
                });
            }

            if (postedRides.isEmpty()) {
                JOptionPane.showMessageDialog(postedRidesFrame, "You haven't posted any rides yet.", "No Posted Rides", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(postedRidesFrame, "An error occurred while fetching posted rides.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        postedRidesFrame.add(mainPanel);
        postedRidesFrame.setVisible(true);
    }


    private static int extractRideIdFromDetails(String rideDetails) {
        String[] details = rideDetails.split(",");
        for (String detail : details) {
            if (detail.trim().startsWith("Ride ID:")) {
                return Integer.parseInt(detail.split(":")[1].trim());
            }
        }
        return -1;
    }
}
