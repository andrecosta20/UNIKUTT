package Logica;

import java.util.Scanner;

/**
 *
 * @author Lucas
 */
public class Unikut {

	private Conta[] contas;
	private int indiceConta;
	private Scanner s = new Scanner(System.in);
	public Conta currentAccount;

	public Unikut() {
		indiceConta = 0;
		contas = new Conta[1000];
	}
	
	// Logica de visualizacao
	public void menuAddAmigos() {
		System.out.println("\nOla " + currentAccount.getNome() + ".");
		System.out.println("\nMenu Adicionar Amigos");
		System.out.println("1 => Adicionar Amigo");
		System.out.println("2 => Voltar para o menu anterior");
		System.out.print("=>");
		int opcao = s.nextInt();
		s.nextLine();
		while (opcao != 2) {
			switch (opcao) {
				case 1 -> { // Adicionar amigo.
					System.out.println("\nDigite o login do amigo que quer adicionar:");
					String friendLogin = s.nextLine();
					while (!this.verificaLogin(friendLogin)) {
						System.out.print("\nErro:\nNão encontramos esse usuario!!\nPor Favor digite novamente o login do seu amigo: ");
						friendLogin = s.nextLine();
					}
					currentAccount.convidarAmigo(friendLogin);
					System.out.println("\nUm convite foi enviado para o amigo: " + friendLogin);
				}
			}
			this.menuAddAmigos();
		}
		this.menuLogado();
	}

	public void menuEditar() {
		System.out.println("\nOla " + currentAccount.getNome() + ".");
		System.out.println("\nMenu Alterar Conta");
		System.out.println("1 => Editar Apelido");
		System.out.println("2 => Editar Senha");
		System.out.println("3 => Voltar ao menu anterior");
		System.out.print("=>");
		int opcao = s.nextInt();
		s.nextLine();
		while (opcao != 3) {
			switch (opcao) {
			case 1 -> { // Alterar Apelido
				System.out.println("\nNovo Apelido:");
				String newNickName = s.nextLine();
				this.alterarContaNome(currentAccount.getLogin(), newNickName);
				System.out.println("\nApelido alterado com sucesso!");
			}
			case 2 -> { // Alterar Senha
				System.out.println("\nNova Senha:");
				String newPassword = s.nextLine();
				this.alterarContaSenha(currentAccount.getLogin(), newPassword);
				System.out.println("\nSenha alterada com sucesso!");
			}
			default -> System.out.println("\nComando inválido");
			}
			this.menuEditar();
		}
		this.menuLogado();
	}

	public void login() {
		System.out.print("\nLogin: ");
		String login, password;
		login = s.nextLine();
		while (!this.verificaLogin(login)) {
			System.out.print("\nErro:\nLogin Não Existente\nPor Favor usar outro Login: ");
			login = s.nextLine();
		}
		System.out.print("Senha: ");
		password = s.nextLine();
		while (!this.verificaSenha(login, password)) {
			System.out.print("\nErro:\nSenha Incorreta\n\nPor Favor tente novamente: ");
			password = s.nextLine();
		}

		currentAccount = this.pesquisarPessoas(login);

		System.out.println("\nBem vindo " + currentAccount.getNome() + "!");
	}
	
	public void mostrarAmigos() {
		Amigo[] amigos = currentAccount.getAmigos();
		if (amigos != null) {
			System.out.println("Solicitacao enviado por vc:");
			for (int i = 0; i < amigos.length; i ++) {
				if (amigos[i] != null) {
					Amigo amigo = amigos[i];
					Conta contaDoAmigo = this.pesquisarPessoas(amigo.getPara());
					String status = amigo.getPendente() ? "pendente" : "aceito";
					System.out.println(contaDoAmigo.getNome() + " status: " + status);
				}
			}
		} else {
			System.out.println("Vc ainda nao possui amigos!");
		}
		
		System.out.println();
		int opcao = 0;
		Amigo[] solicitacoesPendentes = getSolicitacoesPendentes();
		if (solicitacoesPendentes != null) {
			System.out.println("Solicitacoes pendentes enviadas para vc:");
			for (int i = 0; i < solicitacoesPendentes.length; i ++) {
				if (solicitacoesPendentes[i] != null) {
					Amigo amigo = solicitacoesPendentes[i];
					Conta contaDoAmigo = this.pesquisarPessoas(amigo.getDe());
					System.out.println("Convite de: " + contaDoAmigo.getNome() + " numero: " + (i+1));
				}
			}
			System.out.println("Digite o numero da solicitacao para aceitar, ou 0 para sair.");
			opcao = s.nextInt();
			while (opcao != 0) {
				solicitacoesPendentes[opcao - 1].setPendente(false);
				opcao = s.nextInt();
			}
		} else {
			System.out.println("Vc nao possui solicitacoes de amizade pendentes.");
		}
		
		while (opcao != 1) {
			System.out.println("1 => Voltar ao menu anterior");
			opcao = s.nextInt();
			s.nextLine();
		}
	}
	
	public void menuRecados() {
		System.out.println("\nOla " + currentAccount.getNome() + ".");
		System.out.println("Menu Recados:\n");
		System.out.println("1 => Visualizar Recados");
		System.out.println("2 => Enviar Recados");
		System.out.println("3 => Enviar Recados secretos");
		System.out.println("4 => Visualizar Recados secretos");
		System.out.println("5 => Voltar ao menu anterior");
		int opcao = s.nextInt();
		s.nextLine();
		while (opcao != 5) {
			switch (opcao) {
			case 1 -> { // Visualizar recados
				Mensagem[] mensagens = currentAccount.getMensagens();
				if (mensagens != null) {
					for (int i = 0; i < currentAccount.contadorDeMensagens; i++) {
						Mensagem mensagem = mensagens[i];
						System.out.println("De: " + pesquisarPessoas(mensagem.getDe()).getNome() + " Para: " + pesquisarPessoas(mensagem.getPara()).getNome() + " Recado: " + mensagem.getMensagem());
					}
				} else {
					System.out.println("Vc nao possui nenhum recado");
				}
			}
			case 2 -> { // Enviar recado
				System.out.println("digite o login do usuario que vai receber a mensagem:");
				String login = s.nextLine();
				while (!this.verificaLogin(login)) {
					System.out.print("\nErro:\nNão encontramos esse usuario!!\nPor Favor digite novamente o login do seu amigo: ");
					login = s.nextLine();
				}
				System.out.println("digite a mensagem a ser enviada:");
				String mensagem = s.nextLine();
				Mensagem msg = new Mensagem(login, currentAccount.getLogin(), mensagem);
				currentAccount.addMensagem(msg);
				Conta conta = pesquisarPessoas(login);
				conta.addMensagem(msg);
			}
			
			case 3 -> { // Enviar recados com senha
				System.out.println("digite o login do usuario que vai receber a mensagem:");
				String login = s.nextLine();
				while (!this.verificaLogin(login)) {
					System.out.print("\nErro:\nNão encontramos esse usuario!!\nPor Favor digite novamente o login do seu amigo: ");
					login = s.nextLine();
				}
				System.out.println("digite a mensagem a ser enviada:");
				String mensagem = s.nextLine();
				System.out.println("digite a senha para visualizar a mensagem (Nao use sair):");
				String senha = s.nextLine();
				while (senha.equals("sair")) {
					System.out.println("digite a senha para visualizar a mensagem (Nao use sair):");
					senha = s.nextLine();
				}
				Mensagem msg = new Mensagem(login, currentAccount.getLogin(), mensagem,senha);
				currentAccount.addMensagem(msg);
				Conta conta = pesquisarPessoas(login);
				conta.addMensagem(msg);
			}
			
			case 4 -> { // Visualizar recados secretos
				Mensagem[] mensagensSecretas = currentAccount.getMensagensSecretas();
				if (mensagensSecretas != null) {
					int i = 0;
					for (; i < currentAccount.contadorDeMensagens; i++) {
						Mensagem mensagemSecreta = mensagensSecretas[i];
						System.out.println(i + " - " + "De: " + pesquisarPessoas(mensagemSecreta.getDe()).getNome() + " Para: " + pesquisarPessoas(mensagemSecreta.getPara()).getNome() + " Recado: ***");
					}
					int indice = -1 ;
					while (indice < 0 || indice > i) {
						System.out.println("Digite o numero da mensagem que vc quer ler.");
						indice = s.nextInt();
						s.nextLine();
					}
					Mensagem mensagemSecreta = mensagensSecretas[indice];
					System.out.println("digite a senha para visualizar a mensagem:");
					String senha = s.nextLine();
					while (!senha.equals(mensagemSecreta.getSenha())) {
						System.out.println("Senha incorreta. Digite a senha novamente. Ou digite \"sair\" para voltar ao menu anterior.");
						senha = s.nextLine();
						if (senha.equals("sair")) {
							// sair
							System.out.println("Saindo.");
							break;
						}
					}
					if (!senha.equals("sair")) {
						System.out.println(indice + " - " + "De: " + pesquisarPessoas(mensagemSecreta.getDe()).getNome() + " Para: " + pesquisarPessoas(mensagemSecreta.getPara()).getNome() + " Recado: " + mensagemSecreta.getMensagem());
					} else {
						// usuario pediu para sair.
					}
				} else {
					System.out.println("Vc nao possui nenhum recado");
				}
			}
			
			default -> System.out.println("\nComando inválido");
			}
			System.out.println();
			menuRecados();
		}
		System.out.println();
		menuLogado();
	}

	public void menuLogado() {
		System.out.println("\nOla " + currentAccount.getNome() + ".");
		System.out.println("Seu Feed:\n");
		System.out.println("1 => Editar Perfil");
		System.out.println("2 => Ver Amigos");
		System.out.println("3 => Adicionar Amigos");
		System.out.println("4 => Recados");
		System.out.println("5 => para Sair");
		System.out.print("=>");
		int opcao = s.nextInt();
		s.nextLine();
		while (opcao != 5) {
			switch (opcao) {
			case 1 -> { // Editar perfil
				menuEditar();
			}
			case 2 -> { // Exibir amigos
				mostrarAmigos();
			}
			case 3 -> { // Adicionar amigos
				menuAddAmigos();
			}
			case 4 -> { // Enviar recados
				menuRecados();
			}
			default -> System.out.println("\nComando inválido");
			}
			this.menuLogado();
			opcao = s.nextInt();
			s.nextLine();
		}

		System.out.println("Ate a proxima " + currentAccount.getNome() + "!");
		this.menuStart();
	}

	public void menuStart() {
		System.out.println("Bem vindo ao UNIKUT!\n");
		System.out.println("Eh novo por aqui?");
		System.out.println("1 => Criar Nova Conta\n");

		System.out.println("Ja tem cadastro?");
		System.out.println("2 => Entrar na sua conta\n");

		System.out.println("Quer Nos Abandonar?");
		System.out.println("3 => para Sair");
		System.out.print("=>");

		int opcao = s.nextInt();
		s.nextLine();
		while (opcao != 3) {
			switch (opcao) {
			case 1 -> {
				this.criarNovaConta();
				this.menuLogado();
			}
			case 2 -> {
				this.login();
				this.menuLogado();
			}
			default -> System.out.println("\nComando inválido");
			}
		}
		System.out.println("Unikut encerrado!!!");
		Runtime.getRuntime().exit(0);
	}

	public void criarNovaConta() {
		// Criar Nova Conta
		String login, nickName, password, passwordConfirm;
		System.out.print("\nCriar Login: ");
		login = s.nextLine();
		while (login.length() == 0) {
			System.out.print("\nErro:\nLogin Nao Pode Ser Vazio\nPor Favor criar outro Login: ");
			login = s.nextLine();
		}
		// Verifica se o login ja existe
		while (this.verificaLogin(login)) {
			System.out.print("\nErro:\nLogin Ja Existente\nPor Favor criar outro Login: ");
			login = s.nextLine();
		}

		System.out.print("Criar Apelido (Opcional): ");
		nickName = s.nextLine();

		System.out.print("\nCrie uma senha: ");
		password = s.nextLine();
		//Verifica se a senha 1 esta vazia 
		while (password.length() == 0) {
			System.out.print("\nErro:\nSenha nao pode ser vazio: ");
			password = s.nextLine();
		}
		
		System.out.print("\nDigite a senha novamente: ");
		passwordConfirm = s.nextLine();
		
		// Verifica se as senhas estão iguais
		while (!password.equals(passwordConfirm)) {
			System.out.print("\nSuas senhas nao estao compativeis\nDigite novamente a senha: ");
			password = s.next();
			System.out.print("\nVerifique a senha: ");
			passwordConfirm = s.next();
		}
		// Se Nao inserir o nome, o mesmo recebe "Convidado"
		if (nickName.isEmpty() == true) {
			nickName = "Convidado!";
		}
		// System.out.println("\nBem Vindo Ao UNIKUT!");
		System.out.println(nickName + "!");
		System.out.println("Conta criada com sucesso...");

		// Criando a conta com os atributos coletados
		currentAccount = this.criarConta(login, nickName, password);
	}
	
	// Logica de negocio
	// Cria Conta
	public Conta criarConta(String login, String nickName, String password) {
		Conta novaConta = new Conta(login, nickName, password);
		contas[indiceConta] = novaConta;
		indiceConta++;
		return novaConta;
	}

	// Verifica se ja existe o login criado
	public boolean verificaLogin(String login) {
		boolean loginFound = false;
		for (int i = 0; i < indiceConta; i++) {
			if (login.equals(contas[i].getLogin())) {
				loginFound = true;
				break;
			}
		}

		return loginFound;
	}

	// Verifica se a senha existe e se é a do login correspondente
	public boolean verificaSenha(String login, String senha) {
		Conta conta = this.pesquisarPessoas(login);
		if (conta != null) {
			return senha.equals(conta.getSenha());
		}
		return false;

	}

	// Pesquisa contas ja Cadastradas
	public Conta pesquisarPessoas(String login) {
		for (int i = 0; i <= indiceConta; i++) {
			if (login.equals(contas[i].getLogin())) {
				return contas[i];
			}
		}
		return null;
	}

	// Editar Nome
	public void alterarContaNome(String login, String novoNome) {
		Conta conta = this.pesquisarPessoas(login);
		conta.setNome(novoNome);
	}

	// Editar Senha
	public void alterarContaSenha(String login, String novaSenha) {
		Conta conta = this.pesquisarPessoas(login);
		conta.setSenha(novaSenha);
	}

	// Exibir Contas e Dados
	public void exibir() {
		int i;
		for (i = 0; i < this.indiceConta; i++) {
			System.out.println("Usuario: " + i + "\nDados do usuario:\n" + this.contas[i]);
		}
	}
	
	// Retorna uma lista de solicitacoes de amizade o currentAccount, ou null caso nao existam solicitacoes
	public Amigo[] getSolicitacoesPendentes() {
		// vaculhar minhas contas
		Amigo[] solicitacoesPendentes = new Amigo[1000];
		int contadorDeAmigosPendentes = 0;
		for (int i = 0; i < indiceConta; i++) {
			Conta conta = contas[i];
			// nao incluir eu mesmo na busca
			if (!(currentAccount == conta)) {
				// vasculhar array de amigos de cada conta 
				Amigo[] amigosDaConta = conta.getAmigos();
				for (int j = 0; j < conta.getQuantidadeDeAmigos(); j++) {
					// encontrar solicitacoes pendentes apontando pra mim
					Amigo amigo = amigosDaConta[j];
					if (amigo.getPara().equals(currentAccount.getLogin()) && amigo.getPendente()) {
						solicitacoesPendentes[contadorDeAmigosPendentes] = amigo;
						contadorDeAmigosPendentes++;
					}
				}
			}
		}
		if (contadorDeAmigosPendentes > 0) {
			return solicitacoesPendentes;
		}
		
		return null;
	}
}
