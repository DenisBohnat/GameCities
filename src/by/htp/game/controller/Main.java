package by.htp.game.controller;

import by.htp.game.service.GameServiceImpl;

public class Main {

	public static void main(String[] args) {

		GameServiceImpl game = new GameServiceImpl();
		game.playGame();

	}

}
