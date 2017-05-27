package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class GerenciadorPaises {

	private ArrayList<Pais> paises;

	public GerenciadorPaises() {
		paises = new ArrayList<>();
	}

	public void carregaDados() throws IOException {
		Path path1 = Paths.get("countries.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabeçalho
			String codigo, nome;
			while (sc.hasNext()) {
				codigo = sc.next();
				nome = sc.next();
				System.out.format("%s - %s%n", codigo, nome);
				paises.add(new Pais(codigo, nome));
			}
		}
	}
	
	public Pais buscarCodigo(String codigo) {
		for(Pais p: paises)
			if(p.getCodigo().equals(codigo))
				return p;
		return null;
	}
	

}
