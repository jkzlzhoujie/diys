package cn.temobi.core.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptTool {

	public CryptTool() {
	}

	public static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString().toUpperCase();
	}

	public static byte[] hexString2ByteArray(String strIn) throws Exception {
		byte arrB[] = strIn.getBytes();
		int iLen = arrB.length;
		byte arrOut[] = new byte[iLen / 2];
		for (int i = 0; i < iLen; i += 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}

		return arrOut;
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return (new StringBuilder(String.valueOf(hexDigits[d1]))).append(
				hexDigits[d2]).toString();
	}

	public static SecretKey genDESKey(byte key_byte[]) throws Exception {
		SecretKey k = null;
		k = new SecretKeySpec(key_byte, "DESede");
		return k;
	}

	public static SecretKey genDESKey() throws Exception {
		String keyStr = "$1#2@f3&4~6%7!a+*cd(e-h)";
		byte key_byte[] = keyStr.getBytes();
		SecretKey k = null;
		k = new SecretKeySpec(key_byte, "DESede");
		return k;
	}

	public static SecretKey genDESKey(String key) throws Exception {
		String keyStr = key;
		byte key_byte[] = keyStr.getBytes();
		SecretKey k = null;
		k = new SecretKeySpec(key_byte, "DESede");
		return k;
	}

	public static byte[] desDecrypt(SecretKey key, byte crypt[])
			throws Exception {
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(2, key);
		return cipher.doFinal(crypt);
	}

	public static String desDecrypt(SecretKey key, String crypt)
			throws Exception {
		return new String(desDecrypt(key, hexString2ByteArray(crypt)));
	}

	public static String desDecrypt(String key, String crypt) {
		String procKey = procKey(key);
		try {
			return desDecrypt(genDESKey(procKey), crypt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String procKey(String key) {
		if (key.length() < 24) {
			for (; key.length() < 24; key = (new StringBuilder(String
					.valueOf(key))).append("0").toString())
				;
			return key;
		}
		if (key.length() > 24)
			return key.substring(0, 24);
		else
			return key;
	}

	public static byte[] desEncrypt(SecretKey key, byte src[]) throws Exception {
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(1, key);
		return cipher.doFinal(src);
	}

	public static String desEncrypt(SecretKey key, String src) throws Exception {
		return byteArrayToHexString(desEncrypt(key, src.getBytes()));
	}

	public static String desEncrypt(String key, String src) {
		String procKey = procKey(key);
		try {
			return desEncrypt(genDESKey(procKey), src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] md5Digest(byte src[]) throws Exception {
		MessageDigest alg = MessageDigest.getInstance("MD5");
		return alg.digest(src);
	}

	public static String md5Digest(String src) throws Exception {
		return byteArrayToHexString(md5Digest(src.getBytes()));
	}

	public static String base64Encode(String src) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(src.getBytes());
	}

	public static String base64Encode(byte src[]) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(src);
	}

	public static String base64Decode(String src) {
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return byteArrayToHexString(decoder.decodeBuffer(src));
		} catch (Exception ex) {
			return null;
		}
	}

	public static byte[] base64DecodeToBytes(String src) {
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return decoder.decodeBuffer(src);
		} catch (Exception ex) {
			return null;
		}
	}

	public static String urlEncode(String src) {
		try {
			src = URLEncoder.encode(src, "UTF-8");
			return src;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return src;
	}

	public String urlDecode(String value) {
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return value;
	}

	public static void main(String args[]) {
		try {
			String desStr = "MERCHANTID=0219999999&ORDERSEQ=20080225150029000001&ORDERDATE=20080225&ORDERAMOUNT=200";
			String keyStr = "123456";
			desStr = (new StringBuilder(String.valueOf(desStr)))
					.append("&KEY=").append(keyStr).toString();
			System.out
					.println((new StringBuilder(
							"\u5F85\u52A0\u5BC6\u7684\u5B57\u7B26\u4E32 desStr \uFF1D\uFF1D "))
							.append(desStr).toString());
			byte src_byte[] = desStr.getBytes();
			byte md5Str[] = md5Digest(src_byte);
			String SING = byteArrayToHexString(md5Str);
			System.out.println((new StringBuilder("SING == ")).append(SING)
					.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

}

