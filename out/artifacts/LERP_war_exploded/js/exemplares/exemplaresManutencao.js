// LERP.exemplares = new Object();

$(document).ready(function () {

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

                if(listaExemplares[i].status == 3){

                    exemplar += "<div class=\"card livros\">" +
                        "<div class=\"card-body\">" +
                        "<h5 class=\"card-title\">" + listaExemplares[i].titulo + "</h5>" +
                        "<p class=\"card-text\">" + listaExemplares[i].autor + "</p>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\" onclick=\"LERP.exemplares.desativarManutencao('"+listaExemplares[i].id+"')\">Finalizar processo</button>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\" onclick=\"LERP.exemplares.excluir('"+listaExemplares[i].id+"')\">Descartar</button>" +
                        "</div>" +
                        "</div>"

                }
            }

            exemplar += "</div>";

        }

        return exemplar;

    }

    LERP.exemplares.excluir = function (id) {

        let modalExcluirExemplar = {
            title: "Excluir exemplar",
            height: 200,
            width: 550,
            buttons:{
                "Sim": function () {

                    $.ajax({
                        type: "DELETE",
                        url: "/LERP/rest/exemplar/deletar/"+id,
                        success: function (msg) {
                            LERP.modalAviso(msg);
                            $("#modalExcluirExemplar").dialog("close");
                            LERP.exemplares.buscar();
                        },
                        error: function (info) {
                            LERP.modalAviso(info.responseText);
                            $("#modalExcluirExemplar").dialog("close");
                        }
                    })

                },
                "Cancelar": function () {
                    $(this).dialog("close");
                }
            },
            close: function(){
                $(this).dialog("close");
            }
        }

        $("#modalExcluirExemplar").dialog(modalExcluirExemplar);

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

    LERP.exemplares.desativarManutencao = function(id){

        let modalManutencaoExemplar = {
            title: "Manutenção de exemplar",
            height: 200,
            width: 550,
            buttons:{
                "Sim": function(){
                    $.ajax({
                        type: "PUT",
                        url: "/LERP/rest/exemplar/desativarManutencao/"+id,
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

})