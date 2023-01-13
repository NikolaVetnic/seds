package app.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tour {
	
	private static final SimpleDateFormat simpleDatePrintFormat = new SimpleDateFormat("dd-MMM-yyyy");

	private int tourId;
	private Date tourCreated;
	private String tourRoomNo;
	private Date tourDateFrom;
	private Date tourDateTo;
	private int tourPrice;
	private String tourStatus;
	private int tourRating;
	private int custId;
	private int hotId;
	private int agId;
	private List<Excursion> excursions;
	
	public Tour(int tourId, Date tourCreated, String tourRoomNo, Date tourDateFrom, Date tourDateTo, 
			int tourPrice, String tourStatus, int tourRating, int custId, int hotId, int agId) {
		this.tourId = tourId;
		this.tourCreated = tourCreated;
		this.tourRoomNo = tourRoomNo;
		this.tourDateFrom = tourDateFrom;
		this.tourDateTo = tourDateTo;
		this.tourPrice = tourPrice;
		this.tourStatus = tourStatus;
		this.tourRating = tourRating;
		this.custId = custId;
		this.hotId = hotId;
		this.agId = agId;
		this.excursions = new ArrayList<>();
	}

	public int getTourId() {
		return tourId;
	}

	public Date getTourDateFrom() {
		return tourDateFrom;
	}

	public Date getTourDateTo() {
		return tourDateTo;
	}

	public int getCustId() {
		return custId;
	}

	public List<Excursion> getExcursions() {
		return excursions;
	}
	
	public void addExcursions(Excursion e) {
		excursions.add(e);
	}
	
	public List<String> getSqlLines() {
		
		List<String> out = new ArrayList<>();
		
		out.add(this.toString());
		
		for (Excursion e : excursions)
			out.add("\t" + e);
		
		return out;
	}

	@Override
	public String toString() {
		
		String tourCreatedString = simpleDatePrintFormat.format(tourCreated);
		String tourDateFromString = simpleDatePrintFormat.format(tourDateFrom);
		String tourDateToString = simpleDatePrintFormat.format(tourDateTo);
		
		return String.format(
				"INSERT INTO NVP_SRC_Tour " + 
				"(tourId, tourCreated, tourRoomNo, tourDateFrom, tourDateTo, tourPrice, tourStatus, tourRating, NVP_SRC_Customer_custId, NVP_SRC_Hotel_hotId, NVP_SRC_Agent_agId) " + 
				"VALUES (%d, '%s', '%s', '%s', '%s', %d, '%s', %d, %d, %d, %d);", 
				tourId, tourCreatedString, tourRoomNo, tourDateFromString, tourDateToString, tourPrice, tourStatus, tourRating, custId, hotId, agId);
	}
}
