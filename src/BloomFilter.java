package main.java.com.cs525.bf.bloom_filter;

public interface BloomFilter {
	void add(String s);

	boolean appears(String s);

	int filerSize();

	long dataSize();

	int numHashes();

	void setFilterSize(int size);
}
