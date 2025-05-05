package com.voidvvv.game.battle;

public class DefaultBattleComponent implements BattleComponent {
    private float hp;
    BaseBattleFloat maxHp = new BaseBattleFloat();
    private float mp;
    BaseBattleFloat maxMap = new BaseBattleFloat();

    BaseBattleFloat attack = new BaseBattleFloat();
    BaseBattleFloat armor = new BaseBattleFloat();

    @Override
    public void init() {

    }

    public void init(float hp, float mp, float maxHp, float maxMp, float attack, float armor) {
        this.hp = hp;
        this.mp = mp;
        this.maxHp.originVal = maxHp;
        this.maxMap.originVal = maxMp;
        this.attack.originVal = attack;
        this.armor.originVal = armor;

        this.maxHp.finalVal = maxHp;
        this.maxMap.finalVal = maxMp;
        this.attack.finalVal = attack;
        this.armor.finalVal = armor;
    }

    @Override
    public void update(float delta) {
        maxHp.update();
        maxMap.update();
        attack.update(true);
        armor.update(true);
        if (hp > maxHp.finalVal) {
            hp = maxHp.finalVal;
        }
        if (mp > maxMap.finalVal) {
            mp = maxMap.finalVal;
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public float getHp() {
        return hp;
    }

    @Override
    public float getMp() {
        return mp;
    }

    @Override
    public float changeHp(float hpDelta) {
        return hp += hpDelta;
    }

    @Override
    public float changeMp(float mpDelta) {
        return mp += mpDelta;
    }

    @Override
    public BaseBattleFloat getMaxHp() {
        return maxHp;
    }

    @Override
    public BaseBattleFloat changeMaxHp(BattleFloatDelta maxHpDelta) {
        maxHp.addDelta(maxHpDelta);
        return maxHp;
    }

    @Override
    public BaseBattleFloat getMaxMp() {
        return maxMap;
    }

    @Override
    public BaseBattleFloat changeMaxMp(BattleFloatDelta maxMpDelta) {
        maxMap.addDelta(maxMpDelta);
        return maxMap;
    }

    @Override
    public BaseBattleFloat getAttack() {
        return attack;
    }

    @Override
    public BaseBattleFloat changeAttack(BattleFloatDelta attackDelta) {
        attack.addDelta(attackDelta);
        return attack;
    }

    @Override
    public BaseBattleFloat getArmor() {
        return armor;
    }

    @Override
    public BaseBattleFloat changeArmor(BattleFloatDelta armorDelta) {
        armor.addDelta(armorDelta);
        return armor;
    }

    @Override
    public void apply(BattleEvent battleEvent) {

    }
}
