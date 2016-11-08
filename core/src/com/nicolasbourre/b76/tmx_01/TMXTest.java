package com.nicolasbourre.b76.tmx_01;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;



public class TMXTest extends ApplicationAdapter {
	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
    OrthoCamController orthoCamController;
    CameraInputController cic;

	private AssetManager assetManager;
	private BitmapFont font;
	private SpriteBatch batch;

	@Override
	public void create () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w/h) * 10, 10);
        camera.zoom = 2;
        camera.update();

        orthoCamController = new OrthoCamController(camera);
        Gdx.input.setInputProcessor(orthoCamController);

        font = new BitmapFont();

		batch = new SpriteBatch();

        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("monde_01.tmx", TiledMap.class);
        assetManager.finishLoading();
        map = assetManager.get("monde_01.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f / 16f);

	}

    @Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        orthoCamController.update();
        camera.update();
        renderer.setView(camera);
        renderer.render();
		batch.begin();
        font.draw(batch, "FPS : " + Gdx.graphics.getFramesPerSecond(), 10, 30);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
