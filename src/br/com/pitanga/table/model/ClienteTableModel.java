/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pitanga.table.model;

import br.com.pitanga.domain.Cliente;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Douglas Gusson
 */
public class ClienteTableModel extends AbstractTableModel {

    /**
     * Atributos do cliente: cod_cliente nome_cliente cpf_cnpj rg_inscricao
     * endereco numero bairro cep telefone celular email cod_cidade
     */
    private static final int COL_CODIGO = 0;
    private static final int COL_NOME = 1;
    private static final int COL_TELEFONE = 2;
    private static final int COL_CELULAR = 3;
    private static final int COL_EMAIL = 4;
    private static final int COL_NUMERO = 5;
    private static final int COL_BAIRRO = 6;
    private static final int COL_CEP = 7;
    private static final int COL_CIDADE = 8;
    private static final int COL_UF = 9;
    private static final int COL_CPF_CNPJ = 10;
    private static final int COL_RG_INSCRICAO = 11;
    private static final int COL_ENDERECO = 12;

    private final List<Cliente> dados;

    public ClienteTableModel(List<Cliente> dados) {
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
        Cliente obj = dados.get(rowIndex);
        if (columnIndex == COL_CODIGO) {
            return String.format("%05d", obj.getId());
        } else if (columnIndex == COL_NOME) {
            return obj.getNome();
        } else if (columnIndex == COL_TELEFONE) {
            return obj.getTelefone();
        } else if (columnIndex == COL_CELULAR) {
            return obj.getCelular();
        } else if (columnIndex == COL_EMAIL) {
            return obj.getEmail();
        } else if (columnIndex == COL_NUMERO) {
            return obj.getNumero();
        } else if (columnIndex == COL_BAIRRO) {
            return obj.getBairro();
        } else if (columnIndex == COL_CEP) {
            return obj.getCep();
        } else if (columnIndex == COL_CIDADE) {
            return obj.getCidade().getIdCidade();
        } else if (columnIndex == COL_CPF_CNPJ) {
            return obj.getCpfCnpj();
        } else if (columnIndex == COL_RG_INSCRICAO) {
            return obj.getRgInscricao();
        } else if (columnIndex == COL_ENDERECO) {
            return obj.getEndereco();
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
            case COL_TELEFONE:
                coluna = "Telefone";
                break;
            case COL_CELULAR:
                coluna = "Celular";
                break;
            case COL_EMAIL:
                coluna = "E-mail";
                break;
            case COL_NUMERO:
                coluna = "Núm.";
                break;
            case COL_BAIRRO:
                coluna = "Bairro";
                break;
            case COL_CEP:
                coluna = "CEP";
                break;
            case COL_CIDADE:
                coluna = "Cidade";
                break;
            case COL_UF:
                coluna = "UF";
                break;
            case COL_CPF_CNPJ:
                coluna = "CPF/CNPJ";
                break;
            case COL_RG_INSCRICAO:
                coluna = "RG/Insc.";
                break;
            case COL_ENDERECO:
                coluna = "Endereço";
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
        } else if (columnIndex == COL_TELEFONE) {
            return String.class;
        } else if (columnIndex == COL_CELULAR) {
            return String.class;
        } else if (columnIndex == COL_EMAIL) {
            return String.class;
        } else if (columnIndex == COL_NUMERO) {
            return String.class;
        } else if (columnIndex == COL_BAIRRO) {
            return String.class;
        } else if (columnIndex == COL_CEP) {
            return String.class;
        } else if (columnIndex == COL_CIDADE) {
            return int.class;
        } else if (columnIndex == COL_UF) {
            return String.class;
        } else if (columnIndex == COL_CPF_CNPJ) {
            return String.class;
        } else if (columnIndex == COL_RG_INSCRICAO) {
            return String.class;
        } else if (columnIndex == COL_ENDERECO) {
            return String.class;
        }
        return null;
    }

    public Cliente get(int row) {
        return dados.get(row);
    }

}
