package io.github.rreeggkk.nuclearsciences.common.util;

import org.apfloat.Apfloat;

import io.github.rreeggkk.nuclearsciences.common.Constants;

public final class TimeConversion {

	public static Apfloat secToTick(Apfloat sec) {
		return sec.precision(Constants.PRECISION).multiply(new Apfloat(Constants.TICKS_PER_SECOND, Constants.PRECISION));
	}

	public static Apfloat secToTick(double sec) {
		return secToTick(new Apfloat(sec, Constants.PRECISION));
	}

	public static Apfloat minToTick(Apfloat min) {
		return min.precision(Constants.PRECISION).multiply(new Apfloat(60*Constants.TICKS_PER_SECOND, Constants.PRECISION));
	}

	public static Apfloat minToTick(double min) {
		return minToTick(new Apfloat(min, Constants.PRECISION));
	}

	public static Apfloat hourToTick(Apfloat hr) {
		return hr.precision(Constants.PRECISION).multiply(new Apfloat(60*60*Constants.TICKS_PER_SECOND, Constants.PRECISION));
	}

	public static Apfloat hourToTick(double hr) {
		return hourToTick(new Apfloat(hr, Constants.PRECISION));
	}

	public static Apfloat dayToTick(Apfloat day) {
		return day.precision(Constants.PRECISION).multiply(new Apfloat(24*60*60*Constants.TICKS_PER_SECOND, Constants.PRECISION));
	}

	public static Apfloat dayToTick(double day) {
		return dayToTick(new Apfloat(day, Constants.PRECISION));
	}

	public static Apfloat yearToTick(Apfloat years) {
		return years.precision(Constants.PRECISION).multiply(new Apfloat(365.2425*24*60*60*Constants.TICKS_PER_SECOND, Constants.PRECISION));
	}

	public static Apfloat yearToTick(double years) {
		return yearToTick(new Apfloat(years, Constants.PRECISION));
	}
}
