package br.com.spotifycombd.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Time;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import br.com.spotifycombd.bean.AlbumBean;
import br.com.spotifycombd.bean.MusicaBean;
import br.com.spotifycombd.bean.PlaylistBean;
import br.com.spotifycombd.bean.UsuarioBean;
import br.com.spotifycombd.dao.AlbumDao;
import br.com.spotifycombd.dao.MusicaDao;
import br.com.spotifycombd.dao.PlaylistDao;
import br.com.spotifycombd.dao.UsuarioDao;
import br.com.spotifycombd.view.Tela;
import br.com.spotifycombd.view.componentes.HPanel;
import br.com.spotifycombd.view.componentes.LblMouseListener;
import tableModel.CustomTableModel;

public class Controlador {

	
	private static UsuarioBean user;
	
	
	public UsuarioBean getUser() {
		
		return user;
	}
	
	
	public boolean validarUsuario(HPanel pnl) {
		
		//Obtém as informaçoes passadas pelo usuário na tela de login
		String login = ((JTextField) pnl.get("edtLogin")).getText();
		String senha = String.valueOf(((JPasswordField) pnl.get("edtSenha")).getPassword());		
		
		//Obtém o usuário do banco de dados pelo login
		UsuarioBean user = new UsuarioDao().getUser(login);
		
		if (user.get("senhaUsuario").equals(senha)) {
			
			Controlador.user = user;
		}
		
		//Retorna se o usuário pode entrar ou nao
		return user.get("senhaUsuario").equals(senha);
	}
	
	public boolean validarCadastro(HPanel pnl) {
		
		//Obtém os dados informados pelo usuário na tela de cadastro
		String login = ((JTextField) pnl.get("edtLogin")).getText();
		String senha = String.valueOf(((JPasswordField) pnl.get("edtSenha")).getPassword());	
		
		//Verifica se as informaçoes sao válidas
		if ((login.isEmpty()) || (senha.isEmpty())) {
			
			JOptionPane.showMessageDialog(null, "Termine de preencher os campos");
			
			return false;
		}
		
		//Obtém o usuário do banco de dados pelo login
		UsuarioBean user = new UsuarioDao().getUser(login);
		
		//Verifica se o usuário já existe no sistema ou nao
		if (user != null) {
			
			JOptionPane.showMessageDialog(null, "Usuário já existente");
			return false;
		}
		
		//Retorna que o usuário nao existe no banco
		return true;
	}
	
	public boolean cadastrar(HPanel pnl) {
		
		//Obtém os dados informados pelo usuário na tela de cadastro
		String login = ((JTextField) pnl.get("edtLogin")).getText();
		String senha = String.valueOf(((JPasswordField) pnl.get("edtSenha")).getPassword());
		String senhaConfirmar = String.valueOf(((JPasswordField) pnl.get("edtConfirmarSenha")).getPassword());
		boolean artista = ((JCheckBox) pnl.get("cbkArtista")).isSelected();
		
		//Verifica se a senha e a confirmaçao dela sao iguais
		if (!senha.equals(senhaConfirmar)) {
			
			JOptionPane.showMessageDialog(null, "As senhas n�o coincidem");
			return false;
		}
		
		//Cadastra o usuário
		new UsuarioDao().addUsuario(new UsuarioBean(login, senha, artista));
		
		//Retorna que o usuário foi cadastrado
		return true;
	}

	@SuppressWarnings("unchecked")
	public void addPlaylist(HPanel pnl) {
		
		String nome = null;
		
		//Obtém o nome da playlist que o usuário deseja criar
		while (nome == null) {
			
			nome = JOptionPane.showInputDialog("Informe o nome da nova playlist");
			
			if (nome == null) {
				
				return;
				
			} else if (nome.isEmpty()) {
				
				JOptionPane.showMessageDialog(null, "Nome inválido");
				nome = null;
			}
		}
		
		//Cadastra a playlist
		PlaylistBean pb = new PlaylistBean(nome, user.get("idUsuario"));
		new PlaylistDao().addPlaylist(pb);
		
		
		//Adiciona a playlist ao comboBox
		((JComboBox<PlaylistBean>) pnl.get("cbkPlaylists")).addItem(new PlaylistDao().getLastPlaylist());
	}
	
	@SuppressWarnings("unchecked")
	public void delPlaylist(HPanel pnl) {
		
		//Obtém a playlist selecionada
		JComboBox<PlaylistBean> cbkPlaylists = (JComboBox<PlaylistBean>) pnl.get("cbkPlaylists");
		
		PlaylistBean pb = (PlaylistBean) cbkPlaylists.getSelectedItem();
		
		//Verifica se a playlist selecionada é nula
		if (cbkPlaylists.getSelectedItem() == null) {
			
			JOptionPane.showMessageDialog(null, "Escolha uma playlist antes");
			return;
		}
		
		int id = pb.get("idPlaylist");
		
		//Remove a playlist
		new PlaylistDao().excluirPlaylist(id);
		
		//Remove a playlist do comboBox
		cbkPlaylists.removeItem(pb);
	}
	
	public void addMusicaAtPlaylist(HPanel pnl) {
		
		int idUsuario = user.get("idUsuario");
		
		@SuppressWarnings("unchecked")
		JComboBox<PlaylistBean> cbkPlaylists = ((JComboBox<PlaylistBean>) pnl.get("cbkPlaylists"));
				
		int idPlaylist = 0;
		
		//Verifica se existe uma playlist selecionada
		if (cbkPlaylists.getItemCount() > 0) {
			
			idPlaylist = ((PlaylistBean) cbkPlaylists.getSelectedItem()).get("idPlaylist");
		} else {
			
			JOptionPane.showMessageDialog(null, "Escolha uma playlist antes");
			return;
		}
		
		frameAddMusicaAtPlaylist(idPlaylist, idUsuario, pnl.get("cbkPlaylists"), pnl.get("scrollMusicas"));
	}
	
	private void frameAddMusicaAtPlaylist(int idPlaylist, int idUsuario, JComboBox<PlaylistBean> cbkPlaylists, JScrollPane scrollMusicas) {
		
		//Cria uma janela e configura
		JFrame frame = new JFrame("Músicas");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(316, 439);
		
		//Obtém as músicas resumidas
		JScrollPane scroll = new MusicaDao().getResumedModel().getScroll();
		scroll.setBounds(25, 25, 250, 350);
		
		//Adiciona um listener na tela para que quando ela for clickada adicionar as músicas na playlist
		scroll.getViewport().getView().addMouseListener(new LblMouseListener(scrollMusicas) {
			
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent e) {
				
				JTable tabela = (JTable)scroll.getViewport().getView();
				
				if (e.getClickCount() >= 2) {
					
					//Obtém o id da música selecionada
					CustomTableModel<MusicaBean> model = (CustomTableModel<MusicaBean>) tabela.getModel();
					MusicaBean mb = (MusicaBean) model.getObject(tabela.getSelectedRow());
					int idMusica = mb.get("idMusica");
					
					//Adiciona a música na playlist
					new PlaylistDao().addMusica(idMusica, idPlaylist, idUsuario);
					
					PlaylistBean pb = (PlaylistBean) cbkPlaylists.getSelectedItem();
					
					//Atualiza a tabela de músicas da playlist
					new PlaylistDao().getMusicModel(pb.get("idPlaylist"));
				}
			}
		});
		
		frame.add(scroll);
		
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	@SuppressWarnings("unchecked")
	public void delMusicaFromPlaylist(HPanel pnl) {
		
		JComboBox<PlaylistBean> cbkPlaylists = (JComboBox<PlaylistBean>) pnl.get("cbkPlaylists");
		
		//Verifica se existe alguma música sendo mostrada
		if (pnl.get("scrollMusicas") == null) {
			
			JOptionPane.showMessageDialog(null, "Escolha uma playlist antes");
			return;
		}
		
		//Obtém a playlist selecionada
		JTable tabela = (JTable) ((JScrollPane) pnl.get("scrollMusicas")).getViewport().getView();
		
		PlaylistBean pb = (PlaylistBean) cbkPlaylists.getSelectedItem();
		
		if (pb == null) {
			
			JOptionPane.showMessageDialog(null, "Escolha uma playlist antes");
			return;
		}
		
		//Obtém a música selecionada
		CustomTableModel<MusicaBean> modelo = pb.get("musicas");
		
		MusicaBean mb = modelo.getObject(tabela.getSelectedRow());
		
		//Verifica se a música selecionada existe
		if (mb == null) {
			
			JOptionPane.showMessageDialog(null, "Escolha uma música antes");
			return;
		}
		
		//Deleta a música da playlist e atualiza a tabela de músicas da playlist
		new PlaylistDao().delMusica(mb.get("idMusica"), pb.get("idPlaylist"));
		new PlaylistDao().getMusicModel(pb.get("idPlaylist"));
	}

	public void addAlbum(HPanel pnl) {
		
		String nome = null;
		
		//Obtém o nome do novo álbum
		while (nome == null) {
			
			nome = JOptionPane.showInputDialog("Informe o nome do novo álbum");
			
			if (nome == null) {
				
				return;
				
			} else if (nome.isEmpty()) {
				
				JOptionPane.showMessageDialog(null, "Nome inválido");
				nome = null;
			}
		}
		
		//Cadastra o album
		AlbumBean ab = new AlbumBean(nome, user.get("idUsuario"));
		
		AlbumDao ad = new AlbumDao();
		ad.addAlbum(ab);
		
		//Atualiza a comboBox de albuns
		ad.getCombo();
	}
	
	public void delAlbum (HPanel pnl) {
		
		JComboBox<AlbumBean> cbkAlbuns = pnl.get("cbkAlbuns");
		
		//Obtém o album selecionado
		AlbumBean ab = (AlbumBean) cbkAlbuns.getSelectedItem();
		
		//Verifica se o álbum selecionado existe
		if (ab == null) {
			
			JOptionPane.showMessageDialog(null, "Escolha um álbum antes.");
			return;
		}
		
		//Exclui o album do banco
		new AlbumDao().excluirAlbum(ab.get("idAlbum"));
		
		//Atualiza a comboBox
		cbkAlbuns.removeItem(ab);
	}

	public void addMusica(HPanel pnl) {
		
		int idUsuario = user.get("idUsuario");
		
		frameAddMusica(idUsuario);
	}
	
	private void frameAddMusica(int idUsuario) {
		
		//Cria e configura uma janela
		JFrame frm = new JFrame("Nova Música");
		frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frm.setLayout(null);
		frm.setSize(216, 414);
		
		//Criando componentes
		JLabel lblNome = new JLabel("Título");
		lblNome.setBounds(25, 25, 75, 15);
		
		JTextField edtTitulo = new JTextField();
		edtTitulo.setBounds(25, 50, 150, 25);
		
		JLabel lblGenero = new JLabel("Gênero");
		lblGenero.setBounds(25, 100, 75, 15);
		
		JTextField edtGenero = new JTextField();
		edtGenero.setBounds(25, 125, 150, 25);
		
		JLabel lblDuracao = new JLabel("Duraçao");
		lblDuracao.setBounds(25, 175, 75, 15);
		
		JTextField edtDuracao = new JTextField();
		edtDuracao.setBounds(25, 200, 150, 25);
		
		JLabel lblAlbum = new JLabel("Álbum");
		lblAlbum.setBounds(25, 250, 75, 15);
		
		JComboBox<AlbumBean> cbkAlbum = new AlbumDao().getCombo();
		cbkAlbum.setBounds(25, 275, 150, 25);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(50, 325, 100, 25);
		
		//Adicionando listener para obter informaçoes da nova música
		btnAdicionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//Obtendo informaçoes da música
				String titulo = edtTitulo.getText();
				String genero = edtGenero.getText();
				String duracao = edtDuracao.getText();
				int idAlbum = 0;
				
				//Verificando se as informaçoes sao válidas
				if (cbkAlbum.getSelectedItem() != null) {
					
					idAlbum = ((AlbumBean) cbkAlbum.getSelectedItem()).get("idAlbum");
				} else {
					
					JOptionPane.showMessageDialog(null, "Álbum inválido");
					return;
				}
				
				if (titulo.isEmpty()) {
					
					JOptionPane.showMessageDialog(null, "Título inválido");
					return;
				}
				
				if (!genero.matches("^\\w+$")) {
					
					JOptionPane.showMessageDialog(null, "Genero inválido");
					return;
				}
				
				if (!duracao.matches("^\\d{1,2}:\\d{2}$")) {
					
					JOptionPane.showMessageDialog(null, "Duraçao inválida");
					return;
				} else {
					
					if ((Integer.parseInt(duracao.split(":")[0]) > 60) || (Integer.parseInt(duracao.split(":")[0]) > 60)) {
						
						JOptionPane.showMessageDialog(null, "Duraçao inválida");
						return;
					}
				}
				
				//Convertendo o tempo informado pelo usuário
				int tempo = Integer.parseInt(duracao.split(":")[0]) * 60000 + Integer.parseInt(duracao.split(":")[1]) * 1000;
				Time duracaoTime = new Time(tempo);
				
				//Cadastrando a música
				MusicaDao md = new MusicaDao();
				
				md.addMusica(new MusicaBean(0, titulo, genero, duracaoTime, idAlbum, idUsuario));
				
				//Atualizando a tabela de músicas
				md.getModel(idUsuario);
				
				frm.dispose();
			}
		});
		
		frm.add(lblNome);
		frm.add(edtTitulo);
		frm.add(lblGenero);
		frm.add(edtGenero);
		frm.add(lblDuracao);
		frm.add(edtDuracao);
		frm.add(lblAlbum);
		frm.add(cbkAlbum);
		frm.add(btnAdicionar);
		
		frm.setVisible(true);
		frm.setLocationRelativeTo(null);
	}
	
	public void delMusica(HPanel pnl) {
		
		int idArtista = user.get("idUsuario");
		
		JTable tabela = (JTable) ((JScrollPane) pnl.get("scrollMusicas")).getViewport().getView();		
		
		@SuppressWarnings("unchecked")
		CustomTableModel<MusicaBean> modelo = (CustomTableModel<MusicaBean>) tabela.getModel();
		
		MusicaBean mb = modelo.getObject(tabela.getSelectedRow());
		
		new MusicaDao().excluirMusica(mb.get("idMusica"));
		new MusicaDao().getModel(idArtista);
	}
}