package pucrs.myflight.modelo;

import java.util.ArrayList;
import java.util.Collections;

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
		for(Aeroporto a: aeroportos)
			if(a.getCodigo().equals(codigo))
				return a;
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder aux = new StringBuilder();
		for(Aeroporto a: aeroportos)
			aux.append(a + "\n");			
		return aux.toString();
	}
}
