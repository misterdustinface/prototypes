package lua.libs;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.LuajavaLib;

public class MyLuaJava extends LuajavaLib{
	
	static final int INIT           = 0;
	static final int BINDCLASS      = 1;
	static final int NEWINSTANCE	= 2;
	static final int NEW			= 3;
	static final int CREATEPROXY	= 4;
	static final int LOADLIB		= 5;

	static final String[] NAMES = {
		"bindClass", 
		"newInstance", 
		"new", 
		"createProxy", 
		"loadLib",
	};
	
	public Varargs invoke(Varargs args) {
		try {
		switch(opcode)
		{
		case 0: 	
			LuaValue env = args.arg(2);
			LuaTable t = new LuaTable();
			bind( t, MyLuaJava.class, NAMES, BINDCLASS );
			env.set("luajava", t);
			env.get("package").get("loaded").set("luajava", t);
			return t;
		default :
	    	  return super.invoke(args);
		} } catch (LuaError e) {
			throw e;
		} catch (Exception e) {
			throw new LuaError(e);
		}
	}
	
	protected Class classForName(String name) throws ClassNotFoundException {
		return Class.forName(name);
	}
}
