package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.unit.Movement;
import ru.itmo.rogue.model.unit.Position;

import java.io.*;
import java.util.*;
import java.util.List;

public class MapBuilder {
    public static final int DEFAULT_WIDTH = 16;
    public static final int DEFAULT_HEIGHT = 12;
    public static final int MIN_ROOM_WIDTH = 3;
    public static final int MIN_ROOM_HEIGHT = 3;
    public static final int MAX_ROOM_WIDTH = 100;
    public static final int MAX_ROOM_HEIGHT = 20;

    public static final double PROBABILITY_TREASURE_ROOM = 0.2;
    private String filename;
    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;

    private Parameters params;
    private final Random rand = new Random();
    /**
     * Map will be loaded from disk and not generated, overrides all other options
     * @param filename name of the file that contains map
     */
    public MapBuilder loadFromDisk(String filename) {
        this.filename = filename;
        return this;
    }

    /**
     * Changes width of generated map, by default, see: DEFAULT_WIDTH
     * @param width width of generated map
     */
    public MapBuilder width(int width) {
        assert width >= MIN_ROOM_WIDTH && width <= MAX_ROOM_WIDTH;
        this.width = width;
        return this;
    }


    /**
     * Changes height of generated map, by default, see: DEFAULT_HEIGHT
     * @param height width of generated map
     */
    public MapBuilder height(int height) {
        assert height >= MIN_ROOM_HEIGHT && height <= MAX_ROOM_HEIGHT;
        this.height = height;
        return this;
    }

    /**
     * Generates map with set parameters
     * @return Map object
     */
    public Map build() {
        if (filename != null) {
            return buildFromDisk();
        }

        params = new Parameters(4, width, height);

        return generateMap();
    }

    private Map buildFromDisk() {
        try (FileInputStream fileInputStream = new FileInputStream(filename);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (Map) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error occured while map loading: " + e.getMessage());
        }
        return null;
    }

    private void saveNewMaps(int number) {
        for (int i = 0; i < number; i++) {
            var map = generateMap();
            try(
                    FileOutputStream fileOutputStream
                            = new FileOutputStream(String.format("./app/src/main/resources/complex%d.map", i));
                    ObjectOutputStream objectOutputStream
                            = new ObjectOutputStream(fileOutputStream);) {
                objectOutputStream.writeObject(map);
            } catch (IOException e) {
                System.err.println("Error occurred during serializaztion: " + e.getMessage());
            }
        }
    }
    private Map generateMap() {
        Map map = new Map(width, height);
        // dividing space and creating subrooms
        List<Position> corePoints = new Partition(width, height, params.depth, 5).getCenters();
        boolean isEntrance = true;
        for (var point : corePoints) {
            generateAndFillSubRoom(map, point, isEntrance);
            isEntrance = false;
        }
        // check whether all the subrooms is accessible from entry point
        createCorridors(map, corePoints);

        // creating doors out
        map.setTile(generateDoor(map), MapView.Tile.DOOR_OUT_NORMAL);
        map.setTile(generateDoor(map), MapView.Tile.DOOR_OUT_HARD);
        if (rand.nextDouble() < PROBABILITY_TREASURE_ROOM) {
            map.setTile(generateDoor(map), MapView.Tile.DOOR_OUT_TREASURE_ROOM);
        }
        return map;
    }
    private class Partition {
        private final int minimumDimension = 5;
        private final int width;
        private final int height;
        private final Position originPos;
        private final boolean isLeaf;
        private final Partition left;
        private final Partition right;

        Partition(int width, int height, int depth, int variance) {
            this(width, height, new Position(0, 0), depth, variance);
        }
        Partition(int width, int height, Position originPos, int depth, int variance) {
            this.width = width;
            this.height = height;
            this.originPos = originPos;
            if (depth > 0 && width > minimumDimension && height > minimumDimension) {
                var axis = (rand.nextInt(0, width + height) > width) ? Axis.HORIZONTAL : Axis.VERTICAL;
                this.isLeaf = false;
                depth--;
                if (axis == Axis.VERTICAL) {
                    var delimiter = rand.nextInt(width / 2 - variance, width / 2 + variance);
                    left = new Partition(delimiter, height, originPos, depth, variance);
                    right = new Partition(width - delimiter, height, new Position(delimiter, originPos.y()), depth, variance);
                } else {
                    var delimiter = rand.nextInt(height / 2 - variance, height / 2 + variance);
                    left = new Partition(width, delimiter, originPos, depth, variance);
                    right = new Partition(width, height - delimiter, new Position(originPos.x(), delimiter), depth, variance);
                }
            } else {
                this.isLeaf = true;
                left = null;
                right = null;
            }
        }

        private List<Position> getCenters() {
            var centers = new ArrayList<Position>();
            traverse(centers);
            return centers;
        }
        private void traverse(List<Position> centers) {
            if (isLeaf) {
                centers.add(new Position(originPos.x() + width / 2,  originPos.y() + height / 2));
            } else {
                left.traverse(centers);
                right.traverse(centers);
            }
        }

    }

    private void generateAndFillSubRoom(Map map, Position corePoint, boolean isEntrance) {
        int width = rand.nextInt(params.minWidth, params.maxWidth) / 2;
        int height = rand.nextInt(params.minHeight, params.maxHeight) / 2;

        Position top = new Position(Math.max(corePoint.x() - width, 1),
                Math.min(corePoint.y() + height, map.getHeight() - 1));
        Position bottom =  new Position(Math.min(corePoint.x() + width, map.getWidth() - 1),
                Math.max(corePoint.y() - height, 1));
        for (int x = top.x(); x < bottom.x(); x++) {
            for (int y = bottom.y(); y < top.y(); y++) {
                map.setTile(new Position(x, y), MapView.Tile.FLOOR);
            }
        }
        if (isEntrance) {
            map.setTile(generateEntrance(new Position(top.x() - 1, top.y() / 2)), MapView.Tile.DOOR_IN);
        }
    }
    private Position generateEntrance(Position pos) {
//        return (entrySide == EntrySide.EAST) ? new Position(width + 1, height / 2)
//        : new Position(0, height / 2);
        return pos;
    }

    private void createCorridors(Map map, List<Position> corePoints) {
        List<Integer> components = new ArrayList<>();
        int nComponents = getComponents(map, corePoints, components);
        if (nComponents == 0) {
            return;
        }
        for (int roomIdx = 1; roomIdx < components.size(); roomIdx++) {
            // entrance subroom is the zero
            if (components.get(roomIdx) !=  0) {
                // make the corridor
                var path = map.getPossiblePath(map.getEntrance(), corePoints.get(roomIdx));
                for (var coord : path) {
                    if (map.isNotBorderWall(coord)) {
                        map.setTile(coord, MapView.Tile.FLOOR);
                    }
                    for (var movement : Movement.defaults) {
                        var newCoord = coord.move(movement);
                        if (map.isNotBorderWall(newCoord)) {
                            map.setTile(newCoord, MapView.Tile.FLOOR);
                        }
                    }
                }
            }
        }

    }

    /**
     generates door and checks if the player can reach ot from entrance
     * @param map map of the level to check reachability
     * @return position of the generated door
     */
    private Position generateDoor(Map map) {
        var walls = map.getDistance(map.getEntrance(), new Position(width - 1, height - 1)).reachableWalls();
        int randomPos = rand.nextInt(0, walls.size());
        return walls.get(randomPos);
    }

    /**
     *
     * @param centers of generated rooms
     * @return list where idx is the number of center and value is the number of component
     */
    private int getComponents(Map map, List<Position> centers, List<Integer> components) {
        var entrance = centers.get(0);
        int nComponent = 0;
        components.clear();
        components.add(nComponent);
        Set<Position> visited = new HashSet<>();
        for (int i = 1; i < centers.size(); i++) {
            var center = centers.get(i);
            if (!visited.contains(center)) {
                var info = map.getDistance(center, entrance);
                nComponent++;
                visited.addAll(info.reachableFloors());

            }
            components.add(nComponent);
        }
        return nComponent;
    }


    enum Axis {
        HORIZONTAL,
        VERTICAL, NONE
    }
    private class Parameters {
        public final int depth;
        public final int numberOfSubRooms;
        public final int minWidth;
        public final int maxWidth;
        public final int minHeight;
        public final int maxHeight;
        /**
         * Calculates parameters for map generation
         * @param depth is the depth of partition
         * @param width of the map
         * @param height of the map
         */
        Parameters(int depth, int width, int height) {
            this.depth = depth;
            this.numberOfSubRooms = (int)Math.pow(2, depth);
//            int squareDepth = 1;
            int squareDepth = depth * depth;
            var deltaWidth = Math.max(width / squareDepth, squareDepth);
            this.minWidth = deltaWidth / 3;
            this.maxWidth = deltaWidth;

            var deltaHeight = Math.max(height / squareDepth, squareDepth);
            this.minHeight = deltaHeight / 3;
            this.maxHeight = deltaHeight;
        }
    }
}
