
//TODO file da cancellare

package queries;


import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



import db.DbManager;


import java.sql.*;

import javax.ws.rs.PathParam;

@Path("/Accesso/mod/del/utente={Email}/nomefonte={NomeFonte}")
public class DeleteAccessoPerUtente {


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String AddAccessoJSON(@PathParam("Email") String utente, @PathParam("NomeFonte") String fonte) {
		String result = "";
		try {
			
			this.setAccesso(utente,fonte);
			result += "true";
			}
		
		
		catch (Exception exc) {
			result += "false";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void setAccesso(String utente, String fonte) {
		try {
			int id = 0;
		
		Connection conn_utente = DbManager.getConnection();
		Statement stmt_utente = conn_utente.createStatement();
		ResultSet rset_utente = stmt_utente.executeQuery("SELECT ID FROM Utenti WHERE Email = '"+utente+"'");
		while(rset_utente.next()){
			id =  Integer.parseInt(rset_utente.getObject("ID").toString());
		};
		
		rset_utente.close();
		stmt_utente.close();
		conn_utente.close();
		
			String sql = "";
			int idfonte=0;
			Connection conn_fonte = DbManager.getConnection();
			Statement stmt_fonte = conn_fonte.createStatement();
			sql = "SELECT ID FROM Sorgenti WHERE Nome = '"+fonte+"'";
			ResultSet rset = stmt_fonte.executeQuery(sql);
			while(rset.next()){
			idfonte = Integer.parseInt(rset.getObject("ID").toString());
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "DELETE IGNORE FROM Accesso WHERE IDUtente="+id+" AND IDSorgente="+idfonte+";";
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
			};
			
			
			
			rset.close();
			stmt_fonte.close();
			conn_fonte.close();
			
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
}
