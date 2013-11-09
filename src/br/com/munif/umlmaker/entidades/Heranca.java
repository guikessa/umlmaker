/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.umlmaker.entidades;

/**
 *
 * @author Alunos
 */
public class Heranca {

    private Classe superClasse;
    private Classe subClasse;

    public Heranca(Classe superClasse, Classe subClasse) {
        this.superClasse = superClasse;
        this.subClasse = subClasse;
    }

    public Classe getSuperClasse() {
        return superClasse;
    }

    public void setSuperClasse(Classe superClasse) {
        this.superClasse = superClasse;
    }

    public Classe getSubClasse() {
        return subClasse;
    }

    public void setSubClasse(Classe subClasse) {
        this.subClasse = subClasse;
    }
}
