import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Vizhener {
	public static final int MAX_KEY_SIZE = 50;
	public static final int BUFFER_SIZE = 50000;
	public static final int ASCII_TABLE_SIZE = 256;
	private static int statistics[];
	
	public static void encoder(File inputFile, File outputFile, String key) throws IOException, FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		PrintWriter writer = new PrintWriter(outputFile);
		char[] buffer = new char[Vizhener.BUFFER_SIZE];
		int totalSymbols;
		totalSymbols = reader.read(buffer, 0, Vizhener.BUFFER_SIZE);
		for (int i = 0; i < totalSymbols; i++) {
			buffer[i] = (char)((int)((buffer[i] + key.charAt(i % key.length()))) % Vizhener.ASCII_TABLE_SIZE);
		}
		writer.print(buffer);
		reader.close();
		writer.close();
	}
	
	public static void decoder(File inputFile, File outputFile, String key) throws IOException, FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		PrintWriter writer = new PrintWriter(outputFile);
		char[] buffer = new char[Vizhener.BUFFER_SIZE];
		int totalSymbols;
		totalSymbols = reader.read(buffer, 0, Vizhener.BUFFER_SIZE);
		for (int i = 0; i < totalSymbols; i++) {
			buffer[i] = (char)((int)((buffer[i] - key.charAt(i % key.length()))) % Vizhener.ASCII_TABLE_SIZE);
		}
		writer.print(buffer);
		reader.close();
		writer.close();
	}
	
	public static String keyGen(int keyLength) {
		StringBuilder key = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < keyLength; i++) {
			key.append((char) (48 + rand.nextInt(75)));
		}
		return key.toString();
	}
	
	public static String doAttack(File statisticsFile, File encodedFile) throws IOException {
		Vizhener.statistics = LetterFrequency.getStatistics(statisticsFile);
		int keyLength = IndexMetod.run(encodedFile);
		String vizhenerKey = KeyAnalysis.run(statistics, encodedFile, keyLength);
		return vizhenerKey;
	}
	
	public static void main(String[] args) {
		try {
			File text = new File("/home/ilya/Рабочий стол/Vizhener/test_files/text.txt");
			File encoded = new File("/home/ilya/Рабочий стол/Vizhener/test_files/encoded.txt");
			File decoded = new File("/home/ilya/Рабочий стол/Vizhener/test_files/decoded.txt");
			File statistics = new File("/home/ilya/Рабочий стол/Vizhener/test_files/statistics.txt");
			File vizhenerKeyFile = new File("/home/ilya/Рабочий стол/Vizhener/test_files/vizhener_key.txt");
			File userKey = new File("/home/ilya/Рабочий стол/Vizhener/test_files/user_key.txt");
			
			BufferedReader reader = new BufferedReader(new FileReader(userKey));
			String key = reader.readLine();
			if (key.equals("rand")) {
				key = Vizhener.keyGen(Vizhener.MAX_KEY_SIZE);
			}
			reader.close();
			
			Vizhener.encoder(text, encoded, key);
			
			//Vizhener.decoder(encoded, decoded, key);
			
			String vizhenerKey = Vizhener.doAttack(statistics, encoded);
			Vizhener.decoder(encoded, decoded, vizhenerKey);
			PrintWriter writer = new PrintWriter(vizhenerKeyFile);
			writer.print(vizhenerKey);
			writer.close();
			
			System.out.println("Vizhener key is " + vizhenerKey);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}