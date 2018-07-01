package br.com.spotifycombd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
			
			System.out.println("Erro ao cadastrar usuário.");
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
			
			System.out.println("Erro ao excluir usuário.");
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

	public UsuarioBean getUser(String login) {
		
		UsuarioBean user = null;
		
		String sql = "select * from usuario where loginUsuario = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, login);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				user = new UsuarioBean();
				
				user.set("idUsuario", rs.getInt("idUsuario"));
				user.set("loginUsuario", rs.getString("loginUsuario"));
				user.set("senhaUsuario", rs.getString("senhaUsuario"));
				user.set("artista", rs.getBoolean("artista"));
			}
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter usuário.");
			e.printStackTrace();
		}
		
		return user;
	}
	
	public UsuarioBean getUser(int id) {
		
		UsuarioBean user = null;
		
		String sql = "select loginUsuario, senhausuario, artista from usuario where idUsuario = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				user = new UsuarioBean();
				
				user.set("idUsuario", id);
				user.set("loginUsuario", rs.getString("loginUsuario"));
				user.set("senhaUsuario", rs.getString("senhaUsuario"));
				user.set("artista", rs.getBoolean("artista"));
			}
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter usu�rio.");
			e.printStackTrace();
		}
		
		return user;
	}
}