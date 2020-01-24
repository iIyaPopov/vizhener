import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LetterFrequency {
	
	public static int[] toCount(char[] message) throws IOException {
		int[] frequency = new int[Vizhener.ASCII_TABLE_SIZE];
		for (int i = 0; i < message.length; i++) {
			frequency[message[i]] ++;
		}
		return frequency;
	}
	
	public static int[] getStatistics(File file) throws IOException, FileNotFoundException {
		int[] fileStatistics = new int[Vizhener.ASCII_TABLE_SIZE];
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int totalSymbols = 0;
		int symbol;
		while ((symbol = reader.read()) != -1) {
			fileStatistics[symbol] ++;
			totalSymbols ++;
		}
		reader.close();
		return fileStatistics;
	}
}