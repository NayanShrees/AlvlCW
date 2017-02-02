package application;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import database.Logindb;


public final class Hashing {
	public static boolean vHash;

	public static String generateHash(String passWord, byte[] salt)throws Exception{
		Base64.Encoder enc = Base64.getEncoder();
		if(vHash == false){
			salt = SecureRandom.getInstance("SHA1PRNG").getSeed(99);
			Logindb.sSalt = enc.encodeToString(salt);
		}else{
			salt = Base64.getDecoder().decode(salt);
		}
		
		KeySpec spec = new PBEKeySpec(passWord.toCharArray(), salt, 1000000, 1111);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		byte[] hash = f.generateSecret(spec).getEncoded();
		passWord = enc.encodeToString(hash);
		return passWord;
	}
}