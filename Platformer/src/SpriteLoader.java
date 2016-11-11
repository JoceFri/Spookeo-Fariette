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
				ImageView imageView = loadSprite(spriteSheet);
				imageView.setViewport(new Rectangle2D(xOffset, yOffset, width, height));
				animation.setImageView(imageView);
				return animation;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Sprite loadSprite(String path, String name) {
		String source = ("src/Assets/Json/"+path+".json");
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readTree(new File(source));
			if(rootNode.has(name)) {
				JsonNode node = rootNode.get(name);
				String spriteSheet = node.get("spriteSheet").asText();
				int xOffset = node.get("xOffset").asInt();
				int yOffset = node.get("yOffset").asInt();
				int width = node.get("width").asInt();
				int height = node.get("height").asInt();
				Sprite sprite = new Sprite.Builder()
					.xOffset(xOffset)
					.yOffset(yOffset)
					.width(width)
					.height(height)
					.build();
				Image image = loadImage(spriteSheet, xOffset, yOffset, width, height);
				sprite.setSprite(image);
				return sprite;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Image loadImage(String path, int xOffset, int yOffset, int width, int height) {
		final int x = xOffset + width;
		final int y = yOffset + height;
		return new Image("Assets/Art/"+path+".png", x, y, false, false);
	}
	
	private static ImageView loadSprite(String path) {
		Image image = new Image("Assets/Animations/"+path+".png");
		return new ImageView(image);
	}
}
