package main.java.com.cs525.bf.bloom_filter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FalsePositives {
	private static int setSize = 500000;

	static List<String> readInputData() throws IOException {
		List<String> elms = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			elms.add(line);
		}
		reader.close();

		return elms;
	}

	static void dumpToFile(String aFileName, Set<String> list) throws IOException {
		Path path = Paths.get(aFileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			for (String line : list) {
				writer.write(line);
				writer.newLine();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		List<String> elms = readInputData();
		printsStats(4, elms);
		printsStats(8, elms);
		printsStats(16, elms);
	}

	private static long getAddTime(BloomFilter bF, String s) {
		long startTime = System.currentTimeMillis();
		bF.add(s);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		return totalTime;
	}

	private static long getSearchTime(BloomFilter bF, String s) {
		long startTime = System.currentTimeMillis();
		bF.appears(s);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		return totalTime;
	}

	private static void printsStats(int bits, List<String> elements) {
		System.out.println("************* Stats for " + bits + " bit run *************");
		BloomFilter fnvBloomFilter = new FNVBloomFilter(setSize, bits);
		BloomFilter murmurBloomFilter = new MurMurBloomFilter(setSize, bits);
		BloomFilter randomBloomFilter = new RandomBloomFilter(setSize, bits);
		BloomFilter dynamicBloomFilter = new DynamicBloomFilter(bits);

		Set<String> added = new HashSet<>();
		long addTimeFNV = 0;
		long addTimeMurmur = 0;
		long addTimeRandom = 0;
		long addTimeDynamic = 0;

		for (String s : elements) {
			boolean head = tossACoin();
			if (head) {
				added.add(s);
				addTimeFNV += getAddTime(fnvBloomFilter, s);
				addTimeMurmur += getAddTime(murmurBloomFilter, s);
				addTimeRandom += getAddTime(randomBloomFilter, s);
				addTimeDynamic += getAddTime(dynamicBloomFilter, s);

			}
		}

		System.out.println("Average time taken to add for FNVBF : " + ((double) addTimeFNV / added.size()));
		System.out.println("Average time taken to add for MURMURBF : " + ((double) addTimeMurmur / added.size()));
		System.out.println("Average time taken to add for RANDBF : " + ((double) addTimeRandom / added.size()));
		System.out.println("Average time taken to add for DYNBF : " + ((double) addTimeDynamic / added.size()));

		long searchTimeFNV = 0;
		long searchTimeMurmur = 0;
		long searchTimeRandom = 0;
		long searchTimeDynamic = 0;

		for (String s : elements) {
			searchTimeFNV += getSearchTime(fnvBloomFilter, s);
			searchTimeMurmur += getSearchTime(murmurBloomFilter, s);
			searchTimeRandom += getSearchTime(randomBloomFilter, s);
			searchTimeDynamic += getSearchTime(dynamicBloomFilter, s);

		}

		System.out.println();
		System.out.println("Average time taken to search for FNVBF : " + ((double) searchTimeFNV / added.size()));
		System.out.println("Average time taken to search for MURMURBF : " + ((double) searchTimeMurmur / added.size()));
		System.out.println("Average time taken to search for RANDBF : " + ((double) searchTimeRandom / added.size()));
		System.out.println("Average time taken to search for DYNBF : " + ((double) searchTimeDynamic / added.size()));

		long fnv = 0, murmur = 0, random = 0, dynamic = 0;
		for (String s : elements) {
			if (!added.contains(s)) {
				if (fnvBloomFilter.appears(s)) {
					fnv++;
				}

				if (murmurBloomFilter.appears(s)) {
					murmur++;
				}

				if (randomBloomFilter.appears(s)) {
					random++;
				}

				if (dynamicBloomFilter.appears(s)) {
					dynamic++;
				}
			}
		}

		System.out.println();
		System.out.println("False positive rate for FNVBF : " + ((double) fnv / setSize));
		System.out.println("False positive rate for MURMURBF : " + ((double) murmur / setSize));
		System.out.println("False positive rate for RANDBF : " + ((double) random / setSize));
		System.out.println("False positive rate for DYNBF : " + ((double) dynamic / setSize));
	}

	private static boolean tossACoin() {
		Random rndm = new Random();
		return rndm.nextBoolean();
	}

	@Deprecated
	private static Set<String> getRandomStrings(int n) {
		Set<String> set = new HashSet<>();
		Random rndm = new Random();
		for (int i = 0; i < n; i++) {
			set.add(getRandomString(10 + rndm.nextInt() % 5));
		}
		return set;
	}

	private static String getRandomString(int size) {
		Random rndm = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			char ch = (char) ('a' + (int) rndm.nextInt() % 26);
			sb.append(ch);
		}

		return sb.toString();
	}
}
