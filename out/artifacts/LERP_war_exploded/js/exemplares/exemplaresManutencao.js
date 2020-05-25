// LERP.exemplares = new Object();

$(document).ready(function () {

    LERP.exemplares.buscar = function(){

        var status = 3;

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
                    "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\">Finalizar processo</button>" +
                    "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\">Descartar</button>" +
                    "</div>" +
                    "</div>"
            }

            exemplar += "</div>";

        }

        return exemplar;

    }

})