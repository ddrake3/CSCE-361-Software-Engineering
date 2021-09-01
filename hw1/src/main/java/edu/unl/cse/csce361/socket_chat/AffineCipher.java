package edu.unl.cse.csce361.socket_chat;

public class AffineCipher implements Cipher{

    private int a;
    private int b;

    public AffineCipher(){
        a = 5;
        b = 7;
    }
    
    public AffineCipher(String[] keys){
        a = Integer.parseInt(keys[0]);
        b = Integer.parseInt(keys[1]);
    }
    
    @Override
    public String encipher(String plaintext) {
        // Strategy: Use modular arithmetic to transform each character.
        // (ax+b) mod m, where
        //      a is the first key
        //      b is the second key
        //      x is the current character's value
        //      mod m is the size of the alphabet
        //  i.e.
        //      H -> Q
        //      z -> c

        char[] plaintextArray = plaintext.toCharArray();

        for(int i = 0; i < plaintext.length(); i++){
            char current = plaintextArray[i];
            if(current >= 'A' && current <= 'Z'){
                int a1 = this.a * (current - 'A');
                int a2 = a1 + this.b;
                int a3 = a2 % 26;
                int a4 = a3 + 'A';
                plaintextArray[i] = (char) a4;
            }
            else if(current >= 'a' && current <= 'z'){
                int a1 = this.a * (current - 'a');
                int a2 = a1 + this.b;
                int a3 = a2 % 26;
                int a4 = a3 + 'a';
                plaintextArray[i] = (char) a4;
            }
        }
        return new String (plaintextArray);
    }

    @Override
    public String decipher(String ciphertext) {
        // Strategy: Use modular arithmetic to transform each character.
        // a^-1(x-b) mod m
        //      a^-1 is the multiplicative inverse of a
        //      b is the second key
        //      x is the current character's value
        //      mod m is the size of the alphabet
        //  i.e.
        //      Q -> H
        //      c -> z

        char[] ciphertextArray = ciphertext.toCharArray();
        int isInverse = 0;
        int aInverse = 0;

        // find the multiplicative inverse
        // let's say we have some number a = 5
        // the multiplicative inverse = 1/5
        // so we can see that the product of these
        // two numbers is 1
        // in other words we are looking for a number
        // such that (a * i) % 26 = 1
        for(int i = 0; i < 26; i++){
            isInverse = (a * i) % 26;

            if(isInverse == 1){
                aInverse = i;
            }
        }
        for(int i = 0; i < ciphertext.length(); i++){
            char current = ciphertextArray[i];

            if(current >= 'A' && current <= 'Z'){
                int a1 = current + 'A';
                int a2 = a1 - this.b;
                int a3 = a2 * aInverse;
                int a4 = a3 % 26;
                int a5 = a4 + 'A';
                ciphertextArray[i] = (char) a5;

            }
            else if(current >= 'a' && current <= 'z'){
                int a1 = current + 'a';
                int a2 = a1 - this.b;
                int a3 = aInverse * a2;
                // Without the following statement, I found that I wasn't getting the correct
                // decryption.  After doing some research online, I found that I was running into
                // a special case when converting from ASCII to integer, and I was losing some of
                // the bit representation.  I'm sure if I used Big Integer or some other data type,
                // this wouldn't be necessary.  Anyways, the following statement seems to fix my problem
                // so we'll just go with it.
                int a4 = a3 - ('a' - 1);
                int a5 = a4 % 26;
                int a6 = a5 + 'a';
                ciphertextArray[i] = (char) a6;
            }
        }
        return new String(ciphertextArray);
    }
}
