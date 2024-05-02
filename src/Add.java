import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Add {

    private static final File masterListFile = new File("masterList.txt");

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("No arguments");
            return;
        } else if (!masterListFile.exists()) {
            System.out.println("master list not found. Exiting...");
            return;
        }

        TreeSet<String> words = setOfWords();
        Set<String> toAdd = new HashSet<>();
        for (String word : args) {
            String s = capFirstLetter(word.toLowerCase());
            if (!words.contains(s)) {
                toAdd.add(s);
            }
        }

        if (toAdd.isEmpty()) {
            if (args.length == 1) System.out.println("Word already present");
            else System.out.println("Words already present");
            return;
        }

        words.addAll(toAdd);

        writeToMasterList(words);
        System.out.println((toAdd.size() == 1 ? "One word added: " : toAdd.size() + " words added: ") +
                           String.join(", ", toAdd));
    }

    private static TreeSet<String> setOfWords() throws Exception {
        TreeSet<String> strings = new TreeSet<>();
        if (masterListFile.exists()) {
            Scanner scanner = new Scanner(masterListFile);
            while (scanner.hasNextLine()) strings.add(capFirstLetter(scanner.nextLine()));
        }
        return strings;
    }

    private static String capFirstLetter(String string) {
        StringBuilder s = new StringBuilder(string.strip().toLowerCase());
        s.replace(0, 1, String.valueOf(Character.toUpperCase(s.charAt(0))));
        return s.toString();
    }

    private static void writeToMasterList(TreeSet<String> words) {
        try {
            List<String> list = new ArrayList<>(words);
            FileWriter writer = new FileWriter(masterListFile, false);
            for (int i = 0; i < list.size(); ++i) {
                writer.append(list.get(i));
                if (i != list.size() - 1) writer.append("\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
