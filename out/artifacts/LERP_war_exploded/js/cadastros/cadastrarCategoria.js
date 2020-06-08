cadastrarNovaCategoria = function () {
    var categoria = new Object();
    categoria.nome = document.frmAddCategoria.inputCategoria.value;

    var categoria = categoria.nome
    var expRegCategoria = new RegExp("^[A-zÀ-ü]{3,}$")

    if(!expRegCategoria.test(categoria)){
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

