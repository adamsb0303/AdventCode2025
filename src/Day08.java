import java.io.File;
import java.io.IOException;
import java.util.*;

public class Day08 {
    public static void main(String args[]) {
        System.out.println("Part 1 solution");
        part1();
        System.out.println("Part 2 solution");
        part2();
    }
    
    public static void part1() {
        try {
            //Read the file
            File file = new File("Puzzles/Day08.txt");
            Scanner reader = new Scanner(file);

            List<Junction> junctionBoxes = new ArrayList<>();
            int junctionId = 1;
            while (reader.hasNext()) {
                String[] coords = reader.nextLine().split(",");

                junctionBoxes.add(new Junction(junctionId, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])));
                junctionId++;
            }
            
            Map<Junction[], Double> junctionVectors = new HashMap<>();
            for (int i = 0; i < junctionBoxes.size(); i++) {
                for (int j = i + 1; j < junctionBoxes.size(); j++) {
                    double newDistance = findDistance(junctionBoxes.get(i), junctionBoxes.get(j));
                    junctionVectors.put(new Junction[]{junctionBoxes.get(i), junctionBoxes.get(j)}, newDistance);
                }
            }
            
            List<Junction[]> sortedJunctions = 
                    junctionVectors.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue())
                            .map(Map.Entry::getKey)
                            .toList()
                            .subList(0, 1000);

            
            for (int i = 0; i < 1000; i++) {
                Junction[] currentVector = sortedJunctions.get(i);
                
                Junction junctionA = currentVector[0];
                Junction junctionB = currentVector[1];
                
                if(junctionA.circuit != junctionB.circuit) {
                    junctionA.circuit.addAll(junctionB.circuit);
                    
                    for(Junction junction : junctionB.circuit) {
                        junction.circuit = junctionA.circuit;
                    }
                }
            }
            
            long answer = 1;
            List<Integer> largestCircuits = 
                    junctionBoxes.stream()
                            .map(junction -> junction.circuit)
                            .distinct()
                            .map(Set::size)
                            .sorted(Comparator.reverseOrder())
                            .toList();
            
            for(int i = 0; i < 3; i++) {
                answer *= largestCircuits.get(i);
            }
            System.out.println("Answer: " + answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void part2() {
        try {
            //Read the file
            File file = new File("Puzzles/Day08.txt");
            Scanner reader = new Scanner(file);

            List<Junction> junctionBoxes = new ArrayList<>();
            int junctionId = 1;
            while (reader.hasNext()) {
                String[] coords = reader.nextLine().split(",");

                junctionBoxes.add(new Junction(junctionId, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])));
                junctionId++;
            }

            Map<Junction[], Double> junctionVectors = new HashMap<>();
            for (int i = 0; i < junctionBoxes.size(); i++) {
                for (int j = i + 1; j < junctionBoxes.size(); j++) {
                    double newDistance = findDistance(junctionBoxes.get(i), junctionBoxes.get(j));
                    junctionVectors.put(new Junction[]{junctionBoxes.get(i), junctionBoxes.get(j)}, newDistance);
                }
            }

            List<Junction[]> sortedJunctions =
                    junctionVectors.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue())
                            .map(Map.Entry::getKey)
                            .toList();


            for (int i = 0; i < junctionVectors.size(); i++) {
                Junction[] currentVector = sortedJunctions.get(i);

                Junction junctionA = currentVector[0];
                Junction junctionB = currentVector[1];
                
                if(junctionA.circuit != junctionB.circuit) {
                    junctionA.circuit.addAll(junctionB.circuit);

                    for(Junction junction : junctionB.circuit) {
                        junction.circuit = junctionA.circuit;
                    }
                    
                    if(junctionA.circuit.size() == junctionBoxes.size()) {
                        System.out.println("Answer: " + ((long) junctionA.x * (long) junctionB.x));
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    private static double findDistance(Junction a, Junction b){
        return Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2) + Math.pow((a.z - b.z), 2));
    }
}

class Junction {
    public int id;
    public int x;
    public int y;
    public int z;
    public Set<Junction> circuit;
    
    public Junction closestJunction;

    public Junction(int id, int x, int y, int z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        
        circuit = new HashSet<>();
        circuit.add(this);
    }

    public void setClosestJunction(Junction closestJunction) {
        this.closestJunction = closestJunction;
    }
}
