package br.com.pitanga.table.model;

import br.com.pitanga.domain.TituloVenda;
import br.com.pitanga.util.StringUtils;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class ConsultaTituloVendaTableModel extends AbstractTableModel {

    StringUtils strUtil = new StringUtils();

    private static final int COL_VENDA = 0;
    private static final int COL_PARCELA = 1;
    private static final int COL_CLIENTE = 2;
    private static final int COL_EMISSAO = 3;
    private static final int COL_VENCIMENTO = 4;
    private static final int COL_VALOR_PARCELA = 5;
    
    private static final String PAGO = "Pago";

    private final List<TituloVenda> dados;

    public ConsultaTituloVendaTableModel(List<TituloVenda> titulos) {
        dados = titulos;
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TituloVenda obj = dados.get(rowIndex);
        if (columnIndex == COL_VENDA) {
            return obj.getVenda().idVendaToString();
        } else if (columnIndex == COL_PARCELA) {
            return obj.parcelaToString();
        } else if (columnIndex == COL_CLIENTE) {
            return obj.getVenda().getCliente().getNome();
        } else if (columnIndex == COL_EMISSAO) {
            return obj.getVenda().getDataVendaFormatada();
        } else if (columnIndex == COL_VENCIMENTO) {
            return obj.vencimentoToString();
        } else if (columnIndex == COL_VALOR_PARCELA) {
            if (obj.getValorParcela() == obj.getValorRecebido()) {
                return PAGO;
            } else {
                return strUtil.formatarDecimalTabela(
                        obj.getValorParcela() - obj.getValorRecebido());
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        String coluna = "";
        switch (column) {
            case COL_VENDA:
                coluna = "Nº Venda";
                break;
            case COL_PARCELA:
                coluna = "Parcela";
                break;
            case COL_CLIENTE:
                coluna = "Cliente";
                break;
            case COL_EMISSAO:
                coluna = "Emissão";
                break;
            case COL_VENCIMENTO:
                coluna = "Vencimento";
                break;
            case COL_VALOR_PARCELA:
                coluna = "Valor em aberto";
                break;
            default:
                throw new IllegalArgumentException("Coluna inválida!");
        }
        return coluna;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COL_VENDA) {
            return int.class;
        } else if (columnIndex == COL_PARCELA) {
            return String.class;
        } else if (columnIndex == COL_CLIENTE) {
            return String.class;
        } else if (columnIndex == COL_EMISSAO) {
            return String.class;
        } else if (columnIndex == COL_VENCIMENTO) {
            return String.class;
        } else if (columnIndex == COL_VALOR_PARCELA) {
            return double.class;
        }
        return null;
    }

    public TituloVenda get(int row) {
        return dados.get(row);
    }

}
