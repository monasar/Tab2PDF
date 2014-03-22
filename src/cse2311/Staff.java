package cse2311;

import java.util.ArrayList;

public class Staff {
	private ArrayList<StringBuffer> myLines;
	private float printSpace, width;
	private ArrayList<Integer>  repeatNum ;

	public Staff(float printSpace) {
		this.setPrintSpace(printSpace);
	}

	public void addToStaff(Measure m) {
		this.appendToLines(m);
		if (m.isRepeat())
			this.getrepeatNum().add((m.getRepeatNum()));
		this.setWidth(m.getWidth());
	}

	public ArrayList<Integer> getrepeatNum() {
		if (this.repeatNum == null){ // Lazy initialization
				this.repeatNum = new ArrayList<Integer>();}
		return repeatNum;
	}

	public void setrepeatNum(ArrayList<Integer> repeatNum) {
		this.repeatNum = repeatNum;
	}
	
	public int getTopInt() {
		int i =-1;
		if(this.getrepeatNum()!= null && !this.getrepeatNum().isEmpty() ){
			i = this.getrepeatNum().get(0);
			this.getrepeatNum().remove(0);
		}
		return i;
	}
	
	private void appendToLines(Measure m) {
		int i = 0;
		for (String line : m.getLines()) {
			if (!(this.getLines().size() <= i))
				this.getLines().get(i).append(this.barsAdded(m.getBarType(),line));
			else {
				this.addStringBuffer();
				this.getLines().get(i).append(this.barsAdded(m.getBarType(),line));
			}
			i++;
		}
		this.fixBars();
	}

	private String barsAdded(String bar, String line) {
		String single = "|";
		String left = "D-|";
		String right = "|-D";
	
		if("Both".equals(bar))
			line= left + line + right;
		if("Right".equals(bar))
			line= single + line + right;
		if("Left".equals(bar))
			line= left + line + single;
		if("Single".equals(bar))
			line= single + line + single;
		
		return line;
	}

	public void addStringBuffer() {
		this.getLines().add(new StringBuffer());
	}

	public boolean canFitAnother(Measure m) {
		return this.getWidth() + m.getWidth() < this.getPrintSpace();
	}

	private void fixBars() {
		for(int i = 0; i < this.getLines().size(); i++) {
			String line = this.getLines().get(i).toString();
			this.getLines().remove(i);
			this.myLines.add(i, this.fixedLine(line));
		}
	}

	private StringBuffer fixedLine(String line) {
		line = line.replace("T","|-|-|");
		line = line.replace("DD","D");
		line = line.replace("||","|");
		return new StringBuffer(line);
	}	

	public ArrayList<StringBuffer> getLines() {
		if (this.myLines == null)// Lazy initialization
			this.myLines = new ArrayList<StringBuffer>();
		return myLines;
	}

	public float getPrintSpace() {
		return printSpace;
	}

	private void setPrintSpace(float printSpace) {
		this.printSpace = printSpace;
	}

	public float getWidth() {
		return width;
	}

	private void setWidth(float width) {
		this.width += width;
	}

	public void printLines() {
		for (StringBuffer m : myLines) {
			System.out.println(m.toString());
		}
		System.out.println();
	}
}
