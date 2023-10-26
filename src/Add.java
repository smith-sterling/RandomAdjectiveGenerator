import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Add {

    private static final File file =
            new File("/Users/sterlite/Desktop/PetProjects/randomAdjectiveGenerator/src/masterList.txt");

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {System.out.println("No arguments"); return;}
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
        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) strings.add(capFirstLetter(scanner.nextLine()));
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
//            File myObj = new File("/Users/sterlite/Desktop/PetProjects/randomAdjectiveGenerator/src/testing.txt");
//            if (myObj.createNewFile()) {
//                System.out.println("File created: " + myObj.getName());
//            }

            List<String> list = new ArrayList<>(words);
            FileWriter writer = new FileWriter(file, false);
            for(int i = 0; i < list.size(); ++i) {
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
