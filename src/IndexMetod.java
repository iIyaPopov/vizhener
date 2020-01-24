import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class IndexMetod {
	private static final double INDEX_FLAG = 0.065;
	private static char[] buffer;
	private static double maxIndex;
	private static int keyLength;
	
	public static int run(File text) throws IOException, FileNotFoundException {
		buffer = readText(text);
		maxIndex = 0;
		keyLength = 0;
		for (int i = 2; i <= Vizhener.MAX_KEY_SIZE; i++) {
			char[] message = messageSplit(i);
			int[] messageFrequency = LetterFrequency.toCount(message);
			double f = 0;
			int n = message.length;
			for (int j = 0; j < messageFrequency.length; j++) {
				f += messageFrequency[j] * (messageFrequency[j] - 1);
			}
			double index = f / (n * (n - 1));
			if (Math.abs(index - maxIndex) > INDEX_FLAG) {
				maxIndex = index;
				keyLength = i;
			}
		}
		return keyLength;
	}
	
	private static char[] readText(File file) throws IOException, FileNotFoundException {
		char[] buf = new char[Vizhener.BUFFER_SIZE];
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int size = reader.read(buf, 0, Vizhener.BUFFER_SIZE);
		buf = Arrays.copyOf(buf, size);
		reader.close();
		return buf;
	}
	
	public static int sum(int[] a) {
		int result = 0;
		for (int i = 0; i < a.length; i++) {
			result += a[i];
		}
		return result;
	}
	
	private static char[] messageSplit(int sectorSize) {
		int size = buffer.length / sectorSize;
		char[] splitedBuffer = new char[size];
		for (int i = 0; i < size; i++) {
			splitedBuffer[i] = buffer[i * sectorSize];
		}
		return splitedBuffer;
	}
}