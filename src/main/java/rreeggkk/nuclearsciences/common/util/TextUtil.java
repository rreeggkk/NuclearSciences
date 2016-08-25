package rreeggkk.nuclearsciences.common.util;

import org.apfloat.Apfloat;

import rreeggkk.nuclearsciences.common.Constants;

public class TextUtil {
	/**
	 * Color Coding
	 */
	public static final String BLACK = (char) 167 + "0";
	public static final String BLUE = (char) 167 + "1";
	public static final String GREEN = (char) 167 + "2";
	public static final String TEAL = (char) 167 + "3";
	public static final String RED = (char) 167 + "4";
	public static final String PURPLE = (char) 167 + "5";
	public static final String ORANGE = (char) 167 + "6";
	public static final String LIGHT_GRAY = (char) 167 + "7";
	public static final String GRAY = (char) 167 + "8";
	public static final String LIGHT_BLUE = (char) 167 + "9";
	public static final String BRIGHT_GREEN = (char) 167 + "a";
	public static final String BRIGHT_BLUE = (char) 167 + "b";
	public static final String LIGHT_RED = (char) 167 + "c";
	public static final String PINK = (char) 167 + "d";
	public static final String YELLOW = (char) 167 + "e";
	public static final String WHITE = (char) 167 + "f";

	/**
	 * Text formatting
	 */
	public static final String OBFUSCATED = (char) 167 + "k";
	public static final String BOLD = (char) 167 + "l";
	public static final String STRIKETHROUGH = (char) 167 + "m";
	public static final String UNDERLINE = (char) 167 + "n";
	public static final String ITALIC = (char) 167 + "o";
	public static final String END = (char) 167 + "r";
	
	private static final String[] METRIC_PREFIXES = new String[]{"y", "z", "a", "f", "p", "n", "µ", "m", "", "k", "M", "G", "T", "P", "E", "Z", "Y"};
	private static final int METRIC_PREFIXES_ZERO = 8;
	
	public static String getUnitString(Apfloat base, long finalPrecision, String unit, boolean addSpace) {
		int prefix = 0;
		while (prefix < 8 && base.compareTo(new Apfloat(1000, Constants.PRECISION))>0) {
			prefix++;
			base = base.divide(new Apfloat(1000, Constants.PRECISION)).precision(Constants.PRECISION);
		}
		while (prefix > -8 && base.compareTo(new Apfloat(1, Constants.PRECISION))<0) {
			prefix--;
			base = base.multiply(new Apfloat(1000, Constants.PRECISION)).precision(Constants.PRECISION);
		}
		return base.precision(finalPrecision).toString(!(base.compareTo(new Apfloat(1000, Constants.PRECISION))>0 || base.compareTo(new Apfloat(1, Constants.PRECISION))<0)) + (addSpace ? " " : "") + METRIC_PREFIXES[prefix + METRIC_PREFIXES_ZERO] + unit;
	}
}
