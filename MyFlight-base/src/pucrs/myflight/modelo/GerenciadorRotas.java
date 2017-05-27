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

public class GerenciadorRotas {

	private ArrayList<Rota> rotas;

	public GerenciadorRotas() {
		rotas = new ArrayList<>();
	}

	public void adicionar(Rota r) {
		rotas.add(r);
	}

	public ArrayList<Rota> listarTodas() {
		return new ArrayList<Rota>(rotas);
	}

	public int totalRotas() {
		return rotas.size();
	}

	public void ordenaCia() {
		// Collections.sort(rotas);
		rotas.sort((Rota r1, Rota r2) -> r1.getCia().getNome().compareTo(r2.getCia().getNome()));
	}

	public void ordenaOrigem() {
		rotas.sort((Rota r1, Rota r2) -> r1.getOrigem().getNome().compareTo(r2.getOrigem().getNome()));
	}

	public void ordenaOrigemCia() {
		/*
		 * rotas.sort( (Rota r1, Rota r2) -> { int r =
		 * r1.getOrigem().getNome().compareTo(r2.getOrigem().getNome()); if
		 * (r!=0) return r; return
		 * r1.getCia().getNome().compareTo(r2.getCia().getNome()); });
		 */
		rotas.sort(Comparator.comparing((Rota r) -> r.getOrigem().getNome()).thenComparing(r -> r.getCia().getNome()));
	}

	public ArrayList<Rota> buscarOrigem(Aeroporto origem) {
		ArrayList<Rota> lista = new ArrayList<>();
		for (Rota r : rotas) {
			// System.out.println(r.getOrigem().getCodigo());
			if (origem.getCodigo().equals(r.getOrigem().getCodigo()))
				lista.add(r);
		}
		return lista;
	}

	public void carregaDados(GerenciadorCias cias, GerenciadorAeroportos aero, GerenciadorAeronaves aeron)
			throws IOException {
		Path path1 = Paths.get("routes.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabeçalho
			String airline, from, to, codeshare, stops, equipment;
			while (sc.hasNext()) {
				// airline;from;to;codeshare;stops;equipment
				airline = sc.next();
				from = sc.next();
				to = sc.next();
				// precisa?
				codeshare = sc.next();
				// precisa?
				stops = sc.next();
				equipment = sc.next();
				rotas.add(new Rota(cias.buscarNome(airline), aero.buscarCodigo(from), aero.buscarCodigo(to),
						aeron.buscarCodigo(equipment)));
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder aux = new StringBuilder();
		for (Rota r : rotas)
			aux.append(r + "\n");
		return aux.toString();
	}
}
