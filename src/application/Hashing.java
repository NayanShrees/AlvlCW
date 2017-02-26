package application;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import database.Logindb;


public final class Hashing {
	//public boolean to see if the program wants to verify or create a hash
	public static boolean vHash;
	//method to get a hash, when the method is called requires a password and salt
	public static String generateHash(String passWord, byte[] salt)throws Exception{
		//encoder to convert base64 values into string
		Base64.Encoder enc = Base64.getEncoder();
		//checks if the user wants to verify or create a hash
		if(vHash == false){
			//if the program wants to get create a new hash this part of the code is ran
			SecureRandom.getInstance("SHA1PRNG");
			//the salt array is filled with random characters in byte64
			salt = SecureRandom.getSeed(99);
			//the salt is encoded into string
			Logindb.sSalt = enc.encodeToString(salt);
		}else{
			//if it needs to verify a hash than the salt that is entered when the method is called
			//will be decoded from string to base64
			salt = Base64.getDecoder().decode(salt);
		}
		//creates a new key with the properties of the password(User entered value) into characters, the salt is already bytes
		//and it iterates 1000000 times, with the keylenth 1111
		KeySpec spec = new PBEKeySpec(passWord.toCharArray(), salt, 1000000, 1111);
		//Creates a secretkey which is used to hash using the hashing algorithm listed as PBKDF2WithHmacSHA512
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		//generates the hash
		byte[] hash = f.generateSecret(spec).getEncoded();
		//encodes the hash into string from base64
		passWord = enc.encodeToString(hash);
		//returns the output string
		return passWord;
	}
}