package br.com.spotifycombd.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.spotifycombd.bean.MusicaBean;
import br.com.spotifycombd.bean.PlaylistBean;
import br.com.spotifycombd.connection.ConnectionFactory;
import tableModel.CustomTableModel;

public class PlaylistDao {

	private Connection conexao;
	
	private CustomTableModel<PlaylistBean> modelo = new CustomTableModel<>();
	
	
	public PlaylistDao() {
		
		conexao = new ConnectionFactory().getConnect();
	}
	
	
	public void addPlaylist(PlaylistBean obj) {
		
		String sql = "insert into playlist (nomePlaylist) values (?)";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, obj.get("nomePlaylist"));
			
			ps.execute();
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao adicionar playlist.");
		}
	}

	public void excluirPlaylist(int id) {
		
		String sql = "delete from playlist where idPlaylist = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao excluir playlist.");
		}
	}

	public void alterarPlaylist(PlaylistBean obj) {
		
		String sql = "update playlist set nomePlaylist = ? where idPlaylist = ?";
	
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, obj.get("nomePlaylist"));
			ps.setInt(2, obj.get("idPlaylist"));
			
			ps.execute();
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao alterar playlist.");
		}
	}

	public CustomTableModel<PlaylistBean> getModel() {
		
		ArrayList<PlaylistBean> playlists = new ArrayList<PlaylistBean>();
	
		String sql = "select * from playlist";
		
		try {
			
			Statement st = conexao.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				
				playlists.add(getPlaylist(rs.getInt("idPlaylist")));
			}
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter informações do banco.");
		}
		
		return modelo;
	}

	public PlaylistBean getPlaylist(int id) {
		
		PlaylistBean playlist = new PlaylistBean();
		
		String sql = "select * from playlist where idPlaylist = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				playlist.set("idPlaylist", rs.getInt("idPlaylist"));
				playlist.set("nomePlaylist", rs.getString("nomePlaylist"));
			}
			
			ps.close();
	
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter informações da playlist.");
		}
		
		
		ArrayList<MusicaBean> musicas = new ArrayList<MusicaBean>();
		
		sql = "select * from mngrplm where idPlaylist = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				MusicaDao md = new MusicaDao();
				
				musicas.add(md.getMusica(rs.getInt("idMusica")));
			}
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter músicas da playlist.");
		}
		
		playlist.set("musicas", musicas);
		
		return playlist;
	}
}