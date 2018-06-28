package br.com.spotifycombd.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private final String url = "jdbc:mysql://localhost:3306/spotify";
	private final String user = "root";
	private final String password = "";
	
	
	private static Connection conexao = null;
	
	
	public Connection getConnect() {
		
		if (conexao != null) {
			
			return conexao;
		}
		
		try {
			
			conexao = DriverManager.getConnection(url, user, password);
			
		} catch (SQLException e) {
			
			System.out.println("N�o foi poss�vel criar conex�o com o banco de dados.");
			e.printStackTrace();
		}
		
		return conexao;
	}
}
