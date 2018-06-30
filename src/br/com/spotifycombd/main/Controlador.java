package br.com.spotifycombd.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import br.com.spotifycombd.bean.MusicaBean;
import br.com.spotifycombd.bean.PlaylistBean;
import br.com.spotifycombd.bean.UsuarioBean;
import br.com.spotifycombd.dao.MusicaDao;
import br.com.spotifycombd.dao.PlaylistDao;
import br.com.spotifycombd.dao.UsuarioDao;
import br.com.spotifycombd.view.HPanel;
import br.com.spotifycombd.view.LblMouseListener;
import br.com.spotifycombd.view.Tela;
import tableModel.CustomTableModel;

public class Controlador {

	
	public boolean validarUsuario(HPanel pnl, Tela frm) {
		
		String login = ((JTextField) pnl.get("edtLogin")).getText();
		String senha = String.valueOf(((JPasswordField) pnl.get("edtSenha")).getPassword());		
		
		UsuarioBean user = new UsuarioDao().getUser(login);
		
		if (user.get("senhaUsuario").equals(senha)) {
			
			frm.setUser(user);
		}
		
		return user.get("senhaUsuario").equals(senha);
	}
	
	public boolean validarCadastro(HPanel pnl) {
		
		String login = ((JTextField) pnl.get("edtLogin")).getText();
		String senha = String.valueOf(((JPasswordField) pnl.get("edtSenha")).getPassword());	
		
		if ((login.isEmpty()) || (senha.isEmpty())) {
			
			JOptionPane.showMessageDialog(null, "Termine de preencher os campos");
			
			return false;
		}
		
		UsuarioBean user = new UsuarioDao().getUser(login);
		
		if (user != null) {
			
			JOptionPane.showMessageDialog(null, "Usuário já existente");
			return false;
		}
		
		return true;
	}
	
	public boolean cadastrar(HPanel pnl) {
		
		String login = ((JTextField) pnl.get("edtLogin")).getText();
		String senha = String.valueOf(((JPasswordField) pnl.get("edtSenha")).getPassword());
		String senhaConfirmar = String.valueOf(((JPasswordField) pnl.get("edtConfirmarSenha")).getPassword());
		boolean artista = ((JCheckBox) pnl.get("cbkArtista")).isSelected();
		
		if (!senha.equals(senhaConfirmar)) {
			
			JOptionPane.showMessageDialog(null, "As senhas não coincidem");
			return false;
		}
		
		new UsuarioDao().addUsuario(new UsuarioBean(login, senha, artista));
		
		return true;
	}

	@SuppressWarnings("unchecked")
	public void addPlaylist(HPanel pnl) {
		
		Tela tela = (Tela) pnl.getParent().getParent().getParent();
		
		String nome = null;
		
		try {
			
			nome = JOptionPane.showInputDialog("Informe o nome da nova playlist");
		} catch (Exception e) {
			
			return;
		}
		
		PlaylistBean pb = new PlaylistBean(nome, tela.getUser().get("idUsuario"));
		new PlaylistDao().addPlaylist(pb);
		((JComboBox<PlaylistBean>) pnl.get("cbkPlaylists")).addItem(new PlaylistDao().getLastPlaylist());
	}
	
	public void delPlaylist(HPanel pnl) {
		
		int id = ((PlaylistBean) ((JComboBox) pnl.get("cbkPlaylists")).getSelectedItem()).get("idPlaylist");
		
		new PlaylistDao().excluirPlaylist(id);
	}
	
	public void addMusicaAtPlaylist(HPanel pnl) {
		
		Tela tela = (Tela) pnl.getParent().getParent().getParent();
		int idUsuario = tela.getUser().get("idUsuario");
		
		int idPlaylist = ((PlaylistBean)((JComboBox) pnl.get("cbkPlaylists")).getSelectedItem()).get("idPlaylist");
		
		frameAddMusicaAtPlaylist(idPlaylist, idUsuario, pnl.get("cbkPlaylists"), pnl.get("scrollMusicas"));
	}
	
	private void frameAddMusicaAtPlaylist(int idPlaylist, int idUsuario, JComboBox cbkPlaylists, JScrollPane scrollMusicas) {
		
		JFrame frame = new JFrame("Músicas");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setSize(316, 439);
		
		JScrollPane scroll = new MusicaDao().getResumedModel().getScroll();
		scroll.setBounds(25, 25, 250, 350);
		
		scroll.getViewport().getView().addMouseListener(new LblMouseListener(scrollMusicas) {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() >= 2) {
					
					CustomTableModel<MusicaBean> model = new MusicaDao().getResumedModel();
					MusicaBean mb = (MusicaBean) model.getObject(((JTable)scroll.getViewport().getView()).getSelectedRow() + 1);
					int idMusica = mb.get("idMusica");
					
					new PlaylistDao().addMusica(idMusica, idPlaylist, idUsuario);
					
					this.scrollMusicas = new PlaylistDao().getMusicModel(((PlaylistBean) cbkPlaylists.getSelectedItem()).get("idPlaylist")).getScroll();
				}
			}
		});
		
		frame.add(scroll);
		
		frame.setVisible(true);
	}
	
	public void delMusicaFromPlaylist(HPanel pnl) {
		
		
	}
}