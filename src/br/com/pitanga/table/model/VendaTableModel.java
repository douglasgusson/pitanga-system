
package br.com.pitanga.table.model;

import br.com.pitanga.domain.Venda;
import br.com.pitanga.util.StringUtils;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class VendaTableModel extends AbstractTableModel {

    private static final int COL_COD_VENDA = 0;
    private static final int COL_NOME_CLIENTE = 1;
    private static final int COL_DATA_VENDA = 2;
    private static final int COL_VALOR_VENDA = 3;

    private final StringUtils strUtil = new StringUtils();
    private final List<Venda> dados;

    public VendaTableModel(List<Venda> dados) {
        this.dados = dados;
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Venda obj = dados.get(rowIndex);
        if (columnIndex == COL_COD_VENDA) {
            return obj.idVendaToString();
        } else if (columnIndex == COL_NOME_CLIENTE) {
            return obj.getCliente().getNome();
        } else if (columnIndex == COL_DATA_VENDA) {
            return obj.getDataVendaFormatada();
        } else if (columnIndex == COL_VALOR_VENDA) {
            return strUtil.formatarDecimalTabela(obj.getValorVenda());

        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        String coluna = "";
        switch (column) {
            case COL_COD_VENDA:
                coluna = "Cód.";
                break;
            case COL_NOME_CLIENTE:
                coluna = "Cliente";
                break;
            case COL_DATA_VENDA:
                coluna = "Data";
                break;
            case COL_VALOR_VENDA:
                coluna = "Valor";
                break;
            default:
                throw new IllegalArgumentException("Coluna inválida!");
        }
        return coluna;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_COD_VENDA) {
            return String.class;
        } else if (columnIndex == COL_NOME_CLIENTE) {
            return String.class;
        } else if (columnIndex == COL_DATA_VENDA) {
            return String.class;
        } else if (columnIndex == COL_VALOR_VENDA) {
            return double.class;
        }
        return null;
    }

    public Venda get(int row) {
        return dados.get(row);
    }

}
