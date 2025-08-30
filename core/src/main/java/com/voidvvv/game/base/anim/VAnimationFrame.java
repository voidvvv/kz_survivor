package com.voidvvv.game.base.anim;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.voidvvv.game.Main;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VRectBoundComponent;
import com.voidvvv.game.base.world.VWorld;
import com.voidvvv.game.base.world.VWorldActor;
import com.voidvvv.game.battle.DamageType;
import com.voidvvv.game.ecs.ComponentMapperUtil;
import com.voidvvv.game.ecs.components.BattleContextComponent;

import java.util.Collection;
import java.util.List;

public class VAnimationFrame {
    final Vector3 position = new Vector3(0, 0, 0);
    private float time;

    VWorldActor actor;

    AnimationDate animationDate;

    FrameData frameData;


    /**
     *         this.frameData = new FrameData();
     *         this.frameData.setDurationTime(durationTime);
     *         this.frameData.setTextureRegion(textureRegion);
     *         this.frameData.getAttackCheckRects().addAll(attackCheck);
     * @param frameData
     * @param actor
     */
    public VAnimationFrame(FrameData frameData, VWorldActor actor) {
        this.frameData = frameData;
        this.actor = actor;
        this.time = this.frameData.getDurationTime();

    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public VWorldActor getActor() {
        return actor;
    }

    public void setActor(VWorldActor actor) {
        this.actor = actor;
    }

    public AnimationDate getAnimationDate() {
        return animationDate;
    }

    public void setAnimationDate(AnimationDate animationDate) {
        this.animationDate = animationDate;
    }

    public FrameData getFrameData() {
        return frameData;
    }

    public void setFrameData(FrameData frameData) {
        this.frameData = frameData;
    }


    void update(float delta) {
        time -= delta;
        Entity entity = actor.getEntity();
        BattleContextComponent battleContextComponent = ComponentMapperUtil.battleContextComponentMapper.get(entity);

        VRectBoundComponent vRectBoundComponent = ComponentMapperUtil.rectBoundMapper.get(entity);
        position.set(vRectBoundComponent.getPosition());
        VWorld world = actor.getWorldContext().getWorld();
        for (AttackCheck attackCheckRect : frameData.attackCheckRects) {
            Rectangle rectangle = attackCheckRect.rectangle;
            Collection<VWorldActor> allVActor = world.findAllVActor(rectangle.x + position.x,
                rectangle.y + position.y,
                rectangle.width,
                rectangle.height);

            for (VWorldActor vWorldActor : allVActor) {
                if (animationDate.attackCheckObj.add(vWorldActor)) {
                    float v = attackCheckRect.damageValue(entity, vWorldActor.getEntity());
                    battleContextComponent.getBattleContext().createDamage(actor, vWorldActor, DamageType.valueOf(attackCheckRect.damageType), v);
                }
            }
        }

    };

    void render(Batch batch) {
        TextureRegion textureRegion = this.frameData.textureRegion;
        float x = position.x;
        float y = position.y;
        float z = position.z;

        float renderX = x + frameData.renderOffset.x;
        float renderY = y + frameData.renderOffset.y + z;

        batch.draw(textureRegion, renderX, renderY);
    }

    void reset () {
        this.time = this.frameData.getDurationTime();
    }

    boolean end() {
        return this.time == 0f; // default implementation, can be overridden
    };
}
