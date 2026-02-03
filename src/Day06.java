import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day06 {
    public static void part1() {
        try {
            //Read the file
            File file = new File("Puzzles/Day06.txt");
            Scanner reader = new Scanner(file);

            List<String[]> worksheet = new ArrayList<>();
            while (reader.hasNext()) {
                String line = reader.nextLine();
                worksheet.add(line.split("\\s+"));
            }

            long grandTotal = 0;
            for(int i = 0; i < worksheet.get(0).length; i++) {
                char operation = worksheet.get(worksheet.size() - 1)[i].toCharArray()[0];
                long total = Long.parseLong(worksheet.get(0)[i]);
                for (int j = 1; j < worksheet.size() - 1; j++) {
                    switch (operation) {
                        case '+':
                            total += Integer.parseInt(worksheet.get(j)[i]);
                            break;
                        case '*':
                            total *= Integer.parseInt(worksheet.get(j)[i]);
                            break;
                    }
                }
                grandTotal += total;
            }

            System.out.println("Sum of answers: " + grandTotal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void part2() {
        try {
            //Read the file
            File file = new File("Puzzles/Day06.txt");
            Scanner reader = new Scanner(file);

            List<char[]> worksheet = new ArrayList<>();
            while (reader.hasNext()) {
                String line = reader.nextLine();
                if(line.charAt(0) != '*' && line.charAt(0) != '+')
                    worksheet.add(line.toCharArray());
                else
                    worksheet.add(line.replaceAll("\\s+", "").toCharArray());
            }

            long grandTotal = 0;
            long total = 0;
            int equationNumber = 1;
            for(int i = worksheet.get(0).length - 1; i >= 0; i--) {
                char operation = worksheet.get(worksheet.size() - 1)[worksheet.get(worksheet.size() - 1).length - equationNumber];
                String number = "";
                for (int j = 0; j < worksheet.size() - 1; j++) {
                    if(worksheet.get(j)[i] == ' ')
                        continue;

                    number += worksheet.get(j)[i];
                }
                if(number == "") {
                    grandTotal += total;
                    total = 0;
                    equationNumber++;
                    continue;
                }
                switch (operation) {
                    case '+':
                        total += Integer.parseInt(number);
                        break;
                    case '*':
                        if (total == 0)
                            total = Integer.parseInt(number);
                        else
                            total *= Integer.parseInt(number);
                        break;
                }
                if (i == 0)
                    grandTotal += total;
            }

            System.out.println("Sum of answers: " + grandTotal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
