package com.tabaproj.crossages.control;

import com.tabaproj.crossages.model.Scene;
import com.tabaproj.crossages.model.TileModel;
import com.tabaproj.crossages.view.Viewer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Classe responsavel por controlar a ferramenta de edição e criaçao de
 * cenarios.
 *
 * @author José Augusto Gomes
 */
public class Builder {

    /**
     * Constante width representa o tamanho real em pixels da largura da janela
     * visualizadora.
     *
     */
    public static final int WIDTH = 800;

    /**
     * Constante width representa o tamanho real em pixels da autura da janela
     * visualizadora.
     *
     */
    public static final int HEIGHT = 600;

    /**
     * Campo viewer será a janela em que será possivel uma previsialização do
     * cenario.
     *
     */
    private final Viewer viewer;
    /**
     * Representa a largura em unidade de tiles do cenário.
     *
     * @see #getSceneWidth
     * @see #setSceneWidth
     */
    private int sceneWidth;
    /**
     * Representa a autura em unidade de tiles do cenário.
     *
     * @see #getSceneHeight
     * @see #setSceneHeight
     */
    private int sceneHeight;

    private TileModel[][] tiles;

    /**
     * Construtor que recebe o tamanho do cenário à ser criado.
     *
     * @param sceneWidth a largura em unidade de tiles do cenário.
     * @param sceneHeight a autura em unidade de tiles do cenário.
     * @see #sceneWidth
     * @see #sceneHeight
     */
    public Builder(int sceneWidth, int sceneHeight) {
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        //criando matriz de tiles
        this.tiles = new TileModel[sceneWidth][sceneHeight];
        //cria uma janela visualizadora de tamanho padrão
        this.viewer = new Viewer(WIDTH, HEIGHT);
        this.viewer.setFrame(renderFrame(0,0,WIDTH,HEIGHT));
        this.viewer.setDefaultCloseOperation(Viewer.EXIT_ON_CLOSE);
        this.viewer.setVisible(true);
    }

    /**
     * Renderiza um frame(quadro) da simulação do cenario, apartir de um objeto
     * Scene.
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
                TileModel model = TileModel.NULL;
                if (scene.getTile(x, y).getTileModel() != null) {
                    //obtendo o respectivo modelo de tile caso não for nulo.
                    model = scene.getTile(x, y).getTileModel();
                }
                //obtendo a imagem do respectivo tile
                BufferedImage tile = model.getPicture();
                //desenhando o tile no frame
                graphics.drawImage(tile, pixX, pixY, TileModel.WIDTH, TileModel.HEIGHT, null);
            }
        }
        //encerrando o uso do objeto graphics
        graphics.dispose();
        return frame;
    }

    /**
     * Renderiza um frame(quadro) da simulação do cenario, apartir de uma
     * instancia de builder.
     *
     * @param startX pocição horizontal em pixels de onde começa a simulação
     * @param startY pocição vertical em pixels de onde começa a simulação
     * @param width largura em pixels do frame que será renderizado
     * @param height autura em pixels do frame que será renderizado
     * @return um objeto da classe BufferedImage correspondente ao frame
     * renderizado
     *
     */
    public BufferedImage renderFrame(int startX, int startY, int width, int height) {
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
        for (int x = tileStartX; x < tileWidth && x < getSceneWidth(); x++) {
            for (int y = tileStartY; y < tileHeight && y < getSceneHeight(); y++) {
                //obtendo os valores em pixels para desenhar
                int pixX = x * TileModel.WIDTH - startX;
                int pixY = y * TileModel.HEIGHT - startY;
                TileModel model = TileModel.NULL;
                if (tiles[x][y] != null) {
                    //obtendo o respectivo modelo de tile caso não for nulo.
                    model = tiles[x][y];
                }
                //obtendo a imagem do respectivo tile
                BufferedImage tile = model.getPicture();
                //desenhando o tile no frame
                graphics.drawImage(tile, pixX, pixY, TileModel.WIDTH, TileModel.HEIGHT, null);
            }
        }
        //encerrando o uso do objeto graphics
        graphics.dispose();
        return frame;
    }

    /**
     * Obtem a largura em unidade de tiles do cenário.
     *
     * @return valor do campo sceneWidth
     * @see #sceneWidth
     */
    public int getSceneWidth() {
        return sceneWidth;
    }

    /**
     * Define a largura em unidade de tiles do cenário.
     *
     * @param sceneWidth novo valor para a largura do cenário
     * @see #sceneWidth
     */
    public void setSceneWidth(int sceneWidth) {
        this.sceneWidth = sceneWidth;
        resizeTiles();
    }

    /**
     * Obtem a autura em unidade de tiles do cenário.
     *
     * @return valor do campo sceneHeight
     * @see #sceneHeight
     */
    public int getSceneHeight() {
        return sceneHeight;
    }

    /**
     * Define a autura em unidade de tiles do cenário.
     *
     * @param sceneHeight novo valor para a autura do cenário
     * @see #sceneHeight
     */
    public void setSceneHeight(int sceneHeight) {
        this.sceneHeight = sceneHeight;
        resizeTiles();
    }

    private void resizeTiles() {
        TileModel[][] newTiles = new TileModel[sceneWidth][sceneHeight];
        for (int x = 0; x < sceneWidth && x < tiles.length; x++) {
            for (int y = 0; y < sceneHeight && y < tiles[x].length; y++) {
                newTiles[x][y] = this.tiles[x][y];
            }
        }
        this.tiles = newTiles;
    }
    public static void main(String[] args) {
        new Builder(64,64);
    }
}
