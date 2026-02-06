import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Day02 {
    public static void main(String args[]) {
        System.out.println("Part 1 solution");
        part1();
        System.out.println("Part 2 solution");
        part2();
    }
    
    public static void part1() {
        try {
            //Read the file
            File idFile = new File("Puzzles/Day02.txt");
            Scanner reader = new Scanner(idFile);

            long invalidIdSum = 0;

            String[] idRangeList = reader.nextLine().split(",");
            for(int i = 0; i < idRangeList.length; i++){
                String[] idRange = idRangeList[i].split("-");
                long id1 = Long.parseLong(idRange[0]);
                long id2 = Long.parseLong(idRange[1]);

                int id1Length = idRange[0].length();
                int id2Length = idRange[1].length();

                if (id1Length % 2 == 1) {
                    //If the range is between 2 numbers of the same length with an odd number of digits, e.g. 222-555
                    if (id1Length == id2Length)
                        continue;

                    //if id1 length is odd, round up to the nearest number with even number of digits
                    id1 = (long) Math.pow(10, id1Length);
                    id1Length++;
                }

                while (id1 <= id2) {
                    String idHalf = String.valueOf(id1).substring(0, id1Length / 2);
                    long newNumber = Long.parseLong(idHalf + idHalf);
                    if (newNumber >= id1 && newNumber <= id2)
                        invalidIdSum += newNumber;

                    idHalf = String.valueOf(Long.parseLong(idHalf) + 1);
                    id1 = Long.parseLong(idHalf) * (long) Math.pow(10, idHalf.length());
                }
            }

            System.out.println("Sum of Invalid IDs: " + invalidIdSum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void part2() {
        try {
            //Read the file
            File idFile = new File("Puzzles/Day02.txt");
            Scanner reader = new Scanner(idFile);

            long invalidIdSum = 0;

            String[] idRangeList = reader.nextLine().split(",");
            for(int i = 0; i < idRangeList.length; i++){
                String[] idRange = idRangeList[i].split("-");
                long id1 = Long.parseLong(idRange[0]);
                long id2 = Long.parseLong(idRange[1]);

                HashSet<Long> invalidIds = new HashSet<>();

                while (id1 <= id2) {
                    String id1String = String.valueOf(id1);

                    //Split id1 into x sections of y length and test every number for y length
                    for (int j = id1String.length() / 2; j > 0; j--){
                        //Get the section to duplicate and how many times to duplicate it
                        String newId = id1String.substring(0, j);
                        int sections = id1String.length() / j;
                        for(int k = 0; k < sections - 1; k++)
                            newId += id1String.substring(0,j);

                        //check if new invalid string fits in range
                        long newIdLong = Long.parseLong(newId);
                        if (newIdLong >= id1 && newIdLong <= id2)
                            invalidIds.add(newIdLong);

                        //add 1 to the section of the number we are testing
                        //if the new number is bigger than our range or it rolls over to a number with more digits,
                        //reset the id and start over from a smaller section
                        long nextNumber = (Long.parseLong(id1String.substring(0,j)) + 1) * (long) Math.pow(10, id1String.length() - j);
                        if (String.valueOf(nextNumber).length() > id1String.length() || nextNumber > id2) {
                            id1String = String.valueOf(id1);
                            continue;
                        }

                        id1String = String.valueOf(nextNumber);
                        j++;
                    }

                    //roll id1 to the next digit with more numbers
                    id1 = (long) Math.pow(10, id1String.length());
                }

                System.out.println("[" + idRange[0] + "-" + idRange[1] + "]" + "\n" + invalidIds.toString() + '\n');
                for(long invalidId : invalidIds)
                    invalidIdSum += invalidId;
            }

            System.out.println("Sum of Invalid IDs: " + invalidIdSum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
