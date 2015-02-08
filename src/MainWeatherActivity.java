/**
 * @author Akshay
 * 
 * 
 * Main Class for Weather Activity
 */
package com.example.weatherapp;

import java.lang.reflect.Type;
import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainWeatherActivity extends Activity {
	private TextView temperature, max_temp, min_temp, city_title, desc,
			wind_speed, humidity, pressure;
	private ImageView img_iconview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String user_location = "Dallas,US"; // Hard coded location:Dallas Texas

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_weather);
		temperature = (TextView) findViewById(R.id.tv_temp);
		desc = (TextView) findViewById(R.id.tv_description);
		wind_speed = (TextView) findViewById(R.id.tv_windspeed);
		humidity = (TextView) findViewById(R.id.tv_humudity);
		pressure = (TextView) findViewById(R.id.tv_pressure);
		img_iconview = (ImageView) findViewById(R.id.imgv_icon);
		city_title = (TextView) findViewById(R.id.tv_title);
		city_title.setText(user_location);

		checkNetworkConnection(user_location);

	}

	public void checkNetworkConnection(String loc) // Method for checking
													// network connection
	{
		ConnectivityManager cmgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = cmgr.getActiveNetworkInfo();
		if (netinfo != null && netinfo.isConnected()) // checking if network is
														// actively connected
		{
			WeatherDataRetrieveTask wtask = new WeatherDataRetrieveTask();
			wtask.execute(loc);
		} else
			Toast.makeText(getApplicationContext(), "Network Not Available",
					50000).show();
	}

	private class WeatherDataRetrieveTask extends
			AsyncTask<String, Void, String> { // Async Task for retriving
												// weather data in json

		@Override
		protected String doInBackground(String... params) {
			String jsonData = null;
			WeatherHttpConnection wtc = new WeatherHttpConnection();
			try {
				jsonData = wtc.retrieveWeatherData(params[0]); // call for
																// method in
																// Weather
																// connection
																// class to
																// retrive data

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return jsonData; // Json Data is returned
		}

		protected void onPostExecute(String result) {
			String icon_string;
			Type col = new TypeToken<Collection<WeatherInfo>>() {
			}.getType();
			ParentInfo pinfo = new Gson().fromJson(result, ParentInfo.class); // Using
																				// the
																				// Gson
																				// library
																				// for
																				// converting
																				// Json
																				// data
																				// to
																				// Object

			icon_string = pinfo.getWeather().get(0).getIcon();
			desc.setText(pinfo.getWeather().get(0).getDescription());
			temperature.setText(Math.round(pinfo.getMain().getTemp() - 273.15)
					+ "°c");
			humidity.setText("Humidity:" + pinfo.getMain().getHumidity() + "%");
			pressure.setText("Pressure:" + pinfo.getMain().getPressure()
					+ "hPa");
			wind_speed.setText("Wind:" + pinfo.getWind().getSpeed() + "mps");

			ImageTask imgTask = new ImageTask();
			imgTask.execute(icon_string);

		}
	}

	private class ImageTask extends AsyncTask<String, Void, byte[]> { // Async
																		// Task
																		// for
																		// Fetching
																		// Weather
																		// Icon

		@Override
		protected byte[] doInBackground(String... params) {
			// TODO Auto-generated method stub
			byte[] image_data = null;
			ParentInfo pinfo = new ParentInfo();
			try {
				WeatherHttpConnection wtc = new WeatherHttpConnection();
				image_data = wtc.getWeatherIconImage(params[0]); // call to
																	// method in
																	// weather
																	// connection
																	// class for
																	// fetching
																	// icon data
				Log.d(getClass().getSimpleName(), "Icon byte array value="
						+ image_data.toString());
				return image_data; // data returned in byte format
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	protected void onPostExecute(byte[] icon_bytearray) {
		Log.d(getClass().getSimpleName(), "Byte value in onpost="
				+ icon_bytearray);
		if (icon_bytearray != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(icon_bytearray, 0, // creating
					// a
					// bitmap
					// from
					// the
					// byteArray
					// of
					// icon
					icon_bytearray.length);
			img_iconview.setImageBitmap(bitmap); // setting the bitmap in the
													// image view
		}
	}

}
