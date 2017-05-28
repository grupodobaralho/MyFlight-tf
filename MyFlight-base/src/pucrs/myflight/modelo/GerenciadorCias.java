package pucrs.myflight.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class GerenciadorCias implements Iterable<CiaAerea> {
	private ArrayList<CiaAerea> empresas;

	public GerenciadorCias() {
		empresas = new ArrayList<>();
	}

	public void adicionar(CiaAerea cia) {
		empresas.add(cia);
	}
	
	public void gravaSerial() throws IOException {
		Path arq1 = Paths.get("cias.ser");
		try (ObjectOutputStream oarq = new ObjectOutputStream(Files.newOutputStream(arq1))) {
		  oarq.writeObject(empresas);
		}
		catch(IOException e) {
		  System.out.println(e.getMessage());
		  System.exit(1);
		}
	}
	
	public void carregaSerial() throws IOException {
		Path arq1 = Paths.get("cias.ser");
		try (ObjectInputStream iarq = new ObjectInputStream(Files.newInputStream(arq1))) {
		  empresas = (ArrayList<CiaAerea>) iarq.readObject();
		}
		catch(ClassNotFoundException e) {
		  System.out.println("Classe CiaAerea não encontrada!");
		  System.exit(1);
		}
	}

	public void carregaDados() throws IOException {
		Path path1 = Paths.get("airlines.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String header = sc.nextLine(); // pula cabeçalho
			String id, nome;
			while (sc.hasNext()) {
				id = sc.next();
				nome = sc.next();
				System.out.println("ID: "+ id + "- Nome: "+ nome + "-");
				//System.out.format("%s - %s%n", id, nome);
				empresas.add(new CiaAerea(id, nome));
			}
		}
	}

	public ArrayList<CiaAerea> listarTodas() {
		// ArrayList<CiaAerea> nova = new ArrayList<>();
		// for(CiaAerea cia: empresas)
		// nova.add(cia);
		// return nova;
		return new ArrayList<CiaAerea>(empresas);
	}

	public CiaAerea buscarCodigo(String codigo) {
		for (CiaAerea c : empresas) {
			if (codigo.equals(c.getCodigo()))
				return c;
		}
		return null; // não achamos!
	}

	public CiaAerea buscarNome(String nome) {
		for (CiaAerea c : empresas) {
			if (nome.equals(c.getNome()))
				return c;
		}
		return null; // não achamos!
	}

	@Override
	public Iterator<CiaAerea> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<CiaAerea>() {

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public CiaAerea next() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
