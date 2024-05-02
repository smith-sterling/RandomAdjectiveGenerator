import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    //note to self: when running from the IDE, you must prepend "src/" to the pathnames for these files for them to work
    // but as they are, running "java Main" from the terminal works just fine
    private static final File masterListFile = new File("masterList.txt");
    private static final File listOfDaysDoneFile = new File("listOfDaysDone.txt");
    private static final File historyFile = new File("history.txt");

    public static void main(String[] args) {
        try {
            //check if files exist
            if (!masterListFile.exists()) System.out.println("No masterListFile");
            if (!historyFile.exists()) System.out.println("No historyFile");
            if (!listOfDaysDoneFile.exists()) System.out.println("No listOfDaysDoneFile");
            if (!masterListFile.exists() || !historyFile.exists() || !listOfDaysDoneFile.exists()) {
                System.out.println("Exiting...");
                return;
            }

            if (todayHasBeenDone()) {
                printLastWord();
                return;
            }

            List<String> words = listWordsFromFiles(masterListFile);
            while (!words.isEmpty()) { //"while" and not "if" in case you pick a repeat word
                String s = words.get(new SecureRandom().nextInt(words.size()));
                if (isNewWord(s)) {
                    System.out.printf("Your word for the day is%n\t%s%n", "\033[38;5;33m" + s + "\033[0m");
                    writeToHistory(s);
                    break;
                } else {
                    words.remove(s);
                    // TODO: if words.isEmpty write to new file and start checking that one
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    private static boolean todayHasBeenDone() throws FileNotFoundException {
        Scanner scanner = new Scanner(listOfDaysDoneFile);
        String todayString = stringify(new Date());
        while (scanner.hasNextLine()) {
            if (scanner.nextLine().equals(todayString)) {
                return true;
            }
        }
        return false;
    }

    private static void printLastWord() throws FileNotFoundException {
        Scanner scanner = new Scanner(historyFile);
        String lastWord = "";
        while (scanner.hasNextLine()) {
            //note that history.txt has the words of the day on their own lines, so there is no split() needed, only strip()
            lastWord = scanner.nextLine();
        }

        System.out.println(
                "You already got a word for today. That word was:\n" + "\033[38;5;33m" + lastWord + "\033[0m");
    }

    private static List<String> listWordsFromFiles(File... files) throws FileNotFoundException {
        Set<String> strings = new TreeSet<>();
        for (File file : files) {
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) strings.add(capFirstLetter(scanner.nextLine()));
            }
        }
        return new ArrayList<>(strings);
    }

    private static String capFirstLetter(String string) {
        StringBuilder s = new StringBuilder(string.strip());
        s.replace(0, 1, String.valueOf(Character.toUpperCase(s.charAt(0))));
        return s.toString();
    }

    private static boolean isNewWord(String s) throws FileNotFoundException {
        Scanner scanner = new Scanner(historyFile);
        while (scanner.hasNextLine()) {
            //note that history.txt has the words of the day on their own lines, so there is no split() needed, only strip()
            if (scanner.nextLine().strip().equalsIgnoreCase(s)) {
                return false;
            }
        }
        return true;
    }

    private static void writeToHistory(String adjective) {
        try {
            FileWriter writer = new FileWriter(historyFile, true);

            Date theDate = new Date();
            String dateString = stringify(theDate);

            writer.append("\nThe adjective of the day for ");
            writer.append(dateString);
            writer.append(", generated at ");
            writer.append(theDate.toString().substring(11, 16));  //the time of day
            writer.append(", is:\n\t");
            writer.append(adjective);
            writer.flush();
            writer.close();

            writeToListOfDaysDone(dateString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String stringify(Date d) {
        StringBuilder bulk = new StringBuilder(d.toString());

        return fullDay(bulk.substring(0, 3)) +   //day of week string
               ", " +
               bulk.substring(8, 11) + //day number
               fullMonth(bulk.substring(4, 7)) +  //month string
               " " +
               bulk.substring(24, 28);
    }

    private static String fullDay(String abbrev) {
        return switch(abbrev) {
            case "Sun" -> "Sunday";
            case "Mon" -> "Monday";
            case "Tue" -> "Tuesday";
            case "Wed" -> "Wednesday";
            case "Thu" -> "Thursday";
            case "Fri" -> "Friday";
            case "Sat" -> "Saturday";
            default -> "Error";
        };
    }

    private static String fullMonth(String abbrev) {
        return switch(abbrev) {
            case "Jan" -> "January";
            case "Feb" -> "February";
            case "Mar" -> "March";
            case "Apr" -> "April";
            case "May" -> "May";
            case "Jun" -> "June";
            case "Jul" -> "July";
            case "Aug" -> "August";
            case "Sep" -> "September";
            case "Oct" -> "October";
            case "Nov" -> "November";
            case "Dec" -> "December";
            default -> "Error";
        };
    }

    private static void writeToListOfDaysDone(String dateString) throws IOException {
        FileWriter writer = new FileWriter(listOfDaysDoneFile, true);
        writer.append("\n");
        writer.append(dateString);

        writer.flush();
        writer.close();
    }
}