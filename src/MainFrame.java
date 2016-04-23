import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MainFrame extends JFrame {

	// set up private variables
	
	private JTextArea textArea;
	private StudentPanel studentPanel;
	private JScrollPane studentPane;
	private JScrollPane textPane;
	private ImagePanel imagePanel;
	private RightPanel rightPanel;
	private ToolBar toolbar;
	private BufferedImage image;
	private Font font = new Font("Times", Font.PLAIN, 20);
	private Student2 currentStudent;
	private JFileChooser fileChooser;
	private String currentFilename;
	private String[] counterNames = {"Counter #1", "Counter #2", "Counter #3"};
	private Date date;

	
	private ClassOfStudents2 studentClass = new ClassOfStudents2();
	private Stack<Student2> previousStack = new Stack<Student2>();
	private Stack<Student2> nextStack = new Stack<Student2>();
	
	private List<String> rosterClassNames;
	private List<String> rosterFileNames;
	

	public MainFrame() {
		
		this.setTitle("Classroom Assistant");
		setUndecorated(true);
		
		// THE FOLLOWING LINES MAXIMIZE THE WINDOW AUTOMATICALLY
		// I MIGHT WANT TO MAKE THAT A PREFERENCE SETTING.
		
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.setMaximizedBounds(env.getMaximumWindowBounds());
		this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		fileChooser = new JFileChooser(".");
		
		
		//setSize(1200,1000);
		
		setVisible(true);
		
		//testSetup();  // either use this or use loadWindow
		//loadWindow();
		loadLog();
		loadFromLog();
		
	}
	
	private void selectPreviousStudent() {
		if (!previousStack.isEmpty()){
			nextStack.push(currentStudent);
			currentStudent = previousStack.pop();
			textArea.setText(currentStudent.getNotes());
			imagePanel = new ImagePanel(currentStudent.getPic());
			remove(rightPanel);
			rightPanel = new RightPanel(imagePanel, currentStudent, counterNames);
			add(rightPanel, BorderLayout.EAST);
			textArea.requestFocusInWindow();
			setVisible(true);
		}
	}
	
	private void selectStudent(Student2 student) {
		// replace currentStudent notes with content of textarea
		if (currentStudent != null) {
		currentStudent.setNotes(textArea.getText());
		previousStack.push(currentStudent);
		}
		
		// change currentStudent to student
		currentStudent = student;		
		
		// refresh view of text area with new currentStudent notes
		textArea.setText(currentStudent.getNotes());
		
		// refresh view of student image
		
		imagePanel = new ImagePanel(currentStudent.getPic());
		remove(rightPanel);
		rightPanel = new RightPanel(imagePanel, currentStudent, counterNames);
		add(rightPanel, BorderLayout.EAST);
		textArea.requestFocusInWindow();
		setVisible(true);
	}
	
	private void saveData(String filename) {
		currentStudent.setNotes(textArea.getText());
		try {
			FileOutputStream fout = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(studentClass);
			oos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveDataAs() {
		if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
			File outfile = fileChooser.getSelectedFile();
			currentFilename = outfile.getAbsolutePath();
			saveData(currentFilename);
		}
	}
	
	private void loadData(String filename) {
		try{
			FileInputStream fin = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fin);
			this.studentClass = (ClassOfStudents2) ois.readObject();
			ois.close();
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void setupMainGUI() {
		setLayout(new BorderLayout());
		
		loadSettings();
		
		textArea = new JTextArea();
		studentPanel = new StudentPanel(studentClass);
		
		textArea.setFont(font);
		
		textPane = new JScrollPane(textArea);
		
		studentPane = new JScrollPane(studentPanel);
		Dimension dim = studentPanel.getPreferredSize();
		dim.width = dim.width+20;
		studentPane.setPreferredSize(dim);
		
		studentPanel.setStudentPanelListener(new StudentPanelListener() {
			public void panelEventOccurred(StudentPanelEvent e) {
				selectStudent(e.getStudent());
			}
		});
		
		imagePanel = new ImagePanel(noPhoto());
		rightPanel = new RightPanel(imagePanel, null, counterNames);
		rightPanel.setVisible(true);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		toolbar = new ToolBar();
		toolbar.setToolbarListener(new ToolbarListener() {
			public void toolbarEventOccurred(ToolbarEvent e) {
				if (e.action=="save") saveData(currentFilename);
				//if (e.action=="load") loadData();
				if (e.action=="saveAs") saveDataAs();
				if (e.action=="random") selectStudent(studentClass.getRandomStudent());
				if (e.action=="previous" && !previousStack.isEmpty()) selectPreviousStudent();
				if (e.action=="next" && !nextStack.isEmpty()) selectStudent(nextStack.pop());
				if (e.action=="exit") exit();
				if (e.action=="settings") changeSettings();
				if (e.action=="date") textArea.append(dateString()+"\n");
			}
		});
		
		add(studentPane, BorderLayout.WEST);
		add(textPane, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
		add(toolbar, BorderLayout.SOUTH);
		textArea.requestFocusInWindow();
		
		setVisible(true);
		
		selectStudent(studentClass.getStudents().getFirst());
		// This is just for testing:
		takeAttendance();
	}

	
	private void loadWindow() {
		if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
			File infile = fileChooser.getSelectedFile();
			currentFilename = infile.getAbsolutePath();
			loadData(currentFilename);
		}
	}
	
	private ImageIcon noPhoto() {
		try {
			Image image = ImageIO.read(new File("noimage.png"));
			return new ImageIcon(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void exit() {
		saveData(currentFilename);
		System.exit(0);
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
		names[rosterClassNames.size()] = "Edit/Create Roster";
		position = 0;
		for (String filename : rosterFileNames) filenames[position++] = filename;
		int choice = (int) JOptionPane.showOptionDialog(this, "Select a Class Roster", "Select Class", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, names, names[0]);
		if (choice == rosterClassNames.size()) editRosters();
		if (choice < rosterClassNames.size()) {currentFilename = filenames[choice]; 
			loadData(currentFilename); this.getContentPane().removeAll(); 
			setupMainGUI(); setVisible(true); selectStudent(studentClass.getStudents().getFirst());}
	}
	
	
	private void editRosters() {
		
		add(new BuildPanel());
		setVisible(true);
		
	}
	
	private void changeSettings(){
		String[] selections = new String[5];
		selections[0] = "Edit Counter Titles";
		selections[1] = "Export Notes";
		selections[2] = "Export Counter Data";
		selections[3] = "Export Attendance";
		selections[4] = "Done";
		int choice = (int) JOptionPane.showOptionDialog(this, "Select One:", "Tools", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, selections, selections[4]);
		if (choice == 0) {renameCounters(); saveData(currentFilename);}
		if (choice == 1) {saveData(currentFilename); exportNotes();}
		if (choice == 2) {saveData(currentFilename); exportCounters();}
		if (choice == 3) {saveData(currentFilename); exportAttendance();}
		
	}

	private void saveSettings(){
		List<String> linesToWrite = new LinkedList<String>();
		for (String name : counterNames) linesToWrite.add(name);
		try {
			Files.write(Paths.get(currentFilename+".prefs"), linesToWrite, StandardOpenOption.CREATE, 
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadSettings(){
		try {
			List<String> settingsData = Files.readAllLines(Paths.get(currentFilename+".prefs"), Charset.defaultCharset());
			if (settingsData.size() ==3) {
				counterNames[0] = settingsData.get(0);
				counterNames[1] = settingsData.get(1);
				counterNames[2] = settingsData.get(2);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	private String dateString(){
		date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		String dateToString = format.format(date);
		return dateToString;
	}
	
	private void exportNotes(){
		// The following two lines add a filer so only txt files can be selected
				FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
					    "Text files", new String[] {"txt"});
				fileChooser.addChoosableFileFilter(txtFilter);
				fileChooser.setAcceptAllFileFilterUsed(false);
				// Then we get a filename from the user.
		if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
			File outputfile = fileChooser.getSelectedFile();
			if(!outputfile.getAbsolutePath().endsWith(".txt")){
			    outputfile = new File(fileChooser.getSelectedFile() + ".txt");
			}
			String filename = outputfile.getAbsolutePath();
			Export.Notes(studentClass, Paths.get(filename));
	}
	}
	
	private void exportCounters(){
		// The following two lines add a filer so only CSV files can be selected
		FileNameExtensionFilter csvFilter = new FileNameExtensionFilter(
			    "CSV files", new String[] {"csv"});
		fileChooser.addChoosableFileFilter(csvFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		// Then we get a filename from the user.
		if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
			File outputfile = fileChooser.getSelectedFile();
			if(!outputfile.getAbsolutePath().endsWith(".csv")){
			    outputfile = new File(fileChooser.getSelectedFile() + ".csv");
			}
			String filename = outputfile.getAbsolutePath();
			Export.Counters(studentClass, counterNames, Paths.get(filename));
	}
	}
	
	private void exportAttendance(){
		// The following two lines add a filer so only CSV files can be selected
		FileNameExtensionFilter csvFilter = new FileNameExtensionFilter(
				"CSV files", new String[] {"csv"});
		fileChooser.addChoosableFileFilter(csvFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		// Then we get a filename from the user.
		if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
			File outputfile = fileChooser.getSelectedFile();
			if(!outputfile.getAbsolutePath().endsWith(".csv")){
				outputfile = new File(fileChooser.getSelectedFile() + ".csv");
			}
			String filename = outputfile.getAbsolutePath();
			String presentMark = (String) JOptionPane.showInputDialog("Enter the Desired Notation for Present Students:",
					"P");
			String absentMark = (String) JOptionPane.showInputDialog("Enter the Desired Notation for Absent Students:",
					"A");
			Export.Attendance(studentClass, Paths.get(filename), presentMark, absentMark);
		}
	}
	
	private void renameCounters() {
		int counterChoice = (int) JOptionPane.showOptionDialog(this, "Select One:", "Rename Counters", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, counterNames, counterNames[0]);
		String newName = (String) JOptionPane.showInputDialog(this, 
				"Type the new name for "+counterNames[counterChoice],counterNames[counterChoice]);
		if (newName != null) counterNames[counterChoice] = newName.replace(",", "");
		saveSettings();
		selectStudent(currentStudent);
	}
	
	private void takeAttendance(){
		SwingUtilities.invokeLater (new Runnable() {
			public void run() {
				new AttendanceFrame(studentClass);
			}
		});
	}
	
}
