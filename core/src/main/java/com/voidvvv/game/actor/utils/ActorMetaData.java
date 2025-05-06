package com.voidvvv.game.actor.utils;

public class ActorMetaData {
//            <name>Bob</name>
//        <type>com.voidvvv.game.actor.Bob</type>
//
//        <rect-props>
//            <width>50.0</width>
//            <height>150.0</height>
//            <length>150.0</length>
//        </rect-props>
//        <battle-pros>
//            <hp>100</hp>
//            <mp>100</mp>
//            <attack>10</attack>
//            <defense>5</defense>
//        </battle-pros>
    private String name;
    private Class type;
    private RectProps rectProps;
    private BattleProps battleProps;

    public static class RectProps {
        private float width;
        private float height;
        private float length;

        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }

        public float getLength() {
            return length;
        }

        public void setLength(float length) {
            this.length = length;
        }
    }

    public static class BattleProps {
        private float hp;
        private float mp;
        private float attack;
        private float defense;

        public float getHp() {
            return hp;
        }

        public void setHp(float hp) {
            this.hp = hp;
        }

        public float getMp() {
            return mp;
        }

        public void setMp(float mp) {
            this.mp = mp;
        }

        public float getAttack() {
            return attack;
        }

        public void setAttack(float attack) {
            this.attack = attack;
        }

        public float getDefense() {
            return defense;
        }

        public void setDefense(float defense) {
            this.defense = defense;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public RectProps getRectProps() {
        return rectProps;
    }

    public void setRectProps(RectProps rectProps) {
        this.rectProps = rectProps;
    }

    public BattleProps getBattleProps() {
        return battleProps;
    }

    public void setBattleProps(BattleProps battleProps) {
        this.battleProps = battleProps;
    }
}
