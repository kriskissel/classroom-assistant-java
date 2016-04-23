import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


// NEED TO MAKE THIS ITERATE ITERABLE, IN ALPHABETICAL ORDER PERHAPS?

public class ClassOfStudents implements Iterable<Student>, Serializable{

	private LinkedList<Student> students;
	private int numberOfStudents = 0;
	private LinkedList<String> datesOfAttendance = new LinkedList<String>();
	
	
	public ClassOfStudents() {
		students = new LinkedList<Student>();
	}
	
	public void add(Student student) {
		students.add(student);
		numberOfStudents++;
	}
	
	public LinkedList<Student> getStudents() {
		return students;
	}
	
	public int rosterSize() {
		return numberOfStudents;
	}
	
	public void addDate(String date) {
		if (!datesOfAttendance.contains(date)) {datesOfAttendance.add(date);}
	}
	
	public LinkedList<String> attendanceDates() {
		return datesOfAttendance;
	}

	@Override
	public Iterator<Student> iterator() {
		// TODO Auto-generated method stub
		return students.iterator();
	}
	
}
