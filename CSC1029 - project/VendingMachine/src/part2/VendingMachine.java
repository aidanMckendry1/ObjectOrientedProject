package part2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VendingMachine {
	
	private String owner;
	private int maxItems = 10;
	private int itemCount = 0;	// amount of seperate items in the array ()
	private VendItem[] stock = new VendItem[maxItems];	// used to store VendItem instances
	//Data must be read in as an array list so to keep the max amount of items at 10 I have 
	//transfered the values in mainStock(csv file) to the stock array
	private ArrayList <VendItem> mainStock = new ArrayList<VendItem>();
	
	static String csvIPath = "././VendItem.csv"; 
	static String csvOPath = "././VendItem.csv";
	
	//TOTAL STOCK VARIABLE FOR SETTING STATUS?
	
	private double totalMoney;
	private double userMoney;
	private Status vmStatus = Status.VENDING_MODE;
	
	//Constructor for the vending machine object
	public VendingMachine(String owner, int maxItems) {
		setOwner(owner);
		setMaxItems(maxItems);
		//Data is taken from the csv file in the constructor so it is initialised with the pre saved data
		//VendItemRead.importItemData(csvIPath,true).toArray(stock);
		this.mainStock = VendItemRead.importItemData(csvIPath,true);
		mainStock.toArray(stock);
		this.itemCount = mainStock.size();
	}
	
	//----GETTERS
	
	public int getItemCount() {	return itemCount;
	}
	public String getRestockID(int i, int qtyAdded) {	
		String result;
		if (stock[i].restock(qtyAdded) == true) {
			result = "Item successfully restocked";
			return result;
		}
		else {
			result = "Item could not be restocked";
			return result;

		}
	}
	public double getTotalMoney() {	return totalMoney;
	}
	public double getUserMoney() {	return userMoney;
	}
	public Status getVmStatus() {	return vmStatus;
	}
	public String getOwner() {	return owner;
	}
	public int getMaxItems() {	return maxItems;
	}
	
	//Should return information regarding the system ~~ owner? totalMoney? stock? itemCount? vmStatus?
	public String getSystemInfo() {
		String systemInfo = "";
		systemInfo += "Owner: " + owner + "\n";
		systemInfo += "Maximum amount of items possible: " + maxItems + "\n";
		systemInfo += "Number of items currently held: " + itemCount + "\n";
		systemInfo += "Stock of items: " + listItems() + "\n";
		systemInfo += "Total amount of cash in machine: " + String.format("%.2f", totalMoney) + "\n";
		systemInfo += "Current status of this machine: " + vmStatus.getStatus() + "\n"; 
		
		return systemInfo;		
	}


	//----SETTERS
	private void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	private void setOwner(String owner) {
		this.owner = owner;
	}
	
	//----OTHER METHODS
	
	public String exportData() {
		List<VendItem> items = Arrays.asList(stock);
		//new ArrayList<VendItem>(Arrays.asList(stock)) ;
		//VendItemWrite.exportStats(new ArrayList<VendItem>(Arrays.asList(stock)), csvOPath);
		VendItemWrite.exportStats(items, csvOPath);
		return "Data has been saved.";
	}
	
	//Allows user to purchase an item as long as they have the correct amount of money and 
	//returns the sale info after, calling on deliver from that VendItem object and calculating change
	public String purchaseItem(int itemIndex) {
		String saleInfo = ""; //Should return sale information of the product (price, change etc.)
		String itemName = stock[itemIndex].getName();
		double itemPrice = stock[itemIndex].getPrice();
		int itemQty = stock[itemIndex].getQtyAvailable();
		
		if (userMoney >= itemPrice) {
			
			if (itemQty > 0) {
				
				itemQty--;
				userMoney -= itemPrice;
				
				setStatus(Status.VENDING_MODE);
				saleInfo += "Machine is in: " + vmStatus.getStatus(); 
				saleInfo += stock[itemIndex].deliver() + "\n";
				saleInfo += "\tYour change is: £" + String.format("%.2f", userMoney) + "\n";
				return saleInfo;
			}
			else {
				// out of stock counter = item count?
				
				setStatus(Status.SERVICE_MODE);
				saleInfo += "Machine is in: " + vmStatus.getStatus(); 
				saleInfo += "Item out of stock.";
				//SET TO SERVICE MODE
				return saleInfo;
			}
		}
		//else if not enough money input - enter another...
		else {
			double remainingCost;
			remainingCost = itemPrice - userMoney;
			
			setStatus(Status.SERVICE_MODE);
			saleInfo += "Machine is in: " + vmStatus.getStatus(); 
			saleInfo +=	"Insufficient credit. \n";
			saleInfo += "Enter another: £" + String.format("%.2f", remainingCost) + " to purchase " + itemName;
			
			return saleInfo;
		}	
	}
	
	// coin should be: 5p, 10p, 20p, 50p, £1 and £2, in pence
	public boolean insertCoin(int coin) {
		boolean valid = false;
		int count = 0;	//counts number of coins entered
		double cash; //correct format of double for money rather that pennies
		
		if(coin == 5 || coin == 10 || coin == 20 || coin == 50 || coin == 100 || coin == 200) {
			valid = true;
			cash = (double)coin/100;
			totalMoney += cash;
			userMoney += cash;
			count++;
			//Array to see the actual coins inserted?
			return valid;
		}
		else {
			return valid;
		}		
	}

	
	public boolean addNewItem(VendItem newItem) {
		boolean valid = false;
		if (itemCount < maxItems) {
			stock[itemCount] = newItem;
			itemCount++;
			
			valid = true;
			return valid;			
		}			
		else {	
			return valid;
		}
	}
	
	public String listItems() {
		String items = "";
		int count = 1;
		//Loop which prints out name, quantity & price of an item instance	
		for (int i = 0; i <= itemCount - 1; i++) {
			items += "Item no. " + count + ": ";
			items += stock[i].toString();
			count++;
		}
		return items;
	}
	
	public void setStatus(Status vmStatus) {
		//VENDING_MODE or SERVICE_MODE
		//SERVICE MODE SHOULD BE ACTIVATED IN THREE OCCASIONS
		// 1) selected item out of stock/ not enough cash entered (Cancels transaction)
		// 2) last item in machine is purchased
		// 3) engineer user
		
		this.vmStatus = vmStatus;
	}
	
	public void reset() {
		//should reset the vending machine, empty of items and cash
		itemCount = 0;
		userMoney = 0;
		totalMoney = 0;
		Arrays.fill(stock, null);
	}
	
}
