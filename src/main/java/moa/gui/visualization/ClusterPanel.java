/*
 *    ClusterPanel.java
 *    Copyright (C) 2010 RWTH Aachen University, Germany
 *    @author Jansen (moa@cs.rwth-aachen.de)
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package moa.gui.visualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import moa.cluster.SphereCluster;

public class ClusterPanel extends JPanel {
    private SphereCluster cluster;

    private double[] center;
    private final static int MIN_SIZE = 5;
    protected double decay_rate;

    protected int x_dim = 0;
    protected int y_dim = 1;
    protected Color col;
    protected Color default_color = Color.BLACK;
    protected double[] direction = null;

    protected StreamPanel streamPanel;

    protected int panel_size;
    protected int window_size;
    protected boolean highligted = false;
    private double r;



    /** Creates new form ObjectPanel */

    public ClusterPanel(SphereCluster cluster, Color color, StreamPanel sp) {
        this.cluster = cluster;
        center = cluster.getCenter();
        r = cluster.getRadius();
        streamPanel = sp;

        default_color = col = color;

        setVisible(true);
        setOpaque(false);
        setSize(new Dimension(1,1));
        setLocation(0,0);

        initComponents();
    }

    public void setDirection(double[] direction){
        this.direction = direction;
    }

    public void updateLocation(){
        x_dim = streamPanel.getActiveXDim();
        y_dim = streamPanel.getActiveYDim();

        if(cluster!=null && center==null)
            getParent().remove(this);
        else{
            //size of the parent
            window_size = Math.min(streamPanel.getWidth(),streamPanel.getHeight());

            //scale down to diameter
            panel_size = (int) (2* r * window_size);
            if(panel_size < MIN_SIZE)
                    panel_size = MIN_SIZE;

            setSize(new Dimension(panel_size+1,panel_size+1));
            setLocation((int)(center[x_dim]*window_size-(panel_size/2)),(int)(center[y_dim]*window_size-(panel_size/2)));
            
        }
    }

    public void updateTooltip(){
        setToolTipText(cluster.getInfo());
    }

    @Override
    public boolean contains(int x, int y) {
        //only react on the hull of the cluster
        double dist = Math.sqrt(Math.pow(x-panel_size/2,2)+Math.pow(y-panel_size/2,2));
        if(panel_size/2 - 5 < dist && dist < panel_size/2 + 5)
            return true;
        else
            return false;
    }




    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        streamPanel.setHighlightedClusterPanel(this);
    }//GEN-LAST:event_formMouseClicked

    @Override
    protected void paintComponent(Graphics g) {
        updateLocation();
        if(highligted){
            g.setColor(Color.BLUE);
        }
        else{
            g.setColor(default_color);
        }
        int c = (int)(panel_size/2);

        if(cluster.getId()>=0)
            g.drawString("C"+(int)cluster.getId(),c,c);
        
        g.drawOval(0, 0, panel_size, panel_size);

        if(direction!=null){
            double length = Math.sqrt(Math.pow(direction[0], 2) + Math.pow(direction[1], 2));
            g.drawLine(c, c, c+(int)((direction[0]/length)*panel_size), c+(int)((direction[1]/length)*panel_size));
        }

        updateTooltip();
        

    }

    public void highlight(boolean enabled){
        highligted = enabled;
        repaint();
    }

    public boolean isValidCluster(){
        return (center!=null);
    }

    public int getClusterID(){
        return (int)cluster.getId();
    }

    public int getClusterLabel(){
        return (int)cluster.getGroundTruth();
    }


    public String getSVGString(int width){
        StringBuffer out = new StringBuffer();

        int x = (int)(center[x_dim]*window_size);
        int y = (int)(center[y_dim]*window_size);
        int radius = panel_size/2;
        
        out.append("<circle ");
        out.append("cx='"+x+"' cy='"+y+"' r='"+radius+"'");
        out.append(" stroke='green' stroke-width='1' fill='white' fill-opacity='0' />");
        out.append("\n");
        return out.toString();
    }

    public void drawOnCanvas(Graphics2D imageGraphics){
        int x = (int)(center[x_dim]*window_size-(panel_size/2));
        int y = (int)(center[y_dim]*window_size-(panel_size/2));
        int radius = panel_size;
        imageGraphics.drawOval(x, y, radius, radius);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


}
