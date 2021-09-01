# Socket Chat Program

-   Assignment Due: June 23, 2020 at 11:00am (CDT, UTC-5)
-   Peer Assessment Due: June 23, 2020 at 11:59pm

In this assignment you will modify a simple 2-way network chat program to
prompt the user and get responses in a language other than English, and to
provide rudimentary protection of the messages passing over the network.

You will also perform *whitebox testing* of part of your code. In the
Calculator assignment you developed a set of test cases based on the blackbox
specification. Rarely will such a test suite fully exercise the code. You will
develop a test suite such that each statement in the code under test is
executed at least once.  (Note also that whitebox testing will rarely cover the
blackbox specification. In practice, a combination of blackbox and whitebox
testing is necessary.)

### Objectives

Students will:

-   Demonstrate good software engineering practices
-   Learn two design patterns
    -   Strategy Pattern
    -   Simple Factory Pattern
-   Make use of resource "property" files to support internationalization
-   Develop whitebox test cases to attain statement coverage
-   Practice using test doubles
-   Create JUnit tests for those test cases
-   Practice overriding the default author on a git commit

## Instructions

This assignment is to be completed in assigned pairs; **no collaboration
other than with your assigned partner is permitted**.  One of the purposes of
pair-assignments is to practice teamwork. After completing the assignment you
will need to complete a peer assessment. Your contribution grade will be based
on the peer assessments and on the git history.

*Commit material that you worked on individually under your own name* using the
defaults that you set. *When (and only when) you commit material that was
developed using pair programming, override the default commit author to reflect
both authors* so that we can properly credit both authors for their contribution
grades. When you override the default commit author list both students' names,
and for the email address use a fake email address that is unique to the pair
of students by concatenating your Canvas login IDs (the angle brackets around
the email address are required):
```
git commit --author="Herbie Husker and Lil Red <hhusker20lred19@dev.null>"
```
You can use this same technique for the rare circumstance in which your partner
is briefly unable to commit code themselves:
```
git commit --author="Herbie Husker <herbie@huskers.unl.edu>"
```

## Setup

1.  You and your partner will work on a shared repository, which has been
    prepared for you.

    1.  Navigate to your shared directory
        (<https://git.unl.edu/csce_361/summer2020/09pairNN/>, where *NN* is your
        team number).

    1.  Verify that the repository is private, and that you and your partner
        both have Maintainer access.

1.  Both students should:

    1.  Clone the project: `git clone <URL>` (here the angle brackets should
        not be included).

        -   **Do *NOT* place your socket_chat repository inside your
            csce361-homework repository!**

    1.  Import the project into your IDE. The project is set up as a Maven
        project, so you can follow your IDE's instructions to import a Maven
        project.

## Issue Tracker

We have pre-populated your repository's Issue Tracker with issues for the
various parts of the assignment that need to be completed. **We do *not*
guarantee that the pre-populated issues are complete; this document is the
authoritative source of requirements.**

-   A good way to coordinate who is working on which parts of the code is to
    use the [web interface](https://docs.gitlab.com/ee/user/project/issues/index.html#issue-page)
    to "assign" an issue to a team member.
-   You may [add more issues](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#create-a-new-issue)
    to the Issue Tracker if you wish. This is common when you discover more
    tasks that need to be accomplished or when you want to divide an existing
    issue into finer-grained tasks (the original issue would still exist, but
    the finer-grained issues may make it easier to divide the work).
-   When you have completed a task, [close the corresponding issue](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues).
    You can do this through the issue tracker, or you can do it through a
    commit message.
    -   To close an issue through a commit message, include a keyword such as
        `closes #12` in a commit message. You cannot create a commit message
        only to close an issue; this must be a commit message for adding/
        changing/removing files or for a merge.  See <https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#default-closing-pattern>
        for keywords that will close an issue.

## Assignment

Look over the starter code, view
<!--[this short demonstration](https://use.vg/qB6zFZ),-->
[this short demonstration](https://use.vg/eiLVWB) <!-- this one includes relay server -->
and run the program to get a
feel for how the program works.

-   One host, one client, but the same code
-   Normal usage (requires host and client to be on the same network):
    -   To avoid having to do any network discovery, the host displays its IP
        address, which must be shared with whoever is running the client so they
        can type in the IP address
    -   The host sets the port number to be used, and this must be shared with
        whoever is running the client so they can type in the IP address
        -   If the port number were hard-coded then we'd run the risk of that
            port number already being in use
    -   The host sets up a `ServerSocket` to accept the client's connection, and
        the client sets up a `Socket` to connect to the host
-   Remote instruction usage:
    -   We have provided a Relay Server on csce.unl.edu, whose IP address is
        publicly visible, so it doesn't have to be on the same network as the
        chatters
    -   When launching SocketChat, both chatters should select the option to be
        the client
    -   IP address: <!--`-127.93.-91.26`--> `129.93.165.26`, Port: `361NN`,
        where *NN* is your team number
-   The two chatters alternate turns sending Strings to each other over the
    socket
    -   The host (or the first chatter to connect to the Relay Sever) sends the
        first message
-   When there's message from either chatter consisting solely of the keyword
    `EXIT` in all capital letters, the program terminates.

<!--
NOTE: in the demonstration, I used jar files. This was convenient for the
purposes of the demonstration. You do not have to create jar files, but if you
want to, you can use Maven's "package" target. (Separate instructions for this
will be posted on Piazza.)
-->

### Internationalization

For successful software, it's almost certain that there will be people who want
to use your software who don't speak the same language as you. The process of
making your software work with any human language is called
*internationalization*, often abbreviated as *I18N*.

A common technique for I18N is to use a `ResourceBundle` of key-value pairs.
Notice that anyplace that the program outputs something, the argument to
`println()` is `bundle.getString(...)` -- that substitutes the value String
that corresponds to the specified key String. We also use this to compare the
user input to a string.

The key-value pairs are in a properties file in the `resources/` directory.
The files are of the form `basename_XX.properties`, where XX is the 2- or 3-
character language code. Example language codes are `en` for English, `fr` for
French, `de` for German, `zh` for Chinese, and `tlh` for Klingon. If you decide
to do full localization then the suffix includes a language code, a country
code, and possibly a variant code. We'll limit ourselves to just the language
code. Since the basename we're using is "socketchat", the file with the English-
language strings is `socketchat_en.properties`.

-   The repository includes what may appear to be a file simply named
    `socketchat.properties`. **Do not directly edit this file**.

Notice that some of the value strings have numbered arguments such as `{0}` and
`{1}`. When combined with `MessageFormat.format()`, this allows us to create
parameterized strings similar to C's `printf()`.

1.  Create a properties file for another language. If you don't know another
    language, you may use an electronic (or online) translator or bilingual
    dictionary for this assignment.
    -   The language codes can be found in
        [ISO 639-2](https://www.loc.gov/standards/iso639-2/php/code_list.php).
        If both a 2-letter and a 3-letter language code are available for the
        language you choose, you *must* use the 2-letter code.
    -   You *must* use the same keys as the English-language properties file,
        because the program depends on the keys.

1.  Decide on a keyword to indicate that you want to change the language. As
    with the `EXIT` keyword, this keyword will be typed as part of the chat.
    Add this keyword in English to `socket_chat_en.properties` and in the other
    language's properties file, using the key `communicate.keyword.setLocale`.

1.  Un-comment the commented-out code in `Chat.handleKeyword()` and edit it to
    handle the new keyword.
    -   Find out what language the user wants to change to.
    -   At a minimum, you must handle the cases where the user wants to change
        to English and where the user wants to change to the language you chose
        in Step 1.
    -   After you have determined which language the user wants to change to,
        call `Chat.setLocale()`
        -   Use `Locale.ENGLISH` or `Locale.US` if the user wants to change to
            English.
        -   If you earlier chose Chinese, French, German, Italian, Japanese, or
            Korean, you can use `Locale.CHINESE`, `Locale.FRENCH`,
            `Locale.GERMAN`, `Locale.ITALIAN`, `Locale.JAPANESE`, or
            `Locale.KOREAN`.
        -   If you earlier chose a language listed in the first column of the
            table on [this webpage](https://www.oracle.com/technetwork/java/javase/javase7locales-334809.html),
            then you can use the `Locale.Locale(String language)` constructor,
            where `language` is the 2-character language code at the start of
            the Locale ID in the third column of that table. Or use
            `Locale.Locale(String language, String country)` where `country` is
            the 2-character country code at the end of the Locale ID in the
            third column. Or (rarely), `Locale.Locale(String language, String
            country, String variant)`.
        -   Otherwise, create a `Locale` using [`Locale.Builder`](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.Builder.html).
<!--            (Yes, this is using the Builder Pattern.) -->

### Whitebox Testing

This is the specification for `Chat.communicateOneMessage()` (not including
exception handling):
-   The method will return `true` unless otherwise noted below
-   If the message to be processed originates locally then the contents of
    `localInput` shall be enciphered and be placed on `remoteOutput`
-   If the message to be processed originates remotely then the contents of
    `remoteInput` shall be deciphered and be placed on `localOutput`
-   If a remotely-originated message is `null`, then an error message shall be
    placed on `localOutput` indicating that a null message was received, and
    the method will return `false`
-   If the message, regardless of origin, consists solely of a keyword, then
    the message shall be passed to the `handleKeyword()` method for processing;
    the `communicateOneMessage()` method will return the boolean value returned
    by `handleKeyword()`

4.  Write sufficient JUnit tests to attain *statement coverage* of
    `communicateOneMessage()`

    -   You will need to prepare test doubles to be able to run automated tests
        of `communicateOneMessage()`

    -   The exception-handling behavior is not part of the specification, but
        the developers wisely attempted to gracefully handle exceptional
        conditions; you will need to write tests to exercise the catch block
        for a `SocketException` (we have provided a test for the `IOException`
        catch block)

    -   You can confirm that you have statement coverage by generating a
        coverage report
        -   Eclipse: <https://www.eclemma.org/userdoc/importexport.html>
            -   Instructions at the bottom of that page. Export as HTML
        -   IntelliJ IDEA: <https://www.jetbrains.com/help/idea/generating-code-coverage-report.html>

### Strategy Pattern

You will use the *Strategy Pattern* to attach cipher algorithms to the chat
program.[^1]

-   HFDP, [Chapter 1](https://learning.oreilly.com/library/view/head-first-design/0596007124/ch01.html)

[^1]:   For this application, you are going to write cipher algorithms and
        attach them to the chat program using the Strategy Pattern, so that you
        can learn the Strategy Pattern. For a real application, I strongly
        advise against writing your own cipher algorithms. Instead use
        `javax.crypto.Cipher` and, if you're streaming text back and forth as
        in this program, attach the algorithms using the Decorator pattern via
        `javax.crypto.CipherInputStream` and `javax.crypto.CipherOutputStream`.
        (for the Decorator Pattern, see HFDP,
        [Chapter 3](https://learning.oreilly.com/library/view/head-first-design/0596007124/ch03.html))

The original version of this program sends plaintext messages over the network.
The current version passes an outgoing message through `Chat.encipher()` and
incoming messages through `Chat.decipher()`. Right now, all these methods do is
return the original message without enciphering (or deciphering) it.

5.  Create `Cipher.java`, an interface with two methods: `String
    encipher(String plaintext)` whose specification is that it that passes the
    plaintext through a cipher to create ciphertext, and `String
    decipher(String ciphertext)` whose specification is that it passes the
    cipher text through the inverse cipher to produce the original plaintext.

1.  Create `NullCipher.java`, a class that implements the `Cipher` interface.
    `NullCipher.encipher()` and `NullCipher.decipher()` should simply return
    their argument without modification (just like `Chat.encipher()` and
    `Chat.decipher()` do now.)

1.  Create a field in `Chat.java` for the cipher behavior. You might call it
    `Cipher cipherBehavior` or `Cipher cipherStrategy` -- or you might simply
    call it `Cipher cipher`, but by giving it a name with the word *Behavior*
    or *Strategy* in it, you're flagging the existence of the Strategy Pattern
    to anybody who reads your code.
    -   In the `Chat.Chat()` constructor, initialize this field to a
        `NullCipher` object.

1.  Replace the code in `Chat.encipher()` and `Chat.decipher()` with:
    ```
    private String encipher(String plaintext) {
        String ciphertext = cipherBehavior.encipher(plaintext);
        return ciphertext;
    }

    private String decipher (String ciphertext) {
        String plaintext = cipherBehavior.decipher(ciphertext);
        return plaintext;
    }
    ```
    (here I assumed you named the `Cipher` field `cipherBehavior`. If you
    didn't, the substitute the actual name you used.)

At this point, the program should still have the same externally-observable
behavior it had after you finished Step 3. What's different is that,
internally, you've delegated the behavior for `Chat.encipher()` and
`Chat.decipher()` to another object.

This would be a good time to verify that your partial implementation of the
Strategy Pattern hasn't broken anything.

9.  Re-run your test suite to make sure no changes caused your tests to fail.

1.  Implement two classical ciphers as Java classes that implement the `Cipher`
    interface. You can include any other methods you feel are necessary;
    however, only `encipher()` and `decipher()` will be exposed to `Chat.java`.
    -   You may use ciphers you implemented in a previous course (but they must
        be written in Java) or [other
        ciphers](http://practicalcryptography.com/ciphers/classical-era/).
    -   You probably want to have the key(s) be arguments to the constructors,
        but feel free to explore other options.
    -   These must be different ciphers. Implementing the same cipher with
        different keys will be treated as having implemented only one cipher.
        For example, a Caesar cipher with a shift of 5 and a Caesar cipher with
        a shift of 16 are the same cipher with different keys.

1.  Write JUnit tests to verify that your `encipher()` and `decipher()` methods
    function correctly.

1.  You can replace `NullCipher` in the `Chat` constructor with either of the
    other ciphers you wrote, and your program will still work.

1.  If `NullCipher` is no longer the initial cipher then make any changes to
    your tests that are necessary to reflect this new behavior
    -   The option that requires the least effort in the short term would be to
        change the input and/or expected output for some tests to take into
        account the new cipher
    -   A better option would be to make any changes needed to your code to
        allow your tests to set the cipher being used, then set the cipher
        either in your tests or in your `setUp()`, and finally change the input
        and/or expected output for some tests to take into account the assigned
        cipher
        -   While this will require a little more effort in the short term, it
            means you won't have to change your tests again if you (or somebody
            else) later decides to change the initial cipher
    -   Run your test suite to make sure everything passes

What we need now to complete the Strategy Pattern is a way to replace the
cipher algorithm at runtime. Many options are possible; we'll use the...

### Simple Factory Pattern

You will use the *Simple Factory Pattern* to obtain the cipher algorithms

-   HFDP, [Chapter 4](https://learning.oreilly.com/library/view/head-first-design/0596007124/ch04.html)
-   The authors of *Head First Design Patterns* say it isn't a real pattern
    but instead is a commonly-used idiom. This is a distinction without a
    difference: a pattern is a common solution to a common problem, and an
    idiom is a common way to express an idea in code.

The Simple Factory is not nearly as powerful as the Abstract Factory Pattern,
or even the Factory Method Pattern, but I sure seem to use it often. There is
no one-true form of the Simple Factory. Sometimes it takes no arguments and
solely exists to hide which concrete class is being used. Other times, it will
be parameterized and the particular concrete class that is returned depends on
the arguments used. Another option yet is to have a method that sets the
concrete type, and then the `create` method will always return that concrete
type -- but because the `create` method is declared to return the abstraction,
the client code cannot assume a concrete type.

We will use a parameterized factory.

14. Create `CipherFactory.java`.

1.  Write the method
    `public static Cipher createCipher(String name, String[] keys)`. This
    method shall create and return an instance of a `Cipher` implementation
    class that corresponds to `name`. If that cipher only requires one key,
    then this method will use `keys[0]` as that key; if the cipher requires
    multiple keys, then it will use the appropriate number of elements from
    `keys` as the cipher keys. If that cipher requires no keys, then this
    method will ignore `keys`.

1.  Write the method `public static Cipher createCipher()` without any
    arguments. This method shall return a default cipher algorithm. (You decide
    what that default is).

That's it. That's your Simple Factory.

Now it's time to use it.

17. Find line line in the `Chat.Chat()` constructor where you wrote
    `cipherBehavior = new NullCipher()` (or something like that). Replace
    `new NullCipher()` with `CipherFactory.createCipher()`. Now your cipher
    algorithm is whatever the default happens to be.

    -   At this point, nothing in `Chat.java` should depend on any particular
        implementation of the `Cipher` interface.

1.  Decide on a keyword to indicate that you want to change the cipher
    algorithm. As with the `EXIT` keyword, this keyword will be typed as part
    of the chat. Add this keyword in English to `socket_chat_en.properties` and
    in the other language's properties file, using a key of your choosing (but
    make sure it starts with `communicate.keyword.` so that the code to detect
    keywords knows to look for it).

1.  Add code to `Chat.handleKeyword()` to handle the new keyword. This code
    should work with the user to change the cipher algorithm to whichever
    cipher algorithm they want, with the key(s) they want.

    Nothing in your code should directly or indirectly depend on
    implementations of `Cipher`.

    -   There should be no direct dependence: the concrete `Cipher`s should not
        even be mentioned anywhere in Chat.java.
    -   There should be no indirect dependence: you should not have conditional
        statements to determine what arguments to pass to the `CipherFactory`.
    -   You can check whether you attained this by answering the following
        question: If you write another cipher, will you have to add or modify
        any code in `Chat.java`? Obviously you will have to add code to
        `CipherFactory.java`. If you have to add or modify `Chat.java` then
        your code still depends on knowledge of `Cipher` implementations.

1.  Re-run your test suite to make sure no changes caused your tests to fail.

You can now chat away without worrying about a "l337 h4x0r" being snoopy. (If
you're worried about someone with NSA-level snooping capabilities, don't use a
"classical era" cipher! They're relatively easy to break in general, and the
keywords would help someone trying to break it do so by giving known words to
look for. The "EXIT" keyword especially so, since it occurs at the end of every
conversation.)

You might wonder why we used a Simple Factory to create Cipher objects instead
of just putting the same logic in a method in the `Chat` class. There are three
reasons. One is that there might be other projects that could use these Cipher
objects, and we can simply reuse the `CipherFactory` instead of copy-pasting
code. Another is that in a larger project, there may be several classes that
need such an object, and we'd prefer to have a creation class that all classes
in the system can use.

The other reason isn't about code reuse at all. Software engineering has the
*dependency inversion principle*: depend on abstractions, not concretions.
After you've finished this assignment, there isn't a single line of code that
mentions concrete `Cipher` classes in `Chat.java`. Not one. Now, no matter what
we do with cipher algorithms, we aren't worried about accidentally breaking the
`Chat` class. As we make changes, we'll never have to run regression tests on
`Chat`. It's very comforting to know that you've reduced the coupling in your
system so that you aren't afraid of breaking anything important when you make a
small change to an ancillary piece of code.

## Deliverables

For grading, we will pull the `socket_chat` repositories after the assignment
is due, and we will look in the Maven-conventional directories for:

-   A properties file for your chosen language
-   Updated `socketchat_en.properties` file
-   Updated `Chat.java`
-   `Cipher.java` and `CipherFactory.java`
-   Three `Cipher` implementations (`NullCipher.java` plus two others of your
    choosing)
-   Updated `ChatTest.java`

*It is your responsibility to ensure that your work is in the master branch of
the **correct repository** and that we have the correct level of access to the
repository at the **time the assignment is due**.  We will grade what we can
retrieve from the repository at the time it is due.  Any work that is not in
the correct repository, or that we cannot access, will not be graded.*

## Rubric

The assignment is worth **25 points**:

-   **4 points** for internationalization
    -   1 points for creating the properties file for the other language
    -   3 points for writing the code to change Locales

-   **3 points** for creating the Simple Factory

-   **9 points** for the Strategy Pattern
    -   1 point for creating `Cipher.java` and `NullCipher.java`
    -   2 points each for the 2 other classical cipher algorithms
    -   1 point for delegating `encipher` and `decipher`
    -   3 points for writing the code to change cipher algorithms that is fully
        independent of `Cipher` implementations

-   **6 points** for tests
    -   2 point for testing `encipher` and `decipher` for your two classical
        cipher algorithms
    -   4 points for writing sufficient tests to attain statement coverage of
        `Chat.communicateOneMessage()`

-   **1 point** for making regular commits throughout the project

-   **2 points** for meaningful commit messages

This assignment is scoped for a team of 2 students. If, despite your attempts
to engage your partner, your partner does not contribute to the assignment then
we will take that into account when grading.

*If **at any time** your repository is public or has internal visibility then
you will receive a 10% penalty. Further, if another student accesses your
non-private repository and copies your solution then I will assume that you are
complicit in their academic dishonesty.*

## Contribution Rubric

The contribution is worth **10 points**:

-   **1 point** for completing peer assessment on time
-   **1 point** for contacting your partner promptly
-   **4 points** for equitable contribution based on peer assessments
-   **4 points** for equitable contribution based on git history

## Footnote
