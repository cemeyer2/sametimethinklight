package com.ibm.collaboration.realtime.sample.alerts;

/*
 * Licensed Materials - Property of IBM
 *
 * L-KBIM-82KJL8
 *
 * (C) Copyright IBM Corp. 2006, 2010. All rights reserved.
 *
 * US Government Users Restricted Rights- Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.ibm.collaboration.realtime.contacts.search.DirectoryInfo;
import com.ibm.collaboration.realtime.people.Person;
import com.ibm.rcp.realtime.alerts.ui.DefaultAlertWindow;
import com.ibm.rcp.swt.swidgets.SToolBar;
import com.ibm.rcp.swt.swidgets.SToolItem;

/**
 * Demonstrate a custom alert window launched by <code>TextConnectionOpenHandler</code>
 * as a response to new incoming instant messages, allowing the user the option of
 * rejecting or accepting the chat.
 */
public class MyAlertWindow extends DefaultAlertWindow {

	Color foreground;
	Color background;
	Color border;

	private final static int BORDER = 5;
	private final static int PICTURE_SIZE = 48;

	public MyAlertWindow() {
		super();
		// set the border width to 1 so we get a border.
		setBorderWidth(1);
	}

	/**
	 * Overriding buildContents so we create our own titlebar and content composite.
	 */
	protected Composite buildContents(Composite parent) {
		foreground = new Color(parent.getDisplay(), 234,237,242);
		background = new Color(parent.getDisplay(), 216,222,231);

		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new FormLayout());

		Composite titleBar = new Composite(comp, SWT.NONE);
		FontData fdata = titleBar.getFont().getFontData()[0];
		fdata.setHeight(fdata.getHeight()+1);
		fdata.setStyle(SWT.BOLD);
		Font f = new Font(titleBar.getDisplay(), fdata);
		titleBar.setFont(f);
		titleBar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				((Composite)e.getSource()).getFont().dispose();

				if (foreground != null && !foreground.isDisposed()) {
					foreground.dispose();
					foreground = null;
				}
				if (background != null && !background.isDisposed()) {
					background.dispose();
					background = null;
				}
				if (border != null && !border.isDisposed()) {
					border.dispose();
					border = null;
				}
			}
		});
		// paint the background gradient
		titleBar.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Composite self = (Composite) e.getSource();
				Rectangle rect = self.getClientArea();

				Color clrCurFore = e.gc.getForeground();
				Color clrCurBack = e.gc.getBackground();
				e.gc.setForeground(foreground);
				e.gc.setBackground(background);
				e.gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height, true);
				e.gc.setForeground(clrCurFore);
				e.gc.setBackground(clrCurBack);

				e.gc.drawText(getAlertData().getTitle(), e.x+PICTURE_SIZE+2*BORDER, e.y+BORDER, true);
			}
		});
		// setup the drag handler for the titlebar we created.
		setupDragHandler(titleBar);
		FormLayout fl = new FormLayout();
		fl.marginHeight = BORDER;
		fl.marginWidth = BORDER;
		titleBar.setLayout(fl);

		// this is the main image, either from the message, or use the sender Person's directory image
		Label image = new Label(titleBar, SWT.BORDER);
		if (getAlertData().getMessageImage() != null) {
			image.setImage(getAlertData().getMessageImage());
		} else if (getAlertData().getSenderId() != null) {
			Person person = getSender();
			setPersonImageInLabel(person, image, new Point(48, 48));
		}

		FormData fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.left = new FormAttachment(0, 0);
		image.setLayoutData(fd);

		// The StyledText message.
		StyledText text = new StyledText(comp, SWT.BORDER);
		text.setText(getAlertData().getMessage());

		fd = new FormData();
		fd.top = new FormAttachment(0, 1);
		fd.left = new FormAttachment(0, 1);
		fd.right = new FormAttachment(100, -1);
		titleBar.setLayoutData(fd);

		fd = new FormData();
		fd.top = new FormAttachment(titleBar, 0);
		fd.left = new FormAttachment(0, 1);
		fd.right = new FormAttachment(100, -1);
		fd.bottom = new FormAttachment(100, 0);
		text.setLayoutData(fd);

		return comp;
	}

	/**
	 * Return null so we don't draw the default title bar.
	 */
	protected Composite buildTitleBar(Composite parent) {
		return null;
	}

	/**
	 * Do nothing so we don't create the default rounded rectangle region.
	 */
	protected void buildShellRegion() {
		// do nothing
	}

	/**
	 * Return null so we don't draw the default handle bar.
	 */
	protected Composite buildHandleBar(Composite parent) {
		return null;
	}

	/**
	 * Add a Close toolitem since we're overriding the titlebar which
	 * by default has the 'x' close button.
	 */
	protected Control buildToolBar(Composite parent) {
		Control toolbar = super.buildToolBar(parent);
		if (toolbar instanceof SToolBar) {
			SToolItem item = new SToolItem((SToolBar)toolbar, SWT.NONE);
			item.setText("Close");
			item.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					closeAlert();
				}
			});
		}
		return toolbar;
	}

	/**
	 * utility to set an image in a label given a person.
	 * @param person
	 * @param label
	 * @param size
	 */
	private void setPersonImageInLabel(Person person, Label label, Point size) {
		try {
			if (person == null || person.getDirectoryInfo() == null) {
				return;
			}
			String url = (String)person.getDirectoryInfo().get(DirectoryInfo.IMAGE_PATH);
			if (url == null) {
				return;
			}
			if (url.indexOf("http") != 0) {
				url = "file:///" + url;
			}
			URL urlURL = new URL(url); //$NON-NLS-1$
			final ImageDescriptor descriptor = ImageDescriptor.createFromURL(urlURL);
			if (descriptor != null) {
				Image newImage = descriptor.createImage(false);
				if (size != null) {
					Image sizedImage = new Image(label.getDisplay(), size.x, size.y);
					GC gc = new GC(sizedImage);
					gc.drawImage(newImage, 0, 0, newImage.getImageData().width, newImage.getImageData().height, 0, 0, size.x, size.y);
					gc.dispose();
					newImage.dispose();
					label.setImage(sizedImage);
					label.addDisposeListener(new DisposeListener() {
						public void widgetDisposed(DisposeEvent e) {
							((Label)e.getSource()).getImage().dispose();
						}
					});
				} else {
					label.setImage(newImage);
					label.addDisposeListener(new DisposeListener() {
						public void widgetDisposed(DisposeEvent e) {
							((Label)e.getSource()).getImage().dispose();
						}
					});
				}
			}
		}
		catch (Exception e) {}
	}
}
