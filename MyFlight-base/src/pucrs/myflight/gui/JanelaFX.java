package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorCias;
import pucrs.myflight.modelo.GerenciadorRotas;

public class JanelaFX extends Application {

	final SwingNode mapkit = new SwingNode();

	private GerenciadorCias gerCias;
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
		Button btnConsulta = new Button("AAAA");
		leftPane.add(btnConsulta, 0,0);
		leftPane.add(new Button("BBBB"), 0,1);
		leftPane.add(new Button("CCCC"), 0,2);
		leftPane.add(new Button("DDDD"), 0,3);
		btnConsulta.setOnAction(e -> {
			consulta();
		});
		
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
    	gerAero = new GerenciadorAeroportos();
    	gerRotas = new GerenciadorRotas();
    	
		try {
			gerCias.carregaDados();
		} catch (IOException e) {
			System.out.println("Impossível ler airlines.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}
		
		//
		// Chame aqui a leitura dos demais arquivos
		//
		
		Aeroporto poa = new Aeroporto("POA", "Salgado Filho Intl Apt",
				new Geo(-29.9939, -51.1711));
		Aeroporto gru = new Aeroporto("GRU", "São Paulo Guarulhos Intl Apt",
				new Geo(-23.4356, -46.4731));
		Aeroporto mia = new Aeroporto("MIA", "Miami International Apt",
				new Geo(25.7933,-80.2906));
		gerAero.adicionar(poa);
		gerAero.adicionar(gru);
		gerAero.adicionar(mia);
	}
    
	private void consulta() {
		// Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     

        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        // Exemplo de uso:
        
        Aeroporto poa = gerAero.buscarCodigo("POA");
        Aeroporto gru = gerAero.buscarCodigo("GRU"); 
        
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
