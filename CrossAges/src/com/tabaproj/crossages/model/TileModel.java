package com.tabaproj.crossages.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class TileModel {

    private static final List<TileModel> models = importTiles();

    public static final int WIDTH = 16;

    public static final int HEIGHT = 16;

    public static List<TileModel> importTiles() {
        List<TileModel> tiles = new ArrayList<>();
        File files = new File("CrossAges/data/tiles");
        if (!files.exists() || files.isFile()) {
            files.mkdirs();
        } else {
            for (File f : files.listFiles()) {
                if (f.isFile() && f.canRead()) {
                    try {
                        TileModel tile = new TileModel(new FileInputStream(f));
                        tiles.add(tile);
                    } catch (IOException ex) {

                    }
                }
            }
        }
        return tiles;
    }

    public static TileModel getTile(String tile) {
        for (int i = 0; i < models.size(); i++) {
            if (models.get(i).name.equals(tile)) {
                return models.get(i);
            }
        }
        return null;
    }

    private final String name;
    private final BufferedImage picture;

    private final boolean solid;

    private TileModel(InputStream input) throws IOException {
        boolean solid = false;
        String name = "";
        BufferedImage picture = null;
        Scanner scanner = new Scanner(input);
        String line;
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            String[] tokens = line.split(":");
            if (tokens.length == 2) {
                String field = tokens[0];
                switch (field) {
                    case "picture":
                        String pictureSource = tokens[1];
                        InputStream pictureInput = TileModel.class.getResourceAsStream("../picture/tiles/" + pictureSource + ".png");
                        picture = ImageIO.read(pictureInput);
                        break;
                    case "solid":
                        String stringIsSolid = tokens[1];
                        solid = Boolean.parseBoolean(stringIsSolid);
                        break;
                    case "name":
                        name = tokens[1];
                        break;
                    default:
                        break;
                }
            }
        }
        this.picture = picture;
        this.solid = solid;
        this.name = name;
    }

    public BufferedImage getPicture() {
        return picture;
    }

    public boolean isSolid() {
        return solid;
    }

    public static List<TileModel> getModels() {
        return models;
    }

    public String getName() {
        return name;
    }

}
