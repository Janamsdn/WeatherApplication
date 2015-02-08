/**
 * 
 */
package com.example.weatherapp;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

/**
 * @author Akshay
 *
 */
public class ParentInfo {		//Container class for 3  classes
	@Expose
	MainInfo main;		//Declaring object of Main Class
	WindInfo wind;		//Declaring Object of Wind Class
	ArrayList<WeatherInfo> weather = new ArrayList<WeatherInfo>();  //Declaring a Arraylist of type weatherInfo 

	public MainInfo getMain() {
		return main;
	}

	public void setMain(MainInfo main) {
		this.main = main;
	}

	public WindInfo getWind() {
		return wind;
	}

	public void setWind(WindInfo wind) {
		this.wind = wind;
	}

	public ArrayList<WeatherInfo> getWeather() {
		return weather;
	}

	public void setWeather(ArrayList<WeatherInfo> weather) {
		this.weather = weather;
	}

}
