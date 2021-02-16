package com.solactive.price.pricingengine.dto;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TickData {

	private Map<String, PriceData> tickMap = new ConcurrentHashMap<>();
	// Linked hashset to not allow duplicate and maintain the insertion order
	private Map<Long, InstrumentData> instrumentMap = new ConcurrentHashMap<>();
	
	private ReentrantLock lock = new ReentrantLock();

	public void add(String instrument, double amt, long time) {
		lock.lock();
		try {
			addOrUpdateInstrument(instrument, amt, time);
			addOrUpdateAggregate(amt, time);
			instrumentMap.putIfAbsent(new Long(time) ,new InstrumentData(instrument,amt));
			
		} finally {
			lock.unlock();
		}
		
	}

	public void removeUnwantedTick() {
		long time = System.currentTimeMillis() - Long.parseLong("60") * 1000;
		Set<Entry<Long, InstrumentData>> setOfEntries = instrumentMap.entrySet();
		Iterator<Entry<Long, InstrumentData>> iterator = setOfEntries.iterator();
		
		while (iterator.hasNext()) {
			Entry<Long, InstrumentData> entry = iterator.next();
			Long key = entry.getKey();
			if (key < time) {
				InstrumentData data= entry.getValue();
				tickMap.get(data.getInstrument()).removeUnwantedTick(data.getAmt());
				tickMap.get("aggregate").removeUnwantedTick(data.getAmt());
				iterator.remove();
			}
		}
		
	}

	private void addOrUpdateInstrument(String instrument, double amt, long time) {
		if (tickMap.get(instrument) != null) {
			tickMap.get(instrument).update(amt, time);
		} else {
			tickMap.putIfAbsent(instrument, new PriceData(amt, time));
		}
	}

	private void addOrUpdateAggregate(double amt, long time) {
		if (tickMap.get("aggregate") != null) {
			tickMap.get("aggregate").update(amt, time);
		} else {
			tickMap.putIfAbsent("aggregate", new PriceData(amt, time));
		}
	}

	public PriceData get(String instrument) {
		//removeUnwantedTick(System.currentTimeMillis() - Long.parseLong("60") * 1000);
		return tickMap.get(instrument);
	}

	public PriceData getAggregate() {
		//removeUnwantedTick(System.currentTimeMillis() - Long.parseLong("60") * 1000);
		return tickMap.get("aggregate");
	}
}
