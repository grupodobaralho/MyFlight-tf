package pucrs.myflight.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class GerenciadorCias {
	private HashMap<String, CiaAerea> empresas;

	public GerenciadorCias() {
		empresas = new HashMap<>();
	}

	public void carregaDados() throws IOException {
		Path path1 = Paths.get("airlines.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabeçalho
			String id, nome;
			while (sc.hasNext()) {
				id = sc.next();
				nome = sc.next();
				// System.out.println("ID: "+ id + "- Nome: "+ nome + "-");
				// System.out.format("%s - %s%n", id, nome);
				CiaAerea cia = new CiaAerea(id, nome);
				empresas.put(cia.getCodigo(), cia);
			}
		}
	}

	public HashMap<String, CiaAerea> getHash() {
		return empresas;
	}

	public CiaAerea getCiaAerea(String codigo) {

		return empresas.get(codigo);
	}

	public void addCiaAerea(CiaAerea cia) {

		empresas.put(cia.getCodigo(), cia);

	}

	public ArrayList<CiaAerea> listarCiasAereas() {
		ArrayList<CiaAerea> retorno = new ArrayList<>();

		for (HashMap.Entry<String, CiaAerea> entry : empresas.entrySet()) {
			retorno.add(entry.getValue());
		}

		return retorno;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (HashMap.Entry<String, CiaAerea> entry : empresas.entrySet()) {
			str.append(entry.getValue() + "\n");
		}
		return str.toString();
	}

}
