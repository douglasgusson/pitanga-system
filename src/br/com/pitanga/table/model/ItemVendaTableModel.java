package br.com.pitanga.table.model;

import br.com.pitanga.domain.ItemVenda;
import br.com.pitanga.util.StringUtils;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class ItemVendaTableModel extends AbstractTableModel {

    StringUtils strUtil = new StringUtils();

    private static final int COL_DESCRICAO = 0;
    private static final int COL_QUANTIDADE = 1;
    private static final int COL_VALOR_UNITARIO = 2;
    private static final int COL_VALOR_DESCONTO = 3;
    private static final int COL_VALOR_TOTAL = 4;

    private final List<ItemVenda> dados;

    public ItemVendaTableModel(List<ItemVenda> dados) {
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
        ItemVenda obj = dados.get(rowIndex);
        if (columnIndex == COL_DESCRICAO) {
            return obj.getProduto().getDescricao();
        } else if (columnIndex == COL_QUANTIDADE) {
            return this.strUtil.formatarDecimalTabela(obj.getQuantidade());
        } else if (columnIndex == COL_VALOR_UNITARIO) {
            return this.strUtil.formatarDecimalTabela(obj.getValor());
        } else if (columnIndex == COL_VALOR_DESCONTO) {
            return this.strUtil.formatarDecimalTabela(obj.getDesconto());
        } else if (columnIndex == COL_VALOR_TOTAL) {
            double valorTotal = ((obj.getValor() * obj.getQuantidade())
                    - ((obj.getValor() * obj.getQuantidade()) * (obj.getDesconto() / 100)));
            return this.strUtil.formatarDecimalTabela(valorTotal);
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        String coluna = "";
        switch (column) {
            case COL_DESCRICAO:
                coluna = "Descrição";
                break;
            case COL_QUANTIDADE:
                coluna = "Quant.";
                break;
            case COL_VALOR_UNITARIO:
                coluna = "V. Unit.";
                break;
            case COL_VALOR_DESCONTO:
                coluna = "Desconto (%)";
                break;
            case COL_VALOR_TOTAL:
                coluna = "V. Total";
                break;
            default:
                throw new IllegalArgumentException("Coluna inválida!");
        }
        return coluna;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_DESCRICAO) {
            return String.class;
        } else if (columnIndex == COL_QUANTIDADE) {
            return double.class;
        } else if (columnIndex == COL_VALOR_UNITARIO) {
            return double.class;
        } else if (columnIndex == COL_VALOR_DESCONTO) {
            return double.class;
        } else if (columnIndex == COL_VALOR_TOTAL) {
            return double.class;
        }
        return null;
    }

    public ItemVenda get(int row) {
        return dados.get(row);
    }

}
