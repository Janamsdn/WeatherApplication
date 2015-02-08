/**
 * 
 */
package com.example.weatherapp;

/**
 * @author Akshay
 * 
 * Class containing weather parameters for icon and description
 *
 */
public class WeatherInfo {			
	int id;
	String main, description,icon;
	byte[]icon_data;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
