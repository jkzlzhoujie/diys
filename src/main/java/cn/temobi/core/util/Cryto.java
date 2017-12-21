package cn.temobi.core.util;

import java.io.UnsupportedEncodingException;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Cryto
{

	private static Logger logger;
	private static String algorithm = "DESede";
	private static String default_charset = "UTF-8";
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	public Cryto()
	{
	}

	public static String generateKey()
	{
		byte abyte0[] = generateKeyByteArr();
		return byteArrayToHexString(abyte0).toUpperCase();
	}

	public static String byteArrayToHexString(byte buf[])
	{
		StringBuffer sb;
		int iLen = buf.length;
		sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++)
		{
			int intTmp;
			for (intTmp = buf[i]; intTmp < 0; intTmp += 256);
			if (intTmp < 16)
				sb.append("0");
			sb.append(Integer.toString(intTmp, 16));
		}

		return sb.toString();
	}

	private static byte[] generateKeyByteArr()
	{	
		try {
			SecureRandom securerandom = new SecureRandom();
			SecretKey secretkey;
			KeyGenerator keygenerator = KeyGenerator.getInstance(algorithm);
			keygenerator.init(securerandom);
			secretkey = keygenerator.generateKey();
			return secretkey.getEncoded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public static String generateAuthenticator(String source, String key)
	{
		return generateAuthenticator(source, key, default_charset);
	}

	public static String generateAuthenticator(String source, String key, String charset)
	{
		String s2 = null;
		try
		{
			MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");
			messagedigest.update(source.getBytes(charset));
			byte abyte0[] = messagedigest.digest();
			byte abyte1[] = Hex.decode(key);
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS7Padding", "BC");
			cipher.init(1, new SecretKeySpec(abyte1, algorithm), new IvParameterSpec(Hex.decode("0102030405060708")));
			byte abyte2[] = cipher.doFinal(abyte0);
			s2 = base64Encode(abyte2);
		}
		catch (Exception exception)
		{
			logger.error("生成Authenticator认证信息时出错!@" + exception.getMessage());
		}
		return s2;
	}
	
	public static byte[] SHA1(String source, String charset)
	{
		byte abyte0[];
		try {
			MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");
			messagedigest.update(source.getBytes(charset));
			abyte0 = messagedigest.digest();
			return abyte0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static boolean validateAuthenticator(String s, String s1, String s2)
	{
		String s3 = generateAuthenticator(s1, s);
		return s2.equals(s3);
	}

	public static String encryptBase643DES(String source, String key)
	{
		return encryptBase643DES(source, key, default_charset);
	}

	public static String encryptBase643DES(String source, String key, String charset)
	{
		byte abyte1[];
		byte abyte0[] = Hex.decode(key);
		Security.addProvider(new BouncyCastleProvider());
		try {
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS7Padding", "BC");
			cipher.init(1, new SecretKeySpec(abyte0, algorithm), new IvParameterSpec(Hex.decode("0102030405060708")));
			abyte1 = cipher.doFinal(source.getBytes(charset));
			return base64Encode(abyte1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decryptBase643DES(String source, String key)
	{
		return decryptBase643DES(source, key, default_charset);
	}

	public static String decryptBase643DES(String source, String key, String charset)
	{
		Cipher cipher;
		byte base64ed[];
		byte abyte0[] = Hex.decode(key);
		Security.addProvider(new BouncyCastleProvider());
		
		try {
			cipher = Cipher.getInstance("DESede/CBC/PKCS7Padding", "BC");
			cipher.init(2, new SecretKeySpec(abyte0, algorithm), new IvParameterSpec(Hex.decode("0102030405060708")));
			base64ed = base64Decode(source);
			if (base64ed == null)
				return null;
			byte abyte1[] = cipher.doFinal(base64ed);
			return new String(abyte1, charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] base64Decode(String str)
	{	
		try {
			byte a[] = (new BASE64Decoder()).decodeBuffer(str);
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static String base64Encode(byte b[])
	{
		return (new BASE64Encoder()).encode(b);
	}

	public static String binToAscii(byte bySourceByte[])
	{
		String result = new String();
		int len = bySourceByte.length;
		for (int i = 0; i < len; i++)
		{
			byte tb = bySourceByte[i];
			char tmp = (char)(tb >>> 4 & 0xf);
			char high;
			if (tmp >= '\n')
				high = (char)((97 + tmp) - 10);
			else
				high = (char)(48 + tmp);
			result = result + high;
			tmp = (char)(tb & 0xf);
			char low;
			if (tmp >= '\n')
				low = (char)((97 + tmp) - 10);
			else
				low = (char)(48 + tmp);
			result = result + low;
		}

		return result;
	}

	public static String cryptMD5ToHEX(String str)
	{
		return cryptMD5ToHEX(str, default_charset);
	}

	public static String cryptMD5ToHEX(String str, String charset)
	{	
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return hex(md.digest(str.getBytes(charset)));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	public static String hex(byte array[])
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++)
			sb.append(Integer.toHexString(array[i] & 0xff | 0x100).substring(1, 3));

		return sb.toString();
	}
	
	/**
	 * HMAC-SHA1加密
	 */
	public static String calculateRFC2104HMAC(String data, String key)
	throws java.security.SignatureException
	{
	String result;
	try {

	// get an hmac_sha1 key from the raw key bytes
	SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

	// get an hmac_sha1 Mac instance and initialize with the signing key
	Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
	mac.init(signingKey);

	// compute the hmac on input data bytes
	byte[] rawHmac = mac.doFinal(data.getBytes(default_charset));

	// base64-encode the hmac
	result = base64Encode(rawHmac);

	} catch (Exception e) {
	throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
	}
	return result;
	}
	
	
	public static void main(String args[])
		throws UnsupportedEncodingException
	{
		System.out.println(new String(base64Decode("cXFxcXFxMTExMjIz")));
	}

	static 
	{
		logger = Logger.getLogger(Cryto.class);
	}
}
