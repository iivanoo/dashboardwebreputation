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

@Path("/Accesso/mod/add/utente={Email}/nomefonte={NomeFonte}/pagina={Pagina}")
public class AggiungiAccessoPerUtente {


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String AddAccessoJSON(@PathParam("Email") String utente, @PathParam("NomeFonte") String fonte, @PathParam("Pagina") String pagina) {
		String result = "";
		try {
			
			this.setAccesso(utente,fonte, pagina);
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
	// TODO this method must be tested
	public void setAccesso(String utente, String fonte, String pagina) {
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
			
			if(pagina.equals("null")){
				sql = "SELECT ID FROM Sorgenti WHERE Nome = '"+fonte+"'";

			}
			else if(pagina.equals("public")){
				sql = "SELECT ID FROM Sorgenti WHERE Nome = '"+fonte+"' AND Pagina IS NULL";

			}
			else{
				 sql = "SELECT ID FROM Sorgenti WHERE Nome = '"+fonte+"' AND Pagina ='"+pagina+"'";

			};
			
			ResultSet rset = stmt_fonte.executeQuery(sql);
			while(rset.next()){
			idfonte = Integer.parseInt(rset.getObject("ID").toString());
			
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT IGNORE Accesso(IDUtente,IdSorgente) VALUES ("+id+","+idfonte+") ;";
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
