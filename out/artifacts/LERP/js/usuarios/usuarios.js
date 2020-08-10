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

                    if(listaUsuario[i].status == 0){

                        leitor += "<div class=\"card livros\">" +
                            "<div class=\"card-body\">" +
                            "<h5 class=\"card-title\"> Nome: " + listaUsuario[i].nome + "</h5>" +
                            "<p class=\"card-text\"> ID de usuário: #"+ listaUsuario[i].id + "</p>" +
                            "<p class=\"card-text\"> Email: " + listaUsuario[i].email + "</p>" +
                            "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\" onclick=\"LERP.usuario.atualizar('"+listaUsuario[i].id+"')\">Alterar informações</button>" +
                            "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\" onclick=\"LERP.usuario.inativar('"+listaUsuario[i].id+"')\">Inativar</button>" +
                            "</div>" +
                            "</div>"
                    }

                }

            leitor += "</div>";

        }

        return leitor;

    }

    LERP.usuario.atualizar = function (id) {

        $.ajax({
            type: "GET",
            url: "/LERP/rest/usuario/buscarPorId",
            data: "id="+id,
            success: function(usuario) {

                document.frmVisualizaEdicao.nome.value = usuario.nome
                document.frmVisualizaEdicao.id.value = usuario.id
                document.frmVisualizaEdicao.email.value = usuario.email

                var modalVisualizarEdicao = {
                    title: "Alterar cadastro de usuário",
                    height: 500,
                    width: 500,
                    buttons:{
                        "Salvar alterações" : function () {
                            LERP.usuario.editar()
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
                LERP.modalAviso("Erro ao buscar usuário: " + info.status + " - " + info.statusText);
            }
        })

    }

    LERP.usuario.editar = function () {

        var usuario = new Object()

        usuario.id = document.frmVisualizaEdicao.id.value
        usuario.nome = document.frmVisualizaEdicao.nome.value
        usuario.email = document.frmVisualizaEdicao.email.value
        usuario.senha = document.frmVisualizaEdicao.senha.value

        if(usuario.nome == ""){
            LERP.modalAviso("Preencha o campo Nome.")
            document.frmVisualizaEdicao.nome.focus()
            return false
        }
        else if (usuario.email == ""){
            alert("Preencha o campo Email.")
            document.frmVisualizaEdicao.email.focus()
            return false
        }
        else if (document.frmVisualizaEdicao.confirmaSenha.value != usuario.senha){
            alert("As senhas digitadas não correspondem.")
            document.frmVisualizaEdicao.confirmaSenha.focus()
            return false
        }
        else{

            usuario.senha = btoa(usuario.senha);

            $.ajax({
                type: "PUT",
                url: "/LERP/rest/usuario/alterar",
                data: JSON.stringify(usuario),
                success: function (msg) {
                    LERP.modalAviso(msg);
                    LERP.usuario.buscar()
                    $("#editaUsuario").trigger("reset");
                },
                error: function (info) {
                    LERP.modalAviso("Erro ao alterar usuario: "+info.status+" - " + info.statusText);
                }
            })
        }

    }

    LERP.usuario.inativar = function (id) {

        var modalInativarUsuario = {
            title: "Inativar Usuário",
            height: 200,
            width: 550,
            buttons:{
                "Sim": function () {

                    $.ajax({
                        type: "PUT",
                        url: "/LERP/rest/usuario/inativar/"+id,
                        success: function (msg) {
                            LERP.modalAviso(msg);
                            $("#modalInativarUsuario").dialog("close");
                            LERP.usuario.buscar();
                        },
                        error: function (info) {
                            LERP.modalAviso(info.responseText);
                            $("#modalInativarUsuario").dialog("close");
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

        $("#modalInativarUsuario").dialog(modalInativarUsuario);
    }

})