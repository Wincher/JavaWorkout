package test;

public class ErrorMessage {
	
	public static void main(String[] args) {
		System.out.println(ErrorMessage.build("a", "bbb").generateJson());
	}
	private String type;
	private String message;
	
	public ErrorMessage() {
	
	}
	
	public ErrorMessage(String type, String message) {
		this.type = type;
		this.message = message;
	}
	
	public static ErrorMessage build(String type, String message) {
		return new ErrorMessage(type, message);
	}
	
	public String generateJson() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\"type\":\"");
		stringBuilder.append(this.type);
		stringBuilder.append("\",\"message\":\"");
		stringBuilder.append(this.message);
		stringBuilder.append("\"}");
		return stringBuilder.toString();
	}
}
