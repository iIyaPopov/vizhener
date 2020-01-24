public class Symbol implements Comparable {
	private int count;
	private int asciiIndex;
	
	public Symbol(int asciiIndex, int count) {
		this.asciiIndex = asciiIndex;
		this.count = count;
	}
	
	public int getAsciiIndex() {
		return this.asciiIndex;
	}
	
	public int getCount() {
		return this.count;
	}
	
	@Override
	public int compareTo(Object o) {
		Symbol s = (Symbol) o;
		if (this.count > s.count) {
			return -1;
		} else if (this.count == s.count) {
			return 0;
		} else {
			return 1;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(count);
		return s.toString();
	}
}