package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class GerenciadorAeronaves {

	private ArrayList<Aeronave> aeronaves;

	public GerenciadorAeronaves() {
		aeronaves = new ArrayList<>();
	}
	
	public void ordenaCodigo() {
		//aeronaves.sort( (Aeronave a1, Aeronave a2)
		//		-> a1.getCodigo().compareTo(a2.getCodigo()));
		//aeronaves.sort(Comparator.comparing(a -> a.getCodigo()));
		aeronaves.sort(Comparator.comparing(Aeronave::getCodigo).reversed());
	}
	
	public void ordenaDescricao() {
		Collections.sort(aeronaves);
	}

	public void adicionar(Aeronave av) {
		aeronaves.add(av);
	}
	
	public ArrayList<Aeronave> listarTodas() {
		return new ArrayList<Aeronave>(aeronaves);
	}

	public Aeronave buscarCodigo(String codigo) {
		if (codigo == null)
			return null;
		for(Aeronave av: aeronaves)
			if(av.getCodigo().equals(codigo))
				return av;
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder aux = new StringBuilder();
		for(Aeronave av: aeronaves)
			aux.append(av.toString()+"\n");
		return aux.toString();
	}
	
	/*
	 * public void carregaDados() throws IOException {
		Path path1 = Paths.get("airlines.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabeçalho
			String id, nome;
			while (sc.hasNext()) {
				id = sc.next();
				nome = sc.next();
				System.out.format("%s - %s%n", id, nome);
				empresas.add(new CiaAerea(id, nome));
			}
		}
	}
	 */
}
