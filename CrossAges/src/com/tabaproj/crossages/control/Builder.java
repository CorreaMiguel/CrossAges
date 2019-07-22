package com.tabaproj.crossages.control;

import com.tabaproj.crossages.model.SceneFileFilter;
import com.tabaproj.crossages.model.PNGImageFileFilter;
import com.tabaproj.crossages.model.Scene;
import com.tabaproj.crossages.model.Tile;
import com.tabaproj.crossages.model.TileModel;
import com.tabaproj.crossages.view.ChoseTile;
import com.tabaproj.crossages.view.ToolsFrame;
import com.tabaproj.crossages.view.Viewer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Builder {

    public static final int DESTROY = 0, PUT = 1, COPY = 2;

    public static final int WIDTH = 800;

    public static final int HEIGHT = 600;

    private File file = null;
    private boolean saved;
    private Scene scene;
    private TileModel actualTile = null;
    private int mode = 1;
    private final ToolsFrame tools;
    private final Viewer viewer;

    private int sceneWidth;

    private int sceneHeight;
    private int locationX = 0;
    private int locationY = 0;
    private int cursorX = -1;
    private int cursorY = -1;
    private ChoseTile choseTile;

    public Builder(int sceneWidth, int sceneHeight) {
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.scene = new Scene(sceneWidth, sceneHeight);
        this.viewer = new Viewer(WIDTH, HEIGHT);
        this.viewer.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.tools = new ToolsFrame(this);
        this.tools.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.tools.setLocation(viewer.getLocation().x - tools.getWidth() - 30, viewer.getLocation().y);
        this.tools.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        this.viewer.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        this.viewer.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!e.isShiftDown()) {
                    if (e.getButton() == e.BUTTON3) {
                        if (mode == DESTROY) {
                            setMode(PUT);
                        } else {
                            setMode(DESTROY);
                        }
                    } else {
                        if (e.getButton() == e.BUTTON2) {
                            setMode(COPY);
                        }
                        click();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        this.viewer.addMouseMotionListener(new MouseMotionListener() {
            int x = 0, y = 0;

            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.isShiftDown()) {
                    setLocationX(getLocationX() + e.getX() - x);
                    setLocationY(getLocationY() + e.getY() - y);
                } else {
                    click();

                }
                setCursorX(e.getX());
                setCursorY((e.getY() - 30));
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                setCursorX(e.getX());
                setCursorY((e.getY() - 30));
                x = e.getX();
                y = e.getY();
            }
        });
        this.choseTile = new ChoseTile(this);
        update();
        this.tools.setVisible(true);
        this.viewer.setVisible(true);
        saved = true;
    }

    public BufferedImage renderFrame() {
        BufferedImage frame = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = frame.getGraphics();
        graphics.setColor(Color.white);
        for (int x = 0; x < scene.getWidth(); x++) {
            graphics.drawLine(locationX + x * TileModel.WIDTH, locationY, locationX + x * TileModel.WIDTH, locationY + scene.height * TileModel.HEIGHT);
        }
        for (int y = 0; y < scene.getHeight(); y++) {
            graphics.drawLine(locationX, locationY + y * TileModel.HEIGHT, locationX + scene.width * TileModel.WIDTH, locationY + y * TileModel.HEIGHT);
        }
        graphics.setColor(Color.blue);
        graphics.drawRect(locationX, locationY, scene.width * TileModel.WIDTH, scene.height * TileModel.HEIGHT);
        graphics.setColor(Color.white);
        scene.draw(graphics, locationX, locationY);
        graphics.setColor(new Color(255, 0, 0, 196));
        int cursorX = this.cursorX * TileModel.WIDTH + locationX, cursorY = this.cursorY * TileModel.HEIGHT + locationY;
        if (cursorX >= 0 && cursorY >= 0 && cursorX < scene.width * TileModel.WIDTH && cursorY < scene.height * TileModel.HEIGHT) {
            switch (mode) {
                case DESTROY:
                    graphics.fillRect(cursorX, cursorY, TileModel.WIDTH, TileModel.HEIGHT);
                    break;
                case PUT:
                    if (actualTile != null) {
                        BufferedImage pic = new BufferedImage(TileModel.WIDTH, TileModel.HEIGHT, BufferedImage.TYPE_INT_ARGB);
                        for (int x = 0; x < pic.getWidth(); x++) {
                            for (int y = 0; y < pic.getHeight(); y++) {
                                Color color = new Color(actualTile.getPicture().getRGB(x, y));
                                color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 127);
                                pic.setRGB(x, y, color.getRGB());
                            }
                        }
                        graphics.drawImage(pic, cursorX, cursorY, TileModel.WIDTH, TileModel.HEIGHT, null);
                    }
                    break;
                case COPY:
                    graphics.drawRect(cursorX, cursorY, TileModel.WIDTH, TileModel.HEIGHT);
                    break;
                default:
                    break;
            }
        }
        graphics.dispose();
        return frame;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        update();
    }

    public TileModel getActualTile() {
        return actualTile;
    }

    public void setActualTile(TileModel actualTile) {
        this.actualTile = actualTile;
        tools.updateTile();
        update();
    }

    public int getSceneWidth() {
        return sceneWidth;
    }

    public void setSceneWidth(int sceneWidth) {
        this.sceneWidth = sceneWidth;
        resizeTiles();
        saved = false;
    }

    public int getSceneHeight() {
        return sceneHeight;
    }

    public void setSceneHeight(int sceneHeight) {
        this.sceneHeight = sceneHeight;
        resizeTiles();
        saved = false;
    }

    private void resizeTiles() {
        Scene newScene = new Scene(sceneWidth, sceneHeight);
        for (int x = 0; x < sceneWidth && x < scene.getWidth(); x++) {
            for (int y = 0; y < sceneHeight && y < scene.getHeight(); y++) {
                newScene.setTile(x, y, scene.getTile(x, y));
            }
        }
        this.scene = newScene;
        update();
    }

    public static void main(String[] args) {
        File dataTiles = new File("CrossAges/data/tiles");
        File dataPicture = new File("CrossAges/picture/tiles");
        if (!dataPicture.exists()) {
            dataPicture.mkdirs();
        }
        if (!dataTiles.exists()) {
            dataTiles.mkdirs();
        }
        new Builder(48, 36);
    }

    public int getCursorX() {
        return cursorX;
    }

    public void setCursorX(int cursorX) {
        cursorX = (cursorX - locationX) / TileModel.WIDTH;
        if (cursorX >= 0 && cursorX < scene.width) {
            this.cursorX = cursorX;
        }
        update();

    }

    public int getCursorY() {
        return cursorY;
    }

    public void setCursorY(int cursorY) {
        cursorY = (cursorY - locationY) / TileModel.HEIGHT;
        if (cursorY >= 0 && cursorY < scene.height) {
            this.cursorY = cursorY;
        }
        update();
    }

    public void update() {
        viewer.setFrame(renderFrame());
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
        update();
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
        update();
    }

    public void chose() {
        choseTile.setVisible(true);
        setMode(Builder.PUT);
        update();
    }

    public void click() {
        switch (mode) {
            case DESTROY:
                scene.setTile(cursorX, cursorY, new Tile(null));
                break;
            case PUT:
                if (actualTile != null) {
                    scene.setTile(cursorX, cursorY, new Tile(actualTile));
                }
                break;
            case COPY:
                if (scene.getTile(cursorX, cursorY) != null && scene.getTile(cursorX, cursorY).getTileModel() != null) {
                    setActualTile(scene.getTile(cursorX, cursorY).getTileModel());
                    mode = PUT;
                }
                break;
            default:
                break;
        }
        update();
    }

    public void exit() {
        if (!saved) {
            int op = JOptionPane.showConfirmDialog(null, "Deseja salvar as alterações?", "CrossAges Builder", JOptionPane.YES_NO_CANCEL_OPTION);
            if (op == 2) {
                return;
            } else if (op == 0) {
                save();
            }
        }
        viewer.dispose();
        choseTile.dispose();
        tools.dispose();
        System.exit(0);
    }

    public void saveAs() {
        JFileChooser fc = new JFileChooser(file);
        fc.setFileFilter(new SceneFileFilter());
        if (fc.showSaveDialog(null) == 0) {
            file = fc.getSelectedFile();
            if (file != null) {
                save();
            }
        }
    }

    public void export() {
        JFileChooser fc = new JFileChooser(this.file.getParentFile());
        fc.setFileFilter(new PNGImageFileFilter());
        if (fc.showSaveDialog(null) == 0) {
            File file = fc.getSelectedFile();
            if (file != null) {
                if (!file.getName().endsWith(".png")) {
                    file = new File(file.getPath() + ".png");
                }
                BufferedImage image = new BufferedImage(scene.getWidth() * TileModel.WIDTH, scene.getHeight() * TileModel.HEIGHT, 2);
                Graphics g = image.getGraphics();
                scene.draw(g, 0, 0);
                g.dispose();
                try {
                    ImageIO.write(image, "png", file);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Não foi possivel exportar cenario.", "Erro - CrossAges Bulder", 0);
                }
            }
        }
    }

    public void save() {
        try {
            if (file == null) {
                saveAs();
            } else {
                if (!file.getName().endsWith(".scn")) {
                    file = new File(file.getPath() + ".scn");
                }
                scene.export(new FileOutputStream(file));
                saved = true;
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel salvar cenario.", "Erro - CrossAges Bulder", 0);
        }
    }

    public void newFile() {
        if (!saved) {
            int op = JOptionPane.showConfirmDialog(null, "Deseja salvar as alterações?", "CrossAges Builder", JOptionPane.YES_NO_CANCEL_OPTION);
            if (op == 2) {
                return;
            } else if (op == 0) {
                save();
            }
        }
        file = null;
        saved = false;
        scene = new Scene(sceneWidth, sceneHeight);
        update();
    }

    public void open() {
        if (!saved) {
            int op = JOptionPane.showConfirmDialog(null, "Deseja salvar as alterações?", "CrossAges Builder", JOptionPane.YES_NO_CANCEL_OPTION);
            if (op == 2) {
                return;
            } else if (op == 0) {
                save();
            }
        }
        JFileChooser fc = new JFileChooser(file);
        fc.setFileFilter(new SceneFileFilter());
        if (fc.showSaveDialog(null) == 0) {
            try {
                File file = fc.getSelectedFile();
                scene = new Scene(new FileInputStream(file));
                saved = true;
                this.file = file;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possivel abrir cenario.", "Erro - CrossAges Bulder", 0);
            }
        }
        update();
    }
}
