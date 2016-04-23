import java.util.EventListener;


public interface RosterEventListener extends EventListener {

	public void RosterEventOccurred(RosterEvent ev);
}
