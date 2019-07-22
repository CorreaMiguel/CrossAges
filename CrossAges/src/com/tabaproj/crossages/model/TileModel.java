package com.tabaproj.crossages.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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
                if (f.isFile() && f.canRead() && f.getAbsoluteFile().getPath().toLowerCase().endsWith(".data")) {
                    try (InputStream in = new FileInputStream(f)) {
                        TileModel tile = new TileModel(in);
                        tiles.add(tile);
                    } catch (IOException ex) {

                    }
                }
            }
        }
        return tiles;
    }

    public static TileModel getTile(String tile) {
        if (tile.equalsIgnoreCase("null")) {
            return null;
        }
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
    private final String pictureSource;

    private TileModel(InputStream input) throws IOException {
        boolean solid = false;
        String name = "";
        String pictureSource = "";
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
                        pictureSource = tokens[1];
                        InputStream pictureInput = new FileInputStream("CrossAges/picture/tiles/" + pictureSource + ".png");
                        picture = ImageIO.read(pictureInput);
                        pictureInput.close();
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
        this.pictureSource = pictureSource;
        this.picture = picture;
        this.solid = solid;
        this.name = name;
    }

    public void export(PrintStream out) {
        out.printf("name:%s\n", name);
        out.printf("picture:%s\n", pictureSource);
        File picture = new File("CrossAges/picture/tiles/" + pictureSource + ".png");
        if (picture.isDirectory()) {
            picture.delete();
            picture.getParentFile().mkdirs();
            try {
                ImageIO.write(this.picture, "png", picture);
            } catch (IOException ex) {
            }
        }
        out.printf("solid:%s\n", String.valueOf(solid));
    }

    public void export(OutputStream out) {
        export(new PrintStream(out));
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

    @Override
    public String toString() {
        return name;
    }

}
