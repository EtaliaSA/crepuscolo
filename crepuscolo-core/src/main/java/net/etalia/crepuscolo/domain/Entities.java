package net.etalia.crepuscolo.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Entities {

	private static Map<Character, Class<? extends Entity>> classes = new HashMap<Character, Class<? extends Entity>>();

	private Entities() {
	}

	public static void add(Character prefix, Class<? extends Entity> clazz) {
		classes.put(prefix, clazz);
	}

	@SuppressWarnings("unchecked")
	public static void add(Character prefix, String clazz) throws ClassNotFoundException {
		add(prefix, (Class<? extends Entity>) Class.forName(clazz));
	}

	public static Class<? extends Entity> getDomainClass(Character prefix) {
		return classes.get(prefix);
	}

	public static Class<? extends Entity> getDomainClass(String id) {
		return getDomainClass(id.charAt(0));
	}

	public static Character getPrefix(Class<? extends Entity> clazz) {
		Character prefix = null;
		for (Entry<Character, Class<? extends Entity>> entry : classes.entrySet()) {
			if (entry.getValue() == clazz) {
				prefix = entry.getKey();
				break;
			}
		}
		if (prefix == null) {
			throw new IllegalArgumentException("Cannot find any prefix associated with this Class");
		}
		return prefix;
	}
}
