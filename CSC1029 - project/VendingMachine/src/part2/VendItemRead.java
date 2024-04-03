package part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class VendItemRead {
	
	private static int maxItems = 10;
	
	public static ArrayList<VendItem> importItemData(String csvFilePath, boolean hasHeader) {
		 //MotTestCentre objects will be stored here and returned to the calling method
		 ArrayList<VendItem> stock = new ArrayList<VendItem>();
	
		 try {
			 File myFile = new File(csvFilePath); 
			 Scanner mySc = new Scanner(myFile); 
		
			 if(hasHeader) {
				 mySc.nextLine();
			 }
		
			 while(mySc.hasNextLine()) {
		
				 String record = mySc.nextLine();
				 String[] parts = record.split(",");
				 
				 if (record.equals(",,")) {
					 break;
				 }
				 else {
				 	String name = parts[0].trim();
				 	double price = Double.parseDouble(parts[1].trim());
				 	int qtyAvailable = Integer.parseInt(parts[2].trim());

				 //Add a new MotTestCentre to the array list to be returned.
				 	stock.add(new VendItem(name, price, qtyAvailable));
				 //for (int i = 0; i < stock.length; i++)
				  //   stock[i] = new VendItem(name, price, qtyAvailable);
				 }
			 }
			 
			 //loop through each instance of MotTestCentre in the list and calculate
			 //the total number of cars passed and failed for all test centres.
			 /*int totalPass = 0, totalFail = 0;
			 for (VendItem eachItem : stock) {
			 totalPass += eachCentre.getNoPass();
			 totalFail += eachCentre.getNoFail();
			 }*/
			 //New instance of MotTestCentre is created, the constructor will calculate
			 //and add the total number of cars tested, pass rate and fail rate.
			 //myCentres.add(new MotTestCentre("All Mot Centres", totalPass, totalFail));
			 
			 mySc.close();
			 return stock;
		
		 } catch (FileNotFoundException e) { 
			 e.printStackTrace();
			 return null;
		 }
	 }

}
