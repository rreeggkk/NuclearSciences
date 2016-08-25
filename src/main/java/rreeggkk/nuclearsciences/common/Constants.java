package rreeggkk.nuclearsciences.common;

import org.apfloat.Apfloat;

public class Constants {
	public static final String MOD_ID = "nuclearsciences";
	public static final String MOD_NAME = "Nuclear Sciences";
	public static final String MOD_VERSION = "0.0.0";
	
	public static final long PRECISION = 128;
	
	public static final Apfloat C = new Apfloat(299792458, PRECISION); // 299,792,458
	public static final Apfloat C2 = C.multiply(C);
	
	public static final int TICKS_PER_SECOND = 20;
}
