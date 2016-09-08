package rreeggkk.nuclearsciences.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;

public class NuclearMaterialUtil {
	public static void calculateOutputs(Map<AIsotope<?,?>, Apfloat> feed, Apfloat feedMass, Apfloat totalFeedMass, Map<AIsotope<?,?>, Apfloat> product, Map<AIsotope<?,?>, Apfloat> tails, AIsotope<?,?> iso, double productAssay, double tailsAssay, Apfloat productMass, Apfloat tailsMass) {
		
		//Apfloat totalMass = productMass.add(tailsMass);
		
		Apfloat isoProd = productMass.multiply(new Apfloat(productAssay, Constants.PRECISION));
		Apfloat isoTail = tailsMass.multiply(new Apfloat(tailsAssay, Constants.PRECISION));
		
		for (Entry<AIsotope<?,?>, Apfloat> e : feed.entrySet()) {
			if (e.getKey() == iso) {
				product.put(e.getKey(), isoProd);
				tails.put(e.getKey(), isoTail);
				e.setValue(e.getValue().subtract(isoTail).subtract(isoProd).precision(Constants.PRECISION));
			} else {
				product.put(e.getKey(), e.getValue().divide(totalFeedMass).multiply(productMass.multiply(new Apfloat(1).subtract(new Apfloat(productAssay, Constants.PRECISION)))));
				tails.put(e.getKey(), e.getValue().divide(totalFeedMass).multiply(tailsMass.multiply(new Apfloat(1).subtract(new Apfloat(tailsAssay, Constants.PRECISION)))));
				e.setValue(e.getValue().subtract(product.get(e.getKey())).subtract(tails.get(e.getKey())).precision(Constants.PRECISION));
			}
		}
	}
	
	public static HashMap<String, Apfloat> sumMaps(Map<String, Apfloat> map1, Map<String, Apfloat> map2) {
		HashMap<String, Apfloat> newMap = new HashMap<String, Apfloat>();
		
		newMap.putAll(map1);
		
		for (Entry<String, Apfloat> e : map2.entrySet()) {
			newMap.put(e.getKey(), e.getValue().add(newMap.getOrDefault(e.getKey(), new Apfloat(0))).precision(Constants.PRECISION));
		}
		
		return newMap;
	}
	
	public static HashMap<AIsotope<?,?>, Apfloat> sumMassMaps(Map<AIsotope<?,?>, Apfloat> map1, Map<AIsotope<?,?>, Apfloat> map2) {
		HashMap<AIsotope<?,?>, Apfloat> newMap = new HashMap<AIsotope<?,?>, Apfloat>();
		
		newMap.putAll(map1);
		
		for (Entry<AIsotope<?,?>, Apfloat> e : map2.entrySet()) {
			newMap.put(e.getKey(), e.getValue().add(newMap.getOrDefault(e.getKey(), new Apfloat(0))).precision(Constants.PRECISION));
		}
		
		return newMap;
	}
	
	public static <T> boolean areCloseEnough(Map<T, Apfloat> map1, Map<T, Apfloat> map2) {
		return areCloseEnough(map1, map2, new Apfloat("1E-112", Constants.PRECISION));
	}
	
	public static <T> boolean areCloseEnough(Map<T, Apfloat> map1, Map<T, Apfloat> map2, Apfloat epsilon) {
		HashMap<T, Apfloat> newMap = new HashMap<T, Apfloat>();
		
		newMap.putAll(map1);
		
		for (Entry<T, Apfloat> e : map2.entrySet()) {
			if (!newMap.containsKey(e.getKey())) {
				return false;
			}
			newMap.put(e.getKey(), ApfloatMath.abs(e.getValue().subtract(newMap.getOrDefault(e.getKey(), new Apfloat(0))).precision(Constants.PRECISION)));
		}
		
		return newMap.values().stream().allMatch((v)->v.compareTo(epsilon)<=0);
	}
}
