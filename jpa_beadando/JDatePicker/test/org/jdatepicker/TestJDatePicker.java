/**
Copyright 2004 Juan Heyns. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY JUAN HEYNS ``AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JUAN HEYNS OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of Juan Heyns.
*/
package org.jdatepicker;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;


public class TestJDatePicker {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) { }
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        testFrame.setSize(500, 500);
        JPanel jPanel = new JPanel();
        JDatePicker picker = new DefaultComponentFactory().createJDatePicker();
//        new ColorTheme() {
//
//            public Color fgMonthSelector() { return Color.WHITE; }
//            public Color bgMonthSelector() { return new Color(0,0,120); }
//
//            public Color fgGridHeader() { return Color.YELLOW; }
//            public Color bgGridHeader() { return Color.PINK; }
//
//            public Color fgGridThisMonth() { return Color.WHITE; }
//            public Color fgGridOtherMonth() { return Color.CYAN; }
//            public Color fgGridToday() { return Color.GREEN; }
//            public Color bgGrid() { return Color.BLACK; }
//
//            public Color fgGridSelected() { return Color.MAGENTA; }
//            public Color bgGridSelected() { return Color.WHITE; }
//
//            public Color fgGridTodaySelected() { return Color.YELLOW; }
//            public Color bgGridTodaySelected() { return Color.GRAY; }
//
//            public Color fgTodaySelector() { return Color.YELLOW; }
//            public Color bgTodaySelector() { return Color.RED; }
//            
//        },
//        null);
        picker.setTextEditable(true);
        picker.setShowYearButtons(true);
//        picker.getModel().setSelected(true);
        jPanel.add((JComponent)picker);
        JPanel DatePanel = new JPanel();
        DatePanel.setLayout(new BorderLayout());
        DatePanel.add(jPanel, BorderLayout.WEST);
        BorderLayout fb = new BorderLayout();
        testFrame.setLayout(fb);
        testFrame.getContentPane().add(DatePanel, BorderLayout.WEST);
        testFrame.setVisible(true);
    }

}
