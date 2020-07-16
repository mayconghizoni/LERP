$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "/LERP/rest/acesso/buscar",
        success: function(acesso) {

            if (acesso!=""){

                //Cria opção escolha com valor vazio como padrão caso haja algo no banco de dados
                $("#inputAcesso").html("")
                var option = document.createElement("option")
                option.setAttribute("value", "")
                option.innerHTML = ("Escolha")
                $("#inputAcesso").append(option)

                //A cada valor encontrado no banco, cria mais uma option dentro do select
                for (var i = 0; i < acesso.length; i++){

                    var option = document.createElement("option")
                    option.setAttribute("value", acesso[i].id)

                    option.innerHTML = (acesso[i].descricao)
                    $("#inputAcesso").append(option)

                }

            }else{
                $("#inputAcesso").html("")

                //Caso o não tenha nenhum valor cadastrado no banco ele cria uma uma option com aviso!
                var option = document.createElement("option")
                option.setAttribute("value", "")
                option.innerHTML = ("Cadastre um nível de acesso primeiro!")
                $("#inputAcesso").append(option)
                $("#inputAcesso").addClass("aviso")

            }
        },
        error: function (info) {
            LERP.modalAviso("Erro ao buscar níveis de acesso: "+info.status+" - " + info.statusText)

            $("#inputAcesso").html("")
            var option = document.createElement("option")
            option.setAttribute("value", "")
            option.innerHTML = ("Erro ao carregar níveis de acesso!")
            $("#inputAcesso").append(option)
            $("#inputAcesso").addClass("aviso")
        }
    })

    cadastrarNovoUsuario = function () {

        var usuario = new Object()

        usuario.nome = document.frmAddUsuario.inputNome.value;
        usuario.email = document.frmAddUsuario.inputEmail.value;
        usuario.senha = document.frmAddUsuario.inputSenha.value;
        usuario.acesso = document.frmAddUsuario.inputAcesso.value;

        if(usuario.nome == ""){
            alert("Preencha o campo Nome.")
            document.frmAddUsuario.inputNome.focus()
            return false
        }
        else if(usuario.email == ""){
            alert("Preencha o campo Email.")
            document.frmAddUsuario.inputEmail.focus()
            return false
        }
        else if(usuario.senha == ""){
            alert("Preencha o campo Senha.")
            document.frmAddUsuario.inputSenha.focus()
            return false
        }
        else if(usuario.acesso == ""){
            alert("Preencha o campo Nível de acesso.")
            document.frmAddUsuario.inputAcesso.focus()
            return false
        }
        else if(usuario.senha != document.frmAddUsuario.inputConfirmaSenha.value){
            alert("As senhas não coincidem, preencha o campo novamente. ")
            document.frmAddUsuario.inputSenha.focus()
            return false
        }
        else{

            //Criptografa senha em base64
            usuario.senha = btoa(usuario.senha);

            $.ajax({
                type: "POST",
                url: "/LERP/rest/usuario/inserir",
                data: JSON.stringify(usuario),

                success: function (msg) {
                    $("#addUsuario").trigger("reset");
                    LERP.modalAviso(msg);

                },
                error: function (info) {
                    LERP.modalAviso("Erro ao cadastrar usuário: "+info.status+" - " + info.statusText);
                }
            })

        }

    }

})