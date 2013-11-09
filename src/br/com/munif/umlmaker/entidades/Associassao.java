/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.umlmaker.entidades;

import java.util.Objects;

/**
 *
 * @author Alunos
 */
public class Associassao {

    private Classe classe1;
    private String multiplicidade1;
    private Classe classe2;
    private String multiplicidade2;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Classe getClasse1() {
        return classe1;
    }

    public void setClasse1(Classe classe1) {
        this.classe1 = classe1;
    }

    public String getMultiplicidade1() {
        return multiplicidade1;
    }

    public void setMultiplicidade1(String multiplicidade1) {
        this.multiplicidade1 = multiplicidade1;
    }

    public Classe getClasse2() {
        return classe2;
    }

    public void setClasse2(Classe classe2) {
        this.classe2 = classe2;
    }

    public String getMultiplicidade2() {
        return multiplicidade2;
    }

    public void setMultiplicidade2(String multiplicidade2) {
        this.multiplicidade2 = multiplicidade2;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.classe1);
        hash = 53 * hash + Objects.hashCode(this.classe2);
        hash = 53 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Associassao other = (Associassao) obj;
        if (!Objects.equals(this.classe1, other.classe1)) {
            return false;
        }
        if (!Objects.equals(this.classe2, other.classe2)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }
}
