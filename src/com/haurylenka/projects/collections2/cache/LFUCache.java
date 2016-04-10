package com.haurylenka.projects.collections2.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache<K, V> {

	private final Map<K, Entry<V>> storage;
	private final LinkedHashSet<K>[] entries;
	private int size;
	private int frequencyMax;
	private double evictionFactor;
	
	
	public LFUCache(int size, double evictionFactor) {
		if (size < 1) {
			throw new IllegalArgumentException("Cache size must be at least 1");
		}
		if (evictionFactor <= 0 || evictionFactor >= 1) {
			throw new IllegalArgumentException("Eviction factor must be in the "
					+ "range from 0 to 1 exclusively");
		}
		this.size = size;
		this.frequencyMax = size - 1;
		this.evictionFactor = evictionFactor;
		this.storage = new HashMap<>();
		this.entries = new LinkedHashSet[frequencyMax];
		initEntries();
	}
	
	private void initEntries() {
		for (int i = 0; i < entries.length; i++) {
			entries[i] = new LinkedHashSet<>();
		}
	}

	public void put(K key, V value) {
		System.out.println("Adding a new entry: " + key + " - " + value);
		if (storage.size() == size) {
			doEviction();
		}
		Entry<V> entry = storage.get(key);
		if (entry == null) {
			LinkedHashSet<K> curEntries = entries[0];
			curEntries.add(key);
			entry = new Entry<>(value, 0);
			storage.put(key, entry);
		} else {
			entry.value = value;
		}
	}

	private void doEviction() {
		double goal = size * evictionFactor;
		int deleted = 0;
		int curFrequency = 0;
		while (deleted < goal) {
			LinkedHashSet<K> curEntries = entries[curFrequency++];
			Iterator<K> iterator = curEntries.iterator();
			while (iterator.hasNext() && deleted < goal) {
				K key = iterator.next();
				storage.remove(key);
				iterator.remove();
				deleted++;
			}
		}
		System.out.println(deleted + " entries evicted");
	}
	
	public V get(K key) {
		System.out.print("Retrieving the entry: " + key + " - ");
		Entry<V> entry = storage.get(key);
		if (entry != null) {
			doPromotion(key);
			System.out.println(entry.value);
			return entry.value;
		} else {
			System.out.println("no such an entry...");
			return null;
		}
	}

	private void doPromotion(K key) {
		Entry<V> entry = storage.get(key);
		int curFrequency = entry.frequency;
		LinkedHashSet<K> curEntries = entries[curFrequency];
		if (curFrequency < frequencyMax - 1) {
			int newFrequency = ++curFrequency;
			LinkedHashSet<K> newEntries = entries[newFrequency];
			curEntries.remove(key);
			newEntries.add(key);
			entry.frequency = newFrequency;
		} else {
			curEntries.remove(key);
			curEntries.add(key);
		}
	}
	
	public void remove(K key) {
		Entry<V> entry = storage.get(key);
		if (entry != null) {
			int frequency = entry.frequency;
			LinkedHashSet<K> curEntries = entries[frequency];
			curEntries.remove(key);
			storage.remove(key);
		}
	}
	
	public void clear() {
		for (int f = 0; f < size; f++) {
			LinkedHashSet<K> curEntries = entries[f];
			curEntries.clear();
		}
		storage.clear();
	}
	
	private static class Entry<V> {
		private V value;
		private int frequency;
		
		public Entry(V value, int frequency) {
			this.value = value;
			this.frequency = frequency;
		}

	}
	
	
}
