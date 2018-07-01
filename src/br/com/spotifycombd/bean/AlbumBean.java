package br.com.spotifycombd.bean;

import java.util.HashMap;

import br.com.spotifycombd.dao.MusicaDao;
import br.com.spotifycombd.dao.UsuarioDao;
import tableModel.ObjectInfo;

public class AlbumBean implements ObjectInfo {
	
	private HashMap<String, Object> info = new HashMap<>();
	
	
	public AlbumBean() {}
	
	public AlbumBean(int id, String nome, int idUsuario) {
		
		//Define as informaçoes no dicionário
		info.put("idAlbum", id);
		info.put("nomeAlbum", nome);
		info.put("idUsuario", idUsuario);
		
		//obtém os dados adicionais do banco de dados
		info.put("nomeUsuario", new UsuarioDao().getUser(idUsuario).get("nomeUsuario"));
		info.put("musicas", new MusicaDao().getMusicasBy("album", id));
	}
	
	public AlbumBean(String nome, int idUsuario) {
		
		//Define as informaçoes no dicionário
		info.put("nomeAlbum", nome);
		info.put("idUsuario", idUsuario);
		
		//obtém os dados adicionais do banco de dados
		info.put("nomeUsuario", new UsuarioDao().getUser(idUsuario).get("nomeUsuario"));
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
		
		//Retorna as informaçoes que aparecerao na tabela
		//Usado pelo customTableModel para criar o modelo
		return new Object[] {get("idAlbum"), get("nomeAlbum")};
	}

	@Override
	public Object[] getInfoName() {
		
		//Retorna o título das colunas da tabela
		//Usado pelo customTableModel para criar o modelo
		return new Object[] {"Código", "Nome"};
	}
	
	@Override
	public String toString() {
		
		//Retorna informaçao que aparecerá no comboBox
		return get("nomeAlbum");
	}
}
