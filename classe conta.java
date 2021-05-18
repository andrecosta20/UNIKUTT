package Logica;

/**
 *
 * @author Lucas
 */
public class Conta implements Comparable<Conta> {
	private String login;
	private String nome;
	private String senha;
	private Amigo[] amigos;
	int contadorDeAmigos = 0;
	private Mensagem[] mensagens;
	int contadorDeMensagens = 0;

	public Conta(String login, String nome, String senha) {
		this.login = login;
		this.nome = nome;
		this.senha = senha;
		amigos = new Amigo[1000]; 
		mensagens = new Mensagem[1000];
	}
	
	public Mensagem[] getMensagens() {
		if (contadorDeMensagens > 0) {
			return mensagens;
		}
		return null;
	}
	
	public Mensagem[] getMensagensSecretas() {
		Mensagem[] mensagensSecretas = new Mensagem[1000];
		int j = 0;
		if (contadorDeMensagens > 0) {
			for (int i = 0; i < contadorDeMensagens; i++) {
				Mensagem mensagem = mensagens[i];
				if (mensagem.getSenha() != null) {
					mensagensSecretas[j] = mensagem;
					j++;
				}
			}
			if (j>0) {
				return mensagensSecretas;
			}
		}
		return null;
	}

	public void addMensagem(Mensagem mensagem) {
		mensagens[contadorDeMensagens] = mensagem;
		contadorDeMensagens++;
	}
	
	public int getQuantidadeDeAmigos() {
		return contadorDeAmigos;
	}
	
	public Amigo[] getAmigos() {
		return this.amigos;
	}
	
	public Amigo[] getAmigosPendentes() {
		Amigo[] amigosPendentes = new Amigo[contadorDeAmigos];
		int j = 0;
		for (int i = 0; i < contadorDeAmigos; i++) {
			Amigo amigo = amigos[i];
			if (amigo.getPendente()) {
				amigosPendentes[j] = amigo;
				j++;
			}
		}
		if (j>0) {
			return amigosPendentes;
		}
		return null;
	}
	
	public void convidarAmigo(String para) {
		Amigo amigo = new Amigo(login, para);
		amigos[contadorDeAmigos] = amigo;
		contadorDeAmigos++;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return this.login;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha() {
		return this.senha;
	}

	public String toString() {
		return "O Login: " + this.login + "\nUsuario: " + this.nome + "\nSenha: " + this.senha;
	}

	@Override
	public int compareTo(Conta C) {// comparacao de login.
		return this.login.compareTo(C.login);
	}
}
