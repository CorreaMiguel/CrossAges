package com.tabaproj.crossages.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Viewer extends JFrame {

    private final JLabel frameViewer = new JLabel();
    private final int height;
    private final int width;

    public Viewer(int width, int height) {
        this.height = height;
        this.width = width;
        this.setTitle("Visualizar");
        this.setSize(width, height + 30);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(frameViewer);

    }

    public void setFrame(BufferedImage frame) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.drawImage(frame, 0, 0, width, height, null);
        g.dispose();
        ImageIcon icon = new ImageIcon(image);
        frameViewer.setIcon(icon);
    }

}
