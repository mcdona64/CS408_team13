package HumorBot;

public class Lobby {
	private int gameNum;
	private String lobbyHost;
	private int playerCount;
	private int maxPlayers;
	private int spectatorCount;
	private int maxSpectators;
	private boolean lobbyStatus;
	private boolean password;
	public Lobby(int gameNum, String lobbyHost, int playerCount, int maxPlayers, int spectatorCount, int maxSpectatros, boolean lobbyStatus, boolean password) {
		this.gameNum = gameNum;
		this.lobbyHost = lobbyHost;
		this.playerCount = playerCount;
		this.maxPlayers = maxPlayers;
		this.spectatorCount = spectatorCount;
		this.maxSpectators = maxSpectatros;
		this.lobbyStatus = lobbyStatus;
		this.password = password;
	}
	
	public int getGameNum() {
		return this.gameNum;
	}
	
	public String getLobbyHost() {
		return this.lobbyHost;
	}
	
	public int getPlayerCount() {
		return this.playerCount;
	}
	
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
	public int getSpectatorCount() {
		return this.spectatorCount;
	}
	
	public int getMaxSpectators() {
		return this.maxSpectators;
	}
	
	public boolean getLobbyStatus() {
		return this.lobbyStatus;
	}

	public boolean hasPassword() { return this.password; }

	public void print(){
		System.out.println("Game Number: " + this.gameNum);
		System.out.println("Lobby Host: " + this.lobbyHost);
		System.out.println("Player Count: " + this.playerCount);
		System.out.println("Max Players: " + this.maxPlayers);
		System.out.println("Spectator Count: " + this.spectatorCount);
		System.out.println("Max Spectators: " + this.maxSpectators);
		if(this.lobbyStatus) {
			System.out.println("Lobby Status: In Progress");
		} else {
			System.out.println("Lobby Status: Not started");
		}
		if(this.password){
			System.out.println("Has Password");
		} else {
			System.out.println("Does not have password");
		}

	}
}
