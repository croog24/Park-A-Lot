package com.github.parkalot;

public class Temp {
	public static void main(String[] args) {
		System.out.println(pairStar("aaaa"));
	}

	public static String pairStar(String str) {
		if (str == null || str.length() <= 1)
			return str;
		else if (str.charAt(0) == str.charAt(1))
			return str.charAt(0) + "*" + str.charAt(1) + pairStar(str.substring(1));
		return str.charAt(0) + pairStar(str.substring(1));
	}

}
