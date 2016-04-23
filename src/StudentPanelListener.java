import java.util.EventListener;


public interface StudentPanelListener extends EventListener {

	public void panelEventOccurred(StudentPanelEvent e);
}
