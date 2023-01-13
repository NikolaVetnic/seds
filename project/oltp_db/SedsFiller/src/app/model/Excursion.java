package app.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Excursion {
	
//	private static final SimpleDateFormat simpleDatePrintFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat simpleDatePrintFormat = new SimpleDateFormat("dd-MMM-yyyy");

	private int exId;
	private Date exFrom;
	private Date exTo;
	private String exLocation;
	private int exRating;
	private int guId;
	private int tourId;
	
	public Excursion(int exId, Date exFrom, Date exTo, String exLocation, int exRating, int guId, int tourId) {
		this.exId = exId;
		this.exFrom = exFrom;
		this.exTo = exTo;
		this.exLocation = exLocation;
		this.exRating = exRating;
		this.guId = guId;
		this.tourId = tourId;
	}
	
	@Override
	public String toString() {
		
		String exFromString = simpleDatePrintFormat.format(exFrom);
		String exToString = simpleDatePrintFormat.format(exTo);
		
		return String.format(
				"INSERT INTO NVP_SRC_Excursion " + 
				"(exId, exFrom, exTo, exLocation, exRating, NVP_SRC_Guide_guId, NVP_SRC_Tour_tourId) " + 
				"VALUES (%d, '%s', '%s', '%s', %d, %d, %d);",
				exId, exFromString, exToString, exLocation, exRating, guId, tourId);
	}
}
