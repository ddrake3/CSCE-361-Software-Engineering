package edu.unl.cse.csce361.socket_chat;

public class CipherFactory {
	
	public static Cipher createCipher(String name, String[] keys) {
		if(name.equalsIgnoreCase("Caesar")){
			Cipher changedCipher = new CaesarCipher(keys);
			return changedCipher;
		}else{
			Cipher changedCipher = new AffineCipher(keys);
			return changedCipher;
		}
	}
	
	public static Cipher createCipher() {
		Cipher defaultCipher = new CaesarCipher();
		return defaultCipher;
	}

}
