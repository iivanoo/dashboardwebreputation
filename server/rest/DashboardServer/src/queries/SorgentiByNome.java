package queries;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Sorgenti;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.PathParam;

@Path("/Sorgenti/search/nome/nome={Nome}")
public class SorgentiByNome {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllSorgentiJSON(@PathParam("Nome") String nome ) {
		String result = "[";
		try {
			List<Sorgenti> sorgenti = this.getSorgenti(nome);
			for(int i=0; i<sorgenti.size(); i++) {
				result += sorgenti.get(i).toJson();
				if(i != sorgenti.size() - 1) {
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

	

	private List<Sorgenti> getSorgenti(String nome) throws Exception {
		List<Sorgenti> sorgenti = new ArrayList<Sorgenti>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT * from Sorgenti Where Nome = '"+nome+"';");
		while (rset.next()) {
			sorgenti.add(new Sorgenti(rset));
		}
		rset.close();
		stmt.close();
		conn.close();
		return sorgenti;
	}
	
}
