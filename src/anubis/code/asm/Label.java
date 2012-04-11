package anubis.code.asm;

public class Label {
	private final Object marker;
	
	public Label(Object marker) {
		this.marker = marker;
	}
	
	public Object getMarker() {
		return marker;
	}
}
