package main.java.com.cs525.bf.bloom_filter;

import java.util.BitSet;

import main.java.com.cs525.bf.bloom_filter.hash.Hash;

public class BasicBloomFilter implements BloomFilter {
	protected int bits;
	protected int setSize;
	protected int k;
	private int filterSize;
	protected Hash hash;
	protected BitSet set;
	protected long numElms = 0;

	public BasicBloomFilter(int setSize, int bitsPerElm) {
		this.bits = bitsPerElm;
		this.setSize = setSize;
		this.filterSize = bits * setSize;
		this.k = (int) (Math.log(2 * (filterSize / setSize)));
		this.k = (this.k == 0) ? 1 : this.k;
		set = new BitSet(filterSize);
	}

	public void add(String s) {
		if (copyNeeded()) {
			expandSet();
		}

		int i = 0;
		s = s.toLowerCase();
		while (i < k) {
			long code = Math.abs(hash.hashCode(s));
			int block = (int) (code % filterSize);
			set.set(block);
			i++;
			s = "" + code;
		}
		numElms++;
	}

	public boolean appears(String s) {
		int i = 0;
		s = s.toLowerCase();
		boolean appears = true;
		while (i < k) {
			long code = Math.abs(hash.hashCode(s));
			int block = (int) ((code) % filterSize);
			if (!set.get(block)) {
				appears = false;
			}
			i++;
			s = "" + code;
		}

		return appears;
	}

	public int filerSize() {
		return this.filterSize;
	}

	public void setFilterSize(int size) {
		this.filterSize = size;
	}

	public long dataSize() {
		return this.numElms;
	}

	public int numHashes() {
		return this.k;
	}

	protected boolean copyNeeded() {
		return false;
	}

	protected void expandSet() {
		// DO NOTHING
	}
}
