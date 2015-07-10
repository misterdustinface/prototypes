local Keys = luajava.bindClass("lua.GdxKeys")

local RawInput = {
  keyDown = function(keycode)
    return false
  end,
  
  keyUp = function(keycode) 
    return false
  end,
  
  keyTyped = function(character) 
    return false
  end,
  
  touchDown = function(screenX, screenY, pointer, button) 
    return false
  end,
  
  touchUp = function (screenX, screenY, pointer, button) 
    world:dive()
    return false
  end,
  
  touchDragged = function(screenX, screenY, pointer) 
    return false
  end,
  
  mouseMoved = function(screenX, screenY) 
    return false
  end,
  
  scrolled = function(amount) 
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
