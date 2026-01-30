import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Day01 {
    public static void part1() {
        try {
            //Read the file
            File safeCombo = new File("Puzzles/Day01.txt");
            Scanner reader = new Scanner(safeCombo);

            int dial = 50;
            int password = 0;
            while (reader.hasNext()) {
                String line = reader.nextLine();
                char direction = line.charAt(0);
                int distance = Integer.parseInt(line.substring(1));

                switch (direction) {
                    case 'R':
                        dial = (100 + (dial + distance)) % 100;
                        break;
                    case 'L':
                        dial = (100 + (dial - distance)) % 100;
                        break;
                }

                if(dial == 0)
                    password++;
            }

            System.out.println("Password: " + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void part2() {
        try{
            //Read the file
            File safeCombo = new File("Puzzles/Day01.txt");
            Scanner reader = new Scanner(safeCombo);

            int dial = 50;
            int password = 0;
            while (reader.hasNext()) {
                String line = reader.nextLine();
                char direction = line.charAt(0);
                int distance = Integer.parseInt(line.substring(1));

                password += distance / 100;
                distance = distance % 100;

                switch (direction) {
                    case 'R':
                        dial = dial + distance;
                        break;
                    case 'L':
                        dial = dial - distance;
                        break;
                }

                if((dial >= 100 || dial <= 0) && distance != Math.abs(dial))
                    password++;

                dial = (100 + dial) % 100;
            }

            System.out.println("Password: " + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
