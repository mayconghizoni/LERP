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
            LERP.modalAviso("Erro ao buscar categorias: "+info.status+" - " + info.statusText)

            $("#inputCategoria").html("")
            var option = document.createElement("option")
            option.setAttribute("value", "")
            option.innerHTML = ("Erro ao carregar categorias!")
            $("#inputCategoria").append(option)
            $("#inputCategoria").addClass("aviso")
        }
    })

})

cadastrarNovoExemplar = function () {
    var exemplar = new Object();
    exemplar.titulo = document.frmAddExemplar.inputObra.value;
    exemplar.autor = document.frmAddExemplar.inputAutor.value;
    exemplar.categoria = document.frmAddExemplar.inputCategoria.value;
    exemplar.ano = document.frmAddExemplar.inputAno.value;
    exemplar.edicao = document.frmAddExemplar.inputEdicao.value;

    if(exemplar.titulo === "" ||
    exemplar.autor === "" ||
    exemplar.categoria === "" ||
    exemplar.ano === "" ||
    exemplar.edicao === ""){
        alert("Preencha os campos corretamente!")
    }else{

        $.ajax({
            type: "POST",
            url: "/LERP/rest/exemplar/inserir",
            data: JSON.stringify(exemplar),

            success: function (msg) {
                LERP.modalAviso(msg);
                $("#addExemplar").trigger("reset");
            },
            error: function (info) {
                LERP.modalAviso("Erro ao cadastrar exemplar: "+info.status+" - " + info.statusText);
            }
        })

    }
}
