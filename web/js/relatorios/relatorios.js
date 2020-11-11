LERP.relatorios = new Object();

$(document).ready(function ()  {

    LERP.relatorios.buscarDados = function (){

        var dataInicio = document.frmRelatorio.dataInicio.value;
        var dataFim = document.frmRelatorio.dataFim.value;

        $.ajax({
            type: "GET",
            url: "/LERP/rest/relatorio/buscar",
            data: "dataInicio="+dataInicio+"&dataFim="+dataFim,
            success: function(dados){

            },
            error: function(info){
                LERP.modalAviso("Erro ao buscar dados para relatório: "+info.status+" - " + info.statusText);
            }
        })

    }

})