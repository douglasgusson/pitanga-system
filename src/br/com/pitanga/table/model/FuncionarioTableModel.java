/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.table.model;

import br.com.pitanga.domain.Funcionario;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class FuncionarioTableModel extends AbstractTableModel {

    /**
     * Atributos do cliente: cod_cliente nome_cliente cpf_cnpj rg_inscricao
     * endereco numero bairro cep telefone celular email cod_cidade
     */
    private static final int COL_CODIGO = 0;
    private static final int COL_NOME = 1;
    private static final int COL_ENDERECO = 2;
    private static final int COL_TELEFONE = 3;
    private static final int COL_CPF = 4;

    private final List<Funcionario> dados;

    public FuncionarioTableModel(List<Funcionario> dados) {
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
        Funcionario obj = dados.get(rowIndex);
        if (columnIndex == COL_CODIGO) {
            return obj.getIdFuncionario();
        } else if (columnIndex == COL_NOME) {
            return obj.getNome();
        } else if (columnIndex == COL_ENDERECO) {
            return obj.getEndereco();
        } else if (columnIndex == COL_TELEFONE) {
            return obj.getTelefone();
        } else if (columnIndex == COL_CPF) {
            return obj.getCpf();
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
            case COL_NOME:
                coluna = "Nome";
                break;
            case COL_ENDERECO:
                coluna = "Endereço";
                break;
            case COL_TELEFONE:
                coluna = "Telefone";
                break;
            case COL_CPF:
                coluna = "Cpf";
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
        } else if (columnIndex == COL_ENDERECO) {
            return String.class;
        } else if (columnIndex == COL_TELEFONE) {
            return String.class;
        } else if (columnIndex == COL_CPF) {
            return String.class;
        }
        return null;
    }

    public Funcionario get(int row) {
        return dados.get(row);
    }

}
