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

import br.com.spotifycombd.bean.AlbumBean;
import br.com.spotifycombd.bean.MusicaBean;
import br.com.spotifycombd.bean.PlaylistBean;
import br.com.spotifycombd.bean.UsuarioBean;
import br.com.spotifycombd.dao.AlbumDao;
import br.com.spotifycombd.dao.MusicaDao;
import br.com.spotifycombd.dao.PlaylistDao;
import br.com.spotifycombd.main.Controlador;
import br.com.spotifycombd.view.componentes.HPanel;
import br.com.spotifycombd.view.componentes.LblMouseListener;

public class Tela extends JFrame {
	
	
	private Controlador control = new Controlador();
	
	
	private Font fonteMedia = new Font("Georgean", Font.PLAIN, 15);
	private Font fontePequena = new Font("Georgean", Font.BOLD, 10);
	
	private UsuarioBean user;
	
	
	public Tela() {
		
		//Configura a janela
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		login();
		
		setVisible(true);
		
	}
	
	
	private void login() {
		
		HPanel pnl = new HPanel();
		
		//Confgura a janela
		setTitle("Login");
		setSize(265, 200);
		setLocationRelativeTo(null);
		setContentPane(pnl);
		
		//coordenadas para organizar a tela melhor
		int coluna1 = 25;
		int linha1 = 25;
		
		//Adicionando componentes
		JLabel lblLogin = getLabel("Login", fonteMedia);
		lblLogin.setLocation(coluna1, linha1);
		
		//Coordenadas para organizar melhor a tela
		int coluna2 = coluna1 + lblLogin.getWidth() + 15;
		int linha2 = linha1 + lblLogin.getHeight() + 15;
		
		//Adicionando componentes
		JLabel lblSenha = getLabel("Senha", fonteMedia);
		lblSenha.setLocation(coluna1, linha2);
		
		JTextField edtLogin = getEdit(fonteMedia);
		edtLogin.setBounds(coluna2, linha1, 150, edtLogin.getHeight());
		
		JPasswordField edtSenha = new JPasswordField();
		edtSenha.setBounds(coluna2, linha2, 150, edtLogin.getHeight());
		
		int linha3 = linha2 + edtLogin.getHeight() + 15;
		
		JButton btnLogar = new JButton("Entrar");
		btnLogar.setBounds(80, linha3, 100, 25);
		
		//Adicionando listener para validar o usuário
		btnLogar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (control.validarUsuario(pnl)) {
					
					user = control.getUser();
					menu();
				}
			}
		});
		
		//Adicionando componentes
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
		
		//Configura janela
		setTitle("Cadastro");
		setContentPane(pnl);
		setSize(300, 265);
		setLocationRelativeTo(null);
		
		//Adiciona componentes
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
		
		//Adiciona listener para validar o cadastro e cadastrar o usuário
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
		
		//Configura a janela
		setTitle("Menu");
		setContentPane(pnl);
		setSize(266, 214);
		setLocationRelativeTo(null);
		
		//Adicionando componentes e listener para levar às outras janelas
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
		
		if ((boolean) user.get("artista")) {
			
			JButton btnArtista = new JButton("Artista");
			btnArtista.setBounds(25, 175, 200, 50);
			btnArtista.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					artista();
				}
			});
			
			pnl.add(btnArtista);
			
			setSize(266, 289);
			setLocationRelativeTo(null);
		}
		
		pnl.add(btnMusicas);
		pnl.add(btnPlaylists);
	}

	private void musicas() {
		
		HPanel pnl = getItensPanel();
		
		JScrollPane scrollMusicas = new MusicaDao().getModel().getScroll();
		scrollMusicas.setBounds(25, 75, 500, 250);
	
		pnl.add(scrollMusicas);
		
		setSize(566, 389);
		setLocationRelativeTo(null);
	}
	
	private void playlists() {
		
		HPanel pnl = getItensPanel();
		setSize(566, 489);
		setLocationRelativeTo(null);
		
		//Adicionando componentes
		JComboBox<PlaylistBean> cbkPlaylists = new JComboBox<>();
		cbkPlaylists.setBounds(25, 75, 200, 25);
		
		//Adiciona as playlists ao comboBox
		for (PlaylistBean playlist : new PlaylistDao().getModel().getObjects()) {
			
			if (playlist.get("idUsuario") == control.getUser().get("idUsuario")) {
				
				cbkPlaylists.addItem(playlist);
			}
		}
		
		JScrollPane scrollMusicas = null;
		
		//Adiciona listener para atualizar o scroll de musicas quando mudar a playlist selecionada
		cbkPlaylists.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
				if (cbkPlaylists.getSelectedItem() != null) {
					
					JScrollPane scrollMusicas = pnl.get("scrollMusicas");
					
					//Reinicia o scroll
					scrollMusicas = new PlaylistDao().getMusicModel(((PlaylistBean) cbkPlaylists.getSelectedItem()).get("idPlaylist")).getScroll();
					scrollMusicas.setBounds(25, 125, 500, 250);
					
					pnl.add(scrollMusicas, "scrollMusicas");
					
				} else {
					
					//Esconde o scroll
					((JScrollPane) pnl.get("scrollMusicas")).setBounds(0, 0, 0, 0);
				}
			}
		});
		
		//Tenta criar o scroll caso tenha alguma playlist selecionada
		try {
			
			scrollMusicas = new PlaylistDao().getMusicModel(((PlaylistBean) cbkPlaylists.getSelectedItem()).get("idPlaylist")).getScroll();
			scrollMusicas.setBounds(25, 125, 500, 250);
			pnl.add(scrollMusicas, "scrollMusicas");
		} catch (Exception e) {}
		
		//Adicionando componentes
		JButton btnNovaPlaylist = new JButton("Nova Playlist");
		btnNovaPlaylist.setBounds(250, 75, 125, 25);
		btnNovaPlaylist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				control.addPlaylist(pnl);				
			}
		});
		
		JButton btnRemoverPlaylist = new JButton("Excluir Playlist");
		btnRemoverPlaylist.setBounds(400, 75, 125, 25);
		btnRemoverPlaylist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				control.delPlaylist(pnl);
			}
		});
		
		JButton btnAdicionarMusica = new JButton("Adicionar Música");
		btnAdicionarMusica.setBounds(25, 400, 150, 25);
		btnAdicionarMusica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				control.addMusicaAtPlaylist(pnl);
			}
		});
		
		JButton btnRemoverMusica = new JButton("Remover Música");
		btnRemoverMusica.setBounds(200, 400, 150 , 25);
		btnRemoverMusica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				control.delMusicaFromPlaylist(pnl);
			}
		});
		
		pnl.add(cbkPlaylists, "cbkPlaylists");
		pnl.add(btnNovaPlaylist);
		pnl.add(btnRemoverPlaylist);
		pnl.add(btnAdicionarMusica);
		pnl.add(btnRemoverMusica);
	}
	
	private void artista() {
		
		HPanel pnl = getItensPanel();
		
		setSize(266, 264);
		setLocationRelativeTo(null);
		
		//Adicionando componentes
		JButton btnAlbuns = new JButton("Álbuns");
		btnAlbuns.setBounds(25, 75, 200, 50);
		btnAlbuns.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				albuns();
			}
		});
		
		JButton btnMusicas = new JButton("Músicas");
		btnMusicas.setBounds(25, 150, 200, 50);
		btnMusicas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				musicasArtista();
			}
		});
		
		pnl.add(btnMusicas);
		pnl.add(btnAlbuns);
	}
	
	private void albuns() {
		
		HPanel pnl = new HPanel();
		
		setContentPane(pnl);
		setSize(566, 439);
		setLocationRelativeTo(null);
	
		//Adicionando componentes
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(25, 25, 100, 25);
		btnVoltar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				artista();
			}
		});
		
		JScrollPane scrollMusicas = null;
		
		JComboBox<AlbumBean> cbkAlbuns = new AlbumDao().getCombo();
		cbkAlbuns.setBounds(25, 75, 200, 25);
		
		//Adicionando listener para quando mudar o album mudar as músicas do scroll
		cbkAlbuns.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
				try {
					
					if (cbkAlbuns.getSelectedItem() != null) {
						
						JScrollPane scrollMusicas = pnl.get("scrollMusicas");
						
						//Reinicia o scroll
						scrollMusicas = new AlbumDao().getMusicModel(((AlbumBean) cbkAlbuns.getSelectedItem()).get("idAlbum")).getScroll();
						scrollMusicas.setBounds(25, 125, 500, 250);
						
						pnl.add(scrollMusicas, scrollMusicas);
					} else {
	
						//Esconde o scroll
						((JScrollPane) pnl.get("scrollMusicas")).setBounds(0, 0, 0, 0);
					}
				} catch(Exception e) {}
			}
		});
		
		//Tenta obter o scroll de músicas caso algum album esteja selecionado
		try {
			
			scrollMusicas = new AlbumDao().getMusicModel(((AlbumBean) cbkAlbuns.getSelectedItem()).get("idAlbum")).getScroll();
			scrollMusicas.setBounds(25, 125, 500, 250);
			
			pnl.add(scrollMusicas, "scrollMusicas");
		} catch (Exception e) {}
		
		//Adicionando componentes
		JButton btnAdicionar = new JButton("Novo Álbum");
		btnAdicionar.setBounds(250, 75, 125, 25);
		btnAdicionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				control.addAlbum(pnl);
			}
		});
		
		JButton btnRemover = new JButton("Excluir Álbum");
		btnRemover.setBounds(400, 75, 125, 25);
		btnRemover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				control.delAlbum(pnl);
			}
		});
		
		pnl.add(btnVoltar);
		pnl.add(cbkAlbuns, "cbkAlbuns");
		pnl.add(btnAdicionar);
		pnl.add(btnRemover);
	}
	
	private void musicasArtista() {
		
		HPanel pnl = new HPanel();
		
		setContentPane(pnl);
		setSize(566, 439);
		setLocationRelativeTo(null);
	
		//Adicionando componentes
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(25, 25, 100, 25);
		btnVoltar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				artista();
			}
		});
		
		JScrollPane scrollMusicas = new MusicaDao().getModel(control.getUser().get("idUsuario")).getScroll();
		scrollMusicas.setBounds(25, 75, 500, 250);
		
		JButton btnNovaMusica = new JButton("Nova Música");
		btnNovaMusica.setBounds(25, 350, 125, 25);
		btnNovaMusica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				control.addMusica(pnl);
			}
		});
		
		JButton btnRemoverMusica = new JButton("Excluir Música");
		btnRemoverMusica.setBounds(175, 350, 125, 25);
		btnRemoverMusica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				control.delMusica(pnl);
			}
		});
		
		pnl.add(btnVoltar);
		pnl.add(scrollMusicas, "scrollMusicas");
		pnl.add(btnRemoverMusica);
		pnl.add(btnNovaMusica);
	}

	
	private HPanel getItensPanel() {
		
		//Cria um painel predefinido
		HPanel pnl = new HPanel();
		
		setContentPane(pnl);
		
		//Adiciona botao para voltar ao menu principal
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
		
		//Configura uma jlabel com fonte
		JLabel lbl = new JLabel(texto);
		lbl.setFont(fonte);
		lbl.setSize(lbl.getPreferredSize());
		
		return lbl;
	}
	
	private JTextField getEdit(Font fonte) {
		
		//Configura um jtextfield com fonte
		JTextField edt = new JTextField();
		edt.setFont(fonte);
		edt.setSize(edt.getPreferredSize());
		
		return edt;
	}
}