package com.voidvvv.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.world.VWorld;
import com.voidvvv.game.base.world.WorldContext;
import com.voidvvv.game.mode.VWorldContextGameMode;
import com.voidvvv.render.world.WorldRender;
import com.voidvvv.game.base.world.flat.FlatWorldConfig;
import com.voidvvv.game.base.world.flat.VFlatWorld;
import com.voidvvv.game.mode.GameMode;
import com.voidvvv.game.mode.SingleFlatWorldMode;
import com.voidvvv.game.utils.AssetConstants;
import com.voidvvv.render.world.VSimpleFlatWorldRender;

import java.util.function.Supplier;

public class FlatWorldLevel implements Level{
    public static interface LevelPreset {
        String getMapPath();
    }
    public static enum LevelPresetEnum implements LevelPreset{
        DEFAULT(AssetConstants.MAP_TEST_01),

        ;
        public final String mapPath;

        LevelPresetEnum(String mapPath) {
            this.mapPath = mapPath;
        }

        @Override
        public String getMapPath() {
            return this.mapPath;
        }
    }

    static final int DEFAULT_TIME = 500;

    public LevelPreset levelPreset;

    public Supplier<WorldRender> renderSupplier;

    public Supplier<VWorldContextGameMode> gameModeSupplier;

    Viewport worldViewPort;
    VWorldContextGameMode gameMode;
    WorldRender render;

    String name;

    /**
     * this is used to init world mode
     */
    FlatWorldConfig config;

    public FlatWorldLevel() {
    }

    @Override
    public GameMode getMode() {
        return gameMode;
    }

    @Override
    public void init() {
        if (levelPreset == null) {
            levelPreset = LevelPresetEnum.DEFAULT;
        }
        // viewport
        this.worldViewPort = Main.getInstance().getCameraManager().getWorldViewPort();
        loadAssets();
        loadConfig();
        if (gameModeSupplier != null) {
            gameMode = gameModeSupplier.get();
        } else {
            SingleFlatWorldMode localMode = new SingleFlatWorldMode(config);
            localMode.setTimeLimit(DEFAULT_TIME);
            localMode.setTimeLeft(DEFAULT_TIME);
            gameMode = localMode;
        }
        gameMode.init();
        // render
        if (renderSupplier != null) {
            this.render = renderSupplier.get();
        } else {
            VSimpleFlatWorldRender localRender = new VSimpleFlatWorldRender(mapAsset, worldViewPort);
            this.render = localRender;
        }

        render.init();
    }
    TiledMap mapAsset = null;
    private void loadAssets() {
        Main.getInstance().getAssetManager().load(levelPreset.getMapPath(), TiledMap.class);
        Main.getInstance().getAssetManager().finishLoading();
    }

    private FlatWorldConfig loadConfig() {
        this.config = new FlatWorldConfig();
        mapAsset = Main.getInstance().getAssetManager().get(levelPreset.getMapPath());
        MapLayer mapLayer = mapAsset.getLayers().get("main_obj");
        RectangleMapObject mapObj1 = (RectangleMapObject) mapLayer.getObjects().get("birthPlace");

        config.birthPlace.set(mapObj1.getRectangle().x + mapLayer.getOffsetX(),
                mapObj1.getRectangle().y - mapLayer.getOffsetY());

        TiledMapTileLayer mainLayer = (TiledMapTileLayer) mapAsset.getLayers().get("main");
        config.boundingBox.set(mainLayer.getOffsetX(), -mainLayer.getOffsetY(), mainLayer.getWidth() * mainLayer.getTileWidth(), mainLayer.getHeight() * mainLayer.getTileHeight());
        Gdx.app.log("FlatWorldLevel", "boundingBox: " + config.boundingBox);
        return config;
    }


    boolean needInitMode = true;
    @Override
    public void update(float delta) {
        if (gameMode != null) {
            gameMode.update(delta);
        }
    }

    @Override
    public void render() {
        VWorld world = gameMode.getContext().getWorld();
        render.render(world);
    }

    @Override
    public void dispose() {
        gameMode.dispose();
        render.dispose();
        render = null;
        worldViewPort = null;
        config = null;
        mapAsset.dispose();
        mapAsset = null;
    }

    @Override
    public void setMode(GameMode mode) {
        if (mode instanceof SingleFlatWorldMode) {
            gameMode = (SingleFlatWorldMode) mode;
        }
    }

    @Override
    public void setLevelName(String name) {
        this.name = name;
    }

    @Override
    public String getLevelName() {
        return this.name;
    }
}
