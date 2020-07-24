LERP = new Object();

$(document).ready(function(){

    $("#body").load("home/home.html")

    LERP.modalAviso = function (aviso) {
        var modal ={
            title: "Mensagem",
            height: 250,
            width: 400,
            modal: true,
            buttons:{
                "OK": function(){
                    $(this).dialog("close");
                }
            }
        }

        $("#modalAviso").html(aviso)
        $("#modalAviso").dialog(modal)
    }

})



home = function(){
    $("#body").load("index.html")
}

cadastros = function(){
    $("#body").load("cadastros/cadastrarExemplar.html")
}

exemplares = function(){
    $("#body").load("exemplares/exemplaresDisponíveis.html")
}

emprestimos = function(){
    $("#body").load("emprestimos/emprestimosAtivos.html")
}

leitores = function(){
    $("#body").load("leitores/leitores.html")
}

relatorios = function(){
    $("#body").load("relatorios/relatorios.html")
}

perfil = function(){
    $("#body").load("perfil/perfil.html")
}

cadastrarExemplar = function(){
    $("#body").load("cadastros/cadastrarExemplar.html")
}

cadastrarCategorias = function(){
    $("#body").load("cadastros/cadastrarCategorias.html")
}

cadastrarLeitores = function(){
    $("#body").load("cadastros/cadastrarLeitores.html")
}

exemplaresDisponiveis = function () {
    $("#body").load("exemplares/exemplaresDisponíveis.html")
}

exemplaresManutencao = function () {
    $("#body").load("exemplares/exemplaresManutencao.html")
}

emprestimos = function () {
    $("#body").load("emprestimos/emprestimosAtivos.html")
}

novosEmprestimos = function () {
    $("#body").load("emprestimos/emprestimosNovos.html")
}

leitores = function () {
    $("#body").load("leitores/leitores.html")
}

leitoresDevedores = function () {
    $("#body").load("leitores/leitoresDevedores.html")
}
inativos = function () {
    $("#body").load("leitores/inativos.html")
}

relatorios = function () {
    $("#body").load("relatorios/relatorios.html")
}

cadastrarUsuario = function () {
    $("#body").load("cadastros/cadastrarUsuario.html")
}


