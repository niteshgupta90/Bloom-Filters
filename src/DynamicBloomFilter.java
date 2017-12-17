package main.java.com.cs525.bf.bloom_filter;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import main.java.com.cs525.bf.bloom_filter.hash.RandomHash;

public class DynamicBloomFilter extends BasicBloomFilter {
	private static final int START_SIZE = 1000;
	List<BitSet> dynamicFilterList;

	public DynamicBloomFilter(int bitsPerElm) {
		super(START_SIZE, bitsPerElm);
		dynamicFilterList = new ArrayList<BitSet>();
		hash = new RandomHash(setSize, bitsPerElm);
		this.setFilterSize(hash.getPrime());
		set = new BitSet(filerSize());
		dynamicFilterList.add(set);
	}

	protected boolean copyNeeded() {
		return numElms == setSize;
	}

	protected void expandSet() {
		expandCapacity();
		BitSet newSet = new BitSet(setSize);
		set = newSet;
		dynamicFilterList.add(set);
	}

	private void expandCapacity() {
		setSize <<= 1;
		hash = new RandomHash(setSize, bits);
		this.setFilterSize(hash.getPrime());
	}

	@Override
	public void add(String s) {
		if (copyNeeded()) {
			expandSet();
		}

		int i = 0;
		s = s.toLowerCase();
		while (i < k) {
			long code = Math.abs(hash.hashCode(s));
			int block = (int) (code % this.filerSize());
			set.set(block);
			i++;
			s = "" + code;
		}
		numElms++;
	}

	@Override
	public boolean appears(String s) {
		s = s.toLowerCase();
		boolean appears = false;
		for (BitSet listSet : dynamicFilterList) {
			if (appearsInOne(s, listSet)) {
				appears = true;
				break;
			}
		}

		return appears;
	}

	private boolean appearsInOne(String s, BitSet thisSet) {
		int i = 0;
		boolean success = true;
		while (i < k && success) {
			long code = Math.abs(hash.hashCode(s));
			int block = (int) ((code) % thisSet.size());
			if (!thisSet.get(block)) {
				success = false;
			}

			i++;
			s = "" + code;
		}

		return success;
	}

}
