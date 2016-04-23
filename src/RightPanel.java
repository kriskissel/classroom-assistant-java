import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class RightPanel extends JPanel {

	private ImagePanel imagePanel;
	private JPanel bottomPanel1;
	private JPanel bottomPanel2;
	private JPanel bottomPanel3;
	private Dimension dim;
	private JLabel imageCaption;
	private JButton upButton;
	private JButton downButton;
	private JButton upButton2;
	private JButton downButton2;
	private JButton upButton3;
	private JButton downButton3;
	private Student2 student;
	private ImageIcon upIcon;
	private ImageIcon downIcon;
	private JLabel counterValue;
	private JLabel counter2Value;
	private JLabel counter3Value;
	GridBagConstraints gc = new GridBagConstraints();
	GridBagConstraints bgc = new GridBagConstraints();
	GridBagConstraints b2gc = new GridBagConstraints();
	GridBagConstraints b3gc = new GridBagConstraints();
	String[] counterNames;
	
	public RightPanel(ImagePanel imgPanel, Student2 student, String[] counterNames) {
		this.student = student;
		this.counterNames = counterNames;
		Dimension dim2 = new Dimension();
		dim2.width = 320;
		dim2.height = 300;
		setPreferredSize(dim2);
		
		loadIcons();
		
		imagePanel = imgPanel;
		String imageText;
		if (student == null) imageText = "";
		else imageText = student.getStudentName();
		imageCaption = new JLabel(imageText);
		imageCaption.setFont(imageCaption.getFont().deriveFont(22.0f));
		
		imageCaption.setSize(new Dimension(20,200));
		
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		bottomPanel1 = new JPanel();
		
		upButton = new JButton(upIcon);
		upButton.setBorder(emptyBorder);
		downButton = new JButton(downIcon);
		downButton.setBorder(emptyBorder);
		
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				student.incrementCounter(0);
				refreshCounterView();
				
			}
		});
		
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				student.decrementCounter(0);
				refreshCounterView();
			}
		});
		
		
		setLayout(new GridBagLayout());
		
		// Add Image Panel
		
		gc.anchor=GridBagConstraints.CENTER;
		gc.gridx=0;
		gc.gridy=0;
		gc.weighty=0;
		gc.weightx=1;
		
		add(imagePanel, gc);
		
		gc.gridy = 1;
		gc.weighty = 1;
		add(imageCaption, gc);
		
		
		
		// Add 1st Counter
		
		gc.gridy = 2;
		gc.weighty = 10;
		add(bottomPanel1, gc);
		
		dim = imagePanel.getPreferredSize();
		bottomPanel1.setLayout(new GridBagLayout());
		bottomPanel1.setMaximumSize(dim);
		
		bgc.anchor=GridBagConstraints.CENTER;
		bgc.gridx=2;
		bgc.gridy=1;
		bgc.weightx=1;
		bgc.weighty=1;
		
		JLabel counter1label = new JLabel(counterNames[0]);
		counter1label.setFont(counter1label.getFont().deriveFont(16.0f));
		bottomPanel1.add(counter1label, bgc);
		
		bgc.gridx = 1;
		bgc.gridy = 2;
		bottomPanel1.add(downButton, bgc);

		bgc.gridx = 2;
		int counter0 = 0;
		if (student != null) counter0 = student.getCounter(0);
		counterValue = new JLabel(String.valueOf(counter0));
		counterValue.setFont(counterValue.getFont().deriveFont(24.0f));
		bottomPanel1.add(counterValue,bgc);
		
		bgc.gridx=3;
		bottomPanel1.add(upButton, bgc);
		
		
		
		// Add 2nd Counter
		
		bottomPanel2 = new JPanel();
		
		upButton2 = new JButton(upIcon);
		upButton2.setBorder(emptyBorder);
		downButton2 = new JButton(downIcon);
		downButton2.setBorder(emptyBorder);
		
		upButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				student.incrementCounter(1);
				refreshCounter2View();
				
			}
		});
		
		downButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				student.decrementCounter(1);
				refreshCounter2View();
			}
		});
		gc.gridy = 3;
		gc.weighty = 10;
		add(bottomPanel2, gc);
		
		bottomPanel2.setLayout(new GridBagLayout());
		bottomPanel2.setMaximumSize(dim);
		
		b2gc.anchor=GridBagConstraints.CENTER;
		b2gc.gridx=2;
		b2gc.gridy=3;
		b2gc.weightx=1;
		b2gc.weighty=1;
		
		JLabel counter2label = new JLabel(counterNames[1]);
		counter2label.setFont(counter2label.getFont().deriveFont(16.0f));
		bottomPanel2.add(counter2label, b2gc);
		
		b2gc.gridx = 1;
		b2gc.gridy = 4;
		bottomPanel2.add(downButton2, b2gc);

		b2gc.gridx = 2;
		int counter1 = 0;
		if (student != null) counter1 = student.getCounter(1);
		counter2Value = new JLabel(String.valueOf(counter1));
		counter2Value.setFont(counter2Value.getFont().deriveFont(24.0f));
		bottomPanel2.add(counter2Value,b2gc);
		
		b2gc.gridx=3;
		bottomPanel2.add(upButton2, b2gc);
		
		
		
		// Add 3rd Counter

		bottomPanel3 = new JPanel();

		upButton3 = new JButton(upIcon);
		upButton3.setBorder(emptyBorder);
		downButton3 = new JButton(downIcon);
		downButton3.setBorder(emptyBorder);

		upButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				student.incrementCounter(2);
				refreshCounter3View();

			}
		});

		downButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				student.decrementCounter(2);
				refreshCounter3View();
			}
		});
		gc.gridy = 4;
		gc.weighty = 10;
		add(bottomPanel3, gc);

		bottomPanel3.setLayout(new GridBagLayout());
		bottomPanel3.setMaximumSize(dim);

		b3gc.anchor=GridBagConstraints.CENTER;
		b3gc.gridx=2;
		b3gc.gridy=3;
		b3gc.weightx=1;
		b3gc.weighty=1;

		JLabel counter3label = new JLabel(counterNames[2]);
		counter3label.setFont(counter3label.getFont().deriveFont(16.0f));
		bottomPanel3.add(counter3label, b3gc);

		b3gc.gridx = 1;
		b3gc.gridy = 4;
		bottomPanel3.add(downButton3, b3gc);

		b3gc.gridx = 2;
		int counter2 = 0;
		if (student != null) counter2 = student.getCounter(2);
		counter3Value = new JLabel(String.valueOf(counter2));
		counter3Value.setFont(counter3Value.getFont().deriveFont(24.0f));
		bottomPanel3.add(counter3Value,b3gc);

		b3gc.gridx=3;
		bottomPanel3.add(upButton3, b3gc);

				
				
		
		setVisible(true);
		
		
	}
	
	private void refreshCounterView() {
		setVisible(false);
		bottomPanel1.remove(counterValue);
		bgc.gridy = 2;
		bgc.gridx = 2;
		counterValue = new JLabel(String.valueOf(student.getCounter(0)));
		counterValue.setFont(counterValue.getFont().deriveFont(24.0f));
		bottomPanel1.add(counterValue, bgc);
		setVisible(true);
	}
	
	private void refreshCounter2View() {
		setVisible(false);
		bottomPanel2.remove(counter2Value);
		b2gc.gridy = 4;
		b2gc.gridx = 2;
		counter2Value = new JLabel(String.valueOf(student.getCounter(1)));
		counter2Value.setFont(counter2Value.getFont().deriveFont(24.0f));
		bottomPanel2.add(counter2Value, b2gc);
		setVisible(true);
	}
	
	private void refreshCounter3View() {
		setVisible(false);
		bottomPanel3.remove(counter3Value);
		b3gc.gridy = 4;
		b3gc.gridx = 2;
		counter3Value = new JLabel(String.valueOf(student.getCounter(2)));
		counter3Value.setFont(counter3Value.getFont().deriveFont(24.0f));
		bottomPanel3.add(counter3Value, b3gc);
		setVisible(true);
	}
	
	private void loadIcons() {
		upIcon = new ImageIcon("up.png");
		Image upImage = upIcon.getImage().getScaledInstance(37, 37, 0);
		upIcon = new ImageIcon(upImage);
		downIcon = new ImageIcon("down.png");
		Image downImage = downIcon.getImage().getScaledInstance(37, 37, 0);
		downIcon = new ImageIcon(downImage);
	}

	
	
}
