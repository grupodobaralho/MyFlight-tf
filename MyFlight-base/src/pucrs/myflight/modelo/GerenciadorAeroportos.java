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
				Aeroporto aero = new Aeroporto(code, name, new Geo(latitude, longitude));
				aeroportos.put(aero.getCodigo(), aero);

			}
		}
	}
	
	public HashMap<String, Aeroporto> getHash(){
		return aeroportos;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (HashMap.Entry<String, Aeroporto> entry : aeroportos.entrySet())
		{
		    str.append(entry.getValue()+"\n");
		}
		return str.toString();
	}
}
