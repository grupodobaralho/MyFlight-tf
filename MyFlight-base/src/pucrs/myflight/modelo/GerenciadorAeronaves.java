package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class GerenciadorAeronaves {

	private HashMap<String, Aeronave> aeronaves;

	public GerenciadorAeronaves() {
		aeronaves = new HashMap<>();
	}	
	
	public void carregaDados() throws IOException {
		Path path1 = Paths.get("equipment.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabeçalho
			String id, descricao;
			int capacidade = 0;
			while (sc.hasNext()) {
				id = sc.next();
				descricao = sc.next();
				try{
					capacidade = Integer.parseInt(sc.next());
										
				}catch (NumberFormatException e) {
				    e.printStackTrace();
				}	
				Aeronave aero = new Aeronave(id, descricao, capacidade);
				aeronaves.put(aero.getCodigo(),aero);
			}
		}
	}	
	
	public HashMap<String, Aeronave> getHash(){
		return aeronaves;
	}
	
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		for (HashMap.Entry<String, Aeronave> entry : aeronaves.entrySet())
		{
		    str.append(entry.getValue()+"\n");
		}
		return str.toString();
	}
	
}
