package com.tabaproj.crossages.control;

import com.tabaproj.crossages.model.Scene;
import com.tabaproj.crossages.model.TileModel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Classe responsavel por controlar a ferramenta de edição e criaçao de
 * cenarios.
 *
 * @author jose
 */
public class Builder {

    /**
     * Renderiza um frame(quadro) da simulação do cenario.
     *
     * @param scene cenario que será renderizado
     * @param startX pocição horizontal em pixels de onde começa a simulação
     * @param startY pocição vertical em pixels de onde começa a simulação
     * @param width largura em pixels do frame que será renderizado
     * @param height autura em pixels do frame que será renderizado
     * @return um objeto da classe BufferedImage correspondente ao frame
     * renderizado
     *
     * @see Scene
     */
    public static BufferedImage renderFrame(Scene scene, int startX, int startY, int width, int height) {
        //criando nova imagem correspondente ao frame que será retornardo
        BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //convertendo os valores de pixels para unidades de tiles, e pegando apenas a parte inteira
        int tileStartX = startX / TileModel.WIDTH;
        int tileStartY = startY / TileModel.HEIGHT;
        int tileWidth = width / TileModel.WIDTH + 1;
        int tileHeight = height / TileModel.HEIGHT + 1;
        //obtendo objeto grafics da image, que será utilizado para desenha na imagem
        Graphics graphics = frame.getGraphics();
        //loop para desenhar cada tile do enario
        for (int x = tileStartX; x < tileWidth && x < scene.getWidth(); x++) {
            for (int y = tileStartY; y < tileHeight && y < scene.getHeight(); y++) {
                //obtendo os valores em pixels para desenhar
                int pixX = x * TileModel.WIDTH - startX;
                int pixY = y * TileModel.HEIGHT - startY;
                //obtendo a imagem do respectivo tile
                BufferedImage tile = scene.getTile(x, y).getTileModel().getPicture();
                //desenhando o tile no frame
                graphics.drawImage(tile, pixX, pixY, TileModel.WIDTH, TileModel.HEIGHT, null);
            }
        }
        //encerrando o uso do objeto graphics
        graphics.dispose();
        return frame;
    }

}
