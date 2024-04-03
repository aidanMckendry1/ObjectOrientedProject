package part1;

import java.util.Scanner;

public class VMUI {
	static final String [] options = {"List all items", "Insert coin", "Purchase item", "Quit"};
	static final int QUIT = options.length;
	static final int max = 10;
	static int count = 0;	// no of items in array	
	static Scanner in = new Scanner(System.in);	
	static String title = "Vending Machine menu";
	static Menu vMMenu = new Menu(title, options);
	
	static VendingMachine vendMachine = new VendingMachine ("Aidan", max);
	
			
	public static void main(String[] args) {
		int choice;
		
		//initialised vend items
		VendItem item1 = new VendItem("Lucozade", 1.20);
		vendMachine.addNewItem(item1);
		VendItem item2 = new VendItem("Water", 1.00, 8);
		vendMachine.addNewItem(item2);
		VendItem item3 = new VendItem("Walkers, Spring onion", 1.40);
		vendMachine.addNewItem(item3);
		VendItem item4 = new VendItem("Pringles, ready salted", 1.40, 5);
		vendMachine.addNewItem(item4);
		
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
	
	private static void processChoice(int choice) { 
		
		switch(choice) {
	
		case 1: 	listItems(); break;
		case 2: 	insertCoin(); break;
		case 3: 	purchaseItem(); break;
	
		default: System.out.println("Option "+choice+" is invalid.");
	
		} 
	System.out.println();
	}
	
	
	private static void listItems() {
		System.out.println(vendMachine.listItems());
	}

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
	
	private static void purchaseItem() {
		//display list of vend items objects
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



