package io.github.rreeggkk.nuclearsciences.common.util;

import org.apfloat.Apfloat;

import io.github.rreeggkk.nuclearsciences.NuclearSciences;
import io.github.rreeggkk.nuclearsciences.common.Constants;

public class TemperatureUtil {
	
	public static final Apfloat AMBIENT = new Apfloat(273.15 + 25, Constants.PRECISION);
	
	public static Apfloat convertFromKelvin(Apfloat kelvin) {
		String t = NuclearSciences.instance.config.temperatureUnit;
		if (t.equals("C")) {
			return kelvin.subtract(new Apfloat(273.15, Constants.PRECISION));
		} else if (t.equals("F")) {
			return kelvin.multiply(new Apfloat(9d/5, Constants.PRECISION)).subtract(new Apfloat(459.67, Constants.PRECISION));
		}
		return kelvin;		
	}
}
