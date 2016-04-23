import java.util.EventObject;


public class RosterEvent extends EventObject {

	public Student2 student;
	
	public RosterEvent(Object arg0, Student2 student) {
		super(arg0);
		this.student = student;
	}
}
