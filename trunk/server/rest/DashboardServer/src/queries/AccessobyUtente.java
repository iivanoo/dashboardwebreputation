package queries;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Accesso;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.PathParam;

@Path("/Accesso/search/utente={IDUtente}")
public class AccessobyUtente {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllAccessoJSON(@PathParam("IDUtente") int id) {
		String result = "[";
		try {
			List<Accesso> accesso = this.getAccesso(id);
			for(int i=0; i<accesso.size(); i++) {
				result += accesso.get(i).toJson();
				if(i != accesso.size() - 1) {
					result += ",";
				}
			}
			result += "]";
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private List<Accesso> getAccesso(int id) throws Exception {
		List<Accesso> accesso = new ArrayList<Accesso>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT * from Accesso WHERE IDUtente ="+id+";");
		while (rset.next()) {
			accesso.add(new Accesso(rset));
		}
		rset.close();
		stmt.close();
		conn.close();
		return accesso;
	}

}
