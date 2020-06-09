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

                if(listaLeitores[i].status == 1){
                    leitor += "<div class=\"card livros\">" +
                        "<div class=\"card-body\">" +
                        "<h5 class=\"card-title\">" + listaLeitores[i].nome + "</h5>" +
                        "<p class=\"card-text\">" + listaLeitores[i].email + "</p>" +
                        "<p class=\"card-text\">" + listaLeitores[i].fone + "</p>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\" onclick=\"LERP.leitores.visualizarEdicao('"+listaLeitores[i].id+"')\">Visualizar</button>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\")>Editar cadastro</button>" +
                        "</div>" +
                        "</div>"
                }

            }

            leitor += "</div>";

        }

        return leitor;

    }

    LERP.leitores.buscar()

    LERP.leitores.visualizarEdicao = function (id) {

        $.ajax({
            type: "GET",
            url: "/LERP/rest/leitor/buscarPorId",
            data: "id="+id,
            success: function(leitor) {
                console.log(leitor)

                document.frmVisualizaLeitor.nome.value = leitor.nome
                document.frmVisualizaLeitor.id.value = leitor.id
                document.frmVisualizaLeitor.email.value = leitor.email
                document.frmVisualizaLeitor.cpf.value = leitor.cpf
                document.frmVisualizaLeitor.fone.value = leitor.fone
                document.frmVisualizaLeitor.endereco.value = leitor.endereco
                document.frmVisualizaLeitor.status.value = leitor.status

                var modalVisualizarLeitor = {
                    title: "Visualizar Leitor",
                    height: 500,
                    width: 500,
                    buttons:{
                        "Fechar": function(){
                            $(this).dialog("close");
                        }
                    },
                    close: function(){
                        //caso o usuário simplesmente feche a caixa de edição não acontece nada.
                    }
                }

                $("#modalVisualizarLeitor").dialog(modalVisualizarLeitor);

            },
            error: function (info) {
                LERP.modalAviso("Erro ao buscar leitor: " + info.status + " - " + info.statusText);
            }
        })

    }
})