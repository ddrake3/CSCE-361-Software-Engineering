package edu.unl.cse.csce361.socket_chat;

import java.util.Arrays;

public class CaesarCipher implements Cipher{

    private int shift;

    public CaesarCipher(){
        shift = 5;
    }
    
    public CaesarCipher(String[] key){
        shift = Integer.parseInt(key[0]);
    }
    
    @Override
    public String encipher(String plaintext) {
        // Strategy: iterate through the given string &
        // shift each character using its ascii value.
        // i.e.
        //      shift = 2
        //      A -> C
        //      b -> d
        // if the character isn't alphabetic, then just skip it
        // also, if the character + shift is greater than Z/z,
        // then the character rolls back to the start
        // i.e.
        //      shift = 5
        //      z -> e
        //      X -> C
        char[] plaintextArray = plaintext.toCharArray();

        for(int i = 0; i < plaintext.length(); i++){
            char current = plaintextArray[i];
            char shiftChar = (char) (current + this.shift);
            if(current >= 'A' && current <= 'Z'){
                if(shiftChar > 'Z'){
                    plaintextArray[i] = (char) ((shiftChar - 'Z') + 64);
                }
                else{
                    plaintextArray[i] = shiftChar;
                }
            }
            else if(current >= 'a' && current <= 'z'){
                if(shiftChar > 'z'){
                    int temp = 'z' - 'y';
                    plaintextArray[i] = (char) ((shiftChar - 'z') + 96);
                }
                else{
                    plaintextArray[i] = shiftChar;
                }
            }
        }
        return new String(plaintextArray);
    }

    @Override
    public String decipher(String ciphertext) {
        // Strategy: iterate through the given string &
        // shift each character back to its original value.
        // i.e.
        //      shift = 4
        //      E -> A
        //      n -> j
        // if the character isn't alphabetic, then just skip it
        // also, if the character + shift is less than A/a,
        // then the character rolls back to the end
        // i.e.
        //      shift = 5
        //      e -> z
        //      C -> X

        char[] plaintextArray = ciphertext.toCharArray();

        for(int i = 0; i < ciphertext.length(); i++){
            char current = plaintextArray[i];
            char shiftChar = (char) (current - this.shift);
            if(current >= 'A' && current <= 'Z'){
                if(shiftChar < 'A'){
                    int a = current - 'A';
                    int b = a - this.shift;
                    int c = ((b % 26 + 26) % 26);
                    int d = c + 'A';
                    plaintextArray[i] = (char) d;
                }
                else{
                    plaintextArray[i] = shiftChar;
                }
            }
            else if (current >= 'a' && current <= 'z'){
                if(shiftChar < 'a'){
                    int a = current - 'a';
                    int b = a - this.shift;
                    int c = ((b % 26 + 26) % 26);
                    int d = c + 'a';
                    plaintextArray[i] = (char) d;
                }
                else{
                    plaintextArray[i] = shiftChar;
                }
            }
        }
        return new String(plaintextArray);
    }
}
