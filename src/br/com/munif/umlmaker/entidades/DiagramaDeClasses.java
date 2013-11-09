/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.umlmaker.entidades;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Alunos
 */
public class DiagramaDeClasses {

    private File pastaBase;
    private Set<Classe> classes;
    private Set<Grupo> grupos;
    private Set<Associassao> associassoes;
    private Set<Pacote> pacotes;
    private Set<Heranca> herancas;

    public DiagramaDeClasses(File pasta) {
        this.pastaBase = pasta;
        addSoftwareLibrary(pasta);
        classes = new HashSet<>();
        grupos = new HashSet<>();
        associassoes = new HashSet<>();
        pacotes = new HashSet<>();
        herancas = new HashSet<>();

        procuraClasses(pasta);
        procuraAssociacoes();
        procuraHerancas();
    }

    public void procuraClasses(File pasta) {
        try {
            String pb = pastaBase.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\");
            File[] arquivos = pasta.listFiles();
            for (File f : arquivos) {
                if (f.isDirectory()) {
                    procuraClasses(f);
                }
                String nome = f.getAbsolutePath();
                if (nome.endsWith(".class")) {
                    nome = nome.replaceAll(pb, "").replaceAll(".class", "").replaceAll("\\\\", ".").replaceAll("/", ".").substring(1);
                    Class classe = Class.forName(nome);
                    if (classe.isAnnotationPresent(Entity.class)) {
                        Classe nossaClasse = new Classe(classe);
                        classes.add(nossaClasse);
                        grupos.addAll(nossaClasse.getGrupos());
                        pacotes.add(nossaClasse.getPacote());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void addSoftwareLibrary(File file) {
        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            method.invoke(ClassLoader.getSystemClassLoader(), new Object[]{file.toURI().toURL()});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getDot() {
        StringBuilder sb = new StringBuilder();

        sb.append("digraph G{\n"
                + "fontname = \"Bitstream Vera Sans\"\n"
                + "fontsize = 8\n"
                + "\n"
                + "node [\n"
                + "        fontname = \"Bitstream Vera Sans\"\n"
                + "        fontsize = 8\n"
                + "        shape = \"record\"\n"
                + "]\n"
                + "\n"
                + "edge [\n"
                + "        fontname = \"Bitstream Vera Sans\"\n"
                + "        fontsize = 8\n"
                + "]\n");


        sb.append("subgraph grupo\n"
                + "{\n");

        for (Classe classe : classes) {
            sb.append(classe.getNome() + " ");
            sb.append("[style=bold,color=\"#000000\"label = \"{" + classe.getNome() + "|");

            sb.append(classe.getAtributosDot());
            sb.append("|\\l}\"]\n");
        }

        sb.append("}\n");

        //edge [arrowhead = "none" headlabel = "1" taillabel = "*"] FotoParte -> Foto [label = "foto"]

        for (Associassao associassao : associassoes) {
            sb.append("edge [arrowhead = \"none\" headlabel = \"" + associassao.getMultiplicidade1() + "\" taillabel = \""
                    + associassao.getMultiplicidade2() + "\"] " + associassao.getClasse1().getNome() + "-> " + associassao.getClasse2().getNome()
                    + " [label = \"" + associassao.getNome() + "\"]\n");
        }

        for (Heranca heranca : herancas) {
            sb.append("edge [ arrowhead = \"empty\" headlabel = \"\" taillabel = \"\"] " + heranca.getSubClasse().getNome() + " -> " + heranca.getSuperClasse().getNome() + "\n");
        }

        sb.append("}\n");

        return sb.toString();

    }

    private void procuraAssociacoes() {
        for (Classe classe : classes) {
            for (Field atributo : classe.getClazz().getDeclaredFields()) {
                Class tipo = atributo.getType();
                if (tipo.getPackage() == null) {
                    continue;
                }
                Pacote pacoteDoTipo = new Pacote(tipo.getPackage());
                if (pacotes.contains(pacoteDoTipo)) {
                    Associassao associassao = new Associassao();
                    associassao.setClasse1(classe);
                    associassao.setMultiplicidade2("*");
                    if (tipo.isAnnotationPresent(OneToOne.class)) {
                        associassao.setMultiplicidade2("1");
                    }
                    associassao.setClasse2(new Classe(tipo));
                    associassao.setMultiplicidade1("1");
                    associassao.setNome(atributo.getName());
                    associassoes.add(associassao);
                }
                if (tipo.equals(List.class) || tipo.equals(Set.class) || tipo.equals(Map.class)) {
                    ParameterizedType type = (ParameterizedType) atributo.getGenericType();
                    Type[] typeArguments = type.getActualTypeArguments();
                    Class tipoGenerico = (Class) typeArguments[tipo.equals(Map.class) ? 1 : 0];
                    Associassao associassao = new Associassao();
                    associassao.setClasse1(classe);
                    associassao.setMultiplicidade2("*");
                    if (tipo.isAnnotationPresent(OneToMany.class)) {
                        associassao.setMultiplicidade2("1");
                    }
                    associassao.setClasse2(new Classe(tipoGenerico));
                    associassao.setMultiplicidade1("*");
                    associassao.setNome(atributo.getName());
                    associassoes.add(associassao);
                }
            }
        }
    }

    private void procuraHerancas() {
        for (Classe classe : classes) {
            Classe superClasse = new Classe(classe.getClazz().getSuperclass());
            if (pacotes.contains(superClasse.getPacote())) {
                Heranca heranca = new Heranca(superClasse, classe);
                herancas.add(heranca);
            }
        }
    }
}
