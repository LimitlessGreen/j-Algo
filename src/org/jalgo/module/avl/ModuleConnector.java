/* Created on 12.04.2005 */
package org.jalgo.module.avl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.action.SubMenuManager;
import org.eclipse.jface.action.SubStatusLineManager;
import org.eclipse.jface.action.SubToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Composite;
import org.jalgo.main.IModuleConnector;
import org.jalgo.main.IModuleInfo;
import org.jalgo.module.avl.datastructure.Node;
import org.jalgo.module.avl.datastructure.SearchTree;
import org.jalgo.module.avl.gui.GUIController;

/**
 * This class provides the bridge between the main program and the current instance
 * of the AVL module. It handles the references of several objects and provides
 * some getters used in the main program.
 * 
 * @author Alexander Claus
 */
public class ModuleConnector
implements IModuleConnector {

	//gui components
	private SubMenuManager menuManager;
	private SubToolBarManager toolBarManager;
	private SubStatusLineManager statusLineManager;

	//singleton references for each module opened
	private ModuleInfo moduleInfo;

	private SearchTree tree;
	private Controller controller;
	private GUIController gui;

	//saves exceptions to file
	private ErrorLog errorLog;

    /**
	 * Constructs a <code>ModuleConnector</code> object for the AVL module.
	 * Instances of the module specific <code>SearchTree</code>,
	 * <code>Controller</code> and  <code>GUIController</code> are created here.
	 * 
	 * @see IModuleConnector
	 */
	public ModuleConnector(
		ApplicationWindow appWin,
		Composite comp,
		SubMenuManager menu,
		SubToolBarManager tb,
		SubStatusLineManager sl) {

		this.menuManager = menu;
		this.toolBarManager = tb;
		this.statusLineManager = sl;

		moduleInfo = new ModuleInfo();

		//TODO: enable this, when release the product, don't forget close() !
		errorLog = new ErrorLog();

		tree = new SearchTree();
		controller = new Controller(tree);
		gui = new GUIController(this, appWin, comp, menu, tb, sl, controller, tree);
	}

    /**
     * The "program code" of the AVL module. Currently there is only the welcome
     * screen displayed.
     * 
     * @see org.jalgo.main.IModuleConnector#run()
     */
    public void run() {
		gui.installWelcomeScreen();
	}

	/**
	 * This method is invoked, when module or program are intended to be closed.
	 * If there are no changes to save, it returns true, otherwise the user is
	 * asked for saving his work.
	 * 
	 * @return <code>true</code>, if module is ready to be closed,
	 * 			<code>false</code> otherwise
	 */
	public boolean close() {
		//if some dialogs are open, close this first
		if (gui.isDialogOpen()) return false;

		//ensure, that tree is in consistent state
		if (controller.algorithmHasNextStep()) try {
			controller.abort();
			gui.algorithmAborted();
		}
		catch (NoActionException ex) {}

		if (gui.areChangesToSave() && !gui.showSaveDialog()) return false;
		//TODO: enable this, when release the product
		errorLog.close();
		return true;
	}

    /* (non-Javadoc)
     * @see org.jalgo.main.IModuleConnector#setDataFromFile(java.io.ByteArrayInputStream)
     */
    public void setDataFromFile(ByteArrayInputStream data) {
    	try {
    		ObjectInputStream in = new ObjectInputStream(data);
			boolean avlMode = in.readBoolean();
			gui.setAVLMode(avlMode, avlMode);
			tree.importLevelOrder((List<Integer>)in.readObject());
			gui.installStandardLayout();
    	}
		catch (IOException ex) {
			gui.showErrorMessage("Keine g�ltige AVL-Datei.");
		}
		catch (ClassNotFoundException ex) {
			gui.showErrorMessage("Fehler beim Laden der Datei.\r\n"+
				"Die Datei ist m�glicherweise besch�digt.");
		}
    }

    /**
     * Here the data for serializing the <code>SearchTree</code> is formatted.
     * First there is a boolean value serialized, indicating, if the tree is an
     * AVL-tree. Secondly the integer keys of the <code>Node</code>s are serialized
     * in a levelordered linked list.
     * 
     * @see org.jalgo.main.IModuleConnector#getDataForFile()
     */
    public ByteArrayOutputStream getDataForFile() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
        	ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeBoolean(controller.isAVLMode());
            List<Node> nodes = tree.exportLevelOrder();
			List<Integer> keys = new LinkedList<Integer>();
			for (Node node : nodes) keys.add(node.getKey());
			objOut.writeObject(keys);
            objOut.close();
			gui.setChangesToSave(false);
        }
        catch(IOException ex) {ex.printStackTrace();}
        return out;
    }

    /* (non-Javadoc)
     * @see org.jalgo.main.IModuleConnector#print()
     */
    public void print() {

    }

    /* (non-Javadoc)
     * @see org.jalgo.main.IModuleConnector#getMenuManager()
     */
    public SubMenuManager getMenuManager() {
    	return menuManager;
    }

    /* (non-Javadoc)
     * @see org.jalgo.main.IModuleConnector#getToolBarManager()
     */
    public SubToolBarManager getToolBarManager() {
        return toolBarManager;
    }

    /* (non-Javadoc)
     * @see org.jalgo.main.IModuleConnector#getStatusLineManager()
     */
    public SubStatusLineManager getStatusLineManager() {
        return statusLineManager;
    }

    /* (non-Javadoc)
     * @see org.jalgo.main.IModuleConnector#getModuleInfo()
     */
    public IModuleInfo getModuleInfo() {
        return moduleInfo;
    }
}