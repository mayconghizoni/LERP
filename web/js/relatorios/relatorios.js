LERP = new Object();
LERP.relatorios = new Object();

$(document).ready(function ()  {

    LERP.relatorios.validaDatas = function (){
        var dataInicio = document.frmRelatorio.dataInicio.value;
        var dataFim = document.frmRelatorio.dataFim.value;

        if((dataInicio == "" || dataInicio == undefined) || (dataFim == "" || dataFim == undefined)){
            return false;
        }
        return true;
    }

    LERP.relatorios.buscarDados = function (){
        if(LERP.relatorios.validaDatas()) {

            var dataInicio = document.frmRelatorio.dataInicio.value;
            var dataFim = document.frmRelatorio.dataFim.value;

            $.ajax({
                type: "GET",
                url: "/LERP/rest/relatorio/buscar",
                data: "dataInicio=" + dataInicio + "&dataFim=" + dataFim,
                success: function (dados) {
                    LERP.relatorios.geraHTML(JSON.parse(dados));
                },
                error: function (info) {
                    LERP.modalAviso("Erro ao buscar dados para relatório: " + info.status + " - " + info.statusText);
                }
            })
        }
    }

    LERP.relatorios.geraHTML= function (dados){
        $("#date").html(LERP.relatorios.pegaDataAtual())
        $("#inicio-periodo-relatorio").html("Inicio do periodo: " + LERP.relatorios.formataData(document.frmRelatorio.dataInicio.value));
        $("#fim-periodo-relatorio").html("Fim do periodo: " + LERP.relatorios.formataData(document.frmRelatorio.dataFim.value));


        var html = "<fieldset>"
        html += "<table style=\"width:100%; border: 1px solid black;\">\n" +
                "<tr>\n" +
                "<th>Exemplar</th>\n" +
                "<th>Categoria</th>\n" +
                "<th>Data Saida</th>\n" +
                "<th>Data devolução</th>\n" +
                "</tr>\n" +
                "</hr>\n";

        for(i=0; i < dados.length; i++){
            html += "<tr>\n" +
                    "<td>"+dados[i].tituloExemplar+"</td>\n" +
                    "<td>"+dados[i].idCategoria+"</td>\n" +
                    "<td>"+dados[i].dataSaida+"</td>\n" +
                    "<td>"+dados[i].dataDev+"</td>\n" +
                    "</tr>\n";
        }
        html += "</table>"
        html += "</fieldset>"
        $("#conteudo-relatorio").html(html)
        LERP.relatorios.abreModalRelatorio();
    }

    LERP.relatorios.abreModalRelatorio = function(){
        var modalVisualizarRelatorio = {
            title: "Visualizar relatório",
            height: 750,
            width: 700,
            buttons:{
                "Download" : function () {
                    LERP.relatorios.geraRelatorio()
                },
                "Fechar": function(){
                    $(this).dialog("close");
                }
            },
            close: function(){
                //caso o usuário simplesmente feche a caixa de edição não acontece nada.
            }
        }

        $("#imagemGerada").dialog(modalVisualizarRelatorio);
    }

    LERP.relatorios.geraRelatorio = function (){

        html2canvas(document.querySelector("#imagemGerada")).then(canvas => {
            var imgData = canvas.toDataURL('image/png');
            var doc = new jsPDF();
            doc.addImage(imgData, 'PNG', 10, 10);
            doc.save('relatório.pdf');
        });

    }

    LERP.relatorios.formataData = function (data){
        var dataString = data.split("-")
        return (dataString[2] + "/" + dataString[1] + "/" +dataString[0])


    }
    LERP.relatorios.pegaDataAtual = function (){
        date = new Date();
        var dataAtual = date.getDate() + "/" + (date.getMonth().length === 1 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "/" + date.getFullYear();
        return dataAtual;
    }

})