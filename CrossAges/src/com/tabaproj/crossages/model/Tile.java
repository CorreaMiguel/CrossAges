package com.tabaproj.crossages.model;

/**
 * Classe responsavel por representar um tile.
 *
 * @author José Augusto Gomes
 */
public class Tile {

    /**
     * Valor da classe enumeradora TileModel que representa o modelo de tile da
     * instancia.
     *
     * @see #getTileModel
     */
    private final TileModel tileModel;

    /**
     * Construtor que recebe um valor da classe enumeradora TileModel.
     *
     * @param tileModel será o valor atribuido da ao campo tileModel
     */
    public Tile(TileModel tileModel) {
        this.tileModel = tileModel;
    }
    
    /**
     * Obtêm o valor do campo TileModel
     * @return valor do campo TileModel
     * @see #tileModel
     */
    public TileModel getTileModel() {
        return tileModel;
    }

}