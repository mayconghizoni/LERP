LERP.caixa = new Object();

$(document).ready(function () {

    LERP.caixa.buscarValor = function () {

        $.ajax({
            type: "GET",
            url: "/LERP/rest/caixa/buscarValor",
            success: function (dados) {

                if (dados == "") {
                    $("#valorCaixa").html("");
                } else {
                    $("#valorCaixa").html(LERP.caixa.exibir(dados));
                }

            },
            error: function (info) {
                LERP.modalAviso("Erro ao buscar valor em caixa: " + info.status + " - " + info.statusText);
            }
        })

    }

    LERP.caixa.buscarValor();

    LERP.caixa.exibir = function(dados) {

        var caixa = "<div>";

            caixa += "<div class=\"card livros\">" +
                "<div class=\"card-body\">" +
                    "<h5>Valor em caixa: </h5>" +
                    "<p class=\"card-text\">R$"+LERP.formatarDinheiro(dados.valor)+"</p>" +
                    "<hr>" +
                    "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\" onclick=\"LERP.caixa.retirar()\">Retirar</button>" +
                    "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\" onclick=\"LERP.caixa.acrescentar()\">Acrescentar</button>" +
                    "</div>" +
                "</div>"

        caixa += "</div>";

        return caixa;

    }

    LERP.caixa.retirar = function () {

        var modalVisualizaRetirada = {
            title: "Valor de retirada de caixa",
            height: 350,
            width: 400,
            buttons:{
                "Retirar" : function () {
                    LERP.caixa.retirarValor()
                    $(this).dialog("close")
                },
                "Cancelar": function(){
                    $(this).dialog("close");
                }
            },
            close: function(){
                //caso o usuário simplesmente feche a caixa de edição não acontece nada.
            }
        }

        $("#modalVisualizaRetirada").dialog(modalVisualizaRetirada);

    }

    LERP.caixa.acrescentar = function () {

        var modalVisualizaAcrescimo = {
            title: "Valor de acrescimo de caixa",
            height: 350,
            width: 400,
            buttons:{
                "Acrescentar" : function () {
                    LERP.caixa.acrescentarValor();
                    $(this).dialog("close")
                },
                "Cancelar": function(){
                    $(this).dialog("close");
                }
            },
            close: function(){
                //caso o usuário simplesmente feche a caixa de edição não acontece nada.
            }
        }

        $("#modalVisualizaAcrescimo").dialog(modalVisualizaAcrescimo);

    }

})