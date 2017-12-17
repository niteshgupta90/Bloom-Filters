package main.java.com.cs525.bf.bloom_filter;

import main.java.com.cs525.bf.bloom_filter.hash.MurMurHash;

public class MurMurBloomFilter extends BasicBloomFilter {
	public MurMurBloomFilter(int setSize, int bitsPerElm) {
		super(setSize, bitsPerElm);
		hash = new MurMurHash();
	}
}
