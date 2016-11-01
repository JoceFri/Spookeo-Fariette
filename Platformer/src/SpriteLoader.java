import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpriteLoader {
	
	public static Animation loadAnimation(String path, String character) {
		String source = ("src/Assets/Json/" + path + ".json");
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readTree(new File(source));
			if (rootNode.has(character)) {
				JsonNode charNode = rootNode.get(character);
				int xOffset = charNode.get("xOffset").asInt();
				int yOffset = charNode.get("yOffset").asInt();
				int width = charNode.get("width").asInt();
				int height = charNode.get("height").asInt();
				int column = charNode.get("frameNumber").asInt();
				Animation animation = new Animation.Builder()
						.xOffset(xOffset)
						.yOffset(yOffset)
						.width(width)
						.height(height)
						.frameNum(charNode.get("frameNumber").asInt())
						.duration(charNode.get("duration").asInt())
						.column(column)
						.build();
				String spriteSheet = charNode.get("spriteSheet").asText();
				ImageView imageView = loadSprite(spriteSheet, xOffset, yOffset, width, height, column);
				imageView.setViewport(new Rectangle2D(xOffset, yOffset, width, height));
				animation.setImageView(imageView);
				return animation;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static ImageView loadSprite(String path, int xOffset, int yOffset, int width, int height, int column) {
		final int x = xOffset + width * column;
		final int y = yOffset + height;
		Image image = new Image("Assets/Art/"+path+".png", x, y, false, false);
		return new ImageView(image);
	}
}
