package pucrs.myflight.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
		//Collections.sort(rotas);
		rotas.sort((Rota r1, Rota r2)
			-> r1.getCia().getNome().compareTo(r2.getCia().getNome()));
	}
	
	public void ordenaOrigem() {
		rotas.sort( (Rota r1, Rota r2)
			-> r1.getOrigem().getNome().compareTo(r2.getOrigem().getNome()));
	}
	
	public void ordenaOrigemCia() {
		/*
		rotas.sort( (Rota r1, Rota r2) -> {
			int r = r1.getOrigem().getNome().compareTo(r2.getOrigem().getNome());
			if (r!=0)
				return r;
			return r1.getCia().getNome().compareTo(r2.getCia().getNome());
		});
		*/
		rotas.sort(Comparator.comparing((Rota r) -> r.getOrigem().getNome()).
				thenComparing(r -> r.getCia().getNome()));
	}
	
	public ArrayList<Rota> buscarOrigem(Aeroporto origem) {
		ArrayList<Rota> lista = new ArrayList<>();
		for(Rota r : rotas) {			
//			System.out.println(r.getOrigem().getCodigo());
			if(origem.getCodigo().equals(r.getOrigem().getCodigo()))
				lista.add(r);					
		}
		return lista;
	}
	
	@Override
	public String toString() {		
		StringBuilder aux = new StringBuilder();
		for(Rota r: rotas)
			aux.append(r + "\n");			
		return aux.toString();
	}
}
