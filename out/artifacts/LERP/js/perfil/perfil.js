LERP.perfil = new Object();

$(document).ready(function () {

    LERP.perfil.buscarLogado = function () {

        $.ajax({
            type: "GET",
            url: "/LERP/rest/usuario/buscarLogado",
            success: function (dados) {

                if (dados == "") {
                    $("#perfil").html("");
                } else {
                    $("#perfil").html(LERP.perfil.exibir(dados));
                }

            },
            error: function (info) {
                LERP.modalAviso("Erro ao buscar usuários: " + info.status + " - " + info.statusText);
            }
        })

    }

    LERP.perfil.buscarLogado();

    LERP.perfil.exibir = function(listaUsuario) {

            var leitor = "<div>";

            if (listaUsuario.length == undefined){
                leitor += "<div class=\"card livros\">" +
                    "<div class=\"card-body\">" +
                    "<h5 class=\"card-title\"> Nome: " + listaUsuario.nome + "</h5>" +
                    "<p class=\"card-text\"> ID de usuário: #"+ listaUsuario.id + "</p>" +
                    "<p class=\"card-text\"> Email: " + listaUsuario.email + "</p>" +
                    "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\" onclick=\"LERP.perfil.atualizar('"+listaUsuario.id+"')\">Alterar informações</button>" +
                    "</div>" +
                    "</div>"
            }

            leitor += "</div>";

        return leitor;

    }

    LERP.perfil.atualizar = function (id) {

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
                            LERP.perfil.editar()
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

    LERP.perfil.editar = function () {

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
                    LERP.perfil.buscarLogado()
                    $("#editaUsuario").trigger("reset");
                },
                error: function (info) {
                    LERP.modalAviso("Erro ao alterar perfil: "+info.status+" - " + info.statusText);
                }
            })
        }

    }

})