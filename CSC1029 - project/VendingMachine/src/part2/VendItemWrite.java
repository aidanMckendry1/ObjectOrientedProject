package part2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class VendItemWrite {
	public static void exportStats(List<VendItem> items, String csvOutPath) {
		try {
			PrintWriter myPw = new PrintWriter(csvOutPath);
			myPw.println("ItemName, Pirce, Quantity");
			
			for (VendItem eachItem : items) {
				if(eachItem != null){
					myPw.print(eachItem.getName() + ", ");
					myPw.print(eachItem.getPrice() + ", ");
					myPw.print(eachItem.getQtyAvailable() + "\n");
				}
			}	
			System.out.println("Item data output complete.");
			myPw.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
