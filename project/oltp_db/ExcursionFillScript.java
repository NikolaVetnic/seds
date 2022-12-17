package nv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Filler {

	private static String[] localCities = {
			"Subotica", "Backa Topola",
			"Zrenjanin", "Novi Becej",
			"Kanjiza", "Senta",
			"Pancevo", "Vrsac",
			"Sombor", "Apatin",
			"Novi Sad", "Beocin",
			"Ruma", "Sremska Mitrovica",
			"Sabac", "Loznica",
			"Valjevo", "Ljig",
			"Smederevo", "Velika Plana",
			"Pozarevac", "Veliko Gradiste",
			"Kragujevac", "Arandjelovac",
			"Jagodina", "Cuprija",
			"Bor", "Negotin",
			"Zajecar", "Sokobanja",
			"Uzice", "Sjenica",
			"Gornji Milanovac", "Cacak",
			"Kraljevo", "Raska",
			"Krusevac", "Trstenik",
			"Nis", "Razanj",
			"Prokuplje", "Blace",
			"Pirot", "Dimitrovgrad",
			"Leskovac", "Medvedja",
			"Vranje", "Bosilegrad",
			"Pristina", "Obilic",
			"Pec", "Istok",
			"Prizren", "Orahovac",
			"Kosovska Mitrovica", "Vucitrn",
			"Gnjilane", "Kosovska Kamenica",
			"Prijedor", "Kozarska Dubica",
			"Banja Luka", "Mrkonjic Grad",
			"Doboj", "Modrica",
			"Bijeljina", "Zvornik",
			"Istocno Sarajevo", "Visegrad"
	};

	private static String source = "hckey,capital,capital_lat,capital_lng\n"
			+ "ad,Andorra,42.5,1.5165\n"
			+ "al,Tirana,41.3275,19.8189\n"
			+ "at,Vienna,48.2,16.3666\n"
			+ "ba,Sarajevo,43.85,18.383\n"
			+ "be,Brussels,50.8333,4.3333\n"
			+ "bg,Sofia,42.6833,23.3167\n"
			+ "by,Minsk,53.9,27.5666\n"
			+ "ch,Bern,46.9167,7.467\n"
			+ "cy,Nicosia,35.1667,33.3666\n"
			+ "cz,Prague,50.0833,14.466\n"
			+ "de,Berlin,52.5218,13.4015\n"
			+ "dk,Copenhagen,55.6786,12.5635\n"
			+ "ee,Tallinn,59.4339,24.728\n"
			+ "es,Madrid,40.4,-3.6834\n"
			+ "fi,Helsinki,60.1756,24.9341\n"
			+ "fr,Paris,48.8667,2.3333\n"
			+ "gb,London,51.5072,-0.1275\n"
			+ "gr,Athens,37.9833,23.7333\n"
			+ "hr,Zagreb,45.8,16\n"
			+ "hu,Budapest,47.5,19.0833\n"
			+ "ie,Dublin,53.3331,-6.2489\n"
			+ "is,Reykjavik,64.15,-21.95\n"
			+ "it,Rome,41.896,12.4833\n"
			+ "li,Vaduz,47.1337,9.5167\n"
			+ "lt,Vilnius,54.6834,25.3166\n"
			+ "lu,Luxembourg,49.6117,6.13\n"
			+ "lv,Riga,56.95,24.1\n"
			+ "mc,Monaco,43.7396,7.4069\n"
			+ "md,Chisinau,47.005,28.8577\n"
			+ "me,Podgorica,42.466,19.2663\n"
			+ "mk,Skopje,42,21.4335\n"
			+ "mt,Valletta,35.8997,14.5147\n"
			+ "nl,Amsterdam,52.35,4.9166\n"
			+ "no,Oslo,59.9167,10.75\n"
			+ "pl,Warsaw,52.25,21\n"
			+ "pt,Lisbon,38.7227,-9.1449\n"
			+ "ro,Bucharest,44.4334,26.0999\n"
			+ "rs,Belgrade,44.8186,20.468\n"
			+ "ru,Moscow,55.7522,37.6155\n"
			+ "se,Stockholm,59.3508,18.0973\n"
			+ "si,Ljubljana,46.0553,14.515\n"
			+ "sk,Bratislava,48.15,17.117\n"
			+ "sm,San Marino,43.9172,12.4667\n"
			+ "ua,Kiev,50.4334,30.5166\n"
			+ "";

	public static void main(String[] args) throws ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat simpleDatePrintFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Random rnd = new Random();

		List<String> cityNames = new ArrayList<>();

		for (String token : source.split("\n"))
			cityNames.add(token.split(",")[1]);

		for (int i = 1; i <= 300; i++) {

			Date exFrom = between(
					simpleDateFormat.parse("2022-03-23 20:59:35"),
					simpleDateFormat.parse("2022-05-23 20:59:35"));
			String exFromString = simpleDatePrintFormat.format(exFrom) + ".0";

			Date exTo = between(
					simpleDateFormat.parse("2022-06-23 20:59:35"),
					simpleDateFormat.parse("2022-08-23 20:59:35"));
			String exToString = simpleDatePrintFormat.format(exTo) + ".0";

			// String exLocation = cityNames.get(rnd.nextInt(0, cityNames.size()));
			String exLocation = localCities[rnd.nextInt(0, localCities.length)];

			int exRating = rnd.nextInt(0, 10) + 1;
			int NVP_SRC_Guide_guId = rnd.nextInt(0, 100) + 1;
			int NVP_SRC_Tour_tourId = rnd.nextInt(0, 1000) + 1;

			System.out.printf(
					"insert into NVP_SRC_Excursion (exId, exFrom, exTo, exLocation, exRating, NVP_SRC_Guide_guId, NVP_SRC_Tour_tourId) values (%d, TO_TIMESTAMP('%s', 'YYYY-MM-DD HH24:MI:SS.FF'), TO_TIMESTAMP('%s', 'YYYY-MM-DD HH24:MI:SS.FF'), '%s', %d, %d, %d); %n",
					i, exFromString, exToString, exLocation, exRating, NVP_SRC_Guide_guId, NVP_SRC_Tour_tourId);
		}
	}

	public static Date between(Date startInclusive, Date endExclusive) {

		long startMillis = startInclusive.getTime();
		long endMillis = endExclusive.getTime();
		long randomMillisSinceEpoch = ThreadLocalRandom
				.current()
				.nextLong(startMillis, endMillis);

		return new Date(randomMillisSinceEpoch);
	}
}
