import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animation extends Transition{
	private ImageView imageView;
	private Duration duration;
	private int xOffset;
	private int yOffset;
	private int width;
	private int height;
	private int frameNum;
	private int column;
	private int lastIndex;
	
	public Animation(Builder builder) {
		this.xOffset = builder.xOffset;
		this.yOffset = builder.yOffset;
		this.width = builder.width;
		this.height = builder.height;
		this.frameNum = builder.frameNum;
		this.column = builder.column;
		this.duration = Duration.millis(builder.duration);
		setCycleDuration(duration);
		setInterpolator(Interpolator.LINEAR);
	}
	
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	
	public ImageView getImageView() {
		return imageView;
	}
	
	@Override
	protected void interpolate(double k) {
		final int index = Math.min((int)Math.floor(k * frameNum), frameNum - 1);
		if(index != lastIndex) {
			final int x = (index % frameNum) * width + xOffset;
			final int y = (index / frameNum) * height + yOffset;
			imageView.setViewport(new Rectangle2D(x, y, width, height));
			lastIndex = index;
		}
	}
	
	@Override
	public String toString() {
		return "Animation{" +
		"duration=" + duration +
		", xOffset=" + xOffset +
		", yOffset=" + yOffset +
		", width=" + width +
		", height=" + height +
		", frameNum=" + frameNum +
		", column=" + column +
		", imageView'" + imageView.toString() + '\'' +
		'}';
	}
	
	public static class Builder {
		private int duration;
		private int xOffset;
		private int yOffset;
		private int width;
		private int height;
		private int frameNum;
		private int column;
		
		@JsonProperty("xOffset")
		public Builder xOffset(int xOffset) {
			this.xOffset = xOffset;
			return this;
		}
		
		@JsonProperty("yOffset")
		public Builder yOffset(int yOffset) {
			this.yOffset = yOffset;
			return this;
		}
		
		@JsonProperty("width")
		public Builder width(int width) {
			this.width = width;
			return this;
		}
		
		@JsonProperty("height")
		public Builder height(int height) {
			this.height = height;
			return this;
		}
		
		@JsonProperty("frameNumber")
		public Builder frameNum(int frameNum) {
			this.frameNum = frameNum;
			return this;
		}
		
		@JsonProperty("frameNumber")
		public Builder column(int column) {
			this.column = column;
			return this;
		}
		
		@JsonProperty("duration")
		public Builder duration(int duration) {
			this.duration = duration;
			return this;
		}
		
		public Animation build() {
			return new Animation(this);
		}
	}
}
