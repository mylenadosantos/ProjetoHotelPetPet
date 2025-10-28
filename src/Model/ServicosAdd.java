package Model;

//Contrato que diz que qualquer classe que a use 
//PRECISA ter um jeito de adicionar serviços extras
public interface ServicosAdd {
	//Método obrigatório
    void adicionarServico(Servico servico);
}