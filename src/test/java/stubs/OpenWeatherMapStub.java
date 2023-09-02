package stubs;

import com.github.jeffmadrid.weatherfulapp.model.dto.Cloud;
import com.github.jeffmadrid.weatherfulapp.model.dto.Coordinates;
import com.github.jeffmadrid.weatherfulapp.model.dto.Main;
import com.github.jeffmadrid.weatherfulapp.model.dto.Rain;
import com.github.jeffmadrid.weatherfulapp.model.dto.Sys;
import com.github.jeffmadrid.weatherfulapp.model.dto.Weather;
import com.github.jeffmadrid.weatherfulapp.model.dto.WeatherResponse;
import com.github.jeffmadrid.weatherfulapp.model.dto.Wind;

import java.util.Arrays;
import java.util.List;

public class OpenWeatherMapStub {

    public static WeatherResponse stubWeatherResponse() {
        return new WeatherResponse(
            stubCoordinates(),
            stubWeatherList(),
            "stations",
            stubMain(),
            10000,
            stubWind(),
            stubRain(),
            null,
            stubClouds(),
            1661870592L,
            stubSys(),
            7200,
            3163858L,
            "Zocca",
            200
        );
    }

    public static Coordinates stubCoordinates() {
        return new Coordinates(10.99, 44.34);
    }

    public static List<Weather> stubWeatherList() {
        return Arrays.asList(new Weather(501, "Rain", "moderate rain", "10d"));
    }

    public static Main stubMain() {
        return new Main(298.48, 298.74, 297.56, 300.05, 1015, 64, 1015, 933);
    }

    public static Wind stubWind() {
        return new Wind(0.62, 349, 1.18);
    }

    public static Rain stubRain() {
        return new Rain(3.16, null);
    }

    public static Cloud stubClouds() {
        return new Cloud(100);
    }

    public static Sys stubSys() {
        return new Sys(2, 2075663L, "IT", 1661834187L, 1661882248L);
    }


}
