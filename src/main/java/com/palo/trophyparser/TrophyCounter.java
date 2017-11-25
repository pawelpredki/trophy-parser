package com.palo.trophyparser;

import java.util.HashMap;

public class TrophyCounter {
	HashMap<TrophyColor, Integer> countPerColor = new HashMap<TrophyColor, Integer>();

	public TrophyCounter() {
		countPerColor.put(TrophyColor.BRONZE, 0);
		countPerColor.put(TrophyColor.SILVER, 0);
		countPerColor.put(TrophyColor.GOLD, 0);
		countPerColor.put(TrophyColor.PLATINUM, 0);
	}

	public void addCount(TrophyColor color) {
		countPerColor.put(color, countPerColor.get(color) + 1);
	}

	public int getCount(TrophyColor color) {
		return countPerColor.get(color);
	}

}
