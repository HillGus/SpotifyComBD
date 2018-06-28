package br.com.spotifycombd.main;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.spotifycombd.bean.UsuarioBean;
import br.com.spotifycombd.dao.UsuarioDao;
import br.com.spotifycombd.view.HPanel;
import br.com.spotifycombd.view.Tela;

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
}