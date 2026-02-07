import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class Day09 {
    public static void main(String[] args){
        System.out.println("Part 1 solution");
        part1();
        System.out.println("Part 2 solution");
        part2();
    }
    
    public static void part1() {
        try {
            //Read the file
            File file = new File("Puzzles/Day09.txt");
            Scanner reader = new Scanner(file);

            List<Tile> redTiles = new ArrayList<>();
            while (reader.hasNext()) {
                String[] line = reader.nextLine().split(",");
                redTiles.add(new Tile(Long.parseLong(line[0]), Long.parseLong(line[1])));
            }
            
            Tile[] tileCoords = new Tile[2];
            long biggestArea = 0;
            for(int i = 0; i < redTiles.size(); i++){
                for(int j = i + 1; j < redTiles.size(); j++){
                    long width = Math.abs(redTiles.get(i).x - redTiles.get(j).x) + 1;
                    long height = Math.abs(redTiles.get(i).y - redTiles.get(j).y) + 1;
                    if(height * width <= biggestArea)
                        continue;
                    
                    biggestArea = height * width;
                    tileCoords[0] = redTiles.get(i);
                    tileCoords[1] = redTiles.get(j);
                }
            }

            System.out.println("Biggest Area: " + biggestArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void part2() {
        try {
            //Read the file
            File file = new File("Puzzles/Day09.txt");
            Scanner reader = new Scanner(file);

            List<Tile> redTiles = new ArrayList<>();
            while (reader.hasNext()) {
                String[] line = reader.nextLine().split(",");
                redTiles.add(new Tile(Long.parseLong(line[0]), Long.parseLong(line[1])));
            }

            List<TileEdge> tileEdges = new ArrayList<>();
            for(int i = 0; i < redTiles.size(); i++){
                for(int j = i + 1; j < redTiles.size(); j++){
                    if(redTiles.get(i).x != redTiles.get(j).x && redTiles.get(i).y != redTiles.get(j).y)
                        continue;
                    
                    TileEdge tileEdge = new TileEdge(redTiles.get(i), redTiles.get(j), redTiles.get(i).x == redTiles.get(j).x);
                    tileEdges.add(tileEdge);
                }
            }

            long biggestArea = 0;
            for(int i = 0; i < redTiles.size(); i++){
                for(int j = i + 1; j < redTiles.size(); j++){
                    Tile fakeTile1 = new Tile(redTiles.get(i).x , redTiles.get(j).y);
                    Tile fakeTile2 = new Tile(redTiles.get(j).x , redTiles.get(i).y);;

                    long width = Math.abs(redTiles.get(i).x - redTiles.get(j).x) + 1;
                    long height = Math.abs(redTiles.get(i).y - redTiles.get(j).y) + 1;
                    
                    if(height * width <= biggestArea)
                        continue;
                    if(tileEdges.stream().filter(tileEdge -> tileEdge.containsPoint(fakeTile1)).toList().isEmpty())
                        continue;
                    if(tileEdges.stream().filter(tileEdge -> tileEdge.containsPoint(fakeTile2)).toList().isEmpty())
                        continue;

                    biggestArea = height * width;
                }
            }

            System.out.println("Biggest Area: " + biggestArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Tile {
    public long x;
    public long y;
    
    Tile(long x, long y){
        this.x = x;
        this.y = y;
    }
}

class TileEdge {
    public Tile start;
    public Tile end;
    char direction; 
    
    TileEdge(Tile tileA, Tile tileB, boolean direction) {
        if(direction){
            start = (tileA.y < tileB.y) ? tileA : tileB;
            end = (tileA.y < tileB.y) ? tileB : tileA;
        }else {
            start = (tileA.x < tileB.x) ? tileA : tileB;
            end = (tileA.x < tileB.x) ? tileB : tileA;
        }
        
        this.direction = (direction) ? 'x' : 'y';
    }
    
    public boolean containsPoint(Tile tile){
        if(tile.x < start.x || tile.x > end.x)
            return false;
        if(tile.y < start.y || tile.y > end.y)
            return false;
        return true;
    }
}
