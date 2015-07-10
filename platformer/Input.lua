local Class = require '_libs/hump.class'

local Input = Class {  
  paused = false,
  step = false,
}

function Input:init()
  self.paused = false
  self.step   = false
end

Input.keypressed = {
  ['p'] = function(self) self.paused = not self.paused end,
  ['['] = function(self) self.step = true end,
  ["escape"] = function(self) love.event.quit() end,
}

function Input:update()
  self.player.wantsToJump      = love.keyboard.isDown(' ')
  self.player.wantsToMoveLeft  = love.keyboard.isDown('d')
  self.player.wantsToMoveRight = love.keyboard.isDown('f')
end

return Input