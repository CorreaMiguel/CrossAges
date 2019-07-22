package com.tabaproj.crossages.model;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class SceneFileFilter extends FileFilter {

    public SceneFileFilter() {
    }

    @Override
    public boolean accept(File pathname) {
        return pathname.isDirectory() || pathname.getName().toLowerCase().endsWith(".scn");
    }

    @Override
    public String getDescription() {
        return "Arquivos de cenarios.";
    }
}
