package br.com.spotifycombd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.spotifycombd.bean.MusicaBean;
import br.com.spotifycombd.connection.ConnectionFactory;
import tableModel.CustomTableModel;

public class MusicaDao {

	private Connection conexao;
	
	private static CustomTableModel<MusicaBean> modelo = new CustomTableModel<>();
	
	
	public MusicaDao() {
		
		conexao = new ConnectionFactory().getConnect();
	}
	
	
	public void addMusica(MusicaBean obj) {
		
		String sql = "insert into musica (nomeMusica, generoMusica, duracaoMusica, idArtista) values (?, ?, ?, ?)";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, obj.get("nomeMusica"));
			ps.setString(2, obj.get("generoMusica"));
			ps.setTime(3, obj.get("duracaoMusica"));
			ps.setInt(4, obj.get("idArtista"));
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao cadastrar música.");
		}
	}
		
	public void excluirMusica(int id) {
		
		String sql = "delete from musica where idMusica = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao excluir música.");
		}
	}
	
	public void alterarMusica(MusicaBean obj) {
		
		String sql = "update musica set nomeMusica = ?, generoMusica = ?, duracaoMusica = ?, idArtista = ? where idMusica = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, obj.get("nomeMusica"));
			ps.setString(2, obj.get("generoMusica"));
			ps.setTime(3, obj.get("duracaoMusica"));
			ps.setInt(4, obj.get("idArtista"));
			ps.setInt(5, obj.get("idMusica"));
			
			ps.execute();
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao alterar música.");
		}
	}
	
	public CustomTableModel<MusicaBean> getModel() {
		
		ArrayList<MusicaBean> musicas = new ArrayList<MusicaBean>();
		
		String sql = "select * from musica";
		
		try {
			
			Statement st = conexao.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				
				musicas.add(new MusicaBean(rs.getInt("idMusica"), 
							  			   rs.getString("nomeMusica"), 
							  			   rs.getString("generoMusica"), 
							  			   rs.getTime("duracaoMusica"), 
							  			   rs.getInt("idArtista")));
			}
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter informações do banco.");
		}
		
		modelo.setObjects(musicas);
		
		return modelo;
	}
	
	public CustomTableModel<MusicaBean> getModel(ArrayList<Object[]> filtros) {
		
		ArrayList<MusicaBean> musicas = new ArrayList<>();
		
		String sql = "select * from musica where ";
		
		for (Object[] filtro : filtros) {
			
			sql += filtro[0] + " like \"%?\"" + (filtros.get(filtros.size() - 1) == filtro ? "" : ", ");
		}
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			for (int i = 0; i < filtros.size(); i++) {
				
				ps.setObject(i + 1, filtros.get(i)[1]);
			}
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				musicas.add(new MusicaBean(
							
							rs.getInt("idMusica"),
							rs.getString("nomeMusica"),
							rs.getString("duracaoMusica"),
							rs.getTime("duracaoMusica"),
							rs.getInt("idArtista")
						));
			}
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter músicas.");
			e.printStackTrace();
		}
		
		modelo.setObjects(musicas);
		
		return modelo;
	}
	
	public MusicaBean getMusicaBy(String tipo, int id) {
		
		MusicaBean musica = new MusicaBean();
		
		String sql = "select musica.* from musica";
		
		switch (tipo.toLowerCase()) {
		
			case "id": sql += " where idMusica = ?";
			case "playlist": sql += ", mngrplm where mngrplm.idPlaylist = ?";
			case "album": sql += ", mngram where mngram.idAlbum = ?";
		}
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				musica.set("nomeMusica", rs.getString("nomeMusica"));
				musica.set("generoMusica", rs.getString("generoMusica"));
				musica.set("duracaoMusica", rs.getTime("duracaoMusica"));
				musica.set("idArtista", rs.getInt("idArtista"));
			}
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter informações da música.");
		}
		
		return musica;
	}
}