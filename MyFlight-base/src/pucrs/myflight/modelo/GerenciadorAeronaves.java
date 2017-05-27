package pucrs.myflight.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
}
