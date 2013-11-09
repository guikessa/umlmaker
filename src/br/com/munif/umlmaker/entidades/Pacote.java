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
public class Pacote {

    private String nome;

    Pacote(Package aPackage) {
        nome = aPackage.getName();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.nome);
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
        final Pacote other = (Pacote) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }
}
