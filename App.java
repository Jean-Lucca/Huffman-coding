//adicionei muitos comentarios que normalmente ficariam em um arquivo separado
//mudei a entrada pra uma string
public class App {
	
	public static void main(String[] args) {
		HuffmanEncoding hf = new HuffmanEncoding();
		String texto = "";
		texto = hf.read("Teste.txt");
		//texto = "Como eu não estou escrevendo em binario o arquivo compactado é maior que o original";
		hf.runEncodeDecode(texto);
	}
}
