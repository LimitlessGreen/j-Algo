/**
 * @author Frank Staudinger
 * 
 * The NodeListComposite provides a textfield an a button
 *  to edit the nodelist for the graph
 *
 */
package org.jalgo.module.dijkstraModule.gui;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.jalgo.module.dijkstraModule.actions.ApplyNodeListAction;
import org.jalgo.module.dijkstraModule.model.Graph;
import org.jalgo.module.dijkstraModule.util.DefaultExceptionHandler;


public class NodeListComposite extends ControllerComposite {
	protected class ApplyNodeListSelectionAdapter extends SelectionAdapter
	{
		private Controller m_Ctrl;
		private Text m_textNodeList;
		ApplyNodeListSelectionAdapter(Controller ctrl,Text t)
		{
			super();
			m_Ctrl = ctrl;
			this.m_textNodeList = t;
		}
		public void widgetSelected(SelectionEvent e)
		{
		    String strText = this.m_textNodeList.getText();
		    try
		    {
		        new ApplyNodeListAction(m_Ctrl,strText);
		    }
		    catch(Exception exc)
		    {	
		        new DefaultExceptionHandler(exc);
		        this.m_textNodeList.setText(strText);
		    }
		}
	};
	protected class NodeListObserver implements Observer
	{
		private Text m_textNodeList;
		public NodeListObserver(Text t)
		{
			m_textNodeList = t;
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
			Graph gr = ctrl.getGraph();
			m_textNodeList.setText(gr.getNodeListText());			
		}
	};
	
	/* (non-Javadoc)
	 * @see org.jalgo.module.dijkstraModule.gui.ControllerComposite
	 */
	public NodeListComposite(Controller ctrl,Composite arg0, int arg1) {
		super(ctrl,arg0, arg1);
		GridLayout l = new GridLayout();
		l.verticalSpacing = 5;
		l.marginHeight = 5;
		l.marginWidth = 5;
		l.numColumns = 2;
		setLayout(l);

		Text t = new Text(this,SWT.BORDER);
		
		t.setTextLimit(100);
		t.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		t.addMouseTrackListener(new StatusbarTextMouseTrackAdapter(getController(),"Textfeld zur Eingabe der durch Komma getrennten Knotenliste. Beispiel: 1, 2, 3, 4"));
		
		Button b = new Button(this, SWT.CENTER);
		b.setText("Anwenden");
		b.addSelectionListener(new ApplyNodeListSelectionAdapter(this.getController(),t));
		b.addMouseTrackListener(new StatusbarTextMouseTrackAdapter(getController(),"\u00C4nderungen an der Knotenliste \u00FCbernehmen und Graph neu darstellen."));
		  
		b.setLayoutData(new GridData(GridData.END));
		getController().addObserver(new NodeListObserver(t));
		  
	}
	
	
}