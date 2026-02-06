import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day05 {
    public static void main(String args[]) {
        System.out.println("Part 1 solution");
        part1();
        System.out.println("Part 2 solution");
        part2();
    }
    
    public static void part1() {
        try {
            //Read the file
            File safeCombo = new File("Puzzles/Day05.txt");
            Scanner reader = new Scanner(safeCombo);

            List<Long[]> freshIngredients = new ArrayList<>();
            int totalFreshIngredients = 0;

            while (reader.hasNext()) {
                String line = reader.nextLine();
                if (line.isEmpty())
                    break;
                Long[] ingredientRange = {Long.valueOf(line.split("-")[0]), Long.valueOf(line.split("-")[1])};
                freshIngredients.add(ingredientRange);
            }

            while (reader.hasNext()) {
                long ingredientId = Long.parseLong(reader.nextLine());
                for (Long[] ranges : freshIngredients) {
                    if (ingredientId >= ranges[0] && ingredientId <= ranges[1]) {
                        totalFreshIngredients++;
                        break;
                    }
                }
            }

            System.out.println("Total Fresh Ingredients: " + totalFreshIngredients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void part2() {
        try {
            //Read the file
            File safeCombo = new File("Puzzles/Day05.txt");
            Scanner reader = new Scanner(safeCombo);

            List<Long[]> freshIngredients = new ArrayList<>();

            while (reader.hasNext()) {
                String line = reader.nextLine();
                if (line.isEmpty())
                    break;
                Long[] ingredientRange = {Long.valueOf(line.split("-")[0]), Long.valueOf(line.split("-")[1])};
                freshIngredients.add(ingredientRange);
            }
            for (int i = 0; i < freshIngredients.size(); i++){
                for (int j = 0; j < freshIngredients.size(); j++) {
                    if(i == j)
                        continue;
                    //lower value falls in range
                    if(freshIngredients.get(i)[0] >= freshIngredients.get(j)[0] && freshIngredients.get(i)[0] <= freshIngredients.get(j)[1]){
                        if(freshIngredients.get(i)[1] >= freshIngredients.get(j)[1])
                            freshIngredients.set(j, new Long[]{freshIngredients.get(j)[0], freshIngredients.get(i)[1]});

                        freshIngredients.remove(i);
                        i = -1;
                        break;
                    }
                    //upper value falls in range
                    if(freshIngredients.get(i)[1] >= freshIngredients.get(j)[0] && freshIngredients.get(i)[1] <= freshIngredients.get(j)[1]){
                        if(freshIngredients.get(i)[0] <= freshIngredients.get(j)[0])
                            freshIngredients.set(j, new Long[]{freshIngredients.get(i)[0], freshIngredients.get(j)[1]});

                        freshIngredients.remove(i);
                        i = -1;
                        break;
                    }
                }
            }

            long totalFreshIngredients = 0;
            for (int i = 0; i < freshIngredients.size(); i++)
                totalFreshIngredients += freshIngredients.get(i)[1] - freshIngredients.get(i)[0] + 1;


            System.out.println("Total Fresh Ingredients: " + totalFreshIngredients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
