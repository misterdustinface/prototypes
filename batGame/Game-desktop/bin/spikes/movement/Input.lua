local InputMultiplexer  = luajava.bindClass("com.badlogic.gdx.InputMultiplexer")
local Gdx  = luajava.bindClass("com.badlogic.gdx.Gdx")

local inputs = luajava.new(InputMultiplexer)
inputs:addProcessor(dofile("spikes/movement/RawInput.lua"))
inputs:addProcessor(dofile("spikes/movement/GestureInput.lua"))

Gdx.input:setInputProcessor(inputs)