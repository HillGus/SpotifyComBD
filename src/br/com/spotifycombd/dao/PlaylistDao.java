package br.com.spotifycombd.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;

import br.com.spotifycombd.bean.MusicaBean;
import br.com.spotifycombd.bean.PlaylistBean;
import br.com.spotifycombd.connection.ConnectionFactory;
import br.com.spotifycombd.main.Controlador;
import tableModel.CustomTableModel;

public class PlaylistDao {

	private Connection conexao;
	
	private Controlador control = new Controlador();
	
	private static CustomTableModel<PlaylistBean> modelo = new CustomTableModel<>();
	private static CustomTableModel<MusicaBean> musicModel = new CustomTableModel<>();
	
	
	public PlaylistDao() {
		
		conexao = new ConnectionFactory().getConnect();
	}
	
	
	public void addPlaylist(PlaylistBean obj) {
		
		String sql = "insert into playlist (nomePlaylist, idUsuario) values (?, ?)";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, obj.get("nomePlaylist"));
			ps.setInt(2, obj.get("idUsuario"));
			
			ps.execute();
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao adicionar playlist.");
			e.printStackTrace();
		}
	}

	public void excluirPlaylist(int id) {
		
		String sql = "delete from mngrplm where idPlaylist = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao excluir m�sicas da playlist.");
			e.printStackTrace();
		}
		
		sql = "delete from playlist where idPlaylist = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao excluir playlist.");
			e.printStackTrace();
		}
	}

	//Adiciona uma música a uma determinada playlist de um determinado usuário
	public void addMusica(int idMusica, int idPlaylist, int idUsuario) {
		
		String sql = "insert into mngrPLM (idMusica, idPlaylist, idUsuario) values (?, ?, ?)";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, idMusica);
			ps.setInt(2, idPlaylist);
			ps.setInt(3, idUsuario);
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao adicionar música à playlist.");
			e.printStackTrace();
		}
	}
	
	//Deleta uma música de uma determinada playlist
	public void delMusica(int idMusica, int idPlaylist) {
		
		String sql = "delete from mngrPLM where idMusica = ? and idPlaylist = ? limit 1";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
		
		ps.setInt(1, idMusica);
		ps.setInt(2, idPlaylist);
		
		ps.execute();
		
		ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao excluir música da playlist.");
			e.printStackTrace();
		}
		
		getMusicModel(idPlaylist);
	}
	
	public CustomTableModel<PlaylistBean> getModel() {
		
		ArrayList<PlaylistBean> playlists = new ArrayList<PlaylistBean>();
	
		String sql = "select idPlaylist from playlist";
		
		try {
			
			Statement st = conexao.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				
				playlists.add(getPlaylist(rs.getInt("idPlaylist")));
			}
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter informaçoes do banco.");
		}
		
		modelo.setObjects(playlists);
		
		return modelo;
	}
	
	//Obtém o modelo com as músicas de uma deterinada playlist
	public CustomTableModel<MusicaBean> getMusicModel(int id) {
		
		ArrayList<MusicaBean> musicas = new MusicaDao().getMusicasBy("playlist", id);
		
		musicModel.setObjects(musicas);
		
		return musicModel;
	}

	public PlaylistBean getPlaylist(int id) {
		
		PlaylistBean playlist = null;
		
		String sql = "select * from playlist where idPlaylist = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				playlist = new PlaylistBean(
								rs.getInt("idPlaylist"),
								rs.getString("nomePlaylist"),
								rs.getInt("idUsuario"));
			}
			
			ps.close();
	
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter informaçoes da playlist.");
		}
		
		
		ArrayList<MusicaBean> musicas = new MusicaDao().getMusicasBy("playlist", id);
		
		return playlist;
	}

	public PlaylistBean getLastPlaylist() {
		
		PlaylistBean playlist = new PlaylistBean();
		
		String sql = "select * from playlist order by idPlaylist desc limit 1";
		
		try {
			
			Statement st = conexao.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				
				playlist.set("idPlaylist", rs.getInt("idPlaylist"));
				playlist.set("nomePlaylist", rs.getString("nomePlaylist"));
				playlist.set("idUsuario", rs.getInt("idUsuario"));
			}
			
			st.close();
			
		} catch (SQLException e) {
			
			System.out.println("N�o foi poss�vel obter a �ltima playlist.");
			e.printStackTrace();
		}
		
		return playlist;
	}
}