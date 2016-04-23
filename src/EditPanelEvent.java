import java.util.EventObject;


public class EditPanelEvent extends EventObject {

	String action;
	Student2 student;
	
	public EditPanelEvent(Object arg0, String action, Student2 student) {
		super(arg0);
		this.action = action;
		this.student = student;
	}
}
