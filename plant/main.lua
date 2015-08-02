local Circle = require "Circle"
local circleA = Circle()
local circleB = Circle()
local WIDTH, HEIGHT = 800, 600
local xSCALE, ySCALE = 1, 0.65

function love.load()
  circleA.x, circleA.y = 50, 50
  circleA.radius = 40
  circleB.x, circleB.y = 300, 300
  circleB.radius = 50
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

function love.draw()    
    scale(xSCALE, ySCALE)
    translate(-circleA.x + WIDTH/(xSCALE*2), -circleA.y + HEIGHT/(ySCALE*2))
  
    setColor(255, 255, 255)
    drawCircle(circleA)
    
    setColor(255, 0, 0)
    love.graphics.circle("line", circleB.x, circleB.y, circleB.radius, 5)
    
    love.graphics.print('Hello World!', 400, 300)
end

function love.update(dt)
  if love.keyboard.isDown("down") then
    circleA.y = circleA.y + (dt * 50)
  end
  if love.keyboard.isDown("up") then
    circleA.y = circleA.y - (dt * 50)
  end
  if love.keyboard.isDown("right") then
    circleA.x = circleA.x + (dt * 50)
  end
  if love.keyboard.isDown("left") then
    circleA.x = circleA.x - (dt * 50)
  end
end