package edu.unl.cse.csce361.socket_chat;

import java.io.*;
import java.net.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class Chat {

    private static final int MAXIMUM_CONNECTION_ATTEMPTS = 10;
    private Socket socket;
    private boolean isHost;
    private ResourceBundle bundle;
    private Set<String> keywords;
    private Cipher cipherStrategy;

    public Chat() {
        setLocale(Locale.getDefault());
        socket = null;
        cipherStrategy = CipherFactory.createCipher(); //implements default cipher (CaesarCipher)
    }

    /**
     * Provides a test hook to test that the program terminates.
     *
     * @param exitCode the code passed to the shell indicating (ab)normal termination
     */
    protected void exit(int exitCode) {
        System.exit(exitCode);
    }

    /**
     * Overrides the system's default Locale.
     *
     * @param locale the {@link java.util.Locale} for this application to use
     */
    private void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("socketchat", locale);
        Set<String> culledKeySet = bundle.keySet().stream()
                .filter(entry -> entry.startsWith("communicate.keyword."))
                .collect(Collectors.toSet());
        keywords = new HashSet<>(culledKeySet.size());
        culledKeySet.forEach(key -> keywords.add(bundle.getString(key)));
    }

    /*
     *  THESE METHODS SET UP AND TEAR DOWN CONNECTION
     */

    /**
     * Establishes the connection between the host and the client.
     *
     * @param userInput a {@link java.util.Scanner} to read user responses
     * @return the {@link java.net.Socket} for the connection
     */
    @SuppressWarnings("UnusedReturnValue")
    public Socket connect(Scanner userInput) {
        String yes = bundle.getString("connection.response.yes");
        String no = bundle.getString("connection.response.no");
        // "Are you the chat host? [Y] "
        System.out.print(bundle.getString("connection.prompt.isHost") + " [" + yes + "] ");
        String answerString = userInput.nextLine().toUpperCase();
        String answer = answerString.length() > 0 ? answerString.substring(0, no.length()) : yes;
        isHost = (!answer.equals(no));
        Socket socket = null;
        try {
            socket = isHost ? connectAsServer(userInput) : connectAsClient(userInput);
        } catch (IOException ioException) {
            // "Connection failed: ..."
            System.err.println(bundle.getString("connection.error.generalConnectionFailure") + ": " + ioException);
            exit(1);
        }
        this.socket = socket;
        return socket;
    }

    /**
     * Closes the socket connecting the host and the client.
     */
    @SuppressWarnings("WeakerAccess")
    public void disconnect() {
        try {
            socket.close();
        } catch (IOException ioException) {
            // "Error while closing socket: ..."
            System.err.println(bundle.getString("connection.error.closingError") + ": " + ioException);
            // We're terminating anyway, note the error and continue normal termination
        }
    }

    /**
     * Establishes the host end of the connection between the host and the client
     *
     * @param userInput a {@link java.util.Scanner} to read user responses
     * @return the {@link java.net.Socket} for the connection
     * @throws IOException if the local hostname cannot be resolved into an address or if an I/O error occurs while
     *                     creating a socket or waiting for a connection
     */
    private Socket connectAsServer(Scanner userInput) throws IOException {
        byte[] address = InetAddress.getLocalHost().getAddress();
        short[] humanReadableAddress = signedToUnsignedAddress(address);
        // "Host address: ..."
        System.out.println(MessageFormat.format(bundle.getString("connection.info.hostAddress.0.1.2.3"),
                humanReadableAddress[0], humanReadableAddress[1], humanReadableAddress[2], humanReadableAddress[3]));
        int port;
        ServerSocket serverSocket;
        do {
            // "Select port number"
            port = getPort(bundle.getString("connection.prompt.selectPortNumber"), userInput);
            try {
                serverSocket = new ServerSocket(port);
            } catch (BindException ignored) {
                // "Port ... is already in use."
                System.out.println(MessageFormat.format(bundle.getString("connection.error.portInUse.0"), port));
                serverSocket = null;
            }
        } while (serverSocket == null);
        System.out.println(bundle.getString("connection.info.waiting"));
        return serverSocket.accept();
    }

    /**
     * Establishes the host end of the connection between the host and the client
     *
     * @param userInput a {@link java.util.Scanner} to read user responses
     * @return the {@link java.net.Socket} for the connection
     * @throws IOException if an I/O error occurs while creating a socket or waiting for a connection
     */
    private Socket connectAsClient(Scanner userInput) throws IOException {
        byte[] address = getRemoteHostAddress(userInput);
        short[] humanReadableAddress = signedToUnsignedAddress(address);
        // "Enter port host is opening at ..."
        String prompt = MessageFormat.format(bundle.getString("connection.prompt.getHostPort.0.1.2.3"),
                humanReadableAddress[0], humanReadableAddress[1], humanReadableAddress[2], humanReadableAddress[3]);
        int port = getPort(prompt, userInput);
        Socket socket = null;
        int attemptCount = 0;
        do {
            try {
                sleep(1000 * attemptCount++);
            } catch (InterruptedException ignored) {
            }
            try {
                socket = new Socket(InetAddress.getByAddress(address), port);
            } catch (ConnectException ignored) {
                // "Attempt ...: Chat server is not yet ready at ..."
                System.out.println(MessageFormat.format(
                        bundle.getString("connection.error.hostNotReady.0.1.2.3.4.5"),
                        attemptCount, address[0], address[1], address[2], address[3], port));
                if (attemptCount < MAXIMUM_CONNECTION_ATTEMPTS) {
                    // "Will attempt to connect again in ... seconds."
                    System.out.println(MessageFormat.format(bundle.getString("connection.info.reAttempt"),
                            attemptCount));
                    socket = null;
                } else {
                    // "Exceeded maximum number of connection attempts. Terminating."
                    System.err.println(bundle.getString("connection.error.tooManyAttempts"));
                    exit(1);
                }
            }
        } while (socket == null);
        return socket;
    }

    /**
     * Obtains the host's IP address from user input
     *
     * @param userInput a {@link java.util.Scanner} to read user responses
     * @return the host's IP address
     */
    private byte[] getRemoteHostAddress(Scanner userInput) {
        // This assumes IPv4. Probably a good assumption.
        boolean haveGoodAddress = false;
        short[] address = new short[4];
        while (!haveGoodAddress) {
            // "Enter IP address of host <##.##.##.##>:"
            System.out.print(bundle.getString("connection.prompt.getHostAddress") + " ");
            try {
                String addressString = userInput.nextLine();
                String[] tokens = addressString.split("\\.");
                if (tokens.length == 4) {
                    for (int i = 0; i < 4; i++) {
                        address[i] = Short.parseShort(tokens[i]);
                        // mimics range limits of Byte, but for unsigned values
                        if (address[i] < 0 || address[i] > 255) {
                            // "Value out of range. Value:"..." Radix: 10"
                            String message = bundle.getString("exception.numberFormat.startOfValueOutOfRangeMessage") +
                                    " " + MessageFormat.format(bundle
                                            .getString("exception.numberFormat.endOfValueOutOfRangeMessage.0"),
                                    address[i]);
                            throw new NumberFormatException(message);
                        }
                    }
                    haveGoodAddress = true;
                } else {
                    // "The IP address should be four dot-separated numbers; ... does not conform."
                    System.out.println(MessageFormat.format(bundle.getString("connection.error.malformedAddress"),
                            addressString));
                    haveGoodAddress = false;
                }
            } catch (NumberFormatException nfException) {
                // "The IP address should be exactly as reported to the host user."
                System.out.println(bundle.getString("connection.error.badNumberInAddress"));
                String message = nfException.getMessage();
                // "Value out of range."
                if (message.contains(bundle.getString("exception.numberFormat.startOfValueOutOfRangeMessage"))) {
                    String[] messageTokens = message.split("\"");
                    long value = Long.parseLong(messageTokens[1]);   // this may break if message format changes
                    //  "Invalid address field: ... Address fields must be between 0 and 255, inclusive."
                    System.out.println(MessageFormat.format(bundle.getString(
                            "connection.error.valueOutOfRange.0"), value));
                }
                haveGoodAddress = false;
            }
        }
        return unsignedToSignedAddress(address);
    }

    /**
     * Obtains the host's network port from user input
     *
     * @param userInput a {@link java.util.Scanner} to read user responses
     * @return the host's network port
     */
    private int getPort(String prompt, Scanner userInput) {
        boolean haveGoodNumber = false;
        int port = 0;
        while (!haveGoodNumber) {
            System.out.print(prompt + ": ");
            try {
                port = userInput.nextInt();
                if (port <= 0) {
                    // "Expected positive value, got ..."
                    throw new InputMismatchException(MessageFormat.format(
                            bundle.getString("connection.error.portNumberTooLow"), port));
                }
                if (port >= 2 * (Short.MAX_VALUE + 1)) {
                    // "Expected value less than 65536, got ..."
                    throw new InputMismatchException(MessageFormat.format(
                            bundle.getString("connection.error.portNumberTooHigh"), port));
                }
                haveGoodNumber = true;
            } catch (InputMismatchException ignored) {
                // "The port number must be a positive integer strictly less than 65536."
                System.out.println(bundle.getString("connection.error.badPortNumber"));
                haveGoodNumber = false;
            } finally {
                userInput.nextLine();
            }
        }
        return port;
    }

    /**
     * Utility function necessary because Java does not have unsigned integer types. Converts from the signed bytes
     * that Java uses for IP address fields and mimics unsigned values that humans are accustomed to.
     *
     * @param signedAddress IP address fields in the range -128..127
     * @return IP address fields in the range 0..255
     */
    private short[] signedToUnsignedAddress(byte[] signedAddress) {
        short offset = -2 * Byte.MIN_VALUE;
        short[] unsignedAddress = new short[signedAddress.length];
        for (int i = 0; i < signedAddress.length; i++) {
            if (signedAddress[i] >= 0) {
                unsignedAddress[i] = signedAddress[i];
            } else {
                unsignedAddress[i] = (short) (offset + signedAddress[i]);
            }
        }
        return unsignedAddress;
    }

    /**
     * Utility function necessary because Java does not have unsigned integer types. Converts from the unsigned values
     * that humans are accustomed to and converts them to signed bytes that Java uses for IP address fields.
     *
     * @param unsignedAddress IP address fields in the range 0..255
     * @return IP address fields in the range -128..127
     */
    private byte[] unsignedToSignedAddress(short[] unsignedAddress) {
        short offset = -2 * Byte.MIN_VALUE;
        byte[] signedAddress = new byte[unsignedAddress.length];
        for (int i = 0; i < unsignedAddress.length; i++) {
            if (unsignedAddress[i] <= Byte.MAX_VALUE) {
                signedAddress[i] = (byte) unsignedAddress[i];
            } else {
                signedAddress[i] = (byte) (unsignedAddress[i] - offset);
            }
        }
        return signedAddress;
    }

    /*
     *  THESE METHODS PERFORM CHAT AFTER CONNECTION IS SET UP
     */

    /**
     * <p>Relays messages between the host and the client.</p>
     * <p>Implementation detail: delegates to another method, using dependency injection.</p>
     */
    @SuppressWarnings("WeakerAccess")
    public void communicate() {
        try {
            communicate(
                    new BufferedReader(new InputStreamReader(System.in)),
                    new BufferedReader(new InputStreamReader(socket.getInputStream())),
                    System.out,
                    new PrintStream(socket.getOutputStream()));
        } catch (IOException ioException) {
            // "Failed to set up input/output streams: ..."
            // "Terminating."
            System.err.println(bundle.getString("communicate.error.cannotSetUpStreams") + ": " + ioException);
            System.err.println(bundle.getString("communicate.info.terminating"));
            exit(1);
        }
    }

    /**
     * Where the real work for {@link #communicate()} happens.
     *
     * @param localInput   a {@link java.io.BufferedReader} that gets user input from this end of the connection
     * @param remoteInput  a {@link java.io.BufferedReader} that gets user input from the other end of the connection
     * @param localOutput  a {@link java.io.PrintStream} that outputs to the user at this end of the connection
     * @param remoteOutput a {@link java.io.PrintStream} that outputs to the user at the other end of the connection
     */
    @SuppressWarnings("SameParameterValue")
    private void communicate(BufferedReader localInput,
                             BufferedReader remoteInput,
                             PrintStream localOutput,
                             PrintStream remoteOutput) {
        // "Connection established. Host goes first."
        System.out.println(bundle.getString("connection.info.ready"));
        boolean keepTalking = true;
        boolean myTurnToTalk = isHost;
        while (keepTalking) {
            keepTalking = communicateOneMessage(localInput, remoteInput, localOutput, remoteOutput, myTurnToTalk);
            myTurnToTalk = !myTurnToTalk;
        }
    }

    /**
     * Processes a single message from one chatter to the other.
     *
     * @param localInput   a {@link java.io.BufferedReader} that gets user input from this end of the connection
     * @param remoteInput  a {@link java.io.BufferedReader} that gets user input from the other end of the connection
     * @param localOutput  a {@link java.io.PrintStream} that outputs to the user at this end of the connection
     * @param remoteOutput a {@link java.io.PrintStream} that outputs to the user at the other end of the connection
     * @param localMessage {@code true} if the message to be processed will originate locally, {@code false} if remotely
     * @return {@code true} if the program should continue to execute after processing the message; {@code false}
     * otherwise
     */
    boolean communicateOneMessage(BufferedReader localInput, BufferedReader remoteInput,
                                  PrintStream localOutput, PrintStream remoteOutput, boolean localMessage) {
        String message = "";
        boolean keepTalking = true;
        try {
            try {
                if (localMessage) {
                    message = localInput.readLine();
                    remoteOutput.println(encipher(message));
                } else {
                    String encipheredMessage = remoteInput.readLine();
                    if (encipheredMessage != null) {
                        message = decipher(encipheredMessage);
                        localOutput.println(message);
                    } else {
                        // "Received null message: lost connection to remote chatter. Terminating."
                        localOutput.println(bundle.getString("communicate.error.nullMessageFromRemote"));
                        keepTalking = false;
                    }
                }
            } catch (SocketException ignored) {
                // "Unable to exchange message: lost connection to remote chatter. Terminating."
                localOutput.println(bundle.getString("communicate.error.cannotSendMessage"));
                keepTalking = false;
            }
            if (keepTalking && keywords.contains(message)) {
                keepTalking = handleKeyword(message, localMessage, localInput, localOutput);
            }
        } catch (IOException ioException) {
            localOutput.println("Connection dropped: " + ioException);
            exit(1);
        }
        return keepTalking;
    }

    /**
     * Processes keywords to effect changes in the program, such as exiting, changing Locales, and changing Ciphers.
     *
     * @param keyword      the keyword to be processed
     * @param localMessage {@code true} if the message came from the local user, {@code false} if the message came
     *                     from the remote user
     * @param input        a {@link java.io.BufferedReader} for user input
     * @param output       a {@link java.io.PrintStream} for output to the user
     * @return {@code true} if the program should continue to execute after processing the keyword; {@code false}
     * otherwise
     * @throws IOException if an I/O error occurs while reading user responses to prompts
     */
    boolean handleKeyword(String keyword, boolean localMessage, BufferedReader input, PrintStream output)
            throws IOException {
        if (keyword.equals(bundle.getString("communicate.keyword.exit"))) {
            return false;
            // Un-comment this next code block in step 3 of the assignment.
        } else if (keyword.equals(bundle.getString("communicate.keyword.setLocale"))) {
        	if (localMessage) {
                // Here, you should
                // prompt the user using output.println() (be sure to use i18n properties)
            	// and get response using input.readLine(). Get the appropriate Locale and call
            	// setLocale( ... );
            	output.println(bundle.getString("connection.prompt.selectLocale"));
            	String response = input.readLine();
            	if(response.equalsIgnoreCase("fr")) {
            		setLocale(Locale.FRENCH);
            	} else {
            		setLocale(Locale.ENGLISH);
            	}
            	return true;
            }
            else {
                output.println("Remote chatter is making updates; please be patient."); // replace with i18n property
            }
        } else if (keyword.equals(bundle.getString("communicate.keyword.changeCipher"))) {

        	// Determines what encryption algorithm the user wants to use along
        	// with the keys and changes the cipherStrategy to match
        	
        	if (localMessage) {
            	output.println(bundle.getString("connection.prompt.selectCipher"));
            	String response = input.readLine();
            	if(response.equalsIgnoreCase("Caesar")) {
            		output.println(bundle.getString("connection.prompt.getCaesarKey"));
            		String key = input.readLine();
            		String[] keys = new String[1];
            		keys[0] = key;
            		cipherStrategy = CipherFactory.createCipher(response, keys);
            	} else {
            		output.println(bundle.getString("connection.prompt.getAffineKeys"));
            		String key = input.readLine();
            		String[] keys = key.split(",");
            		cipherStrategy = CipherFactory.createCipher(response, keys);
            	}
            	return true;
            }
        	else {
                output.println("Remote chatter is making updates; please be patient."); // replace with i18n property
            }
        } else {
            output.println(bundle.getString("communicate.error.unrecognizedKeyword") + ": " + keyword);
        }
        return true;
    }

    private String encipher(String plaintext) {
        String ciphertext = cipherStrategy.encipher(plaintext);
        return ciphertext;
    }

    private String decipher(String ciphertext) {
        String plaintext = cipherStrategy.decipher(ciphertext);
        return plaintext;
    }

    public static void main(String[] args) {
        Chat chat = new Chat();
        chat.connect(new Scanner(System.in));
        chat.communicate();
        chat.disconnect();
    }
}
