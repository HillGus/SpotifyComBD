package br.com.spotifycombd.bean;

import java.util.HashMap;

import br.com.spotifycombd.dao.PlaylistDao;
import tableModel.CustomTableModel;
import tableModel.ObjectInfo;

public class PlaylistBean implements ObjectInfo {
	
	
	private HashMap<String, Object> info = new HashMap<>();
	
	public PlaylistBean() {}
	
	public PlaylistBean(String nome, int idUsuario) {
		
		//Define as informaçoes no dicionario
		info.put("nomePlaylist", nome);
		info.put("idUsuario", idUsuario);
	}
	
	public PlaylistBean(int id, String nome, int idUsuario) {
		
		//Define as informaçoes no dicionario
		info.put("idPlaylist", id);
		info.put("nomePlaylist", nome);
		info.put("idUsuario", idUsuario);
		
		//Obtém músicas da playlist
		info.put("musicas", new PlaylistDao().getMusicModel(id));
	}
	
	
	public void set(String key, Object value) {
		
		info.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		
		return (T) info.get(key);
	}
	
	@Override
	public Object[] getInfo() {
		
		return new Object[] {get("idPlaylist"), get("nomePlaylist")};
	}

	@Override
	public Object[] getInfoName() {
		
		return new Object[] {"Código", "Nome"};
	}
	
	
	@Override
	public String toString() {
		
		//Retorna informaçao que aparecerá no comboBox
		return get("nomePlaylist");
	}
}
