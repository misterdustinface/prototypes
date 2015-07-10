local InputMultiplexer  = luajava.bindClass("com.badlogic.gdx.InputMultiplexer")
local Gdx  = luajava.bindClass("com.badlogic.gdx.Gdx")

local inputs = luajava.new(InputMultiplexer)
inputs:addProcessor(dofile("spikes/graphicsGenerator/RawInput.lua"))
inputs:addProcessor(dofile("spikes/graphicsGenerator/GestureInput.lua"))

Gdx.input:setInputProcessor(inputs)