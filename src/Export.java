import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Export {

	public static void Notes(ClassOfStudents2 roster, Path pathname){
		List<String> linesToWrite = new LinkedList<String>();
		for (Student2 student : roster){
			linesToWrite.add("********************");
			linesToWrite.add(student.getStudentName());
			linesToWrite.add("\n");
			linesToWrite.add(student.getNotes());
			linesToWrite.add("********************");
		}
		try {
			Files.write(pathname, linesToWrite, StandardOpenOption.CREATE, 
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void Counters(ClassOfStudents2 roster, String[] counterNames, Path pathname){
		List<String> linesToWrite = new LinkedList<String>();
		linesToWrite.add("Name,"+counterNames[0]+","+counterNames[1]+","+counterNames[2]);
		/* 
		 * This could break if the counter names include COMMAS!!!
		 * Or, even more likely, if the student names contain commas!
		 * I DEALT WITH THAT BY NOT AUTOMATICALLY REVING COMMAS WHEN THOSE ARE DEFINED BY THE USER!!!!!
		 */
		for (Student2 student : roster) {
			linesToWrite.add(student.getStudentName()+","+student.getCounter(0)+","+student.getCounter(1)+","+student.getCounter(2));
		}
		try {
			Files.write(pathname, linesToWrite, StandardOpenOption.CREATE, 
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void Attendance(ClassOfStudents2 roster, Path pathname, String presentMark, String absentMark){
		List<String> linesToWrite = new LinkedList<String>();
		String[] dates = roster.attendanceDates();
		Arrays.sort(dates);
		StringBuilder firstLine = new StringBuilder();
		firstLine.append("Name,");
		for (String date : dates) firstLine.append(date+",");
		linesToWrite.add(firstLine.toString());
		for (Student2 student : roster) {
			StringBuilder line = new StringBuilder();
			line.append(student.getStudentName()+",");
			for (String date : dates) {
				if (student.inAttendance(date)) line.append(presentMark);
				else line.append(absentMark);
				line.append(",");
			}
			linesToWrite.add(line.toString());
		}
		try {
			Files.write(pathname, linesToWrite, StandardOpenOption.CREATE, 
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
