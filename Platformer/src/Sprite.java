import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.image.Image;

public class Sprite {
	private String id;
	private String spriteSheet;
	private int width;
	private int height;
	private int xOffset;
	private int yOffset;
	private int frameNumber;

	private Sprite(Builder builder) {
		this.id = builder.id;
		this.spriteSheet = builder.spriteSheet;
		this.width = builder.width;
		this.height = builder.height;
		this.xOffset = builder.xOffset;
		this.yOffset = builder.yOffset;
		this.frameNumber = builder.frameNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(String spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getXOffset() {
		return xOffset;
	}

	public void setXOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	public void setYOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}

	@Override
	public String toString() {
		return "Sprite{" +
		", spriteSheet'" + spriteSheet + '\'' +
		", width=" + width +
		", height=" + height +
		", xOffset=" + xOffset +
		", yOffset=" + yOffset +
		", frameNumber=" + frameNumber +
		'}';
	}

	public static class Builder {
		private String id;
		private String spriteSheet;
		private int width;
		private int height;
		private int xOffset;
		private int yOffset;
		private int frameNumber;

		@JsonProperty("id")
		public Builder id (String id) {
			this.id = id;
			return this;
		}

		@JsonProperty("spriteSheet")
		public Builder spriteSheet (String spriteSheet) {
			this.spriteSheet = spriteSheet;
			return this;
		}

		@JsonProperty
		public Builder width (int width) {
			this.width = width;
			return this;
		}

		@JsonProperty("height")
		public Builder height (int height) {
			this.height = height;
			return this;
		}

		@JsonProperty("xOffset")
		public Builder xOffset (int xOffset) {
			this.xOffset = xOffset;
			return this;
		}

		@JsonProperty("yOffset")
		public Builder yOffset (int yOffset) {
			this.yOffset = yOffset;
			return this;
		}

		@JsonProperty("frameNumber")
		public Builder frameNumber (int frameNumber) {
			this.frameNumber = frameNumber;
			return this;
		}

		public Sprite build() {
			return new Sprite(this);
		}

	}
}