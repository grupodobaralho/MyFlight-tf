package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

public class VooDireto extends Voo {

	private Rota rota;
	
	public VooDireto(LocalDateTime datahora, Rota rota) {
		super(datahora);
		this.rota = rota;
	}
	
	@Override
	public Rota getRota() {
		return rota;
	}

	@Override
	public Duration getDuracao() {
		double dist = rota.getOrigem().getLocal()
				.distancia(rota.getDestino().getLocal());
		double dur = dist / 805 + 0.5;
		int minutos = (int) (dur * 60);
		return Duration.ofMinutes(minutos);
	}
}
