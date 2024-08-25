package lab4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Calendar;

public class JDBC {
	Scanner scanner = new Scanner(System.in);
	Calendar cal = Calendar.getInstance();
	private String jdbcUrl = "jdbc:mysql://localhost:3306/pomonatransit";
	private String jdbcUser = "root";
	private String jdbcPassword = "password";
	private Connection con;
	private Statement stmnt;
	private ResultSet result;
	
	
	public JDBC(String url, String user, String password) throws SQLException {
		this.jdbcUrl = url;
		this.jdbcUser = user;
		this.jdbcPassword = password;
		con = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
		stmnt = con.createStatement();
	}
	
	public void closeConnections() throws SQLException {
		if (result != null) { result.close(); }
		if (stmnt != null) { stmnt.close();}
		if (con != null) { con.close(); }
	}
	

	public void displayTripSchedule() {
		/**
		 * Display the schedule of all trips for a given StartLocationName
		 * and DestinationName, and Date. In addition to these attributes,
		 * the schedule includes: ScheduledStartTime, ScheduledArrivalTime,
		 * DriverID, and BusID
		 */
		
		System.out.println("\nYou have selected >> Display Trips' Schedule\n"
				+ "Please provide the following information:");
		System.out.print("Start Location Name: ");
		String startLocationInput = scanner.nextLine();
		System.out.print("Destination Name: ");
		String destinationInput = scanner.nextLine();
		System.out.print("Month(MM): ");
		String month = scanner.nextLine();
		System.out.print("Day(dd): ");
		String day = scanner.nextLine();
		System.out.print("Year(YYYY): ");
		String year = scanner.nextLine();
		String date = year + "-" + month + "-" + day;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			System.out.println("Date is not in the correct format.");
			e.printStackTrace();
			return;
		}
		
		// format date to yyyy-MM-dd
		String fromDate = sdf.format(cal.getTime());
		
		// display results 
		ResultSet result;
		try {
			result = stmnt.executeQuery("SELECT * FROM Trip "
					+ "LEFT JOIN TripOffering ON Trip.TripNumber = TripOffering.TripNumber"
					+ " WHERE StartLocationName = '" + startLocationInput
					+ "' AND DestinationName = '" + destinationInput
					+ "' AND Date = '" + fromDate 
					+ "' ORDER BY ScheduledStartTime;");
			System.out.println("Displaying Trips on " + fromDate + " from "
					+ startLocationInput + " to " + destinationInput + "...");
			// print query
			int count = 0;
			while (result.next()) {
				count++;
				
				// get data, hard coded the index
				String startTime = result.getString(6);
				String arrivalTime = result.getString(7);
				String driverName = result.getString(8);
				String busID = result.getString(9);
				
				System.out.printf("%d) %s-%s, driver[%s], busID[%s]\n",
						count, startTime, arrivalTime, driverName, busID);

			}
			if (count == 0) {
				System.out.println("No result");
			}
		} catch (SQLException e) {
			System.out.println("Invalid information provided.\n"
					+ "Returning to main menu...");
			e.printStackTrace();
		}
		return;
		
	}
	private static String tripOfferingMenu = 
		"\n0) Exit\n"
		+ "1) Delete a Trip Offering\n"
		+ "2) Add a Set of Trip Offerings\n"
		+ "3) Change the Driver of a Trip Offering\n"
		+ "4) Change the Bus of a Trip Offering\n";
	
	public void editTripOffering() {
		/*
		 * -Delete a trip offering specified by Trip#, Date, and ScheduledStartTime;
		 * -Add a set of trip offerings assuming the values of all attributes are given (the software 
		 * asks if you have more trips to enter) ;
		 * - Change the driver for a given Trip offering (i.e given TripNumber, Date, 
		 * ScheduledStartTime);
		 * - Change the bus for a given Trip offering.
		 */
		System.out.println("You have selected >> Edit Trip Offering\n"
				+ "What would you like to do?\n" + tripOfferingMenu);
		System.out.println("Enter an integer(0-4): ");
		
		int userInput = scanner.nextInt();
		System.out.println();
		
		switch(userInput){
		case 0:
			System.out.println("Returning to main menu...");
			return; 
		case 1: // delete a trip offering
			System.out.println("You have selected >> Delete a Trip Offering\n"
					+ "Please provide the following information or type -1 anytime to cancel");
			System.out.print("Trip Number: ");
			String tripNum = scanner.nextLine();
			if(tripNum.equals("-1"))
			{
				System.out.println("Returning to main menu...");
				return;
			}
			System.out.print("Date(yyyy-mm-dd): ");
			String date = scanner.nextLine();
			if(date.equals("-1"))
			{
				System.out.println("Returning to main menu...");
				return;
			}
			System.out.print("Scheduled Start Time(hh:mm:ss): ");
			String scheduledStartTime = scanner.nextLine();
			if(scheduledStartTime.equals("-1"))
			{
				System.out.println("Returning to main menu...");
				return;
			}
			try {
				stmnt.executeUpdate("DELETE FROM TripOffering WHERE "
						+ "TripNumber = " + tripNum + " AND Date = '" + date + "' AND " 
						+ "ScheduledStartTime = '" + scheduledStartTime + "';");
			} catch (SQLException e) {
				System.out.println("Failed to delete."
						+ "\nReturning to main menu...");
				e.printStackTrace();
				editTripOffering();
			}
			System.out.println("Trip Offering deleted successfully.");
			break;
		case 2: // add a set of trip offerings
			System.out.println("You have selected >> Add a Set of Trip Offerings\n"
					+ "Please provide the following information:");
			boolean addMore = true;
			
			while (addMore) {
				System.out.print("Trip Number: ");
				tripNum = scanner.nextLine();
				System.out.print("Date(yyyy-mm-dd): ");
				date = scanner.nextLine();
				System.out.print("Scheduled Start Time(hh:mm:ss): ");
				scheduledStartTime = scanner.nextLine();
				System.out.print("Scheduled Arrival Time(hh:mm:ss): ");
				String scheduledArrivalTime = scanner.nextLine();
				System.out.print("Driver NameLine: ");
				String driverName = scanner.nextLine();
				System.out.print("Bus ID: ");
				String busID = scanner.nextLine();
				try {
					stmnt.executeUpdate("INSERT INTO TripOffering VALUES ("
							+ tripNum + ",'" + date + "','" + scheduledStartTime
							+ "','" + scheduledArrivalTime + "','" + driverName
							+ "'," + busID + ");");
				} catch (SQLException e) {
					System.out.println("Failed to add."
							+ "\nReturning to main menu...");
					e.printStackTrace();
					return;
				}
				System.out.println("Bus added successfully. Add more (Y/N)? ");
				String userInpt = scanner.nextLine();
				while(!userInpt.equals("Y")) {
					if (userInpt.equals("N")) {
						addMore = false;
						break;
					}
					else
					{
						System.out.println("Invalid input. Add more (Y/N)? ");
						userInpt = scanner.nextLine();
					}
				}
			}
			break;
		case 3: // Change the Driver of a Trip Offering(i.e given TripNumber, Date, 
			 //* ScheduledStartTime);
			System.out.println("You have selected >> Change the Driver of a Trip Offering\n"
					+ "Please provide the following information or type -1 anytime to cancel");
			System.out.print("Trip Number: ");
			tripNum = scanner.nextLine();
			if(tripNum.equals("-1"))
			{
				System.out.println("Returning to edit menu...");
				return;
			}
			System.out.print("Date(yyyy-mm-dd): ");
			date = scanner.nextLine();
			if(date.equals("-1"))
			{
				System.out.println("Returning to edit menu...");
				return;
			}
			System.out.print("Scheduled Start Time(hh:mm:ss): ");
			scheduledStartTime = scanner.nextLine();
			if(scheduledStartTime.equals("-1"))
			{
				System.out.println("Returning to edit menu...");
				return;
			}
			System.out.print("What is the driver's name you want to change to? ");
			String driverName = scanner.nextLine();
			if(driverName.equals("-1"))
			{
				System.out.println("Returning to edit menu...");
				return;
			}
			try {
				stmnt.executeUpdate("UPDATE TripOffering SET DriverName = '" 
						+ driverName + "' WHERE " + "TripNumber = " 
						+ tripNum + " AND Date = '" + date + "' AND " 
						+ "ScheduledStartTime = '" + scheduledStartTime + "';");
			} catch (SQLException e) {
				System.out.println("Failed to update."
						+ "\nReturning to main menu...");
				e.printStackTrace();
				editTripOffering();
			}
			
			System.out.println("Changed driver successfully.");
			break;
		case 4: //Change the Bus of a Trip Offering
			System.out.println("You have selected >> Change the Bus of a Trip Offering\n"
					+ "Please provide the following information or type -1 anytime to cancel");
			System.out.print("Trip Number: ");
			tripNum = scanner.nextLine();
			if(tripNum.equals("-1"))
			{
				System.out.println("Returning to edit menu...");
				return;
			}
			System.out.print("Date(yyyy-mm-dd): ");
			date = scanner.nextLine();
			if(date.equals("-1"))
			{
				System.out.println("Returning to edit menu...");
				return;
			}
			System.out.print("Scheduled Start Time(hh:mm:ss): ");
			scheduledStartTime = scanner.nextLine();
			if(scheduledStartTime.equals("-1"))
			{
				System.out.println("Returning to edit menu...");
				return;
			}
			System.out.print("What is the Bus ID you want to change to? ");
			String busID = scanner.nextLine();
			if(busID.equals("-1"))
			{
				System.out.println("Returning to edit menu...");
				return;
			}
			try {
				stmnt.executeUpdate("UPDATE TripOffering SET BusID = '" 
						+ busID + "' WHERE " + "TripNumber = " 
						+ tripNum + " AND Date = '" + date + "' AND " 
						+ "ScheduledStartTime = '" + scheduledStartTime + "';");
			} catch (SQLException e) {
				System.out.println("Failed to update."
						+ "\nReturning to edit menu...");
				editTripOffering();
			}
			
			System.out.println("Changed bus successfully.");
			break;
			
		default:
			System.out.println("Invalid input! Enter an integer(0-8).");
			break;
		}
		editTripOffering();
		
	}

	public void displayStops() {
		/**
		 * Display the stops of a given trip ( i.e. the attributes of the table TripStopInfo).
		 */
		System.out.println("\nYou have selected >> Display Stops of a given Trip\n"
				+ "Please provide the following information:");
		System.out.print("Trip Number: ");
		String tripNumberInput = scanner.nextLine();
		
		// display results 
		ResultSet result;
		try {
			result = stmnt.executeQuery("SELECT * FROM TripStopInfo "
					+ " WHERE TripNumber = " + tripNumberInput
					+ " ORDER BY SequenceNumber;");
			System.out.println("Displaying Stops for Trip: " + tripNumberInput);
			// print query
			int count = 0;
			while (result.next()) {
				count++;
				
				// get data, hard coded the index
				String stopNumber = result.getString(2);
				String sequenceNumber = result.getString(3);
				String drivingTime = result.getString(4);
				
				System.out.printf("%s) Stop Number[%s], Driving Time[%s]\n",
						sequenceNumber, stopNumber, drivingTime);

			}
			if (count == 0) {
				System.out.println("No result");
			}
		} catch (SQLException e) {
			System.out.println("Invalid information provided.\n"
					+ "Returning to main menu...");
			e.printStackTrace();
		}
		return;
	}
	
	public void displayWeeklySchedule()
	{ // driver name and date
		System.out.println("\nYou have selected >> Display Driver's Weekly Schedule\n"
				+ "Please provide the following information:");
		System.out.print("Driver's Name: ");
		String driverName = scanner.nextLine();
		System.out.print("Month (MM): ");
		String month = scanner.nextLine();
		System.out.print("Day (dd): ");
		String day = scanner.nextLine();
		System.out.print("Year (yyyy): ");
		String year = scanner.nextLine();
		String date = year + "-" + month + "-" + day;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			System.out.println("Date is not in the correct format.");
			e.printStackTrace();
		}
		// Getting day of the week of the given date
		// 1-Su 2-M  3-T  4-W 5-Th 6-F 7-Sa 
		int curr_day_of_week = cal.get(Calendar.DAY_OF_WEEK);
		//System.out.println("Current: " + sdf.format(cal.getTime()));
		
		// Finding the first day of the week the given date is in
		cal.add(Calendar.DATE, -(curr_day_of_week - 1));
		cal.get(Calendar.DAY_OF_WEEK);
		String fromDate = sdf.format(cal.getTime());
		// Finding the last day of the week
		cal.add(Calendar.DATE, 6);
		cal.get(Calendar.DAY_OF_WEEK);
		String toDate = sdf.format(cal.getTime());
		
		// display results 
		ResultSet result;
		try {
			result = stmnt.executeQuery("SELECT * FROM TripOffering"
					+ " WHERE DriverName = '" + driverName
					+ "' AND Date >= '" + fromDate 
					+ "' AND Date <= '" + toDate + "' ORDER BY Date;");
			System.out.println("Displaying " + driverName + "'s Schedule from "
					+ fromDate + " to " + toDate + "...");
			// print query
			while (result.next()) {
				String tripNumber = result.getString(1);
				String scheduledDate = result.getString(2);
				String startTime = result.getString(3);
				String arrivalTime = result.getString(4);
				String busID = result.getString(6);
				
				System.out.printf("TripNumber:%s[%s], %s-%s, busID[%s]\n", 
						tripNumber, scheduledDate, startTime, arrivalTime, busID);
			}
		} catch (SQLException e) {
			System.out.println("Invalid information provided.\n"
					+ "Returning to main menu...");
			e.printStackTrace();
		}
		return;
	}

	public void addDriver() {
		/**
		 * Add a driver (DriverName, DriverTelephoneNumber)
		 */
		System.out.println("\nYou have selected >> Add Driver\nPlease provide the following information"
				+ " or type -1 to not add driver:");
		System.out.print("Driver's Name: ");
		String driverNameInput = scanner.nextLine();
		if(driverNameInput.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}
		System.out.print("Driver's telephone Number (xxx)-xxx-xxxx: ");
		String telephoneInput = scanner.nextLine();
		if(telephoneInput.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}

		try {
			stmnt.executeUpdate("INSERT INTO Driver VALUES ('"
					+ driverNameInput + "', '" + telephoneInput + "');");
		} catch (SQLException e) {
			System.out.println("Failed to add."
					+ "\nReturning to main menu...");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Driver added successfully.");
		
	}

	public void addBus(){
		/**
		 * Add a bus (BusID, Model, Year)
		 */
		System.out.println("\nYou have selected >> Add Bus\nPlease provide the following information"
				+ " or type -1 to not add bus:");
		System.out.print("BusID: ");
		String busID = scanner.nextLine();
		if(busID.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}
		System.out.print("Model: ");
		String model = scanner.nextLine();
		if(model.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}
		System.out.print("Year: ");
		String year = scanner.nextLine();
		if(year.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}
		try {
			stmnt.executeUpdate("INSERT INTO Bus VALUES ("
					+ busID + ",'" + model + "'," + year + ");");
		} catch (SQLException e) {
			System.out.println("Failed to add."
					+ "\nReturning to main menu...");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Bus added successfully.");
		
	}

	public void deleteBus() {
		/**
		 * delete a bus
		 */
		System.out.println("\nYou have selected >> Delete Bus\nPlease provide the following information"
				+ " or type -1 to not delete bus:");
		System.out.print("BusID: ");
		String busID = scanner.nextLine();
		if(busID.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}
		try {
			stmnt.executeUpdate("DELETE FROM Bus WHERE "
					+ "busID = " + busID + ";");
		} catch (SQLException e) {
			System.out.println("Failed to delete."
					+ "\nReturning to main menu...");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Bus deleted successfully.");
	}

	public void insertTrip() {
		/**
		 * Record (insert) the actual data of a given trip offering specified by its key. The actual 
		 * data include the attributes of the table ActualTripStopInfo.
		 */
		System.out.println("\nYou have selected >> Add Trip Stop Info\nPlease provide the following information"
				+ " or type -1 to return to menu:");
		System.out.print("Trip Number: ");
		String tripNumberInput = scanner.nextLine();
		if(tripNumberInput.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}

		System.out.print("Month (MM): ");
		String month = scanner.nextLine();
		System.out.print("Day (dd): ");
		String day = scanner.nextLine();
		System.out.print("Year (yyyy): ");
		String year = scanner.nextLine();
		String date = year + "-" + month + "-" + day;
		if (month.equals("-1") || day.equals("-1") || year.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			System.out.println("Date is not in the correct format.");
			e.printStackTrace();
		}
		
		System.out.print("Scheduled Start Time(hh:mm:ss): ");
		String startTimeInput = scanner.nextLine();
		if(startTimeInput.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}
		
		System.out.print("Stop Number: ");
		String stopNumberInput = scanner.nextLine();
		if(stopNumberInput.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}
		
		System.out.print("Arrival Time Input(hh:mm:ss): ");
		String arrivalTimeInput = scanner.nextLine();
		if(arrivalTimeInput.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}
		
		System.out.print("Actual Start Time Input(hh:mm:ss): ");
		String actualStartTimeInput = scanner.nextLine();
		if(actualStartTimeInput.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}
		
		System.out.print("Actual Arrival Time Input(hh:mm:ss): ");
		String actualArrivalTimeInput = scanner.nextLine();
		if(actualArrivalTimeInput.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}
		
		System.out.print("Number of Passenger Got In Input(integer): ");
		String passengerInInput = scanner.nextLine();
		if(passengerInInput.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}
		
		System.out.print("Number of Passenger Got Out Input(integer): ");
		String passengerOutInput = scanner.nextLine();
		if(passengerOutInput.equals("-1"))
		{
			System.out.println("Returning to main menu...");
			return;
		}

		try {
			stmnt.executeUpdate("INSERT INTO ActualTripStopInfo VALUES ("
					+ tripNumberInput + ",'" + date + "', '" + startTimeInput + "', " 
					+ stopNumberInput + ", '" + arrivalTimeInput + "', '" + actualStartTimeInput
					+ "', '" + actualArrivalTimeInput + "', " + passengerInInput 
					+ ", " + passengerOutInput + ");");
		} catch (SQLException e) {
			System.out.println("Failed to add."
					+ "\nReturning to main menu...");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Actual Trip Stop Info added successfully.");
		
	}
	
}