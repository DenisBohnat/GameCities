package by.htp.game.service;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import by.htp.game.dao.GameDaoImpl;

public class GameServiceImpl implements IGameService{

	private Set<String> cityList;
	private Set<String> cityPlayList;
	private char lastChar;
	private String currentCity;
	
	@Override
	public void playGame() {
		GameDaoImpl gameDao=new GameDaoImpl();
		cityList=gameDao.readCityList();
		cityPlayList=new LinkedHashSet<String>();
		boolean endGame=false;
		boolean inspWord=false;
		boolean opponentMove=false;
		System.out.println("---GAME <<Cities>>---");
		System.out.println("---You go first---");
		System.out.println("---Enter <<выход>> to complete the game---");
		Scanner sc=new Scanner(System.in);
		while(!endGame){
			while(!inspWord){
				System.out.println("Enter the name of a city: ");
				currentCity=sc.nextLine();
				if(currentCity.toLowerCase().equals("выход")){
					System.out.println("You lose!");
					System.out.println("cities chain"+"\n"+cityPlayList);
					this.rememberCity(cityPlayList);
					break;
				}else{
					inspWord=this.inspectionWord(currentCity);
					if(inspWord==true){
						System.out.println("Your city: --> "+currentCity);
					}
				}
			}
			if(currentCity.toLowerCase().equals("выход")){
				endGame=true;
			}else{
			opponentMove=this.opponentMove(currentCity);
			if(opponentMove==false){
				System.out.println("You won!");
				System.out.println("cities chain"+"\n"+cityPlayList);
				this.rememberCity(cityPlayList);
				endGame=true;
			}
			inspWord=false;
			opponentMove=false;
		}
		}
	}

	private boolean inspectionWord(String word){
		boolean inspWord=false;
		if(cityPlayList.size()>0){
			if(cityPlayList.contains(word.toLowerCase())){
				inspWord=false;
				System.out.println("This word was entered earlier");
			}else{
				if(lastChar!=word.charAt(0)){
					inspWord=false;
					System.out.println("The word must stert with the letter "+lastChar);
				}else{
					cityPlayList.add(word.toLowerCase());
					inspWord=true;
				}
			}	
		}else{
			if(cityPlayList.size()==0){
				cityPlayList.add(word.toLowerCase());
				inspWord=true;
			}
		}
		return inspWord;
	}
	
	private boolean opponentMove(String word){
		lastChar=this.getNormalLastChar(word);
		boolean oppMove=false;
		Iterator<String> it=cityList.iterator();
		while(it.hasNext()){
			String currentString=it.next();
			if((currentString.toLowerCase().charAt(0)==lastChar)&&(!(cityPlayList.contains(currentString.toLowerCase())))){
				cityPlayList.add(currentString.toLowerCase());
				System.out.println("Opponent city: --> "+currentString);
				lastChar=this.getNormalLastChar(currentString);
				oppMove=true;
				break;
			}
		}		
		return oppMove;
	}
	
	private char getNormalLastChar(String word){
		int poss=word.length()-1;
		boolean possState=false;
		while(!possState){
			if((word.toLowerCase().charAt(poss)=='ь')||(word.toLowerCase().charAt(poss)=='й')||(word.toLowerCase().charAt(poss)=='ы')||(word.toLowerCase().charAt(poss)=='ъ')){
				poss--;
			}else{
				possState=true;
			}
		}
		return word.toLowerCase().charAt(poss);
	}
	
	private void rememberCity(Set<String> cityPlay){
		if(cityPlay.size()>0){
		GameDaoImpl gameDao=new GameDaoImpl();
		Iterator<String> it=cityPlay.iterator();
		while(it.hasNext()){
			String currentString=it.next();
			currentString=currentString.substring(0,1).toUpperCase()+currentString.substring(1);
			if(!(cityList.contains(currentString))){
				gameDao.addToFileCity(currentString);
			}
		}
		}else{
			System.out.println("no cities");
		}
	}
}
