package com.voidvvv.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class DesktopPlayer implements Player, InputProcessor {

    public static final DesktopMapping PLAYER_1_MAPPING = new DesktopMapping();

    public static final DesktopPlayer PLAYER_1 = new DesktopPlayer();

    static {
        PLAYER_1_MAPPING.up.key = Input.Keys.W;
        PLAYER_1_MAPPING.down.key = Input.Keys.S;
        PLAYER_1_MAPPING.left.key = Input.Keys.A;
        PLAYER_1_MAPPING.right.key = Input.Keys.D;
        PLAYER_1_MAPPING.skill1.mouse(Input.Buttons.LEFT);
        PLAYER_1_MAPPING.skill2.key = Input.Keys.E;
        PLAYER_1_MAPPING.special.key = Input.Keys.SPACE;
        PLAYER_1.setMapping(PLAYER_1_MAPPING);
    }

    List<PlayerInput> list = new ArrayList<>();
    public DesktopMapping mapping;

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
        list.add(input);
    }

    @Override
    public void removeInput(PlayerInput input) {
        list.remove(input);
    }

    public static class DesktopMapping {
        public FunctionMapping up = new FunctionMapping(0);
        public FunctionMapping down = new FunctionMapping(1);
        public FunctionMapping left = new FunctionMapping(2);
        public FunctionMapping right = new FunctionMapping(2);
        public FunctionMapping skill1 = new FunctionMapping(2);
        public FunctionMapping skill2 = new FunctionMapping(2);
        public FunctionMapping special = new FunctionMapping(2);

        public void cpy(DesktopMapping other) {
            this.up.cpy(other.up);
            this.down.cpy(other.down);
            this.left.cpy(other.left);
            this.right.cpy(other.right);
            this.skill1.cpy(other.skill1);
            this.skill2.cpy(other.skill2);
            this.special.cpy(other.special);
        }
    }

    public static class FunctionMapping {
        public static final int KEY_BOARD = 0;
        public static final int MOUSE = 1;

        public int type;

        public int key;

        public FunctionMapping(int type, int key) {
            this.type = type;
            this.key = key;
        }

        public FunctionMapping(int key) {
            this.key = key;
        }

        public void key (int key) {
            this.type = KEY_BOARD;
            this.key = key;
        }

        public void mouse (int key) {
            this.type = MOUSE;
            this.key = key;
        }

        public void cpy (FunctionMapping other) {
            this.type = other.type;
            this.key = other.key;
        }

        public boolean isPressed () {
            boolean re = false;
            if (type == KEY_BOARD) {
                return Gdx.input.isKeyPressed(key);
            } else if (type == MOUSE) {
                return Gdx.input.isButtonPressed(key);
            }
            return false;
        }
    }

    public void setMapping (DesktopMapping desktopMapping) {
        this.mapping= desktopMapping;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (mapping.up.type == FunctionMapping.KEY_BOARD &&keycode == mapping.up.key) {
            dir.y = 1;
            dirFlag = true;
        }
        if (mapping.down.type == FunctionMapping.KEY_BOARD &&keycode == mapping.down.key) {
            dir.y = -1;
            dirFlag = true;
        }
        if (mapping.left.type == FunctionMapping.KEY_BOARD &&keycode == mapping.left.key) {
            dir.x = -1;
            dirFlag = true;
        }
        if (mapping.right.type == FunctionMapping.KEY_BOARD &&keycode == mapping.right.key) {
            dir.x = 1;
            dirFlag = true;
        }

        if (mapping.skill1.type == FunctionMapping.KEY_BOARD &&keycode == mapping.skill1.key) {
            skill1Flag = true;
        }

        if (mapping.skill2.type == FunctionMapping.KEY_BOARD &&keycode == mapping.skill2.key) {
            skill2Flag = true;
        }
        if (mapping.special.type == FunctionMapping.KEY_BOARD &&keycode == mapping.special.key) {
            specialFlag = true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (mapping.up.type == FunctionMapping.KEY_BOARD && keycode == mapping.up.key) {
            if (mapping.down.isPressed()) {
                dir.y = -1;
            } else {
                dir.y = 0;
            }
            dirFlag = true;
        }
        if (mapping.down.type == FunctionMapping.KEY_BOARD &&keycode == mapping.down.key) {
            if (mapping.up.isPressed()) {
                dir.y = 1;
            } else {
                dir.y = 0;
            }
            dirFlag = true;
        }
        if (mapping.left.type == FunctionMapping.KEY_BOARD &&keycode == mapping.left.key) {
            if (mapping.right.isPressed()) {
                dir.x = 1;
            } else {
                dir.x = 0;
            }
            dirFlag = true;
        }
        if (mapping.right.type == FunctionMapping.KEY_BOARD &&keycode == mapping.right.key) {
            if (mapping.left.isPressed()) {
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
        if (pointer != 0) {
            return false;
        }
        if (mapping.up.type == FunctionMapping.MOUSE &&button == mapping.up.key) {
            dir.y = 1;
            dirFlag = true;
        }
        if (mapping.down.type == FunctionMapping.MOUSE &&button == mapping.down.key) {
            dir.y = -1;
            dirFlag = true;
        }
        if (mapping.left.type == FunctionMapping.MOUSE &&button == mapping.left.key) {
            dir.x = -1;
            dirFlag = true;
        }
        if (mapping.right.type == FunctionMapping.MOUSE &&button == mapping.right.key) {
            dir.x = 1;
            dirFlag = true;
        }

        if (mapping.skill1.type == FunctionMapping.MOUSE &&button == mapping.skill1.key) {
            skill1Flag = true;
        }

        if (mapping.skill2.type == FunctionMapping.MOUSE &&button == mapping.skill2.key) {
            skill2Flag = true;
        }
        if (mapping.special.type == FunctionMapping.MOUSE &&button == mapping.special.key) {
            specialFlag = true;
        }

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
