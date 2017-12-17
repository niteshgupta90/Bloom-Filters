package main.java.com.cs525.bf.bloom_filter;

import java.util.BitSet;

import main.java.com.cs525.bf.bloom_filter.hash.RandomHash;

public class RandomBloomFilter extends BasicBloomFilter {

	public RandomBloomFilter(int setSize, int bitsPerElm) {

		super(setSize, bitsPerElm);
		hash = new RandomHash(setSize, bitsPerElm);
		this.setFilterSize(hash.getPrime());
		set = new BitSet(filerSize());
	}

}
