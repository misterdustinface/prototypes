local Class = require '_libs/hump.class'
local State = require 'State.State'
local getStateName

StateMachine = Class {
  states = {},
  nullState = State("NULL STATE"),
}

function StateMachine:init()
  this:addState(this.nullState)
  this:forceState(this.nullState)
end

function StateMachine:addState(xState)
  this.states[xState.name] = xState
end

function StateMachine:forceState(xState) 
  local stateName = getStateName(xState)
  this.currentState = this.states[stateName]
end

function StateMachine:removeState(xState)
  local removedStateName = getStateName(xState)
  this.states[removedStateName] = null
  if this:getCurrentStateName() == removedStateName then
    this:forceState(this.nullState)
  end
end

function StateMachine:update()
  this.currentState:update()
  local transition = this.currentState:transition()
  this:addState(transition)
  this:forceState(transition)
end

function StateMachine:getCurrentStateName()
  return this.currentState.name
end

function getStateName(xStateOrStatename)
  local stateName = xStateOrStatename
  if type(xStateOrStatename) ~= "string" then 
    stateName = xStateOrStatename.name 
  end
  return stateName
end

return StateMachine