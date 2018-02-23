package HumorBot;

public class Lobby {
	private String gameNum;
	private String lobbyHost;
	private int playerCount;
	private int maxPlayers;
	private int spectatorCount;
	private int maxSpectators;
	private String lobbyStatus;
	public Lobby(String gameNum, String lobbyHost, String playerCount, String maxPlayers, String spectatorCount, String maxSpectatros, String lobbyStatus) {
		this.gameNum = gameNum;
		this.lobbyHost = lobbyHost;
		this.playerCount = Integer.parseInt(playerCount);
		this.maxPlayers = Integer.parseInt(maxPlayers);
		this.spectatorCount = Integer.parseInt(spectatorCount);
		this.maxSpectators = Integer.parseInt(maxSpectatros);
		this.lobbyStatus = lobbyStatus;
	}
	
	public String getGameNum() {
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
	
	public String getLobbyStatus() {
		return this.lobbyStatus;
	}
}
