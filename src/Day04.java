import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Day04 {
    public static void main(String args[]) {
        System.out.println("Part 1 solution");
        part1();
        System.out.println("Part 2 solution");
        part2();
    }
    
    public static void part1(){
        try {
            //Read the file
            File paperRolls = new File("Puzzles/Day04.txt");
            Scanner reader = new Scanner(paperRolls);

            Vector<Vector<Character>> shelves = new Vector<>();
            while (reader.hasNext()) {
                String line = reader.nextLine();
                Vector<Character> row = new Vector<>();
                for (char c : line.toCharArray())
                    row.add(c);
                shelves.add(row);
            }

            int availableRolls = 0;

            for(int i = 0; i < shelves.size(); i++) {
                for (int j = 0; j < shelves.get(0).size(); j++){
                    if(shelves.get(i).get(j) == '.')
                        continue;

                    // -1, -1| -1, 0| -1, +1
                    //  0, -1|  0, 0|  0, +1
                    // +1, -1| +1, 0| +1, +1
                    int adjacentRolls = 0;
                    for(int y = -1; y <= 1; y++){
                        for(int x = -1; x <= 1; x++){
                            //skip center
                            if(y == 0 && x == 0)
                                continue;

                            //out of bounds check
                            if(i + y < 0 || i + y >= shelves.size())
                                continue;
                            if(j + x < 0 || j + x >= shelves.get(0).size())
                                continue;

                            if(shelves.get(i+y).get(j+x) == '@' || shelves.get(i+y).get(j+x) == 'x')
                                adjacentRolls++;
                        }
                    }
                    if(adjacentRolls < 4) {
                        shelves.get(i).set(j, 'x');
                        availableRolls++;
                    }
                }
            }
            System.out.println("Available Rolls: " + availableRolls);
            for(int i = 0; i < shelves.size(); i++) {
                for (int j = 0; j < shelves.get(0).size(); j++)
                    System.out.print(shelves.get(i).get(j));
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void part2(){
        try {
            //Read the file
            File paperRolls = new File("Puzzles/Day04.txt");
            Scanner reader = new Scanner(paperRolls);

            Vector<Vector<Character>> shelves = new Vector<>();
            while (reader.hasNext()) {
                String line = reader.nextLine();
                Vector<Character> row = new Vector<>();
                for (char c : line.toCharArray())
                    row.add(c);
                shelves.add(row);
            }

            int availableRolls;
            int totalRolls = 0;

            do {
                availableRolls = 0;
                for (int i = 0; i < shelves.size(); i++) {
                    for (int j = 0; j < shelves.get(0).size(); j++) {
                        if (shelves.get(i).get(j) == '.')
                            continue;

                        // -1, -1| -1, 0| -1, +1
                        //  0, -1|  0, 0|  0, +1
                        // +1, -1| +1, 0| +1, +1
                        int adjacentRolls = 0;
                        for (int y = -1; y <= 1; y++) {
                            for (int x = -1; x <= 1; x++) {
                                //skip center
                                if (y == 0 && x == 0)
                                    continue;

                                //out of bounds check
                                if (i + y < 0 || i + y >= shelves.size())
                                    continue;
                                if (j + x < 0 || j + x >= shelves.get(0).size())
                                    continue;

                                if (shelves.get(i + y).get(j + x) == '@')
                                    adjacentRolls++;
                            }
                        }
                        if (adjacentRolls < 4) {
                            shelves.get(i).set(j, '.');
                            availableRolls++;
                        }
                    }
                }
                totalRolls += availableRolls;
            } while(availableRolls > 0);
            System.out.println("Available Rolls: " + totalRolls);
            for (int i = 0; i < shelves.size(); i++) {
                for (int j = 0; j < shelves.get(0).size(); j++)
                    System.out.print(shelves.get(i).get(j));
                System.out.println();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
