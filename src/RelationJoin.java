package main.java.com.cs525.bf.bloom_filter;

import java.io.IOException;

import main.java.com.cs525.bf.bloom_filter.join.BloomJoin;

public class RelationJoin {
	public static void main(String[] args) {
		try {
			BloomJoin bloomJoin = new BloomJoin("Relation1.txt", "Relation2.txt");
			bloomJoin.join("Result.txt");
			System.out.println("Relation Join Operation is Successful and the result is available at : Result.txt");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOError while joining : " + e);
		}
	}
}
