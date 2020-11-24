package view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import excel.ArquivoGenericoExcel;

public class ExtratorMercadoLivre {

	private ArrayList<String> resultados;
	private ArquivoGenericoExcel excel;
	private AutomacaoMercadoLivre automacao;
	private BufferedWriter writer;
	private String path;
	private String parent;
	private String nome;

	public ExtratorMercadoLivre(String path, String parent, String nome) {
		this.path = path;
		this.parent = parent;
		this.nome = nome;

	}

	private String mudarExtensao(String nome) {
		if(nome.contains(".xls")) {
			return nome.split(".xls")[0] + ".csv";
		}else {
			return nome;
		}
	}

	public void extrairDados(JLabel text) throws Exception {
		try {
			nome = mudarExtensao(nome);
			writer = new BufferedWriter(new FileWriter(new File(parent+File.separatorChar + "extracao-" + nome)));
			resultados = new ArrayList<>();
			excel = new ArquivoGenericoExcel(path);
		} catch (Exception e) {
			apresentarMensagemErro("Erro ao gerar arquivo, tente novamente");
			e.printStackTrace();
		}


		text.setText("Inicializando extração");
		automacao = new AutomacaoMercadoLivre();
		int elementos = 0;
		try {
			for (String linha : excel.percorreSheets()) {
				resultados.add(automacao.buscaResultado(linha));
				text.setText("elementos encontrados: " + (++elementos));
			}

			for (String resultado : resultados) {
				writer.append(resultado + System.lineSeparator());
				text.setText("escrevendo item : " + resultado);
			}

		}catch (Exception e) {
			apresentarMensagemErro("Erro extraindo dados");
			e.printStackTrace();
		}
		writer.close();
		JOptionPane.showMessageDialog(null, "Extração finalizada","Finalização OK", JOptionPane.INFORMATION_MESSAGE);
	}

	private void apresentarMensagemErro(String msg) {
		JOptionPane.showMessageDialog(null, msg,"Erro", JOptionPane.ERROR_MESSAGE);		
	}

}
