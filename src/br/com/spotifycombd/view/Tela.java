package br.com.spotifycombd.view;

import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import br.com.spotifycombd.bean.MusicaBean;
import br.com.spotifycombd.bean.PlaylistBean;
import br.com.spotifycombd.bean.UsuarioBean;
import br.com.spotifycombd.dao.MusicaDao;
import br.com.spotifycombd.dao.PlaylistDao;
import br.com.spotifycombd.main.Controlador;

public class Tela extends JFrame {
	
	
	private Controlador control = new Controlador();
	
	
	private Font fonteMedia = new Font("Georgean", Font.PLAIN, 15);
	private Font fontePequena = new Font("Georgean", Font.BOLD, 10);
	
	private UsuarioBean user = null;
	
	public Tela() {
		
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		login();
		
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	
	public void setUser(UsuarioBean user) {
		
		this.user = user;
	}
	
	
	private void login() {
		
		HPanel pnl = new HPanel();
		
		setTitle("Login");
		setSize(265, 200);
		setContentPane(pnl);
		
		int coluna1 = 25;
		int linha1 = 25;
		
		JLabel lblLogin = getLabel("Login", fonteMedia);
		lblLogin.setLocation(coluna1, linha1);
		
		int coluna2 = coluna1 + lblLogin.getWidth() + 15;
		int linha2 = linha1 + lblLogin.getHeight() + 15;
		
		JLabel lblSenha = getLabel("Senha", fonteMedia);
		lblSenha.setLocation(coluna1, linha2);
		
		JTextField edtLogin = getEdit(fonteMedia);
		edtLogin.setBounds(coluna2, linha1, 150, edtLogin.getHeight());
		
		JPasswordField edtSenha = new JPasswordField();
		edtSenha.setBounds(coluna2, linha2, 150, edtLogin.getHeight());
		
		int linha3 = linha2 + edtLogin.getHeight() + 15;
		
		Tela tela = this;
		
		JButton btnLogar = new JButton("Entrar");
		btnLogar.setBounds(80, linha3, 100, 25);
		btnLogar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (control.validarUsuario(pnl, tela)) {
					
					menu();
				}
			}
		});
		
		JLabel lblCadastrar = getLabel("Cadastrar", fontePequena);
		lblCadastrar.setLocation(132 - lblCadastrar.getWidth() / 2, 125);
		lblCadastrar.addMouseListener(new LblMouseListener(LblMouseListener.CLICK, new Runnable() {

			@Override
			public void run() {
				
				cadastrar();
			}
			
		}));		
		
		pnl.add(lblLogin);
		pnl.add(lblSenha);
		pnl.add(edtLogin, "edtLogin");
		pnl.add(edtSenha, "edtSenha");
		pnl.add(btnLogar);
		pnl.add(lblCadastrar);
		
		getRootPane().setDefaultButton(btnLogar);
	}
	
	private void cadastrar() {
		
		HPanel pnl = new HPanel();
		
		setTitle("Cadastro");
		setContentPane(pnl);
		setSize(300, 265);
		setLocationRelativeTo(null);
		
		int coluna1 = 25;
		int linha1 = 25;
		
		JLabel lblLogin = getLabel("Login", fonteMedia);
		lblLogin.setLocation(coluna1, linha1);
		
		int linha2 = linha1 + lblLogin.getHeight() + 15;
		
		JLabel lblSenha = getLabel("Senha", fonteMedia);
		lblSenha.setLocation(coluna1, linha2);
		
		int linha3 = linha2 + lblSenha.getHeight() + 15;
		
		JLabel lblConfirmarSenha = getLabel("Confirmar", fonteMedia);
		lblConfirmarSenha.setLocation(coluna1, linha3);
	
		int coluna2 = coluna1 + lblConfirmarSenha.getWidth() + 15;
		
		JTextField edtLogin = getEdit(fonteMedia);
		edtLogin.setBounds(coluna2, linha1, 150, edtLogin.getHeight());
		
		JPasswordField edtSenha = new JPasswordField();
		edtSenha.setBounds(coluna2, linha2, 150, edtLogin.getHeight());
		
		JPasswordField edtConfirmarSenha = new JPasswordField();
		edtConfirmarSenha.setBounds(coluna2, linha3, 150, edtLogin.getHeight());
		
		JCheckBox cbkArtista = new JCheckBox("Artista");
		cbkArtista.setFont(fonteMedia);
		cbkArtista.setBounds(coluna1, linha3 + edtConfirmarSenha.getHeight() + 15, 100, 25);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(150, cbkArtista.getY() + cbkArtista.getHeight() + 15, 100, 25);
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (control.validarCadastro(pnl)) {
					
					if (control.cadastrar(pnl)) {
						
						login();
					}
				}
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(25, btnCadastrar.getY(), 100, 25);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				login();
			}
		});
		
		pnl.add(lblLogin);
		pnl.add(lblSenha);
		pnl.add(lblConfirmarSenha);
		pnl.add(edtLogin, "edtLogin");
		pnl.add(edtSenha, "edtSenha");
		pnl.add(edtConfirmarSenha, "edtConfirmarSenha");
		pnl.add(cbkArtista, "cbkArtista");
		pnl.add(btnCadastrar);
		pnl.add(btnCancelar);
	}

	private void menu() {
		
		Panel pnl = new Panel();
		pnl.setLayout(null);
		
		setTitle("Menu");
		setContentPane(pnl);
		setSize(266, 289);
		setLocationRelativeTo(null);
		
		JButton btnMusicas = new JButton("Músicas");
		btnMusicas.setBounds(25, 25, 200, 50);
		btnMusicas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				musicas();
			}
		});
		
		JButton btnPlaylists = new JButton("Playlists");
		btnPlaylists.setBounds(25, 100, 200, 50);
		btnPlaylists.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				playlists();
			}
		});
		
		JButton btnAlbuns = new JButton("Álbuns");
		btnAlbuns.setBounds(25, 175, 200, 50);
		btnAlbuns.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				albuns();
			}
		});
		
		if ((boolean) user.get("artista")) {
			
			JButton btnArtista = new JButton("Artista");
			btnArtista.setBounds(25, 250, 200, 50);
			btnArtista.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					artista();
				}
			});
			
			pnl.add(btnArtista);
			
			setSize(266, 389);
			setLocationRelativeTo(null);
		}
		
		pnl.add(btnMusicas);
		pnl.add(btnPlaylists);
		pnl.add(btnAlbuns);
	}

	private void musicas() {
		
		HPanel pnl = getItensPanel();
		
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setBounds(25, 75, 50, 25);
		lblFiltro.setToolTipText("Filtre por: nome, genero ou artista");
		
		JTextField edtFiltro = new JTextField();
		edtFiltro.setBounds(75, 75, 450, 25);
		edtFiltro.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
				control.filtrarMusicas(pnl);
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
		JScrollPane scrollMusicas = new MusicaDao().getModel().getScroll();
		scrollMusicas.setBounds(25, 125, 500, 250);
		
		pnl.add(scrollMusicas);
		pnl.add(lblFiltro);
		pnl.add(edtFiltro, "edtFiltro");
		
		setSize(566, 389);
	}
	
	private void playlists() {
		
		HPanel pnl = getItensPanel();
		
		JComboBox<PlaylistBean> cbkPlaylists = new JComboBox<>();
		cbkPlaylists.setBounds(25, 75, 500, 25);
		
		for (PlaylistBean playlist : new PlaylistDao().getModel().getObjects()) {
			
			cbkPlaylists.addItem(playlist);
		}
		cbkPlaylists.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
				new MusicaDao().getModel(((PlaylistBean) cbkPlaylists.getSelectedItem()).get("idPlaylist"));
			}
		});
		
		JScrollPane scrollMusicas = new MusicaDao().getModel(((PlaylistBean) cbkPlaylists.getSelectedItem()).get("idPlaylist")).getScroll();
		
		pnl.add(cbkPlaylists);
		pnl.add(scrollMusicas);
		
		setSize(566, 439);
	}
	
	private void albuns() {
		
	}
	
	private void artista() {
		
	}
	
	
	private HPanel getItensPanel() {
		
		HPanel pnl = new HPanel();
		
		setContentPane(pnl);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(25, 25, 100, 25);
		btnVoltar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				menu();
			}
		});
		
		pnl.add(btnVoltar);
		
		return pnl;
	}
	
	private JLabel getLabel(String texto, Font fonte) {
		
		JLabel lbl = new JLabel(texto);
		lbl.setFont(fonte);
		lbl.setSize(lbl.getPreferredSize());
		
		return lbl;
	}
	
	private JTextField getEdit(Font fonte) {
		
		JTextField edt = new JTextField();
		edt.setFont(fonte);
		edt.setSize(edt.getPreferredSize());
		
		return edt;
	}
}