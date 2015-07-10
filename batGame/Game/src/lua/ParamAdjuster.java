package lua;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class ParamAdjuster extends JFrame {

private Thread runThread;

    public void run() {
    	runThread = new Thread(getLoop);
    	runThread.start();
    }
    
    private void initComponents() {

        jPanel1 		= new javax.swing.JPanel();
        get 			= new javax.swing.JButton();
        getContinuous 	= new javax.swing.JToggleButton();
        jScrollPane1 	= new javax.swing.JScrollPane();
        getters 		= new javax.swing.JPanel();
        getters.setLayout(new BoxLayout(getters, BoxLayout.Y_AXIS));
        jScrollPane2 	= new javax.swing.JScrollPane();
        others 			= new javax.swing.JPanel();
        others.setLayout(new BoxLayout(others, BoxLayout.Y_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        
        get.setText("Get");
        get.setFocusable(false);
        get.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getActionPerformed(evt);
            }
        });

        getContinuous.setText("Get Continuously");
        getContinuous.setFocusable(false);
        getContinuous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getContinousActionPerformed(evt);
            }
        });

        getters.setDoubleBuffered(true);
        jScrollPane1.setDoubleBuffered(true);
        jScrollPane1.setViewportView(getters);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(get)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(getContinuous)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(get)
                    .addComponent(getContinuous))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jScrollPane2.setViewportView(others);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }                     

    
    private Runnable getLoop = new Runnable() {
        @Override
        public void run() {
            while(true)
            {
                if(isGetContinous)
                {
                    get();
                }
                try {
                    Thread.sleep(4);
                } catch (InterruptedException ex) {
                }
            }
        }
    };
    
    private boolean isGetContinous = false;
    
    private void getContinousActionPerformed(java.awt.event.ActionEvent evt) {                                             
        isGetContinous = !isGetContinous;
    }                                            

    private void getActionPerformed(java.awt.event.ActionEvent evt) {                                    
        get();
    }                                   

    private void get() {
    	getters.removeAll();
    	LuaValue result;
    	getters.add(new JLabel("toString: " + callMethod("toString")));
    	for(String get : gettersList)
    	{
			getters.add(new JLabel(get + ": " + callMethod("get" + get)));
    	}
    	getters.revalidate();
    	getters.repaint();
    }
    
    private void other()
    {
    	for(String other : othersList)
    	{
    		others.add(new memberPanel(other, this));
    	}
    }

    private javax.swing.JButton get;
    private javax.swing.JToggleButton getContinuous;
    private javax.swing.JPanel getters;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel others;
   	private LuaScriptManager scriptManager;
	private TreeSet<String> gettersList = new TreeSet<String>();
	TreeSet<String> othersList = new TreeSet<String>();
	
	public ParamAdjuster (LuaUserdata data)
	{
		create(data);
	}
	public ParamAdjuster (LuaValue data)
	{
		create((LuaUserdata)data);
	}
	public ParamAdjuster (Object data)
	{
		create((LuaUserdata)CoerceJavaToLua.coerce(data));
	}
	
	private void create(LuaUserdata data) {
		scriptManager = new LuaScriptManager();
		scriptManager.set("data", data);

		initComponents();
		
		setTitle(data.m_instance.getClass().getCanonicalName() + " editor");
		
		for(Method method : data.m_instance.getClass().getMethods())
		{
			if ( Modifier.isPublic( method.getModifiers()) ) {
				
				String name = method.getName();
				if(name.equals("wait") || name.equals("toString") 
						|| name.equals("notifyAll") || name.equals("notify") 
						|| name.equals("hashCode") || name.equals("getClass")
						|| name.equals("equals"))
				{
					
				} else {

					if (name.substring(0, 3).equals("get"))
					{
						gettersList.add(name.substring(3, name.length()));
					} else {
						othersList.add(name);
					}
				}
			}
		}	
		
		get();
		other();
		setVisible(true);
		run();
	}
	
	public LuaValue callMethod (String method) {
		return scriptManager.runScript("data:" + method + "()");
	}
	
	public void callMethod (String method, String args) {
		scriptManager.runScript("data:" + method + "(" + args + ")");
	}
	
	private class memberPanel extends javax.swing.JPanel {

	    /**
	     * Creates new form memberPanel
	     */
	    public memberPanel(String name, ParamAdjuster editor) {
	        this.name = name;
	        this.editor = editor;
	        initComponents();
	    }
	    
	    private String name;
	    private ParamAdjuster editor;

	    private void initComponents() {

	        jLabel1 = new javax.swing.JLabel();
	        jTextField1 = new javax.swing.JTextField();
//	        jButton1 = new javax.swing.JButton();

	        jLabel1.setText(name + ":");
	        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
	        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

	        jTextField1.setColumns(8);
	        jTextField1.setHorizontalAlignment(SwingConstants.CENTER);
	        
	        jTextField1.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jTextField1ActionPerformed(evt);
	            }
	        });
	        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
	            public void focusGained(java.awt.event.FocusEvent evt) {
	                jTextField1FocusGained(evt);
	            }
	        });

//	        jButton1.setText("Call");
//	        jButton1.setFocusable(false);
//	        jButton1.addActionListener(new java.awt.event.ActionListener() {
//	            public void actionPerformed(java.awt.event.ActionEvent evt) {
//	                jButton1ActionPerformed(evt);
//	            }
//	        });

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	        this.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//	                .addComponent(jButton1)
	                .addContainerGap())
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
//	                    .addComponent(jButton1)
	                    .addComponent(jTextField1))
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	    }

	    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
	        actionPreformed();
	    }                                           

//	    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
//	        actionPreformed();
//	    }                                        

	    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {                                        
	        jTextField1.selectAll();
	    }                                       

	    
	    private void actionPreformed() {	    	
	    	editor.callMethod(name, jTextField1.getText());
	    }

	    // Variables declaration - do not modify                     
//	    private javax.swing.JButton jButton1;
	    private javax.swing.JLabel jLabel1;
	    private javax.swing.JTextField jTextField1;
	    // End of variables declaration                   
	}

}
