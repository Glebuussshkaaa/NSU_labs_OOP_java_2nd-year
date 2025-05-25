package com.glebestraikh.chat.client.view;

import com.glebestraikh.chat.client.controller.Controller;
import com.glebestraikh.chat.client.listener.event.*;
import com.glebestraikh.chat.client.listener.Listener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class WorkSpaceFrame {
    private static final String TITLE = "XChat";
    private static final String SEND_BUTTON_ICON_FILE = "pngForChatPanel/sendButton.png";
    private static final String USERS_BUTTON_ICON_FILE = "pngForChatPanel/usersButton.png";
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private static final int BUTTON_ICON_SIZE = 32;

    private static final Color BACKGROUND_COLOR = new Color(15, 20, 25);           // Темный фон
    private static final Color CHAT_BACKGROUND_COLOR = new Color(24, 33, 42);     // Фон области чата
    private static final Color TOOL_PANEL_COLOR = new Color(30, 39, 46);          // Панель инструментов
    private static final Color INPUT_BACKGROUND_COLOR = new Color(45, 52, 58);    // Поле ввода
    private static final Color INPUT_BORDER_COLOR = new Color(85, 89, 92);        // Граница поля ввода
    private static final Color INPUT_FOCUS_COLOR = new Color(0, 136, 204);        // Цвет фокуса
    private static final Color BUTTON_COLOR = new Color(0, 136, 204);             // Синий
    private static final Color BUTTON_HOVER_COLOR = new Color(0, 122, 184);       // Синий при наведении
    private static final Color MESSAGE_BUBBLE_CURRENT_COLOR = new Color(0, 136, 204);  // Пузырь текущего пользователя
    private static final Color MESSAGE_BUBBLE_OTHER_COLOR = new Color(45, 52, 58);     // Пузырь других пользователей
    private static final Color TEXT_PRIMARY_COLOR = new Color(255, 255, 255);     // Основной текст
    private static final Color TEXT_USERNAME_COLOR = new Color(100, 181, 246);    // Цвет имен пользователей
    private static final Color EVENT_TEXT_COLOR = new Color(158, 158, 158);       // Цвет системных сообщений
    private static final Color SCROLLBAR_TRACK_COLOR = new Color(45, 52, 58);     // Трек скроллбара
    private static final Color SCROLLBAR_THUMB_COLOR = new Color(85, 89, 92);     // Ползунок скроллбара

    private static final Font INPUT_AREA_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font USERNAME_TITLE_FONT = new Font("Segoe UI", Font.BOLD, 15);
    private static final Font MESSAGE_FONT = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font EVENT_FONT = new Font("Segoe UI", Font.ITALIC, 14);

    private final JFrame frame = new JFrame();
    private final JPanel chatPanel = new JPanel();
    private final JTextArea inputArea = new JTextArea();
    private final WorkSpaceListener listener = new WorkSpaceListener();
    private final Controller controller;

    public WorkSpaceFrame(Controller controller) {
        frame.setTitle(TITLE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosingListener(frame));
        frame.setContentPane(createContentPane());
        frame.setVisible(true);

        this.controller = controller;
        controller.addListener(listener);
        controller.handleDTO();
    }

    private JPanel createContentPane() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(BACKGROUND_COLOR);
        contentPane.add(createChatPanel(), BorderLayout.CENTER);
        contentPane.add(createToolPanel(), BorderLayout.SOUTH);
        return contentPane;
    }

    private JScrollPane createChatPanel() {
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(CHAT_BACKGROUND_COLOR);
        chatPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(chatPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Стилизация скроллбара
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CHAT_BACKGROUND_COLOR);
        scrollPane.getVerticalScrollBar().setBackground(SCROLLBAR_TRACK_COLOR);
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = SCROLLBAR_THUMB_COLOR;
                this.trackColor = SCROLLBAR_TRACK_COLOR;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new java.awt.Dimension(0, 0));
                button.setMinimumSize(new java.awt.Dimension(0, 0));
                button.setMaximumSize(new java.awt.Dimension(0, 0));
                return button;
            }
        });

        return scrollPane;
    }

    private JPanel createToolPanel() {
        JPanel toolPanel = new JPanel(new BorderLayout());
        toolPanel.setBackground(TOOL_PANEL_COLOR);
        toolPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, INPUT_BORDER_COLOR),
                new EmptyBorder(15, 15, 15, 15)
        ));
        toolPanel.add(createInputArea(), BorderLayout.CENTER);
        toolPanel.add(createButtonGroup(), BorderLayout.EAST);

        return toolPanel;
    }

    private JScrollPane createInputArea() {
        inputArea.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        inputArea.setFont(INPUT_AREA_FONT);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setBackground(INPUT_BACKGROUND_COLOR);
        inputArea.setForeground(TEXT_PRIMARY_COLOR);
        inputArea.setCaretColor(TEXT_PRIMARY_COLOR);
        inputArea.setSelectionColor(INPUT_FOCUS_COLOR.darker());
        inputArea.setSelectedTextColor(TEXT_PRIMARY_COLOR);

        JScrollPane scrollPane = new JScrollPane(inputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setBorder(BorderFactory.createLineBorder(INPUT_BORDER_COLOR, 1));
        scrollPane.setBackground(INPUT_BACKGROUND_COLOR);

        // Эффекты фокуса для области ввода
        inputArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                scrollPane.setBorder(BorderFactory.createLineBorder(INPUT_FOCUS_COLOR, 2));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                scrollPane.setBorder(BorderFactory.createLineBorder(INPUT_BORDER_COLOR, 1));
            }
        });

        return scrollPane;
    }

    private JPanel createButtonGroup() {
        JButton sendButton = createToolButton(SEND_BUTTON_ICON_FILE, new SendButtonListener());
        JButton usersButton = createToolButton(USERS_BUTTON_ICON_FILE, new UsersButtonListener());

        JPanel buttonGroup = new JPanel(new GridLayout(1, 2, 8, 0));
        buttonGroup.setBackground(TOOL_PANEL_COLOR);
        buttonGroup.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        buttonGroup.add(sendButton);
        buttonGroup.add(usersButton);
        return buttonGroup;
    }

    private static JButton createToolButton(String iconFile, ActionListener listener) {
        JButton button = new JButton();
        button.setFont(BUTTON_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Попытка загрузить иконку, если не получается - используем текст
        try {
            URL buttonIconUrl = StartMenuFrame.class.getClassLoader().getResource(iconFile);
            if (buttonIconUrl != null) {
                Image buttonImage = Toolkit.getDefaultToolkit().createImage(buttonIconUrl);
                ImageIcon buttonIcon = new ImageIcon(
                        buttonImage.getScaledInstance(BUTTON_ICON_SIZE, BUTTON_ICON_SIZE, Image.SCALE_SMOOTH));
                button.setIcon(buttonIcon);
            } else {
                // Если иконка не найдена, используем текст
                if (iconFile.contains("send")) {
                    button.setText("Send");
                } else if (iconFile.contains("users")) {
                    button.setText("Users");
                }
                button.setForeground(TEXT_PRIMARY_COLOR);
            }
        } catch (Exception e) {
            // Fallback к тексту если что-то пошло не так с иконками
            if (iconFile.contains("send")) {
                button.setText("Send");
            } else if (iconFile.contains("users")) {
                button.setText("Users");
            }
            button.setForeground(TEXT_PRIMARY_COLOR);
        }

        // Эффекты наведения
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        button.addActionListener(listener);
        return button;
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = inputArea.getText().trim();
            if (message == null || message.isEmpty()) {
                return;
            }

            inputArea.setText("");
            inputArea.repaint();
            controller.sendNewMessage(message);
        }
    }

    private class UsersButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] users = controller.getUsers();
            SwingUtilities.invokeLater(() -> new UsersDialog(frame, users));
        }
    }

    private class WindowClosingListener extends WindowAdapter {
        private final JFrame owner;

        public WindowClosingListener(JFrame owner) {
            this.owner = owner;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            controller.logout();
            owner.dispose();
        }
    }

    private class WorkSpaceListener implements Listener {
        public WorkSpaceListener() {
        }

        @Override
        public void processEvent(Event event) {
            if (event instanceof ErrorEvent) {
                frame.dispose();
            } else if (event instanceof LoginEvent) {
                LoginEvent loginEvent = (LoginEvent) event;
                String eventMessage = String.format("%s присоединился к чату", loginEvent.username());
                addEventMessage(eventMessage);
                chatPanel.repaint();
            } else if (event instanceof NewMessageEvent) {
                NewMessageEvent newMessageEvent = (NewMessageEvent) event;
                addUserMessage(newMessageEvent.username(), newMessageEvent.message(),
                        newMessageEvent.isCurrentUser());
                chatPanel.repaint();
            } else if (event instanceof LogoutEvent) {
                LogoutEvent logoutEvent = (LogoutEvent) event;
                String eventMessage = String.format("%s покинул чат", logoutEvent.username());
                addEventMessage(eventMessage);
                chatPanel.repaint();
            }
        }

        private void addEventMessage(String message) {
            JPanel eventPanel = new JPanel();
            eventPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
            eventPanel.setBackground(CHAT_BACKGROUND_COLOR);
            eventPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

            JLabel eventMessageLabel = new JLabel(message);
            eventMessageLabel.setFont(EVENT_FONT);
            eventMessageLabel.setForeground(EVENT_TEXT_COLOR);
            eventMessageLabel.setBackground(new Color(45, 52, 58, 100));
            eventMessageLabel.setOpaque(true);
            eventMessageLabel.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

            eventPanel.add(eventMessageLabel);
            chatPanel.add(eventPanel);
        }

        private void addUserMessage(String username, String message, boolean isCurrentUser) {
            JPanel messageContainer = new JPanel();
            messageContainer.setLayout(new BoxLayout(messageContainer, BoxLayout.Y_AXIS));
            messageContainer.setBackground(CHAT_BACKGROUND_COLOR);
            messageContainer.setBorder(BorderFactory.createEmptyBorder(4, 0, 12, 0));

            // Панель для имени пользователя
            JPanel usernamePanel = new JPanel(new java.awt.FlowLayout(
                    isCurrentUser ? java.awt.FlowLayout.RIGHT : java.awt.FlowLayout.LEFT, 0, 0));
            usernamePanel.setBackground(CHAT_BACKGROUND_COLOR);

            JLabel usernameLabel = new JLabel(username + (isCurrentUser ? " (Вы)" : ""));
            usernameLabel.setFont(USERNAME_TITLE_FONT);
            usernameLabel.setForeground(isCurrentUser ? new Color(129, 199, 132) : TEXT_USERNAME_COLOR);
            usernameLabel.setBorder(BorderFactory.createEmptyBorder(0, 12, 4, 12));

            usernamePanel.add(usernameLabel);
            messageContainer.add(usernamePanel);

            // Панель для сообщения
            JPanel messageBubblePanel = new JPanel(new java.awt.FlowLayout(
                    isCurrentUser ? java.awt.FlowLayout.RIGHT : java.awt.FlowLayout.LEFT, 0, 0));
            messageBubblePanel.setBackground(CHAT_BACKGROUND_COLOR);

            JLabel messageLabel = new JLabel();
            messageLabel.setText("<html><div style='padding: 8px 12px; max-width: 400px;'>" +
                    message.replace("<", "&lt;")
                            .replace(">", "&gt;")
                            .replace("\n", "<br/>") +
                    "</div></html>");
            messageLabel.setFont(MESSAGE_FONT);
            messageLabel.setForeground(TEXT_PRIMARY_COLOR);
            messageLabel.setBackground(isCurrentUser ? MESSAGE_BUBBLE_CURRENT_COLOR : MESSAGE_BUBBLE_OTHER_COLOR);
            messageLabel.setOpaque(true);
            messageLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(2, 8, 2, 8),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));

            messageBubblePanel.add(messageLabel);
            messageContainer.add(messageBubblePanel);

            chatPanel.add(messageContainer);

            // Автоскролл вниз
            SwingUtilities.invokeLater(() -> {
                JScrollPane scrollPane = (JScrollPane) chatPanel.getParent().getParent();
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
            });
        }
    }
}