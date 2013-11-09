package br.com.munif.umlmaker.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GrupoDiagrama {

    String[] value();
}
