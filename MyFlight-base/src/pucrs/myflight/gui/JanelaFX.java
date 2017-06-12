package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.CiaAerea;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.GerenciadorAeronaves;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorCias;
import pucrs.myflight.modelo.GerenciadorPaises;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Rota;

public class JanelaFX extends Application {

	final SwingNode mapkit = new SwingNode();

	private GerenciadorCias gerCias;
	private GerenciadorPaises gerPaises;	
	private GerenciadorAeronaves gerAvioes;
	private GerenciadorAeroportos gerAero;
	private GerenciadorRotas gerRotas;	


	private GerenciadorMapa gerenciador;

	private EventosMouse mouse;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		setup();
		
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
		leftPane.setPadding(new Insets(10,10,10,10));
		
		
		
		Button btnConsulta = new Button("TESTEEEEEEEEEEEEEEE");			
		leftPane.add(btnConsulta, 0,0);	
		/*
		leftPane.add(new Button("BBBB"), 0,1);
		leftPane.add(new Button("CCCC"), 0,2);
		leftPane.add(new Button("DDDD"), 0,3);
		*/	
		btnConsulta.setOnAction(e -> {
			consulta();
		});
		
		
		//Botoes da Consulta 1
		Label consultaUmLB = new Label("Cons.1 : Lista Companhias");	
		
		ComboBox consultaUmCB = new ComboBox();
		//consultaUmCB.getItems().addAll(gerCias.getLista());
		
		Button consultaUmBT = new Button("Exibir");
		consultaUmBT.setOnAction(e -> {
			gerenciador.clear();	
			consultaUm(consultaUmCB);	
			gerenciador.getMapKit().repaint();
		});
		
		leftPane.add(consultaUmLB, 0,1);
		leftPane.add(consultaUmCB, 0, 2);
		leftPane.add(consultaUmBT, 0, 3);
		
		
		
		pane.setCenter(mapkit);
		pane.setLeft(leftPane);

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();

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
			System.out.println("Msg: "+e);
			System.exit(1);
		}
		
		//
		// Chame aqui a leitura dos demais arquivos
		//
		
		try {
			gerPaises.carregaDados();
		} catch (IOException e) {
			System.out.println("Impossivel ler countries.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}
		
		try {
			gerAvioes.carregaDados();
		} catch (IOException e) {
			System.out.println("Impossivel ler equipment.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}
		
		try {
			gerAero.carregaDados(gerPaises);
		} catch (IOException e) {
			System.out.println("Impossivel ler airports.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}		
		
		try {
			gerRotas.carregaDados(gerCias, gerAero, gerAvioes);
		} catch (IOException e) {
			System.out.println("Impossivel ler routes.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}	
	}
    
	private void consulta() {
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
        
        // Quando for o caso de limpar os traçados...
        //gerenciador.clear();
        
        // Exemplo: criando um traçado
        Tracado tr = new Tracado();        
        // Adicionando as mesmas localizações de antes
        tr.addPonto(locPoa);
        tr.addPonto(locGru);
        tr.setWidth(4);       // largura do traçado
        tr.setCor(Color.RED); // cor do traçado
        
        // E adicionando o traçado...
        gerenciador.addTracado(tr);        
        gerenciador.getMapKit().repaint();        
	}
	
	private void consultaUm(ComboBox consultaUmCB){
		//Lista de Aeroportos referentes (Pontinhos)
		Set<MyWaypoint> aeroportos = new HashSet<MyWaypoint>(); 
		
		//Pega a Cia Selecionada no ComboBox
		CiaAerea ciaSelecionada= (CiaAerea)consultaUmCB.getValue();
		
		List<Rota> rotas = gerRotas.listarTodas();	
		Set<Rota> setRotas = new HashSet<>();
		rotas.stream()
			.filter(e->e.getCia().equals(ciaSelecionada))
			.forEach(e -> setRotas.add(e));			
		
		Set<Aeroporto> setAeros = new HashSet<>();
		
		setRotas.forEach(e -> {
			setAeros.add(e.getOrigem());
			setAeros.add(e.getDestino());
		});
		
		setAeros.forEach(e -> {
			aeroportos.add(new MyWaypoint(Color.BLUE, e.getNome(), e.getLocal(), 5));
		});				
	}

	private class EventosMouse extends MouseAdapter {
		private int lastButton = -1;

		@Override
		public void mousePressed(MouseEvent e) {
			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
			// System.out.println(loc.getLatitude()+", "+loc.getLongitude());
			lastButton = e.getButton();
			// Botão 3: seleciona localização
			if (lastButton == MouseEvent.BUTTON3) {
				gerenciador.setPosicao(loc);				
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
