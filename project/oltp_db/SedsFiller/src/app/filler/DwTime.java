package app.filler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

public class DwTime {
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat simpleDatePrintFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
	private static final Random rnd = new Random();
	
	private static final int NUM_DAYS = 2000;

	public static void main(String[] args) throws ParseException {
		
		Date startingDate = simpleDateFormat.parse(
				String.format("%4d-%02d-%02d %02d:%02d:35 %n", 2018, 12, 1, 0, 0));
		LocalDateTime startingDateLdt = startingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("res/out.txt")));
			) {
				for (int i = 1; i < NUM_DAYS + 1; i++) {
					
					LocalDateTime ldt = startingDateLdt.plusDays(i);
					
					String dateString = String.format("%02d-%3s-%4d",
							ldt.getDayOfMonth(), ldt.getMonth().toString().substring(0, 3), ldt.getYear(), 
							rnd.nextInt(24) + 1, rnd.nextInt(60));
					
					String out = String.format(
							"INSERT INTO NVP_DW_Time (timeId, day, month, year, \"date\", dayInYear, monthInYear) VALUES (%d, %d, '%s', '%d', '%s', %d, %d);", 
							i, ldt.getDayOfMonth(), ldt.getMonth().toString(), ldt.getYear(), dateString, ldt.getDayOfYear(), ldt.getMonthValue());
					
					pw.println(out);
				}
		} catch (IOException e) {
			System.out.println("Greska prilikom kopiranja");
		}
	}
}
