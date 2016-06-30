/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.table.model;

import br.com.pitanga.domain.FormaPagamento;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class FormaPagamentoTableModel extends AbstractTableModel {

    /**
     * Atributos do cliente: cod_cliente nome_cliente cpf_cnpj rg_inscricao
     * endereco numero bairro cep telefone celular email cod_cidade
     */
    private static final int COL_FORMA_PAGAMENTO = 0;
    private static final int COL_DESCRICAO_FORMA = 1;

    private final List<FormaPagamento> dados;

    public FormaPagamentoTableModel(List<FormaPagamento> dados) {
        this.dados = dados;
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FormaPagamento obj = dados.get(rowIndex);
        if (columnIndex == COL_FORMA_PAGAMENTO) {
            return obj.getId_FormaPagamento();
        } else if (columnIndex == COL_DESCRICAO_FORMA) {
            return obj.getDescricao();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        String coluna = "";
        switch (column) {
            case COL_FORMA_PAGAMENTO:
                coluna = "Código";
                break;
            case COL_DESCRICAO_FORMA:
                coluna = "Descrição";
                break;
            default:
                throw new IllegalArgumentException("Coluna inválida!");
        }
        return coluna;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_FORMA_PAGAMENTO) {
            return int.class;
        } else if (columnIndex == COL_DESCRICAO_FORMA) {
            return String.class;
        }
        return null;
    }

    public FormaPagamento get(int row) {
        return dados.get(row);
    }

}
