LERP.leitores = new Object();

$(document).ready(function () {

    LERP.leitores.buscar = function () {

        $.ajax({
            type: "GET",
            url: "/LERP/rest/leitor/buscar",
            success: function (dados) {

                if (dados == "") {
                    $("#listaLeitores").html("");
                } else {
                    $("#listaLeitores").html(LERP.leitores.exibir(dados));
                }

            },
            error: function (info) {
                LERP.modalAviso("Erro ao buscar leitores: " + info.status + " - " + info.statusText);
            }
        })
    }

    LERP.leitores.exibir = function(listaLeitores) {

        if(listaLeitores != undefined && listaLeitores.length > 0 ) {

            var leitor = "<div>";

            for (var i = 0; i < listaLeitores.length; i++) {

                if(listaLeitores[i].status == 4){
                    leitor += "<div class=\"card livros\">" +
                        "<div class=\"card-body\">" +
                        "<h5 class=\"card-title\">" + listaLeitores[i].nome + "</h5>" +
                        "<p class=\"card-text\">" + listaLeitores[i].email + "</p>" +
                        "<p class=\"card-text\">" + listaLeitores[i].fone + "</p>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\">BOTAO 1</button>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\">BOTAO 2</button>" +
                        "</div>" +
                        "</div>"
                }

            }

            leitor += "</div>";

        }

        return leitor;

    }

    LERP.leitores.buscar();

})