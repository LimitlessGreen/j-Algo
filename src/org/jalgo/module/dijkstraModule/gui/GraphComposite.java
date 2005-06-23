/*
 * Created on 20.05.2005
 */

package org.jalgo.module.dijkstraModule.gui;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.jalgo.module.dijkstraModule.gfx.GraphParent;
import org.jalgo.module.dijkstraModule.model.Graph;
import org.jalgo.module.dijkstraModule.actions.RescaleGraphAction;
import org.jalgo.module.dijkstraModule.gui.Controller;
/**
 * @author Martin Winter
 */

public class GraphComposite extends ControllerComposite {

	protected class GraphCompositeObserver implements Observer
	{
		private GraphParent m_graphParent;
		
		public GraphCompositeObserver(GraphParent g)
		{
			m_graphParent = g;
		}
		
		public void update(Observable o, Object arg)
		{
			Controller ctrl =  null;
			try {
				ctrl = (Controller)o;
			} catch(Exception e) {
				return;
			}
			if (ctrl == null) {
				return;
			}
			Graph gr = ctrl.getGraph();
			m_graphParent.setGraph(gr);			
			m_graphParent.update();			
		}
	};
	protected class RescaleGraphButtonAdapter extends SelectionAdapter
	{
		private Controller m_Controller;
		
		public RescaleGraphButtonAdapter(Controller controller)
		{
			super();
			this.m_Controller = controller;
		}
		
		public void widgetSelected(SelectionEvent e)
		{
			try
			{
				new RescaleGraphAction(this.m_Controller);
			}
			catch (Exception ex)
			{
				
			}
		}
	};
	
	protected GraphParent m_graphParent;
	
	public GraphComposite(Controller controller, Composite composite, int style, Device device, boolean bEditMode) {
		super(controller, composite, style);
		
		this.setLayout(getGraphCompositeGridLayout());
		
		Canvas canvas = new Canvas(this, SWT.BORDER);
		
		Button rescaleGraphButton = new Button(this, SWT.PUSH);
		rescaleGraphButton.addSelectionListener(new RescaleGraphButtonAdapter(controller));
		rescaleGraphButton.setText("Knoten automatisch anordnen");
		rescaleGraphButton.setLayoutData(getButtonGridData());
		rescaleGraphButton.addMouseTrackListener(new StatusbarTextMouseTrackAdapter(getController(),"Alle Knoten automatisch in einem regelm\u00E4\u00DFigen Vieleck anordnen."));
		
		canvas.setBackground(ColorConstants.white);
		canvas.setLayoutData(getCanvasGridData());
		
		LightweightSystem lws = new LightweightSystem(canvas);	
		m_graphParent = new GraphParent(device, controller);
		m_graphParent.setBackgroundColor(ColorConstants.white);
		m_graphParent.setOpaque(true);
		lws.setContents(m_graphParent);
		
		controller.addObserver(new GraphCompositeObserver(m_graphParent));
	}
	
	private GridLayout getGraphCompositeGridLayout()
	{
		GridLayout graphCompositeGridLayout = new GridLayout();
		graphCompositeGridLayout.numColumns = 1;
		graphCompositeGridLayout.horizontalSpacing = 5;
		graphCompositeGridLayout.verticalSpacing = 5;
		graphCompositeGridLayout.marginHeight = 5;
		graphCompositeGridLayout.marginWidth = 5;
		//graphCompositeGridLayout.makeColumnsEqualWidth = false;
	
		return graphCompositeGridLayout;
	}
	
	private GridData getCanvasGridData()
	{
	    return new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
	}
	
	private GridData getButtonGridData()
	{
	    return new GridData(GridData.HORIZONTAL_ALIGN_END);
	}
}