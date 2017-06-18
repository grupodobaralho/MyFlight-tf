package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GerenciadorPaises {

	private HashMap<String, Pais> paises;

	public GerenciadorPaises() {
		paises = new HashMap<>();
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
				Pais pais = new Pais(codigo, nome);
				paises.put(pais.getCodigo(), pais);
			}
		}
	}

	public HashMap<String, Pais> getHash() {
		return paises;
	}

	public Pais getAeroporto(String codigo) {

		return paises.get(codigo);
	}

	public void addAeroporto(Pais pais) {
		paises.put(pais.getCodigo(), pais);
	}

	public ArrayList<Pais> listarPaises() {
		ArrayList<Pais> retorno = new ArrayList<>();
		for (HashMap.Entry<String, Pais> entry : paises.entrySet()) {
			retorno.add(entry.getValue());
		}

		return retorno;
	}
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (HashMap.Entry<String, Pais> entry : paises.entrySet()) {
			str.append(entry.getValue() + "\n");
		}
		return str.toString();
	}

}