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

@Path("/Sorgenti/search/bobbles/nomefonte={fonte}/idutente={id}")
public class BobblesGeneral {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getBobblesLabels(@PathParam("fonte") String fonte,@PathParam("id") int id ) {
		String result = "";
		try {
			List<String> sorgenti = this.getSorgenti(fonte, id);
			for(int i=0; i<sorgenti.size(); i++) {
				result += sorgenti.get(i);
				if(i != sorgenti.size() - 1) {
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

	
	private List<String> getSorgenti(String fonte,  int id) throws Exception {
		List<String> sorgenti = new ArrayList<String>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT DISTINCT s.Pagina AS label FROM Accesso AS acc INNER JOIN Sorgenti AS s INNER JOIN Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE s.ID = p.ID_Fonte AND p.ID = c.IDPost AND t.ID = c.IDTopic AND s.Nome LIKE '"+fonte+"'  AND s.Pagina IS NOT NULL AND s.ID = acc.IDSorgente AND acc.IDUtente = "+id+" UNION SELECT DISTINCT s.Nome AS label FROM Accesso AS acc INNER JOIN Sorgenti AS s INNER JOIN Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE s.ID = p.ID_Fonte AND p.ID = c.IDPost AND t.ID = c.IDTopic AND s.Nome LIKE '"+fonte+"' AND s.Pagina IS NULL AND s.ID = acc.IDSorgente AND acc.IDUtente = "+id+";");
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
