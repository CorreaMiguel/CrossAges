package com.tabaproj.crossages.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Classe que herda JFrame, responsavel por visualizar um cenario.
 *
 * @author José Augusto Gomes
 */
public class Viewer extends JFrame {

    /**
     * Campo objeto da classe JLabel, que será inserida na janela. inicializada
     * com um novo objeto do contrutor vasio da classe.
     */
    private final JLabel frameViewer = new JLabel();
    /**
     * Campo responsavel por armazanar a autura real em pixels do frame.
     */
    private final int height;
    /**
     * Campo responsavel por armazanar a largura real em pixels do frame.
     */
    private final int width;

    /**
     * Construtor que cria uma nova instancia da classe.
     *
     * @param width será o valor atribuidoao campo height
     * @param height será o valor atribuidoao campo height
     * @see #height
     * @see #width
     */
    public Viewer(int width, int height) {
        //atribuindo aos campos, os valores recebidos por parâmetro
        this.height = height;
        this.width = width;
        //fazendo as configurações da janela.
        //definindo o tamanho da janela. obs: 30 pixels foram adcionados na autura para corrigir a barra da janela.
        this.setSize(width, height + 30);
        //definindo a localização da janela no centr da tela.
        this.setLocationRelativeTo(null);
        //definindo a janela como não redimencionavel
        this.setResizable(false);
        //adiconando a label na janela
        this.add(frameViewer);
    }

    /**
     * Método que define um novo frame da janela.
     *
     * @param frame uma imagem que representa o frame.
     */
    public void setFrame(BufferedImage frame) {
        //criando um novo objeto de imagem para ser exibido na janela
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //obtendo objeto Graphics da imagem
        Graphics g = image.getGraphics();
        //sobrepondo o frame na imagem, já redimencionado
        g.drawImage(frame, 0, 0, width, height, null);
        //encerrando o objeto graphics
        g.dispose();
        //convertendo a imagem para icone
        ImageIcon icon = new ImageIcon(image);
        //definindo o icone como icone da imagem
        frameViewer.setIcon(icon);
    }

}
