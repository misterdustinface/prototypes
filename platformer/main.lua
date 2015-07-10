local Level = require 'Level'

local level

function love.load(arg)
  if arg[#arg] == '-debug' then require('mobdebug').start() end
  io.stdout:setvbuf('no')
  --love.window.setFullscreen(false)  
  
  level = Level('_levels/test1')
end
  
function love.update(dt)
  level:update(dt)
end

function love.draw()
  love.graphics.setBackgroundColor(255,0,255,0)
  
  level:draw()

  love.graphics.setColor(255,255,255)
end

function love.keypressed(key) if level.input.keypressed[key] then level.input.keypressed[key](level.input) end end