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

@Path("/links/topic/all/topic={topic}/idutente={IDUtente}/nomefonte={NomeFonte}/limit={inf}-{sup}")
public class LinksForTopic {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getLinksGeneral(@PathParam("topic") String topic, @PathParam("IDUtente") int idutente,@PathParam("NomeFonte") String nome, @PathParam("inf") int inf, @PathParam("sup") int sup) {
		String result="";
		try {
			result = this.getLinks(topic,idutente,nome,inf,sup);
			
				
			
	
		}
		catch (Exception exc) {
			//result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private String getLinks(String topic, int idutente, String nome,int inf, int sup) throws Exception {
		
		String links ="[ ";
		List<String> sorgenti = new ArrayList<String>();
		Connection conn_b = DbManager.getConnection();
		Statement stmt_b = conn_b.createStatement();
		ResultSet rset_b = stmt_b.executeQuery("SELECT DISTINCT s.Pagina AS label FROM Accesso AS acc INNER JOIN Sorgenti AS s INNER JOIN Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE s.ID = p.ID_Fonte AND p.ID = c.IDPost AND t.ID = c.IDTopic AND (s.Nome = \""+nome+"\" OR s.Pagina=\""+nome+"\")  AND s.Pagina IS NOT NULL AND s.ID = acc.IDSorgente AND acc.IDUtente = "+idutente+" UNION SELECT DISTINCT s.Nome AS label FROM Accesso AS acc INNER JOIN Sorgenti AS s INNER JOIN Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE s.ID = p.ID_Fonte AND p.ID = c.IDPost AND t.ID = c.IDTopic AND s.Nome LIKE '"+nome+"' AND s.Pagina IS NULL AND s.ID = acc.IDSorgente AND acc.IDUtente = "+idutente+";");
		while (rset_b.next()) {
			
			String st_b = rset_b.getObject("label").toString();
			sorgenti.add(st_b);
			
		}
		
		rset_b.close();
		stmt_b.close();
		conn_b.close();
		
	
		
		
for(int x=0;x<sorgenti.size();x++){
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT DISTINCT p.Link AS label, p.Text AS testo FROM Utenti AS u INNER JOIN Accesso AS acc INNER JOIN Sorgenti AS s INNER JOIN Post AS p INNER JOIN Contenuto AS c INNER JOIN Topics AS t WHERE p.ID = c.IDPost AND c.IDTopic = t.ID AND u.ID = acc.IDUtente AND acc.IDSorgente = s.ID AND s.ID = p.ID_Fonte AND (s.Nome = \""+sorgenti.get(x)+"\" OR s.Pagina=\""+sorgenti.get(x)+"\") AND acc.IDUtente = "+idutente+" AND t.Topic='"+topic+"' LIMIT "+inf+","+sup+";");
		links += "{\"nome\":\""+sorgenti.get(x)+"\",\"links\":[ ";
		while (rset.next()) {
			
			String st = rset.getObject("label").toString();
			String tes = rset.getObject("testo").toString();
			if (tes.length() != 0 && tes.length()>25){tes = tes.substring(0,25)+"...";}
			else if (tes.length() == 0){tes = st;};
			
			links += "{\"link\":\""+st+"\",\"testo\":\""+tes+"\"},";
			
		}
		links = links.substring(0,links.length()-1);
		links += "]},";
		rset.close();
		stmt.close();
		conn.close();
};//end for x
		
		links = links.substring(0,links.length()-1)+"]";
	
		return links;
	}
	
}

//restituisce i link relativi ad un topic a prescindere dalla polarità
//TODO nel lato client inserire il controllo: se pagina NOT NULL allora l'etichetta del bobble è la pagina altrimenti è il nome della fonte, ma non ripetuto
