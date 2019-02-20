package spellchecker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;

public class Deteksi extends JFrame {

	private JPanel contentPane;
	private static String filePath;
	private JList<String> typoList;
	public String[] word;
	public static String line; 
	public static String fileName = "kbbi.txt";
	public String rootWord; 
	private JButton btnCekKoreksi;
	private JLabel lblTypographicalErrorDetection;
	private JLabel lblDeteksiKesalahanPenulisan;
	private JLabel lblDaftarKesalahanPenulisan;
	private JLabel label_1;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Deteksi frame = new Deteksi(filePath);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Deteksi(String data) {
		this.filePath = data;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		this.setTitle("Spell Checker Application");
		ImageIcon img = new ImageIcon("D:/Dokumen Skripsi/primary_spellcheck_actual.png");
		this.setIconImage(img.getImage());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(96, 150, 251));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JScrollPane acrossScrollBar;
		
		ArrayList<String> listModel = new ArrayList<String>();
		
		ArrayList<String> dict = new ArrayList<String>();
		
		try {
			Scanner s = new Scanner(new File(fileName));
			
			while(s.hasNextLine()){
				dict.add(s.nextLine().toLowerCase());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileOperations fo = new FileOperations();
		String ext = extension(filePath, '.');
	    System.out.println(ext);
	    String sentence = null;	
	    
	    if(ext.equals("pdf")){
			String text = fo.read(filePath);
	    	sentence = text;
		}else if(ext.equals("txt")){
			sentence = readTxt();
		}
    	
		sentence = sentence.replaceAll("[^a-zA-Z]", " ");
        word = sentence.split(" ");
        
        System.out.println("Is ArrayList Empty: "+listModel.isEmpty());
        for (int i = 0; i < word.length; i++) {
            word[i] = word[i].toLowerCase();
            
    		if(dict.contains(word[i])){
    		}else{
    			rootWord = this.stemming(word[i]);
    			if (rootWord.equals(word[i])){
    				listModel.add(word[i]);
    				
    			}else{
    				if(dict.contains(rootWord)){
    	    		}else{
    	    			listModel.add(word[i]);
    	    			System.out.println(rootWord);
    	    		}
    			}
    		}
        }
        
        //create the list
        String[] target = new String[listModel.size()];

		
		btnCekKoreksi = new JButton("Cek Koreksi Kesalahan");
		btnCekKoreksi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TampilHasil(listModel).setVisible(true);
				setVisible(false);
			}
		});
		btnCekKoreksi.setFont(new Font("Californian FB", Font.PLAIN, 13));
		
		lblTypographicalErrorDetection = new JLabel("Typographical Error Detection");
		lblTypographicalErrorDetection.setLocation(224, 31);
		lblTypographicalErrorDetection.setForeground(Color.WHITE);
		lblTypographicalErrorDetection.setFont(new Font("Californian FB", Font.BOLD, 28));
		
		lblDeteksiKesalahanPenulisan = new JLabel("Deteksi Kesalahan Penulisan pada Dokumen yang di masukkan");
		lblDeteksiKesalahanPenulisan.setFont(new Font("Californian FB", Font.BOLD, 14));
		lblDeteksiKesalahanPenulisan.setForeground(Color.WHITE);
		
		lblDaftarKesalahanPenulisan = new JLabel("Daftar Kesalahan Penulisan");
		lblDaftarKesalahanPenulisan.setFont(new Font("Californian FB", Font.BOLD, 14));
		lblDaftarKesalahanPenulisan.setForeground(Color.WHITE);
		
		label_1 = new JLabel("Copyright \u00A9 2018 Muhamad Muhyidin Amin");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Californian FB", Font.PLAIN, 12));
		
		scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDaftarKesalahanPenulisan, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCekKoreksi)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
							.addComponent(lblDeteksiKesalahanPenulisan)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addContainerGap(20, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(279, Short.MAX_VALUE)
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
					.addGap(260))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(221)
					.addComponent(lblTypographicalErrorDetection)
					.addContainerGap(188, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTypographicalErrorDetection, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDaftarKesalahanPenulisan, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
						.addComponent(lblDeteksiKesalahanPenulisan, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCekKoreksi)
					.addGap(9)
					.addComponent(label_1)
					.addContainerGap())
		);
		typoList = new JList(listModel.toArray(target));
		System.out.println("Is ArrayList Empty: "+listModel.isEmpty());
		System.out.println(listModel.size());
		scrollPane.setViewportView(typoList);
		typoList.setFont(new Font("Californian FB", Font.PLAIN, 14));
		contentPane.setLayout(gl_contentPane);
	}
	    
	private String stemming(String word) {
		// TODO Auto-generated method stub
			StemmingNaziefNew sta;
			try {
				sta = new StemmingNaziefNew();
				rootWord = sta.Stemming(word);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return rootWord;
	}
	
	public String extension(String fullPath, char ext ) {
	    int dot = fullPath.lastIndexOf(ext);
	    return fullPath.substring(dot + 1);
	  }
	
	public String readTxt(){
		String sCurrentLine = null;
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			while ((sCurrentLine = br.readLine()) != null) {
				return sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sCurrentLine;
	}
 }
    		
