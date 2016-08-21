package io.github.rreeggkk.nuclearsciences.common.util;

import java.util.Map;
import java.util.stream.Collectors;

public class MapUtil {
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map , boolean ascending)
	{
		if (ascending) {
			return map.entrySet().stream().sorted((o1,o2)->o1.getValue().compareTo(o2.getValue()))
				.collect(Collectors.toMap((e)->e.getKey(), (e)->e.getValue()));
		} else {
			return map.entrySet().stream().sorted((o1,o2)->o2.getValue().compareTo(o1.getValue()))
				.collect(Collectors.toMap((e)->e.getKey(), (e)->e.getValue()));
		}
	}
}
