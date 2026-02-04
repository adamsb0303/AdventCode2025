import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day07 {
    public static void part1() {
        try {
            //Read the file
            File file = new File("Puzzles/Day07.txt");
            Scanner reader = new Scanner(file);

            int numOfSplits = 0;
            List<char[]> diagram = new ArrayList<>();
            diagram.add(reader.nextLine().toCharArray());
            while (reader.hasNext()) {
                String line = reader.nextLine();
                
                for(int i = 1; i < line.length() - 1; i++) {
                    if (line.charAt(i) == '^' && diagram.get(diagram.size() - 1)[i] == '|') {
                        line = line.substring(0, i - 1) + "|^|" + line.substring(i + 2);
                        numOfSplits++;
                    }
                    else if (diagram.get(diagram.size() - 1)[i] == 'S' || diagram.get(diagram.size() - 1)[i] == '|')
                        line = line.substring(0, i) + "|" + line.substring(i + 1);
                }
                diagram.add(line.toCharArray());
            }

            System.out.println("Splits: " + numOfSplits);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void part2() {
        try {
            //Read the file
            File file = new File("Puzzles/Day07.txt");
            Scanner reader = new Scanner(file);

            List<Day07Node[]> diagram = new ArrayList<>();
            
            String firstLine = reader.nextLine();
            Day07Node[] firstLineNodes = new Day07Node[firstLine.length()];
            for (int i = 0; i < firstLine.length(); i++) {
                if (firstLine.charAt(i) == 'S')
                    firstLineNodes[i] = new Day07Node('|', 1);
                else 
                    firstLineNodes[i] = new Day07Node(firstLine.charAt(i), 0);
            }
            diagram.add(firstLineNodes);
            
            while (reader.hasNext()) {
                String line = reader.nextLine();
                Day07Node[] nodeLine = new Day07Node[line.length()];
                for (int i = 0; i < line.length(); i++){
                    Day07Node nodeAbove = diagram.get(diagram.size() - 1)[i];
                    if (line.charAt(i) == '^' && nodeAbove.value == '|') {
                        nodeLine[i] = new Day07Node(line.charAt(i), 0);
                        //left
                        nodeLine[i - 1].setValue('|');
                        nodeLine[i - 1].addTimeline(nodeAbove.getTimelines());
                        //right
                        nodeLine[i + 1] = new Day07Node('|', nodeAbove.getTimelines());
                    }
                    else if (nodeAbove.value == '|')
                        if(nodeLine[i] == null)
                            nodeLine[i] = new Day07Node('|', nodeAbove.getTimelines());
                        else 
                            nodeLine[i].addTimeline(nodeAbove.getTimelines());
                    else if (nodeLine[i] == null)
                        nodeLine[i] = new Day07Node(line.charAt(i), 0);
                }
                diagram.add(nodeLine);
            }

            long totalTimelines = 0;
            for (int i = 0; i < diagram.get(diagram.size() - 1).length; i++){
                totalTimelines += diagram.get(diagram.size() - 1)[i].getTimelines();
            }
            System.out.println("Timelines: " + totalTimelines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**Could work, but is taking way too long to run on problem dataset.**/
    private static long calculateTimelines(List<char[]> diagram, long timelines){
        if (diagram.size() == 1) {
            for(int i = 0; i < diagram.get(0).length; i++)
                if(diagram.get(0)[i] == '|') {
                    System.out.println("New Timeline Found - " + timelines + 1);
                    return timelines + 1;
                }
            return timelines;
        }
        
        char[] above = diagram.get(0);
        char[] current = diagram.get(1);
        diagram.remove(0);
        for (int i = 0; i < above.length; i++) {
            if (above[i] != '|') {
                if (current[i] == '|')
                    current[i] = '.';
                
                continue;
            }
            
            if (current[i] != '^') {
                current[i] = '|';
                continue;
            }
            List<char[]> newDiagram;
            
            current[i - 1] = '|';
            newDiagram = new ArrayList<>(diagram);
            newDiagram.set(0, current);
            timelines = calculateTimelines(newDiagram, timelines);
            current[i - 1] = '.';
            
            current[i + 1] = '|';
            newDiagram = new ArrayList<>(diagram);
            newDiagram.set(0, current);
            timelines = calculateTimelines(newDiagram, timelines);
            current[i + 1] = '.';
        }
               
        return calculateTimelines(diagram, timelines);
    }
}

class Day07Node {
    public char value;
    public long timelines;
    
    public Day07Node(char value, long timelines) {
        this.value = value;
        this.timelines = timelines;
    }
    
    public void setValue(char value){
        this.value = value;
    }
    
    public void setTimelines(long timelines) {
        this.timelines = timelines;
    }
    
    public void addTimeline(long timelines) {
        this.timelines += timelines;
    }
    
    public long getTimelines() {
        return this.timelines;
    }
}