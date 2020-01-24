import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class KeyAnalysis {
	
	public static String run(int[] statistics, File encoded, int keyLength) throws IOException, FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(encoded));
		char[] buffer = new char[Vizhener.BUFFER_SIZE];
		int totalSymbols = reader.read(buffer, 0, buffer.length);
		
		StringBuilder possibleKey = new StringBuilder();
		int sectorSize = totalSymbols / keyLength;
		char[] sector = new char[sectorSize + 1];
		for (int i = 0; i < keyLength; i++) {
			int k = 0;
			for (int j = i; j < totalSymbols; j += keyLength) {
				sector[k++] = buffer[j];
			}
			possibleKey.append((char) comparison(statistics, LetterFrequency.toCount(sector)));
		}
		reader.close();
		return possibleKey.toString();
	}
	
	public static int comparison(int[] originalStatistics, int[] sectorStatistics) {
		Symbol[] original = new Symbol[Vizhener.ASCII_TABLE_SIZE];
		Symbol[] sector = new Symbol[Vizhener.ASCII_TABLE_SIZE];
		for (int i = 0; i < Vizhener.ASCII_TABLE_SIZE; i++) {
			original[i] = new Symbol(i, originalStatistics[i]);
			sector[i] = new Symbol(i, sectorStatistics[i]);
		}
		Arrays.sort(original);
		Arrays.sort(sector);
		int[] comparisonArray = new int[Vizhener.ASCII_TABLE_SIZE];
		for (int i = 0; i < 30; i++) {
			int f = (sector[i].getAsciiIndex() - original[i].getAsciiIndex()) % Vizhener.ASCII_TABLE_SIZE;
			if (f < 0) {
				f += Vizhener.ASCII_TABLE_SIZE;
			}
			comparisonArray[f]++;
		}
		int result = max(comparisonArray);
		return result;
	}
	
	public static int max(int[] array) {
		int maximum = array[0];
		int maximumIndex = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > maximum) {
				maximum = array[i];
				maximumIndex = i;
			}
		}
		return maximumIndex;
	}
}