import java.awt.Image;
import java.io.Serializable;


public class Student implements Serializable {

	private String studentName;
	private String imageFilename;
	private String notes;
	private Image fullpic;
	private Image iconpic;
	
	public Student(String name, String imageFilename, String notes) {
		this.studentName = name;
		this.imageFilename = imageFilename;
		this.notes = notes;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getImageFilename() {
		return imageFilename;
	}

	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Image getFullpic() {
		return fullpic;
	}

	public void setFullpic(Image fullpic) {
		this.fullpic = fullpic;
	}

	public Image getIconpic() {
		return iconpic;
	}

	public void setIconpic(Image iconpic) {
		this.iconpic = iconpic;
	}
	
	
	
	
	
}
