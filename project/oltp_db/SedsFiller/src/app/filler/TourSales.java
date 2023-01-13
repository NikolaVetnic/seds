package app.filler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TourSales {

	public static void main(String[] args) {
		
		try (BufferedReader br = new BufferedReader(new FileReader("res/tsData.csv"));
			 PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("res/out.txt")));
		) {
			String line = br.readLine();
			int idx = 0;
			
			while ((line = br.readLine()) != null) {
				
				String[] tokens = line.split(",");
				idx++;
				
				String tsQuantity = tokens[0];
				String NVP_DW_Customer_custId = tokens[1];
				String tsCreatedFirst = tokens[2];
				String tsCreatedLast = tokens[3];
				
				String out = String.format(
						"INSERT INTO NVP_DW_TourSales (tsId, tsQuantity, NVP_DW_Customer_custId, tsCreatedFirst, tsCreatedLast) VALUES (%d, %s, %s, '%s', '%s');", 
						idx, tsQuantity, NVP_DW_Customer_custId, tsCreatedFirst, tsCreatedLast);
				
				pw.println(out);
			}
		} catch (IOException e) {
			System.out.println("Greska prilikom kopiranja");
		}
	}
}
