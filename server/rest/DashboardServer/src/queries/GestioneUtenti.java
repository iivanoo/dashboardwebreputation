package queries;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import db.DbManager;
import java.net.BindException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/gestioneutenti")
public class GestioneUtenti {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getLinksGeneral() {
		String result="";
		try {
			result = this.getData();
			
				
			
	
		}
		catch (Exception exc) {
			//result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private String getData() throws Exception {
		
		String data ="{\"selezione\":[ ";
		List<String> utenti = new ArrayList<String>();
		Connection conn_b = DbManager.getConnection();
		Statement stmt_b = conn_b.createStatement();
		ResultSet rset_b = stmt_b.executeQuery("SELECT Email FROM Utenti;");
		while (rset_b.next()) {
			
			String st_b = rset_b.getObject("Email").toString();
			utenti.add(st_b);
			
		}
		
		rset_b.close();
		stmt_b.close();
		conn_b.close();
		
		List<String> sorgenti = new ArrayList<String>();
		Connection conn_s = DbManager.getConnection();
		Statement stmt_s = conn_s.createStatement();
		ResultSet rset_s = stmt_s.executeQuery("SELECT DISTINCT Nome FROM Sorgenti");
		while (rset_s.next()) {
			String st_s = rset_s.getObject("Nome").toString();
			sorgenti.add(st_s);
			
		}
		
		rset_b.close();
		stmt_b.close();
		conn_b.close();
	

		for(int i = 0; i<utenti.size(); i++){
			data+="{\"utente\":\""+utenti.get(i)+"\",\"fonti\":[ ";
			for(int k = 0; k<sorgenti.size(); k++){
				Connection conn_data = DbManager.getConnection();
				Statement stmt_data = conn_data.createStatement();
				ResultSet rset_data = stmt_data.executeQuery("SELECT DISTINCT s.Nome, \"checked\" AS checked FROM Utenti AS u INNER JOIN Accesso aS acc INNER JOIN Sorgenti AS s WHERE u.ID = acc.IDUtente AND acc.IDSorgente = s.ID AND u.Email = '"+utenti.get(i)+"' AND s.Nome = '"+sorgenti.get(k)+"'");
				int count = 0;
				while (rset_data.next()) {
					count = count+1;
						data+= "{\"nome\":\""+rset_data.getObject("Nome")+"\", \"checked\":\""+rset_data.getObject("checked")+"\"},";
				};//end while
				if(count == 0){data+= "{\"nome\":\""+sorgenti.get(k)+"\", \"checked\":\"\"},";};
			
		rset_data.close();
		stmt_data.close();
		conn_data.close();

			};//end for k (sorgenti) 
			data = data.substring(0,data.length()-1);
			data += "]},";
		};//end for i (utenti)





data = data.substring(0,data.length()-1);
data += "]}";
return data;
	}
	
}
