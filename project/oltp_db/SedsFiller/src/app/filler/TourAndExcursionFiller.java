package app.filler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import app.model.Excursion;
import app.model.Tour;

public class TourAndExcursionFiller {
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat simpleDatePrintFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final Random rnd = new Random();
	
	private static final int NUM_TOURS = 10_000;
	private static final int NUM_CUSTOMERS = 1000;
	private static final int NUM_HOTELS = 200;
	private static final int NUM_AGENTS = 200;
	private static final int NUM_GUIDES = 100;
	
	private static final int ROOM_NOS = 200;
	private static final String[] ROOM_LETTERS = {
			"A", "B", "C", "D", "E", "F"
	};
	
	private static final String[] statusOptions = {
		"valid", "canceled"
	};
	
	private static final int MAX_EX = 3;
	
	private static int exCnt = 0;

	public static void main(String[] args) throws ParseException {
		
		try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("res/out.txt")));
			) {
				List<Tour> tours = new ArrayList<>();
				
				for (int i = 0; i < NUM_TOURS; i++) {
					
					Date tourCreated = simpleDateFormat.parse(
							String.format("%4d-%02d-%02d %02d:%02d:35 %n",
									rnd.nextInt(3) + 2019, rnd.nextInt(3) + 1, rnd.nextInt(25),
									rnd.nextInt(24) + 1, rnd.nextInt(60)));
					
					Date tourDateFrom = simpleDateFormat.parse(
							String.format("%4d-%02d-%02d %02d:%02d:35 %n",
									rnd.nextInt(3) + 2019, rnd.nextInt(4) + 5, rnd.nextInt(25),
									rnd.nextInt(24) + 1, rnd.nextInt(60)));
					
					LocalDateTime tourDateToLdt = 
							tourDateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays((rnd.nextInt(0, 4) + 1) * 7);
					
					Date tourDateTo = simpleDateFormat.parse(
							String.format("%4d-%02d-%02d %02d:%02d:35 %n",
									tourDateToLdt.getYear(), tourDateToLdt.getMonthValue(), tourDateToLdt.getDayOfMonth(), 
									rnd.nextInt(24) + 1, rnd.nextInt(60)));
					
					String tourRoomNo = "" + rnd.nextInt(ROOM_NOS) + ROOM_LETTERS[rnd.nextInt(ROOM_LETTERS.length)];
					
					int tourPrice = rnd.nextInt(180, 600) * 100;
					
					String tourStatus = statusOptions[rnd.nextInt(0, statusOptions.length)];
					
					int tourRating = rnd.nextInt(0, 10) + 1;
					
					int custId = rnd.nextInt(0, NUM_CUSTOMERS) + 1;
					int hotId = rnd.nextInt(0, NUM_HOTELS) + 1;
					int agId = rnd.nextInt(0, NUM_AGENTS) + 1;
					
					Tour t = new Tour(
							i, tourCreated, tourRoomNo, tourDateFrom, tourDateTo, tourPrice, tourStatus, tourRating, custId, hotId, agId);
					
					addExcursions(t);
					
					pw.println(t);
					
					tours.add(t);
				}
				
				for (Tour t : tours)
					for (Excursion e : t.getExcursions())
						pw.println(e);
				
		} catch (IOException e) {
			System.out.println("Greska prilikom kopiranja");
		}
	}

	private static void addExcursions(app.model.Tour t) throws ParseException {
		
		int numEx = rnd.nextInt(MAX_EX);
		
		if (numEx == 0) return;
		
		LocalDateTime tourDateFromLdt = 
				t.getTourDateFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime tourDateToLdt = 
				t.getTourDateTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		int duration = (int) java.time.temporal.ChronoUnit.DAYS.between(tourDateFromLdt.toLocalDate(), tourDateToLdt.toLocalDate());
		int factor = duration / (numEx + 1);
		
		for (int i = 0; i < numEx; i++) {
			
			int exId = exCnt++;
			
			LocalDateTime exFromLdt = tourDateFromLdt.plusDays(factor * (i + 1));
			LocalDateTime exToLdt = exFromLdt.plusDays(rnd.nextInt(2));
			
			Date exFrom = simpleDateFormat.parse(
					String.format("%4d-%02d-%02d %02d:%02d:35 %n",
							exFromLdt.getYear(), exFromLdt.getMonthValue(), exFromLdt.getDayOfMonth(), 
							rnd.nextInt(24) + 1, rnd.nextInt(60)));
			Date exTo = simpleDateFormat.parse(
					String.format("%4d-%02d-%02d %02d:%02d:35 %n",
							exToLdt.getYear(), exToLdt.getMonthValue(), exToLdt.getDayOfMonth(), 
							rnd.nextInt(24) + 1, rnd.nextInt(60)));
			
			String exLocation = "Excursion Site #" + (rnd.nextInt(10) + 1);
			
			int exRating = rnd.nextInt(0, 10) + 1;
			
			int guId = rnd.nextInt(0, NUM_GUIDES) + 1;
			int tourId = t.getTourId();
			
			Excursion e = new Excursion(exId, exFrom, exTo, exLocation, exRating, guId, tourId);
			
			t.addExcursions(e);
		}
	}
}
