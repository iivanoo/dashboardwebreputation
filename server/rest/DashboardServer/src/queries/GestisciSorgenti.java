package queries;

import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;


import db.DbManager;

import java.sql.*;

@Path("/Sorgenti/gestione/action={Action}/id={ID}/nomefonte={Nome}/pagina={Pagina}/link={Link}/tipo={Tipo}/autore={Autore}/icona={Icona}")
public class GestisciSorgenti {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String aggiungiSorgente(@PathParam("ID") int id, @PathParam("Action") String action, @PathParam("Nome") String nome, @PathParam("Pagina") String pagina, @PathParam("Link") String link, @PathParam("Tipo") String tipo, @PathParam("Autore") int autore, @PathParam("Icona") String icona) {
		String result = "";
		try {
			
			if(action.equals("add")){result += this.addSorgente(nome, pagina, link, tipo , autore, icona, 86400, "2001-01-01");}
			else if(action.equals("delete")){result += this.delSorgente(id);}
			else if(action.equals("mod")){result+= this.modSorgente(id, nome, pagina, link, tipo, autore, icona);}
			else{result+="false";};
			
			
			
			
			
			
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
	
	
	public String addSorgente(String nome, String pagina, String link, String tipo , int autore, String icona, int updateinterval, String lasttimestamp) {
		System.out.println(lasttimestamp);
		
		String result = "";
		try {
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Sorgenti (ID, Nome, Pagina, Link, Tipo, Autore, Icona, updateInterval, lastTimestamp) VALUES (0, '"+nome+"', '"+pagina+"', '"+link+"', '"+tipo+"', '"+autore+"', '"+icona+"',"+updateinterval+", '"+lasttimestamp+"');";
			stmt.executeUpdate(query);
			result+="true";
			stmt.close();
			conn.close();
			
		} catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
			result+="false";
		}
		return result;
	}


	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)

	public String delSorgente(int id) {
		String result = "";
		try {
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "DELETE FROM Sorgenti WHERE ID="+id+";";
			stmt.executeUpdate(query);
			result+="true";
			stmt.close();
			conn.close();
			
		} catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
			result+="false";
		}
		return result;
	}

	
@PUT
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public String modSorgente(int id, String nome, String pagina, String link, String tipo , int autore, String icona) {
	String result = "";
	try {
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		String query = "UPDATE Sorgenti SET Nome='"+nome+"', Pagina='"+pagina+"', Link='"+link+"', Tipo='"+tipo+"',Autore="+autore+", Icona='"+icona+"' WHERE ID ="+id+";";
		stmt.executeUpdate(query);
		result+="true";
		stmt.close();
		conn.close();
		
	} catch (Exception exc) {
		result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
		exc.printStackTrace();
		result+="false";
	}
	return result;
}
	
	
	
	
	
	
	

}




