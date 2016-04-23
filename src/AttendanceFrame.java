import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class AttendanceFrame extends JFrame {

	private ClassOfStudents2 roster;
	private JScrollPane rosterPane;
	private JPanel rosterPanel;
	private int N;
	private JButton[] buttonArray;
	private String[] attendanceText;
	private JLabel[] attendanceLabel;
	private Date date = new Date();
	
	
	public AttendanceFrame(ClassOfStudents2 roster) {
		
		//setUndecorated(true); // uncomment this later when there is an exit button and the sizing is correct
		
		setSize(400, 700);
		this.roster = roster;
		
		refreshView();
	}
	
	
	private String dateString(){
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		String dateToString = format.format(date);
		return dateToString;
	}
	
	
	private String getAttendanceCode(Student2 student){
		if (student.inAttendance(dateString())) {return "P";}
		else {return "A";}
	}
	
	private void markAttendance(Student2 student){
		roster.addDate(dateString());
		if (student.inAttendance(dateString())) {student.markAttendance(dateString(), false);}
		else {student.markAttendance(dateString(),true);}
	}
	
	
	private void refreshView(){
		
		rosterPanel = new JPanel();
		rosterPanel.setLayout(new GridBagLayout());
		//pane = new JScrollPane();
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridy = 0;
		gc.gridx = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		
		JLabel panelLabel = new JLabel("Attendance for "+dateString());
		panelLabel.setFont(panelLabel.getFont().deriveFont(20.0f));
		rosterPanel.add(panelLabel, gc);
		
		gc.gridy++;
		
		N = roster.rosterSize();
		buttonArray = new JButton[N];
		attendanceText = new String[N];
		attendanceLabel = new JLabel[N];
		
		int i = 0;
		for (Student2 student : roster.getStudents()) {
			ImageIcon smallpic = student.getSmallpic();
			buttonArray[i] = new JButton(student.getStudentName(), smallpic);
			buttonArray[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					markAttendance(student);
					setVisible(false);
					remove(rosterPane);
					refreshView();
					}
				});
			rosterPanel.add(buttonArray[i], gc);
			gc.gridx++;
			attendanceText[i] = getAttendanceCode(student);
			attendanceLabel[i] = new JLabel(attendanceText[i]);
			attendanceLabel[i].setFont(attendanceLabel[i].getFont().deriveFont(20.0f));
			rosterPanel.add(attendanceLabel[i], gc);
			
			gc.gridy++;
			gc.gridx--;
			i++;
		}
		
		rosterPane = new JScrollPane(rosterPanel);
		
		add(rosterPane);
		
		setVisible(true);
	}
	
}
