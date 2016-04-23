import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class TextPanel extends JScrollPane {

	private JTextArea textArea;
	
	public TextPanel(JTextArea txtArea) {
		this.textArea = txtArea;
		add(textArea);
		setVisible(true);
	}
}
