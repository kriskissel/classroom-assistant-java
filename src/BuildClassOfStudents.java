
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class BuildClassOfStudents extends JFrame {

	private ClassOfStudents2 studentRoster;
	private JFileChooser fileChooser;
	private JPanel namePanel;
	private JPanel nameInputPanel;
	private JTextField textField;
	private JTextField textInputField;
	private JTable table;
	private JButton imageButton;
	private JButton addButton;
	private JButton saveButton;
	private Image image;
	private Image noimage;
	private Image resizedImage;
	private Image miniImage;
	private JLabel photo;
	private JLabel nophoto;
	private String[][] tableData;
	private int rosterSize = 0;
	private String[] columnNames = {"Name", "Photo Available"};
	private GridBagConstraints photogc = new GridBagConstraints();
	private String photoSelected = "No";
	private List<String> rosterClassNames;
	private List<String> rosterFileNames;
	private String currentFilename;
	private String currentClassName;
	private Calendar calendar = Calendar.getInstance();
	
	public BuildClassOfStudents() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		fileChooser = new JFileChooser(".");
		
		setSize(1200,700);
		
		setVisible(true);
		
		//testSetup();  // either use this or use loadWindow
		//loadWindow();
		
		loadLog();
		
		createNewRoster();
		
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
		
		private void createNewRoster() {
			studentRoster = new ClassOfStudents2();
			fileChooser = new JFileChooser(".");
			tableData = new String[64][2];
			
			studentRoster = new ClassOfStudents2();
			fileChooser = new JFileChooser(".");
			tableData = new String[64][2];
			
			setSize(1200,800);
			setLayout(new BorderLayout());
			
			nameInputPanel = new JPanel();
			textInputField = new JTextField(30);
			textInputField.setFont(textInputField.getFont().deriveFont(24.0f));
			
			JLabel nameInputLabel = new JLabel("Enter Student Name:");
			nameInputLabel.setFont(nameInputLabel.getFont ().deriveFont (24.0f));
			
			nameInputPanel.setLayout(new FlowLayout());
			nameInputPanel.add(nameInputLabel);
			nameInputPanel.add(textInputField);
			addButton = new JButton("Add Student To Roster");
			addButton.setFont(addButton.getFont().deriveFont(24.0f));
			nameInputPanel.add(addButton);
			
			namePanel = new JPanel();
			
			
			
			
			
			
			// cut this out and put in in the edit panel later
//			imageButton = new JButton("Select Photo");
//			noimage = null;
//			try {
//				noimage = ImageIO.read(new File("noimage.png")).getScaledInstance(300, 300, 1);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			saveButton = new JButton("Save Roster");
//			
//			nophoto = new JLabel(new ImageIcon(noimage));
//			
//			namePanel.setLayout(new GridBagLayout());
//			GridBagConstraints namegc = new GridBagConstraints();
//			namegc.anchor = GridBagConstraints.CENTER;
//			namegc.gridx=0;
//			namegc.gridy=0;
//			namegc.weightx=1;
//			namegc.weighty=1;
//			namePanel.add(new JLabel("Enter Student Name:"), namegc);
//			namegc.gridy = 1;
//			namePanel.add(textField, namegc);
//			namegc.gridy=2;
//			namePanel.add(imageButton, namegc);
//			namegc.gridy=3;
//			photo = nophoto;
//			namePanel.add(photo, namegc);
//			photogc.gridx=namegc.gridx;
//			photogc.gridy=namegc.gridy;
//			photogc.weightx=namegc.weightx;
//			photogc.weighty=namegc.weighty;
//			photogc.anchor=namegc.anchor;
//			namegc.gridy=5;
//			namegc.weighty=10;
//			namePanel.add(saveButton, namegc);
			
			table = new JTable(tableData, columnNames);
			
			add(namePanel, BorderLayout.WEST);
			add(nameInputPanel, BorderLayout.NORTH);
			add(new JScrollPane(table), BorderLayout.CENTER);
			setVisible(true);
			
			imageButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Image newStudentImage = selectImage();
					resizedImage = newStudentImage.getScaledInstance(300, 300, 1);
					miniImage = newStudentImage.getScaledInstance(60, 60, 1);
					namePanel.remove(photo);
					photo = new JLabel(new ImageIcon(resizedImage));
					namePanel.add(photo, photogc);
					namePanel.setVisible(true);
					setVisible(true);
					photoSelected = "Yes";
					
				}
			});
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addStudent();
				}
			});
			
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (currentFilename != null) saveDataForNew(currentFilename);
					else saveDataAsForNew();
				}
			});
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
		}
		
		
		
		
	
		private Image selectImage() {
			Image imageFromFile = null;
			
			if (fileChooser.showOpenDialog(BuildClassOfStudents.this) == JFileChooser.APPROVE_OPTION) {
				File infile = fileChooser.getSelectedFile();
				String filename = infile.getAbsolutePath();
				try {
					imageFromFile = ImageIO.read(new File(filename));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return imageFromFile;
		}
		
		private void addStudent() {
			Student2 newStudent = new Student2(textField.getText(), resizedImage, textField.getText()+"\n");
			studentRoster.add(newStudent);
			tableData[rosterSize][0] = textField.getText();
			tableData[rosterSize][1] = photoSelected;
			rosterSize++;
			remove(table);
			namePanel.remove(photo);
			photo = new JLabel(new ImageIcon(noimage));
			namePanel.add(photo, photogc);
			table = new JTable(tableData, columnNames);
			add(new JScrollPane(table), BorderLayout.CENTER);
			namePanel.setVisible(true);
			setVisible(true);
			textField.setText("");
			photoSelected = "No";
			resizedImage = noimage.getScaledInstance(300, 300, 1);
			
		}
		
		
		private void saveDataForNew(String filename) {
			try {
				// save current class roster
				FileOutputStream fout = new FileOutputStream(filename);
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(studentRoster);
				oos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void saveDataAsForNew() {
			if (currentFilename == null) {
				String className = (String) JOptionPane.showInputDialog("Enter a name for this course (e.g. English 101 9 am):");
				if (className != null) {
					currentFilename = String.valueOf(System.currentTimeMillis());
					rosterClassNames.add(className);
					rosterFileNames.add(currentFilename);
					saveLogForNew();
				}
			}
			if (currentFilename != null) saveDataForNew(currentFilename);
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
		
	}
