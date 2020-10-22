LERP.emprestimos = new Object();

$(document).ready(function () {

    LERP.emprestimos.buscar = function (pagina, first) {

        if (first){
            LERP.emprestimos.montarNavegacao(pagina)
        }else{
            var itensPorPag = 9;
            var offset = (pagina * itensPorPag) - itensPorPag;
            $.ajax({
                type: "GET",
                url: "/LERP/rest/emprestimo/buscar",
                data: "offset="+offset,
                success: function(dados){

                    console.log(dados)

                    if(dados == ""){
                        $("#listaEmprestimos").html("");
                    }else{

                        $("#listaEmprestimos").html(LERP.emprestimos.exibir(dados));
                    }

                },
                error: function(info){
                    LERP.modalAviso("Erro ao buscar exemplares: "+info.status+" - " + info.statusText);
                }
            })
        }

    }

    LERP.emprestimos.montarNavegacao = function (pagina){

        $.ajax({
            type: "GET",
            url: "/LERP/rest/emprestimo/buscarTotal",
            success: function(lista){
                var itensPorPag = 9;
                var quantidadePaginas = Math.ceil(lista.length / itensPorPag);
                var paginacao = "";
                for (x = 0; x < quantidadePaginas; x ++){
                    paginacao += "<li class=\"page-item\" onclick='LERP.emprestimos.buscar("+ (x + 1) +","+false+")'><a class=\"page-link\" href=\"#\">" + (x + 1) + "</a></li>"
                }
                $("#paginacao").html(paginacao);

                LERP.emprestimos.buscar(pagina, false);
            },
            error: function(info){
                LERP.modalAviso("Erro ao buscar usuários: "+info.status+" - " + info.statusText);
            }
        })
    }

    LERP.emprestimos.exibir = function (listaEmprestimos) {

        if(listaEmprestimos != undefined && listaEmprestimos.length > 0 ) {

            var emprestimo = "<div>";

            for (var i = 0; i < listaEmprestimos.length; i++) {

                    emprestimo += "<div class=\"card livros\">" +
                        "<div class=\"card-body\">" +
                        "<h5 class=\"card-title\">" + listaEmprestimos[i].tituloExemplar + "</h5>" +
                        "<p class=\"card-text\"> Leitor: " + listaEmprestimos[i].nomeLeitor + "</p>" +
                        "<p class=\"card-text\"> Data de retirada: " +listaEmprestimos[i].dataSaida +"</p>" +
                        "<p class=\"card-text\"> Data de devolução: " + listaEmprestimos[i].dataDev + "</p>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\" onclick=\"LERP.emprestimos.finalizarEmprestimo('"+listaEmprestimos[i].id+"')\">Finalizar</button>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\" onclick=\"LERP.emprestimos.prorrogarPrazo('"+listaEmprestimos[i].id+"')\">Prorrogar entrega</button>" +
                        "</div>" +
                        "</div>"

            }

            emprestimo += "</div>";

        }

        return emprestimo;

    }

    LERP.emprestimos.buscar(1, true);

    LERP.emprestimos.finalizarEmprestimo = function (id) {

        let modalEmprestimo = {
            title: "Finalizar emprestimo?",
            height: 200,
            width: 550,
            buttons:{
                "Sim": function(){
                    $.ajax({
                        type: "DELETE",
                        url: "/LERP/rest/emprestimo/finalizar/"+id,
                        success: function(msg){
                            LERP.modalAviso(msg);
                            LERP.emprestimos.buscar(1, true);
                            $("#modalEmprestimo").dialog("close");
                        },
                        error: function(info){
                            LERP.modalAviso(info.responseText);
                            $("#modalEmprestimo").dialog("close");
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

        $("#modalEmprestimo").dialog(modalEmprestimo);

    }

    LERP.emprestimos.prorrogarPrazo = function(id){

        let modalProrrogar = {
            title: "Prorrogar prazo de entrega",
            height: 200,
            width: 550,
            buttons:{
                "Sim": function(){
                    $.ajax({
                        type: "PUT",
                        url: "/LERP/rest/emprestimo/maisSete/"+id,
                        success: function(msg){
                            LERP.modalAviso(msg);
                            LERP.emprestimos.buscar(1, true);
                            $("#modalProrrogar").dialog("close");
                        },
                        error: function(info){
                            LERP.modalAviso(info.responseText);
                            $("#modalProrrogar").dialog("close");
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

        $("#modalProrrogar").dialog(modalProrrogar);


    }

    LERP.emprestimos.buscarPorParametro = function () {

        var valorBusca = $("#campoBuscarPorParametro").val();

        console.log(valorBusca)


        $.ajax({
            type: "GET",
            url: "/LERP/rest/emprestimo/buscarPorParametro",
            data: "valorBusca="+valorBusca,
            success: function (dados) {

                $("#listaEmprestimos").html(LERP.emprestimos.exibir(dados));

            },
            error: function (info) {
                LERP.modalAviso("Erro: "+ info.status + " - " + info.statusText)
            }



        })

    }
})

