package part1;

public class VendItem implements Vendible {
	private int itemID;
	private static int nextItemID = 0;
	private String name;
	private double unitPrice;
	private int qtyAvailable;
	private static int MAX_QTY = 10;	//maximum qty for each individual item
	
	public VendItem(String name, double price) {
		this.itemID = nextItemID;
		nextItemID++;
		setName(name);
		setUnitPrice(price);
		qtyAvailable = 1;	//By default only one stock is added of an item
	}
	
	public VendItem(String name, double price, int qtyAvailable) {
		this.itemID = nextItemID;
		nextItemID++;
		setName(name);
		setUnitPrice(price);
		setQtyAvailable(qtyAvailable);
	}
	
	//GETTERS
	public String getName() {	return name;
	}	
	public double getPrice() {	return unitPrice;
	}
	public int getQtyAvailable() {	return qtyAvailable;
	}		
	
	@Override
	public String toString() {
		return "" + name + "\n\tPrice: £" + unitPrice + "0"	+ "\n\tQuantity available: " + qtyAvailable + "\n";
	}

	//SETTERS
	private void setName(String name) {
		this.name = name;
	}
	
	private void setUnitPrice(double price) {
		this.unitPrice = price;
	}
	
	private void setQtyAvailable(int qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}
	
	//OTHER METHODS
	
	//Should update the stock of one item by parameter qtyAdded 
	public boolean restock(int qtyAdded) {
		boolean valid = false;
		
		if ((MAX_QTY - qtyAvailable)>= qtyAdded && qtyAdded > 0) {
			qtyAvailable += qtyAdded;
			
			valid = true;
			return valid;	
		}
		
		else {
			return valid;	
		}	
	}
	
	public String deliver() {
		String info = "Thank you for purchasing: " + name;	
		qtyAvailable --; // takes away one stock as the item is being purchased
 		return info; 
	}
}
