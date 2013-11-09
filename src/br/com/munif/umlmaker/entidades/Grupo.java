/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.umlmaker.entidades;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Alunos
 */
public class Grupo {

    private String nome;

    public Grupo(String s) {
        nome = s;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.nome);
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
        final Grupo other = (Grupo) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }
}
