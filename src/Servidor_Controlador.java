
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor_Controlador {

	static ServerSocket serversocket;
	static Socket client_socket;
	static Socket banco_socket_1;
	static Socket banco_socket_2;
	static Socket banco_socket_3;
	static Conexao c;

	public Servidor_Controlador() {
		try {
			serversocket = new ServerSocket(9600);
			//banco_socket_1 = new Socket("localhost", 9601);
			//banco_socket_2 = new Socket("localhost", 9602);
			//banco_socket_3 = new Socket("localhost", 9603);
			System.out.println("Calculadora distribuida no ar!!!");
			System.out.println("Aguardando cliente fazer requisiçao ...");
		} catch (Exception e) {
			System.out.println("Nao criei o server socket...");
		}
	}

	public static void main(String args[]) {
		Requisicao msgReq;
		Resposta msgResp = new Resposta();
		int visits = 0;
		new Servidor_Controlador();

		while (true) {
			if (connect()) {
				msgReq = (Requisicao) c.receive(client_socket);

				int op = msgReq.getOperacao();
				System.out.println("Operacao " + op);
				msgResp = new Resposta();
				msgResp.setStatus(0);
				/*switch (op) {
				case 1:
					msgResp = insercao(msgReq);
					break;
				case 2:
					msgResp = update(msgReq);
					break;
				case 3:
					msgResp = consulta_delete(msgReq);
					break;
				case 4:
					msgResp = consulta_delete(msgReq);
					break;
				default:
					msgResp.setStatus(1);
					break;
				}*/
				c.send(client_socket, msgResp);
			} else {
				try {
					serversocket.close();
					break;
				} catch (Exception e) {
					System.out.println("Nao desconectei...");
				}
			}
		}
	}

	private static Resposta consulta_delete(Requisicao msgReq) {
		Lista pessoas = msgReq.getLista();
		Resposta msgResp = new Resposta();
		Pessoa pessoa = pessoas.get(0);
		switch (msgReq.getTipoProcura()) {
		case 1:
			msgResp = consulta_delete_nome(msgReq);
			break;
		case 2:
			msgResp = consulta_delete_altura_idade(msgReq);
			break;
		case 3:
			msgResp = consulta_delete_altura_idade(msgReq);
			break;
		default:
			msgResp.setStatus(4);
		}
		
		return msgResp;
	}

	private static Resposta consulta_delete_altura_idade(Requisicao msgReq) {
		Resposta msgResp = new Resposta();
		Lista retornoListaPessoas = new Lista();
		msgResp.setStatus(0);
		Lista pessoasAlteradas = new Lista();
		c.send(banco_socket_1, msgReq);
		Resposta resp = (Resposta) c.receive(banco_socket_1);
		c.send(banco_socket_2, msgReq);
		Resposta resp2 = (Resposta)c.receive(banco_socket_2);
		c.send(banco_socket_3, msgReq);
		Resposta resp3 = (Resposta)c.receive(banco_socket_3);
		if(resp == null && resp2 == null && resp3 == null)
		{
			msgResp.setStatus(5);
			return msgResp;
		}
		if(resp == null || resp2 == null || resp3 == null)
		{
			msgResp.setStatus(6);
		}
		if(resp != null)
		{
			for(int i = 0; i < resp.getResult().size(); i++)
			{
				retornoListaPessoas.add(resp.getResult().get(i));
			}
		}
		if(resp2 != null)
		{
			for(int i = 0; i < resp2.getResult().size(); i++)
			{
				retornoListaPessoas.add(resp2.getResult().get(i));
			}
		}
		if(resp3 != null)
		{
			for(int i = 0; i < resp3.getResult().size(); i++)
			{
				retornoListaPessoas.add(resp3.getResult().get(i));
			}
		}
		msgResp.setResult(retornoListaPessoas);
		
		return msgResp;
	}

	private static Resposta consulta_delete_nome(Requisicao msgReq) {
		Pessoa pessoa = msgReq.getLista().get(0);
		Resposta msgResp = new Resposta();
		char primeiraLetra = pessoa.getNome().charAt(0);
		if (primeiraLetra >= 65 && primeiraLetra <= 73) {
			c.send(banco_socket_1, msgReq);
			Resposta resposta = (Resposta) c.receive(banco_socket_1);
			if(resposta == null) {
				c.send(banco_socket_3, msgReq);
				resposta = (Resposta) c.receive(banco_socket_3);
				if(resposta == null) {
					msgResp.setStatus(5);
					return msgResp;
				}else {
					return resposta;
				}
			}else {
				return resposta;
			}
		
			
		} else if (primeiraLetra >= 74 && primeiraLetra <= 82) {
			c.send(banco_socket_2, msgReq);
			Resposta resposta = (Resposta) c.receive(banco_socket_2);
			if(resposta == null) {
				c.send(banco_socket_1, msgReq);
				resposta = (Resposta) c.receive(banco_socket_1);
				if(resposta == null) {
					msgResp.setStatus(5);
					return msgResp;
				}else {
					return resposta;
				}
			}else {
				return resposta;
			}
		} else if (primeiraLetra >= 83 && primeiraLetra <= 90) {
			c.send(banco_socket_3, msgReq);
			Resposta resposta = (Resposta) c.receive(banco_socket_3);
			if(resposta == null) {
				c.send(banco_socket_2, msgReq);
				resposta = (Resposta) c.receive(banco_socket_2);
				if(resposta == null) {
					msgResp.setStatus(5);
					return msgResp;
				}else {
					return resposta;
				}
			}else {
				return resposta;
			}
		} else {
			msgResp.setStatus(6);
			return msgResp;
		}
	
	}

	private static Resposta update(Requisicao msgReq) {
		Lista pessoas = msgReq.getLista();
		Lista pessoasAtualizadas = new Lista();
		Resposta msgResp = new Resposta();
		for (int i = 0; i < pessoas.size(); i++) {
			Pessoa pessoa = pessoas.get(i);
			char primeiraLetra = pessoa.getNome().charAt(0);
			if (primeiraLetra >= 65 && primeiraLetra <= 73) {
				c.send(banco_socket_1, msgReq);
				Resposta resposta = (Resposta) c.receive(banco_socket_1);
				switch (resposta.getStatus()) {
				case 0:
					pessoasAtualizadas.add(resposta.getResult().get(0));
					break;
				case 1:
					msgResp.setStatus(1);
					msgResp.setResult(pessoasAtualizadas);
					return msgResp;
				case 2:
					break;
				default:
				}
				c.send(banco_socket_3, msgReq);
				resposta = (Resposta) c.receive(banco_socket_3);
				switch (resposta.getStatus()) {
				case 0:
					pessoasAtualizadas.add(resposta.getResult().get(0));
					break;
				case 1:
					msgResp.setStatus(1);
					msgResp.setResult(pessoasAtualizadas);
					return msgResp;
				case 2:
					break;
				default:
				}
			} else if (primeiraLetra >= 74 && primeiraLetra <= 82) {
				c.send(banco_socket_2, msgReq);
				Resposta resposta = (Resposta) c.receive(banco_socket_2);
				switch (resposta.getStatus()) {
				case 0:
					pessoasAtualizadas.add(resposta.getResult().get(0));
					break;
				case 1:
					msgResp.setStatus(1);
					msgResp.setResult(pessoasAtualizadas);
					return msgResp;
				case 2:
					break;
				default:
				}
				c.send(banco_socket_1, msgReq);
				resposta = (Resposta) c.receive(banco_socket_1);
				switch (resposta.getStatus()) {
				case 0:
					pessoasAtualizadas.add(resposta.getResult().get(0));
					break;
				case 1:
					msgResp.setStatus(1);
					msgResp.setResult(pessoasAtualizadas);
					return msgResp;
				case 2:
					break;
				default:
				}
			} else if (primeiraLetra >= 83 && primeiraLetra <= 90) {
				c.send(banco_socket_3, msgReq);
				Resposta resposta = (Resposta) c.receive(banco_socket_3);
				switch (resposta.getStatus()) {
				case 0:
					pessoasAtualizadas.add(resposta.getResult().get(0));
					break;
				case 1:
					msgResp.setStatus(1);
					msgResp.setResult(pessoasAtualizadas);
					return msgResp;
				case 2:
					break;
				default:
				}
				c.send(banco_socket_2, msgReq);
				resposta = (Resposta) c.receive(banco_socket_2);
				switch (resposta.getStatus()) {
				case 0:
					pessoasAtualizadas.add(resposta.getResult().get(0));
					break;
				case 1:
					msgResp.setStatus(1);
					msgResp.setResult(pessoasAtualizadas);
					return msgResp;
				case 2:
					break;
				default:
				}
			} else {
				msgResp.setStatus(1);
				msgResp.setResult(pessoasAtualizadas);
				return msgResp;
			}
		}
		msgResp.setStatus(0);
		msgResp.setResult(pessoasAtualizadas);
		return msgResp;
	}

	private static Resposta insercao(Requisicao msgReq) {
		Lista pessoas = msgReq.getLista();
		Lista pessoasAdicionadas = new Lista();
		Resposta msgResp = new Resposta();
		for (int i = 0; i < pessoas.size(); i++) {
			Pessoa pessoa = pessoas.get(i);
			char primeiraLetra = pessoa.getNome().charAt(0);
			if (primeiraLetra >= 65 && primeiraLetra <= 73) {
				c.send(banco_socket_1, msgReq);
				Resposta resposta = (Resposta) c.receive(banco_socket_1);
				switch (resposta.getStatus()) {
				case 0:
					pessoasAdicionadas.add(pessoa);
					break;
				case 1:
					msgResp.setStatus(1);
					msgResp.setResult(pessoasAdicionadas);
					return msgResp;
				case 2:
					break;
				default:
				}
				c.send(banco_socket_3, msgReq);
				resposta = (Resposta) c.receive(banco_socket_3);
				switch (resposta.getStatus()) {
				case 0:
					pessoasAdicionadas.add(pessoa);
					break;
				case 1:
					msgResp.setStatus(1);
					msgResp.setResult(pessoasAdicionadas);
					return msgResp;
				case 2:
					break;
				default:
				}
			} else if (primeiraLetra >= 74 && primeiraLetra <= 82) {
				c.send(banco_socket_2, msgReq);
				Resposta resposta = (Resposta) c.receive(banco_socket_2);
				switch (resposta.getStatus()) {
				case 0:
					pessoasAdicionadas.add(pessoa);
					break;
				case 1:
					msgResp.setStatus(1);
					msgResp.setResult(pessoasAdicionadas);
					return msgResp;
				case 2:
					break;
				default:
				}
				c.send(banco_socket_1, msgReq);
				resposta = (Resposta) c.receive(banco_socket_1);
				switch (resposta.getStatus()) {
				case 0:
					pessoasAdicionadas.add(pessoa);
					break;
				case 1:
					msgResp.setStatus(1);
					msgResp.setResult(pessoasAdicionadas);
					return msgResp;
				case 2:
					break;
				default:
				}
			} else if (primeiraLetra >= 83 && primeiraLetra <= 90) {
				c.send(banco_socket_3, msgReq);
				Resposta resposta = (Resposta) c.receive(banco_socket_3);
				switch (resposta.getStatus()) {
				case 0:
					pessoasAdicionadas.add(pessoa);
					break;
				case 1:
					msgResp.setStatus(1);
					msgResp.setResult(pessoasAdicionadas);
					return msgResp;
				case 2:
					break;
				default:
				}
				c.send(banco_socket_2, msgReq);
				resposta = (Resposta) c.receive(banco_socket_2);
				switch (resposta.getStatus()) {
				case 0:
					pessoasAdicionadas.add(pessoa);
					break;
				case 1:
					msgResp.setStatus(1);
					msgResp.setResult(pessoasAdicionadas);
					return msgResp;
				case 2:
					break;
				default:
				}
			} else {
				msgResp.setStatus(1);
				msgResp.setResult(pessoasAdicionadas);
				return msgResp;
			}
		}
		msgResp.setStatus(0);
		msgResp.setResult(pessoasAdicionadas);
		return msgResp;

	}

	static boolean connect() {
		boolean ret;
		try {
			client_socket = serversocket.accept();
			return true;
		} catch (Exception e) {
			System.out.println("Erro de connect..." + e.getMessage());
			return false;
		}
	}
}
