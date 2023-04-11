package blebdapleb.arsenic.arsenic.util.shader;

import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class ArsenicCoreShaders {
	
	private static final ShaderProgram COLOR_OVERLAY_SHADER;
	
	public static ShaderProgram getColorOverlayShader() {
		return COLOR_OVERLAY_SHADER;
	}
	
	static {
		try {
			COLOR_OVERLAY_SHADER = ShaderLoader.load(VertexFormats.POSITION_COLOR_TEXTURE, new Identifier("arsenic",
					"color_overlay"));
		} catch (IOException e) {
			throw new RuntimeException("Failed to initialize Arsenic core shaders", e);
		}
	}

}
