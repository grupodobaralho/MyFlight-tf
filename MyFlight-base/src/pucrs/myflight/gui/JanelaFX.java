package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.CiaAerea;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.GerenciadorAeronaves;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorCias;
import pucrs.myflight.modelo.GerenciadorPaises;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Grafo;
import pucrs.myflight.modelo.Pais;
import pucrs.myflight.modelo.Rota;

public class JanelaFX extends Application {

	final SwingNode mapkit = new SwingNode();

	private GerenciadorCias gerCias;
	private GerenciadorPaises gerPaises;
	private GerenciadorAeronaves gerAvioes;
	private GerenciadorAeroportos gerAero;
	private GerenciadorRotas gerRotas;

	private Grafo grafo;
	private Aeroporto aeroSelecionado;

	private GerenciadorMapa gerenciador;

	private EventosMouse mouse;

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Carrega os dado=========================================
		setup();

		// Seta os atributos do JavaFX=============================
		GeoPosition poa = new GeoPosition(-30.05, -51.18);
		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);

		createSwingContent(mapkit);

		BorderPane pane = new BorderPane();
		GridPane leftPane = new GridPane();

		leftPane.setAlignment(Pos.CENTER);
		leftPane.setHgap(10);
		leftPane.setVgap(10);
		leftPane.setPadding(new Insets(10, 10, 10, 10));
		// leftPane.setGridLinesVisible(true);
		// ==========================================================

		// Teste do Professor========================================
		Button btnConsulta = new Button("Teste do Professor");
		leftPane.add(btnConsulta, 0, 0);
		btnConsulta.setOnAction(e -> {
			consulta();
		});
		// ==========================================================
		
		leftPane.add(new Separator(), 0, 1);
		
		// Botoes da Consulta 1======================================
		Label consultaUmLB = new Label("Cons.1 : Lista Companhias");
		ComboBox consultaUmCB = new ComboBox();
		consultaUmCB.getItems().addAll(gerCias.listarCiasAereas());
		Button consultaUmBT = new Button("Exibir");
		consultaUmBT.setOnAction(e -> {
			gerenciador.clear();
			consultaUm(consultaUmCB);
			gerenciador.getMapKit().repaint();
		});
		leftPane.add(consultaUmLB, 0, 2);
		leftPane.add(consultaUmCB, 0, 3);
		leftPane.add(consultaUmBT, 0, 4);
		// ==========================================================
		
		leftPane.add(new Separator(), 0, 5);
		
		// Botoes da consulta 2=======================================
		Label consultaDoisLB = new Label("Cons.2 : Estimativa de volume de trafego");
		ComboBox consultaDoisCB = new ComboBox();
		consultaDoisCB.getItems().add("Todos os paÌses");
		consultaDoisCB.getItems().addAll(gerPaises.listarPaises());
		Button consultaDoisBT = new Button("Exibir");
		consultaDoisBT.setOnAction(e -> {
			gerenciador.clear();
			consultaDois(consultaDoisCB);
			gerenciador.getMapKit().repaint();
		});
		
		leftPane.add(consultaDoisLB, 0, 6);
		leftPane.add(consultaDoisCB, 0, 7);
		leftPane.add(consultaDoisBT, 0, 8);
		// ===========================================================

		leftPane.add(new Separator(), 0, 9);
		
		// Botoes da Consulta 3======================================
		Label consultaTresLB = new Label("Cons.3 : Mostra todas rotas entre 2 aeroportos");

		Label origemLB = new Label("Origem");
		TextField origemTF = new TextField();
		origemTF.setPromptText("CÛdigo da origem");
		HBox origemHB = new HBox(origemLB, origemTF);
		origemHB.setSpacing(10);

		Label destinoLB = new Label("Destino");
		TextField destinoTF = new TextField();
		destinoTF.setPromptText("CÛdigo do destino");
		HBox destinoHB = new HBox(destinoLB, destinoTF);
		destinoHB.setSpacing(10);

		Button consultaTrestBT = new Button("Buscar");

		Label invalido = new Label("CÛdigos informados inv·lidos!");
		invalido.setVisible(false);

		HBox buscarRotas_invalido = new HBox(consultaTrestBT, invalido);

		consultaTrestBT.setOnAction(e -> {
			gerenciador.clear();
			Aeroporto aeroOrigem = gerAero.getAeroporto(origemTF.getText());
			Aeroporto aeroDestino = gerAero.getAeroporto(destinoTF.getText());
			if (aeroOrigem == null || aeroDestino == null || aeroOrigem.equals(aeroDestino))
				invalido.setVisible(true);
			else {
				consultaTres(aeroOrigem, aeroDestino);
				invalido.setVisible(false);
			}
			gerenciador.getMapKit().repaint();
		});
		leftPane.add(consultaTresLB, 0, 10);
		leftPane.add(origemHB, 0, 11);
		leftPane.add(destinoHB, 0, 12);
		leftPane.add(buscarRotas_invalido, 0, 13);
		// ==========================================================

		leftPane.add(new Separator(), 0, 14);
		
		// Consulta 4 ===============================================	
		Label consultaQuatroLB = new Label("Cons.4 : Mostrar todos os aeroportos alcancaveis");	
		Label consultaQuatroLB2 = new Label("(Selecione o Aeroporto no mapa)");		
		
		Label tempoLB = new Label("Tempo maximo de voo (em horas)");		
		Slider distSli = new Slider(0, 40, 0);
		
		Button consultaQuatrotBT = new Button("Buscar");	
		
		distSli.setShowTickMarks(true);
		distSli.setShowTickLabels(true);
		distSli.setMajorTickUnit(5);		
		distSli.setBlockIncrement(1);
		distSli.setMinWidth(50);
		
		consultaQuatrotBT.setOnAction(e -> {
			gerenciador.clear();
			consultaQuatro(distSli.getValue());
			gerenciador.getMapKit().repaint();
		});
		leftPane.add(consultaQuatroLB, 0, 15);	
		leftPane.add(consultaQuatroLB2, 0, 16);	
		leftPane.add(tempoLB, 0, 17);			
		leftPane.add(distSli, 0, 18);	
		leftPane.add(consultaQuatrotBT, 0, 19);			
		// ==========================================================
		
		leftPane.add(new Separator(), 0, 20);
		
		// Funcao teste que exibe todos os Aeroportos================
		Label exibeTodosLB = new Label("Mostrar  todos Aeroportos");		
		Button exibeTodosBT = new Button("Exibir");
		exibeTodosBT.setOnAction(e -> {
			exibeTodos();
		});
		leftPane.add(exibeTodosLB, 0, 21);
		leftPane.add(exibeTodosBT, 0, 22);
		// ==========================================================
		
		leftPane.add(new Separator(), 0, 23);
		
		// Botao Limpar tela=========================================
		Label clearLB = new Label("Limpar tela");
		Button clearBT = new Button("Limpar");
		clearBT.setOnAction(e -> limparTela());
		leftPane.add(clearLB, 0, 24);
		leftPane.add(clearBT, 0, 25);
		// ==========================================================
		
		// Montando a tela do JavaFX=================================
		pane.setCenter(mapkit);
		pane.setLeft(leftPane);

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();
		// ==========================================================

	}

	// Inicializando os dados aqui...
	private void setup() {

		gerCias = new GerenciadorCias();
		gerPaises = new GerenciadorPaises();
		gerAvioes = new GerenciadorAeronaves();
		gerAero = new GerenciadorAeroportos();
		gerRotas = new GerenciadorRotas();

		try {
			gerCias.carregaDados();
		} catch (IOException e) {
			System.out.println("Impossivel ler airlines.dat!");
			System.out.println("Msg: " + e);
			System.exit(1);
		}

		//
		// Chame aqui a leitura dos demais arquivos
		//

		try {
			gerPaises.carregaDados();
		} catch (IOException e) {
			System.out.println("Impossivel ler countries.dat!");
			System.out.println("Msg: " + e);
			System.exit(1);
		}

		try {
			gerAvioes.carregaDados();
		} catch (IOException e) {
			System.out.println("Impossivel ler equipment.dat!");
			System.out.println("Msg: " + e);
			System.exit(1);
		}

		try {
			gerAero.carregaDados(gerPaises);
		} catch (IOException e) {
			System.out.println("Impossivel ler airports.dat!");
			System.out.println("Msg: " + e);
			System.exit(1);
		}

		try {
			gerRotas.carregaDados(gerCias, gerAero, gerAvioes);
		} catch (IOException e) {
			System.out.println("Impossivel ler routes.dat!");
			System.out.println("Msg: " + e);
			System.exit(1);
		}
		
		grafo = new Grafo(gerRotas.listarTodas(), gerAero.listarAeroportos());
	}
	
	/**
	 * Limpa a tela
	 */
	private void limparTela() {
		gerenciador.clear();
		gerenciador.getMapKit().repaint();
	}

	/**
	 * Exemplo de consulta do professor.
	 */
	private void consulta() {
		gerenciador.clear();
		// Para obter um ponto clicado no mapa, usar como segue:
		GeoPosition pos = gerenciador.getPosicao();

		// Lista para armazenar o resultado da consulta
		List<MyWaypoint> lstPoints = new ArrayList<>();

		// Exemplo de uso:

		Aeroporto poa = gerAero.getHash().get("POA");
		Aeroporto gru = gerAero.getHash().get("GRU");

		Geo locPoa = poa.getLocal();
		Geo locGru = gru.getLocal();

		lstPoints.add(new MyWaypoint(Color.BLUE, poa.getNome(), locPoa, 10));
		lstPoints.add(new MyWaypoint(Color.RED, gru.getNome(), locGru, 5));

		// Informa o resultado para o gerenciador
		gerenciador.setPontos(lstPoints);

		// Quando for o caso de limpar os tra√ßados...
		// gerenciador.clear();

		// Exemplo: criando um tra√ßado
		Tracado tr = new Tracado();
		// Adicionando as mesmas localiza√ß√µes de antes
		tr.addPonto(locPoa);
		tr.addPonto(locGru);
		tr.setWidth(4); // largura do tra√ßado
		tr.setCor(Color.RED); // cor do tra√ßado

		// E adicionando o tra√ßado...
		gerenciador.addTracado(tr);
		gerenciador.getMapKit().repaint();
	}

	/**
	 * Exibe todos os Aeroportos no mapa
	 */
	private void exibeTodos() {
		gerenciador.clear();
		List<MyWaypoint> pontos = new ArrayList<>();
		List<Aeroporto> aeroportos = gerAero.listarAeroportos();
		for (Aeroporto a : aeroportos)
			pontos.add(new MyWaypoint(Color.RED, a.getNome(), a.getLocal(), 4));
		gerenciador.setPontos(pontos);
	}

	// Funcional, porÈm necessita ser refatorado;
	private void consultaDois(ComboBox consultaDoisCb) {
		gerenciador.clear();
		List<MyWaypoint> pontos = new ArrayList<>();
		List<Aeroporto> aeroportos = gerAero.listarAeroportos();
		List<Rota> rotas = gerRotas.listarTodas();
		int cont = 0;
		if (consultaDoisCb.getValue().toString().equalsIgnoreCase("Todos os paÌses")) {
			for (Aeroporto a : aeroportos) {
				System.out.println(a);
				cont = 0;
				for (int i = 0; i < rotas.size(); i++) {
					if (rotas.get(i).getOrigem().equals(a))
						cont++;
					if (rotas.get(i).getDestino().equals(a))
						cont++;
					if (rotas.size() - i == 1) {
						System.out.println(cont);
						if (cont < 10) {
							pontos.add(new MyWaypoint(Color.RED, a.getNome(), a.getLocal(), 4));
						}

						else if (cont >= 10 && cont < 50) {
							pontos.add(new MyWaypoint(Color.GREEN, a.getNome(), a.getLocal(), 6));
						}

						else if (cont >= 50 && cont < 100) {
							pontos.add(new MyWaypoint(Color.YELLOW, a.getNome(), a.getLocal(), 8));
						}

						else
							pontos.add(new MyWaypoint(Color.MAGENTA, a.getNome(), a.getLocal(), 10));

					}

				}
			}
		} else {

			Pais pais = (Pais) consultaDoisCb.getValue();

			System.out.println(pais);
			System.out.println();
			for (Aeroporto a : aeroportos) {
				System.out.println(a.getPais());
				if (!a.getPais().equals(pais))
					continue;
				System.out.println(a);
				cont = 0;
				for (int i = 0; i < rotas.size(); i++) {
					if (rotas.get(i).getOrigem().equals(a) && a.getPais().equals(pais))
						cont++;
					if (rotas.get(i).getDestino().equals(a) && a.getPais().equals(pais))
						cont++;
					if (rotas.size() - i == 1) {
						System.out.println(cont);
						if (cont < 10) {
							pontos.add(new MyWaypoint(Color.red, a.getNome(), a.getLocal(), 4));
						}

						else if (cont >= 10 && cont < 50) {
							pontos.add(new MyWaypoint(Color.GREEN, a.getNome(), a.getLocal(), 6));
						}

						else if (cont >= 50 && cont < 100) {
							pontos.add(new MyWaypoint(Color.YELLOW, a.getNome(), a.getLocal(), 8));
						}

						else
							pontos.add(new MyWaypoint(Color.magenta, a.getNome(), a.getLocal(), 10));

					}

				}
			}

		}

		gerenciador.setPontos(pontos);
	}

	/**
	 * Desenha todos os aeroportos onde uma determinada companhia aÈrea opera.
	 * Mostra tambÈm as rotas envolvidas
	 * 
	 * @param consultaUmCB
	 */
	private void consultaUm(ComboBox consultaUmCB) {
		gerenciador.clear();

		// Lista de Aeroportos referentes (Pontinhos)
		List<MyWaypoint> aeroportos = new ArrayList<MyWaypoint>();

		// Pega a Cia Selecionada no ComboBox
		CiaAerea ciaSelecionada = (CiaAerea) consultaUmCB.getValue();

		List<Rota> rotas = gerRotas.listarTodas();
		Set<Rota> setRotas = new HashSet<>();
		rotas.stream().filter(e -> e.getCia().equals(ciaSelecionada)).forEach(e -> setRotas.add(e));

		Set<Aeroporto> setAeros = new HashSet<>();

		setRotas.forEach(e -> {
			setAeros.add(e.getOrigem());
			setAeros.add(e.getDestino());
			Tracado tr = new Tracado();
			tr.setCor(Color.GREEN);
			tr.addPonto(e.getOrigem().getLocal());
			tr.addPonto(e.getDestino().getLocal());
			gerenciador.addTracado(tr);
		});

		setAeros.forEach(e -> {
			aeroportos.add(new MyWaypoint(Color.BLUE, e.getNome(), e.getLocal(), 4));
		});
		gerenciador.setPontos(aeroportos);
	}

	/**
	 * Desenha todos os aeroportos onde uma determinada companhia aÈrea opera.
	 * Mostra tambÈm as rotas envolvidas
	 * 
	 * @param consultaUmCB
	 */
	private void consultaTres(Aeroporto origemTF, Aeroporto destinoTF) {
		// SCK - GRU VIX - GRU
		limparTela();
		grafo.reseta();
		// Realiza consulta no grafo
		Set<ArrayList<Rota>> listaConsulta3 = new HashSet<>(grafo.encontra(origemTF, destinoTF));

		// Lista de Aeroportos referentes (Pontinhos)
		List<MyWaypoint> aeroportos = new ArrayList<MyWaypoint>();
		Set<MyWaypoint> setAeros = new HashSet<MyWaypoint>();

		listaConsulta3.forEach(e -> {
			int i = 0;
			for (Rota r : e) {
				setAeros.add(new MyWaypoint(Color.BLACK, r.getOrigem().getNome(), r.getOrigem().getLocal(), 4));
				setAeros.add(new MyWaypoint(Color.BLACK, r.getDestino().getNome(), r.getDestino().getLocal(), 4));
				Tracado tr = new Tracado();
				switch (i) {
				case 0:
					tr.setCor(Color.RED);
					break;
				case 1:
					tr.setCor(Color.GREEN);
					break;
				case 2:
					tr.setCor(Color.BLUE);
					break;
				default:
					tr.setCor(Color.BLACK);
				}
				tr.addPonto(r.getOrigem().getLocal());
				tr.addPonto(r.getDestino().getLocal());
				tr.setWidth(2);
				gerenciador.addTracado(tr);
				i++;
			}
			gerenciador.getMapKit().repaint();
		});

		setAeros.forEach(e -> {
			aeroportos.add(e);
		});
		gerenciador.setPontos(aeroportos);		
	}
	
	private void consultaQuatro(double tempo){
		Set<Aeroporto> teste = grafo.pesquisaQuatro(aeroSelecionado, tempo);		
		// Lista de Aeroportos referentes (Pontinhos)
		List<MyWaypoint> aeroportos = new ArrayList<MyWaypoint>();					
		teste.forEach(e -> {
			aeroportos.add(new MyWaypoint(Color.MAGENTA, e.getNome(), e.getLocal(), 4));
		});		
		gerenciador.setPontos(aeroportos);
	}
	
	private Aeroporto aeroSelecionado(){
		return aeroSelecionado = gerAero.buscarAeroProximo(gerenciador.getPosicao());
	}
	
	private class EventosMouse extends MouseAdapter {
		private int lastButton = -1;

		@Override
		public void mousePressed(MouseEvent e) {
			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
			// System.out.println(loc.getLatitude()+", "+loc.getLongitude());
			lastButton = e.getButton();
			// Bot√£o 3: seleciona localiza√ß√£o
			if (lastButton == MouseEvent.BUTTON3) {
				gerenciador.setPosicao(loc);
				aeroSelecionado = aeroSelecionado();
				gerenciador.getMapKit().repaint();
			}
		}
	}

	private void createSwingContent(final SwingNode swingNode) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swingNode.setContent(gerenciador.getMapKit());
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}