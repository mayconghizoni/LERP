$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "/LERP/rest/categoria/buscar",
        success: function(categorias) {

            if (categorias!=""){

                //Cria opção escolha com valor vazio como padrão caso haja algo no banco de dados
                $("#inputCategoria").html("")
                var option = document.createElement("option")
                option.setAttribute("value", "")
                option.innerHTML = ("Escolha")
                $("#inputCategoria").append(option)

                //A cada valor encontrado no banco, cria mais uma option dentro do select
                for (var i = 0; i < categorias.length; i++){

                    var option = document.createElement("option")
                    option.setAttribute("value", categorias[i].id)

                    option.innerHTML = (categorias[i].nome)
                    $("#inputCategoria").append(option)

                }

            }else{
                $("#inputCategoria").html("")

                //Caso o não tenha nenhum valor cadastrado no banco ele cria uma uma option com aviso!
                var option = document.createElement("option")
                option.setAttribute("value", "")
                option.innerHTML = ("Cadastre uma categoria primeiro!")
                $("#inputCategoria").append(option)
                $("#inputCategoria").addClass("aviso")

            }
        },
        error: function (info) {
            console.log("erro")
        }
    })
})
