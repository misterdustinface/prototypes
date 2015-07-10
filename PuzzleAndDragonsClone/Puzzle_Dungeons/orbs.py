import random
from globalVariables import *
from imgLoader import *
import pygame
from pygame.locals import *

### ORB TYPES ###
# Red, Blue, Green, Yellow, Purple, Heal, Block, White (all)
RED     = "R" #0
BLUE    = "B" #1
GREEN   = "G" #2
YELLOW  = "Y" #3
PURPLE  = "P" #4
HEAL    = "H" #5
#= SPECIAL =#
WHITE   = "W" #6
BLOCK   = "X" #7
NONE    = " "
EMPTY   = " "

ORB_NAMES           = {'R':"Fire",      'B':"Water",    'G':"Wind",
                       'Y':"Light",     'P':"Dark",     'H':"Heal",
                       'X':"Block",     'W':"Magic"}
ORB_IMAGE_FILE_MAP  = {'R':"red.png",   'B':"blue.png", 'G':"green.png",
                       'Y':"yellow.png",'P':"purple.png",'H':"heal.png",
                       'X':"block.png", 'W':"white.png"}
ORBS                = [RED,BLUE,GREEN,YELLOW,PURPLE,HEAL,WHITE,BLOCK];

ORB_TO_COLOR_MAP        = {'R':(196,2,51,0),     'B':(0,135,189,0),  'G':(0,159,107,0),
                           'Y':(255,211,0,0),   'P':(105,53,156,0),'H':(255,0,255,0),
                           'W':(240,255,240,0), ' ':(0,0,0,255)}

def GENERATE_RANDOM_ORB():
    #return ORBS[random.randint(0, 6)] 
    return ORBS[random.randint(0, 5)]   

def GENERATE_RANDOM_ORB_OF_MONSTER_TYPE():
    #return ORBS[random.randint(0, 6)] 
    return ORBS[random.randint(0, 4)]   

ORB_IMAGES_FOLDER = "orbImages"

def ADD_ORB_IMAGES_TO_IMAGE_LOADER(loader):
    for orb in ORBS:
        loader.load(ORB_IMAGES_FOLDER, ORB_IMAGE_FILE_MAP[orb], orb)
        loader.ImageMap[orb] = pygame.transform.scale(loader.get(orb), (ORB_WIDTH, ORB_HEIGHT))
    
