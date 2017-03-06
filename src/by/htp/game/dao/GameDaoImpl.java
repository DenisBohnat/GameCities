package by.htp.game.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class GameDaoImpl implements IGameDao {

	private static final String PATH = "resources\\CityList.txt";

	@Override
	public Set<String> readCityList() {
		Set<String> citySet = new HashSet<String>();
		BufferedReader br = null;
		String tmpLine;
		try {
			br = new BufferedReader(new FileReader(new File(PATH)));
			while ((tmpLine = br.readLine()) != null) {
				citySet.add(tmpLine.trim());
			}
		} catch (FileNotFoundException e) {
			System.err.println("something wrong with file" + e);
		} catch (IOException e) {
			System.err.println("something wrong with file" + e);
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.err.println("something wrong with file" + e);
				}
			}
		}
		return citySet;
	}

	@Override
	public void addToFileCity(String word) {
		if (word != null) {
			File f = new File(PATH);
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
				pw.println(word);
			} catch (IOException e) {
				System.err.println("something wrong with file" + e);
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
		} else {
			System.err.println("String is null");
		}
	}
}
