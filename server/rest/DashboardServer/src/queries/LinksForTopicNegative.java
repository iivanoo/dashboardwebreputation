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

@Path("/links/topic/negative/idutente={IDUtente}/idfonte={IDFonte}/topic={topic}/limit={inf}-{sup}")
public class LinksForTopicNegative {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getLinksForTopic(@PathParam("IDUtente") int idutente,@PathParam("IDFonte") int id,@PathParam("topic") String topic, @PathParam("inf") int inf, @PathParam("sup") int sup) {
		String result = "[ ";
		try {
			List<String> links = this.getLinks(idutente, id, topic,inf,sup);
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
			//System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private List<String> getLinks(int idutente, int id, String topic, int inf, int sup) throws Exception {
		List<String>links = new ArrayList<String>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT DISTINCT p.Link AS label FROM Utenti AS u INNER JOIN Accesso AS acc INNER JOIN Sorgenti AS s INNER JOIN  Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE u.ID = acc.IDUtente AND acc.IDSorgente=s.ID AND p.ID = c.IDPost AND c.IDTopic=t.ID AND p.ID_Fonte = s.ID AND p.ID_Fonte ="+id+" AND t.Topic = '" + topic + "' AND acc.IDUtente = "+idutente+" AND p.Polarity = '-1' LIMIT "+inf+" , "+sup+";");
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