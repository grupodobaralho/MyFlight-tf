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
		} catch (IOException e) {
			System.out.println("Erro carregando dados de cias.");
			System.exit(1);
		}

		System.out.println(gerAvioes.toString());

		/*
		 * 
		 * ArrayList<Rota> arrayTest = new ArrayList<>(gerRotas.listarTodas());
		 * for(Rota r : arrayTest) { System.out.println("Cia: "+ r.getCia() +
		 * " Origem: " + r.getOrigem() + " Destino: " + r.getDestino() +
		 * " Aeronave: " + r.getAeronave() + "\n"); }
		 */

	}
}
