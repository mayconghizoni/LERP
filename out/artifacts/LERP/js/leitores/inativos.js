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
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\" onclick=\"LERP.leitores.visualizar('"+listaLeitores[i].id+"')\">Visualizar</button>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\" onclick=\"LERP.leitores.ativar('"+listaLeitores[i].id+"')\">Ativar Leitor</button>" +
                        "</div>" +
                        "</div>"
                }

            }

            leitor += "</div>";

        }

        return leitor;

    }

    LERP.leitores.buscar();

    LERP.leitores.visualizar = function (id) {

        $.ajax({
            type: "GET",
            url: "/LERP/rest/leitor/buscarPorId",
            data: "id="+id,
            success: function(leitor) {

                document.frmVisualizaEdicao.nome.value = leitor.nome
                document.frmVisualizaEdicao.id.value = leitor.id
                document.frmVisualizaEdicao.email.value = leitor.email
                document.frmVisualizaEdicao.cpf.value = leitor.cpf
                document.frmVisualizaEdicao.fone.value = leitor.fone
                document.frmVisualizaEdicao.endereco.value = leitor.endereco
                document.frmVisualizaEdicao.status.value = leitor.status

                var modalVisualizarEdicao = {
                    title: "Visualizar Leitor",
                    height: 500,
                    width: 500,
                    buttons:{
                        "Salvar alterações" : function () {
                            LERP.leitores.editar()
                            $(this).dialog("close")
                        },
                        "Fechar": function(){
                            $(this).dialog("close");
                        }
                    },
                    close: function(){
                        //caso o usuário simplesmente feche a caixa de edição não acontece nada.
                    }
                }

                $("#modalVisualizarEdicao").dialog(modalVisualizarEdicao);

            },
            error: function (info) {
                LERP.modalAviso("Erro ao buscar leitor: " + info.status + " - " + info.statusText);
            }
        })

    }

    LERP.leitores.editar = function () {

        var leitor = new Object()

        leitor.id = document.frmVisualizaEdicao.id.value
        leitor.nome = document.frmVisualizaEdicao.nome.value
        leitor.cpf = document.frmVisualizaEdicao.cpf.value
        leitor.fone = document.frmVisualizaEdicao.fone.value
        leitor.endereco = document.frmVisualizaEdicao.endereco.value
        leitor.email = document.frmVisualizaEdicao.email.value
        leitor.status = document.frmVisualizaEdicao.status.value

        var nome = leitor.nome
        var expRegNome = new RegExp("^[A-zÀ-ü]{3,}([ ]{1}[A-zÀ-ü]{2,})+$")

        var fone = leitor.fone
        var expRegFone = new RegExp("^[(]{1}[1-9]{2}[)]{1}[0-9]{4,5}[-]{1}[0-9]{4}$")

        var cpf = leitor.cpf
        var expRegCpf = new RegExp("^[0-9]{3}[.]{1}[0-9]{3}[.]{1}[0-9]{3}[-]{1}[0-9]{2}$")

        if(!expRegNome.test(nome)){
            LERP.modalAviso("Preencha o campo Nome.")
            document.frmVisualizaEdicao.nome.focus()
            return false
        }
        else if (leitor.email == ""){
            alert("Preencha o campo Email.")
            document.frmVisualizaEdicao.email.focus()
            return false
        }
        else if (!expRegFone.test(fone)){
            LERP.modalAviso("Preencha o campo Telefone.")
            document.frmVisualizaEdicao.fone.focus()
            return false
        }
        else if (!expRegCpf.test(cpf)){
            LERP.modalAviso("Preencha o campo CPF.")
            document.frmVisualizaEdicao.cpf.focus()
            return false
        }
        else if (leitor.endereco==""){
            LERP.modalAviso("Preencha o campo Endereço.")
            document.frmVisualizaEdicao.endereco.focus()
            return false
        }
        else if (leitor.status==""){
            LERP.modalAviso("Preencha o campo Status.")
            document.frmVisualizaEdicao.status.focus()
            return false
        }
        else if (leitor.id==""){
            LERP.modalAviso("Preencha o campo ID.")
            document.frmVisualizaEdicao.id.focus()
            return false
        }
        else{

            $.ajax({
                type: "PUT",
                url: "/LERP/rest/leitor/alterar",
                data: JSON.stringify(leitor),
                success: function (msg) {
                    LERP.leitores.buscar()
                    LERP.modalAviso(msg);

                },
                error: function (info) {
                    LERP.modalAviso("Erro ao alterar leitor: "+info.status+" - " + info.statusText);
                }
            })

        }

    }

    LERP.leitores.ativar = function (id) {

        let modalAtivarLeitor = {
            title: "Ativar Leitor",
            height: 200,
            width: 550,
            buttons:{
                "Sim": function () {

                    $.ajax({
                        type: "PUT",
                        url: "/LERP/rest/leitor/ativar/"+id,
                        success: function (msg) {
                            LERP.modalAviso(msg);
                            $("#modalAtivarLeitor").dialog("close");
                            LERP.leitores.buscar();
                        },
                        error: function (info) {
                            LERP.modalAviso(info.responseText);
                            $("#modalAtivarLeitor").dialog("close");
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

        $("#modalAtivarLeitor").dialog(modalAtivarLeitor);

    }

})