package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import org.jxmapviewer.viewer.GeoPosition;

public class GerenciadorAeroportos {

	private HashMap<String, Aeroporto> aeroportos;

	public GerenciadorAeroportos() {
		aeroportos = new HashMap<>();
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
				Aeroporto aero = new Aeroporto(code, name, new Geo(latitude, longitude), paises.getHash().get(cCode));
				aeroportos.put(aero.getCodigo(), aero);

			}
		}
	}

	public Aeroporto getAeroporto(String codigo) {

		return aeroportos.get(codigo);
	}

	public void addAeroporto(Aeroporto aero) {
		aeroportos.put(aero.getCodigo(), aero);
	}

	public HashMap<String, Aeroporto> getHash() {
		return aeroportos;
	}

	public ArrayList<Aeroporto> listarAeroportos() {
		ArrayList<Aeroporto> retorno = new ArrayList<>();

		for (HashMap.Entry<String, Aeroporto> entry : aeroportos.entrySet()) {
			retorno.add(entry.getValue());
		}

		return retorno;
	}
	
	public Aeroporto buscarAeroProximo(GeoPosition pos){
		
		Aeroporto aero = listarAeroportos().stream()
				.filter(a -> Geo.distancia(a.getLocal(), pos)<=15)
				.findAny().get();		
		return aero;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (HashMap.Entry<String, Aeroporto> entry : aeroportos.entrySet()) {
			str.append(entry.getValue() + "\n");
		}
		return str.toString();
	}
}
