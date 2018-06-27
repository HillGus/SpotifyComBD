package br.com.spotifycombd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.spotifycombd.bean.UsuarioBean;
import br.com.spotifycombd.connection.ConnectionFactory;

public class UsuarioDao {

	private Connection conexao;
	
	
	public UsuarioDao() {
		
		conexao = new ConnectionFactory().getConnect();
	}
	
	
	public void addUsuario(UsuarioBean obj) {
		
		String sql = "insert into usuario(loginUsuario, senhaUsuario, artista) values (?, ?, ?)";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, obj.get("loginUsuario"));
			ps.setString(2, obj.get("senhaUsuario"));
			ps.setBoolean(3, obj.get("artista"));
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao cadastrar usu�rio.");
		}
	}
	
	public void excluirUsuario(int id) {
		
		String sql = "delete from usuario where idUsuario = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao excluir usu�rio.");
		}
	}
	
	public void alterarUsuario(UsuarioBean obj) {
		
		String sql = "update usuario set loginUsuario = ?, set senhaUsuario = ?, set artista = ? where idUsuario = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, obj.get("loginUsuario"));
			ps.setString(2, obj.get("senhaUsuario"));
			ps.setBoolean(3, obj.get("artista"));
			ps.setInt(4, obj.get("idUsuario"));
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao alterar usu�rio.");
		}
	}
}