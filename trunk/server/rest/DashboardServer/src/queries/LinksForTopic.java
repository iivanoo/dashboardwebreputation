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

@Path("/links/topic/all/idfonte={IDFonte}/topic={topic}/limit={inf}-{sup}")
public class LinksForTopic {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getLinksForTopic(@PathParam("IDFonte") int id,@PathParam("topic") String topic, @PathParam("inf") int inf, @PathParam("sup") int sup) {
		String result = "[";
		try {
			List<String> links = this.getLinks(id, topic,inf,sup);
			for(int i=0; i<links.size(); i++) {
				result += links.get(i);
				if(i != links.size() - 1) {
					result += ",";
				}
			}
			result += "]";
		}
		catch (Exception exc) {
			//result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private List<String> getLinks(int id, String topic, int inf, int sup) throws Exception {
		List<String>links = new ArrayList<String>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT p.Link AS label FROM Sorgenti AS s INNER JOIN  Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE p.ID = c.IDPost AND c.IDTopic=t.ID AND p.ID_Fonte = s.ID AND p.ID_Fonte ="+id+" AND t.Topic = '" + topic + "' LIMIT "+inf+" , "+sup+";");
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
//restituisce i link relativi ad al topic cercato per la fonte specifica cliccata nel box carrot