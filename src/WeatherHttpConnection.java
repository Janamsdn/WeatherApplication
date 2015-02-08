/**
 * 
 */
package com.example.weatherapp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

/**
 * @author Akshay
 * 
 * Class for the fetching Json Data from OpenWeatherMap API 
 *
 */

  public class WeatherHttpConnection {							//Connection class for fetching json Data from Weather API								

	private final String Weather_URL = "http://api.openweathermap.org/data/2.5/weather?q=";		//URL for fetching jason Data for weather
	private final String Image_URL = "http://openweathermap.org/img/w/";					//URL for fetching icon for day's weather
	InputStream is = null;
	String data = null;

	public String retrieveWeatherData(String loc) throws IOException {			//Method for Retriving Json Data
		StringBuffer strbuff = null;
		try {
			URL url = new URL(Weather_URL + loc);
			Log.d(getClass().getSimpleName(), "URL=" + url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();	//Opening a connection with the specified URL
			con.setRequestMethod("GET");			
			con.setConnectTimeout(15000);
			con.connect();
			is = con.getInputStream();
			strbuff = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((data = br.readLine()) != null)
				strbuff.append(data + "\r\n");					//appending json data to a Stringbuffer

			is.close();
			con.disconnect();
			return strbuff.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public byte[] getWeatherIconImage(String icon_data) {		//Method for retriving Weather Icon
		byte[] buff = new byte[1024];				//Declaring a new byte array
		try {
			URL url = new URL(Weather_URL + icon_data + ".png");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();  //opening a connection for seperate image URL
			con.setRequestMethod("GET");
			con.setConnectTimeout(15000);
			con.connect();
			is = con.getInputStream();
			ByteArrayOutputStream bfs = new ByteArrayOutputStream();
			while (is.read(buff) != -1)
				bfs.write(buff);

			return bfs.toByteArray();		//converting the outputstream to Byte Array
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
