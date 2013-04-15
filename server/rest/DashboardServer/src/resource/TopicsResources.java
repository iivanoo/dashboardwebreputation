package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Topics;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/Topics")
public class TopicsResources {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTopicsJSON() {
		String result = "[";
		try {
			List<Topics> topic = this.getTopics();
			for(int i=0; i<topic.size(); i++) {
				result += topic.get(i).toJson();
				if(i != topic.size() - 1) {
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

	
	
	private List<Topics> getTopics() throws Exception {
		List<Topics> topic = new ArrayList<Topics>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT * from Topics;");
		while (rset.next()) {
			topic.add(new Topics(rset));
		}
		rset.close();
		stmt.close();
		conn.close();
		return topic;
	}
}
