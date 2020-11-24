package view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import selenium.DriverExtrator;

public class AutomacaoMercadoLivre {
	
	private static final String IMAGEM = "//img[@data-index='0']";
	private static final String URL_ML = "https://produto.mercadolivre.com.br/";
	private DriverExtrator extrator;
	private WebDriver driver;
	
	public AutomacaoMercadoLivre() throws Exception {
		extrator = new DriverExtrator(false, false, false);
		driver = extrator.getDriver();
	}

	public String buscaResultado(String linha) throws InterruptedException{
		if(ehProdutoML(linha)) {
			String produtoNormalizado = retornaProdutoNormalizado(linha);
			driver.get(URL_ML + produtoNormalizado);
			extrator.waitForLoad();
			if(extrator.existeElemento(IMAGEM)) {
				String imagem = driver.findElement(By.xpath(IMAGEM)).getAttribute("src").toString();
				linha = linha + ";" + imagem;
				System.out.println(imagem);
			}
		}
		return linha;
	}

private String retornaProdutoNormalizado(String linha) {
		String linhaInteira[] = linha.split(";");
		String codigoMLB[] = linhaInteira[0].split("MLB");
		return "MLB" + "-" + codigoMLB[1];
	}

	private boolean ehProdutoML(String linha) {
		return linha.split(";")[0].contains("MLB");
	}

}
