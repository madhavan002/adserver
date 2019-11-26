package com.xumo.adserver;

public class BannerAds {
	private long id;
	private Campaign campaign;
	private String imageUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String url) {
		this.imageUrl = url;
	}

	public Campaign getCampaign() {
		return this.campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public BannerAds(long id,Campaign campaign, String url) {
		this.id = id;
		this.imageUrl = url;
		this.campaign = campaign;
	}

	public String toString() {
		return "BannerAd id[" + id + "] with Compaign ID[" + getCampaign().getId() + "] and Image URL["+imageUrl+"]";
	}
}
