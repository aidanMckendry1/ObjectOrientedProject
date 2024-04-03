package part2;

import java.util.ArrayList;
import java.util.Scanner;

public class VMUI {
	static final String [] options = {"List all items", "Insert coin", "Purchase item", "Quit"};
	static final String [] engOptions = {"View system info", "Set vending machine status", "Add new item", "Restock", "Reset", "Export data", "Quit"};
	static final int QUIT = options.length;
	static final int eQUIT = engOptions.length;
	static final int max = 10;
	static int count = 0;	// no of items in array	
	static Scanner in = new Scanner(System.in);	
	static String title = "Vending Machine menu";
	static String engTitle = "Engineer menu";
	static Menu vMMenu = new Menu(title, options);
	static Menu engMenu = new Menu(engTitle, engOptions);
	private static final String [] engineerLogIn = {"user", "pass"}; // holds engineers username (user) and password (pass)

	static VendItem[] items = new VendItem[max];
	
	static VendingMachine vendMachine = new VendingMachine ("Aidan", max);
	
			
	public static void main(String[] args) {
		int choice;
		
		do {
			vMMenu.display();

			System.out.println("Enter choice: ");
		
			choice = in.nextInt();
		
			if(choice != QUIT) {
					processChoice(choice);
			}
		}
		while( choice != QUIT );
		System.out.println("\nGoodbye!");
		in.close();
		}
	
	//Choices the user can make
	private static void processChoice(int choice) { 
		
		switch(choice) {
	
			case 0: 	engineerLogIn(); break;	
			case 1: 	listItems(); break;
			case 2: 	insertCoin(); break;
			case 3: 	purchaseItem(); break;
		
			default: System.out.println("Option "+choice+" is invalid.");
	
		} 
		System.out.println();
	}
	
	//Choices an engineer can make
	private static void processEngChoice(int choice) { 
		
		switch(choice) {
		
			case 1: getSystemInfo();	break;
			case 2: setStatus();	break;
			case 3:	addNewItem();	break;
			case 4: restock();		break;
			case 5: reset();	break;
			case 6: exportData();	break;
		
			default: System.out.println("Option "+choice+" is invalid.");	
			
		}
		System.out.println();
	}

	//Saves the current stock to the csv file
	private static void exportData() {
		vendMachine.exportData();
	}

	//username and password protected log in for an engineer
	private static void engineerLogIn() {
		
		in.nextLine();
		System.out.println("Enter username: ");
		String userAttempt = in.nextLine();
		
		System.out.println("Enter password: ");
		String passAttempt = in.nextLine();
		
		if(userAttempt.equals(engineerLogIn[0]) && passAttempt.equals(engineerLogIn[1])) {
			
			//If the login details are correct, this do while loop will be repeated until quit is selected
			int choice;
			
			do {
				engMenu.display();

				System.out.println("Enter choice: ");
				
				choice = in.nextInt();
				
				if(choice != eQUIT) {
				processEngChoice(choice);
				}
				}
				while( choice != eQUIT );
				System.out.println("Returning to main vendnig machine menu.");
			
		}
		else {
			System.out.println("Incorrect username or password.");
		}
	}
	
	//Prints all the information hels currently in the vending machine object
	private static void getSystemInfo() {
		String systemInfo = vendMachine.getSystemInfo();
		System.out.println(systemInfo);
	}
	
	//Asks user for a status to be input to call the set status from the vending machine object
	private static void setStatus() {
		int choice;
		
		System.out.println("1) Set status to Vending mode.");
		System.out.println("2) Set status to Service mode.");
		System.out.println("3) Cancel.");

		if (in.hasNextInt()){
			choice = in.nextInt();
			
			if (choice == 1) {
				vendMachine.setStatus(Status.VENDING_MODE);
				System.out.println("Machine is now in: " + vendMachine.getVmStatus().getStatus());
			}
			else if (choice == 2) {
				vendMachine.setStatus(Status.SERVICE_MODE);
				System.out.println("Machine is now in: " + vendMachine.getVmStatus().getStatus());
			}
			else if (choice == 3) {
				System.out.println("Request canceled.");
			}
			else {
				System.out.println(choice + "is not an option.");
			}
		}
		
		else {
			System.out.println("Invalid choice");
			in.nextLine();
			in.nextLine();
		}
	}
	
	//Allows the user to enter an item to restock by an amount that they have decided, as long as it 
	//doesnt exceed the max amount per item of 10
	private static void restock() {
		int choice;
		
		listItems();
		System.out.println("Enter item no. to restock.");
		
		if (in.hasNextInt()){
			choice = in.nextInt();	// Not allowing item to be picked after invalid selection
			if  (choice >= 1 && choice <= vendMachine.getItemCount()) {
				choice -= 1; // taking away one to match the stock index for the vendItem object
				
				System.out.println("Enter amount to add: ");
				if (in.hasNextInt()){
					int qtyAdded = in.nextInt();
					
					System.out.println(vendMachine.getRestockID(choice, qtyAdded));					
				}
				
				else {
					System.out.println("Invalid number of items. ");
				}
			}
			
			else {
				in.nextLine();
				System.out.println("Item cant be found");
			}
		}
		
		else {
			in.nextLine();
			System.out.println("Invalid item no.");
			in.nextLine();
		}		
	}

	//Lists all items currently in stock including the price and quantity
	private static void listItems() {
		System.out.println(vendMachine.listItems());
	}

	//Asks the user to enter a coin value in pence, if this represents a valid coin, it is added to the machine
	private static void insertCoin() {
		int coin;
		System.out.println("Enter a coin value in pence. ");
		if (in.hasNextInt()) {
			coin = in.nextInt();
			in.nextLine();

			if (vendMachine.insertCoin(coin) == true) {
				System.out.println("Coin added to machine successfully.");
				System.out.printf(" --> Current amount = " + "£" + "%.2f" , vendMachine.getUserMoney());
				System.out.println();
			}
			else {
				System.out.println("Invalid coin.");
				System.out.println("Enter an integer representing a coin, eg 5, 10, 20...");
			}
		}
		else {
			System.out.println("Enter an integer representing a coin, eg 5, 10, 20...");
			in.nextLine();
			in.nextLine();
		}
	} 
	
	//Allows the user to add a new item to the machine by asking for the name, price & qty
	private static void addNewItem() {
		String name = "";
		double price = 0;
		int qty = 0;
		in.nextLine();
		
		System.out.println("Enter name of product: ");
		name = in.nextLine();
		
		System.out.println("Enter price of product: ");
		if(in.hasNextDouble()) {
			price = in.nextDouble();
		}
		else {
			System.out.println("Invalid price");
		}
		in.nextLine();
		
		System.out.println("Enter quantity of product: ");
		if(in.hasNextInt()) {
			qty = in.nextInt();
		}
		else {
			System.out.println("Invalid quantity");
		}
		in.nextLine();

		if (qty == 0 && price > 0.05) {
			VendItem newItem = new VendItem(name, price);
			if (vendMachine.addNewItem(newItem) == true) {
				System.out.println("Item successfully added");
			}
			else {
				System.out.println("Item could not be added");
			}
		}
		//qty must be more than or equal to 1 and less than or equal to 10
		//price must be more than or equal to 5p
		else if (qty >= 1 && qty <=10 && price > 0.05) {
			VendItem newItem = new VendItem(name, price, qty);
			if (vendMachine.addNewItem(newItem) == true) {
				System.out.println("Item successfully added");
			}
			else {
				System.out.println("Item could not be added");
			}
		}
		else {
			System.out.println("Item could not be added");
		}
	}

	//wipes the machine of all items and money entered
	private static void reset() {
		vendMachine.reset();
		System.out.println("Vending machine successfully reset.");
	}
	
	//Displays the list of stock for the user and allows them to choose an item to buy, 
	//System then tells them the relevant sale information or asks them to enter another amount of money depending on the amount already added
	private static void purchaseItem() {
		int choice;
		String saleInfo;
		
		listItems();
		System.out.println("Enter item no. to purchase.");
		
		if (in.hasNextInt()){
			choice = in.nextInt();	// Not allowing item to be picked after invalid selection
			if  (choice >= 1 && choice <= vendMachine.getItemCount()) {
				choice -= 1; // taking away one to match the stock index for the vendItem object
				
				saleInfo = vendMachine.purchaseItem(choice);
				System.out.println(saleInfo);
			}
			else {
				in.nextLine();
				System.out.println("Item cant be found");
			}
		}
		
		else {
			in.nextLine();
			System.out.println("Invalid item no.");
			in.nextLine();
		}
	}
}



