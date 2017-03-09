package lv.tele2.javaschool.phonebook;

import asg.cliche.ShellFactory;

import java.io.IOException;

/**
 * @author Dimitrijs Fedotovs <a href="http://www.bug.guru">www.bug.guru</a>
 * @version 1.0
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {
        try {
            ShellFactory.createConsoleShell("book", null, new PhoneBook())
                    .commandLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
