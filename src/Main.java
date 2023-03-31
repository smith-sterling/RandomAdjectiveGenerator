import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static File file = new File("./src/adjectives.txt");
    public static File file2 = new File("./src/otherList.txt");
    public static File file3 = new File("./src/personAdjectives.txt");
    public static File file4 = new File("./src/untrimmedMasterList.txt");
    public static File file5 = new File("./src/masterList.txt");

    public static void main(String[] args) {
        /*
        if (!file.exists()) System.out.println("No file1");
        if (!file2.exists()) System.out.println("No file2");
        if (!file3.exists()) System.out.println("No file3");
        if (!file4.exists()) System.out.println("No file4");
        if (!file5.exists()) System.out.println("No file5");
        Set<String> strings = new TreeSet<>();
        Scanner scanner = null;
        try {scanner = new Scanner(file);} catch(FileNotFoundException ignore) {}
        while (scanner.hasNextLine()) strings.add(capFirstLetter(scanner.nextLine()));
        System.out.println("After adjectives: " + strings.size());
    
        try {scanner = new Scanner(file2);} catch(FileNotFoundException ignore) {}
        while (scanner.hasNextLine()) strings.add(capFirstLetter(scanner.nextLine()));
        System.out.println("After otherList: " + strings.size());
    
        try {scanner = new Scanner(file3);} catch(FileNotFoundException ignore) {}
        while (scanner.hasNextLine()) strings.add(capFirstLetter(scanner.nextLine()));
        System.out.println("After personAdjectives: " + strings.size());
    
        try {scanner = new Scanner(file4);} catch(FileNotFoundException ignore) {}
        while (scanner.hasNextLine()) strings.add(capFirstLetter(scanner.nextLine()));
        System.out.println("After untrimmedMasterList :" + strings.size());
        
        try (FileWriter writer = new FileWriter(file5, false)) {
            for (String s : strings) {
                writer.append(s).append("\n");
            }
        } catch(IOException e) {
            System.out.println("didn't work");
            e.printStackTrace();
        }*/
    
        // TODO: 3/28/23 we could add definitions to the words already? a lot more work, and not that much to gain,
        //  but an option at least
        
        try {
            Set<String> strings = new TreeSet<>();
//            if (file.exists()) {
//                Scanner scanner = new Scanner(file);
//                while (scanner.hasNextLine()) {
//                    StringBuilder s = new StringBuilder(scanner.nextLine().toLowerCase());
//                    s.replace(0, 1, String.valueOf(Character.toUpperCase(s.charAt(0))));
//                    strings.add(s.toString());
//                }
//            }
            if (file2.exists()) {
                Scanner scanner = new Scanner(file2);
                while (scanner.hasNextLine()) strings.add(capFirstLetter(scanner.nextLine()));
//                {
//                    StringBuilder s = new StringBuilder(scanner.nextLine().toLowerCase());
//                    s.replace(0, 1, String.valueOf(Character.toUpperCase(s.charAt(0))));
//                    strings.add(s.toString());
//                }
            }
            if (file3.exists()) {
                Scanner scanner = new Scanner(file3);
                while (scanner.hasNextLine()) strings.add(capFirstLetter(scanner.nextLine()));
//                while (scanner.hasNextLine()) {
//                    StringBuilder s = new StringBuilder(scanner.nextLine().toLowerCase());
//                    s.replace(0, 1, String.valueOf(Character.toUpperCase(s.charAt(0))));
//                    strings.add(s.toString());
//                }
            }
//            for (String s : strings) {
//                System.out.println(s);
//            }
            ArrayList<String> array = new ArrayList<>(strings);
            while (strings.size() > 0) {
                String s = array.get(new Random().nextInt(strings.size()));
                
                if (isNewWord(s)) {
                    System.out.println(s);
                    writeToHistory(s);
                    break;
                }
            }

        } catch(FileNotFoundException e) {
            System.out.println("Error");
        }
    }
    
    public static boolean isNewWord(String s) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/history.txt"));
        while (scanner.hasNextLine()) {
            if (scanner.nextLine().trim().equalsIgnoreCase(s)) {
                return false;
            }
        }
        return true;
    }
    
    public static String capFirstLetter(String string) {
        StringBuilder s = new StringBuilder(string.strip());
        s.replace(0, 1, String.valueOf(Character.toUpperCase(s.charAt(0))));
        return s.toString();
    }

    public static String stringify(Date d) {
        StringBuilder bulk = new StringBuilder(d.toString());
        StringBuilder toReturn = new StringBuilder();

        toReturn.append(fullDay(bulk.substring(0,3)));   //day of week string
        toReturn.append(", ");
        toReturn.append(bulk.substring(8, 11)); //day number
        toReturn.append(fullMonth(bulk.substring(4, 7)));  //month string
        toReturn.append(" ");
        toReturn.append(bulk.substring(24, 28));//year
        toReturn.append(", generated at ");
        toReturn.append(bulk.substring(11, 16));

        return toReturn.toString();
    }
    public static String fullDay(String abbrev) {
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
    public static String fullMonth(String abbrev) {
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

    public static void writeToHistory(String adjective) {
        try {
            File myObj = new File("src/history.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }

            FileWriter writer = new FileWriter(myObj, true);
            writer.append("\nThe adjective of the day for ");
            writer.append(stringify(new Date())).append(", is:\n\t");
            writer.append(adjective);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}