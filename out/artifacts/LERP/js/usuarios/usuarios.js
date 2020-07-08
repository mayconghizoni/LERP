LERP.usuario = new Object();

$(document).ready(function () {

    LERP.usuario.buscar = function () {

        $.ajax({
            type: "GET",
            url: "/LERP/rest/usuario/buscar",
            success: function (dados) {

                if (dados == "") {
                    $("#listaUsuario").html("");
                } else {
                    $("#listaUsuario").html(LERP.usuario.exibir(dados));
                }

            },
            error: function (info) {
                LERP.modalAviso("Erro ao buscar usuários: " + info.status + " - " + info.statusText);
            }
        })

    }

    LERP.usuario.buscar();

    LERP.usuario.exibir = function(listaUsuario) {

        if(listaUsuario != undefined && listaUsuario.length > 0 ) {

            var leitor = "<div>";

            for (var i = 0; i < listaUsuario.length; i++) {

                leitor += "<div class=\"card livros\">" +
                    "<div class=\"card-body\">" +
                    "<h5 class=\"card-title\"> Nome: " + listaUsuario[i].nome + "</h5>" +
                    "<p class=\"card-text\"> ID de usuário: #"+ listaUsuario[i].id + "</p>" +
                    "<p class=\"card-text\"> Email: " + listaUsuario[i].email + "</p>" +
                    "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\">Alterar informações</button>" +
                    "</div>" +
                    "</div>"

            }

            leitor += "</div>";

        }

        return leitor;

    }

})