package edu.najah.cap;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class Editor extends JFrame implements ActionListener, DocumentListener {

	public static  void main(String[] args) {
		new Editor();
	}

	public JEditorPane TexTextPanelanel;
	private JMenuBar menu;
	private JMenuItem copy, paste, cut, move;
	public boolean changed = false;
	private File file;

	public Editor() {
		super("Editor the name of our application");
		TexTextPanelanel = new JEditorPane();
		add(new JScrollPane(TexTextPanelanel), "Center");
		TexTextPanelanel.getDocument().addDocumentListener(this);

		menu = new JMenuBar();
		setJMenuBar(menu);
		BuildMenu();
		SizeOfWindow();
	}
	private void SizeOfWindow(){
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	private void BuildMenu() {
		buildFileMenu();
		buildEditMenu();
	}

	private void buildFileMenu() {
		JMenu file = new JMenu("File");
		file.setMnemonic('F');
		menu.add(file);
		JMenuItem n = new JMenuItem("New");
		n.setMnemonic('N');
		n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		n.addActionListener(this);
		file.add(n);
		JMenuItem open = new JMenuItem("Open");
		file.add(open);
		open.addActionListener(this);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		JMenuItem save = new JMenuItem("Save");
		file.add(save);
		save.setMnemonic('S');
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem saveas = new JMenuItem("Save as...");
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		file.add(saveas);
		saveas.addActionListener(this);
		JMenuItem quit = new JMenuItem("Quit");
		file.add(quit);
		quit.addActionListener(this);
		quit.setMnemonic('Q');
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
	}

	private void buildEditMenu() {
		JMenu edit = new JMenu("Edit");
		menu.add(edit);
		edit.setMnemonic('E');
		
		cut();
		copy();
		paste();
		move();
		 find();
		 select();
		
	}
private void cut() {
	JMenu edit = new JMenu("Edit");
	menu.add(edit);
	edit.setMnemonic('E');
	cut = new JMenuItem("Cut");
	cut.addActionListener(this);
	cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
	cut.setMnemonic('T');
	edit.add(cut);
	
}
private void copy() {
	JMenu edit = new JMenu("Edit");
	menu.add(edit);
	edit.setMnemonic('E');
	copy = new JMenuItem("Copy");
	copy.addActionListener(this);
	copy.setMnemonic('C');
	copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
	edit.add(copy);
}
private void paste() {
	JMenu edit = new JMenu("Edit");
	menu.add(edit);
	edit.setMnemonic('E');
	paste = new JMenuItem("Paste");
	paste.setMnemonic('P');
	paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
	edit.add(paste);
	paste.addActionListener(this);
}
private void move() {
	JMenu edit = new JMenu("Edit");
	menu.add(edit);
	edit.setMnemonic('E');
	move = new JMenuItem("Move");
	move.setMnemonic('M');
	move.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
	edit.add(move);
	move.addActionListener(this);move = new JMenuItem("Move");
	move.setMnemonic('M');
	move.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
	edit.add(move);
	move.addActionListener(this);
}
private void find() {
	JMenu edit = new JMenu("Edit");
	menu.add(edit);
	edit.setMnemonic('E');
	JMenuItem find = new JMenuItem("Find");
	find.setMnemonic('F');
	find.addActionListener(this);
	edit.add(find);
	find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
	JMenuItem find1 = new JMenuItem("Find");
	find1.setMnemonic('F');
	find1.addActionListener(this);
	edit.add(find1);
	find1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
}
private void select() {
	JMenu edit = new JMenu("Edit");
	menu.add(edit);
	edit.setMnemonic('E');
	JMenuItem sall = new JMenuItem("Select All");
	sall.setMnemonic('A');
	sall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
	sall.addActionListener(this);
	edit.add(sall);
}
@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Quit")) {
			System.exit(0);
		} else if (action.equals("Open")) {
			loadFile();
		} else if (action.equals("Save")) {
			
			int saveFile = 0;
			if (changed) {
				saveFile = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", 0, 2);
			}
			if (saveFile != 1) {
				if (file == null) {
					saveAs("Save");
				} else {
					String text = TextPanel.getText();
					System.out.println(text);
					try (PrintWriter writer = new PrintWriter(file);){
						if (!file.canWrite())
							throw new Exception("Cannot write file!");
						writer.write(text);
						changed = false;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} else if (action.equals("New")) {
			if (changed) {
				if (changed) {
					int saveFile = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
							0, 2);
					if (saveFile == 1)
						return;
				}
				if (file == null) {
					saveAs("Save");
					return;
				}
				String text = TextPanel.getText();
				System.out.println(text);
				try (PrintWriter writer = new PrintWriter(file);){
					if (!file.canWrite())
						throw new Exception("Cannot write file!");
					writer.write(text);
					changed = false;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			file = null;
			TextPanel.setText("");
			changed = false;
			setTitle("Editor");
		} else if (action.equals("Save as...")) {
			saveAs("Save as...");
		} else if (action.equals("Select All")) {
			TextPanel.selectAll();
		} else if (action.equals("Copy")) {
			TextPanel.copy();
		} else if (action.equals("Cut")) {
			TextPanel.cut();
		} else if (action.equals("Paste")) {
			TextPanel.paste();
		} else if (action.equals("Find")) {
			FindDialog find = new FindDialog(this, true);
			find.showDialog();
		}
	}


	private void loadFile() {
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setMultiSelectionEnabled(false);
		try {
			int cancelIsChosen = dialog.showOpenDialog(this);
			int approveYesOrOkIsChosen = dialog.showOpenDialog(this);

			if (cancelIsChosen == 1)
				return;
			if (approveYesOrOkIsChosen == 0) {
				if (changed){
					if (changed) {
						int answer = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
								0, 2);
						if (answer == 1)
							return;
					}
					if (file == null) {
						saveAs("Save");
						return;
					}
					String text = TextPanel.getText();
					System.out.println(text);
					try (PrintWriter writer = new PrintWriter(file);){
						if (!file.canWrite())
							throw new Exception("Cannot write file!");
						writer.write(text);
						changed = false;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				file = dialog.getSelectedFile();
				//Read file 
				StringBuilder rs = new StringBuilder();
				try (	FileReader fr = new FileReader(file);		
						BufferedReader reader = new BufferedReader(fr);) {
					String line;
					while ((line = reader.readLine()) != null) {
						rs.append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cannot read file !", "Error !", 0);//0 means show Error Dialog
				}
				
				TextPanel.setText(rs.toString());
				changed = false;
				setTitle("Editor - " + file.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			//0 means show Error Dialog
			JOptionPane.showMessageDialog(null, e, "Error", 0);
		}
	}

	
	private void saveAs(String dialogTitle) {
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setDialogTitle(dialogTitle);
		int approveYesOrOkIsChosen = dialog.showSaveDialog(this);
		if (approveYesOrOkIsChosen != 0)
			return;
		file = dialog.getSelectedFile();
		try (PrintWriter writer = new PrintWriter(file);){
			writer.write(TextPanel.getText());
			changed = false;
			setTitle("Editor - " + file.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private void saveAsText(String dialogTitle) {
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setDialogTitle(dialogTitle);
		int approveYesOrOkIsChosen = dialog.showSaveDialog(this);
		if (approveYesOrOkIsChosen != 0)//0 value if approve (yes, ok) is chosen.
			return;
		file = dialog.getSelectedFile();
		try (PrintWriter writer = new PrintWriter(file);){
			writer.write(TextPanel.getText());
			changed = false;
			setTitle("Save as Text Editor - " + file.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changed = true;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changed = true;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changed = true;
	}

}