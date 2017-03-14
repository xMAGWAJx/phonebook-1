package lv.tele2.javaschool.phonebook;

import asg.cliche.ShellFactory;

import java.io.*;

/**
 * @author Dimitrijs Fedotovs <a href="http://www.bug.guru">www.bug.guru</a>
 * @version 1.0
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("myPhones.ser");
            PhoneBook phoneBook;
            if (file.exists()) {
                phoneBook = readPhoneBook(file);
            } else {
                phoneBook = new PhoneBook();
            }
            ShellFactory.createConsoleShell("book", null, phoneBook)
                    .commandLoop();
            savePhoneBook(file, phoneBook);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PhoneBook readPhoneBook(File file) {
        try (
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            PhoneBook result = (PhoneBook) ois.readObject();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading. Creating new phone book");
            return new PhoneBook();
        }
    }

    private static void savePhoneBook(File file, PhoneBook phoneBook) {
        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(phoneBook);
        } catch (IOException e) {
            System.out.println("Error saving file");
        }
    }
}
