package com.glebestraikh.chat.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class UserListView extends JDialog {
    private static final String TITLE = "Пользователи онлайн";
    private static final int WIDTH = 450;
    private static final int HEIGHT = 550;
    private static final Color DIALOG_BACKGROUND_COLOR = new Color(30, 39, 46);    // Фон диалога
    private static final Color USER_CARD_COLOR = new Color(45, 52, 58);           // Карточка пользователя
    private static final Color USER_CARD_HOVER_COLOR = new Color(55, 62, 68);     // Карточка при наведении
    private static final Color HEADER_COLOR = new Color(24, 33, 42);              // Цвет заголовка
    private static final Color BUTTON_COLOR = new Color(0, 136, 204);             // Синий
    private static final Color BUTTON_HOVER_COLOR = new Color(0, 122, 184);       // Синий при наведении
    private static final Color TEXT_PRIMARY_COLOR = new Color(255, 255, 255);     // Основной текст
    private static final Color TEXT_SECONDARY_COLOR = new Color(170, 170, 170);   // Вторичный текст
    private static final Color ONLINE_INDICATOR_COLOR = new Color(76, 175, 80);   // Зеленый индикатор онлайн
    private static final Color SCROLLBAR_TRACK_COLOR = new Color(45, 52, 58);     // Трек скроллбара
    private static final Color SCROLLBAR_THUMB_COLOR = new Color(85, 89, 92);     // Ползунок скроллбара

    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font USER_ENTRY_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font COUNT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private final String[] users;

    public UserListView(JFrame owner, String[] users) {
        super(owner, true);

        this.users = users;

        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setBackground(DIALOG_BACKGROUND_COLOR);
        setLocationRelativeTo(owner);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(DIALOG_BACKGROUND_COLOR);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createScrollableUserList(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(85, 89, 92)),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(TITLE);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_PRIMARY_COLOR);

        JLabel countLabel = new JLabel(String.format("(%d пользователей)", users.length));
        countLabel.setFont(COUNT_FONT);
        countLabel.setForeground(TEXT_SECONDARY_COLOR);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(countLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private JScrollPane createScrollableUserList() {
        JPanel userListPanel = createUserEntryArea();

        JScrollPane scrollPane = new JScrollPane(userListPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(DIALOG_BACKGROUND_COLOR);
        scrollPane.setBackground(DIALOG_BACKGROUND_COLOR);

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

    private JPanel createUserEntryArea() {
        JPanel userEntryArea = new JPanel();
        userEntryArea.setLayout(new BoxLayout(userEntryArea, BoxLayout.Y_AXIS));
        userEntryArea.setBackground(DIALOG_BACKGROUND_COLOR);
        userEntryArea.setBorder(new EmptyBorder(15, 15, 15, 15));

        if (users.length == 0) {
            JLabel emptyLabel = new JLabel("Нет пользователей онлайн");
            emptyLabel.setFont(USER_ENTRY_FONT);
            emptyLabel.setForeground(TEXT_SECONDARY_COLOR);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            emptyLabel.setHorizontalAlignment(JLabel.CENTER);
            userEntryArea.add(emptyLabel);
        } else {
            for (int i = 0; i < users.length; i++) {
                String user = users[i];
                JPanel userCard = createUserCard(user, i == users.length - 1);
                userEntryArea.add(userCard);
            }
        }

        return userEntryArea;
    }

    private JPanel createUserCard(String username, boolean isLast) {
        JPanel userCard = new JPanel(new BorderLayout());
        userCard.setBackground(USER_CARD_COLOR);
        userCard.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        userCard.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, userCard.getPreferredSize().height));

        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        userInfoPanel.setBackground(USER_CARD_COLOR);

        JLabel onlineIndicator = new JLabel("●");
        onlineIndicator.setForeground(ONLINE_INDICATOR_COLOR);
        onlineIndicator.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        onlineIndicator.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));

        JLabel userLabel = new JLabel(username);
        userLabel.setFont(USER_ENTRY_FONT);
        userLabel.setForeground(TEXT_PRIMARY_COLOR);

        userInfoPanel.add(onlineIndicator);
        userInfoPanel.add(userLabel);

        userCard.add(userInfoPanel, BorderLayout.WEST);

        userCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                userCard.setBackground(USER_CARD_HOVER_COLOR);
                userInfoPanel.setBackground(USER_CARD_HOVER_COLOR);
                userCard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                userCard.setBackground(USER_CARD_COLOR);
                userInfoPanel.setBackground(USER_CARD_COLOR);
                userCard.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });

        JPanel cardWrapper = new JPanel(new BorderLayout());
        cardWrapper.setBackground(DIALOG_BACKGROUND_COLOR);
        cardWrapper.add(userCard, BorderLayout.CENTER);

        if (!isLast) {
            cardWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        }

        return cardWrapper;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(DIALOG_BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(85, 89, 92)),
                new EmptyBorder(15, 20, 15, 20)
        ));

        JButton closeButton = new JButton("Закрыть");
        closeButton.setFont(BUTTON_FONT);
        closeButton.setForeground(TEXT_PRIMARY_COLOR);
        closeButton.setBackground(BUTTON_COLOR);
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(BUTTON_COLOR);
            }
        });

        closeButton.addActionListener(_ -> dispose());

        buttonPanel.add(closeButton);

        return buttonPanel;
    }
}