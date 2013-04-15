package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Accesso;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/Accesso")
public class AccessoResources {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllAccessoJSON() {
		String result = "[";
		try {
			List<Accesso> accesso = this.getAccesso();
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

	
	private List<Accesso> getAccesso() throws Exception {
		List<Accesso> accesso = new ArrayList<Accesso>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT * from Accesso;");
		while (rset.next()) {
			accesso.add(new Accesso(rset));
		}
		rset.close();
		stmt.close();
		conn.close();
		return accesso;
	}

}
