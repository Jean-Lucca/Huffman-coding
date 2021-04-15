import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Map.Entry;

public class HuffmanEncoding {

	Hashtable<Character,Double> ft;
	Hashtable<Character,String> et;
	PriorityQueue<Node> q;

	public HuffmanEncoding() {
		this.ft = new Hashtable<Character, Double>();
		this.et = new Hashtable<Character,String> ();
		this.q = new PriorityQueue<Node>();
	}

	private class Node implements Comparable<Node> {
		
		private char character;
		private double freqVal;
		private Node leftChild;
		private Node rightChild;
		
		public Node(){}
		
		public Node(char character, double freqVal, Node leftChild, Node rightChild) {
			this.character = character;
			this.freqVal = freqVal;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}

		public void setLeftChild(Node leftChild) {
			this.leftChild = leftChild;
		}
		
		public void setRightChild(Node rightChild) {
			this.rightChild = rightChild;
		}
		
		public void setFreq(Double freqVal) {
			freqVal = freqVal;
		}
		
		public Node getLeftChild() {
			return leftChild;
		}
		
		public Node getRightChild() {
			return rightChild;
		}
		
		public Double getFreq() {
			return freqVal;
		}

		public boolean isLeaf() {
            assert ((leftChild == null) && (rightChild == null)) || ((leftChild != null) && (rightChild != null));
            return (leftChild == null) && (rightChild == null);
        }

		public int compareTo(Node outro) {
            return (int) (this.freqVal - outro.freqVal);
        }
	}

	//adicionei um metodo para contar a prequencia dos caracteres
	public Hashtable<Character, Double> countFrequency(String str) {
        Hashtable<Character, Double> table = new Hashtable<Character, Double>();
        char[] strC = str.toCharArray();
        int i = 0;
        for (char c : strC){
            i++;
            if (table.containsKey(c)){
                table.put(c, table.get(c) + 1.0); 
            }
            else {
                table.put(c, 1.0); 
            }
        }
        return table;
    }

	//substitui a lista por uma fila de prioridade
	public void buildQueue(Hashtable<Character, Double> table) {
        for (Entry<Character, Double> ent : table.entrySet()){
            char c = (char) ent.getKey();
            double fr = (Double) ent.getValue();
            Node n = new Node(c,fr,null,null);
            q.add(n);
        }
    }

	//auto explicativo controe a arvore, mas usando a fila
	public Node buildBinTree() {
        Node root;
		while(q.size()>1){
            Node leftChild = q.poll();
            Node rightChild = q.poll();
            Node parent = new Node('\0', leftChild.freqVal+rightChild.freqVal, leftChild, rightChild);
            q.add(parent);
        }
		root = q.poll();
        return root;
    }

	//substitui os metodos que imprimem a arvore por um metodo que constroe a arvore com o codigo
	public void buildTreeCode(Node root,String cd, Hashtable<Character,String> table) {
        if(!root.isLeaf()){
            buildTreeCode(root.leftChild, cd + '0', table);
            buildTreeCode(root.rightChild, cd + '1', table);
        }else{
            table.put(root.character, cd);
        }
    }

	//construo o codigo a partir da tabela
	public String buildCode(String s, Hashtable<Character,String> table) {
        StringBuilder builder = new StringBuilder();
        for (char chr : s.toCharArray()){
            builder.append(table.get(chr));
        }
        return builder.toString();
    }
	
	public String decode(Node root, String encodedData) {
        StringBuilder builder = new StringBuilder();
        Node curr = root;
        for(int i =0;i<encodedData.length();){
            while(!curr.isLeaf()){
                if(encodedData.charAt(i) == '1'){
                    curr = curr.rightChild;
                }
                if(encodedData.charAt(i) == '0'){
                    curr = curr.leftChild;
                }
                i++;
            }
            builder.append(curr.character);
            curr = root;
        }
        return builder.toString();
    }
	//adicionei metodos para leitura e escrita
	public String read(String f) {
        String fileName = f;
        String line = null;
        StringBuffer buffer = new StringBuffer();
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null){
                buffer.append(line);
            }
        }catch (IOException ex){
            System.out.println("Erro");
        }
        return buffer.toString();
    }
	
    public void writeCode(String s) {
		String content = s;
        try (FileWriter writer = new FileWriter("Compactado.txt");
             BufferedWriter bw = new BufferedWriter(writer)) {

            bw.write(content);

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

	public void writeDecode(String s) {
        String content = s;
        try (FileWriter writer = new FileWriter("Copia.txt");
             BufferedWriter bw = new BufferedWriter(writer)) {

            bw.write(content);

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    public void writeTable(Hashtable<Character,String> t) {
        try (FileWriter writer = new FileWriter("Tabela.txt");
        BufferedWriter bw = new BufferedWriter(writer)) {
        for (Entry<Character, String> ent : t.entrySet()) {
            bw.write(ent.getKey()+","+ent.getValue()+"\n");
        }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

	public void runEncodeDecode(String texto) {

		buildQueue(countFrequency(texto));
        Node treeRoot = buildBinTree();
		buildTreeCode(treeRoot,"", et);

		String codigo = buildCode(texto, et);
		String copia = decode(treeRoot, codigo);
		
		writeTable(et);
        writeCode(codigo);
		writeDecode(copia);

	}
}
