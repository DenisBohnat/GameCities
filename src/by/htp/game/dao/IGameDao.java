package by.htp.game.dao;

import java.util.Set;

public interface IGameDao {

	Set<String> readCityList();

	void addToFileCity(String word);

}
