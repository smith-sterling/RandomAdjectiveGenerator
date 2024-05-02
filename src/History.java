import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class History {

    private static final File historyFile = new File("history.txt");

    public static void main(String[] args) {
        if (!historyFile.exists()) {
            System.out.println("historyFile not found. Exiting...");
            return;
        }

        int val = 8;
        for (String arg : args) {
            try {
                val = Integer.parseInt(arg);
                break;
            } catch (NumberFormatException ignore) { }
        }

//    System.out.printf("val = %d%n", val);

        try(Scanner reader = new Scanner(historyFile)) {
            List<String> history = new ArrayList<>();
            while (reader.hasNext()) history.add(reader.nextLine());
            if (val * 2 > history.size()) {
                val = history.size() / 2;
                System.out.println("There are only " + history.size() / 2 + " words...");
                Thread.sleep(1000);
                System.out.println("But here you go...");
                Thread.sleep(1000);
            }
            for (int i = history.size() - 2 * val; i < history.size(); ++i) {
                System.out.println(history.get(i));
            }
        } catch (FileNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
