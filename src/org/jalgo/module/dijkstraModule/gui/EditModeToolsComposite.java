/* j-Algo - j-Algo is an algorithm visualization tool, especially useful for students and lecturers of computer sience. It is written in Java and platform independant. j-Algo is developed with the help of Dresden University of Technology.
 *
 * Copyright (C) 2004-2005 j-Algo-Team, j-algo-development@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/*
 * Created on 15.05.2005
 *

 */
package org.jalgo.module.dijkstraModule.gui;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Composite;
import org.jalgo.module.dijkstraModule.actions.*;
import org.jalgo.module.dijkstraModule.actions.UndoAction;
import org.jalgo.module.dijkstraModule.actions.FileAction;
import org.jalgo.module.dijkstraModule.util.DefaultExceptionHandler;

/**
 * @author Frank Staudinger
 *
 * This composite provides the tools in edit mode
 */
public class EditModeToolsComposite extends ControllerComposite {
	
	protected class UndoButtonObserver implements Observer
	{
		protected Button m_UndoButton;
		public UndoButtonObserver(Button b)
		{
			m_UndoButton = b;
		}
		public void update(Observable o, Object arg)
		{
			Controller ctrl =  null;
			try
			{
				ctrl = (Controller)o;
			}
			catch(Exception e)
			{
				return;
			}
			if(ctrl == null)
				return;
			m_UndoButton.setEnabled(ctrl.hasUndoAction());
		}		
	}
	protected class RedoButtonObserver implements Observer
	{
		protected Button m_UndoButton;
		public RedoButtonObserver(Button b)
		{
			m_UndoButton = b;
		}
		public void update(Observable o, Object arg)
		{
			Controller ctrl =  null;
			try
			{
				ctrl = (Controller)o;
			}
			catch(Exception e)
			{
				return;
			}
			if(ctrl == null)
				return;
			m_UndoButton.setEnabled(ctrl.hasRedoAction());
		}		
	};
	protected class ButtonObserver implements Observer
	{
		protected Button m_btnTarget;
		protected int m_iEditMode;
		public ButtonObserver(Button btnTarget,int iEditMode)
		{
			m_btnTarget = btnTarget;
			m_iEditMode = iEditMode;
		}
		public void update(Observable arg0, Object arg1) 
		{
			Controller ctrl = (Controller)arg0;
			m_btnTarget.setSelection(ctrl.getEditingMode() == m_iEditMode);           
			
		}
	}
	
	
	protected class RedoButtonSelectionAdapter extends SelectionAdapter
	{
		private Controller m_Ctrl;
		
		RedoButtonSelectionAdapter(Controller ctrl)
		{
			super();
			m_Ctrl = ctrl;
		}
		public void widgetSelected(SelectionEvent e)
		{
			try
			{
				new RedoAction(m_Ctrl);
			}
			catch(Exception exc)
			{	
				new DefaultExceptionHandler(exc);
			}		        
		}
	};		
	protected class SaveButtonSelectionAdapter extends SelectionAdapter
	{
		protected boolean m_bSave;
		protected Controller m_ctrl;
		public SaveButtonSelectionAdapter(Controller ctrl, boolean bSave)
		{
			m_ctrl = ctrl;
			m_bSave = bSave;
		}
		
		public void widgetSelected(SelectionEvent e)
		{
			try
			{
				new FileAction(m_ctrl,m_bSave);
			}
			catch(Exception exc)
			{	
				new DefaultExceptionHandler(exc);
				
			}
			
		}		
	}
	protected class UndoButtonSelectionAdapter extends SelectionAdapter
	{
		private Controller m_Ctrl;
		
		UndoButtonSelectionAdapter(Controller ctrl)
		{
			super();
			
			m_Ctrl = ctrl;
		}
		public void widgetSelected(SelectionEvent e)
		{
			try
			{
				new UndoAction(m_Ctrl);
			}
			catch(Exception exc)
			{	
				new DefaultExceptionHandler(exc);
				
			}		        
		}
	};		
	protected class ButtonSelectionAdapter extends SelectionAdapter
	{
		Controller m_Ctrl;
		int m_iMode;
		ButtonSelectionAdapter(Controller Ctrl, int iMode)
		{
			m_Ctrl = Ctrl;
			m_iMode = iMode;
			
		}
		public void widgetSelected(SelectionEvent e)
		{
			try
			{
				if (this.m_Ctrl.getEditingMode() != m_iMode) {
					// Button was clicked when editing mode was different from the one associated with it
					// -> button has become active now.
					new SetEditingModeAction(m_Ctrl, m_iMode);
				} else {
					// Button was clicked when it was already active -> set "non-visual" editing mode.
					new SetEditingModeAction(m_Ctrl, Controller.MODE_NO_TOOL_ACTIVE);
				}
			}
			catch(Exception exc)
			{	
				new DefaultExceptionHandler(exc);
			}		        
		} 	    
	}

	/* (non-Javadoc)
	 * @see org.jalgo.module.dijkstraModule.gui.ControllerComposite
	 */
	public EditModeToolsComposite(Controller ctrl,Composite arg0, int arg1) {
		super(ctrl,arg0, arg1);
		GridLayout l = new GridLayout();
		l.verticalSpacing = 5;
		l.marginHeight = 5;
		l.marginWidth = 5;
		l.numColumns = 2;
		l.makeColumnsEqualWidth = true;
		this.setLayout(l);      	
		
		Button b = new Button(this,SWT.CENTER|SWT.TOGGLE);
		b.setText("Knoten hinzuf\u00FCgen/verschieben");    	    	  
		GridData g = new GridData(GridData.FILL_HORIZONTAL);
		b.setLayoutData(g);
		b.addMouseTrackListener(new StatusbarTextMouseTrackAdapter(getController(),"Mit dem Werkzeug \u201EKnoten hinzuf\u00FCgen/verschieben\u201C k\u00F6nnen neue Knoten durch Klicken auf die Zeichenfl\u00E4che erzeugt sowie vorhandene Knoten durch Klicken und Ziehen verschoben werden."));
		b.addSelectionListener(new ButtonSelectionAdapter(getController(),Controller.MODE_ADD_MOVE_NODE));
		getController().addObserver(new ButtonObserver(b,Controller.MODE_ADD_MOVE_NODE));
		
		
		b = new Button(this,SWT.CENTER|SWT.TOGGLE);
		b.setText("Kante hinzuf\u00FCgen/bewerten");    	    	  
		g = new GridData(GridData.FILL_HORIZONTAL);
		b.setLayoutData(g);    	    	
		b.addMouseTrackListener(new StatusbarTextMouseTrackAdapter(getController(),"Mit dem Werkzeug \u201EKante hinzuf\u00FCgen/bewerten\u201C k\u00F6nnen neue Kanten zwischen Knoten gezogen sowie vorhandene Kantengewichte durch Klicken und Ziehen auf die Gewichtszahl ver\u00E4ndert werden."));
		b.addSelectionListener(new ButtonSelectionAdapter(getController(),Controller.MODE_ADD_WEIGH_EDGE));
		getController().addObserver(new ButtonObserver(b,Controller.MODE_ADD_WEIGH_EDGE));
		
/*
		b = new Button(this,SWT.CENTER);
		b.setText("R\u00FCckg\u00E4ngig");
		g = new GridData(GridData.FILL_HORIZONTAL);
		b.setLayoutData(g);
		b.addSelectionListener(new UndoButtonSelectionAdapter(getController()));
		b.addMouseTrackListener(new StatusbarTextMouseTrackAdapter(getController(),"Hier muss noch Text hin."));
		getController().addObserver(new UndoButtonObserver(b));
		b.setEnabled(false);    	    	
		
		b = new Button(this,SWT.CENTER);
		b.setText("Laden");
		g = new GridData(GridData.FILL_HORIZONTAL);
		b.setLayoutData(g);
		b.addSelectionListener(new SaveButtonSelectionAdapter(getController(),false));
		b.addMouseTrackListener(new StatusbarTextMouseTrackAdapter(getController(),"Hier muss noch Text hin."));		
*/				
		b = new Button(this,SWT.CENTER|SWT.TOGGLE);
		b.setText("Knoten l\u00F6schen");
		g = new GridData(GridData.FILL_HORIZONTAL);
		b.setLayoutData(g); 	    	
		b.addMouseTrackListener(new StatusbarTextMouseTrackAdapter(getController(),"Mit dem Werkzeug \u201EKnoten l\u00F6schen\u201C k\u00F6nnen Knoten durch Anklicken entfernt werden."));
		b.addSelectionListener(new ButtonSelectionAdapter(getController(),Controller.MODE_DELETE_NODE));
		getController().addObserver(new ButtonObserver(b,Controller.MODE_DELETE_NODE));
		
		b = new Button(this,SWT.CENTER|SWT.TOGGLE);
		b.setText("Kante l\u00F6schen");    	    	  
		g = new GridData(GridData.FILL_HORIZONTAL);
		b.setLayoutData(g);    
		b.addMouseTrackListener(new StatusbarTextMouseTrackAdapter(getController(),"Mit dem Werkzeug \u201EKante l\u00F6schen\u201C k\u00F6nnen Kanten durch Klicken auf die Gewichtszahl entfernt werden."));
		b.addSelectionListener(new ButtonSelectionAdapter(getController(),Controller.MODE_DELETE_EDGE));
		getController().addObserver(new ButtonObserver(b,Controller.MODE_DELETE_EDGE));
/*		
		b = new Button(this,SWT.CENTER);
		b.setText("Wiederholen");
		g = new GridData(GridData.FILL_HORIZONTAL);
		b.setLayoutData(g);
		b.addSelectionListener(new RedoButtonSelectionAdapter(getController()));       	
		getController().addObserver(new RedoButtonObserver(b));
		b.setEnabled(false);   	
		b.addMouseTrackListener(new StatusbarTextMouseTrackAdapter(getController(),"Hier muss noch Text hin."));
		
		b = new Button(this,SWT.CENTER);
		b.setText("Speichern");
		g = new GridData(GridData.FILL_HORIZONTAL);
		b.setLayoutData(g);
		b.addSelectionListener(new SaveButtonSelectionAdapter(getController(),true));  
		b.addMouseTrackListener(new StatusbarTextMouseTrackAdapter(getController(),"Hier muss noch Text hin."));
*/
		
	}
	
	
}
