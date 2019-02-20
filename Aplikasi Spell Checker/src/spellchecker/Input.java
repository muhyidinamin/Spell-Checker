package spellchecker;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JSeparator;

public class Input extends JFrame{

	private JFrame frame;
	private JTextField txtPath;
	private JPanel panel;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Input window = new Input();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public Input() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		this.setTitle("Spell Checker Application");
		ImageIcon img = new ImageIcon("D:/Dokumen Skripsi/primary_spellcheck_actual.png");
		this.setIconImage(img.getImage());
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBackground(new Color(96, 150, 251));
		panel.setLayout(null);
		setContentPane(panel);
		
		JButton btnNext = new JButton("Lanjutkan");
		btnNext.setEnabled(false);	
		btnNext.setFont(new Font("Californian FB", Font.PLAIN, 13));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Deteksi readfile = new Deteksi(txtPath.getText());
				readfile.setVisible(true);
				setVisible(false);
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(96, 150, 251));
		
		JLabel lblMasukkan = new JLabel("Masukkan Dokumen yang akan di cek");
		lblMasukkan.setForeground(Color.WHITE);
		lblMasukkan.setFont(new Font("Californian FB", Font.BOLD, 14));
		
		JLabel lblSpellCheckerApplication = new JLabel("Typographical Error Detection");
		lblSpellCheckerApplication.setLocation(226, 16);
		lblSpellCheckerApplication.setForeground(Color.WHITE);
		lblSpellCheckerApplication.setFont(new Font("Californian FB", Font.BOLD, 28));
		
		JLabel label = new JLabel("Copyright \u00A9 2018 Muhamad Muhyidin Amin");
		label.setLocation(283, 530);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Californian FB", Font.PLAIN, 12));
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(280, Short.MAX_VALUE)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
					.addGap(232))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(64)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblMasukkan, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNext)
							.addGap(59))))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(220)
					.addComponent(lblSpellCheckerApplication)
					.addContainerGap(189, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSpellCheckerApplication, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(75)
					.addComponent(lblMasukkan)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(9)
							.addComponent(btnNext))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
					.addComponent(label)
					.addContainerGap())
		);
		panel_1.setLayout(null);
		
		JButton btnBrowse = new JButton("Pilih File");
		btnBrowse.setFont(new Font("Californian FB", Font.PLAIN, 13));
		btnBrowse.setBounds(7, 12, 91, 23);
		panel_1.add(btnBrowse);
		
		
		txtPath = new JTextField();
		txtPath.setEditable(false);
		txtPath.setForeground(Color.WHITE);
		txtPath.setBounds(118, 12, 414, 22);

		panel_1.add(txtPath);
		txtPath.setFont(new Font("Californian FB", Font.PLAIN, 14));
		txtPath.setBorder(null);
		txtPath.setOpaque(false);
		txtPath.setColumns(30);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(119, 32, 413, 2);
		panel_1.add(separator);
		
		btnBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();
		    	  
				// For Directory
				//fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		   
				// For File
		        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.pdf *.txt", "pdf",  "txt");
		        fileChooser.setFileFilter(filter);
		        
		        //fileChooser.setAcceptAllFileFilterUsed(false);
		   
		        int rVal = fileChooser.showOpenDialog(null);
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		        	txtPath.setText(fileChooser.getSelectedFile().toString());
		        }
		        if(txtPath.getText().equals("")) {
		        	btnNext.setEnabled(false);
		        }else {
		        	btnNext.setEnabled(true);
		        }
		        
		        }
		});
		panel.setLayout(gl_panel);
	}
}
