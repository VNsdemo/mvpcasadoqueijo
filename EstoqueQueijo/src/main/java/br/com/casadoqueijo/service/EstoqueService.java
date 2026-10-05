package br.com.casadoqueijo.service;

import br.com.casadoqueijo.model.Produto;
import br.com.casadoqueijo.repository.FornecedorRepository;
import br.com.casadoqueijo.repository.MovimentacaoRepository;
import br.com.casadoqueijo.repository.ProdutoRepository;
import java.util.List;

public class EstoqueService {
    private final ProdutoRepository produtos;
    private final FornecedorRepository fornecedores;
    private final MovimentacaoRepository movimentacoes;
    public EstoqueService(ProdutoRepository produtos, FornecedorRepository fornecedores, MovimentacaoRepository movimentacoes) {
        this.produtos=produtos; this.fornecedores=fornecedores; this.movimentacoes=movimentacoes;
    }
    public void cadastrar(Produto produto) {
        validarTexto(produto.nome(),"Nome"); validarTexto(produto.categoria(),"Categoria"); validarTexto(produto.unidade(),"Unidade");
        if (produto.quantidade()<0 || produto.estoqueMinimo()<0 || produto.precoCusto()<0) throw new IllegalArgumentException("Quantidade, estoque mínimo e preço não podem ser negativos.");
        if (produto.fornecedorId()!=null && fornecedores.listarTodos().stream().noneMatch(f -> f.id()==produto.fornecedorId())) throw new IllegalArgumentException("Fornecedor não encontrado.");
        produtos.salvar(produto);
    }
    public void movimentar(int produtoId, String tipo, double quantidade) {
        if (quantidade<=0) throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        Produto atual = produtos.buscarPorId(produtoId);
        if (atual==null) throw new IllegalArgumentException("Produto não encontrado.");
        if (tipo.equals("SAÍDA") && quantidade>atual.quantidade()) throw new IllegalArgumentException("Estoque insuficiente. Disponível: " + atual.quantidade() + " " + atual.unidade());
        double novoEstoque = tipo.equals("ENTRADA") ? atual.quantidade()+quantidade : atual.quantidade()-quantidade;
        produtos.atualizarQuantidade(produtoId,novoEstoque);
        movimentacoes.salvar(produtoId,tipo,quantidade);
    }
    public List<Produto> produtos() { return produtos.listarTodos(); }
    public List<Produto> criticos() { return produtos.listarAbaixoDoMinimo(); }
    public double sugestao(Produto p) { return Math.max(0,(p.estoqueMinimo()*2)-p.quantidade()); }
    private void validarTexto(String valor,String campo) { if (valor==null || valor.isBlank()) throw new IllegalArgumentException(campo+" é obrigatório."); }
}
