/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.table.model;

import br.com.pitanga.domain.TipoProduto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class TipoProdutoTableModel extends AbstractTableModel {

    /**
     * Atributos do cliente: cod_cliente nome_cliente cpf_cnpj rg_inscricao
     * endereco numero bairro cep telefone celular email cod_cidade
     */
    private static final int COL_CODIGO = 0;
    private static final int COL_DESCRICAO = 1;

    private final List<TipoProduto> dados;

    public TipoProdutoTableModel(List<TipoProduto> dados) {
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
        TipoProduto obj = dados.get(rowIndex);
        if (columnIndex == COL_CODIGO) {
            return obj.getIdTipo();
        } else if (columnIndex == COL_DESCRICAO) {
            return obj.getDescricao();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        String coluna = "";
        switch (column) {
            case COL_CODIGO:
                coluna = "Código";
                break;
            case COL_DESCRICAO:
                coluna = "Descrição";
                break;
            default:
                throw new IllegalArgumentException("Coluna inválida!");
        }
        return coluna;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_CODIGO) {
            return int.class;
        } else if (columnIndex == COL_DESCRICAO) {
            return String.class;
        }
        return null;
    }

    public TipoProduto get(int row) {
        return dados.get(row);
    }

}
