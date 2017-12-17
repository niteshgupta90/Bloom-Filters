package main.java.com.cs525.bf.bloom_filter.hash;

public interface Hash {
	long hashCode(String s);
	int getPrime();
}
