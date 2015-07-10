from orbs import *
import random
import copy

class MonsterStatGrowth():
    def __init__(self, currentLevel, MAX_LEVEL, MAX_ATTACK, MAX_HEALTH, MAX_RECOVER):
        self._currentLevel = currentLevel
        self._MAX_LEVEL = MAX_LEVEL
        self._MAX_ATTACK = MAX_ATTACK
        self._MAX_HEALTH = MAX_HEALTH
        self._MAX_RECOVER = MAX_RECOVER

        self._attack  = 0
        self._health  = 0
        self._recover = 0
        
        self.defineAttack()
        self.defineHealth()
        self.defineRecover()

    def increaseLevel(self):
        if self._currentLevel < self._MAX_LEVEL:
            self._currentLevel += 1
            self.defineAttack()
            self.defineHealth()
            self.defineRecover()

    def setLevel(self, level):
        self._currentLevel = level
        self.defineAttack()
        self.defineHealth()
        self.defineRecover()

    def defineAttack(self):
        self._attack = (self._currentLevel / self._MAX_LEVEL) * self._MAX_ATTACK
    def defineHealth(self):
        self._health = (self._currentLevel / self._MAX_LEVEL) * self._MAX_HEALTH
    def defineRecover(self):
        self._recover = (self._currentLevel / self._MAX_LEVEL) * self._MAX_RECOVER

    def getAttack(self):
        return self._attack
    def getHealth(self):
        return self._health
    def getRecover(self):
        return self._recover
    def getLevel(self):
        return self._currentLevel
    

class MonsterStats():
    def __init__(self, currentLevel, statGrowth):
        self.statGrowth = statGrowth
        self.statGrowth.setLevel(currentLevel)
        self._xp = 0
        
    def getLevel(self):
        return self.statGrowth.getLevel()
    def addXp(self, amount):
        if self._xp + amount > self.xpNeededToGetToNextLevel():
            self._xp = (self._xp + amount) - self.xpNeededToGetToNextLevel()
            self.statGrowth.increaseLevel()
    def xpNeededToGetToNextLevel(self):
        return 100 * self._currentLevel # TODO

    def getCurrentXp(self):
        return self._xp

class MonsterType():
    def __init__(self, orbTypeA, orbTypeB, fightingStyle):
        self._typeA = orbTypeA
        self._typeB = orbTypeB
        self._fightingStyle = fightingStyle
    def typeA(self):
        return self._typeA
    def typeB(self):
        return self._typeB
    def style(self):
        return self._fightingStyle
    
class Monster():
    def __init__(self, name, monsterType, monsterStats, countdown):
        self._name           = name
        self._monsterType    = monsterType
        self._monsterStats   = monsterStats
        self._currentHealth  = self.getMaxHealth()
        self._INITIAL_TURN_STEP = countdown
        self._turnStep = self._INITIAL_TURN_STEP
    def getName(self):
        return self._name
    def getMaxHealth(self):
        return self._monsterStats.statGrowth.getHealth()
    def getCurrentHealth(self):
        return self._currentHealth
    def getHealthPercentage(self):
        return self.getCurrentHealth()/self.getMaxHealth()
    def isDead(self):
        return self._currentHealth <= 0
    def isAlive(self):
        return self._currentHealth > 0
    def getAttack(self):
        return self._monsterStats.statGrowth.getAttack()
    def getRecover(self):
        return self._monsterStats.statGrowth.getRecover()
    def heal(self, amount):
        self._currentHealth += amount
        if self._currentHealth > self.getMaxHealth():
            self._currentHealth = self.getMaxHealth()
    def damage(self, amount):
        self._currentHealth -= amount
        if self._currentHealth < 0:
            self._currentHealth = 0
    def hurt(self, amount):
        self.damage(amount)
    def isAttackTurn(self):
        return self._turnStep == 1
    def getTurnStep(self):
        return self._turnStep
    def turnStep(self):
        self._turnStep -= 1
        if self._turnStep < 1:
            self._turnStep = self._INITIAL_TURN_STEP
    def typeA(self):
        return self._monsterType.typeA()
    def typeB(self):
        return self._monsterType.typeB()
    def getCurrentLevel(self):
        return self._monsterStats.getLevel()
    def toString(self):
        return self.__str__()
    def __repr__(self):
        return self.__str__()
    def __str__(self):
        string  = "\n"
        string += "---------------------------" + "\n"
        string += " MONSTER: " + self._name + "\n"
        string += "---------------------------" + "\n"
        string += "   LEVEL: " + str(self.getCurrentLevel()) + "\n"
        string += "    TYPE: " + str(ORB_NAMES[self._monsterType.typeA()])
        if self._monsterType.typeB() != EMPTY:
            string += ", " + str(ORB_NAMES[self._monsterType.typeB()])
        string += "\n"
        string += "      HP: " + str(self.getCurrentHealth()) + " / " + str(self.getMaxHealth()) + "\n"
        string += "     ATK: " + str(self.getAttack()) + "\n";
        string += "     RCV: " + str(self.getRecover()) + "\n";
        return string

########################################################################
# MONSTER TYPES #
HEALER   = "Healer"
DRAGON   = "Dragon"
ATTACKER = "Attacker"
DEVIL    = "Devil"
BALANCED = "Balanced"
PHYSICAL = "Physical"
GOD      = "God"

#global STAT_GROWTH_MAP
STAT_GROWTH_MAP = {"Standard":              MonsterStatGrowth(1, 20,  20,  2000,  50),
                   "Standard Health Nut":   MonsterStatGrowth(1, 20,  20,  3000,  100),
                   "Powerful":              MonsterStatGrowth(1, 50,  50,  5000,  250),
                   "Diety":                 MonsterStatGrowth(1, 100, 1000,100000,5000),
                   "Simple Balanced":       MonsterStatGrowth(1, 10,  14,  1400,  140)}

def getStatGrowthCopy(key):
    return copy.deepcopy(STAT_GROWTH_MAP[key])

global MONSTER_LIST
MONSTER_LIST = [Monster("Richard Pattis",   MonsterType(PURPLE, PURPLE, DEVIL),     MonsterStats(3, getStatGrowthCopy('Powerful')), 4),
                Monster("Demon",            MonsterType(PURPLE, RED,    DEVIL),     MonsterStats(5, getStatGrowthCopy('Powerful')), 3),
                Monster("Aunt Eater",       MonsterType(PURPLE, NONE,   ATTACKER),  MonsterStats(2, getStatGrowthCopy('Standard')), 2),
                Monster("Bunny",            MonsterType(PURPLE, NONE,   BALANCED),  MonsterStats(1, getStatGrowthCopy('Simple Balanced')), 1),
                Monster("The Pattis Node",  MonsterType(YELLOW, PURPLE, GOD),       MonsterStats(10,getStatGrowthCopy('Diety')), 6),
                Monster("Ghost",            MonsterType(YELLOW, PURPLE, BALANCED),  MonsterStats(3, getStatGrowthCopy('Standard')), 2),
                Monster("Light Bulb",       MonsterType(YELLOW, YELLOW, ATTACKER),  MonsterStats(3, getStatGrowthCopy('Standard')), 2),
                Monster("The Green Beast",  MonsterType(GREEN,  YELLOW, GOD),       MonsterStats(15,getStatGrowthCopy('Powerful')), 4),
                Monster("Green Dino",       MonsterType(GREEN,  BLUE,   BALANCED),  MonsterStats(1, getStatGrowthCopy('Standard')), 2),
                Monster("Plant",            MonsterType(GREEN,    NONE,   HEALER),  MonsterStats(1, getStatGrowthCopy('Standard Health Nut')), 2),
                Monster("Snake",            MonsterType(GREEN,    NONE,   BALANCED),  MonsterStats(1, getStatGrowthCopy('Simple Balanced')), 1),
                Monster("Phoenix",          MonsterType(RED,    NONE,   BALANCED),  MonsterStats(1, getStatGrowthCopy('Simple Balanced')), 1),
                Monster("Pele",             MonsterType(RED,    RED,    GOD),       MonsterStats(20,getStatGrowthCopy('Powerful')), 4),
                Monster("Burning House",    MonsterType(RED,    PURPLE, ATTACKER),  MonsterStats(2, getStatGrowthCopy('Standard')), 2),
                Monster("Dangerous Billy",  MonsterType(RED,    NONE,   PHYSICAL),  MonsterStats(3, getStatGrowthCopy('Simple Balanced')),1),
                Monster("Pet Dragon of Zeus",  MonsterType(BLUE,   YELLOW, GOD),       MonsterStats(20,getStatGrowthCopy('Powerful')), 4),
                Monster("Cloud",            MonsterType(BLUE,   BLUE,   PHYSICAL),  MonsterStats(4, getStatGrowthCopy('Standard Health Nut')), 2),
                Monster("Blue Dino",        MonsterType(BLUE,   NONE,   BALANCED),  MonsterStats(2, getStatGrowthCopy('Simple Balanced')), 1)]

global MONSTER_IMAGE_DIRECTORY
MONSTER_IMAGE_DIRECTORY = "monsters"
global MONSTER_IMAGE_LIST
MONSTER_IMAGE_LIST = {"Richard Pattis":"pattis.jpg", "Demon":"demon.jpg", "Aunt Eater":"aunteater.jpg", "Bunny":"bunny.jpg",
                      "The Pattis Node":"Spore_plant_pod.png", "Ghost":"ghost.jpg", "Light Bulb": "light_bulb.jpg",
                      "The Green Beast":"greenBeast.png", "Green Dino":"greenDino.png", "Plant":"plant.jpg", "Snake":"snake.jpg",
                      "Phoenix":"firebird1.png", "Pele":"firebird2.png", "Burning House":"house.jpg", "Dangerous Billy":"mustache.jpg",
                      "Pet Dragon of Zeus":"blueDragon.png", "Cloud":"cloud.jpg", "Blue Dino":"blueDino.png"}
global MONSTER_ICON_IMAGE_LIST
MONSTER_ICON_IMAGE_LIST = {}

def GET_RANDOM_MONSTER():
    return copy.deepcopy(MONSTER_LIST[random.randint(0, len(MONSTER_LIST)-1)])
def GET_RANDOM_MONSTER_OF_TYPE(orbType):
    currentMonster = MONSTER_LIST[random.randint(0, len(MONSTER_LIST)-1)]
    while currentMonster.typeA() != orbType:
        currentMonster = MONSTER_LIST[random.randint(0, len(MONSTER_LIST)-1)]
    return copy.deepcopy(currentMonster)

def ADD_MONSTER_IMAGES_TO_IMAGE_LOADER(loader):
    for monster in MONSTER_LIST:
        loader.load(MONSTER_IMAGE_DIRECTORY, MONSTER_IMAGE_LIST[monster.getName()], monster.getName())
        loader.ImageMap[monster.getName()] = pygame.transform.scale(loader.get(monster.getName()), (ENEMY_WIDTH, ENEMY_HEIGHT))
    
########################################################################

if __name__ == "__main__":

    for x in MONSTER_LIST:
        print(x.toString())
        #x.damage(35)
        #print(x.toString())
    
