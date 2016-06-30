/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.view;

import br.com.pitanga.dao.model.FornecedorDAO;
import br.com.pitanga.domain.Fornecedor;
import br.com.pitanga.table.cellrenderer.FornecedorCellRenderer;
import br.com.pitanga.table.model.FornecedorTableModel;
import br.com.pitanga.util.GUIUtils;
import br.com.pitanga.dao.DAOFactory;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author Netsul
 */
public class FrmBuscaFornecedor extends javax.swing.JDialog {

    public FrmBuscaFornecedor(Window owner) {
        super(owner, DEFAULT_MODALITY_TYPE);
        initComponents();
        inicializarFrame();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSair = new javax.swing.JButton();
        tfPesquisa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbFornecedor = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        tfPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfPesquisaKeyTyped(evt);
            }
        });

        jLabel1.setText("Pesquisa:");

        tbFornecedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbFornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFornecedorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbFornecedor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfPesquisa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSair)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnSair))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        int selecionada = this.tbFornecedor.getSelectedRow();

        if (selecionada == -1) {
            JOptionPane.showMessageDialog(rootPane,
                    "- NENHUMA FORNECEDOR SELECIONADO -",
                    "Pitanga System - FORNECEDOR",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            Fornecedor fornecedor = new Fornecedor();
            fornecedor = buscaFornecedor(selecionada);
//            FrmCompra frame = (FrmCompra) parent;
//            frame.preencherCamposFornecedor(fornecedor);
            dispose();
        }
    }//GEN-LAST:event_btnSairActionPerformed

    private void tfPesquisaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPesquisaKeyTyped
        // TODO add your handling code here:
        String termo = this.tfPesquisa.getText();
        preencherTabelaPesquisa(termo);
    }//GEN-LAST:event_tfPesquisaKeyTyped

    private void tbFornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFornecedorMouseClicked
        if (evt.getClickCount() == 2) {
            int selecionada = this.tbFornecedor.getSelectedRow();
            if (selecionada == -1) {
                JOptionPane.showMessageDialog(rootPane,
                        "- NENHUMA CLIENTE SELECIONADO -",
                        "Pitanga System - CLIENTES",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor = buscaFornecedor(selecionada);
//                FrmCompra frame = (FrmCompra) parent;
//                frame.preencherCamposFornecedor(fornecedor);
                dispose();
            }
        }
    }//GEN-LAST:event_tbFornecedorMouseClicked

    private void inicializarFrame() {
        listenerFechar();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        // Preenche a tabela de cidades
        preencherTabela();
        // Seta o título para o frame
        this.setTitle("Pitanga System - Consulta clientes");
        // Instancia a classe Complemento
        GUIUtils obj = new GUIUtils();
        // Habilita o Enter como TAB, passando o Componente
        GUIUtils.considerarEnterComoTab(this);
        this.tfPesquisa.requestFocus();
    }

    private void preencherTabela() {

        FornecedorDAO fd = DAOFactory.getDefaultDAOFactory().getFornecedorDAO();
        fornecedores = fd.listarTodos();

        if (fornecedores != null) {
            this.tbFornecedor.setModel(new FornecedorTableModel(fornecedores));
            this.tbFornecedor.setDefaultRenderer(Object.class, new FornecedorCellRenderer());
        }
    }

    public Fornecedor buscaFornecedor(int linha) {

        Fornecedor fornecedor = new Fornecedor();

        FornecedorTableModel tbModel = new FornecedorTableModel(fornecedores);
        fornecedor = tbModel.get(linha);

        FornecedorDAO fd = DAOFactory.getDefaultDAOFactory().getFornecedorDAO();
        fornecedor = fd.buscar(Integer.parseInt((this.tbFornecedor.getValueAt(linha, 0)).toString()));

        return fornecedor;

    }

    private void preencherTabelaPesquisa(String nome) {

        List<Fornecedor> lista = new ArrayList<>();

        for (Fornecedor f : fornecedores) {
            if ((f.getNomeFornecedor().toUpperCase()).contains(nome.toUpperCase())) {
                lista.add(f);
            }
        }

        this.tbFornecedor.setModel(new FornecedorTableModel(lista));
        this.tbFornecedor.setDefaultRenderer(Object.class, new FornecedorCellRenderer());
    }

    private void listenerFechar() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                int selecionada = tbFornecedor.getSelectedRow();

                if (selecionada == -1) {
                    JOptionPane.showMessageDialog(rootPane,
                            "- NENHUM CLIENTE SELECIONADO -",
                            "Pitanga System - CLIENTES",
                            JOptionPane.INFORMATION_MESSAGE);
                    repaint();
                } else {
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor = buscaFornecedor(selecionada);
//                    FrmCompra frame = (FrmCompra) parent;
//                    frame.preencherCamposFornecedor(fornecedor);
                    dispose();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbFornecedor;
    private javax.swing.JTextField tfPesquisa;
    // End of variables declaration//GEN-END:variables
    private List<Fornecedor> fornecedores = new ArrayList<>();
}
