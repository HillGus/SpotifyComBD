package br.com.spotifycombd.bean;

import java.util.HashMap;

import tableModel.ObjectInfo;

public class AlbumBean implements ObjectInfo {
	
	private HashMap<String, Object> info = new HashMap<>();
	
	
	public AlbumBean() {}
	
	public AlbumBean(int id, String nome) {
		
		info.put("idAlbum", id);
		info.put("nomeAlbum", nome);
	}

	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		
		return (T) info.get(key);
	}
	
	public void set(String key, Object value) {
		
		info.put(key, value);
	}

	@Override
	public Object[] getInfo() {
		
		return new Object[] {get("idAlbum"), get("nomeAlbum")};
	}

	@Override
	public Object[] getInfoName() {
		
		return new Object[] {"Código", "Nome"};
	}
}
