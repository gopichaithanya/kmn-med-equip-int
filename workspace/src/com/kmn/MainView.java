/*
 * MainView.java
 */

package com.kmn;

import com.kmn.controller.UserSession;
import com.kmn.gui.setup.SetupDatabase;
import com.kmn.gui.setup.SetupEquipment;
import com.kmn.gui.setup.SetupServer;
import com.kmn.gui.workspace.OpenDialog;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The application's main frame.
 */
public class MainView extends FrameView {

    public MainView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();

        windowImage = getResourceMap().getImageIcon("Window.idleIcon").getImage();
        getFrame().setIconImage(windowImage);
        

        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = MainApps.getApplication().getMainFrame();
            aboutBox = new About(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        MainApps.getApplication().show(aboutBox);
    }

    @Action
    public void showLoginBox() {
        if (loginBox == null) {
            JFrame mainFrame = MainApps.getApplication().getMainFrame();
            loginBox = new Login(this, true);
            loginBox.setLocationRelativeTo(mainFrame);
        }
        MainApps.getApplication().show(loginBox);
    }

    @Action
    public void logoutUser() {
        int confirm = JOptionPane.showConfirmDialog(this.getComponent(), "Are you sure to logout ?", "Logout", 2);
        if(confirm == 0)
            loginBox.getController().logoutUser();
    }

    @Action
    public void changeLogin() {
        if(UserSession.getInstance().getSecuser() == null) showLoginBox();
        else logoutUser();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        panelBody = new javax.swing.JPanel();
        tabMain = new javax.swing.JTabbedPane();
        panelWelcome = new javax.swing.JPanel();
        toolbarWelcome = new javax.swing.JToolBar();
        buttonLogin2 = new javax.swing.JButton();
        panelInfo = new javax.swing.JPanel();
        labelKmn = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        loginMenu = new javax.swing.JMenu();
        loginMenuItem = new javax.swing.JMenuItem();
        logoutMenuItem = new javax.swing.JMenuItem();
        sepUser = new javax.swing.JPopupMenu.Separator();
        profileMenuItem = new javax.swing.JMenuItem();
        setupMenu = new javax.swing.JMenu();
        equiMenuItem = new javax.swing.JMenuItem();
        dbMenuItem = new javax.swing.JMenuItem();
        serverMenuItem = new javax.swing.JMenuItem();
        sepTools = new javax.swing.JPopupMenu.Separator();
        statusMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        userLoginLabel = new javax.swing.JLabel();
        userIconLabel = new javax.swing.JLabel();
        toolBar = new javax.swing.JToolBar();
        btnOpen = new javax.swing.JButton();
        btnFind = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        separatorToolBar = new javax.swing.JToolBar.Separator();
        btnEquipment = new javax.swing.JButton();
        buttonDb = new javax.swing.JButton();
        buttonServer = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnLogin = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.kmn.MainApps.class).getContext().getResourceMap(MainView.class);
        mainPanel.setBackground(resourceMap.getColor("mainPanel.background")); // NOI18N
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(800, 400));

        panelBody.setBackground(resourceMap.getColor("panelBody.background")); // NOI18N
        panelBody.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panelBody.setName("panelBody"); // NOI18N

        tabMain.setAlignmentX(0.0F);
        tabMain.setAlignmentY(0.0F);
        tabMain.setName("tabMain"); // NOI18N

        panelWelcome.setName("panelWelcome"); // NOI18N

        toolbarWelcome.setRollover(true);
        toolbarWelcome.setName("toolbarWelcome"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.kmn.MainApps.class).getContext().getActionMap(MainView.class, this);
        buttonLogin2.setAction(actionMap.get("showLoginBox")); // NOI18N
        buttonLogin2.setIcon(resourceMap.getIcon("buttonLogin2.icon")); // NOI18N
        buttonLogin2.setText(resourceMap.getString("buttonLogin2.text")); // NOI18N
        buttonLogin2.setToolTipText(resourceMap.getString("buttonLogin2.toolTipText")); // NOI18N
        buttonLogin2.setFocusable(false);
        buttonLogin2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonLogin2.setName("buttonLogin2"); // NOI18N
        buttonLogin2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbarWelcome.add(buttonLogin2);

        panelInfo.setBackground(resourceMap.getColor("panelInfo.background")); // NOI18N
        panelInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelInfo.setName("panelInfo"); // NOI18N

        labelKmn.setIcon(resourceMap.getIcon("labelKmn.icon")); // NOI18N
        labelKmn.setText(resourceMap.getString("labelKmn.text")); // NOI18N
        labelKmn.setName("labelKmn"); // NOI18N

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                .addContainerGap(447, Short.MAX_VALUE)
                .addComponent(labelKmn)
                .addContainerGap())
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                .addContainerGap(182, Short.MAX_VALUE)
                .addComponent(labelKmn)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelWelcomeLayout = new javax.swing.GroupLayout(panelWelcome);
        panelWelcome.setLayout(panelWelcomeLayout);
        panelWelcomeLayout.setHorizontalGroup(
            panelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbarWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
            .addComponent(panelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelWelcomeLayout.setVerticalGroup(
            panelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWelcomeLayout.createSequentialGroup()
                .addComponent(toolbarWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabMain.addTab(resourceMap.getString("panelWelcome.TabConstraints.tabTitle"), resourceMap.getIcon("panelWelcome.TabConstraints.tabIcon"), panelWelcome); // NOI18N

        javax.swing.GroupLayout panelBodyLayout = new javax.swing.GroupLayout(panelBody);
        panelBody.setLayout(panelBodyLayout);
        panelBodyLayout.setHorizontalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabMain, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
        );
        panelBodyLayout.setVerticalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabMain, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menuBar.setName("menuBar"); // NOI18N
        menuBar.setPreferredSize(new java.awt.Dimension(800, 21));

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setText(resourceMap.getString("openMenuItem.text")); // NOI18N
        openMenuItem.setEnabled(false);
        openMenuItem.setName("openMenuItem"); // NOI18N
        fileMenu.add(openMenuItem);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N

        cutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutMenuItem.setText(resourceMap.getString("cutMenuItem.text")); // NOI18N
        cutMenuItem.setEnabled(false);
        cutMenuItem.setName("cutMenuItem"); // NOI18N
        editMenu.add(cutMenuItem);

        copyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyMenuItem.setText(resourceMap.getString("copyMenuItem.text")); // NOI18N
        copyMenuItem.setEnabled(false);
        copyMenuItem.setName("copyMenuItem"); // NOI18N
        editMenu.add(copyMenuItem);

        pasteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pasteMenuItem.setText(resourceMap.getString("pasteMenuItem.text")); // NOI18N
        pasteMenuItem.setEnabled(false);
        pasteMenuItem.setName("pasteMenuItem"); // NOI18N
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        deleteMenuItem.setText(resourceMap.getString("deleteMenuItem.text")); // NOI18N
        deleteMenuItem.setEnabled(false);
        deleteMenuItem.setName("deleteMenuItem"); // NOI18N
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        loginMenu.setText(resourceMap.getString("loginMenu.text")); // NOI18N
        loginMenu.setName("loginMenu"); // NOI18N

        loginMenuItem.setAction(actionMap.get("showLoginBox")); // NOI18N
        loginMenuItem.setText(resourceMap.getString("loginMenuItem.text")); // NOI18N
        loginMenuItem.setName("loginMenuItem"); // NOI18N
        loginMenu.add(loginMenuItem);

        logoutMenuItem.setAction(actionMap.get("logoutUser")); // NOI18N
        logoutMenuItem.setText(resourceMap.getString("logoutMenuItem.text")); // NOI18N
        logoutMenuItem.setName("logoutMenuItem"); // NOI18N
        loginMenu.add(logoutMenuItem);

        sepUser.setName("sepUser"); // NOI18N
        loginMenu.add(sepUser);

        profileMenuItem.setText(resourceMap.getString("profileMenuItem.text")); // NOI18N
        profileMenuItem.setEnabled(false);
        profileMenuItem.setName("profileMenuItem"); // NOI18N
        loginMenu.add(profileMenuItem);

        menuBar.add(loginMenu);

        setupMenu.setText(resourceMap.getString("setupMenu.text")); // NOI18N
        setupMenu.setName("setupMenu"); // NOI18N

        equiMenuItem.setAction(actionMap.get("openSetupEquipment")); // NOI18N
        equiMenuItem.setText(resourceMap.getString("equiMenuItem.text")); // NOI18N
        equiMenuItem.setName("equiMenuItem"); // NOI18N
        setupMenu.add(equiMenuItem);

        dbMenuItem.setAction(actionMap.get("openSetupDB")); // NOI18N
        dbMenuItem.setText(resourceMap.getString("dbMenuItem.text")); // NOI18N
        dbMenuItem.setName("dbMenuItem"); // NOI18N
        setupMenu.add(dbMenuItem);

        serverMenuItem.setAction(actionMap.get("openSetupServer")); // NOI18N
        serverMenuItem.setText(resourceMap.getString("serverMenuItem.text")); // NOI18N
        serverMenuItem.setName("serverMenuItem"); // NOI18N
        setupMenu.add(serverMenuItem);

        sepTools.setName("sepTools"); // NOI18N
        setupMenu.add(sepTools);

        statusMenuItem.setText(resourceMap.getString("statusMenuItem.text")); // NOI18N
        statusMenuItem.setName("statusMenuItem"); // NOI18N
        setupMenu.add(statusMenuItem);

        menuBar.add(setupMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N
        statusPanel.setPreferredSize(new java.awt.Dimension(800, 27));

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        userLoginLabel.setFont(resourceMap.getFont("userLoginLabel.font")); // NOI18N
        userLoginLabel.setText(resourceMap.getString("userLoginLabel.text")); // NOI18N
        userLoginLabel.setName("userLoginLabel"); // NOI18N

        userIconLabel.setIcon(resourceMap.getIcon("userIconLabel.icon")); // NOI18N
        userIconLabel.setText(resourceMap.getString("userIconLabel.text")); // NOI18N
        userIconLabel.setName("userIconLabel"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(statusMessageLabel))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(userIconLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(userLoginLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 351, Short.MAX_VALUE)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statusAnimationLabel)))
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusMessageLabel)
                            .addComponent(statusAnimationLabel)
                            .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(userLoginLabel)
                            .addComponent(userIconLabel))
                        .addContainerGap())))
        );

        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        toolBar.setMaximumSize(new java.awt.Dimension(70, 27));
        toolBar.setMinimumSize(new java.awt.Dimension(70, 27));
        toolBar.setName("toolBar"); // NOI18N
        toolBar.setPreferredSize(new java.awt.Dimension(800, 34));

        btnOpen.setAction(actionMap.get("openWorkspace")); // NOI18N
        btnOpen.setIcon(resourceMap.getIcon("btnOpen.icon")); // NOI18N
        btnOpen.setText(resourceMap.getString("btnOpen.text")); // NOI18N
        btnOpen.setToolTipText(resourceMap.getString("btnOpen.toolTipText")); // NOI18N
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setName("btnOpen"); // NOI18N
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btnOpen);

        btnFind.setIcon(resourceMap.getIcon("btnFind.icon")); // NOI18N
        btnFind.setText(resourceMap.getString("btnFind.text")); // NOI18N
        btnFind.setToolTipText(resourceMap.getString("btnFind.toolTipText")); // NOI18N
        btnFind.setEnabled(false);
        btnFind.setFocusable(false);
        btnFind.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFind.setName("btnFind"); // NOI18N
        btnFind.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btnFind);

        btnRefresh.setIcon(resourceMap.getIcon("btnRefresh.icon")); // NOI18N
        btnRefresh.setText(resourceMap.getString("btnRefresh.text")); // NOI18N
        btnRefresh.setToolTipText(resourceMap.getString("btnRefresh.toolTipText")); // NOI18N
        btnRefresh.setEnabled(false);
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setName("btnRefresh"); // NOI18N
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btnRefresh);

        separatorToolBar.setName("separatorToolBar"); // NOI18N
        separatorToolBar.setSeparatorSize(new java.awt.Dimension(2, 34));
        toolBar.add(separatorToolBar);

        btnEquipment.setAction(actionMap.get("openSetupEquipment")); // NOI18N
        btnEquipment.setIcon(resourceMap.getIcon("btnEquipment.icon")); // NOI18N
        btnEquipment.setText(resourceMap.getString("btnEquipment.text")); // NOI18N
        btnEquipment.setToolTipText(resourceMap.getString("btnEquipment.toolTipText")); // NOI18N
        btnEquipment.setFocusable(false);
        btnEquipment.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEquipment.setMaximumSize(new java.awt.Dimension(37, 35));
        btnEquipment.setMinimumSize(new java.awt.Dimension(37, 35));
        btnEquipment.setName("btnEquipment"); // NOI18N
        btnEquipment.setPreferredSize(new java.awt.Dimension(37, 35));
        btnEquipment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btnEquipment);

        buttonDb.setAction(actionMap.get("openSetupDB")); // NOI18N
        buttonDb.setIcon(resourceMap.getIcon("buttonDb.icon")); // NOI18N
        buttonDb.setText(resourceMap.getString("buttonDb.text")); // NOI18N
        buttonDb.setToolTipText(resourceMap.getString("buttonDb.toolTipText")); // NOI18N
        buttonDb.setFocusable(false);
        buttonDb.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonDb.setName("buttonDb"); // NOI18N
        buttonDb.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(buttonDb);

        buttonServer.setAction(actionMap.get("openSetupServer")); // NOI18N
        buttonServer.setIcon(resourceMap.getIcon("buttonServer.icon")); // NOI18N
        buttonServer.setText(resourceMap.getString("buttonServer.text")); // NOI18N
        buttonServer.setToolTipText(resourceMap.getString("buttonServer.toolTipText")); // NOI18N
        buttonServer.setFocusable(false);
        buttonServer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonServer.setName("buttonServer"); // NOI18N
        buttonServer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(buttonServer);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jSeparator1.setSeparatorSize(new java.awt.Dimension(2, 34));
        toolBar.add(jSeparator1);

        btnLogin.setAction(actionMap.get("changeLogin")); // NOI18N
        btnLogin.setIcon(resourceMap.getIcon("btnLogin.icon")); // NOI18N
        btnLogin.setText(resourceMap.getString("btnLogin.text")); // NOI18N
        btnLogin.setToolTipText(resourceMap.getString("btnLogin.toolTipText")); // NOI18N
        btnLogin.setFocusable(false);
        btnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin.setName("btnLogin"); // NOI18N
        btnLogin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btnLogin);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
        setToolBar(toolBar);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEquipment;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton buttonDb;
    private javax.swing.JButton buttonLogin2;
    private javax.swing.JButton buttonServer;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem dbMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem equiMenuItem;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JLabel labelKmn;
    private javax.swing.JMenu loginMenu;
    private javax.swing.JMenuItem loginMenuItem;
    private javax.swing.JMenuItem logoutMenuItem;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JPanel panelBody;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelWelcome;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem profileMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JPopupMenu.Separator sepTools;
    private javax.swing.JPopupMenu.Separator sepUser;
    private javax.swing.JToolBar.Separator separatorToolBar;
    private javax.swing.JMenuItem serverMenuItem;
    private javax.swing.JMenu setupMenu;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JMenuItem statusMenuItem;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTabbedPane tabMain;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JToolBar toolbarWelcome;
    private javax.swing.JLabel userIconLabel;
    private javax.swing.JLabel userLoginLabel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private final Image windowImage;

    private JDialog aboutBox;
    private Login loginBox;
    private SetupDatabase setupDbBox;
    private SetupServer setupServerBox;
    private SetupEquipment setupEquipmentBox;
    private OpenDialog openBox;

    public JLabel getUserLoginLabel() {
        return userLoginLabel;
    }

    public void setUserLoginLabel(JLabel userLoginLabel) {
        this.userLoginLabel = userLoginLabel;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JButton getBtnFind() {
        return btnFind;
    }

    public JButton getBtnOpen() {
        return btnOpen;
    }

    public JButton getBtnRefresh() {
        return btnRefresh;
    }

    public JMenuItem getOpenMenuItem() {
        return openMenuItem;
    }

    public JMenuItem getLoginMenuItem() {
        return loginMenuItem;
    }

    public JMenuItem getLogoutMenuItem() {
        return logoutMenuItem;
    }

    public JMenuItem getProfileMenuItem() {
        return profileMenuItem;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JButton getButtonLogin2() {
        return buttonLogin2;
    }

    public JTabbedPane getTabMain() {
        return tabMain;
    }

    @Action
    public void openSetupDB() {
        if (setupDbBox == null) {
            JFrame mainFrame = MainApps.getApplication().getMainFrame();
            setupDbBox = new SetupDatabase(this, true);
            setupDbBox.setLocationRelativeTo(mainFrame);
        }
        MainApps.getApplication().show(setupDbBox);
    }

    @Action
    public void openSetupServer() {
        if (setupServerBox == null) {
            JFrame mainFrame = MainApps.getApplication().getMainFrame();
            setupServerBox = new SetupServer(this, true);
            setupServerBox.setLocationRelativeTo(mainFrame);
        }
        MainApps.getApplication().show(setupServerBox);
    }

    @Action
    public void openSetupEquipment() {
        if (setupEquipmentBox == null) {
            JFrame mainFrame = MainApps.getApplication().getMainFrame();
            setupEquipmentBox = new SetupEquipment(this, true);
            setupEquipmentBox.setLocationRelativeTo(mainFrame);
        }
        MainApps.getApplication().show(setupEquipmentBox);
    }

    @Action
    public void openWorkspace() {
        if (openBox == null) {
            JFrame mainFrame = MainApps.getApplication().getMainFrame();
            openBox = new OpenDialog(this, true);
            openBox.setLocationRelativeTo(mainFrame);
        }
        MainApps.getApplication().show(openBox);
    }
}
