package main.java.com.cs525.bf.bloom_filter.join;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.com.cs525.bf.bloom_filter.BloomFilter;
import main.java.com.cs525.bf.bloom_filter.MurMurBloomFilter;

public class BloomJoin {
	List<String[]> r1, r2;

	public BloomJoin(String p1, String p2) throws IOException {
		r1 = readRelation(p1);
		r2 = readRelation(p2);
	}

	public void join(String p3) {
		BloomFilter fnvBloomFilter = new MurMurBloomFilter(r1.size(), 1);
		for (String[] row : r1) {
			fnvBloomFilter.add(row[0]);
		}

		List<String[]> squashedTable = new ArrayList<String[]>();
		for (String[] row : r2) {
			if (fnvBloomFilter.appears(row[0])) {
				squashedTable.add(row);
			}
		}

		Map<String, Set<String>> m = new HashMap<>();
		for (String[] row : squashedTable) {
			String key = row[0].toLowerCase();
			if (!m.containsKey(key)) {
				m.put(key, new HashSet<String>());
			}
			m.get(key).add(row[1]);
		}

		List<String> result = new ArrayList<>();
		for (String[] row : r1) {
			String key = row[0].toLowerCase();
			if (m.containsKey(key)) {
				for (String s : m.get(key)) {
					StringBuilder sb = new StringBuilder(row[1]);
					sb.append(" " + row[0]);
					sb.append(" " + s);
					result.add(sb.toString());
				}
			}
		}

		try {
			dumpToFile(p3, result);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to write output to file : " + p3);
		}
	}

	void dumpToFile(String aFileName, List<String> list) throws IOException {
		Path path = Paths.get(aFileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			for (String line : list) {
				writer.write(line);
				writer.newLine();
			}
		}
	}

	public List<String[]> readRelation(String file) throws IOException {
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(file));
		ArrayList<String[]> list = new ArrayList<String[]>();
		String line = "";
		while ((line = reader.readLine()) != null) {
			String[] token = line.split("\\s+");
			list.add(token);
		}
		return list;
	}
}
