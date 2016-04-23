import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;


// NEED TO MAKE THIS ITERATE ITERABLE, IN ALPHABETICAL ORDER PERHAPS?

public class ClassOfStudents2 implements Iterable<Student2>, Serializable{

	private LinkedList<Student2> students;
	private int numberOfStudents = 0;
	private Random random = new Random();
	private String[] datesOfAttendance = new String[100];
	private int numberOfDates = 0;
	
	public ClassOfStudents2() {
		students = new LinkedList<Student2>();
	}
	
	public void add(Student2 student) {
		students.add(student);
		numberOfStudents++;
	}
	
	public LinkedList<Student2> getStudents() {
		return students;
	}
	
	public int rosterSize() {
		return numberOfStudents;
	}

	@Override
	public Iterator<Student2> iterator() {
		// TODO Auto-generated method stub
		return students.iterator();
	}
	
	public Student2 getRandomStudent() {
		int randomIndex = random.nextInt(numberOfStudents);
		return students.get(randomIndex);
	}
	
	public void removeStudent(Student2 student){
		if (students.contains(student)) {students.remove(student); numberOfStudents--;}
	}
	
	public void addDate(String date) {
		if (!Arrays.asList(datesOfAttendance).contains(date)) {datesOfAttendance[numberOfDates++]=date;}
	}
	
	public String[] attendanceDates() {
		String[] dates = Arrays.copyOf(datesOfAttendance, numberOfDates);
		return dates;
	}
	public int numberOfAttendanceDates() {
		return numberOfDates;
	}
	
}
