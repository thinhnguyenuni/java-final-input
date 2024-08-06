package fa.training.main;

import java.util.Scanner;

import fa.training.services.Function;

public class Management {
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		int choice = -1;
		do {
			System.out.println("=====CONTRACT MANAGEMENT SYSTEM=====");
			System.out.println("0. Exit");
			System.out.println("1. Add a contract");
			System.out.println("2. Show data from database without sort");
			System.out.println("3. Show data from database with sort");
			System.out.println("4. Update CarNumber of a contract using ResultSet");
			System.out.print("Please choose function you'd like to do:");
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid number format");
				continue;
			}

			switch (choice) {
			case 1:
				Function.insertCDR();
				break;
			case 2:
				Function.showContractsFromDB();
				break;
			case 3:
				Function.showContractsFromDBWithSorted();
				break;
			case 4:
//				Function.updateContract();
				break;

			case 0:
				System.out.println("Exit program");
				break;
			default:
				System.out.println("Invalid choice");
				break;
			}
		} while (choice != 0);

	}
}
