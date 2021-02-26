package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	
	static {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public DatabaseAccessorObject() {
		
	}

  @Override
  public Film findFilmById(int filmId) throws SQLException {
	Film film = null;
	
	String user = "student";
	String pass = "student";
	Connection conn = DriverManager.getConnection(URL, user, pass);

	
	  String sql = "SELECT * FROM film WHERE id = ?";
	  PreparedStatement stmt = conn.prepareStatement(sql);
	  stmt.setInt(1,filmId);
	  ResultSet filmResult = stmt.executeQuery();
	  if (filmResult.next()) {
	    film = new Film(); 
	    film.setId(filmResult.getInt("id"));
	    film.setTitle(filmResult.getString("title"));
	    film.setDescription(filmResult.getString("description"));
	    film.setReleaseYear(filmResult.getString("release_year"));
	    film.setLanguageId(filmResult.getInt("language_id"));
	    film.setRentalDuration(filmResult.getInt("rental_duration"));
	    film.setRentalRate(filmResult.getDouble("rental_rate"));
	    film.setLength(filmResult.getInt("length"));
	    film.setReplacementCost(filmResult.getDouble("replacement_cost"));;
	    film.setRating(filmResult.getString("rating"));
	    film.setSpecialFeatures(filmResult.getString("special_features"));
	    film.setActors(findActorsByFilmId(filmId));
	  }
	 
	    filmResult.close();
	    stmt.close();
	    conn.close();
	
    return film;
  }

@Override
public Actor findActorById(int actorId) throws SQLException {
	 Actor actor = null;
	 
	 String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		
	  String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
	  PreparedStatement stmt = conn.prepareStatement(sql);
	  stmt.setInt(1,actorId);
	  ResultSet actorResult = stmt.executeQuery();
	  if (actorResult.next()) {
	    actor = new Actor();
	    actor.setId(actorResult.getInt("id"));
	    actor.setFirstName(actorResult.getString("first_name"));
	    actor.setLastName(actorResult.getString("last_name"));
	  }
	  
	    actorResult.close();
	    stmt.close();
	    conn.close();
	    
	  return actor;
	
}

@Override
public List<Actor> findActorsByFilmId(int filmId) {
	
	List<Actor> actors = new ArrayList<>();
	
	 String user = "student";
		String pass = "student";
	
	  try {
	    Connection conn = DriverManager.getConnection(URL, user, pass);
	    
	    String sql = "SELECT * FROM actor JOIN film_actor ON actor.id = film_actor.actor_id WHERE film_id = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setInt(1, filmId);
	    ResultSet rs = stmt.executeQuery();
	    while (rs.next()) {
	      Actor actor = new Actor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"));
	      actors.add(actor);
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	  } catch (SQLException e) {
	    e.printStackTrace();
	  }
	  return actors;
	
	
	
}

}
