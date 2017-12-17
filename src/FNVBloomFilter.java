package main.java.com.cs525.bf.bloom_filter;

import main.java.com.cs525.bf.bloom_filter.hash.FNVHash;

public class FNVBloomFilter extends BasicBloomFilter {

	public FNVBloomFilter(int setSize, int bitsPerElm) {
		super(setSize, bitsPerElm);
		hash = new FNVHash();
	}

}