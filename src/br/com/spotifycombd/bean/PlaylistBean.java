package br.com.spotifycombd.bean;

import java.util.HashMap;

import tableModel.ObjectInfo;

public class PlaylistBean implements ObjectInfo {
	
	
	private HashMap<String, Object> info = new HashMap<>();	
	
	
	public PlaylistBean() {}
	
	public PlaylistBean(String nome, int idUsuario) {
		
		info.put("nomePlaylist", nome);
		info.put("idUsuario", idUsuario);
	}
	
	public PlaylistBean(int id, String nome, int idUsuario) {
		
		info.put("idPlaylist", id);
		info.put("nomePlaylist", nome);
		info.put("idUsuario", idUsuario);
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
		
		return new Object[] {"C�digo", "Nome"};
	}
	
	
	@Override
	public String toString() {
		
		return get("nomePlaylist");
	}
}
