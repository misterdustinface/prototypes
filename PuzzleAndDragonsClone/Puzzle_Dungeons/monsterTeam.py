from monster import *
#import random

global MIN_TEAM_SIZE
MIN_TEAM_SIZE = 1
global MAX_TEAM_SIZE
MAX_TEAM_SIZE = 6

def MAKE_RANDOM_FULL_TEAM():
    monsterTeam = MonsterTeam()
    for i in range(MAX_TEAM_SIZE):
        monsterTeam.tryToAddMonsterToTeam(GET_RANDOM_MONSTER())
    return monsterTeam

class MonsterTeam():
    def __init__(self):
        self._TEAM = []

    def tryToAddMonsterToTeam(self, monster):
        if len(self._TEAM) < MAX_TEAM_SIZE:
            self._TEAM.append(monster)

    def removeMonsterFromTeam(self, monster):
        self._TEAM.remove(monster)

##    def sort(self):
##        self._TEAM.sort()

    def reverseList(self):
        self._TEAM.reverse()

    def getMonsters(self):
        return self._TEAM

    def getTeamSize(self):
        return len(self._TEAM)

    def getListCopy(self):
        return self._TEAM[:]

    def getListCopyNoDuplicates(self):
        val = []
        for element in self._TEAM:
            if element not in val:
                val.append(element)
        return val

    def getMonsterAtIndex(self, index):
        if index < MAX_TEAM_SIZE:
            return self._TEAM[index]

    def getTotalCombinedMaxHealth(self):
        MAX_HEALTH = 0
        for monster in self._TEAM:
            MAX_HEALTH += monster.getMaxHealth()
        return MAX_HEALTH
    def getTotalCombinedCurrentHealth(self):
        currentHealth = 0
        for monster in self._TEAM:
            currentHealth += monster.getCurrentHealth()
        return currentHealth

    def isAlive(self):
        return self.getTotalCombinedCurrentHealth() > 0

    def heal(self, amount):
        individualHealAmount = amount / self.getTeamSize()
        for monster in self._TEAM:
            monster.heal(individualHealAmount)

    def hurt(self, amount):
        individualHurtAmount = amount / self.getTeamSize()
        for monster in self._TEAM:
            monster.hurt(individualHurtAmount)

    def __repr__(self):
        return self.__str__()
    def __str__(self):
        string = ""
        for monster in self._TEAM:
            string += monster.toString()
        return string
