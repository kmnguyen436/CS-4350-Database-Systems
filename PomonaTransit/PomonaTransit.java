package lab4;

import java.sql.SQLException;
import java.util.Scanner;

public class PomonaTransit {

	public static void main(String[] args) throws SQLException {
		System.out.println("Welcome to pomona transit system.");
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/pomonatransit";
		String jdbcUser = "root";
		String jdbcPassword = "password";
		
		// JDBC
		JDBC mysql = new JDBC(jdbcUrl, jdbcUser, jdbcPassword);
		
		// main loop
		boolean running = true;
		Scanner scanner = new Scanner(System.in);
		int menuInput = -1;
		
		while (running) {
			System.out.print(mainMenu);
			try {
				String userInput = scanner.nextLine();
				menuInput = Integer.parseInt(userInput);
			}
			catch (Exception InputMismatchException) {
//				scanner.nextLine();
				menuInput = -1;
			}
			
			switch (menuInput) {
			case 0:
				running = false;
				break;
			case 1: // display trip schedule ***
				mysql.displayTripSchedule();
				break;
			case 2: // edit Trip Offering ***
				mysql.editTripOffering();
				break;
			case 3: // display stops ***
				mysql.displayStops();
				break;
			case 4: // display driver schedule ***
				mysql.displayWeeklySchedule();
				break;
			case 5: // add driver
				mysql.addDriver();
				break;
			case 6: // add bus ***
				mysql.addBus();
				break;
			case 7: // delete bus ***
				mysql.deleteBus();
				break;
			case 8: // insert trip
				mysql.insertTrip();
				break;
			default:
				System.out.println("Invalid input! Enter an integer(0-8).");
				break;
			}
			if (menuInput != 0) {
				System.out.println("Press enter to continue...");
				scanner.nextLine();
			}
			
		}
		
		scanner.close();
		mysql.closeConnections();
		System.out.println("\nGoodbye!");
	}
	
	private static String mainMenu = 
			"\n0) Exit\n"
			+ "1) Display Trip Schedule\n"
			+ "2) Edit Trip Offering\n"
			+ "3) Display Stops\n"
			+ "4) Display Driver's Weekly Schedule\n"
			+ "5) Add Driver\n"
			+ "6) Add Bus\n"
			+ "7) Delete Bus\n"
			+ "8) Insert Trip\n"
			+ "Enter an integer(0-8): ";

}
