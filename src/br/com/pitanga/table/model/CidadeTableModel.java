/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.table.model;

import br.com.pitanga.domain.Cidade;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class CidadeTableModel extends AbstractTableModel {

    private static final int COL_CODIGO = 0;
    private static final int COL_NOME = 1;
    private static final int COL_UF = 2;

    private final List<Cidade> dados;

    public CidadeTableModel(List<Cidade> dados) {
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
        Cidade obj = dados.get(rowIndex);
        if (columnIndex == COL_CODIGO) {
            return obj.getIdCidade();
        } else if (columnIndex == COL_NOME) {
            return obj.getNomeCidade();
        } else if (columnIndex == COL_UF) {
            return obj.getUf();
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
            case COL_NOME:
                coluna = "Nome";
                break;
            case COL_UF:
                coluna = "UF";
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
        } else if (columnIndex == COL_NOME) {
            return String.class;
        } else if (columnIndex == COL_UF) {
            return String.class;
        }
        return null;
    }

    public Cidade get(int row) {
        return dados.get(row);
    }

}
