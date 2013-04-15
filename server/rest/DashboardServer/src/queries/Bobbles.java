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

@Path("/Sorgenti/search/bobbles/nomefonte-topic={Parametro}")
public class Bobbles {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getBobblesLabels(@PathParam("Parametro") String par ) {
		String result = "[";
		try {
			List<String> sorgenti = this.getSorgenti(par);
			for(int i=0; i<sorgenti.size(); i++) {
				result += sorgenti.get(i);
				if(i != sorgenti.size() - 1) {
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

	
	private List<String> getSorgenti(String par) throws Exception {
		List<String> sorgenti = new ArrayList<String>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT DISTINCT s.Pagina AS label FROM Sorgenti AS s INNER JOIN Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE s.ID = p.ID_Fonte AND p.ID = c.IDPost AND t.ID = c.IDTopic AND CONCAT_WS('$',s.Nome,t.Topic) LIKE '"+par+"%' AND s.Pagina IS NOT NULL UNION SELECT DISTINCT s.Nome AS label FROM Sorgenti AS s INNER JOIN Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE s.ID = p.ID_Fonte AND p.ID = c.IDPost AND t.ID = c.IDTopic AND CONCAT_WS('$',s.Nome,t.Topic) LIKE '"+par+"%' AND s.Pagina IS NULL;");
		while (rset.next()) {
			
			String st = rset.getObject("label").toString();
			sorgenti.add(st);
		}
		
		rset.close();
		stmt.close();
		conn.close();
	
		return sorgenti;
	}
	
}

//restituisce le sorgenti dei post relativi ad un topic.
//TODO nel lato client inserire il controllo: se pagina NOT NULL allora l'etichetta del bobble è la pagina altrimenti è il nome della fonte, ma non ripetuto
