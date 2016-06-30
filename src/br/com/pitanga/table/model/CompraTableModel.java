/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.table.model;

import br.com.pitanga.domain.Compra;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class CompraTableModel extends AbstractTableModel {

    /**
     * Atributos do cliente: cod_cliente nome_cliente cpf_cnpj rg_inscricao
     * endereco numero bairro cep telefone celular email cod_cidade
     */
    private static final int COL_COD_COMPRA = 0;
    private static final int COL_COD_FUNCIONARIO = 1;
    private static final int COL_COD_FORNECEDOR = 2;
    private static final int COL_DATA_COMPRA = 3;
    private static final int COL_VALOR_COMPRA = 4;

    private final List<Compra> dados;

    public CompraTableModel(List<Compra> dados) {
        this.dados = dados;
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Compra obj = dados.get(rowIndex);
        if (columnIndex == COL_COD_COMPRA) {
            return obj.getIdCompra();
        } else if (columnIndex == COL_DATA_COMPRA) {
            return obj.getDataCompra();
        } else if (columnIndex == COL_VALOR_COMPRA) {
            return obj.getValorCompra();

        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        String coluna = "";
        switch (column) {
            case COL_COD_COMPRA:
                coluna = "Cód.";
                break;
            case COL_COD_FUNCIONARIO:
                coluna = "Cód.";
                break;
            case COL_COD_FORNECEDOR:
                coluna = "Cód.";
                break;
            case COL_DATA_COMPRA:
                coluna = "data";
                break;
            case COL_VALOR_COMPRA:
                coluna = "valor";
                break;
           
            default:
                throw new IllegalArgumentException("Coluna inválida!");
        }
        return coluna;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_COD_COMPRA) {
            return int.class;
        } else if (columnIndex == COL_COD_FUNCIONARIO) {
            return String.class;
        } else if (columnIndex == COL_COD_FORNECEDOR) {
            return String.class;
        } else if (columnIndex == COL_DATA_COMPRA) {
            return String.class;
        } else if (columnIndex == COL_VALOR_COMPRA) {
            return String.class;
        
        }
        return null;
    }

    public Compra get(int row) {
        return dados.get(row);
    }

}
