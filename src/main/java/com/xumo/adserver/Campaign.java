/**
 * 
 */
package com.xumo.adserver;

import java.util.Date;

public class Campaign {

	private long id;
	private String name;
	private Date startDate;
	private Date endDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Campaign(long id, String name, Date startDate, Date endDate) {
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String toString() {
		return "Campaign id[" + id + "] with name["+ name+"] start date[" + startDate.toString() + "] with start date["
				+ startDate.toString() + "]";
	}

}
