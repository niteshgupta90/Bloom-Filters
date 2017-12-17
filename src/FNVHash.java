package main.java.com.cs525.bf.bloom_filter.hash;

import java.math.BigInteger;

public class FNVHash implements Hash {
	public static final BigInteger FNV64PRIME = new BigInteger("109951168211");
	public static final BigInteger FNV_64INIT = new BigInteger("14695981039346656037");
	private static final BigInteger MOD64 = new BigInteger("2").pow(64);

	public long hashCode(String s) {
		byte[] data = s.getBytes();
		BigInteger hash = FNV_64INIT;

		for (byte b : data) {
			hash = hash.multiply(FNV64PRIME).mod(MOD64);
			hash = hash.xor(BigInteger.valueOf((int) b & 0xff));
		}

		String h = hash.toString();
		h = (h.length() > 18) ? h.substring(0, 18) : h;
		return Long.parseLong(h);
	}

	@Override
	public int getPrime() {
		// TODO Auto-generated method stub
		return 0;
	}

}
