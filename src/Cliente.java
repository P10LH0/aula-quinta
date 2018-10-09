/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author rafael.oliveira
 */
public class Cliente {

    static Conexao c;
    static Socket socket;

    private static Pessoa CriaPessoa() {
        Pessoa pessoa;
        int clienteInt = JOptionPane.showConfirmDialog(null, "É cliente?");
        boolean clienteBool = clienteInt == JOptionPane.YES_OPTION;
        pessoa = new Pessoa(JOptionPane.showInputDialog(null, "Qual o nome da pessoa?"),
                Integer.parseInt(JOptionPane.showInputDialog(null, "Qual a idade da pessoa?")),
                Integer.parseInt(JOptionPane.showInputDialog(null, "Qual a altura em Centimetros da pessoa?")),
                clienteBool);
        return pessoa;
    }

    private static int VerificaTipo(int tipoConsulta) {
        char maiorMenor = '4';
        do {
            maiorMenor = JOptionPane.showInputDialog(null, "Devo procurar por:\n\n"
                    + "1 - Maior\n"
                    + "2 - Menor\n"
                    + "3 - Igual").charAt(0);
            switch (maiorMenor) {
                case '1':
                    tipoConsulta = 2;
                    maiorMenor = '1';
                    break;
                case '2':
                    tipoConsulta = 3;
                    maiorMenor = '2';
                    break;
                case '3':
                    tipoConsulta = 4;
                    maiorMenor = '3';
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Errado! tente novamente");
                    maiorMenor = '4';
                    break;
            }
        } while (maiorMenor == '4');
        return tipoConsulta;
    }

    public Cliente() {
        try {
            socket = new Socket("localhost", 9600);
        } catch (Exception e) {
            System.out.println("Nao consegui resolver o host...");
        }
    }

    public static void main(String[] args) {

        new Cliente();
        Pessoa pessoa;
        Lista lista;
        Requisicao msgReq;
        Resposta msgRep;
        int op;
        int operacao = 0;
        int tipoConsulta;
        boolean volta = true;
        do {
            operacao = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Selecione uma das opções\n\n"
                    + "1 - Insert\n"
                    + "2 - Update\n"
                    + "3 - Select\n"
                    + "4 - Delete\n"
                    + "5 - Sair"));
            switch (operacao) {
                case 1:
                    lista = new Lista();
                    do {
                        pessoa = CriaPessoa();
                        lista.add(pessoa);
                        op = JOptionPane.showConfirmDialog(null, "Fazer de novo?");
                    } while (op == JOptionPane.YES_OPTION);
                    //envia requisição com a lista de pessoas que a operação que será executada, no caso insert
                    msgReq = new Requisicao(lista, operacao);
                    c.send(socket, msgReq);
                    msgRep = (Resposta) c.receive(socket);
                    //verifica status da resposta
                    if (msgRep.getStatus() == 0) {
                        JOptionPane.showMessageDialog(null, "As pessoas:\n" + lista.toString() + "foram gravadas com sucesso");
                    } else {
                        JOptionPane.showMessageDialog(null, "Houve um erro ao tentar gravar");
                    }
                    break;
                case 2:
                    lista = new Lista();
                    do {
                        pessoa = CriaPessoa();
                        lista.add(pessoa);
                        op = JOptionPane.showConfirmDialog(null, "Fazer de novo?");
                    } while (op == JOptionPane.YES_OPTION);
                    //envia requisição com a lista de pessoas que a operação que será executada, no caso Update
                    msgReq = new Requisicao(lista, operacao);
                    c.send(socket, msgReq);
                    msgRep = (Resposta) c.receive(socket);
                    //verifica status da resposta
                    if (msgRep.getStatus() == 0) {
                        JOptionPane.showMessageDialog(null, "As pessoas:\n" + lista.toString() + "foram gravadas com sucesso");
                    } else {
                        JOptionPane.showMessageDialog(null, "Houve um erro ao tentar gravar");
                    }
                    break;
                case 3:
                    lista = new Lista();
                    do {
                        tipoConsulta = Integer.parseInt(JOptionPane.showInputDialog(
                                "Qual tipo de consulta?\n"
                                + "1 - Por Nome\n"
                                + "2 - Por Idade\n"
                                + "3 - Por altura\n"
                                + "4 - É cliente?\n"
                                + "5 - Para voltar ao menu anterior"));
                        switch (tipoConsulta) {
                            case 1:
                                String nome;
                                do {
                                    nome = JOptionPane.showInputDialog(null, "Qual o nome da pessoa?");
                                    op = JOptionPane.showConfirmDialog(null, "O nome que deseja consultar é:" + nome + "?");
                                } while (op == JOptionPane.NO_OPTION);
                                //envia requisição com a lista de pessoas que a operação que será executada, no caso Select
                                msgReq = new Requisicao(nome, operacao, tipoConsulta);
                                c.send(socket, msgReq);
                                msgRep = (Resposta) c.receive(socket);
                                break;
                            case 2:
                                String idade = JOptionPane.showInputDialog(null, "Qual a idade da pessoa?");
                                tipoConsulta = VerificaTipo(tipoConsulta);
                                msgReq = new Requisicao(idade, operacao, tipoConsulta);
                                c.send(socket, msgReq);
                                msgRep = (Resposta) c.receive(socket);
                                break;
                            case 3:
                                String altura = JOptionPane.showInputDialog(null, "Qual a idade da pessoa?");
                                tipoConsulta = VerificaTipo(tipoConsulta);
                                msgReq = new Requisicao(altura, operacao, tipoConsulta);
                                c.send(socket, msgReq);
                                msgRep = (Resposta) c.receive(socket);
                                break;
                            case 4:
                                int cliente = JOptionPane.showConfirmDialog(null, "Pesquisar por clientes (sim) ou não clientes (não)");
                                boolean clibool = cliente == JOptionPane.YES_OPTION;
                                String boolString = String.valueOf(clibool);
                                msgReq = new Requisicao(boolString, operacao, 8);
                                c.send(socket, msgReq);
                                msgRep = (Resposta) c.receive(socket);
                                break;
                            case 5:
                                JOptionPane.showMessageDialog(null, "Voltando");
                                volta = false;
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Errado! tente novamente");
                                break;
                        }

                    } while (volta);
                    break;
                case 4:
                    lista = new Lista();
                    do {
                        tipoConsulta = Integer.parseInt(JOptionPane.showInputDialog(
                                "Qual tipo de exclusão?\n"
                                + "1 - Por Nome\n"
                                + "2 - Por Idade\n"
                                + "3 - Por altura\n"
                                + "4 - É cliente?\n"
                                + "5 - Para voltar ao menu anterior"));
                        switch (tipoConsulta) {
                            case 1:
                                String nome;
                                do {
                                    nome = JOptionPane.showInputDialog(null, "Qual o nome da pessoa?");
                                    op = JOptionPane.showConfirmDialog(null, "O nome que deseja excluir é:" + nome + "?");
                                } while (op == JOptionPane.NO_OPTION);
                                //envia requisição com a lista de pessoas que a operação que será executada, no caso Select
                                msgReq = new Requisicao(nome, operacao, tipoConsulta);
                                c.send(socket, msgReq);
                                msgRep = (Resposta) c.receive(socket);
                                break;
                            case 2:
                                String idade = JOptionPane.showInputDialog(null, "Qual a idade da pessoa?");
                                tipoConsulta = VerificaTipo(tipoConsulta);
                                msgReq = new Requisicao(idade, operacao, tipoConsulta);
                                c.send(socket, msgReq);
                                msgRep = (Resposta) c.receive(socket);
                                break;
                            case 3:
                                String altura = JOptionPane.showInputDialog(null, "Qual a idade da pessoa?");
                                tipoConsulta = VerificaTipo(tipoConsulta);
                                msgReq = new Requisicao(altura, operacao, tipoConsulta);
                                c.send(socket, msgReq);
                                msgRep = (Resposta) c.receive(socket);
                                break;
                            case 4:
                                int cliente = JOptionPane.showConfirmDialog(null, "Excluir clientes (sim) ou não clientes (não)");
                                boolean clibool = cliente == JOptionPane.YES_OPTION;
                                String boolString = String.valueOf(clibool);
                                msgReq = new Requisicao(boolString, operacao, 8);
                                c.send(socket, msgReq);
                                msgRep = (Resposta) c.receive(socket);
                                break;
                            case 5:
                                JOptionPane.showMessageDialog(null, "Voltando");
                                volta = false;
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Errado! tente novamente");
                                break;
                        }

                    } while (volta);
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Tchau!");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Errado! tente novamente");
                    operacao = -1;
                    break;
            }
        } while (operacao != 5);
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("problemas ao fechar socket");
        }
    }

}
