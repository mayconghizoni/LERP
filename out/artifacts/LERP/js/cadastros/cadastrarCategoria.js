LERP.cadastros = new Object()

cadastrarNovaCategoria = function () {
    var categoria = new Object();
    categoria.nome = document.frmAddCategoria.inputCategoria.value;

    if(categoria.nome == "" || categoria.nome == undefined){
        LERP.modalAviso("Preencha o campo Categoria.")
        document.frmAddCategoria.inputCategoria.focus()
        return false
    }
    else{

        $.ajax({
            type: "POST",
            url: "/LERP/rest/categoria/inserir",
            data: JSON.stringify(categoria),
            success: function (msg) {
                LERP.modalAviso(msg);
                $("#addCategoria").trigger("reset");
            },
            error: function (info) {
                LERP.modalAviso("Erro ao cadastrar categoria: "+info.status+" - " + info.statusText);
            }
        })

    }

}

LERP.cadastros.buscarLogado = function () {

    $.ajax({
        type: "GET",
        url: "/LERP/rest/usuario/buscarLogado",
        success: function (dados) {

            if (dados == "") {
                console.log("erro")
            } else {
                LERP.telasExibidas(dados)
            }

        },
        error: function (info) {
            LERP.modalAviso("Erro ao buscar usuário logado: " + info.status + " - " + info.statusText);
        }
    })

}

LERP.cadastros.buscarLogado()

LERP.telasExibidas = function(dados){
    if(dados.acesso != 1){
        $('#cadastrarUsuario').remove()
    }
}
