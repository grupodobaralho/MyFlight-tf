package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.jxmapviewer.viewer.GeoPosition;

public class GerenciadorAeroportos {

	private ArrayList<Aeroporto> aeroportos;

	public GerenciadorAeroportos() {
		aeroportos = new ArrayList<>();
	}

	public void adicionar(Aeroporto a) {
		aeroportos.add(a);
	}

	public ArrayList<Aeroporto> listarTodos() {
		return new ArrayList<Aeroporto>(aeroportos);
	}

	public void ordenaNome() {
		Collections.sort(aeroportos);
	}

	public Aeroporto buscarCodigo(String codigo) {
		for (Aeroporto a : aeroportos)
			if (a.getCodigo().equals(codigo))
				return a;
		return null;
	}

	public void carregaDados(GerenciadorPaises paises) throws IOException {
		Path path1 = Paths.get("airports.dat");

		try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha

			String header = sc.nextLine(); // pula cabeçalho

			String code, name, cCode;
			double latitude = 0;
			double longitude = 0;

			while (sc.hasNext()) {
				// # iata code; latitude; longitude; airport name; country code
				code = sc.next();
				try {
					latitude = Double.parseDouble(sc.next());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				try {
					longitude = Double.parseDouble(sc.next());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				name = sc.next();
				cCode = sc.next();

				aeroportos.add(new Aeroporto(code, name, new Geo(latitude, longitude)));

			}
		}
	}

	@Override
	public String toString() {
		StringBuilder aux = new StringBuilder();
		for (Aeroporto a : aeroportos)
			aux.append(a + "\n");
		return aux.toString();
	}
}
