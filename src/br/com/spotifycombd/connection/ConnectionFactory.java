package br.com.spotifycombd.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private final String url = "jdbc:mysql://localhost:3306/spotifycombd";
	private final String user = "root";
	private final String password = "";
	
	
	public Connection getConnect() {
		
		Connection conexao = null;
		
		try {
			
			conexao = DriverManager.getConnection(url, user, password);
			
		} catch (SQLException e) {
			
			System.out.println("Não foi possível criar conexão com o banco de dados.");
		}
		
		return conexao;
	}
}
