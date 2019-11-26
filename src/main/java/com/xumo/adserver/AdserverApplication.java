package com.xumo.adserver;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Madhavan Thiyagarajan
 *
 */
@SpringBootApplication
public class AdserverApplication implements CommandLineRunner {

	@Autowired
	private CSVParser csvParser;

	public static void main(String[] args) {
		SpringApplication.run(AdserverApplication.class, args);
	}

	// access command line arguments
	@Override
	public void run(String... args) throws Exception {
		//Parse the CSV's and populate the datastructures[ArrayList] in memory
		//Going to use core java based rudimentary CSV parser - Not trying out external libraries because of time constraint.
		try {
			csvParser.parseCSV();
		} catch (Throwable t) {
			System.out.println(t.getMessage());
			return; //Any exception while parsing CSV's? then exit the application.
		}

		
		//If BannerAds list data structure is empty then no data to process so exit the application.
		if (csvParser.getBannerAdsList() == null || csvParser.getBannerAdsList().size() == 0) {
			System.out.println("Banner Ads List is empty. Please check the CSV file. ");
			return;
		}
		
		//Find out the active campaign count. Only if its more than zero then proceed to find out the Banner Ad. URL.
		long count = csvParser.getBannerAdsList()
				.stream()
				.filter(banner -> bannerWithActiveCampaign(banner))
				.count();
		if (count == 0) {
			System.out.println("There are no Banner Ads URL to be displayed for the given campaign input.");
			return;
		} 
		
		//Print all active Banner Ad Image URL's 
		System.out.println("There are totally [" + count + "] Banner Ads Image URL's whose campaign is active currently");
		System.out.println("Banner Ads Image URL's for active campaign as follows:-");
		csvParser.getBannerAdsList()
		.stream()
		.filter(banner -> bannerWithActiveCampaign(banner))
		.forEach(banner -> System.out.println("URL[" + banner.getImageUrl() + "]"));
		
		
		//Print only 10 active Banner Ad Image URL's 
		System.out.println("***********Going to print 10 random Banner Ads Image URL's***************");
		csvParser.getBannerAdsList()
		.stream()
		.filter(banner -> randomBannerWithActiveCampaign(banner))
		.limit(10)
		.forEach(banner -> System.out.println("URL[" + banner.getImageUrl() + "]"));
		
	}

	
	/**
	 * Check whether the supplied BannerAd has been attached to an active campaign. 
	 * @param b - Object representing BannerAds
	 * @return boolean - returns a boolean to confirm whether the supplied BannerAd has an active campaign or not.
	 */
	private static boolean bannerWithActiveCampaign(BannerAds b) {
		Date todayDate = new Date();
		if ((b.getCampaign().getStartDate().before(todayDate) || b.getCampaign().getStartDate().equals(todayDate))
				&& (b.getCampaign().getEndDate().after(todayDate) || b.getCampaign().getEndDate().equals(todayDate))) {
			//System.out.println("CID-->"+b.getCampaign().getId());
			//System.out.println("BID-->"+b.getCampaign().getId());
			return true;
		}
		return false;
	}
	

	/**
	 * Check whether the supplied BannerAd has been attached to an active campaign and also choose it randomly using Math Random function. 
	 * @param b - Object representing BannerAds
	 * @return boolean - returns a boolean to confirm whether the supplied BannerAd has an active campaign or not.
	 */
	private static boolean randomBannerWithActiveCampaign(BannerAds b) {
		Date todayDate = new Date();
		if ((b.getCampaign().getStartDate().before(todayDate) || b.getCampaign().getStartDate().equals(todayDate))
				&& (b.getCampaign().getEndDate().after(todayDate) || b.getCampaign().getEndDate().equals(todayDate))) {
			//Not all active campaigns
			double d = Math.random();
			if (d > 0.5)
				return true;
		}
		return false;
	}

}
