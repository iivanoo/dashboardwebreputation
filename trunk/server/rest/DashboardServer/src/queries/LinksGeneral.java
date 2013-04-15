package queries;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.PathParam;

@Path("/links/general/all/idfonte={IDFonte}/limit={inf}-{sup}")
public class LinksGeneral {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getLinksGeneral(@PathParam("IDFonte") int id, @PathParam("inf") int inf, @PathParam("sup") int sup) {
		String result="";
		try {
			List<String> links = this.getLinks(id,inf,sup);
			for(int i=0; i<links.size(); i++) {
				result += links.get(i);
				if(i != links.size() - 1) {
					result += ",";
				}
			}
	
		}
		catch (Exception exc) {
			//result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private List<String> getLinks(int id,int inf, int sup) throws Exception {
		List<String>links = new ArrayList<String>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT p.Link AS label FROM Sorgenti AS s INNER JOIN  Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE p.ID = c.IDPost AND c.IDTopic=t.ID AND p.ID_Fonte = s.ID AND p.ID_Fonte ="+id+" LIMIT "+inf+" , "+sup+";");
		while (rset.next()) {
			
			String st = rset.getObject("label").toString();
			links.add(st);
		}
		
		rset.close();
		stmt.close();
		conn.close();
	
		return links;
	}
	
}

//restituisce i link relativi ad un topic a prescindere dalla polarità
//TODO nel lato client inserire il controllo: se pagina NOT NULL allora l'etichetta del bobble è la pagina altrimenti è il nome della fonte, ma non ripetuto
