import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class ToolBar extends JPanel {

	private JButton saveButton;
	private ImageIcon saveIcon;
	private ImageIcon previousIcon;
	private ImageIcon nextIcon;
	private ImageIcon exitIcon;
	private ImageIcon settingsIcon;
	private ImageIcon randomIcon;
	private JButton loadButton;
	private JButton saveAsButton;
	private JButton randomButton;
	private JButton previousButton;
	private JButton nextButton;
	private JButton exitButton;
	private JButton settingsButton;
	private JButton dateButton;
	private ToolbarListener toolbarListener;
	
	public ToolBar() {
		 saveIcon = new ImageIcon("floppy.png");
		 saveButton = new JButton(saveIcon);
		 saveButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 
				 ToolbarEvent ev = new ToolbarEvent(this, "save");
				 
				 if (toolbarListener != null) {
					 toolbarListener.toolbarEventOccurred(ev);
				 }
			 }
		 });
		 
		 saveAsButton = new JButton("Save as...");
		 saveAsButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 
				 ToolbarEvent ev = new ToolbarEvent(this, "saveAs");
				 
				 if (toolbarListener != null) {
					 toolbarListener.toolbarEventOccurred(ev);
				 }
			 }
		 });
		 
		 Border emptyBorder = BorderFactory.createEmptyBorder();
		 
		 randomIcon = new ImageIcon("random.png");
		 randomButton = new JButton(randomIcon);
		 randomButton.setBorder(emptyBorder);
		 randomButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 
				 ToolbarEvent ev = new ToolbarEvent(this, "random");
				 
				 if (toolbarListener != null) {
					 toolbarListener.toolbarEventOccurred(ev);
				 }
			 }
		 });
		 
		 previousIcon = new ImageIcon("back.png");
		 previousButton = new JButton(previousIcon);
		 previousButton.setBorder(emptyBorder);
		 previousButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 
				 ToolbarEvent ev = new ToolbarEvent(this, "previous");
				 
				 if (toolbarListener != null) {
					 toolbarListener.toolbarEventOccurred(ev);
				 }
			 }
		 });
		 
		 nextIcon = new ImageIcon("forward.png");
		 nextButton = new JButton(nextIcon);
		 nextButton.setBorder(emptyBorder);
		 nextButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 
				 ToolbarEvent ev = new ToolbarEvent(this, "next");
				 
				 if (toolbarListener != null) {
					 toolbarListener.toolbarEventOccurred(ev);
				 }
			 }
		 });
		 
		 exitIcon = new ImageIcon("exit.png");
		 exitButton = new JButton(exitIcon);
		 exitButton.setBorder(emptyBorder);
		 exitButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 
				 ToolbarEvent ev = new ToolbarEvent(this, "exit");
				 
				 if (toolbarListener != null) {
					 toolbarListener.toolbarEventOccurred(ev);
				 }
			 }
		 });
		 
		 ImageIcon settingsIcon = new ImageIcon("tools.png");
		 settingsButton = new JButton(settingsIcon);
		 settingsButton.setBorder(emptyBorder);
		 settingsButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 
				 ToolbarEvent ev = new ToolbarEvent(this, "settings");
				 
				 if (toolbarListener != null) {
					 toolbarListener.toolbarEventOccurred(ev);
				 }
			 }
		 });
		 
		 ImageIcon dateIcon = new ImageIcon("date.png");
		 dateButton = new JButton(dateIcon);
		 dateButton.setBorder(emptyBorder);
		 dateButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 
				 ToolbarEvent ev = new ToolbarEvent(this, "date");
				 
				 if (toolbarListener != null) {
					 toolbarListener.toolbarEventOccurred(ev);
				 }
			 }
		 });
		 
		 
		 setLayout(new FlowLayout(FlowLayout.LEFT));
		 //add(saveButton);
		 //add(saveAsButton);
		 add(randomButton);
		 add(previousButton);
		 add(nextButton);
		 add(dateButton);
		 add(settingsButton);
		 add(exitButton);
		 
	}
	
	public void setToolbarListener(ToolbarListener listener) {
		this.toolbarListener = listener;
	}
}
