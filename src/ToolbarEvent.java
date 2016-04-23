import java.util.EventObject;


public class ToolbarEvent extends EventObject {
	
	String action;

	public ToolbarEvent(Object arg0, String act) {
		super(arg0);
		this.action = act;
	}
}
