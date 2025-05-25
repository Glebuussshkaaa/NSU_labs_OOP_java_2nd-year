package com.glebestraikh.chat.client.gui;

import com.glebestraikh.chat.client.controller.ClientController;
import com.glebestraikh.chat.client.listener.Listener;
import com.glebestraikh.chat.client.listener.event.ErrorEvent;
import com.glebestraikh.chat.client.listener.event.Event;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class LoginView {
    private static final String TITLE = "XChat";
    private static final String SERVER_ADDRESS_FIELD_TITLE = "Server address: ";
    private static final String SERVER_PORT_FIELD_TITLE = "Server port: ";
    private static final String USERNAME_FIELD_TITLE = "Username: ";
    private static final String START_BUTTON_TITLE = "Start";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final int POPUP_DISPLAY = 5000;
    private static final Color BACKGROUND_COLOR = new Color(15, 20, 25);           // Темно-серый фон
    private static final Color CARD_BACKGROUND_COLOR = new Color(30, 39, 46);      // Цвет карточек
    private static final Color BUTTON_COLOR = new Color(0, 136, 204);             // Синий
    private static final Color BUTTON_HOVER_COLOR = new Color(0, 122, 184);       // Синий при наведении
    private static final Color INPUT_BACKGROUND_COLOR = new Color(45, 52, 58);    // Фон полей ввода
    private static final Color INPUT_BORDER_COLOR = new Color(85, 89, 92);        // Граница полей
    private static final Color INPUT_FOCUS_COLOR = new Color(0, 136, 204);        // Цвет фокуса
    private static final Color TEXT_PRIMARY_COLOR = new Color(255, 255, 255);     // Основной текст
    private static final Color TEXT_SECONDARY_COLOR = new Color(170, 170, 170);   // Вторичный текст
    private static final Color ERROR_BACKGROUND_COLOR = new Color(220, 53, 69);   // Красный для ошибок
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 50);
    private static final Font FIELD_TITLE_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font FIELD_TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font ERROR_MESSAGE_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private final JFrame frame = new JFrame();
    private final JTextField serverAddressTextField = new JTextField();
    private final JTextField serverPortTextField = new JTextField();
    private final JTextField usernameTextField = new JTextField();
    private final JLabel errorMessageLabel = new JLabel();
    private final ClientController controller = new ClientController();
    private final GridBagConstraints contentPaneConstraints = new GridBagConstraints();
    private final LoginViewListener listener = new LoginViewListener();

    public LoginView() {
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(createContentPane());
        frame.setVisible(true);

        controller.addListener(listener);
    }

    private JPanel createContentPane() {
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(BACKGROUND_COLOR);

        contentPaneConstraints.gridx = GridBagConstraints.RELATIVE;
        contentPaneConstraints.gridy = 0;

        addTitleLabel(contentPane);
        addInputPanel(contentPane);
        addStartButton(contentPane);
        addErrorLabel(contentPane);

        return contentPane;
    }

    private void addTitleLabel(JPanel contentPane) {
        JLabel titleLabel = new JLabel(TITLE);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_PRIMARY_COLOR);

        contentPaneConstraints.anchor = GridBagConstraints.CENTER;
        contentPaneConstraints.insets = new Insets(60, 0, 40, 0);

        contentPane.add(titleLabel, contentPaneConstraints);
        contentPaneConstraints.gridy++;
    }

    private void addInputPanel(JPanel contentPane) {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(CARD_BACKGROUND_COLOR);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(INPUT_BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints inputConstraints = new GridBagConstraints();
        inputConstraints.gridx = 0;
        inputConstraints.gridy = 0;
        inputConstraints.insets = new Insets(0, 0, 20, 0);
        inputConstraints.anchor = GridBagConstraints.WEST;
        inputConstraints.fill = GridBagConstraints.HORIZONTAL;

        addModernField(SERVER_ADDRESS_FIELD_TITLE, serverAddressTextField, inputPanel, inputConstraints);
        inputConstraints.gridy++;
        addModernField(SERVER_PORT_FIELD_TITLE, serverPortTextField, inputPanel, inputConstraints);
        inputConstraints.gridy++;
        inputConstraints.insets = new Insets(0, 0, 0, 0);
        addModernField(USERNAME_FIELD_TITLE, usernameTextField, inputPanel, inputConstraints);

        contentPaneConstraints.anchor = GridBagConstraints.CENTER;
        contentPaneConstraints.insets = new Insets(0, 50, 40, 50);
        contentPaneConstraints.fill = GridBagConstraints.HORIZONTAL;

        contentPane.add(inputPanel, contentPaneConstraints);
        contentPaneConstraints.gridy++;
    }

    private void addModernField(String fieldTitle, JTextField textField, JPanel inputPanel, GridBagConstraints constraints) {
        JLabel fieldLabel = new JLabel(fieldTitle);
        fieldLabel.setFont(FIELD_TITLE_FONT);
        fieldLabel.setForeground(TEXT_SECONDARY_COLOR);

        constraints.insets = new Insets(constraints.insets.top, 0, 5, 0);
        inputPanel.add(fieldLabel, constraints);

        constraints.gridy++;

        textField.setFont(FIELD_TEXT_FONT);
        textField.setBackground(INPUT_BACKGROUND_COLOR);
        textField.setForeground(TEXT_PRIMARY_COLOR);
        textField.setCaretColor(TEXT_PRIMARY_COLOR);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(INPUT_BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(INPUT_FOCUS_COLOR, 2),
                        BorderFactory.createEmptyBorder(11, 14, 11, 14)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(INPUT_BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15)
                ));
            }
        });

        constraints.insets = new Insets(0, 0, 20, 0);
        inputPanel.add(textField, constraints);

        constraints.gridy++;
    }

    private void addStartButton(JPanel contentPane) {
        JButton startButton = new JButton(START_BUTTON_TITLE);
        startButton.setFont(BUTTON_FONT);
        startButton.setForeground(TEXT_PRIMARY_COLOR);
        startButton.setBackground(BUTTON_COLOR);
        startButton.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        startButton.setFocusPainted(false);
        startButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(BUTTON_COLOR);
            }
        });

        startButton.addActionListener(new StartButtonListener());

        contentPaneConstraints.anchor = GridBagConstraints.CENTER;
        contentPaneConstraints.insets = new Insets(0, 0, 30, 0);
        contentPaneConstraints.fill = GridBagConstraints.NONE;

        contentPane.add(startButton, contentPaneConstraints);
        contentPaneConstraints.gridy++;
    }

    private void addErrorLabel(JPanel contentPane) {
        errorMessageLabel.setFont(ERROR_MESSAGE_FONT);
        errorMessageLabel.setForeground(TEXT_PRIMARY_COLOR);
        errorMessageLabel.setOpaque(false);
        errorMessageLabel.setHorizontalAlignment(JLabel.CENTER);
        errorMessageLabel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        errorMessageLabel.setText(" ");

        contentPaneConstraints.anchor = GridBagConstraints.CENTER;
        contentPaneConstraints.insets = new Insets(0, 50, 0, 50);
        contentPaneConstraints.fill = GridBagConstraints.HORIZONTAL;

        contentPane.add(errorMessageLabel, contentPaneConstraints);
        contentPaneConstraints.gridy++;
    }

    private void showErrorMessage(String message) {
        errorMessageLabel.setOpaque(true);
        errorMessageLabel.setBackground(ERROR_BACKGROUND_COLOR);
        errorMessageLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ERROR_BACKGROUND_COLOR.darker(), 1),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        errorMessageLabel.setText(message);

        Timer timer = new Timer(POPUP_DISPLAY, _ -> {
            errorMessageLabel.setOpaque(false);
            errorMessageLabel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
            errorMessageLabel.setText(" ");
        });
        timer.setRepeats(false);
        timer.start();
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String serverAddressText = serverAddressTextField.getText().trim();
            String serverPortText = serverPortTextField.getText().trim();
            String username = usernameTextField.getText().trim();

            if (serverAddressText.isEmpty()) {
                showErrorMessage("Fill in the field \"Server address\"");
                return;
            }
            if (serverPortText.isEmpty()) {
                showErrorMessage("Fill in the field \"Server port\"");
                return;
            }
            if (username.isEmpty()) {
                showErrorMessage("Fill in the field \"Username\"");
                return;
            }

            InetAddress serverAddress;
            try {
                serverAddress = InetAddress.getByName(serverAddressText);
            } catch (UnknownHostException ex) {
                showErrorMessage("IP address of a server could not be determined");
                return;
            }

            int serverPort;
            try {
                serverPort = Integer.parseInt(serverPortText);
            } catch (NumberFormatException ex) {
                showErrorMessage("Port of a server is invalid number");
                return;
            }

            if (!controller.connect(serverAddress, serverPort)) {
                return;
            }
            if (!controller.login(username)) {
                return;
            }

            SwingUtilities.invokeLater(() -> {
                frame.dispose();
                controller.removeListener(listener);
                new ChatView(controller);
            });
        }
    }

    private class LoginViewListener implements Listener {
        @Override
        public void processEvent(Event event) {
            if (event instanceof ErrorEvent) {
                showErrorMessage(((ErrorEvent) event).reason());
            }
        }
    }
}