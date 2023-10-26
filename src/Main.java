import java.awt.*;
import java.io.*;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;

public class Main {

//    private static final File file = new File("/Users/sterlite/Desktop/PetProjects/randomAdjectiveGenerator/src/adjectives.txt");
//    private static final File file2 = new File("/Users/sterlite/Desktop/PetProjects/randomAdjectiveGenerator/src/otherList.txt");
//    private static final File file3 = new File("/Users/sterlite/Desktop/PetProjects/randomAdjectiveGenerator/src/personAdjectives.txt");
//    private static final File file4 = new File("/Users/sterlite/Desktop/PetProjects/randomAdjectiveGenerator/src/untrimmedMasterList.txt");
    private static final File file5 = new File("/Users/sterlite/Desktop/PetProjects/randomAdjectiveGenerator/src/masterList.txt");

    public static void main(String[] args) {
        try {
            checkFilesExist();
            
            if (todayHasBeenDone()) {
                printLastWord();
                return;
            }
            
            List<String> words = listWordsFromFiles(file5);
            while (! words.isEmpty()) { //"while" and not "if" in case you pick a repeat word
                String s = words.get(new SecureRandom().nextInt(words.size()));
                if (isNewWord(s)) {
                    System.out.printf("Your word for the day is%n\t%s%s%n", "\033[0;36m", s + "\033[0m");
                    writeToHistory(s);
                    break;
                } else {
                    words.remove(s);
                    // TODO: 6/15/23 if words.isEmpty write to new file and start checking that one
                }
            }

        } catch(FileNotFoundException e) {
            System.out.println("Error");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    private static void checkFilesExist() {
//        if (!file.exists()) System.out.println("No file1");
//        if (!file2.exists()) System.out.println("No file2");
//        if (!file3.exists()) System.out.println("No file3");
//        if (!file4.exists()) System.out.println("No file4");
        if (!file5.exists()) System.out.println("No file5");
    }
    private static boolean todayHasBeenDone() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("/Users/sterlite/Desktop/PetProjects/randomAdjectiveGenerator/src/listOfDaysDone.txt"));
        String todayString = stringify(new Date());
        while (scanner.hasNextLine()) {
            if (scanner.nextLine().equals(todayString)) {
                return true;
            }
        }
        return false;
    }
    private static void printLastWord() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("/Users/sterlite/Desktop/PetProjects/randomAdjectiveGenerator/src/history.txt"));
        String lastWord = "";
        while (scanner.hasNextLine()) {
            //note that history.txt has the words of the day on their own lines, so there is no split() needed, only strip()
            lastWord = scanner.nextLine();
        }
        
        System.out.println("You already got a word for today. That word was:\n" + "\033[0;36m" + lastWord + "\033[0m");
    }
    
    private static List<String> listWordsFromFiles(File...files) throws FileNotFoundException {
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
        Scanner scanner = new Scanner(new File("/Users/sterlite/Desktop/PetProjects/randomAdjectiveGenerator/src/history.txt"));
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
            File history = new File("/Users/sterlite/Desktop/PetProjects/randomAdjectiveGenerator/src/history.txt");
            FileWriter writer = new FileWriter(history, true);
            
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
        return switch (abbrev) {
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
        return switch (abbrev) {
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
        File listOfDaysDone = new File("/Users/sterlite/Desktop/PetProjects/randomAdjectiveGenerator/src/listOfDaysDone.txt");
        
        FileWriter writer = new FileWriter(listOfDaysDone, true);
        writer.append("\n");
        writer.append(dateString);
        
        writer.flush();
        writer.close();
    }
}