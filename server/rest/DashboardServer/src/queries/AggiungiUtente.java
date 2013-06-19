package queries;

import entities.Utenti;


import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;


import db.DbManager;

import java.sql.*;

@Path("/Utenti/add/nome={Nome}/cognome={Cognome}/email={Email}/pwd={Pwd}/data_nascita={DataN}/citta={Citta}/creato_da={Cda}/data_ins={DataI}/admin={Admin}/attivo={Attivo}")
public class AggiungiUtente {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String aggiungiUtente(@PathParam("Nome") String nome, @PathParam("Cognome") String cognome, @PathParam("Email") String email, @PathParam("Pwd") String pwd, @PathParam("DataN") String dataN, @PathParam("Citta") String citta, @PathParam("Cda") int cda, @PathParam("DataI") String dataI, @PathParam("Admin") boolean admin, @PathParam("Attivo") boolean attivo) {
		String result = "";
		try {
			result = this.addUtente(nome, cognome, email, pwd ,dataN, citta, cda, dataI, admin, attivo);
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	
	public String addUtente(String nome, String cognome, String email, String pwd , String dataN, String citta, int cda, String dataI, boolean admin, boolean attivo) {
		String result = "";
		try {
			
			Utenti utenti = new Utenti(0, nome, cognome, email, pwd, dataN, citta, cda, dataI, admin, attivo);
		
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Utenti (ID, Nome, Cognome, Email, Password, Data_nascita, Citta, Creato_da, Data_ins, Admin, Attivo) VALUES (0, '"+utenti.getNome()+"', '"+utenti.getCognome()+"', '"+utenti.getEmail()+"', '"+utenti.getPassword()+"', '"+utenti.getData_nascita()+"', '"+utenti.getCitta()+"', "+utenti.getCreato_da()+", '"+utenti.getData_ins()+"', "+utenti.getAdmin()+", "+utenti.getAttivo()+");";
			stmt.executeUpdate(query);
			
			stmt.close();
			conn.close();
			
		} catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	

}
