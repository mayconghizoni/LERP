LERP.exemplares = new Object();

$(document).ready(function ()  {

    LERP.exemplares.buscar = function(){

        $.ajax({
            type: "GET",
            url: "/LERP/rest/exemplar/buscar",
            success: function(dados){

                if(dados == ""){
                    $("#listaExemplares").html("");
                }else{
                    $("#listaExemplares").html(LERP.exemplares.exibir(dados));
                }

            },
            error: function(info){
                LERP.modalAviso("Erro ao buscar exemplares: "+info.status+" - " + info.statusText);
            }
        })
    }

    LERP.exemplares.buscar();

    LERP.exemplares.exibir = function(listaExemplares) {

        if(listaExemplares != undefined && listaExemplares.length > 0 ) {

            var exemplar = "<div>";

            for (var i = 0; i < listaExemplares.length; i++) {

                if(listaExemplares[i].status == 1){

                    exemplar += "<div class=\"card livros\">" +
                        "<div class=\"card-body\">" +
                        "<h5 class=\"card-title\">" + listaExemplares[i].titulo + "</h5>" +
                        "<p class=\"card-text\">" + listaExemplares[i].autor + "</p>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\" onclick=\"LERP.exemplares.exibirCadastroEmprestimo('"+listaExemplares[i].id+"')\">Reserve agora</button>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\" onclick=\"LERP.exemplares.ativarManutencao('"+listaExemplares[i].id+"')\">Manutenção</button>" +
                        "</div>" +
                        "</div>"
                }

            }

            exemplar += "</div>";

        }


        return exemplar;

    }

    LERP.exemplares.exibirCadastroEmprestimo = function(id){

        document.frmCadastroEmprestimo.idExemplar.value = id;

        var modalManutencaoExemplar = {
            title: "Cadastrar novo empréstimo",
            height: 350,
            width: 550,
            buttons:{
                "Cadastrar": function(){

                    var emprestimo = new Object();

                    emprestimo.idLeitor = document.frmCadastroEmprestimo.idLeitor.value;
                    emprestimo.idLivro = document.frmCadastroEmprestimo.idExemplar.value;

                    if(emprestimo.idLeitor == ""){
                        LERP.modalAviso("Preencha o campo com o ID do leitor.")
                        document.frmAddEmprestimo.inputIdLeitor.focus()
                        return false
                    }
                    else if(emprestimo.idLivro == ""){
                        LERP.modalAviso("Preencha o campo com o ID do livro.")
                        document.frmAddEmprestimo.inputIdLivro.focus()
                    }
                    else{

                        $.ajax({
                            type: "POST",
                            url: "/LERP/rest/emprestimo/inserir",
                            data: JSON.stringify(emprestimo),

                            success: function (msg) {
                                LERP.modalAviso(msg);
                                LERP.exemplares.buscar();
                                $("#cadastraEmprestimo").trigger("reset");

                            },
                            error: function (info) {
                                LERP.modalAviso(info.responseText);
                            }
                        })
                        $(this).dialog("close");
                    }

                },
                "Cancelar": function(){
                    $(this).dialog("close");
                }
            },
            close: function(){
                $(this).dialog("close");
            }

        }

        $("#modalCadastroEmprestimo").dialog(modalManutencaoExemplar);

    }

    LERP.exemplares.ativarManutencao = function(id){

        let modalManutencaoExemplar = {
            title: "Manutenção de exemplar",
            height: 200,
            width: 550,
            buttons:{
                "Sim": function(){
                    $.ajax({
                        type: "PUT",
                        url: "/LERP/rest/exemplar/ativarManutencao/"+id,
                        success: function(msg){
                            LERP.modalAviso(msg);
                            LERP.exemplares.buscar();
                            $("#modalManutencao").dialog("close");
                        },
                        error: function(info){
                            LERP.modalAviso(info.responseText);
                            $("#modalManutencao").dialog("close");
                        }
                    })
                },
                "Cancelar": function(){
                    $(this).dialog("close");
                }
            },
            close: function(){
                $(this).dialog("close");
            }
        }

        $("#modalManutencao").dialog(modalManutencaoExemplar);

    }

    LERP.exemplares.buscarPorNome = function () {

        var valorBusca = $("#campoBuscarPorNome").val();

        console.log(valorBusca)

        $.ajax({
            type: "GET",
            url: "/LERP/rest/exemplar/buscarPorNome",
            data: "valorBusca="+valorBusca,
            success: function (dados) {

                $("#listaExemplares").html(LERP.exemplares.exibir(dados));

            },
            error: function (info) {
                LERP.modalAviso("Erro: "+ info.status + " - " + info.statusText)
            }



        })

    }
})


