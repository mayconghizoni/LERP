cadastrarNovaCategoria = function () {
    var categoria = new Object();
    categoria.nome = document.frmAddCategoria.inputCategoria.value;

    if(categoria.nome === ""){
        alert("Preencha os campos corretamente!") // Fazer modal decente
    }else{
        // Continua AJAX

        $.ajax({
            type: "POST",
            url: "/LERP/rest/categoria/inserir",
            data: JSON.stringify(categoria),
            success: function (msg) {
                alert(msg);
                $("#addCategoria").trigger("reset");
            },
            error: function (info) {
                alert("ERRO");
            }
        })

    }

}