import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Day03 {
    public static void main(String args[]) {
        System.out.println("Part 1 solution");
        part1();
        System.out.println("Part 2 solution");
        part2();
    }
    
    public static void part1() {
        try {
            //Read the file
            File batteryBanksFile = new File("Puzzles/Day03.txt");
            Scanner reader = new Scanner(batteryBanksFile);

            long joltageSum = 0;

            while (reader.hasNext()) {
                String line = reader.nextLine();
                int[] batteryIndexes = {0,1};

                for (int i = 1; i < line.length(); i++) {
                    if (line.charAt(batteryIndexes[0]) < line.charAt(i) && i < line.length() - 1){
                        batteryIndexes[0] = i;
                        batteryIndexes[1] = i + 1;
                        continue;
                    }

                    if (line.charAt(batteryIndexes[1]) < line.charAt(i)) {
                        batteryIndexes[1] = i;
                    }
                }
                joltageSum += Character.getNumericValue(line.charAt(batteryIndexes[0])) * 10L + Character.getNumericValue(line.charAt(batteryIndexes[1]));
            }

            System.out.println("Total Joltage: " + joltageSum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void part2() {
        try {
            //Read the file
            File batteryBanksFile = new File("Puzzles/Day03.txt");
            Scanner reader = new Scanner(batteryBanksFile);

            long joltageSum = 0;

            while (reader.hasNext()) {
                String line = reader.nextLine();
                int[] batteryIndexes = {0,1,2,3,4,5,6,7,8,9,10,11};

                for (int i = 1; i < line.length(); i++) {
                    for (int j = 0; j < batteryIndexes.length; j++) {
                        if (i > line.length() - (batteryIndexes.length - j))
                            continue;

                        if(i == batteryIndexes[j])
                            break;

                        if (line.charAt(batteryIndexes[j]) < line.charAt(i)){
                            for (int k = 0; j <= batteryIndexes.length - 1; k++){
                                batteryIndexes[j] = i + k;
                                j++;
                            }
                            break;
                        }
                    }
                }
                for(int i = 0; i < batteryIndexes.length; i++)
                    joltageSum += (long) (Character.getNumericValue(line.charAt(batteryIndexes[i])) * Math.pow(10, batteryIndexes.length-1-i));
            }

            System.out.println("Total Joltage: " + joltageSum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
