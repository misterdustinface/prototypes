from orbs import *

def GET_MONSTER_TEAM_BATTLE_POWER(pointMap, numberOfCombos, team):
    damageMap = {}
    for monster in team.getListCopyNoDuplicates():
        damageMap[monster] = _CALCULATE_DAMAGE_FOR_MONSTER(pointMap, monster)
        damageMap[monster] = _COMBO_MULTIPLIER(numberOfCombos, damageMap[monster])

    return damageMap

def GET_HEAL_AMOUNT(pointMap, numberOfCombos, totalMaxHealth):
    if HEAL in pointMap:
        return totalMaxHealth * (_COMBO_MULTIPLIER(numberOfCombos, pointMap[HEAL]) / 60)
    else:
        return 0

def ATTACK_MONSTER(team, damageMap, defendingMonster):
    for monster in team.getListCopy():
        damageToInflict = _ADJUST_DAMAGE_BASED_ON_ENEMY(damageMap[monster], monster.typeA(), monster.typeB(), defendingMonster.typeA())
        defendingMonster.damage(damageToInflict)
        if defendingMonster.isDead():
            break

def ENEMY_ATTACK_TEAM(enemy, team):
    damageToInflict = 0
    for monster in team.getListCopy():
        damageToInflict += _ADJUST_DAMAGE_BASED_ON_ENEMY(enemy.getAttack(), enemy.typeA(), enemy.typeB(), monster.typeA())
    team.hurt(damageToInflict * enemy.getCurrentLevel())

#def ATTACK_TEAM(team, damageMap, defendingTeam):
#    pass

def _COMBO_MULTIPLIER(numberOfCombos, damage):
    newDamage = damage
    for i in range(1,numberOfCombos):
        newDamage += damage/4
        
    return newDamage

def _CALCULATE_DAMAGE_FOR_MONSTER(pointMap, monster):
    damage = 0
    if pointMap[monster.typeA()] >= 3:
        damage += monster.getAttack() * (pointMap[monster.typeA()]/3)
    if monster.typeB() != NONE and pointMap[monster.typeB()] >= 3:
        damage += monster.getAttack() * (pointMap[monster.typeB()]/6)
    
    return damage

def _ADJUST_DAMAGE_BASED_ON_ENEMY(damage, attackTypeA, attackTypeB, defenderType):
    if   defenderType == RED:
        if attackTypeA == BLUE:
            damage *= 1.5
        if attackTypeB == BLUE:
            damage *= 1.125
        if attackTypeA == GREEN or attackTypeB == GREEN:
            damage *= 0.5
    elif defenderType == BLUE:
        if attackTypeA == GREEN:
            damage *= 1.5
        if attackTypeB == GREEN:
            damage *= 1.125
        if attackTypeA == RED or attackTypeB == RED:
            damage *= 0.5
    elif defenderType == GREEN:
        if attackTypeA == RED:
            damage *= 1.5
        if attackTypeB == RED:
            damage *= 1.125
        if attackTypeA == BLUE or attackTypeB == BLUE:
            damage *= 0.5
    elif defenderType == YELLOW:
        if attackTypeA == PURPLE:
            damage *= 1.5
        if attackTypeB == PURPLE:
            damage *= 1.125
        if attackTypeA == YELLOW or attackTypeB == YELLOW:
            damage *= 0.75
    elif defenderType == PURPLE:
        if attackTypeA == YELLOW:
            damage *= 1.5
        if attackTypeB == YELLOW:
            damage *= 1.125
        if attackTypeA == PURPLE or attackTypeB == PURPLE:
            damage *= 0.75

    return damage
