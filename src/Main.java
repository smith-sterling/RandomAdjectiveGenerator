import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.spi.CalendarDataProvider;

public class Main {

    public static File file = new File("./src/adjectives.txt");
    public static File file2 = new File("./src/otherList.txt");
    public static File file3 = new File("./src/personAdjectives.txt");

    public static void main(String[] args) {
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
                while (scanner.hasNextLine()) {
                    StringBuilder s = new StringBuilder(scanner.nextLine().toLowerCase());
                    s.replace(0, 1, String.valueOf(Character.toUpperCase(s.charAt(0))));
                    strings.add(s.toString());
                }
            }
            if (file3.exists()) {
                Scanner scanner = new Scanner(file3);
                while (scanner.hasNextLine()) {
                    StringBuilder s = new StringBuilder(scanner.nextLine().toLowerCase());
                    s.replace(0, 1, String.valueOf(Character.toUpperCase(s.charAt(0))));
                    strings.add(s.toString());
                }
            }
//            for (String s : strings) {
//                System.out.println(s);
//            }
            ArrayList<String> array = new ArrayList<>(strings);
            if (strings.size() > 0) {
                String s = array.get(new Random().nextInt(strings.size()));
                System.out.println(s);

                writeToHistory(s);
            }

        } catch(FileNotFoundException e) {
            System.out.println("Error");
        }
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
        toReturn.append(" at ");
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
            writer.append(stringify(new Date())).append(" is :\n\t");
            writer.append(adjective);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}