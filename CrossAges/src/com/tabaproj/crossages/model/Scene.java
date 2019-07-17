package com.tabaproj.crossages.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Scene {

    public final int width;

    public final int height;

    private final Tile[][] tiles;

    public Scene(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }

    private Scene(InputStream input) {
        int width = 0;
        int height = 0;
        Tile[][] tiles = null;
        try {
            Scanner scanner = new Scanner(input);
            int tilesMapCount = scanner.nextInt();
            TileModel[] tilesMap = new TileModel[tilesMapCount];
            for (int i = 0; i < tilesMapCount; i++) {
                tilesMap[i] = TileModel.getTile(scanner.next());
            }
            width = scanner.nextInt();
            height = scanner.nextInt();
            tiles = new Tile[width][height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int mapIndex = scanner.nextInt();
                    tiles[x][y] = new Tile(tilesMap[mapIndex]);
                }
            }

        } catch (Exception ex) {
            System.err.println("Erro, não foi possivel importar um cenario.\n" + ex.getMessage());
        }
        this.width = width;
        this.height = height;
        this.tiles = tiles;
    }

    public void export(PrintStream out) {
        List<TileModel> map = new ArrayList<>();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                if (!map.contains(tiles[x][y])) {
                    map.add(tiles[x][y].getTileModel());
                }
            }
        }
        out.println(map.size());
        for (int i = 0; i < map.size(); i++) {
            out.println(map.get(i));
        }
        out.printf("%d %d\n", width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.printf("%d ", map.indexOf(tiles[x][y]));
            }
            out.println();
        }
    }

    public void export(OutputStream out) {
        export(new PrintStream(out));
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setTile(int cursorX, int cursorY, TileModel actualTile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
