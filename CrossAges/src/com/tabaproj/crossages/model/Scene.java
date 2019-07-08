package com.tabaproj.crossages.model;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Classe enumeradora responsavel por representar os cenarios.
 */
public enum Scene {

    /**
     * Cenario de exemplo.
     */
    EXEMPLE("exemple");

    /**
     * representa a largura em unidade do cenario.
     *
     * @see #getWidth
     */
    public final int width;

    /**
     * representa a autura em unidade do cenario.
     *
     * @see #getHeight
     */
    public final int height;

    /**
     * Matriz de tiles, são todos os tiles dos cenario.
     *
     * @see #getTile
     */
    private final Tile[][] tiles;

    /**
     * Construtor responsavel por criar uma nova instancia da da classe.
     *
     * @param source é o caminho sem a extenção dentro do pacote
     * "com.tabaproj.crossages.scenes" do arquivo de formato "scn" que contem as
     * informações do novo cenario. Exemplo: "exemple"
     */
    private Scene(String source) {
        //importação do aquivo do cenario
        //criando variavel local que será atribuida a constante width que terá o valor 0 como padrao
        int width = 0;
        //criando variavel local que será atribuida a constante height que terá o valor 0 como padrao
        int height = 0;
        //criando variavel local que será atribuida a constante tiles que terá o valor nulo como padrao
        Tile[][] tiles = null;
        //tentando importar o arquivo
        try {
            //obtendo entrada de fluxo do arquivo source
            InputStream input = TileModel.class.getResourceAsStream("../scenes/" + source + ".scn");
            //criando um objeto Scanner para ler o arquivo source
            Scanner scanner = new Scanner(input);
            //lendo e armazenando a quantiva de modelos de tiles diferentes que serão usados
            int tilesMapCount = scanner.nextInt();
            //criando mapa de tiles, que serão todos os modelos de tiles armazenados no senario
            TileModel[] tilesMap = new TileModel[tilesMapCount];
            //loop que irá ler e preencher o mapa de tiles
            for (int i = 0; i < tilesMapCount; i++) {
                //armazenando no mapa de tiles, um tile model que será obtido pelo Scanner em formato de String, e convertido para TileModel
                tilesMap[i] = TileModel.valueOf(scanner.next());
            }
            //lendo largura em unidades de tiles do cenario
            width = scanner.nextInt();
            //lendo autura em unidades de tiles do cenario
            height = scanner.nextInt();
            //criando matriz de tiles com as dimenções obtidas
            tiles = new Tile[width][height];
            //lendo os valores dos tiles do cenario
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //obtendo do scanner o indice no mapa de tiles
                    int mapIndex = scanner.nextInt();
                    //atribuindo à matriz de tiles, uma nova istancia de Tile que recebe o tile model do mapa de tiles na posição obtida 
                    tiles[x][y] = new Tile(tilesMap[mapIndex]);
                }
            }

        } catch (/*IO*/Exception ex) {
            //caso algo dê errado na leitura do arquivo
            System.err.println("Erro, não foi possivel importar o cenario " + this.name() + "\n" + ex.getMessage());
        }
        //atribuindo o valor da variavel local tiles, para o campo tiles
        this.width = width;
        this.height = height;
        this.tiles = tiles;
    }

    /**
     * Obtem um tile em sua respectiva posição.
     *
     * @param x posição em unidade horizontal no canario
     * @param y posição em unidade vertical no canario
     * @return o tile na posição x e y no cenario
     * @see #tiles
     */
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    /**
     * Obtem a largura em unidades de tiles do cenario
     *
     * @return largura do cenario
     * @see #width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Obtem a autura em unidades de tiles do cenario
     *
     * @return autura do cenario
     * @see #height
     */
    public int getHeight() {
        return height;
    }

}
