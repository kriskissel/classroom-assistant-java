import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class StudentPanel extends JPanel {

	private int N;
	private JButton[] buttonArray;
	private StudentPanelListener studentPanelListener;
	//private JScrollPane pane;
	
	public StudentPanel(ClassOfStudents2 students) {
		
		setLayout(new GridBagLayout());
		//pane = new JScrollPane();
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridy = 0;
		gc.gridx = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		
		N = students.rosterSize();
		buttonArray = new JButton[N];
		int i = 0;
		for (Student2 student : students) {
		//for (int i = 0; i < N; i++) {
			ImageIcon smallpic = student.getSmallpic();
			buttonArray[i] = new JButton(student.getStudentName(), smallpic);
			buttonArray[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				
					StudentPanelEvent ev1 = new StudentPanelEvent(this, student);
					
					if (studentPanelListener != null) {
						studentPanelListener.panelEventOccurred(ev1);
					}
					
					
				}
			});
			add(buttonArray[i], gc);
			gc.gridy++;
			i++;
		}
		setVisible(true);
		
		
	}
	
	public void setStudentPanelListener(StudentPanelListener listener) {
		this.studentPanelListener = listener;
	}
}
