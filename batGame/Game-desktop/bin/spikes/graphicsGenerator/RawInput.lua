local Keys = luajava.bindClass("lua.GdxKeys")

local RawInput = {
  keyDown = function(keycode)
    if     keycode == Keys.UP then
      world:moveUp()
    elseif keycode == Keys.DOWN then
      world:moveDown()
    elseif keycode == Keys.LEFT then
      world:moveLeft()
    elseif keycode == Keys.RIGHT then
      world:moveRight()
    elseif keycode == Keys.SPACE then
      world:click()
    elseif keycode == Keys.C then
      world:clearAdditions()
    elseif keycode == Keys.NUM_1 then
      world:getSlopeManipPrompt()
    elseif keycode == Keys.NUM_2 then
      world:getFlipPercentManipPrompt()
    elseif keycode == Keys.NUM_3 then
      world:getFPS()
    end

    return false
  end,
  
  keyUp = function(keycode) 
    if     keycode == Keys.UP then
      world:moveUp()
    elseif keycode == Keys.DOWN then
      world:moveDown()
    elseif keycode == Keys.LEFT then
      world:moveLeft()
    elseif keycode == Keys.RIGHT then
      world:moveRight()
    end
  
    return false
  end,
  
  keyTyped = function(character) 
    return false
  end,
  
  touchDown = function(screenX, screenY, pointer, button) 
  
    return false
  end,
  
  touchUp = function (screenX, screenY, pointer, button) 
    return false
  end,
  
  touchDragged = function(screenX, screenY, pointer) 
    return false
  end,
  
  mouseMoved = function(screenX, screenY) 
    return false
  end,
  
  scrolled = function(amount) 
      if amount < 0 then
        world:generatorAdd()
      elseif amount > 0 then
        world:generatorRemove()  
      end
    return false
  end
}


local RawInputWraper = {
  keyDown = function(keycode)
    return RawInput.keyDown(keycode)
  end,
  
  keyUp = function(keycode) 
    return RawInput.keyUp(keycode)
  end,
  
  keyTyped = function(character) 
    return RawInput.keyTyped(character)
  end,
  
  touchDown = function(screenX, screenY, pointer, button) 
    return RawInput.touchDown(screenX,screenY,pointer,button)
  end,
  
  touchUp = function (screenX, screenY, pointer, button) 
    return RawInput.touchUp(screenX,screenY,pointer,button)
  end,
  
  touchDragged = function(screenX, screenY, pointer) 
    return RawInput.touchDragged(screenX,screenY,pointer)
  end,
  
  mouseMoved = function(screenX, screenY) 
    return RawInput.mouseMoved(screenX,screenY)
  end,
  
  scrolled = function(amount) 
    return RawInput.scrolled(amount)
  end
}

return luajava.createProxy("com.badlogic.gdx.InputProcessor", RawInputWraper)
