LERP.exemplares = new Object();

$(document).ready(function () {

    LERP.exemplares.buscarCategorias = function(){
        $.ajax({
            type: "GET",
            url: "/LERP/rest/categoria/buscar",
            success: function(categorias) {

                if (categorias!=""){

                    //Cria opção escolha com valor vazio como padrão caso haja algo no banco de dados
                    $("#inputCategoria").html("")
                    var option = document.createElement("option")
                    option.setAttribute("value", "")
                    option.innerHTML = ("Escolha")
                    $("#inputCategoria").append(option)

                    //A cada valor encontrado no banco, cria mais uma option dentro do select
                    for (var i = 0; i < categorias.length; i++){

                        var option = document.createElement("option")
                        option.setAttribute("value", categorias[i].id)

                        option.innerHTML = (categorias[i].nome)
                        $("#inputCategoria").append(option)

                    }

                }else{
                    $("#inputCategoria").html("")

                    //Caso o não tenha nenhum valor cadastrado no banco ele cria uma uma option com aviso!
                    var option = document.createElement("option")
                    option.setAttribute("value", "")
                    option.innerHTML = ("Cadastre uma categoria primeiro!")
                    $("#inputCategoria").append(option)
                    $("#inputCategoria").addClass("aviso")

                }
            },
            error: function (info) {
                LERP.modalAviso("Erro ao buscar categorias: "+info.status+" - " + info.statusText)

                $("#inputCategoria").html("")
                var option = document.createElement("option")
                option.setAttribute("value", "")
                option.innerHTML = ("Erro ao carregar categorias!")
                $("#inputCategoria").append(option)
                $("#inputCategoria").addClass("aviso")
            }
        })
    }

    LERP.exemplares.buscarCategorias();

    LERP.exemplares.buscar = function(){

        var status = 1;

        $.ajax({
            type: "GET",
            url: "/LERP/rest/exemplar/buscar",
            data: "statusBusca="+status,
            success: function(dados){

                $("#listaExemplares").html(LERP.exemplares.exibir(dados));

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

                exemplar += "<div class=\"card livros\">" +
                    "<div class=\"card-body\">" +
                    "<h5 class=\"card-title\">" + listaExemplares[i].titulo + "</h5>" +
                    "<p class=\"card-text\">" + listaExemplares[i].autor + "</p>" +
                    "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\">Reserve agora</button>" +
                    "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\" onclick=\"LERP.exemplares.manutencao('"+listaExemplares[i].id+"')\">Manutenção</button>" +
                    "</div>" +
                    "</div>"
            }

            exemplar += "</div>";

        }


        return exemplar;

    }

    LERP.exemplares.manutencao = function(id){

        let modalManutencaoExemplar = {
            title: "Manutenção de exemplar",
            height: 200,
            width: 550,
            modal: true,
            buttons:{
                "Sim": function(){
                    $.ajax({
                        type: "PUT",
                        url: "/LERP/rest/exemplar/manutencao/"+id,
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

            }
        }

        $("#modalManutencao").dialog(modalManutencaoExemplar);

    }

})


