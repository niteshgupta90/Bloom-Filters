package main.java.com.cs525.bf.bloom_filter.hash;

import java.util.Random;

public class RandomHash implements Hash {
	private int prime;

	public RandomHash(int setSize, int bits) {
		prime = getPrime(setSize * bits + 1);
	}

	boolean isPrime(int N) {
		boolean prime = true;
		for (int i = (int) Math.sqrt(N); i > 1 && prime; i--) {
			if (N % i == 0) {
				prime = false;
				break;
			}
		}
		return prime;
	}

	public int getPrime() {
		return prime;
	}

	int getPrime(int N) {
		while (!isPrime(N))
			N++;

		return N;
	}

	public long hashCode(String s) {
		int x = s.hashCode(), p = prime;

		Random rand = new Random();
		int a = rand.nextInt((int) (p - 1)) + 0;
		int b = rand.nextInt((int) (p - 1)) + 0;
		return (a * x + b) % p;
	}

}
