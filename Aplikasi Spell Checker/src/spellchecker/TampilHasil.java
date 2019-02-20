package spellchecker;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TampilHasil extends JFrame {

	private JPanel contentPane;
	public String[] target;
	public static ArrayList<String> typo = new ArrayList<String>(); 
	public ArrayList<String> suggest = new ArrayList<String>(); 
	public static String line; 
	public static String fileName = "kbbi.txt";
	//static MyHighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);
	static DefaultListModel<String> dataTypo = new DefaultListModel<>();
	JList<String> listTypo = new JList();
	//JList<String> listSuggest = new JList();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TampilHasil frame = new TampilHasil(typo);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public TampilHasil(ArrayList<String> listModel){
		
		this.typo = listModel;
		//this.dataTypo = listModel;
		
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
		
		JPanel panel = new JPanel();
		
		ArrayList<String> dict = new ArrayList<String>();
		String typoList;
		DefaultListModel<String> coba = new DefaultListModel<String>();
		for(int i = 0; i<listModel.size();i++){
			typoList = listModel.get(i);
			coba.addElement(typoList);
			
		}
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		
		panel.setLayout(gl_panel);
		
		try {
			Scanner s = new Scanner(new File(fileName));
			
			while(s.hasNextLine()){
				dict.add(s.nextLine());
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel lblTypographicalErrorDetection = new JLabel("Typographical Error Detection");
		lblTypographicalErrorDetection.setLocation(227, 22);
		lblTypographicalErrorDetection.setForeground(Color.WHITE);
		lblTypographicalErrorDetection.setFont(new Font("Californian FB", Font.BOLD, 26));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setLocation(377, 104);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setLocation(35, 97);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		JTextArea listSuggest = new JTextArea();
		listSuggest.setEditable(false);
		scrollPane.setViewportView(listSuggest);
		listSuggest.setFont(new Font("Californian FB", Font.PLAIN, 14));
		JTextArea txtHasil = new JTextArea();
		txtHasil.setEditable(false);
		scrollPane_2.setViewportView(txtHasil);
		txtHasil.setFont(new Font("Californian FB", Font.PLAIN, 14));
		
		
		listTypo = new JList<>(coba);
		scrollPane_1.setViewportView(listTypo);
		listTypo.setFont(new Font("Californian FB", Font.PLAIN, 14));
		listTypo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		listTypo.addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e)
		    {
		        if(!e.getValueIsAdjusting()) {
		        	listSuggest.setText("");
		        	txtHasil.setText("");
		        	int distTolerance = 0;
		        	suggest.clear();
		            final String selectedValuesList = listTypo.getSelectedValue();
		            DamerauLevenshtein  dl = new DamerauLevenshtein();
		            DefaultListModel<String> saran = new DefaultListModel<String>();
    				
    				target = new String[dict.size()];
    				target = dict.toArray(target);
    				
    				while(suggest.isEmpty()&&distTolerance<=10){
    					for(int j=0;j< target.length;j++){
            				int dist = dl.calculateDistance(selectedValuesList.toString(), target[j]);
            				if(dist<=distTolerance){
                                suggest.add(target[j]);
                                saran.addElement(target[j]);
                                listSuggest.append(target[j]+"\n");
            				}
            			}
    					distTolerance = distTolerance + 1;
    				}
        			txtHasil.append("Distance terkecil : "+ (distTolerance-1));
        			txtHasil.append("\nJumlah Kandidat Kata : "+suggest.size());
        			txtHasil.append("\nNilai Presisi : "+(1.0/(double)suggest.size()));
        			
        		
		        }
		    }
		});
		
		JLabel lblNewLabel_1 = new JLabel("Daftar Rekomendasi Kata");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Californian FB", Font.BOLD, 14));
		
		JLabel lblNewLabel_2 = new JLabel("Nilai Akurasi");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Californian FB", Font.BOLD, 14));
		
		JLabel label_1 = new JLabel("Copyright \u00A9 2018 Muhamad Muhyidin Amin");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Californian FB", Font.PLAIN, 12));
		label_1.setBounds(280, 535, 214, 15);
		contentPane.add(label_1);
		
		JButton btnKembaliKeHome = new JButton("Kembali ke Home");
		btnKembaliKeHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Input home = new Input();
				home.setVisible(true);
				setVisible(false);
			}
		});
		btnKembaliKeHome.setFont(new Font("Californian FB", Font.PLAIN, 13));
		
		JLabel label_2 = new JLabel("Daftar Kesalahan Penulisan");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("Californian FB", Font.BOLD, 14));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(222)
							.addComponent(lblTypographicalErrorDetection, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE))
							.addGap(103)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
									.addComponent(btnKembaliKeHome)
									.addPreferredGap(ComponentPlacement.RELATED)))))
					.addContainerGap(18, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnKembaliKeHome, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(lblTypographicalErrorDetection)
							.addGap(29)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel_2)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)))))
					.addGap(154))
		);
		contentPane.setLayout(gl_contentPane);
	}
}

/*class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

    public MyHighlightPainter(Color color) {
        super(color);
    }
}*/
