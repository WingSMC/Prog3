package swingmvclab;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import javax.swing.*;

/*
 * A megjelenítendõ ablakunk osztálya.
 */
public class StudentFrame extends JFrame {
	/*
	 * Ebben az objektumban vannak a hallgatói adatok. A program indulás után
	 * betölti az adatokat fájlból, bezáráskor pedig kimenti oda.
	 * 
	 * NE MÓDOSÍTSD!
	 */
	private StudentData data;

	private JTextField nameField, neptunField;
	
	/*
	 * Itt hozzuk létre és adjuk hozzá az ablakunkhoz a különbözõ komponenseket
	 * (táblázat, beviteli mezõ, gomb).
	 */
	private void initComponents() {
		this.setLayout(new BorderLayout());
			JTable studentTable = new JTable(this.data);
			studentTable.setFillsViewportHeight(true);
		this.add(new JScrollPane(studentTable), BorderLayout.CENTER);
			JPanel addPanel = new JPanel();
				addPanel.add(new JLabel("Név:"));
				this.nameField = new JTextField(20);
				addPanel.add(this.nameField);
				addPanel.add(new JLabel("Neptun:"));
				this.neptunField = new JTextField(6);
				addPanel.add(this.neptunField);
				JButton addButton = new JButton("Felvesz");
				addButton.addActionListener(e -> {
					if(!(
						this.nameField.getText().length() > 0 &&
						this.neptunField.getText().length() > 0
					)) return;
					this.data.addStudent(this.nameField.getText(), this.neptunField.getText());
					this.nameField.setText("");
					this.neptunField.setText("");
				});
			addPanel.add(addButton);
		this.add(addPanel, BorderLayout.SOUTH);
	}

	/*
	 * Az ablak konstruktora.
	 * 
	 * NE MÓDOSÍTSD!
	 */
	@SuppressWarnings("unchecked")
	public StudentFrame() {
		super("Hallgatói nyilvántartás");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Induláskor betöltjük az adatokat
		try {
			data = new StudentData();
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"));
			data.students = (List<Student>)ois.readObject();
			ois.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		// Bezáráskor mentjük az adatokat
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"));
					oos.writeObject(data.students);
					oos.close();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		// Felépítjük az ablakot
		setMinimumSize(new Dimension(500, 200));
		initComponents();
	}

	/*
	 * A program belépési pontja.
	 * 
	 * NE MÓDOSÍTSD!
	 */
	public static void main(String[] args) {
		// Megjelenítjük az ablakot
		StudentFrame sf = new StudentFrame();
		sf.setVisible(true);
	}
}
