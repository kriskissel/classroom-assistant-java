import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

//import org.bytedeco.javacpp.opencv_core.IplImage;
//import org.bytedeco.javacv.OpenCVFrameGrabber;


public class EditPanel extends JPanel {

	private Image blankImage;
	private ImageIcon image;
	private String studentName;
	private JLabel nameLabel;
	private JLabel photoLabel;
	private Student2 currentStudent;
	private JFileChooser fileChooser;
	private JButton imageButton;
	private JButton changeNameButton;
	private JButton cameraButton;
	private JButton saveButton;
	private JButton removeButton;
	private JButton exitButton;
	private GridBagConstraints gc;
	private EditPanelListener editEventListener;
	
	public EditPanel(Student2 student){
		
		fileChooser = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		fileChooser.setCurrentDirectory(workingDirectory);
		// The following two lines add a filer so only image files can be selected
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
			    "Image files", ImageIO.getReaderFileSuffixes());
		fileChooser.addChoosableFileFilter(imageFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		PreviewPane previewPane = new PreviewPane();
		fileChooser.setAccessory(previewPane);
		fileChooser.addPropertyChangeListener(previewPane);
		

		this.currentStudent = student;
		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.CENTER;
		
		if (currentStudent == null) {image = loadNoImage(); studentName = "";}
		else {image = currentStudent.getPic(); studentName = currentStudent.getStudentName();}
		
		gc.gridx = 1;
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		nameLabel = new JLabel("Current Student: "+studentName);
		nameLabel.setFont(nameLabel.getFont().deriveFont(18.0f));
		add(nameLabel, gc);
		
		gc.gridy = 2;
		photoLabel = new JLabel(image);
		add(photoLabel, gc);
		
		gc.gridy = 3;
		imageButton = new JButton("Select Image From File");
		if (student == null) {imageButton.setEnabled(false);
		}
		imageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Image newImage = selectImage();
				if (newImage != null) {
					image = new ImageIcon(newImage.getScaledInstance(320, 180, 1));
					ImageIcon smallImage = new ImageIcon(newImage.getScaledInstance(64, 36, 1));
					currentStudent.setPic(image);
					currentStudent.setSmallpic(smallImage);
					refreshImage();
					EditPanelEvent ev = new EditPanelEvent(this, "selectimage", null);
					if (editEventListener != null) {
						editEventListener.EditPanelEventOccurred(ev);
					}
				}
				
			}
		});
		add(imageButton, gc);
		
		
		/*
		 * 
		 * The camera is not yet working
		gc.gridy = 4;
		cameraButton = new JButton("Take photo with camera");
		cameraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Image newImage = (Image) captureFrame();
				if (newImage != null) {
					image = new ImageIcon(newImage.getScaledInstance(320, 180, 1));
					ImageIcon smallImage = new ImageIcon(newImage.getScaledInstance(64, 36, 1));
					currentStudent.setPic(image);
					currentStudent.setSmallpic(smallImage);
					refreshImage();
					EditPanelEvent ev = new EditPanelEvent(this, "captureimage");
					if (editEventListener != null) {
						editEventListener.EditPanelEventOccurred(ev);
					}
				}
			}
		});
		add(cameraButton, gc);
		
		*/
		
		
		
		
		changeNameButton = new JButton("Change Name of Student");
		if (student == null) changeNameButton.setEnabled(false);
		changeNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ar0) {
				EditPanelEvent ev = new EditPanelEvent(this, "rename", currentStudent);
				if (editEventListener != null) {
					editEventListener.EditPanelEventOccurred(ev);
				}
			}
		});
		
		gc.gridy = 5;
		add(changeNameButton, gc);
		
		
		
		removeButton = new JButton("Remove Student From Roster");
		if (student == null) removeButton.setEnabled(false);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EditPanelEvent ev = new EditPanelEvent(this, "remove", currentStudent);
				if (editEventListener != null) {
					editEventListener.EditPanelEventOccurred(ev);
				}
			}
		});
		
		gc.gridy = 6;
		add(removeButton, gc);
		
		
		saveButton = new JButton("Save Roster");
		if (student == null) {saveButton.setEnabled(false);}
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditPanelEvent ev = new EditPanelEvent(this, "save", null);
				if (editEventListener != null) {
					editEventListener.EditPanelEventOccurred(ev);
				}
			}
		
		});
		
		gc.gridy = 7;
		add(saveButton, gc);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				//EditPanelEvent ev = new EditPanelEvent(this, "save");
				//if (editEventListener != null) {
				//	editEventListener.EditPanelEventOccurred(ev);
				}
			});
		
		gc.gridy = 8;
		add(exitButton, gc);
		
		setVisible(true);
		
		
	}
	
	private ImageIcon loadNoImage(){
		try {
			ImageIcon noPhoto = new ImageIcon(ImageIO.read(new File("noimage.png")).getScaledInstance(320, 180, 1));
			return noPhoto;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private Image selectImage() {
		Image imageFromFile = null;
		
		if (fileChooser.showOpenDialog(EditPanel.this) == JFileChooser.APPROVE_OPTION) {
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

	private void refreshImage() {
		setVisible(false);
		remove(photoLabel);
		photoLabel = new JLabel(currentStudent.getPic());
		gc.gridy = 2;
		add(photoLabel, gc);
		setVisible(true);
	}
	
	public void setEditPanelListener(EditPanelListener listener) {
		this.editEventListener = listener;
	}

	// This is for previewing images in the file chooser.
	static class PreviewPane extends JPanel implements PropertyChangeListener {
		private JLabel label;
		private int maxImgWidth;
		public PreviewPane() {
			setLayout(new BorderLayout(5,5));
			setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			add(new JLabel("Preview:"), BorderLayout.NORTH);
			label = new JLabel();
			label.setBackground(Color.WHITE);
			label.setOpaque(true);
			label.setPreferredSize(new Dimension(320, 180));
			maxImgWidth = 195;
			label.setBorder(BorderFactory.createEtchedBorder());
			add(label, BorderLayout.CENTER);
		}
		public void propertyChange(PropertyChangeEvent evt) {
			Icon icon = null;
			if(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(evt
					.getPropertyName())) {
				File newFile = (File) evt.getNewValue();
				if(newFile != null) {
					String path = newFile.getAbsolutePath();
					if(path.endsWith(".gif") || path.endsWith(".jpg") || path.endsWith(".png") || path.endsWith(".bmp")) {
						try {
							BufferedImage img = ImageIO.read(newFile);
							float width = img.getWidth();
							float height = img.getHeight();
							float scale = height / width;
							width = maxImgWidth;
							height = (width * scale); // height should be scaled from new width							
							icon = new ImageIcon(img.getScaledInstance(Math.max(1, (int)width), Math.max(1, (int)height), Image.SCALE_SMOOTH));
						}
						catch(IOException e) {
							// couldn't read image.
						}
					}
				}
					
				label.setIcon(icon);
				this.repaint();
						
			}
		}
	}
/*	
	private static BufferedImage captureFrame() {
        // 0-default camera, 1 - next...so on
		BufferedImage image;
        final OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try {
			grabber.start();
			IplImage img = grabber.grab();
			grabber.stop();
			image = img.getBufferedImage();
			System.out.println("got this far");
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			image=null;
		}
        return image;
    }
*/
	
}
