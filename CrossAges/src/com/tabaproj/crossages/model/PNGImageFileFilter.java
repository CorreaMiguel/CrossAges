package com.tabaproj.crossages.model;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class PNGImageFileFilter extends FileFilter {

    public PNGImageFileFilter() {
    }

    @Override
    public boolean accept(File pathname) {
        return pathname.isDirectory() || pathname.getName().toLowerCase().endsWith(".png");
    }

    @Override
    public String getDescription() {
        return "Arquivos de imagem do formato PNG.";
    }

}
