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

})