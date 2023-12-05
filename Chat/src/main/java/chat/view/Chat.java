package chat.view;

import chat.model.Cliente;
import chat.model.Mensagem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author jota
 */
public class Chat extends javax.swing.JFrame {

    private DefaultListModel<Cliente> usuarios;
    private DefaultListModel<String> mensagens;
    private DefaultComboBoxModel<String> destinatarios;

    private Cliente cliente;
    private SwingWorker atualizaClientes;
    private SwingWorker atualizaMensagens;

    public Chat() {
        initComponents();

        config();
        
        try {
            criarThreads();
        } catch (InterruptedException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void config() {
        this.setLocationRelativeTo(null);
        this.usuarios = new DefaultListModel<>();
        this.mensagens = new DefaultListModel<>();
        this.destinatarios = new DefaultComboBoxModel<>();

        this.destinatarios.addElement("Todo mundo");

        this.userList.setModel(usuarios);
        this.msgList.setModel(mensagens);
        this.comboBoxUser.setModel(destinatarios);
    }

    private void criarThreads() throws InterruptedException {
        this.atualizaClientes = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (cliente.isConectado()) {
                    try {
                        Mensagem mensagem = new Mensagem(cliente.getNome(), "");
                        mensagem.setAction(Mensagem.LISTAR_USERS);

                        cliente.enviar_mensagem(mensagem);
                        ArrayList<Cliente> clientes = (ArrayList<Cliente>) cliente.receber_mensagem();

                        SwingUtilities.invokeLater(() -> {
                            usuarios.clear();
                            usuarios.addAll(clientes);
                            listarUsuarios(clientes);
                        });

                        Thread.sleep(1000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                return null;
            }
        };

        this.atualizaMensagens = new SwingWorker<Void, Mensagem>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (cliente.isConectado()) {
                    try {
                        Mensagem mensagem = (Mensagem) cliente.receber_mensagem();

                        SwingUtilities.invokeLater(() -> {
                            if (mensagem.getIdDestinatario() != 0) {
                                mensagens.addElement("[Privada] " + mensagem.toString());
                            } else {
                                mensagens.addElement(mensagem.toString());
                            }
                        });

                        Thread.sleep(200);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                return null;
            }
        };
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        userList = new javax.swing.JList<>();
        msgTxt = new javax.swing.JTextField();
        enviarBtn = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        listarBtn = new javax.swing.JButton();
        listarDmBtn = new javax.swing.JButton();
        connectBtn = new javax.swing.JButton();
        disconnectBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        nomeTxt = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        msgList = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        carregarBtn = new javax.swing.JButton();
        comboBoxUser = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(234, 234, 234));
        jPanel1.setToolTipText("");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        userList.setBackground(new java.awt.Color(255, 255, 255));
        userList.setBorder(null);
        userList.setEnabled(false);
        jScrollPane2.setViewportView(userList);

        msgTxt.setBackground(new java.awt.Color(255, 255, 255));
        msgTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        msgTxt.setForeground(new java.awt.Color(153, 153, 153));
        msgTxt.setText("  Escreva sua mensagem");
        msgTxt.setToolTipText("");
        msgTxt.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        msgTxt.setOpaque(true);
        msgTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                msgTxtKeyPressed(evt);
            }
        });

        enviarBtn.setBackground(new java.awt.Color(255, 255, 255));
        enviarBtn.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        enviarBtn.setForeground(new java.awt.Color(0, 0, 0));
        enviarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/message-sent.png"))); // NOI18N
        enviarBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        enviarBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        enviarBtn.setEnabled(false);
        enviarBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        enviarBtn.setIconTextGap(10);
        enviarBtn.setOpaque(true);
        enviarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarBtnActionPerformed(evt);
            }
        });

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        listarBtn.setBackground(new java.awt.Color(255, 255, 255));
        listarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/list-messages.png"))); // NOI18N
        listarBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        listarBtn.setEnabled(false);
        listarBtn.setName("listar mensagens"); // NOI18N

        listarDmBtn.setBackground(new java.awt.Color(255, 255, 255));
        listarDmBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/list-private.png"))); // NOI18N
        listarDmBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        listarDmBtn.setEnabled(false);

        connectBtn.setBackground(new java.awt.Color(234, 234, 234));
        connectBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/connect.png"))); // NOI18N
        connectBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        connectBtn.setFocusCycleRoot(true);
        connectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectBtnActionPerformed(evt);
            }
        });

        disconnectBtn.setBackground(new java.awt.Color(234, 234, 234));
        disconnectBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/disconnect.png"))); // NOI18N
        disconnectBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        disconnectBtn.setFocusCycleRoot(true);
        disconnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectBtnActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Usuários");

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        nomeTxt.setBackground(new java.awt.Color(234, 234, 234));
        nomeTxt.setFont(new java.awt.Font("Comic Sans MS", 2, 20)); // NOI18N
        nomeTxt.setForeground(new java.awt.Color(0, 0, 0));
        nomeTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nomeTxt.setText("Nome do usuário");
        nomeTxt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        nomeTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nomeTxtKeyPressed(evt);
            }
        });

        msgList.setBackground(new java.awt.Color(255, 255, 255));
        msgList.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        msgList.setForeground(new java.awt.Color(0, 0, 0));
        msgList.setToolTipText("");
        msgList.setEnabled(false);
        jScrollPane3.setViewportView(msgList);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chat-settings.png"))); // NOI18N
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Configurações");

        carregarBtn.setBackground(new java.awt.Color(255, 255, 255));
        carregarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/private-sent.png"))); // NOI18N
        carregarBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        carregarBtn.setEnabled(false);

        comboBoxUser.setBackground(new java.awt.Color(255, 255, 255));
        comboBoxUser.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        comboBoxUser.setForeground(new java.awt.Color(0, 0, 0));
        comboBoxUser.setMaximumRowCount(3);
        comboBoxUser.setEnabled(false);
        comboBoxUser.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxUserItemStateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Enviar mensagem para:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(listarDmBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                                    .addComponent(listarBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(carregarBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                                .addGap(25, 25, 25))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(nomeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)))
                        .addComponent(connectBtn)
                        .addGap(18, 18, 18)
                        .addComponent(disconnectBtn)
                        .addGap(43, 43, 43))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(msgTxt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enviarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(comboBoxUser, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(25, 25, 25))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(355, 355, 355)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(355, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 25, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(nomeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(disconnectBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(connectBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(msgTxt)
                                    .addComponent(enviarBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                                .addGap(8, 8, 8))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboBoxUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)))
                .addGap(10, 10, 10))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(145, 145, 145)
                .addComponent(listarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(listarDmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(carregarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(222, 222, 222)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(223, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //-------------> Métodos úteis <-------------\\
    
    public boolean conectar() {
        try {
            String nome = nomeTxt.getText();
            this.cliente = new Cliente("192.168.2.165", 15500, nome);

            Mensagem mensagem = new Mensagem(nome, "");
            mensagem.setAction(Mensagem.CONECTAR);

            this.cliente.enviar_mensagem(mensagem);

            String resposta = (String) this.cliente.receber_mensagem();
            if (resposta.equals("SUCESSO")) {
                this.atualizaClientes.execute();
                this.atualizaMensagens.execute();
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Esse usuário já está online");
                return false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Falha na conexão", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean desconectar() {
        try {
            String nome = this.cliente.getNome();
            Mensagem mensagem = new Mensagem(nome, "");
            mensagem.setAction(Mensagem.DESCONECTAR);

            this.cliente.enviar_mensagem(mensagem);

            this.atualizaClientes.wait();
            this.atualizaMensagens.wait();
            this.cliente.finalizar();
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Algo deu errado!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Algo deu errado!", "Erro", JOptionPane.ERROR_MESSAGE);
        } 
        
        return false;
    }

    public boolean enviarMensagem() {
        try {
            if (this.comboBoxUser.getSelectedIndex() != 0) {
                String nome = this.cliente.getNome();
                String texto = this.msgTxt.getText();
                String destinatario = (String) this.comboBoxUser.getSelectedItem();
                
                Mensagem mensagem = new Mensagem(nome, texto, destinatario);
                mensagem.setAction(Mensagem.ENVIAR_DM);
                this.cliente.enviar_mensagem(mensagem);
                
                return this.cliente.receber_mensagem() == "SUCESSO";
            } else {
                String nome = this.cliente.getNome();
                String texto = this.msgTxt.getText();

                Mensagem mensagem = new Mensagem(nome, texto);
                mensagem.setIdDestinatario(0);
                mensagem.setAction(Mensagem.ENVIAR_GERAL);
                this.cliente.enviar_mensagem(mensagem);
                
                return this.cliente.receber_mensagem() == "SUCESSO";
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    public void listarUsuarios(ArrayList<Cliente> clientes) {
        this.destinatarios.removeAllElements();
        this.destinatarios.addElement("Para todo mundo");

        for (Cliente c : clientes) {
            if (!c.getNome().equals(this.cliente.getNome())) {
                this.destinatarios.addElement(c.getNome());
            }
        }
    }

    //-------------> Eventos <-------------\\

    private void enviarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarBtnActionPerformed
        if (!this.msgTxt.getText().isEmpty()) {
            if (!this.enviarMensagem()) {
                JOptionPane.showMessageDialog(this, "Não foi possível enviar a mensagem!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Escreva alguma coisa!",
                    "Erro", JOptionPane.WARNING_MESSAGE);
        }
        
        this.msgTxt.setText("");
    }//GEN-LAST:event_enviarBtnActionPerformed

    private void nomeTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nomeTxtKeyPressed
        if (this.nomeTxt.getText().equals("Nome do usuário")) {
            this.nomeTxt.setText("");
        }
    }//GEN-LAST:event_nomeTxtKeyPressed

    private void connectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectBtnActionPerformed
        if (this.nomeTxt.getText().isEmpty()) {
            this.nomeTxt.setText("Escreva seu nome!!");
        } else {
            if (this.conectar()) {
                this.enableChat(true);
                this.connectBtn.setEnabled(false);
            } else {
                this.disconnectBtnActionPerformed(evt);
            }

            this.nomeTxt.setEditable(false);
            this.connectBtn.setEnabled(false);
        }
    }//GEN-LAST:event_connectBtnActionPerformed

    private void comboBoxUserItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxUserItemStateChanged
        if (this.comboBoxUser.getSelectedIndex() != 0) {
            String nome = (String) this.comboBoxUser.getSelectedItem();
            this.msgTxt.setText("  Escreva sua mensagem para " + nome);
        } else {
            this.msgTxt.setText("  Escreva sua mensagem");
        }
    }//GEN-LAST:event_comboBoxUserItemStateChanged

    private void disconnectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectBtnActionPerformed
        if (this.desconectar()) {
            this.disconnectBtn.setEnabled(false);
            this.enableChat(false);
        }
    }//GEN-LAST:event_disconnectBtnActionPerformed

    private void msgTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_msgTxtKeyPressed
        if (this.msgTxt.getText().equals("  Escreva sua mensagem")) {
            this.msgTxt.setText("");
        }
    }//GEN-LAST:event_msgTxtKeyPressed

    //-------------> Métodos visuais <-------------\\
    private void enableChat(boolean op) {
        this.msgTxt.setEnabled(op);
        this.enviarBtn.setEnabled(op);
        this.userList.setVisible(op);
        this.msgList.setEnabled(op);
        this.listarBtn.setEnabled(op);
        this.listarDmBtn.setEnabled(op);
        this.carregarBtn.setEnabled(op);
        this.comboBoxUser.setEnabled(op);
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                break;
            }
        } //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton carregarBtn;
    private javax.swing.JComboBox<String> comboBoxUser;
    private javax.swing.JButton connectBtn;
    private javax.swing.JButton disconnectBtn;
    private javax.swing.JButton enviarBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton listarBtn;
    private javax.swing.JButton listarDmBtn;
    private javax.swing.JList<String> msgList;
    private javax.swing.JTextField msgTxt;
    private javax.swing.JTextField nomeTxt;
    private javax.swing.JList<Cliente> userList;
    // End of variables declaration//GEN-END:variables
}