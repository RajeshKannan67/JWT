//
//package com.learning.jwt;
//
//import java.security.SecureRandom;
//
//public class JWT {
//	public static void main(String[] args) {
//		
//		SecureRandom secureRandom = new SecureRandom();
//		byte[] key = new byte[32]; // 256-bit key
//		
//		secureRandom.nextBytes(key);
//		
//		StringBuilder hexString = new StringBuilder();
//		for (byte b : key) {
//			hexString.append(String.format("%02x", b));
//		}
//		
//		String secretKey = hexString.toString();
//		System.out.println(secretKey);
//	}
//}
