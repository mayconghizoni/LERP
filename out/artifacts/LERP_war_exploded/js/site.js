$(document).ready(function(){

    $("#body").load("pages/webapp/home.html")

})

home = function(){
    $("#body").load("pages/webapp/home.html")  
}

cadastros = function(){
    $("#body").load("pages/webapp/cadastros/cadastrarExemplar.html")
}

exemplares = function(){
    $("#body").load("pages/webapp/exemplares/exemplaresDisponíveis.html")
}

emprestimos = function(){
    $("#body").load("pages/webapp/emprestimos/emprestimosAtivos.html")
}

leitores = function(){
    $("#body").load("pages/webapp/leitores/leitores.html")
}

relatorios = function(){
    $("#body").load("pages/webapp/relatorios/relatorios.html")
}

perfil = function(){
    $("#body").load("pages/webapp/perfil/perfil.html")
}

cadastrarExemplar = function(){
    $("#body").load("pages/webapp/cadastros/cadastrarExemplar.html")
}

cadastrarCategorias = function(){
    $("#body").load("pages/webapp/cadastros/cadastrarCategorias.html")
}

cadastrarLeitores = function(){
    $("#body").load("pages/webapp/cadastros/cadastrarLeitores.html")
}

exemplaresDisponiveis = function () {
    $("#body").load("pages/webapp/exemplares/exemplaresDisponíveis.html")
}

exemplaresManutencao = function () {
    $("#body").load("pages/webapp/exemplares/exemplaresManutencao.html")
}

emprestimos = function () {
    $("#body").load("pages/webapp/emprestimos/emprestimosAtivos.html")
}

novosEmprestimos = function () {
    $("#body").load("pages/webapp/emprestimos/emprestimosNovos.html")
}

leitores = function () {
    $("#body").load("pages/webapp/leitores/leitores.html")
}

leitoresDevedores = function () {
    $("#body").load("pages/webapp/leitores/leitoresDevedores.html")
}

relatorios = function () {
    $("#body").load("pages/webapp/relatorios/relatorios.html")
}

//  DIVISAO INPORTANTE

cadastrarNovaCategoria = function () {
    var categoria = new Object();
    categoria.nome = document.frmAddCategoria.inputCategoria.value;

    if(categoria.nome == ""){
        alert("Preencha os campos corretamente!") // Fazer modal decente
    }else{
        // Continua AJAX

        $.ajax({
            type: "POST",
            url: "/LERP/rest/categoria/inserir",
            data: JSON.stringify(categoria),
            success: function (msg) {
                alert(msg);
                $("#addCategoria").trigger("reset");
            },
            error: function (info) {
                alert("ERRO");
            }
        })

    }

}
