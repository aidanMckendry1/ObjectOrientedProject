package part2;

public enum Status {
		
	VENDING_MODE(1), SERVICE_MODE(2);	
	private int statusValue;
	private String[] statuses = {"Vending mode.", "Service mode."};
	
	private Status(int mode) {
		this.statusValue = mode;
	}
	
	public String getStatus() {
		return statuses [statusValue - 1];
	}
}
