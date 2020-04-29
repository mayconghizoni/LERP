$(document).ready(function(){

    $("#body").load("pages/webapp/home.html")   

})

home = function(){
    $("#body").load("pages/webapp/home.html")  
}

cadastros = function(){
    $("#body").load("pages/webapp/cadastrarExemplar.html") 
}

exemplares = function(){
    $("#body").load("pages/webapp/exemplaresDisponíveis.html")
}

emprestimos = function(){
    $("#body").load("pages/webapp/emprestimos.html")
}

leitores = function(){
    $("#body").load("pages/webapp/leitores.html")
}

relatorios = function(){
    $("#body").load("pages/webapp/relatorios.html")
}

perfil = function(){
    $("#body").load("pages/webapp/perfil.html")
}

// INICIO SUB PÁGINAS 

cadastrarExemplar = function(){
    $("#body").load("pages/webapp/cadastrarExemplar.html")  
}

cadastrarCategorias = function(){
    $("#body").load("pages/webapp/cadastrarCategorias.html")  
}

cadastrarLeitores = function(){
    $("#body").load("pages/webapp/cadastrarLeitores.html")  
}

exemplaresDisponiveis = function () {
    $("#body").load("pages/webapp/exemplaresDisponíveis.html")
}

exemplaresEmprestados = function () {
    $("#body").load("pages/webapp/exemplaresEmprestados.html")
}

exemplaresManutencao = function () {
    $("#body").load("pages/webapp/exemplaresManutencao.html")
}