package resource;
//TODO Nomefonte--->ID_Fonte
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Post;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/Post")
public class PostResources {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllPostJSON() {
		String result = "[";
		try {
			List<Post> post = this.getPost();
			for(int i=0; i<post.size(); i++) {
				result += post.get(i).toJson();
				if(i != post.size() - 1) {
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

	
	private List<Post> getPost() throws Exception {
		List<Post> post = new ArrayList<Post>();
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT * from Post;");
		while (rset.next()) {
			post.add(new Post(rset));
		}
		rset.close();
		stmt.close();
		conn.close();
		return post;
	}
}
