package pack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static File file = new File("./src/pack/adjectives.txt");

    public static void main(String[] args) {
        try {
            ArrayList<String> strings = new ArrayList<>();
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    strings.add(scanner.nextLine());
                }
                System.out.println(strings.get(new Random().nextInt(strings.size())));
            }
        } catch(FileNotFoundException e) {
            System.out.println("Error");
        }
    }
}