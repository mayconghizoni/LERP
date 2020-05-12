$(document).ready(function () {
    buscarCategoria = function () {
        $.ajax({
            type: "GET",
            url: "/LERP/rest/categoria/buscar",
            success: function(dados) {
                //mostrar lista
            },
            error: function (info) {
                //Mostrar erro
            }
        })
    }
})
