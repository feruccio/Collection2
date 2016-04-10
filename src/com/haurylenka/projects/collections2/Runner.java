package com.haurylenka.projects.collections2;

import com.haurylenka.projects.collections2.cache.LFUCache;

public class Runner {

	public static void main(String[] args) {
		LFUCache<String, Integer> cache = new LFUCache<>(9, 0.7);
		cache.put("one", 1);
		cache.get("one");
		cache.get("two");
		cache.get("one");
		cache.put("two", 2);
		cache.get("two");
		cache.get("one");
		cache.put("three", 3);
		cache.get("one");
		cache.get("two");
		cache.put("four", 4);
		cache.get("one");
		cache.get("two");
		cache.get("one");
		cache.get("three");
		cache.put("five", 5);
		cache.get("one");
		cache.get("two");
		cache.get("three");
		cache.put("six", 6);
		cache.get("one");
		cache.get("four");
		cache.get("six");
		cache.put("seven", 7);
		cache.get("one");
		cache.get("two");
		cache.get("seven");
		cache.put("eight", 8);
		cache.get("one");
		cache.get("two");
		cache.get("three");
		cache.put("nine", 9);
		cache.put("ten", 10);
		cache.get("one");
		cache.get("two");
		cache.get("three");
		cache.get("four");
		cache.get("five");
		cache.get("six");
		cache.get("seven");
		cache.get("eight");
		cache.get("nine");
		cache.get("ten");
	}

}
