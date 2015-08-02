local Circle = require "Circle"
local player = Circle()
local plant = Circle()
local WIDTH, HEIGHT = 800, 600
local xSCALE, ySCALE = 1, 0.65
xSCALE, ySCALE = xSCALE * 2, ySCALE * 2
local ROOT_TWO = math.sqrt(2)

function love.load()
  player.x, player.y = 50, 50
  player.radius = 10
  player.title = "Player"
  player.color = {r = 255, g = 255, b = 255, a = 255}
  
  plant.x, plant.y = 300, 300
  plant.radius = 80
  plant.title = "Plant"
  plant.color = {r = 35, g = 200, b = 80, a = 255}
  love.window.setMode(WIDTH, HEIGHT)
end

local function translate(dx, dy)
  love.graphics.translate(dx, dy)
end

local function scale(sx, sy)
  love.graphics.scale(sx, sy)
end

local function setColor(...)
  love.graphics.setColor(...)
end

local function drawLine(...)
  love.graphics.line(...)
end

local function drawCircle(xCircle)
  love.graphics.circle("line", xCircle.x, xCircle.y, xCircle.radius, 20)
end

local function fillCircle(xCircle)
  love.graphics.circle("fill", xCircle.x, xCircle.y, xCircle.radius, 20)
end

local function drawBody(xBody)
    if xBody.isCollided then
      setColor(125, 80, 0)
    else
      setColor(0, 80, 125)
    end
    drawCircle(xBody)
    
    local color = xBody.color
    setColor(color.r, color.g, color.b, color.a)
    love.graphics.rectangle("line", xBody.x - xBody.radius/2, xBody.y, xBody.radius, -xBody.radius*2)
    if xBody.title then
      love.graphics.print(xBody.title, xBody.x + xBody.radius/ROOT_TWO, xBody.y + xBody.radius/ROOT_TWO, math.pi/4)
    end
end


function love.draw()    
    scale(xSCALE, ySCALE)
    translate(-player.x + WIDTH/(xSCALE*2), -player.y + HEIGHT/(ySCALE*2))

    local isCollided = player:intersects(plant)
    player.isCollided = isCollided
    plant.isCollided = isCollided

    drawBody(player)
    drawBody(plant)
end

function love.update(dt)
  if love.keyboard.isDown("down") then
    player.y = player.y + (dt * 50)
  end
  if love.keyboard.isDown("up") then
    player.y = player.y - (dt * 50)
  end
  if love.keyboard.isDown("right") then
    player.x = player.x + (dt * 50)
  end
  if love.keyboard.isDown("left") then
    player.x = player.x - (dt * 50)
  end
end