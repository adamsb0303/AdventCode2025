import java.io.File;
import java.io.IOException;
import java.util.*;

public class Day08 {
    public static void part1() {
        try {
            //Read the file
            File file = new File("Puzzles/Day08.txt");
            Scanner reader = new Scanner(file);

            List<Junction> junctionBoxes = new ArrayList<>();
            char junctionId = 'A';
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

            List<List<Character>> connectedCircuits = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                Junction[] smallestVector = new Junction[2];
                double closestDistance = Double.MAX_VALUE;
                for (Map.Entry<Junction[], Double> entry : junctionVectors.entrySet()) {
                    if (entry.getValue() < closestDistance) {
                        smallestVector = entry.getKey();
                        closestDistance = entry.getValue();
                    }
                }
                Junction junctionA = smallestVector[0];
                Junction junctionB = smallestVector[1];
                
                if(junctionA.connected && junctionB.connected){
                    if(junctionA.circuitNumber != junctionB.circuitNumber) {
                        int largerIndex = (junctionA.circuitNumber > junctionB.circuitNumber) ? 0 : 1;
                        int smallerIndex = Math.abs(largerIndex - 1);
                        connectedCircuits.get(smallestVector[smallerIndex].circuitNumber - 1).addAll(connectedCircuits.get(smallestVector[largerIndex].circuitNumber - 1));
                        connectedCircuits.set(smallestVector[largerIndex].circuitNumber - 1, new ArrayList<>());
                        smallestVector[largerIndex].circuitNumber = smallestVector[smallerIndex].circuitNumber;
                    }
                }
                else if(!junctionA.connected && !junctionB.connected) {
                    junctionA.connected = true;
                    junctionB.connected = true;
                    
                    connectedCircuits.add(new ArrayList<>());
                    junctionA.circuitNumber = connectedCircuits.size();
                    junctionB.circuitNumber = connectedCircuits.size();
                    connectedCircuits.get(connectedCircuits.size() - 1).add(junctionA.id);
                    connectedCircuits.get(connectedCircuits.size() - 1).add(junctionB.id);
                }
                else {
                    int connectedIndex = (junctionA.connected) ? 0 : 1;
                    int disconnectedIndex = (junctionA.connected) ? 1 : 0;
                    
                    smallestVector[disconnectedIndex].connected = true;
                    smallestVector[disconnectedIndex].circuitNumber = smallestVector[connectedIndex].circuitNumber;
                    
                    connectedCircuits.get(smallestVector[connectedIndex].circuitNumber - 1).add(smallestVector[disconnectedIndex].id);
                }
                junctionVectors.remove(smallestVector);
            }
            
            long answer = 1;
            List<Integer> largestCircuits = 
                    connectedCircuits.stream()
                            .map(List::size)
                            .sorted()
                            .toList();
            for(int i = 1; i <= 3; i++) {
                System.out.println(largestCircuits.get(largestCircuits.size() - i));
                answer *= largestCircuits.get(largestCircuits.size() - i);
            }
            System.out.println(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static double findDistance(Junction a, Junction b){
        return Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2) + Math.pow((a.z - b.z), 2));
    }
}

class Junction {
    public char id;
    public int x;
    public int y;
    public int z;
    public boolean connected = false;
    public int circuitNumber = -1;
    
    public Junction closestJunction;

    public Junction(char id, int x, int y, int z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setClosestJunction(Junction closestJunction) {
        this.closestJunction = closestJunction;
    }
}
