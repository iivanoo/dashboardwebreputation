package queries;

import entities.Post;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;


import db.DbManager;

import java.sql.*;

@Path("/Post/id={ID}")
public class PostResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String searchPostByIdJSON(@PathParam("ID") String id) {
		String result = "";
		try {
			result = this.getPost(id).toJson();
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	
	@DELETE
	public void deletePost(@PathParam("ID") String id) {
		try {
			Connection conn = DbManager.getConnection();
			String query = "DELETE from Post where ID = " + id + ";";
			Statement stmt = conn.prepareStatement(query);
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new WebApplicationException(404);
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	// TODO this method must be tested
	public void updatePost(@PathParam("ID") String id, String payload) {
		try {
			Post post = new Post(payload);
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "UPDATE Post SET " + 
					"Data = '" + post.getData() + "', " +
					"Polarity = " + post.getPolarity() + ", " +
					"ID_Fonte = '" + post.getID_Fonte() + "', " +
					"Text = '" + post.getText() + "', " +
					" WHERE ID = " + id + ";";
			stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// TODO this method must be tested
	public String addPost(String payload) {
		String result = "";
		try {
			Post post = new Post(payload);
			Connection conn = DbManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Post VALUES (0,'" +
					post.getLink() + "','" +
					post.getData() + "','" +
					post.getPolarity() + "','" +
					post.getID_Fonte() + "','" +
					post.getText() + "');";
			ResultSet rset = stmt.executeQuery(query);
			result = new Post(rset).toJson();
			stmt.close();
			conn.close();
		} catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			exc.printStackTrace();
		}
		return result;
	}

	private Post getPost(String id) throws Exception {
		Post post = null;
		Connection conn = DbManager.getConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT * from Post where ID = " + id + ";";
		ResultSet rset = stmt.executeQuery(query);
		if (rset.next()) {
			post = new Post(rset);
		}
		rset.close();
		stmt.close();
		conn.close();
		return post;
	}
}
