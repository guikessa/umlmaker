/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.umlmaker;

import br.com.munif.umlmaker.entidades.DiagramaDeClasses;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Alunos
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File pastaClasses = selecionaPastaClasses();
        DiagramaDeClasses dc = new DiagramaDeClasses(pastaClasses);
        try {
            FileWriter fw=new FileWriter("c:/saida.dot", false);
            fw.append(dc.getDot());
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        

    }

    private static File selecionaPastaClasses() {
        JFileChooser jc = new JFileChooser("C:\\Users\\Alunos\\Desktop\\SSerra");
        jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jc.setAcceptAllFileFilterUsed(false);

        if (jc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println(jc.getSelectedFile());

        }
        return jc.getSelectedFile();
    }
}
