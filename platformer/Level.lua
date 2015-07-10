local Class    = require '_libs/hump.class'
local Camera   = require '_libs/hump.camera' 
local STI      = require '_libs/Simple-Tiled-Implementation'
local Collider = require 'Collider'
local Lighting = require 'Lighting'
local Input    = require 'Input'
local Player   = require 'Player'

Level = Class {
  player = nil,
  map = nil,
  collider = nil,
  lighting = nil,
  input    = nil,
  playerCamera  = nil,
  playerCameraOffset = nil,
  
  -- Constants
  staticTileLayer           = 'staticTiles',
  staticsObjectLayer        = 'staticCollisions',
  levelLocationsObjectLayer = 'levelLocations',
  lightingObjectLayer       = 'lighting'
}

function Level:init(level)
  self.windowWidth  = love.graphics.getWidth()
  self.windowHeight = love.graphics.getHeight()
  
  self.map = STI.new(level)

  self.collider = Collider()
  self.collider:loadStaticsFromMap(self.map, self.staticsObjectLayer)
      
  self.lighting = Lighting(self.collider)
  self.lighting:loadMap(self.map, self.lightingObjectLayer)
      
  self:loadLevelLocations()
  
  self.input = Input()
  self.input.player = self.player
  
  local playerX = self.player.shape:center()
  self.playerCamera = Camera(self.windowWidth / 2, self.windowHeight / 2)
  self.playerCameraOffset = self.windowWidth / 2 - playerX
end

function Level:update(dt)
  self.input:update()

  if not self.input.paused or self.input.step then
    self.collider:update(dt)
    self.player:update(dt)

    self.lighting:update(dt)
    
    self.input.step = false    
  end
end

function Level:draw()
  local playerX = self.player.shape:center()
  self.playerCamera.x = playerX + self.playerCameraOffset
  
  self.playerCamera:attach()
  
  self.lighting:preDraw()
  
  self.map:setDrawRange(0, 0, self.windowWidth, self.windowHeight)

  -- Draw the map and all objects within
  self.map.layers[self.staticTileLayer]:draw()
  
  love.graphics.setColor(255,0,0)
  self.collider:drawStatics('line')
 
  self.player:debugDraw()    
  
  self.lighting:draw()
  
  self.playerCamera:detach()
end

function Level:loadLevelLocations()
  local levelLocations = self.map.layers[self.levelLocationsObjectLayer]
  for _, location in pairs(levelLocations.objects) do
    if location.name == 'playerSpawn' then
      self.player = Player(location.x, location.y, location.width, location.height)
      self.collider:addShape(self.player.shape)
    end
  end
end

return Level