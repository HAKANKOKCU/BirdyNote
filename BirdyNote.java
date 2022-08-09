import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.text.*;
import javax.swing.undo.*;
import java.util.*;
class BirdyNote {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				System.out.println("Loading..");
				mapp application = new mapp();
				application.GUI();
				if (args.length != 0) {
					application.LoadText(args[0]);
				}
			}
		});
	}
}

class mapp implements ActionListener {
	JTextArea maintb = new JTextArea();
	JFrame frame = new JFrame();
	String currentFile = "";
	//UndoManager Umanager = new UndoManager();
	public void GUI() {
		JScrollPane scrollPane = new JScrollPane(maintb);
		//maintb.getDocument().addUndoableEditListener(Umanager);
		//maintb.getDocument().addUndoableEditListener(new UndoableEditListener() {
			//public void undoableEditHappened(UndoableEditEvent evt) {
				//Umanager.addEdit(evt.getEdit());
			//}
		//});
		//// Create an undo action and add it to the text component
		//maintb.getActionMap().put("Undo",
		//	new AbstractAction("Undo") {
		//	public void actionPerformed(ActionEvent evt) {
        //    try {
        //        if (Umanager.canUndo()) {
        //            Umanager.undo();
        //        }
        //    } catch (CannotUndoException e) {
        //    }
        //}
		//});

		//// Bind the undo action to ctl-Z
		//maintb.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
		frame.add(scrollPane, BorderLayout.CENTER);
		JMenuBar menuBar;
		JMenu tmenu, tedit;
		JMenuItem OpenMenuItem, SaveMenuItem, SaveCurtMenuItem, NewMenuItem, PasteMenuItem, CopyMenuItem, CutMenuItem, FindMenuItem, ReplaceMenuItem;

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		tmenu = new JMenu("File");
		tmenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(tmenu);
		
		tedit = new JMenu("Edit");
		tedit.setMnemonic(KeyEvent.VK_E);
		menuBar.add(tedit);

		//a group of JMenuItems
		FindMenuItem = new JMenuItem("Find..",
                         KeyEvent.VK_F);
		FindMenuItem.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		tedit.add(FindMenuItem);
		FindMenuItem.addActionListener(this);
		
		ReplaceMenuItem = new JMenuItem("Replace..",
                         KeyEvent.VK_R);
		ReplaceMenuItem.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		tedit.add(ReplaceMenuItem);
		ReplaceMenuItem.addActionListener(this);
		
		tedit.addSeparator();
		
		PasteMenuItem = new JMenuItem("Paste",
                         KeyEvent.VK_V);
		tedit.add(PasteMenuItem);
		PasteMenuItem.addActionListener(this);
		
		CopyMenuItem = new JMenuItem("Copy",
                         KeyEvent.VK_C);
		tedit.add(CopyMenuItem);
		CopyMenuItem.addActionListener(this);
		
		CutMenuItem = new JMenuItem("Cut",
                         KeyEvent.VK_X);
		tedit.add(CutMenuItem);
		CutMenuItem.addActionListener(this);
		
		
		NewMenuItem = new JMenuItem("New",
                         KeyEvent.VK_N);
		NewMenuItem.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		tmenu.add(NewMenuItem);
		
		OpenMenuItem = new JMenuItem("Open",
                         KeyEvent.VK_O);
		OpenMenuItem.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		tmenu.add(OpenMenuItem);
		
		SaveMenuItem = new JMenuItem("Save As..",
                         KeyEvent.VK_A);
		tmenu.add(SaveMenuItem);
		
		SaveCurtMenuItem = new JMenuItem("Save",
                         KeyEvent.VK_S);
		SaveCurtMenuItem.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		tmenu.add(SaveCurtMenuItem);
		
		NewMenuItem.addActionListener(this);
		OpenMenuItem.addActionListener(this);
		SaveMenuItem.addActionListener(this);
		SaveCurtMenuItem.addActionListener(this);
		JPopupMenu menu = new JPopupMenu("Menu");
		JMenuItem cut = new JMenuItem("Cut");
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem paste = new JMenuItem("Paste");
		cut.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		menu.add(paste);
		menu.add(cut); 
		menu.add(copy);
		maintb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//right mouse click event
				if (SwingUtilities.isRightMouseButton(e)){
					menu.show(frame , e.getX() - scrollPane.getHorizontalScrollBar().getValue(), e.getY() - scrollPane.getVerticalScrollBar().getValue() + 60);
				}
			}               
		});
		//JPanel toolbar = new JPanel();
		//toolbar.setLayout(new FlowLayout());
		//JButton openButton = new JButton("Open");
		////openButton.setBorderPainted(false);
		//openButton.setContentAreaFilled(false);
		////filechooser f1 = new filechooser();
		//openButton.addActionListener(this);
		//toolbar.add(openButton);
		//JButton saveButton = new JButton("Save");
		////saveButton.setBorderPainted(false);
		//saveButton.setContentAreaFilled(false);
		////filechooser f1 = new filechooser();
		//saveButton.addActionListener(this);
		//toolbar.add(saveButton);
		//frame.add(toolbar,BorderLayout.SOUTH);
		frame.setTitle("Birdy Note");
		frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.pack();
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		findGUI();
		replaceGUI();
	}
	
	JFrame findUI = new JFrame();
	JTextField findTB = new JTextField();
	JFrame replaceUI = new JFrame();
	JTextField replaceFromTB = new JTextField("",20);
	JTextField replaceToTB = new JTextField("",20);
	
	public void findGUI() {
		findUI.add(findTB, BorderLayout.CENTER);
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new FlowLayout());
		JButton findButton = new JButton("Find");
		findButton.addActionListener(this);
		toolbar.add(findButton);
		//JButton findNextButton = new JButton("Find Next");
		//findNextButton.addActionListener(this);
		//toolbar.add(findNextButton);
		findUI.add(toolbar, BorderLayout.SOUTH);
		findUI.pack();
		findUI.setLocationRelativeTo(null);
	}
	
	public void replaceGUI() {
		JPanel tbtoolbar = new JPanel();
		tbtoolbar.setLayout(new FlowLayout());
		tbtoolbar.add(replaceFromTB);
		tbtoolbar.add(replaceToTB);
		tbtoolbar.setLayout(new FlowLayout());
		JButton rpButton = new JButton("Replace");
		rpButton.addActionListener(this);
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new FlowLayout());
		toolbar.add(rpButton);
		replaceUI.add(tbtoolbar, BorderLayout.CENTER);
		replaceUI.add(toolbar, BorderLayout.SOUTH);
		replaceUI.pack();
		replaceUI.setLocationRelativeTo(null);
	}
	
	Highlighter.HighlightPainter painter = 
					new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Listened event");
		String cmd = e.getActionCommand();
		if (cmd.equals("Open")) {
			JFileChooser j = new JFileChooser();
			int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
				System.out.println(j.getSelectedFile().getAbsolutePath());
				frame.setTitle("Birdy Note - " + j.getSelectedFile().getAbsolutePath());
				try {
				FileReader reader = new FileReader(j.getSelectedFile().getAbsolutePath());
				maintb.read(reader, j.getSelectedFile().getAbsolutePath());
				}catch (IOException err) {}
				currentFile = j.getSelectedFile().getAbsolutePath();
			}
		}else if (cmd.equals("Save As..")) {
			userSaveFile();
		}else if (cmd.equals("Save")) {
			SaveCurrentFile();
		}else if (cmd.equals("New")) {
			int input = JOptionPane.showConfirmDialog(null, "Save This File?");
			// 0=yes, 1=no, 2=cancel
			System.out.println(input);
			if (input == 0) {
				if (currentFile == "") {
					if (userSaveFile() == 1) {
						maintb.setText("");
						currentFile = "";
						frame.setTitle("Birdy Note");
					}
				}else {
					SaveCurrentFile();
					maintb.setText("");
					currentFile = "";
					frame.setTitle("Birdy Note");
				}
			}else if (input == 1) {
				maintb.setText("");
				currentFile = "";
				frame.setTitle("Birdy Note");
			}
		}else if (cmd.equals("Paste")) {
			maintb.paste();
		}else if (cmd.equals("Copy")) {
			maintb.copy();
		}else if (cmd.equals("Cut")) {
			maintb.cut();
		}else if (cmd.equals("Find")) {
			//maintb.find(findTB.getText());
			maintb.getHighlighter().removeAllHighlights();
			if (!findTB.getText().isEmpty()) {

				int offset = maintb.getText().indexOf(findTB.getText());
				int length = findTB.getText().length(); 

				while ( offset != -1)
				{
					try
					{
						maintb.getHighlighter().addHighlight(offset, offset + length, painter);
						offset = maintb.getText().indexOf(findTB.getText(), offset+1);
					}catch(BadLocationException exception) { System.out.println(exception); }
				}
			}
		}else if (cmd.equals("Find Next")) {
			//maintb.findNext();
		}else if (cmd.equals("Replace")) {
			String txt = maintb.getText();
			String txt2 = replaceFromTB.getText();
			String txt3 = replaceToTB.getText();

			if (txt.toLowerCase().contains(txt2.toLowerCase())) {
				maintb.setText(txt.replaceAll("(?i)" + txt2, txt3));
			}
		}else if (cmd.equals("Find..")) {
			findUI.setVisible(true);
		}else if (cmd.equals("Replace..")) {
			replaceUI.setVisible(true);
		}
	}
	
	public int userSaveFile() {
		JFileChooser j = new JFileChooser();
		int r = j.showSaveDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
			System.out.println(j.getSelectedFile().getAbsolutePath());
			frame.setTitle("Birdy Note - " + j.getSelectedFile().getAbsolutePath());
			try {
				PrintWriter out = new PrintWriter(j.getSelectedFile().getAbsolutePath());
				out.println(maintb.getText());
				out.close();
			}catch (IOException err) {}
			currentFile = j.getSelectedFile().getAbsolutePath();
			return 1;
		}else {return 0;}
	}
	
	public void LoadText(String path) {
		if (path != "") {
			frame.setTitle("Birdy Note - " + path);
			try {
				FileReader reader = new FileReader(path);
				currentFile = path;
				maintb.read(reader, path);
			}catch (IOException err) {}
		}
	}
	
	public void SaveCurrentFile() {
		if (currentFile != "") {
			try {
				PrintWriter out = new PrintWriter(currentFile);
				out.println(maintb.getText());
				out.close();
			}catch (IOException err) {}
		}else {
			userSaveFile();
		}
	}
}