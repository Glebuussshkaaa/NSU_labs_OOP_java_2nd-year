package com.glebestraikh.chat.client;

import javax.swing.SwingUtilities;
import com.glebestraikh.chat.client.view.StartMenuFrame;
public class ClientApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartMenuFrame::new);
    }
}
