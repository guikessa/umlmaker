/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.umlmaker.entidades;

import br.com.munif.umlmaker.anotacoes.GrupoDiagrama;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Alunos
 */
public class Classe {

    private List<Method> metodos;
    private List<Field> atributos;
    private List<Grupo> grupos;
    private List<Associassao> associassoes;
    private Pacote pacote;
    private String nome;
    private Class clazz;

    public Classe(Class classe) {
        clazz = classe;
        Package aPackage = classe.getPackage();
        pacote = new Pacote(aPackage);
        nome = classe.getSimpleName();
        grupos = new ArrayList<>();
        atributos = new ArrayList<>();
        adicionaAtributos(classe);
        if (classe.isAnnotationPresent(GrupoDiagrama.class)) {
            GrupoDiagrama grupoDiagrama = (GrupoDiagrama) classe.getAnnotation(GrupoDiagrama.class);
            for (String s : grupoDiagrama.value()) {
                grupos.add(new Grupo(s));
            }
        } else {
            grupos.add(new Grupo("outros"));
        }

    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public List<Method> getMetodos() {
        return metodos;
    }

    public void setMetodos(List<Method> metodos) {
        this.metodos = metodos;
    }

    public List<Field> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Field> atributos) {
        this.atributos = atributos;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public List<Associassao> getAssociassoes() {
        return associassoes;
    }

    public void setAssociassoes(List<Associassao> associassoes) {
        this.associassoes = associassoes;
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return pacote + "  " + nome;
    }

    public String getAtributosDot() {
        StringBuilder sb = new StringBuilder();
        for (Field f : atributos) {
            sb.append(f.getName() + ":" + f.getType().getSimpleName() + "\\l");
        }
        return sb.toString();
    }

    private void adicionaAtributos(Class classe) {
        Field[] todos = classe.getDeclaredFields();
        for (Field f : todos) {
            if ((f.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }
            atributos.add(f);
        }

    }
}
