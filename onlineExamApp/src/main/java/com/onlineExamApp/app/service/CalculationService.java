package com.onlineExamApp.app.service;

import org.springframework.stereotype.Component;

@Component("calculateService")
public class CalculationService {

	String dDisplay;
	String hDisplay;
	String mDisplay;
	String sDisplay;
	
	public String durationDis(Integer seconds) {
	
		dDisplay = null;
		hDisplay = null;
		mDisplay = null; 
		sDisplay = null;
		
		Integer d = (seconds / (3600*24));
		Integer h = (seconds % (3600*24) / 3600);
		Integer m = (seconds % 3600 / 60);
		Integer s = (seconds % 60);

		if(d>0) {
			if(d==1) {
				dDisplay = Integer.toString(d) + " day ";
			}else {
				dDisplay = Integer.toString(d) + " days ";
			}
		}else {
			dDisplay ="";
		}
		
		if(h>0) {
			if(h==1) {
				hDisplay = Integer.toString(h) + " hour ";
			}else {
				hDisplay = Integer.toString(h) + " hours ";
			}
		}else {
		hDisplay ="";
		}
		
		if(m>=0) {
			if(m==1) {
				mDisplay = Integer.toString(m) + " minute ";
			}else {
				mDisplay = Integer.toString(m) + " minutes ";
			}
		}
		
		if(s>=0) {
			if(s==1) {
				sDisplay = Integer.toString(s) + " second ";
			}else {
				sDisplay = Integer.toString(s) + " seconds ";
			}
		}

		return dDisplay + hDisplay + mDisplay + sDisplay ;
	
	}
	
}
