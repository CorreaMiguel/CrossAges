package com.tabaproj.crossages.model;

import java.awt.image.BufferedImage;
import javax.swing.JLabel;

/**
 * Classe enumeradora responsavel por representar um modelo de tile.
 *
 * @author jose
 */
public enum TileModel {

    /**
     * Modelo de tile nullo. Representa a ausencia de um modelo de tile.
     */
    NULL("null"),
    /**
     * Modelo de tile que representa o chão.
     */
    FLOOR("floor");

    /**
     * É um vetor de objetos da classe BufferedImage, que representa todas as
     * variações do modelo de tile.
     *
     * @see #getPicture
     *
     */
    private final BufferedImage[] pictures;

    /**
     * Indica se o modelo de tile permite que sprite passem por ele.
     *
     * @see #isSolid
     */
    private final boolean solid;

    /**
     * Construtor responsavel por criar uma nova instancia da da classe.
     *
     * @param source é o caminho dentro do pacote
     * "com.tabaproj.crossages.data.tiles" do arquivo de formato ".data" que
     * contem as informações basicas do novo modelo de tile. Exemplo: "null"
     */
    TileModel(String source) {
        //fazer importação do tile
    }

    /**
     * Obtem image correspondente à sua variante.
     *
     * @param variant é o número da variação da image.
     * @return a image respectiva à variante
     * @see #pictures
     */
    public BufferedImage getPicture(int variant) {
        return pictures[variant];
    }

    /**
     * Retorna o valor do campo solid.
     *
     * @return o valor do campo solid
     * @see #solid
     */
    public boolean isSolid() {
        return solid;
    }

}
