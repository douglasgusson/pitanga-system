/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.table.model;

import br.com.pitanga.domain.TituloVenda;
import br.com.pitanga.util.StringUtils;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class TituloVendaTableModel extends AbstractTableModel {
    
    StringUtils strUtil = new StringUtils();

    private static final int COL_NUMERO_PARCELA = 0;
    private static final int COL_VALOR_PARCELA = 1;
    private static final int COL_VENCIMENTO = 2;

    private final List<TituloVenda> dados;

    public TituloVendaTableModel(List<TituloVenda> dados) {
        this.dados = dados;
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TituloVenda obj = dados.get(rowIndex);
        if (columnIndex == COL_NUMERO_PARCELA) {
            return obj.getNumParcela();
        } else if (columnIndex == COL_VALOR_PARCELA) {
            return strUtil.formatarDecimalTabela(obj.getValorParcela());
        } else if (columnIndex == COL_VENCIMENTO) {
            return obj.vencimentoToString();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        String coluna = "";
        switch (column) {
            case COL_NUMERO_PARCELA:
                coluna = "Parcela";
                break;
            case COL_VALOR_PARCELA:
                coluna = "Valor (R$)";
                break;
            case COL_VENCIMENTO:
                coluna = "Vencimento";
                break;
            default:
                throw new IllegalArgumentException("Coluna inválida!");
        }
        return coluna;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_NUMERO_PARCELA) {
            return int.class;
        } else if (columnIndex == COL_VALOR_PARCELA) {
            return double.class;
        } else if (columnIndex == COL_VENCIMENTO) {
            return String.class;
        }
        return null;
    }

    public TituloVenda get(int row) {
        return dados.get(row);
    }

}
