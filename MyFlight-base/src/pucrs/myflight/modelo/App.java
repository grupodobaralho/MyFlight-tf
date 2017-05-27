package pucrs.myflight.modelo;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class App {

	public static void main(String[] args) {
		
		GerenciadorCias gerCias = new GerenciadorCias();
		GerenciadorAeroportos gerAero = new GerenciadorAeroportos();
		GerenciadorAeronaves gerAvioes = new GerenciadorAeronaves();
		GerenciadorRotas gerRotas = new GerenciadorRotas();		
		GerenciadorPaises gerPaises = new GerenciadorPaises();
					
		
		try {
			gerCias.carregaDados();
			gerPaises.carregaDados();
			gerAvioes.carregaDados();
			gerAero.carregaDados(gerPaises);
			gerRotas.carregaDados(gerCias, gerAero, gerAvioes);			
			//gerCias.gravaSerial();
			//gerCias.carregaSerial();
		} catch (IOException e) {
			System.out.println("Erro carregando dados de cias.");
			System.exit(1);
		}
		
		ArrayList<Rota> arrayTest = new ArrayList<>(gerRotas.listarTodas());
		for(Rota r : arrayTest) {
			System.out.println("Cia: "+ r.getCia() + " Origem: " + r.getOrigem() + " Destino: " + r.getDestino() + " Aeronave: " + r.getAeronave() + "\n");
		}
		
		/*		
		CiaAerea gol = gerCias.buscarCodigo("G3");
		CiaAerea latam = gerCias.buscarCodigo("JJ");
		//System.out.println("Cias: "+CiaAerea.getTotalCias());
		//CiaAerea gol = new CiaAerea("G3", "Gol Linhas Aéreas SA");
		//System.out.println("Cias: "+CiaAerea.getTotalCias());
		//CiaAerea latam = new CiaAerea("JJ", "LATAM Linhas Aéreas");
		//System.out.println("Cias: "+CiaAerea.getTotalCias());
		//gerCias.adicionar(gol);
		//gerCias.adicionar(latam);
		//System.out.println("Cias: "+latam.getTotalCias());
		
		//Geo locPoa = new Geo(-29.9939, -51.1711);
		Aeroporto poa = new Aeroporto("POA", "Salgado Filho Intl Apt",
				new Geo(-29.9939, -51.1711));
		Aeroporto gru = new Aeroporto("GRU", "São Paulo Guarulhos Intl Apt",
				new Geo(-23.4356, -46.4731));
		Aeroporto mia = new Aeroporto("MIA", "Miami International Apt",
				new Geo(25.7933,-80.2906));
		
		gerAero.adicionar(poa);
		gerAero.adicionar(gru);
		gerAero.adicionar(mia);
		
		gerAvioes.adicionar(new Aeronave("73G", "Boeing 737-700", 126));
		gerAvioes.adicionar(new Aeronave("733", "Boeing 737-300", 140));
		gerAvioes.adicionar(new Aeronave("380", "Airbus Industrie A380", 644));
		gerAvioes.adicionar(new Aeronave(null, "blablabla", 1));
		
		Aeronave a1 = gerAvioes.buscarCodigo("733");
		Aeronave a2 = gerAvioes.buscarCodigo("73G");		
		
		gerRotas.adicionar(new Rota(gol, gru,
			poa, gerAvioes.buscarCodigo("733")));
		gerRotas.adicionar(new Rota(latam, gru,
				mia, gerAvioes.buscarCodigo("380")));
		gerRotas.adicionar(new Rota(gol, poa,
				gru, gerAvioes.buscarCodigo("73G")));		
		
		System.out.println();	
		ArrayList<Rota> rotas = gerRotas.listarTodas();
		for(Rota r: rotas)
			System.out.println(r.getCia().getNome()+"-"
					+r.getOrigem().getNome()+" -> "
					+r.getDestino().getNome());
		GerenciadorVoos gerVoos = new GerenciadorVoos();
		System.out.println();
		
		// Vôo com duração de 1h30min GRU->POA
		LocalDateTime datahora1 = LocalDateTime.of(2016,
				8, 18, 8, 30);
		Duration duracao1 = Duration.ofMinutes(90);
		
		// Rota: GRU -> POA
		Rota rota1 = rotas.get(0);
		// Rota: GRU -> MIA
		Rota rota2 = rotas.get(1);
		// Rota: POA -> GRU
		Rota rota3 = rotas.get(2);
		
		Voo voo1 = new VooDireto(datahora1, rota1);
		//Voo voo2 = new VooDireto(rota1);
				
		gerVoos.adicionar(voo1);
		//gerVoos.adicionar(voo2);
		
		ArrayList<Voo> listaVoos = gerVoos.listarTodos();
		for(Voo v: listaVoos) {			
			System.out.println("Voo: "+v);
			System.out.println(" dur: "+v.getDuracao().toMinutes());			
		}
		
		// Teste: procurar o vôo
		ArrayList<Voo> meusVoos = gerVoos.buscarData(LocalDate.of(2016, 8, 18));
		for(Voo v: meusVoos) {
			System.out.println(v.getRota().getOrigem().getNome());
			System.out.println(v.getRota().getDestino().getNome());
			System.out.println(v.getDatahora());
		}
		
		double dist = Geo.distancia(poa.getLocal(), gru.getLocal());
		System.out.println("Dist. POA->GRU (static): "+dist);
		double dist2 = poa.getLocal().distancia(gru.getLocal());
		System.out.println("Dist. POA->GRU: "+dist2);
		double dist3 = gru.getLocal().distancia(poa.getLocal());
		System.out.println("Dist. GRU->POA: "+dist3);
		
		// Teste de ordenação:
		// Mostra lista de aeronaves antes de ordenar
		System.out.println("\nLista antes de ordenar:\n"+gerAvioes);
		gerAvioes.ordenaDescricao();
		System.out.println("\nLista depois de ordenar por descrição:\n"+gerAvioes);
		gerAvioes.ordenaCodigo();
		System.out.println("\nLista depois de ordenar por código:\n"+gerAvioes);
		
		// Teste de ordenação - exerc. com Comparator
		System.out.println("Rotas antes de ordenar:\n"+gerRotas);
		gerRotas.ordenaCia();
		System.out.println("Rotas depois de ordenar pelo nome da cia:\n"+gerRotas);
		
		System.out.println("Aeroportos antes de ordenar:\n"+gerAero);
		gerAero.ordenaNome();
		System.out.println("Aeroportos depois de ordenar pelo nome:\n"+gerAero);
			
		gerRotas.ordenaOrigem();
		System.out.println("Rotas depois de ordenar pelo nome do aer. de origem:\n"+gerRotas);
		
		gerRotas.ordenaOrigemCia();
		System.out.println("Rotas depois de ordenar pelo aer. de origem + cia:\n"+gerRotas);
		
		// Ordenando vôos por data+hora
		gerVoos.ordenaDataHora();
		listaVoos = gerVoos.listarTodos();
		for(Voo v: listaVoos) {			
			System.out.println("Voo: "+v);
			System.out.println(" dur: "+v.getDuracao().toMinutes());			
		}
		
		LocalDateTime datahora2 = LocalDateTime.of(2017,
						4, 11, 8, 0);
		// Não precisamos mais de duração: ela será calculada automaticamente
		// pela distância percorrida
//		Duration duracao2 = Duration.ofHours(12);				
				
		VooEscalas voo3 = new VooEscalas(datahora2);
		voo3.adicionarEscala(rota3);			
		System.out.println("Vôo com uma escala: "+voo3);
		
		VooEscalas voo4 = new VooEscalas(datahora2);
		voo4.adicionarEscala(rota3);
		voo4.adicionarEscala(rota2);		
		System.out.println("Vôo com duas escalas: "+voo4);
		
		// Polimorfismo de variáveis
		gerVoos.adicionar(voo3);
		gerVoos.adicionar(voo4);
		
		// Polimorfismo de métodos: exemplo do toString (varia entre as classes)
		System.out.println("-------------------\n");
		listaVoos = gerVoos.listarTodos();
		for(Voo v: listaVoos) {			
			System.out.println("Voo: "+v);
			System.out.println("   >>> Duração: "+v.getDuracao().toMinutes());
			if(v instanceof VooEscalas) {
				VooEscalas aux = (VooEscalas) v;
				System.out.println(">>> Escalas: "+aux.getTotalEscalas());
			}
		}
		*/
	}
}
