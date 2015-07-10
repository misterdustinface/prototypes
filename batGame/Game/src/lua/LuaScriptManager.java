package lua;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import lua.libs.MyLuaJava;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.Bit32Lib;
import org.luaj.vm2.lib.CoroutineLib;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.ResourceFinder;
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.TableLib;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseIoLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JseOsLib;

import com.badlogic.gdx.Gdx;


public class LuaScriptManager extends Globals{

    private LuaValue chunk;

    public LuaScriptManager() {
    	//Jse standard globals
    	load(new JseBaseLib());
		load(new PackageLib());
		load(new Bit32Lib());
		load(new TableLib());
		load(new StringLib());
		load(new CoroutineLib());
		load(new JseMathLib());
		load(new JseIoLib());
		load(new JseOsLib());
		load(new MyLuaJava()); //Need this one for android
		LuaC.install();
		compiler = LuaC.instance;
		
		FINDER = new ResourceFinder(){
			@Override
			public InputStream findResource(String filename) {
				return Gdx.files.internal(filename).read();
			}			
        };       
        
        set("consts", new LuaTable());
    }

    public LuaValue loadFile(String scriptFileName) {
    	Gdx.app.log("Load", "Load " + scriptFileName);
        chunk = super.loadFile(scriptFileName);
        chunk.call();
        return chunk;
    }
    
    public void addConstant(String name, LuaValue luaValue) {
		LuaValue consts = get("consts");
		consts.set(name, luaValue);

    }
    
    
    
    public void executeString(String string) 
    {
    	String [] words = string.split(" ");
    	if(words[0].equalsIgnoreCase("file"))
    	{
    		for(int i = 1; i < words.length; i++)
    		{
    			loadFile(words[i]);
    		}
    	} else {
    		runScript(string);
    	}
    }
    
    public LuaValue runScript(String script)
    {
    	script = "function runScript() return " + script + " end";
    	InputStream input = new ByteArrayInputStream(script.getBytes());
        Prototype p;
		try {
			p = LuaC.compile(input, "script");
	        LuaClosure c = new LuaClosure(p, this);
	        c.call();
	        return  get("runScript").call();
		} catch (Exception e) {
			e.printStackTrace();
			return LuaValue.NIL;
		}
    }
}
