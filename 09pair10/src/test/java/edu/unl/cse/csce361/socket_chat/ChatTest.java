package edu.unl.cse.csce361.socket_chat;

import javafx.scene.transform.Affine;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.SocketException;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

public class ChatTest {
    static class MockChat extends Chat {
        boolean exitCalled = false;
        int actualExitCode = 0;

        @Override
        protected void exit(int exitCode) {
            exitCalled = true;
            actualExitCode = exitCode;
        }
    }

    private MockChat chatter;
    private String newLine;

    private BufferedReader remoteInput;
    private BufferedReader localInput;

    private PrintStream remoteOutput;
    private PrintStream localOutput;

    private ByteArrayOutputStream remoteOutputSpy;
    private ByteArrayOutputStream localOutputSpy;

    private ResourceBundle bundle;

    private CaesarCipher cipher;
    @Before
    public void setUp() {
        chatter = new MockChat();
        newLine = System.getProperty("line.separator");
        bundle = ResourceBundle.getBundle("socketchat");
        localOutputSpy = new ByteArrayOutputStream();
        localOutput = new PrintStream(localOutputSpy);
        remoteOutputSpy = new ByteArrayOutputStream();
        remoteOutput = new PrintStream(remoteOutputSpy);
        cipher = new CaesarCipher();
    }

    @Test
    public void testCommunicateOneMessageCatchesIOException() {
        // arrange
        Reader stubReader = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                throw new IOException("IOException from stubReader");
            }

            @Override
            public void close() throws IOException {
            }
        };
        remoteInput = new BufferedReader(stubReader);
        int expectedExitCode = 1;
        String expectedOutput = "Connection dropped: java.io.IOException: IOException from stubReader" + newLine;
        // act
        chatter.communicateOneMessage(null, remoteInput, localOutput, null, false);
        // assert
        assertTrue(chatter.exitCalled);
        assertEquals(expectedExitCode, chatter.actualExitCode);
        assertEquals(expectedOutput, localOutputSpy.toString());
    }

    // added the following tests to attain statement coverage for communicateOneMessage()
    // Drake - 6/20/2020

    @Test
    public void testCommunicateOneMessageSocketException(){
        // arrange
        Reader stubReader = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws SocketException {
                throw new SocketException("SocketException from stubReader");
            }

            @Override
            public void close() throws SocketException {
            }
        };
        remoteInput = new BufferedReader(stubReader);
        String expectedOutput = bundle.getString("communicate.error.cannotSendMessage") + newLine;
        // act
        chatter.communicateOneMessage(null, remoteInput, localOutput, null, false);
        // assert
        assertEquals(expectedOutput, localOutputSpy.toString());
    }

    @Test
    public void testCommunicateOneMessageIfLocalMessage(){
        //arrange
        CaesarCipher cipher = new CaesarCipher();
        String localMessage = "Hello, this is a message from local";
        Reader stubReader = new StringReader(localMessage);
        BufferedReader localInput = new BufferedReader(stubReader);
        String expectedOutput = "Hello, this is a message from local" + newLine;
        expectedOutput = cipher.encipher(expectedOutput);
        //act
        chatter.communicateOneMessage(localInput, null, null, remoteOutput, true);
        //assert
        assertEquals(expectedOutput, remoteOutputSpy.toString());
    }

    @Test
    public void testCommunicateOneMessageIfNonNullRemoteMessage(){
        //arrange
        String remoteMessage = "Hello, this is a message from remote";
        remoteMessage = cipher.encipher(remoteMessage);
        Reader stubReader = new StringReader(remoteMessage);
        BufferedReader remoteInput = new BufferedReader(stubReader);
        String expectedOutput = "Hello, this is a message from remote" + newLine;
        //act
        chatter.communicateOneMessage(null, remoteInput, localOutput, null, false);
        //assert
        assertEquals(expectedOutput, localOutputSpy.toString());
    }

    @Test
    public void testCommunicateOneMessageIfNullRemoteMessage(){
        //arrange
        String remoteMessage = "";
        Reader stubReader = new StringReader(remoteMessage);
        BufferedReader remoteInput = new BufferedReader(stubReader);
        String expectedOutput = bundle.getString("communicate.error.nullMessageFromRemote");
        expectedOutput = expectedOutput + newLine;
        //expectedOutput = cipher.encipher(expectedOutput);
        //act
        chatter.communicateOneMessage(null, remoteInput, localOutput, null, false);
        //assert
        assertEquals(expectedOutput, localOutputSpy.toString());
    }

    @Test
    public void testCommunicateOneMessageInputWithMultipleLines(){
        //arrange
        String remoteMessage = "CHANGE LANGUAGE";
        Reader stubReader = new StringReader(remoteMessage);
        BufferedReader remoteInput = new BufferedReader(stubReader);
        String expectedOutput = "";
        //act
        chatter.communicateOneMessage(null, remoteInput, localOutput, null, false);
    }

    // added the following tests to attain functionality coverage
    // for encipher/decipher in CaesarCipher
    // Drake - 6/21/2020
    @Test
    public void testCaesarCipherEncipherSimple(){
        //arrange
        String before = "Hello, my name is Master Chief ~~~ Sierra 117.";
        String expectedResult = "Mjqqt, rd sfrj nx Rfxyjw Hmnjk ~~~ Xnjwwf 117.";
        //act
        String actualResult = cipher.encipher(before);
        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public  void  testCaesarCipherEncipherExtreme(){
        //arrange
        String before = "ZIGGITY ZEBRAS AND VERY COOL and awesome and amazing 312.555.1234 06/22/2020";
        String expectedResult = "ENLLNYD EJGWFX FSI AJWD HTTQ fsi fbjxtrj fsi frfensl 312.555.1234 06/22/2020";
        //act
        String actualResult = cipher.encipher(before);
        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCaesarCipherDecipherSimple(){
        //arrange
        String before = "MJQQT EJI!, N'r xqjjudD eeEE.";
        String expectedResult ="HELLO ZED!, I'm sleepyY zzZZ.";
        //act
        String actualResult = cipher.decipher(before);
        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCaesarCipherDecipherExtreme(){
        //arrange
        String before = "Nk mj mfi fsdymnsl htsknijsynfq yt xfd, mj bwtyj ny ns hnumjw, ymfy nx, gd xt hmfslnsl ymj twijw tk ymj qjyyjwx tk ymj fqumfgjy, ymfy sty f btwi htzqi gj rfij tzy.";
        String expectedResult = "If he had anything confidential to say, he wrote it in cipher, that is, by so changing the order of the letters of the alphabet, that not a word could be made out.";
        //act
        String actualResult = cipher.decipher(before);
        //assert
        assertEquals(expectedResult, actualResult);
    }

    // added the following tests to attain functionality coverage
    // for encipher/decipher in AffineCipher
    // Drake - 6/22/2020
    @Test
    public void testAffineCipherEncipherSimple(){
        //arrange
        AffineCipher ac = new AffineCipher();
        String before = "Hello, my name is Master Chief ~~~ Sierra 117.";
        String expectedResult = "Qbkkz, px uhpb vt Phtybo Rqvbg ~~~ Tvbooh 117.";
        //act
        String actualResult = ac.encipher(before);
        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAffineCipherEncipherExtreme(){
        //arrange
        AffineCipher ac = new AffineCipher();
        String before = "ZIGGITY ZEBRAS AND VERY COOL and awesome and amazing 312.555.1234 06/22/2020";
        String expectedResult = "CVLLVYX CBMOHT HUW IBOX RZZK huw hnbtzpb huw hphcvul 312.555.1234 06/22/2020";
        //act
        String actualResult = ac.encipher(before);
        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAffineCipherDecipherSimple(){
        //arrange
        AffineCipher ac = new AffineCipher();
        String before = "Qbkkz, px uhpb vt Phtybo Rqvbg ~~~ Tvbooh 117.";
        String expectedResult = "Hello, my name is Master Chief ~~~ Sierra 117.";
        //act
        String actualResult = ac.decipher(before);
        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAffineCipherDecipherExtreme(){
        //arrange
        AffineCipher ac = new AffineCipher();
        String before = "Vg qb qhw huxyqvul rzugvwbuyvhk yz thx, qb nozyb vy vu rveqbo, yqhy vt, mx tz rqhulvul yqb zowbo " +
                "zg yqb kbyybot zg yqb hkeqhmby, yqhy uzy h nzow rzdkw mb phwb zdy.";
        String expectedResult = "If he had anything confidential to say, he wrote it in cipher, that is, by so changing the order " +
                "of the letters of the alphabet, that not a word could be made out.";
        //act
        String actualResult = ac.decipher(before);
        //assert
        assertEquals(expectedResult, actualResult);
    }
}
