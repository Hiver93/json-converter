package json_converter.test;

public class SimpleClass {
	private String str = "str";
	private int i = 123;
	private char ch = 'c';
	
	public SimpleClass() {
		
	}
	
	public SimpleClass(String str, int i, char ch) {
		super();
		this.str = str;
		this.i = i;
		this.ch = ch;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public char getCh() {
		return ch;
	}
	public void setCh(char ch) {
		this.ch = ch;
	}
	@Override
	public String toString() {
		return "SimpleClass [str=" + str + ", i=" + i + ", ch=" + ch + "]";
	}
	@Override
	public boolean equals(Object obj) {
		SimpleClass sc = (SimpleClass)obj;
		return sc.getCh() == this.ch && sc.getI() == this.getI() && sc.getStr().equals(this.str);
	}
	
}
