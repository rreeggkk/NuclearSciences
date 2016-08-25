package rreeggkk.nuclearsciences.common.util;

import java.util.Random;

public class RandomUtil {
	public static final Random rand = new Random();
	
	public static double boundedGaussian(double mean, double standardDev, double min, double max) {
		double val;
		do {
			val = rand.nextGaussian()*standardDev + mean;
		} while (min > val || val > max);
		return val;
	}
}
