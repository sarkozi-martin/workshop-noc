package sk.slsp.workshop.mazesolver;

import java.util.Objects;

public class Coordinates {
	private final Integer x;
	private final Integer y;
	
	
	public Coordinates(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	
	public Integer getX() {
		return x;
	}
	
	
	public Integer getY() {
		return y;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Coordinates that = (Coordinates) o;
		
		if (!Objects.equals(x, that.x)) {
			return false;
		}
		return Objects.equals(y, that.y);
	}
	
	
	@Override
	public int hashCode() {
		int result = x != null ? x.hashCode() : 0;
		result = 31 * result + (y != null ? y.hashCode() : 0);
		return result;
	}
}
