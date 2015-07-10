import pygame, sys, os, random
from pygame.locals import *
#from pygame import image

class imgLoader:
    def __init__(self):
        self.ImageMap = {}

    def load(self, folder, img, key):
        directImagePath = os.path.join(folder, img)
        image = pygame.image.load(directImagePath)
        image.convert_alpha()
        self.ImageMap[key] = image

    def get(self,key):
        return self.ImageMap[key]

    def getRandom(self):
        return self.ImageMap[random.sample(self.ImageMap.keys(),1)[0]]

