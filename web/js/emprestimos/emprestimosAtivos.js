LERP.emprestimos = new Object();

$(document).ready(function () {

    LERP.emprestimos.buscar = function () {
        $.ajax({
            type: "GET",
            url: "/LERP/rest/emprestimo/buscar",
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

    LERP.emprestimos.exibir = function (listaEmprestimos) {


        if(listaEmprestimos != undefined && listaEmprestimos.length > 0 ) {

            var emprestimo = "<div>";

            for (var i = 0; i < listaEmprestimos.length; i++) {

                    emprestimo += "<div class=\"card livros\">" +
                        "<div class=\"card-body\">" +
                        "<h5 class=\"card-title\">" + listaEmprestimos[i].tituloExemplar + "</h5>" +
                        "<p class=\"card-text\"> Leitor: " + listaEmprestimos[i].nomeLeitor + "</p>" +
                        "<p class=\"card-text\"> Data de devolução: " + listaEmprestimos[i].dataSaida + "</p>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\" onclick=\"LERP.emprestimos.finalizarEmprestimo('"+listaEmprestimos[i].id+"')\">Finalizar empréstimo</button>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\">Alterar</button>" +
                        "</div>" +
                        "</div>"

            }

            emprestimo += "</div>";

        }

        return emprestimo;

    }

    LERP.emprestimos.buscar();

    LERP.emprestimos.finalizarEmprestimo = function (id) {

        let modalEmprestimo = {
            title: "Manutenção de exemplar",
            height: 200,
            width: 550,
            buttons:{
                "Sim": function(){
                    $.ajax({
                        type: "DELETE",
                        url: "/LERP/rest/emprestimo/finalizar/"+id,
                        success: function(msg){
                            LERP.modalAviso(msg);
                            LERP.emprestimos.buscar();
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

})

