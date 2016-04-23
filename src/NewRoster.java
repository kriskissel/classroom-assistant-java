import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;


public class NewRoster extends JPanel {
	

	private RosterEventListener rosterEventListener;
	
	public NewRoster(ClassOfStudents2 roster){
		int N = roster.rosterSize();
		if (N != 0) {
			setLayout(new GridBagLayout());
			GridBagConstraints gc = new GridBagConstraints();
			gc.anchor = GridBagConstraints.CENTER;
			gc.gridx = 1;
			gc.gridy = 1;
			gc.weightx=1;
			gc.weighty=1;
			List<Student2> rosterList = roster.getStudents();
			JButton[] btn = new JButton[N];
			for (int i = 0; i < N; i++) {
				Student2 student = rosterList.get(i);
				btn[i] = new JButton(student.getStudentName(), student.getSmallpic());
				btn[i].setFont(btn[i].getFont().deriveFont(16.0f));
				btn[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg) {
						RosterEvent ev = new RosterEvent(this, student);
						if (rosterEventListener != null) {
							rosterEventListener.RosterEventOccurred(ev);
						}
					}
				});
				add(btn[i], gc);
				gc.gridy++;
				setVisible(true);
			}
		}
		setVisible(true);
	}
	
	public void setRosterEventListener(RosterEventListener listener) {
		this.rosterEventListener = listener;
	}
}
