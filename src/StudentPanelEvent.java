import java.util.EventObject;


public class StudentPanelEvent extends EventObject {
	
	private Student2 student;

	public StudentPanelEvent(Object arg0, Student2 s) {
		super(arg0);
		this.student = s;
	}
	
	public Student2 getStudent() {
		return this.student;
	}
}
