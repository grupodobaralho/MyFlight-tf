package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VooEscalas extends Voo {
	
	private ArrayList<Rota> rotas;
	
	public VooEscalas(LocalDateTime datahora)
	{
		super(datahora);
		rotas = new ArrayList<>();		
	}
	
	public void adicionarEscala(Rota r) {
		rotas.add(r);
	}
	
	public int getTotalEscalas() { 
		return rotas.size();		
	}
	
	@Override
	public String toString() {
		StringBuilder aux = new StringBuilder();
		for(Rota r: rotas)
			aux.append("\n   "+r);
		return super.toString() + aux.toString();
	}

	@Override
	public Rota getRota() {
		if(rotas.size() > 0)
			return rotas.get(0);
		else
			return null;
	}

	@Override
	public Duration getDuracao() {
		// TODO: passar por todas as escalas, acumulando
		// as distâncias...
		return Duration.ofMinutes(0); // calcular corretamente!
	}

}
