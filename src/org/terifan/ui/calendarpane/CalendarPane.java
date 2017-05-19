package org.terifan.ui.calendarpane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import org.terifan.ui.PopupFactory;
import org.terifan.util.Calendar;


public class CalendarPane extends JPanel implements Iterable<CalendarElement>
{
	private int mMajorUnitHeight = 80;
	private int mMinorUnitHeight = 20;
	private long mStartDate;
	private long mEndDate;

	private ArrayList<CalendarElement> mElements;
	private CalendarElement mSelectedElement;
	private ArrayList<SelectionListener> mSelectionListeners;
	private Point mMouseScrollPoint;
	private long mLastMouseScrollTime;
	private Rectangle mBounds;
	private Insets mPadding;
	private JScrollPane mScrollPane;
	private ViewController mViewController;
	private Point mDragStart;
	private boolean mWasDragged;
	private PopupFactory<CalendarElement> mPopupFactory;


	public CalendarPane(Calendar aFirstDate, Calendar aLastDate)
	{
		super.setBackground(Color.WHITE);
		super.addMouseListener(mMouseListener);
		super.addMouseMotionListener(mMouseMotionListener);

		mBounds = new Rectangle();
		mPadding = new Insets(0, 20, 0, 20);

		mStartDate = aFirstDate.clone().clearTime().get();
		mEndDate = aLastDate.clone().clearTime().roll("day", 1).get();

		mElements = new ArrayList<>();
		mSelectionListeners = new ArrayList<>();

		requestFocus();
	}


	public ViewController getViewController()
	{
		return mViewController;
	}


	public void setViewController(ViewController aViewController)
	{
		this.mViewController = aViewController;
	}


	public PopupFactory<CalendarElement> getPopupFactory()
	{
		return mPopupFactory;
	}


	public void setPopupFactory(PopupFactory<CalendarElement> aPopupFactory)
	{
		mPopupFactory = aPopupFactory;
	}


	@Override
	public void removeAll()
	{
		super.removeAll();

		mBounds.setBounds(new Rectangle());
		mElements.clear();
	}


	public Calendar getStartDate()
	{
		return new Calendar(mStartDate);
	}


	public Calendar getEndDate()
	{
		return new Calendar(mEndDate);
	}


	public void addSelectionListener(SelectionListener aSelectionListener)
	{
		mSelectionListeners.add(aSelectionListener);
	}


	public void addElement(CalendarElement aElement)
	{
		if (aElement.getFromDate() == null || aElement.getToDate() == null)
		{
			throw new IllegalArgumentException("Bad element: " + aElement);
		}

		mElements.add(aElement);
	}


	public void removeElement(CalendarElement aElement)
	{
		mElements.remove(aElement);
	}


	public void setSelectedElement(CalendarElement aSelectedElement)
	{
		mSelectedElement = aSelectedElement;
	}


	public CalendarElement getSelectedElement()
	{
		return mSelectedElement;
	}


	@Override
	protected void paintComponent(Graphics aGraphics)
	{
		if (mScrollPane == null)
		{
			mScrollPane = (JScrollPane)SwingUtilities.getAncestorOfClass(JScrollPane.class, this);
		}

		Graphics2D g = (Graphics2D)aGraphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		int width = getWidth();
		int height = getHeight();

		g.setColor(getBackground());
		g.fillRect(0, 0, width, height);

		g.setColor(new Color(230, 237, 247));
		for (int y = 0; y < height; y += mMinorUnitHeight)
		{
			if (g.hitClip(0, y, width, mMinorUnitHeight + 6))
			{
				g.drawLine(0, y, width, y);
			}
		}
		g.setColor(new Color(169, 176, 184));
		for (int y = 0; y < height; y += mMajorUnitHeight)
		{
			if (g.hitClip(0, y, width, mMajorUnitHeight + 6))
			{
				g.drawLine(0, y, width, y);
			}
		}
		g.setColor(new Color(0, 0, 0));
		for (int y = 0; y < height; y += 24 * mMajorUnitHeight)
		{
			if (g.hitClip(0, y, width, 24 * mMajorUnitHeight + 6))
			{
				g.drawLine(0, y, width, y);
			}
		}

		for (CalendarElement element : mElements)
		{
			if (element.isEnabled() && (mViewController == null || mViewController.isVisible(element)))
			{
				Rectangle r = element.getVisualBounds();

				if (r != null && g.hitClip(r.x, r.y, r.width, r.height))
				{
					element.setSelected(element == mSelectedElement);

					element.render(g);
				}
			}
		}

		Point msp = mMouseScrollPoint;
		if (msp != null)
		{
			Point pt = new Point(msp);
			SwingUtilities.convertPointFromScreen(pt, this);

			aGraphics.setColor(Color.GRAY);
			aGraphics.fillOval(pt.x - 10, pt.y - 10, 20, 20);

			mouseScroll();
		}
	}


	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(mBounds.width + mPadding.left + mPadding.right, (int)((mEndDate - mStartDate) / 1000 / 60 / 60 * mMajorUnitHeight));
	}


	public void layoutCalendar()
	{
		mBounds = new Rectangle();

		ArrayList<Rectangle> rects = new ArrayList<>();

		for (CalendarElement element : mElements)
		{
			if (element.isEnabled())
			{
				int y0 = timeToOffset(element.getFromDate().get());
				int y1 = timeToOffset(element.getToDate().get());

				Dimension d = element.getPreferredSize();

				Rectangle r0 = new Rectangle(mPadding.left, y0, d.width, Math.max(y1 - y0, 4));
				Rectangle r1 = new Rectangle(mPadding.left, y0, d.width, Math.max(d.height, r0.height));

				if (y1 - y0 < 0)
				{
//					mErrors.add(new SimpleCalendarElement("ERROR").setVisualBounds(new Rectangle(0, r0.y, 10000, Math.max(r0.height, 20))));
				}

				for (boolean ok = false; !ok;)
				{
					ok = true;

					for (Rectangle or : rects)
					{
						if (r1.intersects(or))
						{
							r0.translate(or.width + 10, 0);
							r1.translate(or.width + 10, 0);
							ok = false;
						}
					}
				}

				mBounds.add(r1);

				rects.add(r1);
				element.setTimeBounds(r0);
				element.setVisualBounds(r1);
			}
		}
	}


	public int timeToOffset(long aTime)
	{
		return (int)(mMajorUnitHeight * (aTime - mStartDate) / 60 / 60 / 1000);
	}


	public long offsetToTime(int aOffset)
	{
		return mStartDate + aOffset * 60L * 60 * 1000 / mMajorUnitHeight;
	}


	private void mouseScroll()
	{
		Point msp = mMouseScrollPoint;

		if (msp != null && mScrollPane != null)
		{
			PointerInfo pi = MouseInfo.getPointerInfo();

			int deltaX = (pi.getLocation().x - msp.x) / 5;
			int deltaY = (pi.getLocation().y - msp.y) / 5;
			int deltaT = (int)(System.currentTimeMillis() - mLastMouseScrollTime) / 10;

			if (deltaT == 0)
			{
				SwingUtilities.invokeLater(() -> repaint());
				return;
			}

			deltaX *= deltaT;
			deltaY *= deltaT;

			Rectangle rect = mScrollPane.getViewport().getViewRect();
			rect.translate(deltaX, deltaY);
			CalendarPane.this.scrollRectToVisible(rect);

			mLastMouseScrollTime = System.currentTimeMillis();

			SwingUtilities.invokeLater(() -> repaint());
		}
	}


	@FunctionalInterface
	public interface SelectionListener
	{
		void onSelection(CalendarElement aCalendarElement);
	}


	@Override
	public Iterator<CalendarElement> iterator()
	{
		return mElements.iterator();
	}

	private MouseAdapter mMouseListener = new MouseAdapter()
	{
		@Override
		public void mousePressed(MouseEvent aEvent)
		{
			requestFocus();

			if (mMouseScrollPoint != null)
			{
				mMouseScrollPoint = null; // end repaint-loop
				repaint();
				return;
			}
			if (SwingUtilities.isMiddleMouseButton(aEvent))
			{
				mLastMouseScrollTime = System.currentTimeMillis();
				mMouseScrollPoint = aEvent.getLocationOnScreen();
				repaint(); // start repaint-loop
				return;
			}

			CalendarElement selectedElement = null;

			for (CalendarElement element : mElements)
			{
				if (element.isEnabled() && (mViewController == null || mViewController.isVisible(element)) && element.getVisualBounds() != null && element.getVisualBounds().contains(aEvent.getPoint()))
				{
					selectedElement = element;

					for (SelectionListener listener : mSelectionListeners)
					{
						listener.onSelection(element);
					}

					repaintAll();
				}
			}

			mSelectedElement = selectedElement;

			if (SwingUtilities.isRightMouseButton(aEvent))
			{
				mPopupFactory.createPopup(mSelectedElement).show(CalendarPane.this, aEvent.getX(), aEvent.getY());
				return;
			}

			if (mSelectedElement != null)
			{
				mDragStart = aEvent.getPoint();
				mDragStart.y = mDragStart.y - mSelectedElement.getVisualBounds().y;
			}
			else
			{
				mDragStart = null;
			}
		}


		@Override
		public void mouseReleased(MouseEvent aEvent)
		{
			if (mDragStart != null && mSelectedElement != null && mWasDragged)
			{
				long duration = mSelectedElement.getToDate().difference(Calendar.Field.MILLISECOND, mSelectedElement.getFromDate());

				mSelectedElement.getFromDate().set(offsetToTime(mSelectedElement.getTimeBounds().y));
				mSelectedElement.getToDate().set(mSelectedElement.getFromDate().get() + duration);

				mSelectedElement.setUpdated(true);
			}

			mDragStart = null;
			mWasDragged = false;

			repaintAll();
		}
	};


	private void repaintAll()
	{
		if (mScrollPane != null)
		{
			mScrollPane.repaint();
		}
		else
		{
			repaint();
		}
	}


	private MouseMotionListener mMouseMotionListener = new MouseMotionAdapter()
	{
		@Override
		public void mouseDragged(MouseEvent aEvent)
		{
			if (mDragStart != null)
			{
				int s = 5 * 60 * 1000;
				int y = timeToOffset(offsetToTime(aEvent.getY() - mDragStart.y) / s * s);

				mSelectedElement.getTimeBounds().y = y;
				mSelectedElement.getVisualBounds().y = y;
				mWasDragged = true;

				repaintAll();
			}
		}
	};
}
