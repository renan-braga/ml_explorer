package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class TelaExtrator {

	private JFrame frmExtratorMercadoLivre;
	private JTextField txtArquivo;
	private JLabel txtFeedback;
	private ExtratorMercadoLivre extrator;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					TelaExtrator window = new TelaExtrator();
					window.frmExtratorMercadoLivre.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaExtrator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExtratorMercadoLivre = new JFrame();
		frmExtratorMercadoLivre.setTitle("Extrator - Mercado Livre");
		frmExtratorMercadoLivre.setBounds(100, 100, 588, 447);
		frmExtratorMercadoLivre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmExtratorMercadoLivre.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Local do arquivo");
		lblNewLabel.setBounds(20, 88, 110, 20);
		frmExtratorMercadoLivre.getContentPane().add(lblNewLabel);
		
		txtArquivo = new JTextField();
		txtArquivo.setBounds(132, 88, 272, 20);
		frmExtratorMercadoLivre.getContentPane().add(txtArquivo);
		txtArquivo.setColumns(10);
		
		txtFeedback = new JLabel();
		txtFeedback.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtFeedback.setBounds(35, 228, 509, 144);
		txtFeedback.setText("");
		frmExtratorMercadoLivre.getContentPane().add(txtFeedback);
		
		JButton btnNewButton = new JButton("Pesquisar");
		btnNewButton.setIcon(new ImageIcon(TelaExtrator.class.getResource("/images/build.png")));
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(chooser.showSaveDialog(null) != JFileChooser.SAVE_DIALOG) {
					File arquivo = chooser.getSelectedFile();
					txtArquivo.setText(arquivo.getAbsolutePath());
					extrator = new ExtratorMercadoLivre(
							arquivo.getAbsolutePath(), 
							arquivo.getParent(),
							arquivo.getName());
				}
				
			}
		});
		btnNewButton.setBounds(434, 83, 110, 31);
		frmExtratorMercadoLivre.getContentPane().add(btnNewButton);
		
		
		JButton btnNewButton_1 = new JButton("Iniciar extração");
		btnNewButton_1.setIcon(new ImageIcon(TelaExtrator.class.getResource("/images/run.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					extrator.extrairDados(txtFeedback);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(174, 139, 210, 40);
		frmExtratorMercadoLivre.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("Mercado Livre - Scrapper");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(84, 21, 401, 31);
		frmExtratorMercadoLivre.getContentPane().add(lblNewLabel_1);
		
	}
}
