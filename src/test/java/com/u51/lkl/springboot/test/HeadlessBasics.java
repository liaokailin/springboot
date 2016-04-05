package com.u51.lkl.springboot.test;
import java.awt.*;
import java.io.*;
import java.awt.print.*;
 
import javax.imageio.*;
 
public class HeadlessBasics
{
    public static void main(String[] args)
    {
        // Set system property.
        // Call this BEFORE the toolkit has been initialized, that is,
        // before Toolkit.getDefaultToolkit() has been called.
        System.setProperty("java.awt.headless", "true");
 
        // This triggers creation of the toolkit.
        // Because java.awt.headless property is set to true, this
        // will be an instance of headless toolkit.
        Toolkit tk = Toolkit.getDefaultToolkit();
        // Standard beep is available.
        tk.beep();
 
        // Check whether the application is
        // running in headless mode.
        GraphicsEnvironment ge =
        GraphicsEnvironment.getLocalGraphicsEnvironment();
        System.out.println("Headless mode: " + ge.isHeadless());
 
        // No top levels are allowed.
        boolean created = false;
        try
        {
            Frame f = new Frame("Frame");
            created = true;
        }
        catch (Exception z)
        {
            z.printStackTrace(System.err);
            created = false;
        }
        System.err.println("Frame is created: " + created);
 
        // No other components except Canvas and Panel are allowed.
        created = false;
        try
        {
            Button b = new Button("Button");
            created = true;
        }
        catch (Exception z)
        {
            z.printStackTrace(System.err);
            created = false;
        }
        System.err.println("Button is created: " + created);
         
        // Canvases can be created.
        final Canvas c = new Canvas()
        {
            public void paint(Graphics g)
            {
                Rectangle r = getBounds();
                g.drawLine(0, 0, r.width - 1, r.height - 1);
                // Colors work too.
                g.setColor(new Color(255, 127, 0));
                g.drawLine(0, r.height - 1, r.width - 1, 0);
                // And fonts
                g.setFont(new Font("Arial", Font.ITALIC, 12));
                g.drawString("Test", 32, 8);
            }
        };
        // And all the operations work correctly.
        c.setBounds(32, 32, 128, 128);
 
        // Images are available.
        Image i = null;
        try
        {
            File f = new File("grapefruit.jpg");
            i = ImageIO.read(f);
        }
        catch (Exception z)
        {
            z.printStackTrace(System.err);
        }
        final Image im = i;
         
        // Print system is available.
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new Printable()
        {
            public int print(Graphics g, PageFormat pf, int pageIndex)
            {
                if (pageIndex > 0)
                {
                    return Printable.NO_SUCH_PAGE;
                }
                ((Graphics2D)g).translate(pf.getImageableX(),
                                          pf.getImageableY());
 
                // Paint the canvas.
                c.paint(g);
 
                // Paint the image.
                if (im != null)
                {
                    g.drawImage(im, 32, 32, 64, 64, null);
                }
 
                return Printable.PAGE_EXISTS;
            }
        });
        try
        {
            pj.print();
        }
        catch (Exception z)
        {
            z.printStackTrace(System.err);
        }
    }
}