package com.voidvvv.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class DesktopPlayer implements Player, InputProcessor {

    public static final DesktopMapping PLAYER_1_MAPPING = new DesktopMapping();

    public static final DesktopPlayer PLAYER_1 = new DesktopPlayer();

    static {
        PLAYER_1_MAPPING.up = Input.Keys.W;
        PLAYER_1_MAPPING.down = Input.Keys.S;
        PLAYER_1_MAPPING.left = Input.Keys.A;
        PLAYER_1_MAPPING.right = Input.Keys.D;
        PLAYER_1_MAPPING.skill1 = Input.Keys.Q;
        PLAYER_1_MAPPING.skill2 = Input.Keys.E;
        PLAYER_1_MAPPING.special = Input.Keys.SPACE;
        PLAYER_1.setMapping(PLAYER_1_MAPPING);
    }

    List<PlayerInput> list = new ArrayList<>();
    public final DesktopMapping mapping = new DesktopMapping();

    // dir
    Vector2 dir = new Vector2();
    boolean dirFlag = false;

    // skill 1
    boolean skill1Flag = false;

    // skill 2
    boolean skill2Flag = false;

    // special
    boolean specialFlag = false;

    @Override
    public List<PlayerInput> getPlayerInputList() {
        return list;
    }

    @Override
    public void update(float delta) {
        if (dirFlag) {
            dir.nor();
            for (PlayerInput input : list) {
                input.move(dir);
            }
            dirFlag = false;
        }

        if (skill1Flag) {
            for (PlayerInput input : list) {
                input.skill1();
            }
            skill1Flag = false;
        }
        if (skill2Flag) {
            for (PlayerInput input : list) {
                input.skill2();
            }
            skill2Flag = false;
        }

        if (specialFlag) {
            for (PlayerInput input : list) {
                input.special();
            }
            specialFlag = false;
        }
    }

    @Override
    public void addInput(PlayerInput input) {

    }

    public static class DesktopMapping {
        public int up = 0;
        public int down = 1;
        public int left = 2;
        public int right = 3;
        public int skill1 = 4;
        public int skill2 = 5;
        public int special = 6;

        public void cpy(DesktopMapping other) {
            this.up = other.up;
            this.down = other.down;
            this.left = other.left;
            this.right = other.right;
            this.skill1 = other.skill1;
            this.skill2 = other.skill2;
            this.special = other.special;
        }
    }

    public void setMapping (DesktopMapping desktopMapping) {
        this.mapping.cpy(desktopMapping);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == mapping.up) {
            dir.y = 1;
            dirFlag = true;
        }
        if (keycode == mapping.down) {
            dir.y = -1;
            dirFlag = true;
        }
        if (keycode == mapping.left) {
            dir.x = -1;
            dirFlag = true;
        }
        if (keycode == mapping.right) {
            dir.x = 1;
            dirFlag = true;
        }

        if (keycode == mapping.skill1) {
            skill1Flag = true;
        }

        if (keycode == mapping.skill2) {
            skill2Flag = true;
        }
        if (keycode == mapping.special) {
            specialFlag = true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == mapping.up) {
            if (Gdx.input.isKeyPressed(mapping.down)) {
                dir.y = -1;
            } else {
                dir.y = 0;
            }
            dirFlag = true;
        }
        if (keycode == mapping.down) {
            if (Gdx.input.isKeyPressed(mapping.up)) {
                dir.y = 1;
            } else {
                dir.y = 0;
            }
            dirFlag = true;
        }
        if (keycode == mapping.left) {
            if (Gdx.input.isKeyPressed(mapping.right)) {
                dir.x = 1;
            } else {
                dir.x = 0;
            }
            dirFlag = true;
        }
        if (keycode == mapping.right) {
            if (Gdx.input.isKeyPressed(mapping.left)) {
                dir.x = -1;
            } else {
                dir.x = 0;
            }
            dirFlag = true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
