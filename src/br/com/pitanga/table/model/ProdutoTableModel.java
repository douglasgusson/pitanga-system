package br.com.pitanga.table.model;

import br.com.pitanga.domain.Produto;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class ProdutoTableModel extends AbstractTableModel {

    private static final int COL_CODIGO = 0;
    private static final int COL_DESCRICAO = 1;
    private static final int COL_VALOR = 2;
    private static final int COL_UNIDADE = 3;
    private static final int COL_TIPO = 4;

    private final List<Produto> dados;

    public ProdutoTableModel(List<Produto> dados) {
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
        Produto obj = dados.get(rowIndex);
        if (columnIndex == COL_CODIGO) {
            return String.format("%05d", obj.getIdProduto());
        } else if (columnIndex == COL_DESCRICAO) {
            return obj.getDescricao();
        } else if (columnIndex == COL_VALOR) {
            return new DecimalFormat("#,##0.00").format(obj.getValorVenda());
        } else if (columnIndex == COL_UNIDADE) {
            return obj.getUnidade().getIdUnidade();
        } else if (columnIndex == COL_TIPO) {
            return obj.getTipoProduto().getDescricao();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        String coluna = "";
        switch (column) {
            case COL_CODIGO:
                coluna = "Cód.";
                break;
            case COL_DESCRICAO:
                coluna = "Descrição";
                break;
            case COL_VALOR:
                coluna = "Valor";
                break;
            case COL_UNIDADE:
                coluna = "Unid.";
                break;
            case COL_TIPO:
                coluna = "Tipo";
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
        } else if (columnIndex == COL_VALOR) {
            return String.class;
        } else if (columnIndex == COL_UNIDADE) {
            return  String.class;
        } else if (columnIndex == COL_TIPO) {
            return String.class;
        }
        return null;
    }

    public Produto get(int row) {
        return dados.get(row);
    }

}
