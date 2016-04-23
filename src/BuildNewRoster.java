import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class BuildNewRoster extends JFrame {

	
	private JFileChooser fileChooser;
	private ClassOfStudents2 roster;
	private Image nophoto;
	private JScrollPane rosterPane;
	private JTextArea temp = new JTextArea();
	private Dimension dim1 = new Dimension();
	private EditPanel editPanel;
	private NewRoster rosterPanel;
	private Student2 newStudent;
	private String currentFilename;
	private List<String> rosterClassNames;
	private List<String> rosterFileNames;

	public BuildNewRoster() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Initialize a few things
		fileChooser = new JFileChooser(".");
		roster = new ClassOfStudents2();
		nophoto = null;
		try {
			nophoto = ImageIO.read(new File("noimage.png")).getScaledInstance(320, 180, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//  Startup
		setSize(1200,700);
		setVisible(true);
		loadLog();
		loadFromLog();
		setVisible(false);
		
		
		// Create Layout
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		if (roster != null) {
			rosterPanel = new NewRoster(roster); rosterPane = new JScrollPane(rosterPanel);
			refreshRosterPane();
			}
		else rosterPane = new JScrollPane();
		dim1.width = 520;
		rosterPane.setPreferredSize(dim1);
		add(rosterPane, BorderLayout.WEST);
		
		addToolbar();
		
		editPanel = new EditPanel(null);
		editPanel.setEditPanelListener(new EditPanelListener() {
			public void EditPanelEventOccurred(EditPanelEvent ev) {
				refreshRosterPane();
				if (ev.action == "save") {saveDataAs();}
			}
		});
		add(editPanel, BorderLayout.CENTER);
	
		
		
		setVisible(true);
	}
		
	private void addToolbar() {
		JPanel nameInputBar = new JPanel();
		nameInputBar.setLayout(new FlowLayout());
		
		JLabel typeName = new JLabel("Enter Student Name: ");
		typeName.setFont(typeName.getFont().deriveFont(24.0f));
		nameInputBar.add(typeName);
		
		JTextField textfield = new JTextField(30);
		textfield.setFont(textfield.getFont().deriveFont(24.0f));
		nameInputBar.add(textfield);
		
		JButton addStudentButton = new JButton("Add to Roster");
		addStudentButton.setFont(addStudentButton.getFont().deriveFont(24.0f));
		addStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textfield.getText().equals("")) {
					newStudent = new Student2(textfield.getText(), nophoto, "");
					roster.add(newStudent);
					rosterPane.setVisible(false);
					//setVisible(false);
					remove(rosterPane);
					//temp.setText(textfield.getText());
					rosterPanel = new NewRoster(roster);
					rosterPanel.setRosterEventListener(new RosterEventListener() {
						public void RosterEventOccurred(RosterEvent ev) {
							updateEditPanel(ev.student);
							refreshRosterPane();
						}
					});
					rosterPane = new JScrollPane(rosterPanel);
					rosterPane.setPreferredSize(dim1);
					rosterPane.setVisible(true);
					add(rosterPane, BorderLayout.WEST);
					//editPanel.setVisible(false);
					//remove(editPanel);
					//editPanel = new EditPanel(roster.getStudents().getLast());
					//add(editPanel, BorderLayout.CENTER);
					updateEditPanel(roster.getStudents().getLast());
					setVisible(true);
					
				}
				else System.out.println("Empty text field");
				textfield.setText("");
			}
		});
		//nameInputBar.add(addStudentButton);
		
		add(nameInputBar, BorderLayout.NORTH);
	}

	private void updateEditPanel(Student2 student){
		editPanel.setVisible(false);
		remove(editPanel);
		editPanel = new EditPanel(student);
		editPanel.setEditPanelListener(new EditPanelListener() {
			public void EditPanelEventOccurred(EditPanelEvent ev) {
				refreshRosterPane();
				if (ev.action == "save") {saveDataAs();}
			}
		});
		add(editPanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
	private void refreshRosterPane(){
		rosterPane.setVisible(false);
		remove(rosterPane);
		rosterPanel = new NewRoster(roster);
		rosterPane = new JScrollPane(rosterPanel);
		rosterPane.setPreferredSize(dim1);
		rosterPanel.setRosterEventListener(new RosterEventListener() {
			public void RosterEventOccurred(RosterEvent ev) {
				updateEditPanel(ev.student);
			}
		});
		add(rosterPane, BorderLayout.WEST);
		setVisible(true);
	}
	
	private void loadLog() {
		try {
			List<String> logData = Files.readAllLines(Paths.get("CArosters.log"), Charset.defaultCharset());
			rosterClassNames = new LinkedList<String>();
			rosterFileNames = new LinkedList<String>();
			int numberOfClasses = logData.size()/2;
			int state = 0;  // keeps track of number of lines read
			for (String line : logData) {
				if (state < numberOfClasses) {
					rosterClassNames.add(line);
				}
				else if (state < 2*numberOfClasses) {
					rosterFileNames.add(line);
				}
				state++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rosterClassNames = new LinkedList<String>();
			rosterFileNames = new LinkedList<String>();
		}
	}
		
	private void loadFromLog() {
		String[] names = new String[rosterClassNames.size()+1];
		String[] filenames = new String[rosterFileNames.size()];
		int position = 0;
		for (String name : rosterClassNames) names[position++] = name;
		names[rosterClassNames.size()] = "Create New Class Roster";
		position = 0;
		for (String filename : rosterFileNames) filenames[position++] = filename;
		int choice = (int) JOptionPane.showOptionDialog(this, "Select a Class Roster", "Select Class", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, names, names[0]);
		if (choice == rosterClassNames.size()) {
			System.out.println("Chose a new roster");
			roster = new ClassOfStudents2();
		}
		if (choice < rosterClassNames.size()) {
			currentFilename = filenames[choice]; 
			System.out.println("Chose an existing roster");
			loadData(currentFilename);
			}
			//loadData(currentFilename); this.getContentPane().removeAll(); 
			//setupMainGUI(); setVisible(true); selectStudent(studentClass.getStudents().getFirst());}
	}
	
	private void saveData(String filename) {
		try {
			// save current class roster
			FileOutputStream fout = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(roster);
			oos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveDataAs() {
		System.out.println(currentFilename);
		if (currentFilename == null) {
			String className = (String) JOptionPane.showInputDialog("Enter a name for this course (e.g. English 101 9 am):");
			if (className != null) {
				currentFilename = String.valueOf(System.currentTimeMillis());
				rosterClassNames.add(className);
				rosterFileNames.add(currentFilename);
				saveLogForNew();
			}
		}
		if (currentFilename != null) saveData(currentFilename);
	}

	private void saveLogForNew() {
		List<String> linesToWrite = new LinkedList<String>();
		for (String rosterClassName : rosterClassNames) linesToWrite.add(rosterClassName);
		for (String rosterFileName : rosterFileNames) linesToWrite.add(rosterFileName);
		try {
			Files.write(Paths.get("CArosters.log"), linesToWrite, StandardOpenOption.CREATE, 
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}

	private void selectEditOrNew() {
		
	}
	
	private void loadData(String filename) {
		try{
			FileInputStream fin = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fin);
			this.roster = (ClassOfStudents2) ois.readObject();
			ois.close();
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
