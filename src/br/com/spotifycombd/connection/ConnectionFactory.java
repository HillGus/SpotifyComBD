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
			
			//Cria conexao
			conexao = DriverManager.getConnection(url, user, password);
			
		} catch (SQLException e) {
			
			System.out.println("Nao foi possível criar conexao com o banco de dados.\n");
			e.printStackTrace();
		}
		
		return conexao;
	}
}
