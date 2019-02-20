package spellchecker;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Welcome extends JFrame {
	
	private JPanel contentPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome frame = new Welcome();
					frame.setVisible(true);;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the panel.
	 */
	public Welcome() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(96, 150, 251));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		Button btnNewButton = new Button("Mulai");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Input home = new Input();
				home.setVisible(true);
				setVisible(false);
			}
		});
		
		btnNewButton.setBounds(336, 412, 106, 23);
		btnNewButton.setBackground(new Color(0, 102, 255));
		btnNewButton.setForeground(Color.WHITE);
		//btnNewButton.setFocusPainted(false);
		btnNewButton.setFont(new Font("Californian FB", Font.BOLD, 13));
		contentPane.add(btnNewButton);
		
		JLabel lblSelamatDatangDi = new JLabel("Welcome to Typographical Error Detection");
		lblSelamatDatangDi.setForeground(new Color(255, 255, 255));
		lblSelamatDatangDi.setFont(new Font("Californian FB", Font.BOLD, 28));
		lblSelamatDatangDi.setBounds(154, 236, 518, 67);
		contentPane.add(lblSelamatDatangDi);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("D:\\Dokumen Skripsi\\primary_spellcheck_actual.png"));
		lblNewLabel.setBounds(337, 81, 138, 131);
		contentPane.add(lblNewLabel);
		
		JLabel lblAplikasiPendeteksiKesalahan = new JLabel("Aplikasi pendeteksi kesalahan penulisan dan rekomendasi kata yang benar pada dokumen");
		lblAplikasiPendeteksiKesalahan.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAplikasiPendeteksiKesalahan.setForeground(new Color(255, 255, 255));
		lblAplikasiPendeteksiKesalahan.setBounds(154, 303, 490, 29);
		contentPane.add(lblAplikasiPendeteksiKesalahan);
		
		JLabel lblCopyright = new JLabel("Copyright \u00A9 2018 Muhamad Muhyidin Amin");
		lblCopyright.setForeground(Color.WHITE);
		lblCopyright.setFont(new Font("Californian FB", Font.PLAIN, 12));
		lblCopyright.setBounds(283, 557, 233, 14);
		contentPane.add(lblCopyright);
		setUndecorated(true);

	}
}
