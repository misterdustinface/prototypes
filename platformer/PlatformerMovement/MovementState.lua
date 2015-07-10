local State = require 'State.State'

MovementState = State {
  transitionStates = {},
}

function MovementState:init(xName)
  this.name = xName
end

function MovementState:setTransitionStates(transitionStates)
  this.transitionStates = transitionStates
end

function MovementState:transition()
  for _, transitionState in ipairs(this.transitionStates) do
    if transitionState:isApplicable() then
      return transitionState
    end
  end
  
  return this
end

return MovementState