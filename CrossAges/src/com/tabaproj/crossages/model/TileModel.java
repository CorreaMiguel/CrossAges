package com.tabaproj.crossages.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.imageio.ImageIO;

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
     * Modelo de tile que representa o tile de grama.
     */
    GRASS("grass");

    /**
     * É um objeto da classe BufferedImage que representa a imagem do modelo de
     * tile
     *
     * @see #getPicture
     *
     */
    private final BufferedImage picture;

    /**
     * Indica se o modelo de tile permite que sprite passem por ele.
     *
     * @see #isSolid
     */
    private final boolean solid;

    /**
     * Construtor responsavel por criar uma nova instancia da da classe.
     *
     * @param source é o caminho sem a extenção dentro do pacote
     * "com.tabaproj.crossages.data.tiles" do arquivo de formato "data" que
     * contem as informações basicas do novo modelo de tile. Exemplo: "null"
     */
    TileModel(String source) {
        //importação do aquivo do tile
        //criando variavel local que será atribuida a constante solid que terá o valor falso como padrao
        boolean solid = false;
        //criando variavel local que será atribuida a constante picture que terá o valor nulo como padrao
        BufferedImage picture = null;
        //tentando importar o arquivo
        try {
            //obtendo entrada de fluxo do arquivo source
            InputStream input = TileModel.class.getResourceAsStream("../data/tiles/" + source + ".data");
            //criando um objeto Scanner para ler o arquivo source
            Scanner scanner = new Scanner(input);
            //variavel do tipo String que armazenará as linha do arquivo
            String line;
            //loop que lê linha por linha do arquivo.
            //continua o loop caso o arquivo ainda tiver ao menos uma linha para ler.
            while (scanner.hasNext()) {
                //lendo uma linha do arquivo
                line = scanner.nextLine();
                //criando um vetor de string que recebe a linha separada por ":"
                String[] tokens = line.split(":");
                //verificando se a quantidade de tokens é igual a 2 
                if (tokens.length == 2) {
                    //obtendo o primeiro token da linha que representa o nome do campo
                    String field = tokens[0];
                    //atribuindo o valor de um atributo dependendo do valor da variavel field
                    switch (field) {
                        case "picture":
                            //obtendo o segundo token da linha que representa o valor do campo
                            String pictureSource = tokens[1];
                            //obtendo entrada de fluxo do arquivo de imagem do modelo de tile
                            InputStream pictureInput = TileModel.class.getResourceAsStream("../picture/tiles/" + pictureSource + ".png");
                            //definindo o valor de picture apartir da imagem obtida no arquivo
                            picture = ImageIO.read(pictureInput);
                            break;
                        case "solid":
                            //obtendo o segundo token da linha que representa o valor do campo
                            String stringIsSolid = tokens[1];
                            //definindo o valor de solid apartir da conversão do token obtido
                            solid = Boolean.parseBoolean(stringIsSolid);
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            //caso algo dê errado na leitura do arquivo
            System.err.println("Erro, não foi possivel importar o tile model " + this.name() + "\n" + ex.getMessage());
        }
        //atribuindo o valor da variavel local picture, para o campo picture
        this.picture = picture;
        //atribuindo o valor da variavel local solid, para o campo solid
        this.solid = solid;
    }

    /**
     * Obtem image correspondente à sua variante.
     *
     * @return valor do campo picture
     * @see #picture
     */
    public BufferedImage getPicture() {
        return picture;
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
