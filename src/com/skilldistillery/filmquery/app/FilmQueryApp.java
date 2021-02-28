package com.skilldistillery.filmquery.app;


import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
//      app.test();
		app.launch();
	}

//	private void test() throws SQLException {
//		Film film = db.findFilmById(1);
//		System.out.println(film);
//		showActors(film);
//	}

	private void showActors(Film film) {
		for (Actor a : film.getActors()) {
			System.out.println(a);
		}
	}

	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) throws SQLException {

		boolean option = true;
		int numOption = 0;
		int filmId = 0;
		String filmKeyword = null;
		boolean keepGoing = true;
         
	    OUTER:	
		while (option) {
			displayUserMenu();

			try {

				numOption = input.nextInt();
				input.nextLine();

				switch (numOption) {
				case 1:
					INNER: while (keepGoing) {
						System.out.println("Please enter film id: ");
						filmId = input.nextInt();
						input.nextLine();
						Film film = db.findFilmById(filmId);
						if (film != null) {
							System.out.println(film);
							System.out.println();
							showActors(film);
							System.out.println();
							System.out.println();
							System.out.println("Do you want to look another film? (y/n)");
							String yesOrnot = input.nextLine();
							if(yesOrnot.equalsIgnoreCase("y")) {
							continue INNER;	
							} else {
								continue OUTER;
							}
						} else {
							System.out.println("That film id doesnt exist please try again");
							continue INNER;
						}
					}
					break;
				case 2:
					INNER: while (keepGoing) {
						System.out.println("Please enter film keyword: ");
						filmKeyword = input.nextLine();
						Film singleFilm	= db.findFilmByKeyword("%"+filmKeyword+"%");
						
						if (singleFilm == null ) {
							System.out.println("That keyword doesnt match any existing film please try again");
							continue INNER;
							
						} else {
							System.out.println("Do you want to look another film? (y/n)");
							String yesOrnot = input.nextLine();
							if(yesOrnot.equalsIgnoreCase("y")) {
								continue INNER;	
							} else {
								continue OUTER;
							}
						}
					}
					break;
				case 3:
					System.out.println("See You Later!");
					option = false;
					break;

				default:

					System.out.println("Invalid option please try again");

					break;
				}

			} catch (InputMismatchException e) {
				System.err.println("Invalid input please try again.");
				input.nextLine();
			}
		}
	}

	private void displayUserMenu() {

		System.out.println("--------------------------------------------");
		System.out.println("|                                          |");
		System.out.println("|             Enter an option:             |");
		System.out.println("|                                          |");
		System.out.println("--------------------------------------------");
		System.out.println("|                                          |");
		System.out.println("|   1. Look up a film by its id            |");
		System.out.println("|                                          |");
		System.out.println("|   2. Look up a film by a search keyword. |");
		System.out.println("|                                          |");
		System.out.println("|   3. Exit the application.               |");
		System.out.println("|                                          |");
		System.out.println("|                                          |");
		System.out.println("|                                          |");
		System.out.println("--------------------------------------------");
	}

}
