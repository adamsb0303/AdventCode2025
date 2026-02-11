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
            long biggestX = 0, biggestY = 0;
            while (reader.hasNext()) {
                String[] line = reader.nextLine().split(",");
                Tile newTile = new Tile(Long.parseLong(line[0]), Long.parseLong(line[1]));
                redTiles.add(newTile);
                
                if(newTile.x > biggestX)
                    biggestX = newTile.x;
                if(newTile.y > biggestY)
                    biggestY = newTile.y;
            }

            List<TileEdge> tileEdges = new ArrayList<>();
            long centerX = biggestX / 2;
            long centerY = biggestY / 2;
            for(int i = 0; i < redTiles.size(); i++){
                for(int j = i + 1; j < redTiles.size(); j++){
                    if(redTiles.get(i).x != redTiles.get(j).x && redTiles.get(i).y != redTiles.get(j).y)
                        continue;
                    
                    Tile edgeStart = new Tile(redTiles.get(i).x, redTiles.get(i).y);
                    Tile edgeEnd = new Tile(redTiles.get(j).x, redTiles.get(j).y);
                    
                    TileEdge tileEdge = new TileEdge(edgeStart, edgeEnd);

                    //increase size of polygon by 1 unit   
                    tileEdge.start.x = (centerX < tileEdge.start.x) ? tileEdge.start.x + 1 : tileEdge.start.x - 1;
                    tileEdge.start.y = (centerY < tileEdge.start.y) ? tileEdge.start.y + 1 : tileEdge.start.y - 1;
                    
                    tileEdge.end.x = (centerX < tileEdge.end.x) ? tileEdge.end.x + 1 : tileEdge.end.x - 1;
                    tileEdge.end.y = (centerY < tileEdge.end.y) ? tileEdge.end.y + 1 : tileEdge.end.y - 1;
                    
                    tileEdges.add(tileEdge);
                }
            }

            long biggestArea = 0;
            for(int i = 0; i < redTiles.size(); i++){
                for(int j = i + 1; j < redTiles.size(); j++){
                    Tile tile1 = redTiles.get(i);
                    Tile tile2 = redTiles.get(j);

                    long width = Math.abs(tile1.x - tile2.x) + 1;
                    long height = Math.abs(tile1.y - tile2.y) + 1;
                    
                    if(height * width == 1566346198){
                        System.out.println("THE ANSWER IS " + tile1.x + ", " + tile1.y + " | " + tile2.x + ", " + tile2.y);
                        System.out.println("THE CURRENT HIGHEST IS " + biggestArea);
                    }
                    
                    if(height * width == 2403572472L) {
                        System.out.println("THIS IS THE PROBLEM " + tile1.x + ", " + tile1.y + " | " + tile2.x + ", " + tile2.y);
                    }
                    
                    if(height * width <= biggestArea)
                        continue;
                    
                    Tile fakeTile1 = new Tile(tile1.x , tile2.y);
                    Tile fakeTile2 = new Tile(tile2.x , tile1.y);
                    
                    TileEdge[] areaCheck = {new TileEdge(tile1, fakeTile1), new TileEdge(tile1, fakeTile2), new TileEdge(tile2, fakeTile1), new TileEdge(tile2, fakeTile2)};

                    //Edge is overlapping, but there are valid points past the point of intersection.
                    List<TileEdge> checkOverlapEdge1 = tileEdges.stream().filter(tileEdge -> tileEdge.tileOverlaps(areaCheck[0])).toList();
                    if(!checkOverlapEdge1.isEmpty())
                        continue;

                    List<TileEdge> checkOverlapEdge2 = tileEdges.stream().filter(tileEdge -> tileEdge.tileOverlaps(areaCheck[1])).toList();
                    if(!checkOverlapEdge2.isEmpty()) 
                        continue;

                    List<TileEdge> checkOverlapEdge3 = tileEdges.stream().filter(tileEdge -> tileEdge.tileOverlaps(areaCheck[2])).toList();
                    if(!checkOverlapEdge3.isEmpty()) 
                        continue;

                    List<TileEdge> checkOverlapEdge4 = tileEdges.stream().filter(tileEdge -> tileEdge.tileOverlaps(areaCheck[3])).toList();
                    if(!checkOverlapEdge4.isEmpty()) 
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
    
    public boolean equals(Tile tile){
        return this.x == tile.x && this.y == tile.y;
    }
}

class TileEdge {
    public Tile start;
    public Tile end;
    char direction;
    
    TileEdge(Tile tileA, Tile tileB) {
        boolean direction = tileA.y != tileB.y;
        if(direction){
            start = (tileA.y < tileB.y) ? tileA : tileB;
            end = (tileA.y < tileB.y) ? tileB : tileA;
        }else {
            start = (tileA.x < tileB.x) ? tileA : tileB;
            end = (tileA.x < tileB.x) ? tileB : tileA;
        }
        
        this.direction = (direction) ? 'y' : 'x';
    }
    
    public boolean tileOverlaps(TileEdge tileEdge) {
        if (tileEdge.direction == this.direction) {
            /*if (tileEdge.direction == 'x') {
                if(tileEdge.start.x >= this.start.x && tileEdge.start.x <= this.end.x)
                    return true;
                if(tileEdge.end.x >= this.start.x && tileEdge.end.x <= this.end.x)
                    return true;
            } else {
                if(tileEdge.start.y >= this.start.y && tileEdge.start.y <= this.end.y)
                    return true;
                if(tileEdge.end.y >= this.start.y && tileEdge.end.y <= this.end.y)
                    return true;
            }*/
            return false;
        }
            
        
        if(tileEdge.direction == 'x'){            
            if(tileEdge.start.y > this.end.y || tileEdge.start.y < this.start.y)
                return false;
            else
                return tileEdge.start.x <= this.start.x && tileEdge.end.x >= this.start.x;
        } else {
            if(tileEdge.start.x > this.end.x || tileEdge.start.x < this.start.x)
                return false;
            else
                return tileEdge.start.y <= this.start.y && tileEdge.end.y >= this.start.y;
        }
    }
}
