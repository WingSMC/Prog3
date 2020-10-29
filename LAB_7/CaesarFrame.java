import javax.swing.*;
import java.awt.*;

public class CaesarFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JComboBox<Character> offset;
	private JTextField input;
	private JButton encode;
	private JTextField output;

	public CaesarFrame() {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("SwingLab");
		this.setSize(400, 110);
		this.setResizable(false);
		this.setLayout(new BorderLayout());

		Character offsets[] = new Character[26];
		for (byte i = 0; i < offsets.length; ++i) {
			offsets[i] = (char)('A' + i);
		}
		this.offset = new JComboBox<Character>(offsets);
		this.input = new JTextField(20);
		this.encode = new JButton("Code!");
		this.output = new JTextField();
		this.output.setEditable(false);

		this.encode.addActionListener(e -> {
			this.output.setText(
				this.caesarCode(
					this.input.getText(),
					(char)this.offset.getSelectedItem()
				)
			);
		});

		JPanel panel1 = new JPanel();
		panel1.add(this.offset);
		panel1.add(this.input);
		panel1.add(this.encode);

		JPanel panel2 = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Output:");
 		panel2.add(label, BorderLayout.LINE_START);
		panel2.add(this.output, BorderLayout.CENTER);

		this.add(panel1, BorderLayout.PAGE_START);
		this.add(panel2, BorderLayout.CENTER);
		this.setVisible(true);
	}

	private char caesarHelper(char c, short offset) {
		short cn = (short)c;
		short type = 0; // 0: not a letter, 97: small, 65: caps
		if('a' <= c && c <= 'z') {
			type = 97;
		} else if ('A' <= c && c <= 'Z') {
			type = 65;
		}
		if(type == 0) return c;
		offset -= 65;
		cn -= type - offset;
		cn %= 26;
		return (char)(cn + type);
	}
	private String caesarCode(String input, char offset) {
		char inputArray[] = input.toCharArray();
		char output[] = new char[inputArray.length];
		for(int i = 0; i < inputArray.length; ++i) {
			output[i] = caesarHelper(inputArray[i], (short)offset);
		}
		return String.valueOf(output);
	}
}
