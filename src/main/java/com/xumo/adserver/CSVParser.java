package com.xumo.adserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
/*
 * Utility class to parse CSV files using core java features. No external libraries used in it.
 * 
 */
@Component
public class CSVParser {

	String csvDateFormat = "dd-MMM-yyyy";
	//Initialise in memory data structure to store Campaigns and Banner Ads loaded from CSV files.
	List<Campaign> campaignList = new ArrayList<Campaign>();;
	List<BannerAds> bannerAdsList = new ArrayList<BannerAds>();
	//Constants to name the CSV files. 
	final String CAMPAIGN_CSV = "campaign.csv";
	final String BANNER_ADS_CSV = "banners.csv";

	protected void parseCSV() throws IOException {
		String[] fileNames = { CAMPAIGN_CSV, BANNER_ADS_CSV };
		//Run the same parsing logic for both the CSV files
		for (String strFile : fileNames) {
			BufferedReader br = null;
			System.out.println("Going to parse the CSV file[" + strFile + "]");
			//Load the CSV files from resource folder
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(strFile);
			//If no CSV files found under resources folder then throw error and exit
			if (is == null) {
				throw new FileNotFoundException("CSV File[" + strFile + "] not found.");
			}
			String line = "";
			try {
				//Flag to skip the first line in CSV, if its header.
				boolean isFirstLine = true;
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				while ((line = br.readLine()) != null) {
					if (isFirstLine) {
						isFirstLine = false;
						continue;
					}
					// use comma or tab as separator
					String[] fields = line.split(",");

					//Logic to process Campaign CSV files
					if (CAMPAIGN_CSV.equals(strFile)) {
						System.out.println("Fields from " + CAMPAIGN_CSV + "[" + fields[0] + "]" + "[" + fields[1] + "]"
								+ "[" + fields[2] + "]");
						//Initialise the Campaign domain object and add it into the list for in-memory storage.
						Campaign campaign = new Campaign(Integer.parseInt(fields[0]),fields[1], parseDate(fields[2]),
								parseDate(fields[3]));
						campaignList.add(campaign);
					} else if (BANNER_ADS_CSV.equals(strFile) && campaignList.size() > 0) { //Logic to process BannerAds CSV files
						System.out.println("Fields from " + BANNER_ADS_CSV + "[" + fields[0] + "]" + "[" + fields[1]
								+ "]" + "[" + fields[2] + "]");
						final long campaignId = Integer.parseInt(fields[1]);
						Campaign campaign = campaignList.stream().filter(c -> c.getId() == campaignId).findFirst()
								.get();
						BannerAds bannerAds = new BannerAds(Integer.parseInt(fields[0]), campaign, fields[2] );
						bannerAdsList.add(bannerAds);
					}
				}
			} catch (Throwable t) {
				System.out.println(t.getMessage());
				return;
			} finally {
				//Release the resource always before exiting the try/catch. 
				if (br != null) {
					br.close();
				}
			}
		}

	}


	/*
	 * Build java.util.date from the CSV date string using SimpleDataFormatter.
	 * 
	 * 
	 */
	public Date parseDate(String date) {
		try {
			return new SimpleDateFormat(csvDateFormat).parse(date);
		} catch (ParseException e) {
			System.out.println("Invalid date string passed[" + "]. Going to default to current system date.");
		} catch (Throwable te) {
			System.out.println("Invalid date string passed[" + "]. Going to default to current system date.");
		}
		// Exception case, so return current date irrespective of the input date string.
		return new Date();
	}

	public String getCsvDateFormat() {
		return csvDateFormat;
	}

	protected List<BannerAds> getBannerAdsList() {
		return bannerAdsList;
	}

}
