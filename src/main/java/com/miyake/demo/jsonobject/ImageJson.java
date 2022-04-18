package com.miyake.demo.jsonobject;

import java.util.Calendar;
import java.util.Date;

public class ImageJson {
	public Date date;
	public byte[] image;
	
	public ImageJson() {}
	
	public ImageJson(byte[] image) {
		this.image = image;
		this.date = Calendar.getInstance().getTime();
	}
}
