package pucrs.myflight.modelo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grafo {

	private List<Rota> rotas;
	private List<Aeroporto> aeroportos;
	private Rota[][] matriz;
	private Set<ArrayList<Rota>> rotasPossiveis;// Consulta 3
	private HashSet<Aeroporto> setAero;// Consulta 4

	public Grafo(ArrayList<Rota> rotas, ArrayList<Aeroporto> aeroportos) {
		this.rotas = rotas;
		this.aeroportos = aeroportos;
		this.matriz = new Rota[aeroportos.size()][aeroportos.size()];
		reseta();
		preenche();
	}

	public void preenche() {
		for (Rota r : rotas) {
			int x = aeroportos.indexOf(r.getOrigem());
			int y = aeroportos.indexOf(r.getDestino());
			matriz[x][y] = r;
		}
	}

	public void reseta() {
		this.rotasPossiveis = new HashSet<>();
	}

	public Set<ArrayList<Rota>> pesquisaTres(Aeroporto origem, Aeroporto destino) {
		int x = aeroportos.indexOf(origem);
		// int y = aeroportos.indeyOf(destino);
		ArrayList<Rota> umaRota = new ArrayList<>();
		HashSet<Aeroporto> visitados = new HashSet<>();
		visitados.add(origem);
		pesquisaTresAux(x, destino, umaRota, visitados);
		return rotasPossiveis;
	}

	private void pesquisaTresAux(int x, Aeroporto y, ArrayList<Rota> uma, HashSet<Aeroporto> visi) {

		if (x == -1 || visi.size() > 3)
			return;

		ArrayList<Rota> umaRota = new ArrayList<>(uma);
		HashSet<Aeroporto> visitados = new HashSet<>(visi);

		for (int i = 0; i < matriz.length; i++) {
			if (matriz[x][i] != null) {
				if (!visitados.contains(matriz[x][i].getDestino())) {
					visitados.add(matriz[x][i].getDestino());
					umaRota.add(matriz[x][i]);
					// IF i = y? matriz[x][i].getDestino().equals(y)
					if (matriz[x][i].getDestino().equals(y)) {
						ArrayList<Rota> umaRota2 = new ArrayList<>(umaRota);
						rotasPossiveis.add(umaRota2);
						pesquisaTresAux(-1, y, umaRota, visitados);
					} else {
						pesquisaTresAux(i, y, umaRota, visitados);
					}
					umaRota.remove(matriz[x][i]);
					visitados.remove(matriz[x][i].getDestino());

				}
			}
		}

	}

	public HashSet<Aeroporto> pesquisaQuatro(Aeroporto origem, double limite) {		
		int x = aeroportos.indexOf(origem);			
		setAero = new HashSet<>();
		setAero.add(origem);		
		pesquisaQuatroAux(x, 0, limite);
		return setAero;
	}

	private void pesquisaQuatroAux(int x, double tempoVoo, double limite) {
		double tempoVooAux = tempoVoo;		
		for (int i = 0; i < matriz.length; i++) {
			//!(setAero.contains(matriz[x][i].getDestino()) ESTE BUGA MUITO!
			if (matriz[x][i] != null) {	
				System.out.println(matriz[x][i]);
				tempoVooAux = tempoVooAux + calculaTempo(matriz[x][i]);			
				if (!(tempoVooAux > limite)) {
					/*
					  System.err.printf("%s %s %f %f\n",matriz[x][i].getOrigem().getCodigo(),					
							matriz[x][i].getDestino().getCodigo(),
							Geo.distancia(matriz[x][i].getOrigem().getLocal(), matriz[x][i].getDestino().getLocal()),
						    calculaTempo(matriz[x][i]));	
						    */							
					setAero.add(matriz[x][i].getDestino());
					pesquisaQuatroAux(i, tempoVooAux, limite);
				}
				tempoVooAux = tempoVoo;
			}
		}
	}

	public double calculaTempo(Rota rota) {		
		double alo = Geo.distancia(rota.getOrigem().getLocal(), rota.getDestino().getLocal());		
		//double antigo = Duration.ofSeconds((long) ((alo / 805 + 0.5) * 3600)).toMinutes();			
		return (alo / 805 + 0.5);
	}

}
