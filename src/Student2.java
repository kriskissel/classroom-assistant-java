import java.awt.Image;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;


public class Student2 implements Serializable {

	private String studentName;
	private String imageFilename;
	private String notes;
	private ImageIcon pic;
	private ImageIcon smallpic;
	private int[] counters;
	private int numberOfCounters = 10;  // 10 counters should be enough
	private Map attendance;
	
	public Student2(String name, Image pic, String notes) {
		this.studentName = name;
		this.pic = new ImageIcon(pic);
		this.smallpic = new ImageIcon(pic.getScaledInstance(64, 36, 1));
		this.notes = notes;
		this.counters = new int[numberOfCounters];
		this.attendance = new HashMap();
	}

	public Map getAttendance() {
		return attendance;
	}
	
	
	public void markAttendance(String date, boolean present){
		this.attendance.put(date, present);
	}
	
	public boolean inAttendance(String date){
		if (!this.attendance.containsKey(date)) return false;
		else return (boolean) this.attendance.get(date);
	}

	public int getCounter(int counterIndex) {
		return counters[counterIndex];
	}

	public void incrementCounter(int counterIndex) {
		if (counterIndex < numberOfCounters && counterIndex >= 0) counters[counterIndex]++;
	}
	
	public void decrementCounter(int counterIndex) {
		if (counterIndex < numberOfCounters && counterIndex >= 0) counters[counterIndex]--;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getImageFilename() {
		return imageFilename;
	}

	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}


	public ImageIcon getPic() {
		return pic;
	}

	public void setPic(ImageIcon pic) {
		this.pic = pic;
	}

	public ImageIcon getSmallpic() {
		return smallpic;
	}

	public void setSmallpic(ImageIcon smallpic) {
		this.smallpic = smallpic;
	}
	
	

}
